package nl.tudelft.bw4t.client;

import eis.EnvironmentInterfaceStandard;
import eis.EnvironmentListener;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.QueryException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.startup.ConfigFile;
import nl.tudelft.bw4t.client.startup.InitParam;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.network.BW4TClientActions;
import nl.tudelft.bw4t.network.BW4TServerActions;
import nl.tudelft.bw4t.network.BW4TServerHiddenActions;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;

import org.apache.log4j.Logger;

/**
 * A client remote object that can be registered to a BW4TServer. This object lives at the client side.
 * This object is a listener for server events, and forwards them to the owner: the {@link RemoteEnvironment}.
 */
public class BW4TClient extends UnicastRemoteObject implements BW4TClientActions {
    private static final long serialVersionUID = -7174958200299731682L;

    /**
     * The parent that we serve.
     */
    private final EnvironmentListener parent;

    private String bindAddress;

    private BW4TServerActions server;

    private static final Logger LOGGER = Logger.getLogger(BW4TClient.class);

    /**
     * the map that the server uses.
     */
    private NewMap map;

    
    /**
     * Create a listener for the server.
     * 
     * @param parent
     *           is the parent {@link RemoteEnvironment}
     * @throws RemoteException
     *          if an exception occurs during the execution of a remote object call
     * @throws MalformedURLException 
     * @throws NotBoundException 
     */
    public BW4TClient(EnvironmentListener parent) throws RemoteException, MalformedURLException, NotBoundException {
        this.parent = parent;
    }

    /**
     * run the client. This is provided as separate function from the constructor, because the parent (BW4TEnvironment)
     * needs to do some setup with the given constructor, before it is really ready to handle callbacks from the server.
     * 
     * @param parameters
     *            the set of initialization parameters as Map<String,Parameter>. Should contain values for the following
     *            keys:
     *            <ul>
     *            <li>clientip
     *            <li>clientport
     *            <li>serverip
     *            <li>serverport
     *            <li>agentcount
     *            <li>humancount
     *            <li>goal
     *            </ul>
     *            Note, these are not all init parameters available to BW4TRemoteEnvironment, these are just the ones
     *            needed to launch this client class.
     * @throws MalformedURLException 
     * @throws RemoteException 
     * @throws NotBoundException 
     */
    public void connectServer() throws RemoteException, MalformedURLException, NotBoundException {

        int portNumber = Integer.parseInt(InitParam.CLIENTPORT.getValue());

        // Launch the client and bind it
        Registry register = null;
        while (register == null) {
            try {
                bindAddress = "rmi://" + InitParam.CLIENTIP.getValue() + ":" + portNumber + "/BW4TClient";
                register = LocateRegistry.createRegistry(portNumber);
            } catch (Exception e) {
                LOGGER.warn("Registry was already created, trying the next port number.", e);
                portNumber++;
            }
        }
        Naming.rebind(bindAddress, this);
        LOGGER.info("The BW4T Client is bound to: " + bindAddress);

        // Register the client to the server
        String address = "//" + InitParam.SERVERIP.getValue() + ":" + InitParam.SERVERPORT.getValue() + "/BW4TServer";
        try {
            server = (BW4TServerActions) Naming.lookup(address);
        } catch (Exception e) {
            LOGGER.error("The BW4T Client failed to connect to the server: " + address);
            throw new NoEnvironmentException("Failed to connect " + address, e);
        }

    }

    /**
     * Try to shutdown the server with the given parameters.
     * 
     * @param shutdownParams
     *            the parameters given by console
     */
    public void shutdownServer() {
        String killKey = InitParam.KILL.getValue();

        // Register the client to the server
        String address = "//" + InitParam.SERVERIP.getValue() + ":" + InitParam.SERVERPORT.getValue() + "/BW4TServer";
        try {
            server = (BW4TServerActions) Naming.lookup(address);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            LOGGER.info("The server is already down: " + address);
            return;
        }

        LOGGER.info("Attempting to shutdown the server with key: " + killKey);
        try {
            ((BW4TServerHiddenActions) server).stopServer(killKey);
        } catch (RemoteException e) {
            // Will always throw an exception because the server stop the connection before we can get an answer from it
            return;
        }
        LOGGER.error("Unable to shutdown the server.");
    }

