package nl.tudelft.bw4t.server;

import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.QueryException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.network.BW4TClientActions;
import nl.tudelft.bw4t.network.BW4TServerHiddenActions;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.server.eis.EntityInterface;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.environment.Launcher;

import org.apache.log4j.Logger;

/**
 * Server that allows connected client to perform actions and receive percepts from the central {@link BW4TEnvironment}
 * and notifies clients of new entities at the central {@link BW4TEnvironment}, all using RMI
 */
public class BW4TServer extends UnicastRemoteObject implements BW4TServerHiddenActions {

    /**
     * The log4j logger, logs to the console.
     */
    private static final Logger LOGGER = Logger.getLogger(BW4TServer.class);

    private static final long serialVersionUID = -3459272460308988888L;
    /**
     * Stores references to all connected clients plus information about them. Is not transfered to the clients as it is
     * only used by the server.
     */
    private transient Map<BW4TClientActions, ClientInfo> clients;

    private String servername;
    private String messageOfTheDay = "";
    private Registry registry;

    /**
     * Create a new instance of the server
     * 
     * @param serverIp
     *            the ip address that the server should listen to
     * @param serverPort
     *            the port that the server should listen to
     * 
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     * @throws MalformedURLException
     */
    public BW4TServer(String serverIp, String serverPort) throws RemoteException, MalformedURLException {
        super();
        reset();
        try {
            registry = LocateRegistry.createRegistry(Integer.parseInt(serverPort));
        } catch (RemoteException e) {
            LOGGER.warn("Registry is already running. Getting running registry instead.");
            registry = LocateRegistry.getRegistry(Integer.parseInt(serverPort));
        }
        servername = "rmi://" + serverIp + ":" + serverPort + "/BW4TServer";
        Naming.rebind(servername, this);
        LOGGER.debug("Server bound to: " + servername);
    }

