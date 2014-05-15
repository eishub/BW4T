package nl.tudelft.bw4t.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.server.BW4TServerActions;
import eis.EnvironmentInterfaceStandard;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * A client remote object that can be registered to a BW4TServer. This object
 * lives at the client side.
 * <p>
 * This object is a listener for server events, and forwards them to the owner:
 * the {@link BW4TRemoteEnvironment}.
 * 
 * @author trens
 * @author W.Pasman 8feb2012 changed to make this an explicit child of a
 *         {@link BW4TRemoteEnvironment}.
 * @modified W.Pasman 13feb2012 added start and pause calls.
 * 
 */
public class BW4TClient extends UnicastRemoteObject implements
		BW4TClientActions {

	private static final long serialVersionUID = 4060908402879332038L;

	/**
	 * The parent that we serve.
	 */
	private BW4TRemoteEnvironment parent;

	private String bindAddress;

	private BW4TServerActions server;

	/**
	 * the map that the server uses.
	 */
	private NewMap map;

	/**
	 * Create a listener for the server.
	 * 
	 * @param parent
	 *            is the parent {@link BW4TRemoteEnvironment}
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws NotBoundException
	 * @throws MalformedURLException
	 */
	protected BW4TClient(BW4TRemoteEnvironment parent) throws RemoteException,
			MalformedURLException, NotBoundException {
		this.parent = parent;
	}

	/**
	 * run the client. This is provided as separate function from the
	 * constructor, because the parent (BW4TEnvironment) needs to do some setup
	 * with the given constructor, before it is really ready to handle callbacks
	 * from the server.
	 * 
	 * 
	 * @param parameters
	 *            the set of initialization parameters as Map<String,Parameter>.
	 *            Should contain values for the following keys:
	 *            <ul>
	 *            <li>clientip
	 *            <li>clientport
	 *            <li>serverip
	 *            <li>serverport
	 *            <li>agentcount
	 *            <li>humancount
	 *            <li>goal
	 *            </ul>
	 *            Note, these are not all init parameters available to
	 *            BW4TRemoteEnvironment, these are just the ones needed to
	 *            launch this client class.
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public void connectServer(java.util.Map<String, Parameter> initParameters)
			throws RemoteException, MalformedURLException, NotBoundException {

		// Get all necessary parameters from initialization arguments
		Parameter clientIp = initParameters.get(InitParam.CLIENTIP.nameLower());
		String clientIpString = ((Identifier) clientIp).getValue();

		Parameter clientPort = initParameters.get(InitParam.CLIENTPORT
				.nameLower());
		String clientPortString = ((Identifier) clientPort).getValue();

		Parameter serverIp = initParameters.get(InitParam.SERVERIP.nameLower());
		String serverIpString = ((Identifier) serverIp).getValue();

		Parameter serverPort = initParameters.get(InitParam.SERVERPORT
				.nameLower());
		String serverPortString = ((Identifier) serverPort).getValue();

		// Launch the client and bind it
		bindAddress = "rmi://" + clientIpString + ":" + clientPortString
				+ "/BW4TClient";
		try {
			LocateRegistry.createRegistry(Integer.parseInt(clientPortString));
		} catch (Exception e) {
			System.out.println("Registry already created");
		}
		Naming.rebind(bindAddress, this);
		System.out.println("BW4TClient bound");

		// Register the client to the server
		String address = "//" + serverIpString + ":" + serverPortString
				+ "/BW4TServer";
		try {
			server = (BW4TServerActions) Naming.lookup(address);
		} catch (Exception e) {
			throw new NoEnvironmentException("Failed to connect " + address, e);
		}

	}

	/**
	 * Register our client with the server. Provided separately, of run
	 * 
	 * @param initParameters
	 * @throws RemoteException
	 */
	public void register(java.util.Map<String, Parameter> initParameters)
			throws RemoteException {
		Parameter agentCount = initParameters.get(InitParam.AGENTCOUNT
				.nameLower());
		int agentCountInt = Integer.parseInt(((Identifier) agentCount)
				.getValue());

		Parameter humanCount = initParameters.get(InitParam.HUMANCOUNT
				.nameLower());
		int humanCountInt = Integer.parseInt(((Identifier) humanCount)
				.getValue());

		server.registerClient(this, agentCountInt, humanCountInt);
	}

	/**
	 * kill the client interface. kill at this moment does not kill the server,
	 * it just disconnects the client. Make sure all entities and agents have
	 * been unbound before doing this.
	 * 
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws MalformedURLException
	 */
	public void kill() throws RemoteException, MalformedURLException,
			NotBoundException {
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
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	public Percept performEntityAction(String entity, Action action)
			throws RemoteException {
		return server.performEntityAction(entity, action);
	}

	/**
	 * 
	 * @param serverIP
	 *            , the ip address of the server
	 * @param serverPort
	 *            , the port where the server is listening
	 * @param expectedCount
	 *            , the amount of entities the client wants to receive
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws MalformedURLException
	 *             if the given url could not be parsed
	 * @throws NotBoundException
	 *             if the server was not bound at the given address
	 */
	protected void connectToServer(int agentCount, int humanCount)
			throws RemoteException, MalformedURLException, NotBoundException {
	}

	/**
	 * Associate an agent to an entity on the server
	 * 
	 * @param agentId
	 *            , an already registered agent that should be associated to the
	 *            entity
	 * @param entityId
	 *            , the entity that the agent should be associated to
	 * @throws RelationException
	 *             if the attempt to manipulate the agents-entities-relation has
	 *             failed.
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	public void associateEntity(String agentId, String entityId)
			throws RelationException, RemoteException {
		server.associateEntity(agentId, entityId);

	}

	/**
	 * Register an agent to the server, should be done before trying to
	 * associate the agent with an entity
	 * 
	 * @param agentId
	 *            , the agent to be registered
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws AgentException
	 *             if the attempt to register or unregister an agent has failed.
	 */
	public void registerAgent(String agentId) throws RemoteException,
			AgentException {
		server.registerAgent(agentId);
	}

	/**
	 * Get all percepts for a certain entity from the server
	 * 
	 * @param entity
	 *            , the entity for which percepts should be gotten
	 * @return a list of all received percepts
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	public LinkedList<Percept> getAllPerceptsFromEntity(String entity)
			throws RemoteException {
		return server.getAllPerceptsFromEntity(entity);
	}

	/**
	 * Get all agents registered on the server
	 * 
	 * @return a list of agent names
	 * @throws RemoteException
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 */
	public LinkedList<String> getAgents() throws RemoteException {
		return server.getAgents();
	}

	/**
	 * Get all associated entities for a certain agent from the server
	 * 
	 * @param agent
	 *            , the agent
	 * @return a list of entity names
	 * @throws RemoteException
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 * @throws AgentException
	 *             , if an attempt to register or unregister an agent has
	 *             failed.
	 */
	public HashSet<String> getAssociatedEntities(String agent)
			throws RemoteException, AgentException {
		return server.getAssociatedEntities(agent);
	}

	/**
	 * Unregister an agent on the server
	 * 
	 * @param agent
	 *            , the agent to unregister
	 * @throws AgentException
	 *             , if an attempt to register or unregister an agent has
	 *             failed.
	 * @throws RemoteException
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 */
	public void unregisterAgent(String agent) throws AgentException,
			RemoteException {
		server.unregisterAgent(agent);
	}

	/**
	 * Get all the entities on the server
	 * 
	 * @return the list of entities
	 * @throws RemoteException
	 *             , if an exception occurs during the execution of a remote
	 *             object call
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
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 * @throws RelationException
	 *             , if an attempt to manipulate the agents-entities-relation
	 *             has failed.
	 * @throws EntityException
	 *             , if something unexpected happens when attempting to add or
	 *             remove an entity.
	 */
	public void freeEntity(String entity) throws RemoteException,
			RelationException, EntityException {
		server.freeEntity(entity);
	}

	/**
	 * Free an agent on the server
	 * 
	 * @param agent
	 *            , the agent to free
	 * @throws RemoteException
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 * @throws RelationException
	 *             , if an attempt to manipulate the agents-entities-relation
	 *             has failed.
	 */
	public void freeAgent(String agent) throws RemoteException,
			RelationException {
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
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 * @throws RelationException
	 *             , if an attempt to manipulate the agents-entities-relation
	 *             has failed.
	 */
	public void freePair(String agent, String entity) throws RemoteException,
			RelationException {
		server.freePair(agent, entity);
	}

	/**
	 * Get all associated agents for a certain entity from the server
	 * 
	 * @param entity
	 *            , the entity for which to get all associated agents
	 * @return the list of agents
	 * @throws RemoteException
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 * @throws EntityException
	 *             , if something unexpected happens when attempting to add or
	 *             remove an entity.
	 */
	public Collection<String> getAssociatedAgents(String entity)
			throws RemoteException, EntityException {
		return server.getAssociatedAgents(entity);
	}

	/**
	 * Get all free entities on the server
	 * 
	 * @return all free entities
	 * @throws RemoteException
	 *             , if an exception occurs during the execution of a remote
	 *             object call
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
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 * @throws EntityException
	 *             , if something unexpected happens when attempting to add or
	 *             remove an entity.
	 */
	public String getType(String entity) throws RemoteException,
			EntityException {
		return server.getType(entity);
	}

	/**
	 * Get the state of the environment
	 * 
	 * @return the state of the environment
	 * @throws RemoteException
	 *             , if an exception occurs during the execution of a remote
	 *             object call
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
	 * @throws RemoteException
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 */
	public String queryProperty(String property) throws RemoteException {
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
	 *             , if an exception occurs during the execution of a remote
	 *             object call
	 */
	public String queryEntityProperty(String entity, String property)
			throws RemoteException {
		return server.queryEntityProperty(entity, property);
	}

	public boolean isSupportedByEnvironment(Action arg0) throws RemoteException {
		return server.isSupportedByEnvironment(arg0);
	}

	/**
	 * tell server to start. This will cause callback to us of state change when
	 * succesful.
	 */
	public void start() throws ManagementException {
		try {
			server.requestStart();
		} catch (RemoteException e) {
			throw new ManagementException("start failed", e);
		}
	}

	/**
	 * tell server to stop. This will cause callback to us of state change when
	 * succesful.
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
	 *            init parameters for eis. see
	 *            {@link EnvironmentInterfaceStandard#init(java.util.Map)}
	 */
	public void initServer(java.util.Map<String, Parameter> parameters)
			throws ManagementException {
		try {
			server.requestInit(parameters);
		} catch (RemoteException e) {
			throw new ManagementException("server init failed", e);
		}
	}

	/**
	 * reset the server, used for BatchRunner. NOTE: we don't do reset as it's
	 * not implemented, but we just do an INIT. This will clear and re-create
	 * the entities.
	 * 
	 * @param parameters
	 *            init parameters for eis. see
	 *            {@link EnvironmentInterfaceStandard#init(java.util.Map)}
	 */
	public void resetServer(java.util.Map<String, Parameter> parameters)
			throws ManagementException {
		try {
			server.requestInit(parameters);
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
	public void handleNewEntity(String entity) throws RemoteException,
			EntityException {
		parent.notifyNewEntity(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleFreeEntity(String entity, Collection<String> agents)
			throws RemoteException {
		parent.notifyFreeEntity(entity, agents);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleDeletedEntity(String entity, Collection<String> agents)
			throws RemoteException {
		parent.notifyDeletedEntity(entity, agents);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleStateChange(EnvironmentState newState)
			throws RemoteException {
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
