package nl.tudelft.bw4t.network;

import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.QueryException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;

/**
 * This interface defines the calls that the clients can make to the server. Note, this interface does not support kill
 * of the server, since we want the server to stay alive if the client is killed.
 */
public interface BW4TServerActions extends Remote {

    /**
     * Called when a client wants to register itself to this server
     * 
     * @param client
     *            the client that wants to register
     * @param agentCount
     *            the amount of agent entities that the client is waiting for
     * @param humanCount
     *            the amount of human GUI entities that the client is waiting for
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     */
    void registerClient(BW4TClientActions client, int agentCount, int humanCount) throws RemoteException;

    /**
     * Called when a client wants to register itself to this server
     * 
     * @param client
     *            the client that wants to register
     * @param config
     *           The client configuration used (can be null).
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     */
    void registerClient(BW4TClientActions client, BW4TClientConfig config) throws RemoteException;

    /**
     * Remove a client from the server. At this moment the client makes sure that it frees its entities first.
     * 
     * @param client
     *            the client interface to be disconnected
     * @throws RemoteException communication-related exception
     *                 may occur during the execution of a remote method call
     */
    void unregisterClient(BW4TClientActions client) throws RemoteException;

    /**
     * Called when a client wants to perform an entity action
     * 
     * @param entity
     *            , the entity that should perform the action
     * @param action
     *            , the action that should be performed
     * @return Percept 
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     */
    Percept performEntityAction(String entity, Action action) throws RemoteException;

    /**
     * Called when a client wants to associate an agent to an entity
     * 
     * @param agentId
     *            , the agent that should be associated to the entity
     * @param entityId
     *            , the entity that the agent should be associated to
     * @throws RelationException
     *             if the attempt to manipulate the agents-entities-relation has failed.
     * @throws RemoteException communication-related exception
     *                 may occur during the execution of a remote method call
     */
    void associateEntity(String agentId, String entityId) throws RelationException, RemoteException;

    /**
     * Called when a client wants to register an agent
     * 
     * @param agentId
     *            , the agent that should be registered
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     * @throws AgentException
     *             if the attempt to register or unregister an agent has failed.
     */
    void registerAgent(String agentId) throws RemoteException, AgentException;

    /**
     * Called when a client wants to unregister an agent
     * 
     * @param agent
     *            , the agent that should be unregistered
     * @throws AgentException
     *             if the attempt to register or unregister an agent has failed.
     * @throws RemoteException communication-related exception
     *                 may occur during the execution of a remote method call
     */
    void unregisterAgent(String agent) throws AgentException, RemoteException;

    /**
     * Called when a client wants to get all percepts from an entity
     * 
     * @param entity
     *            , the entity for which all percepts should be gotten
     * @return a list of all gotten percepts
     * @throws RemoteException
     *             if an exception occurs during the execution of a remote object call
     */
    List<Percept> getAllPerceptsFromEntity(String entity) throws RemoteException;

    /**
     * Get all agents registered on the server
     * 
     * @return a list of agent names
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     */
    List<String> getAgents() throws RemoteException;

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
    Set<String> getAssociatedEntities(String agent) throws RemoteException, AgentException;

    /**
     * Get all the entities on the server
     * 
     * @return the list of entities
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     */
    Collection<String> getEntities() throws RemoteException;

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
    void freeEntity(String entity) throws RemoteException, RelationException, EntityException;

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
    void freeAgent(String agent) throws RemoteException, RelationException;

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
    void freePair(String agent, String entity) throws RemoteException, RelationException;

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
    Collection<String> getAssociatedAgents(String entity) throws RemoteException, EntityException;

    /**
     * Get all free entities on the server
     * 
     * @return all free entities
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     */
    Collection<String> getFreeEntities() throws RemoteException;

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
    String getType(String entity) throws RemoteException, EntityException;

    /**
     * Get the state of the environment
     * 
     * @return the state of the environment
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     */
    EnvironmentState getState() throws RemoteException;

    /**
     * Query a property of the server environment
     * 
     * @param property
     *            , the property to query
     * @return the property's value
     * @throws QueryException
     *             , if an exception occurs during the execution of a remote object call 
     * @throws RemoteException communication-related exception
     *                 may occur during the execution of a remote method call
     */
    String queryProperty(String property) throws RemoteException, QueryException;

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
    String queryEntityProperty(String entity, String property) throws RemoteException;

    /**
     * 
     * @param action 
     * @return true
     *             iff action is supported by the environment.
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     */
    boolean isSupportedByEnvironment(Action action) throws RemoteException;

   /**
    * request the server to start environment.
    *
    * @throws RemoteException
    *             , if an exception occurs during the execution of a remote object call
    * @throws ManagementException
    *                 , if an attempt to manage an environment did not succeed.
    */
    void requestStart() throws RemoteException, ManagementException;

    /**
     * request the server to pause environment.
     *  
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     * @throws ManagementException
     *                 , if an attempt to manage an environment did not succeed.
     */
    void requestPause() throws RemoteException, ManagementException;

    /**
     * initialize the server. Does not reset the connection, but loads map and resets repast
     * 
     * @param parameters
     *            init parameters for eis. see {@link EnvironmentInterfaceStandard#init(java.util.Map)}
     *
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     * @throws ManagementException
     *                 , if an attempt to manage an environment did not succeed.
     */
    void requestInit(Map<String, Parameter> parameters) throws RemoteException, ManagementException;

    /**
     * * reset the server, following the requirements for {@link BatchRunner}. This means that no new entities are
     * created, but the old ones are left connected.
     * 
     * @param parameters
     *            init parameters for eis. see {@link EnvironmentInterfaceStandard#init(java.util.Map)}
     * @throws RemoteException
     *             , if an exception occurs during the execution of a remote object call
     * @throws ManagementException
     *                 , if an attempt to manage an environment did not succeed.
     */
    void requestReset(Map<String, Parameter> parameters) throws RemoteException, ManagementException;

}