    public BW4TServer(String serverIp, int serverPort, String motd) throws RemoteException, MalformedURLException {
        this(serverIp, Integer.toString(serverPort));
        messageOfTheDay = motd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerClient(BW4TClientActions client, int agentCount, int humanCount) throws RemoteException {
        registerClient(client, new ClientInfo(agentCount, humanCount));
    }

    @Override
    public void registerClient(BW4TClientActions client, BW4TClientConfig clientConfig)
            throws RemoteException {
        registerClient(client, new ClientInfo(clientConfig));
    }
    
    private void registerClient(BW4TClientActions client, ClientInfo cInfo) throws RemoteException{
        BW4TEnvironment env = Launcher.getInstance().getEnvironment();
        LOGGER.info("Registering client: " + client);
        clients.put(client, cInfo);

        // send the client our map
        client.useMap(BW4TEnvironment.getInstance().getMap());
        client.handleStateChange(getState());

        // for every request and attach them
        env.spawnBots(cInfo.getRequestedBots(), client);

        // for every request and attach them
        env.spawnEPartners(cInfo.getRequestedEPartners(), client);

        // set the path finding and collision arguments.
        env.setCollisionEnabled(cInfo.isCollisionEnabled());
        env.setDrawPathsEnabled(cInfo.isVisualizePaths());
    }

    @Override
    public void unregisterClient(BW4TClientActions client) {
        clients.remove(client);
    }

    /**
     * Notify given client of a new free entity, if the new entity is of interest for that client. Note, we use this
     * both when an entity is new and when an entity became free after use.
     * 
     * @param client
     * @param ci
     * @throws EntityException
     */
    public void notifyFreeEpartner(BW4TClientActions client, EPartnerConfig ci) throws EntityException {
        try {
            String entity = ci.getEpartnerName();
            if ("unknown".equals(Launcher.getInstance().getEnvironment().getType(entity))) {
                BW4TEnvironment.getInstance().setType(entity, EntityType.EPARTNER.nameLower());
            }
            client.handleNewEntity(entity);
            return;
        } catch (RemoteException e) {
            reportClientProblem(client, e);
        }
    }

    public void notifyFreeRobot(BW4TClientActions client, BotConfig ci) throws EntityException {
        try {
            String entity = ci.getBotName();
            if ("unknown".equals(Launcher.getInstance().getEnvironment().getType(entity))) {
                String type = "bot";
                if (EntityType.HUMAN == ci.getBotController()) {
                    type = "human";
                }
                BW4TEnvironment.getInstance().setType(entity, type);
            }
            client.handleNewEntity(entity);
            return;
        } catch (RemoteException e) {
            reportClientProblem(client, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregisterAgent(String agent) throws AgentException {
        BW4TEnvironment env = BW4TEnvironment.getInstance();
        Set<String> ents = env.getAssociatedEntities(agent);
        for (String entity : ents) {
            try {
                BW4TEnvironment.getInstance().deleteEntity(entity);
            } catch (EntityException | RelationException e) {
                throw new AgentException("failed to delete entity", e);
            }
        }
        env.unregisterAgent(agent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Percept performEntityAction(String entity, Action action) throws RemoteException {

        try {
            return BW4TEnvironment.getInstance().performClientAction(entity, action);
        } catch (ActException e) {
            throw new RemoteException("action failed", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void associateEntity(final String agentId, final String entityId) throws RelationException {
        BW4TEnvironment.getInstance().associateEntity(agentId, entityId);

        ((EntityInterface) BW4TEnvironment.getInstance().getEntity(entityId)).connect();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerAgent(String agentId) throws RemoteException, AgentException {
        BW4TEnvironment.getInstance().registerAgent(agentId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Percept> getAllPerceptsFromEntity(String entity) throws RemoteException {
        return BW4TEnvironment.getInstance().getAllPerceptsFrom(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAgents() throws RemoteException {
        return BW4TEnvironment.getInstance().getAgents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getAssociatedEntities(String agent) throws RemoteException, AgentException {
        return BW4TEnvironment.getInstance().getAssociatedEntities(agent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getEntities() throws RemoteException {
        return BW4TEnvironment.getInstance().getEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void freeEntity(String entity) throws RemoteException, RelationException, EntityException {
        BW4TEnvironment.getInstance().freeEntity(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void freeAgent(String agent) throws RemoteException, RelationException {
        BW4TEnvironment.getInstance().freeAgent(agent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void freePair(String agent, String entity) throws RemoteException, RelationException {
        BW4TEnvironment.getInstance().freePair(agent, entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getAssociatedAgents(String entity) throws RemoteException, EntityException {
        return BW4TEnvironment.getInstance().getAssociatedAgents(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getFreeEntities() throws RemoteException {
        return BW4TEnvironment.getInstance().getFreeEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupportedByEnvironment(Action arg0) throws RemoteException {
        return BW4TEnvironment.getInstance().isSupportedByEnvironment(arg0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType(String entity) throws RemoteException, EntityException {
        return BW4TEnvironment.getInstance().getType(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnvironmentState getState() throws RemoteException {
        return BW4TEnvironment.getInstance().getState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String queryProperty(String property) throws RemoteException, QueryException {
        return BW4TEnvironment.getInstance().queryProperty(property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String queryEntityProperty(String entity, String property) throws RemoteException {
        return BW4TEnvironment.getInstance().queryEntityProperty(entity, property);
    }

    public void reset() {
        clients = new HashMap<BW4TClientActions, ClientInfo>();
    }

    public void notifyDeletedEntity(String entity, Collection<String> agents) {
        for (BW4TClientActions client : clients.keySet()) {
            try {
                client.handleDeletedEntity(entity, agents);
            } catch (RemoteException e) {
                reportClientProblem(client, e);
            }
        }
    }

    /**
     * This is called when we should call back to one of our clients but it gives us an exception.
     * 
     * @param client
     * @param e
     */
    private void reportClientProblem(BW4TClientActions client, Exception e) {
        LOGGER.warn("Problems detected with client " + client, e);
    }

    /**
     * notify all clients of state change. If connection fails, this will modify client hashmaps. Therefore callers
     * should not iterate directly over the clientWaitingForAgent and clientWaitingForHuman arrays.
     * 
     * @param newState
     * @throws RemoteException
     */
    public void notifyStateChange(EnvironmentState newState) {
        // duplicate the set before iteration, since we may call
        // unregisterClient.
        Set<BW4TClientActions> clientset = new HashSet<BW4TClientActions>(this.clients.keySet());
        for (BW4TClientActions client : clientset) {
            try {
                client.handleStateChange(newState);
            } catch (RemoteException e) {
                reportClientProblem(client, e);
                unregisterClient(client);
            }

        }
    }

    @Override
    public void requestStart() throws RemoteException, ManagementException {
        BW4TEnvironment.getInstance().start();
    }

    @Override
    public void requestPause() throws RemoteException, ManagementException {
        BW4TEnvironment.getInstance().pause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void requestInit(java.util.Map<String, Parameter> parameters) throws RemoteException, ManagementException {
        BW4TEnvironment.getInstance().init(parameters);
    }

    @Override
    public void requestReset(Map<String, Parameter> parameters) throws RemoteException, ManagementException {
        throw new ManagementException("not implemented.");
    }

    /**
     * Notifies connected clients of new entities on a first come first serve basis. All clients should notify server of
     * how many entities they expect. The server moves on when expectations of a client have been fulfilled.
     * 
     * @param entity
     *            , the new entity
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     * @throws EntityException
     *             if something unexpected happens when attempting to add or remove an entity.
     */
    public void notifyNewEntity(String entity) {
    }

    /**
     * Stop the RMI service. Used in total reset of env, eg when loading new map.
     * 
     * @throws NotBoundException
     * @throws RemoteException
     * @throws AccessException
     */
    public void takeDown() {
        try {
            // Unregister ourself
            // Naming.unbind(servername);

            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);

        } catch (RemoteException e) {
            LOGGER.error("server disconnect RMI failed", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopServer(String key) throws RemoteException {
        Launcher.getInstance().getEnvironment().shutdownServer(key);

    }

}
