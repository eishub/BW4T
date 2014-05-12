package nl.tudelft.bw4t.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.BW4TClientInterface;
import eis.EnvironmentInterfaceStandard;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * Interface of the actions performed by BW4TServer.
 */
public interface BW4TServerInterface extends Remote {

	/**
	 * Communicate a message.
	 * 
	 * @return a String containing a message
	 * @throws RemoteException
	 *             if the remote was not responding correctly.
	 */
	String say() throws RemoteException;

	/**
	 * Called when a client wants to register itself to this server
	 * 
	 * @param client
	 *            the client that wants to register
	 * @param agentCount
	 *            the amount of agent entities that the client is waiting for
	 * @param humanCount
	 *            the amount of human GUI entities that the client is waiting
	 *            for
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	void registerClient(BW4TClientInterface client, int agentCount,
			int humanCount) throws RemoteException;

	/**
	 * Get the state of the environment
	 * 
	 * @return the state of the environment
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	EnvironmentState getState() throws RemoteException;

	/**
	 * Get all free entities on the server
	 * 
	 * @return all free entities
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	Collection<String> getFreeEntities() throws RemoteException;

	/**
	 * Remove a client from the server. At this moment the client makes sure
	 * that it frees its entities first.
	 * 
	 * @param client
	 *            the client to be unregistered
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	void unregisterClient(BW4TClientInterface client) throws RemoteException;

	/**
	 * Called when a client wants to perform an entity action
	 * 
	 * @param entity
	 *            the entity that should perform the action
	 * @param action
	 *            the action that should be performed
	 * @return the percept verifies the execution of the action
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	public Percept performEntityAction(String entity, Action action)
			throws RemoteException;

	/**
	 * Called when a client wants to associate an agent to an entity
	 * 
	 * @param agentId
	 *            the agent that should be associated to the entity
	 * @param entityId
	 *            the entity that the agent should be associated to
	 * @throws RelationException
	 *             if the attempt to manipulate the agents-entities-relation has
	 *             failed.
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	void associateEntity(String agentId, String entityId)
			throws RelationException, RemoteException;

	/**
	 * Called when a client wants to register an agent
	 * 
	 * @param agentId
	 *            the agent that should be registered
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws AgentException
	 *             if the attempt to register or unregister an agent has failed.
	 */
	void registerAgent(String agentId) throws RemoteException, AgentException;

	/**
	 * Called when a client wants to unregister an agent
	 * 
	 * @param agent
	 *            the agent that should be unregistered
	 * @throws AgentException
	 *             if the attempt to register or unregister an agent has failed.
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	void unregisterAgent(String agent) throws AgentException, RemoteException;

	/**
	 * Called when a client wants to get all percepts from an entity
	 * 
	 * @param entity
	 *            the entity for which all percepts should be gotten
	 * @return a list of all gotten percepts
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	LinkedList<Percept> getAllPerceptsFromEntity(String entity)
			throws RemoteException;

	/**
	 * Get all agents registered on the server
	 * 
	 * @return a list of agent names
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	LinkedList<String> getAgents() throws RemoteException;

	/**
	 * Get all associated entities for a certain agent from the server
	 * 
	 * @param agent
	 *            the agent
	 * @return a list of entity names
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws AgentException
	 *             if an attempt to register or unregister an agent has failed.
	 */
	HashSet<String> getAssociatedEntities(String agent) throws RemoteException,
			AgentException;

	/**
	 * Get all the entities on the server
	 * 
	 * @return the list of entities
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	Collection<String> getEntities() throws RemoteException;

	/**
	 * Free an entity on the server
	 * 
	 * @param entity
	 *            the entity to free
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws RelationException
	 *             if an attempt to manipulate the agents-entities-relation has
	 *             failed.
	 * @throws EntityException
	 *             if something unexpected happens when attempting to add or
	 *             remove an entity.
	 */
	void freeEntity(String entity) throws RemoteException, RelationException,
			EntityException;

	/**
	 * Free an agent on the server
	 * 
	 * @param agent
	 *            the agent to free
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws RelationException
	 *             if an attempt to manipulate the agents-entities-relation has
	 *             failed.
	 */
	void freeAgent(String agent) throws RemoteException, RelationException;

	/**
	 * Free an agent-entity pair on the server
	 * 
	 * @param agent
	 *            the agent for which an entity should be freed
	 * @param entity
	 *            the entity to be freed
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws RelationException
	 *             if an attempt to manipulate the agents-entities-relation has
	 *             failed.
	 */
	void freePair(String agent, String entity) throws RemoteException,
			RelationException;

	/**
	 * Get all associated agents for a certain entity from the server
	 * 
	 * @param entity
	 *            the entity for which to get all associated agents
	 * @return the list of agents
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws EntityException
	 *             if something unexpected happens when attempting to add or
	 *             remove an entity.
	 */
	Collection<String> getAssociatedAgents(String entity)
			throws RemoteException, EntityException;

	/**
	 * Get the type of an entity
	 * 
	 * @param entity
	 *            the entity for which to get its type
	 * @return the type of the entity
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 * @throws EntityException
	 *             if something unexpected happens when attempting to add or
	 *             remove an entity.
	 */
	String getType(String entity) throws RemoteException, EntityException;

	/**
	 * Query a property of the server environment
	 * 
	 * @param property
	 *            the property to query
	 * @return the property's value
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	String queryProperty(String property) throws RemoteException;

	/**
	 * Query a property of an entity on the server
	 * 
	 * @param entity
	 *            the entity for which to query the property
	 * @param property
	 *            the property to query
	 * @return the value of the property
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	String queryEntityProperty(String entity, String property)
			throws RemoteException;

	/**
	 * Check whether the given action is supported in the environment
	 * 
	 * @param action
	 *            the {@link Action} that needs to be tested
	 * @return true iff the environment supports the given action
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	boolean isSupportedByEnvironment(Action action) throws RemoteException;

	/**
	 * request the server to start environment.
	 * 
	 * @throws ManagementException
	 *             thrown if the server is unable to change the state of the
	 *             environment
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	void requestStart() throws RemoteException, ManagementException;

	/**
	 * request the server to pause environment.
	 * 
	 * @throws ManagementException
	 *             thrown if the server is unable to change the state of the
	 *             environment
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	void requestPause() throws RemoteException, ManagementException;

	/**
	 * initialize the server. Does not reset the connection, but loads map and
	 * resets repast.
	 * 
	 * @param parameters
	 *            init parameters for eis. see
	 *            {@link EnvironmentInterfaceStandard#init(java.util.Map)}
	 * 
	 * @throws ManagementException
	 *             thrown if the server is unable to change the state of the
	 *             environment
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	void requestInit(java.util.Map<String, Parameter> parameters)
			throws RemoteException, ManagementException;

	/**
	 * reset the server, following the requirements for BatchRunner. This means
	 * that no new entities are created, but the old ones are left connected.
	 * 
	 * @param parameters
	 *            init parameters for eis. see
	 *            {@link EnvironmentInterfaceStandard#init(java.util.Map)}
	 * @throws ManagementException
	 *             thrown if the server is unable to change the state of the
	 *             environment
	 * @throws RemoteException
	 *             if an exception occurs during the execution of a remote
	 *             object call
	 */
	void requestReset(java.util.Map<String, Parameter> parameters)
			throws RemoteException, ManagementException;

	/**
	 * TODO: methods from RemoteEnvironment
	 */
	void notifyFreeEntity(String entity, Collection<String> agents);

	void handleStateChange(EnvironmentState newState);

	void notifyNewEntity(String entity);

	void notifyDeletedEntity(String entity, Collection<String> agents);

}
