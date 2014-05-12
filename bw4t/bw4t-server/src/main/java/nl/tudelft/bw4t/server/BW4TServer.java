package nl.tudelft.bw4t.server;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import nl.tudelft.bw4t.client.BW4TClientInterface;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;



/**
 * The BW4TServer, to which the client can connect to.
 */
public class BW4TServer extends UnicastRemoteObject implements
        BW4TServerInterface {

    /**
     * The serial, generated.
     */
    private static final long serialVersionUID = -7313583479222142380L;
    /**
     * The message which is going to be sent towards the client.
     */
    private final String message;

	/**
	 * Hashmaps to track which clients are connected
	 * TODO: explain difference
	 */
    private final HashMap<BW4TClientInterface, Integer> clientWaitingForAgent;
	/**
	 * Hashmaps to track which clients are connected (2)
	 */
	private final HashMap<BW4TClientInterface, Integer> clientWaitingForHuman;

	/**
	 * ip and port of the server.
	 */
	private final String servername; 
	
    /**
     * The constructor, which creates the server object.
     * @param msg The message that should be said.
     * @throws RemoteException If anything goes wrong with the set up.
     */
	public BW4TServer(String serverIp, String serverPort, String msg)
			throws RemoteException, MalformedURLException {
		super();
        message = msg;
        clientWaitingForAgent = new HashMap<BW4TClientInterface, Integer>();
		clientWaitingForHuman = new HashMap<BW4TClientInterface, Integer>();
		Registry registry;
		try {
			registry = LocateRegistry.createRegistry(Integer
					.parseInt(serverPort));
		} catch (RemoteException e) {
			System.out.println("registry is already running. Reconnecting.");
			registry = LocateRegistry.getRegistry(Integer.parseInt(serverPort));
		}
		servername = "rmi://" + serverIp + ":" + serverPort + "/BW4TServer";
		Naming.rebind(servername, this);
    }

    /**
     * Method which says the message towards the client.
     * @return message The message being said.
     */
    @Override
	public final String say() {

        return message;
    }

    /**
     * The main method launching the BW4TServer.
     * @param args Optional Arguments, not used.
     * @throws RemoteException If something goes wrong during initialization.
     */
    public static void main(final String[] args) throws RemoteException {
        String servername;
        String serverIp = "127.0.0.1";
        String serverPort = "8989";
        @SuppressWarnings("unused")
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(Integer
                    .parseInt(serverPort));
        } catch (RemoteException e) {
            System.out.println("Registry is already running. Reconnecting.");
            registry = LocateRegistry.getRegistry(Integer.parseInt(serverPort));
        }
        servername = "rmi://" + serverIp + ":" + serverPort + "/BW4TServer";
        try {
            Naming.rebind(servername, new BW4TServer(serverIp,serverPort,
                    "Hello Client! Welcome to the BW4TServer."));
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
        System.out
                .println("BW4TServer is ready to accept incoming connections.");
    }

	@Override
	public void registerClient(BW4TClientInterface client, int agentCount, int humanCount) throws RemoteException {
		System.out.println("registerClient " + client);
		clientWaitingForAgent.put(client, new Integer(agentCount));
		clientWaitingForHuman.put(client, new Integer(humanCount));

		/**
		 * #2234. First, tell client which map to use. Because of the many
		 * things that may relate to maps, this must come before the client gets
		 * to do anything else.
		 */
		client.useMap(BW4TEnvironment.getInstance().getMap());

		// #2013 late-listening pattern... partial implement.
		client.handleStateChange(getState());
		// System.out.println("late=listening: " + getFreeEntities());
		for (String entity : getFreeEntities()) {
			try {
				notifyFreeEntity(client, entity);
			} catch (EntityException e) {
				reportClientProblem(client, e);
			}
		}
		
	}
	
	/**
	 * Method to remove client from server list.
	 */
	@Override
	public void unregisterClient(BW4TClientInterface client) {
		clientWaitingForAgent.remove(client);
		clientWaitingForHuman.remove(client);

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Percept performEntityAction(String entity, Action action)
			throws RemoteException {

		try {
			return BW4TEnvironment.getInstance().performClientAction(entity,
					action);
		} catch (ActException e) {
			throw new RemoteException("action failed", e);
		}
	}
	
	/**
	 * Notify given client of a new free entity, if the new entity is of
	 * interest for that client. Note, we use this both when an entity is new
	 * and when an entity became free after use.
	 * 
	 * @param client
	 * @param entity
	 * @throws EntityException
	 */
	private void notifyFreeEntity(BW4TClientInterface client, String entity)
			throws EntityException {
		String type = BW4TEnvironment.getInstance().getType(entity);

		if (clientWaitingForAgent.get(client).intValue() > 0
				&& (type.equals("unknown") || type.equals("bot"))) {
			try {
				if (type.equals("unknown")) {
					BW4TEnvironment.getInstance().setType(entity, "bot");
				}
				client.handleNewEntity(entity);
				// Client is now waiting for one less entity
				clientWaitingForAgent.put(client, new Integer(
						clientWaitingForAgent.get(client).intValue() - 1));
				return;
			} catch (RemoteException e) {
				reportClientProblem(client, e);
			}
		} else if (clientWaitingForHuman.get(client).intValue() > 0
				&& (type.equals("unknown") || type.equals("human"))) {
			try {
				if (type.equals("unknown")) {
					BW4TEnvironment.getInstance().setType(entity, "human");
				}
				client.handleNewEntity(entity);
				// Client is now waiting for one less entity
				clientWaitingForHuman.put(client, new Integer(
						clientWaitingForHuman.get(client).intValue() - 1));
				return;
			} catch (RemoteException e) {
				reportClientProblem(client, e);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * TODO: object is cast to an interface
	 */
	@Override
	public void associateEntity(final String agentId, final String entityId)
			throws RelationException {
		BW4TEnvironment.getInstance().associateEntity(agentId, entityId);

		((RobotEntityInterface) BW4TEnvironment.getInstance().getEntity(entityId))
				.connect();

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerAgent(String agentId) throws RemoteException,
			AgentException {
		BW4TEnvironment.getInstance().registerAgent(agentId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unregisterAgent(String agent) throws AgentException {
		BW4TEnvironment.getInstance().unregisterAgent(agent);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public LinkedList<Percept> getAllPerceptsFromEntity(String entity)
			throws RemoteException {
		return BW4TEnvironment.getInstance().getAllPerceptsFrom(entity);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashSet<String> getAssociatedEntities(String agent)
			throws RemoteException, AgentException {
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
	public void freeEntity(String entity) throws RemoteException,
			RelationException, EntityException {
		BW4TEnvironment.getInstance().freeEntity(entity);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public LinkedList<String> getAgents() throws RemoteException {
		return BW4TEnvironment.getInstance().getAgents();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void freeAgent(String agent) throws RemoteException,
			RelationException {
		BW4TEnvironment.getInstance().freeAgent(agent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void freePair(String agent, String entity) throws RemoteException,
			RelationException {
		BW4TEnvironment.getInstance().freePair(agent, entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getAssociatedAgents(String entity)
			throws RemoteException, EntityException {
		return BW4TEnvironment.getInstance().getAssociatedAgents(entity);
	}

	/**
	 * This is called when we should call back to one of our clients but it
	 * gives us an exception.
	 * 
	 * @param client
	 * @param e
	 */
	private void reportClientProblem(BW4TClientInterface client, Exception e) {
		System.out.println("issue with client " + client + ":" + e);
		e.printStackTrace();
	}

	/**
	 * {@inheritDoc}
	 * instance GetState() comes from EIS package.
	 */
	@Override
	public EnvironmentState getState() throws RemoteException {
		return BW4TEnvironment.getInstance().getState();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getType(String entity) throws RemoteException,
			EntityException {
		return BW4TEnvironment.getInstance().getType(entity);
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
	public String queryProperty(String property) throws RemoteException {
		return BW4TEnvironment.getInstance().queryProperty(property);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String queryEntityProperty(String entity, String property)
			throws RemoteException {
		return BW4TEnvironment.getInstance().queryEntityProperty(entity,
				property);
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
	public void requestStart() throws RemoteException, ManagementException {
		BW4TEnvironment.getInstance().start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void requestPause() throws RemoteException, ManagementException {
		BW4TEnvironment.getInstance().pause();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void requestInit(java.util.Map<String, Parameter> parameters)
			throws RemoteException, ManagementException {
		BW4TEnvironment.getInstance().init(parameters);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void requestReset(Map<String, Parameter> parameters)
			throws RemoteException, ManagementException {
		throw new ManagementException("not implemented.");
	}
	
	/**
	 * Notifies connected clients of new entities on a first come first server
	 * basis All clients should notify server of how many entities they expect
	 * The server moves on when expectations of a client have been fulfilled
	 * 
	 * @param entity
	 *            , the new entity
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws EntityException
	 *             if something unexpected happens when attempting to add or
	 *             remove an entity.
	 */
	public void notifyNewEntity(String entity) {
		for (BW4TClientInterface client : clientWaitingForAgent.keySet()) {
			try {
				notifyFreeEntity(client, entity);
			} catch (EntityException e) {
				reportClientProblem(client, e);
			}
		}
	}

	/**
	 * Stop the RMI service. Used in total reset of env, eg when loading new
	 * map.
	 * 
	 * @throws NotBoundException
	 * @throws RemoteException
	 * @throws AccessException
	 */
	public void takeDown() {
		try {
			// registry.unbind(servername); //throws for unknown reason.
			UnicastRemoteObject.unexportObject(this, true);
		} catch (NoSuchObjectException e) {
			System.err.println("server disconnect RMI failed" + e);
			e.printStackTrace();
		}
	}
}