    /**
     * Register our client with the server. Provided separately, of run
     * 
     * @param initParameters
     * @throws RemoteException 
     */
    public void register() throws RemoteException {
        if (ConfigFile.hasReadInitFile()) {
            BW4TClientConfig conf = ConfigFile.getConfig();
            
            LOGGER.info(String.format("Requesting %d robots and %d e-partners.", 
                    conf.getAmountBot(), 
                    conf.getAmountEPartner()));
            server.registerClient(this, conf);
        } else {
            int agentCountInt = Integer.parseInt(InitParam.AGENTCOUNT.getValue());
            int humanCountInt = Integer.parseInt(InitParam.HUMANCOUNT.getValue());

            LOGGER.info("Requesting " + agentCountInt + " automated agent(s) and " + humanCountInt + " human agent(s)");
            server.registerClient(this, agentCountInt, humanCountInt);
        }
    }

    /**
     * kill the client interface. kill at this moment does not kill the server, it just disconnects the client. Make
     * sure all entities and agents have been unbound before doing this.
     * 
     * @throws RemoteException 
     * @throws NotBoundException 
     * @throws MalformedURLException 
     */
    public void kill() throws RemoteException, MalformedURLException, NotBoundException {
        server.unregisterClient(this);
        Naming.unbind(bindAddress);
        UnicastRemoteObject.unexportObject(this, true);
    }

    /**
     * Perform an entity action on the server
     * 
     * @param entity
     *            , the entity that should perform the action
     * @param action
     *            , the action to be performed
     * @return the resulting percept of the action
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     */
    public Percept performEntityAction(String entity, Action action) throws RemoteException {
        LOGGER.debug("Entity " + entity + " performing action: " + action.toProlog());
        return server.performEntityAction(entity, action);
    }

    /**
     * Associate an agent to an entity on the server
     * 
     * @param agentId
     *            , an already registered agent that should be associated to the entity
     * @param entityId
     *            , the entity that the agent should be associated to
     * @throws RelationException
     *             if the attempt to manipulate the agents-entities-relation has failed.
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     */
    public void associateEntity(String agentId, String entityId) throws RelationException, RemoteException {
        LOGGER.debug("Agent " + agentId + " associated with entity: " + entityId);
        server.associateEntity(agentId, entityId);
    }

    /**
     * Register an agent to the server, should be done before trying to associate the agent with an entity
     * 
     * @param agentId
     *            , the agent to be registered
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     * @throws AgentException
     *             if the attempt to register or unregister an agent has failed.
     */
    public void registerAgent(String agentId) throws RemoteException, AgentException {
        server.registerAgent(agentId);
        LOGGER.debug("Register agent: " + agentId);
    }

    /**
     * Get all percepts for a certain entity from the server
     * 
     * @param entity
     *            , the entity for which percepts should be gotten
     * @return a list of all received percepts
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     */
    public List<Percept> getAllPerceptsFromEntity(String entity) throws RemoteException {
        return server.getAllPerceptsFromEntity(entity);
    }

    /**
     * Get all agents registered on the server
     * 
     * @return a list of agent names
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     */
    public List<String> getAgents() throws RemoteException {
        return server.getAgents();
    }

    /**
     * Get all associated entities for a certain agent from the server
     * 
     * @param agent
     *            , the agent
     * @return a list of entity names
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     * @throws AgentException
     *             , if an attempt to register or unregister an agent has failed.
     */
    public Set<String> getAssociatedEntities(String agent) throws RemoteException, AgentException {
        return server.getAssociatedEntities(agent);
    }

    /**
     * Unregister an agent on the server
     * 
     * @param agentId
     *            , the agent to unregister
     * @throws AgentException
     *             , if an attempt to register or unregister an agent has failed.
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     */
    public void unregisterAgent(String agentId) throws AgentException, RemoteException {
        server.unregisterAgent(agentId);
        LOGGER.debug("Unregistered agent: " + agentId);
    }

    /**
     * Get all the entities on the server
     * 
     * @return the list of entities
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     */
    public Collection<String> getEntities() throws RemoteException {
        return server.getEntities();
    }

    /**
     * Free an entity on the server
     * 
     * @param entity
     *            , the entity to free
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     * @throws RelationException
     *             , if an attempt to manipulate the agents-entities-relation has failed.
     * @throws EntityException
     *             , if something unexpected happens when attempting to add or remove an entity.
     */
    public void freeEntity(String entity) throws RemoteException, RelationException, EntityException {
        server.freeEntity(entity);
    }

    /**
     * Free an agent on the server
     * 
     * @param agent
     *            , the agent to free
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     * @throws RelationException
     *             , if an attempt to manipulate the agents-entities-relation has failed.
     */
    public void freeAgent(String agent) throws RemoteException, RelationException {
        server.freeAgent(agent);
    }

    /**
     * Free an agent-entity pair on the server
     * 
     * @param agent
     *            , the agent for which an entity should be freed
     * @param entity
     *            , the entity to be freed
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     * @throws RelationException
     *             , if an attempt to manipulate the agents-entities-relation has failed.
     */
    public void freePair(String agent, String entity) throws RemoteException, RelationException {
        server.freePair(agent, entity);
    }

    /**
     * Get all associated agents for a certain entity from the server
     * 
     * @param entity
     *            , the entity for which to get all associated agents
     * @return the list of agents
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     * @throws EntityException
     *             , if something unexpected happens when attempting to add or remove an entity.
     */
    public Collection<String> getAssociatedAgents(String entity) throws RemoteException, EntityException {
        return server.getAssociatedAgents(entity);
    }

    /**
     * Get all free entities on the server
     * 
     * @return all free entities
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     */
    public Collection<String> getFreeEntities() throws RemoteException {
        return server.getFreeEntities();
    }

    /**
     * Get the type of an entity
     * 
     * @param entity
     *            , the entity for which to get its type
     * @return the type of the entity
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     * @throws EntityException
     *             , if something unexpected happens when attempting to add or remove an entity.
     */
    public String getType(String entity) throws RemoteException, EntityException {
        return server.getType(entity);
    }

    /**
     * Get the state of the environment
     * 
     * @return the state of the environment
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     */
    public EnvironmentState getState() throws RemoteException {
        return server.getState();
    }

    /**
     * Query a property of the server environment
     * 
     * @param property
     *            , the property to query
     * @return the property's value
     * @throws QueryException the server was unable to query
     * @throws RemoteException if an exception occurs during the execution of a remote object call
     */
    public String queryProperty(String property) throws QueryException, RemoteException {
        return server.queryProperty(property);
    }

    /**
     * Query a property of an entity on the server
     * 
     * @param entity
     *            , the entity for which to query the property
     * @param property
     *            , the property to query
     * @return the value of the property
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     */
    public String queryEntityProperty(String entity, String property) throws RemoteException {
        return server.queryEntityProperty(entity, property);
    }

    /**
     * 
     * @param arg0 
     *      The action to be checked
     * @return
     *      returns true if an Action is supported by the environment
     * @throws RemoteException 
     */
    public boolean isSupportedByEnvironment(Action arg0) throws RemoteException {
        return server.isSupportedByEnvironment(arg0);
    }

    /**
     * tell server to start. This will cause callback to us of state change when succesful.
     * @throws ManagementException 
     *          Throws a ManagementException when the server fails to start
     */
    public void start() throws ManagementException {
        try {
            server.requestStart();
        } catch (RemoteException e) {
            throw new ManagementException("start failed", e);
        }
    }

    /**
     * tell server to stop. This will cause callback to us of state change when succesful.
     * @throws ManagementException 
     *          Throws a ManagementException when the server fails to pause
     */
    public void pause() throws ManagementException {
        try {
            server.requestPause();
        } catch (RemoteException e) {
            throw new ManagementException("pause failed", e);
        }
    }

    /**
     * initialize the server.
     * 
     * @param parameters
     *            init parameters for eis. see {@link EnvironmentInterfaceStandard#init(java.util.Map)}
     * @throws ManagementException 
     *          Throws a ManagementException when the server fails to initialize
     */
    public void initServer(java.util.Map<String, Parameter> parameters) throws ManagementException {
        try {
            server.requestInit(parameters);
        } catch (RemoteException e) {
            throw new ManagementException("server init failed", e);
        }
    }

    /**
     * reset the server, used for BatchRunner. NOTE: we don't do reset as it's not implemented, but we just do an INIT.
     * This will clear and re-create the entities.
     * 
     * @param parameters
     *            init parameters for eis. see {@link EnvironmentInterfaceStandard#init(java.util.Map)}
     * @throws ManagementException 
     *          Throws a ManagementException when the server fails to reset
     */
    public void resetServer(java.util.Map<String, Parameter> parameters) throws ManagementException {
        try {
            server.requestInit(parameters);
            LOGGER.info("BW4T Server was reset.");
        } catch (RemoteException e) {
            throw new ManagementException("server reset failed", e);
        }
    }

    /**********************************************************************/
    /******************** Implements BW4TClientActions ********************/
    /**********************************************************************/
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleNewEntity(String entity) throws RemoteException, EntityException {
        parent.handleNewEntity(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFreeEntity(String entity, Collection<String> agents) throws RemoteException {
        parent.handleFreeEntity(entity, agents);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleDeletedEntity(String entity, Collection<String> agents) throws RemoteException {
        parent.handleDeletedEntity(entity, agents);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleStateChange(EnvironmentState newState) throws RemoteException {
        parent.handleStateChange(newState);
    }

    @Override
    public void useMap(NewMap newMap) {
        map = newMap;
    }

    public NewMap getMap() {
        return map;
    }

}
