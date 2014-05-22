package nl.tudelft.bw4t.client.environment;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.operations.ProcessingOperations;
import nl.tudelft.bw4t.client.startup.InitParam;
import nl.tudelft.bw4t.map.NewMap;

import org.apache.log4j.Logger;

import eis.AgentListener;
import eis.EnvironmentInterfaceStandard;
import eis.EnvironmentListener;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.QueryException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * A remote BW4TEnvironment that delegates all actions towards the central
 * BW4TEnvironment, through RMI. This is the "Client", the connector for goal.
 * This object lives on the client, and is a singleton (so one per JVM).
 * <p>
 * You can launch a stand-alone BW4TRemoteEnvironment (via {@link #main}.
 * Typical args are: <code>
 *  -clientip localhost -serverip localhost -clientport 2000
 * -serverport 8000 -launchgui true -map
 * BW4TClient/environments/maps/ColorTestScenario -agentcount 0 -humancount 2 
 * </code> to run 2 HumanGUIs. Note though that these agents will not be coupled
 * to GOAL, and will not appear to GOAL as entities. So you can not communicate
 * with them from GOAL by using the GOAL send action.
 * 
 * @author trens
 * @modified W.Pasman 8feb2012 this object is an EIS environment and therefore
 *           can not be a singleton. This has considerable implications for the
 *           design.
 */
public class RemoteEnvironment implements EnvironmentInterfaceStandard {

    private final static Logger LOGGER = Logger
            .getLogger(RemoteEnvironment.class);

    private RemoteEnvironmentData data = new RemoteEnvironmentData(null,
            new LinkedList<EnvironmentListener>(),
            new HashMap<String, BW4TClientGUI>(), false,
            new ArrayList<String>(),
            new ConcurrentHashMap<String, HashSet<AgentListener>>());

    public RemoteEnvironmentData getData() {
        return data;
    }

    /**
     * Get the initial parameters for this environment
     * 
     * @return the initial parameters
     */
    public Map<String, Parameter> getInitParameters() {
        return data.getInitParameters();
    }

    /**
     * Notifies all listeners about an entity that is free.
     * 
     * @param entity
     *            is the free entity.
     * @param agents
     *            is the list of agents that were associated
     */
    public void notifyFreeEntity(String entity, Collection<String> agents) {
        for (EnvironmentListener listener : data.getEnvironmentListeners()) {
            listener.handleFreeEntity(entity, agents);
        }
    }

    /**
     * Notifies all listeners about an entity that has been newly created.
     * 
     * @param entity
     *            is the new entity.
     */
    public void notifyNewEntity(String entity) {
        for (EnvironmentListener listener : data.getEnvironmentListeners()) {
            listener.handleNewEntity(entity);
        }
    }

    /**
     * Notifies all listeners about an entity that has been deleted.
     * 
     * @param entity
     *            is the deleted entity.
     */
    public void notifyDeletedEntity(String entity, Collection<String> agents) {
        LOGGER.debug("Notifying all listeners about an entity that has been deleted.");
        if (data.getEntityToGUI().get(entity) != null) {
            data.getEntityToGUI().get(entity).getBW4TClientInfo().jFrame
                    .dispose();
        }
        for (EnvironmentListener listener : data.getEnvironmentListeners()) {
            listener.handleDeletedEntity(entity, agents);
        }
    }

    /**
     * Method required for GOAL to work
     */
    @Override
    public String getType(String entity) throws EntityException {
        try {
            return data.getClient().getType(entity);
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    /**
     * Register an agent to the environment, is passed towards the server
     * 
     * @param agentId
     *            , the agent that should be registered
     */
    @Override
    public void registerAgent(String agentId) throws AgentException {
        try {
            data.getClient().registerAgent(agentId);
            data.getLocalAgents().add(agentId);
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    @Override
    public List<String> getAgents() {
        try {
            return (LinkedList<String>) data.getClient().getAgents();
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    @Override
    public Set<String> getAssociatedEntities(String agent)
            throws AgentException {
        try {
            return (HashSet<String>) data.getClient().getAssociatedEntities(
                    agent);
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    /**
     * We detected that environment suddenly died. Notify our listeners and
     * return {@link NoEnvironmentException} reporting the problem.
     * 
     * @param e
     *            is the exception from which we detected the death.
     */
    private NoEnvironmentException environmentSuddenDeath(Exception e) {
        this.LOGGER.error("The BW4T Server disconnected unexpectedly.");
        handleStateChange(EnvironmentState.KILLED);
        if (e instanceof NoEnvironmentException) {
            return (NoEnvironmentException) e;
        }
        return new NoEnvironmentException("Unable to access environment.", e);
    }

    /**
     * Perform an entity action, is passed towards the server
     * 
     * @param entity
     *            , the entity that should perform the action
     * @param action
     *            , the action that should be performed
     * @return the percept resulting from the action, null if an error occurred.
     * @throws ActException
     */
    public Percept performEntityAction(String entity, Action action)
            throws RemoteException, ActException {
        if (data.isConnectedToGoal() && "sendToGUI".equals(action.getName())) {
            if (data.getEntityToGUI().get(entity) == null) {
                ActException e = new ActException("sendToGUI failed:" + entity
                        + " is not connected to a GUI.");
                e.setType(ActException.FAILURE);
                throw e;
            }
            return data.getEntityToGUI().get(entity)
                    .sendToGUI(action.getParameters());
        } else {
            return data.getClient().performEntityAction(entity, action);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void associateEntity(String agentId, String entityId)
            throws RelationException {
        try {
            if (data.isConnectedToGoal() && "human".equals(getType(entityId))) {
                data.getClient().associateEntity(agentId, entityId);
                BW4TClientGUI renderer = new BW4TClientGUI(this, entityId,
                        true, true);
                data.getEntityToGUI().put(entityId, renderer);
            } else if (data.isConnectedToGoal()
                    && "true".equals(((Identifier) data.getInitParameters()
                            .get(InitParam.LAUNCHGUI.nameLower())).getValue())) {
                data.getClient().associateEntity(agentId, entityId);
                BW4TClientGUI renderer = new BW4TClientGUI(this, entityId,
                        true, false);
                data.getEntityToGUI().put(entityId, renderer);
            } else if ("bot".equals(getType(entityId))
                    && "true".equals(((Identifier) data.getInitParameters()
                            .get(InitParam.LAUNCHGUI.nameLower())).getValue())) {
                data.getClient().associateEntity(agentId, entityId);
                BW4TClientGUI renderer = new BW4TClientGUI(this, entityId,
                        false, false);
                data.getEntityToGUI().put(entityId, renderer);
            } else {
                data.getClient().associateEntity(agentId, entityId);
                data.getEntityToGUI().put(entityId, null);
            }
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        } catch (Exception e) {
            throw new RelationException("failed to associate entity", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(Map<String, Parameter> parameters)
            throws ManagementException {
        this.data.setInitParameters(parameters);
        Parameter goal = data.getInitParameters().get(
                InitParam.GOAL.nameLower());
        data.setConnectedToGoal(Boolean.parseBoolean(((Identifier) goal)
                .getValue()));
        try {
            LOGGER.info("Connecting to BW4T Server.");
            data.setClient(new BW4TClient(this));
            data.getClient().connectServer(data.getInitParameters());
            Map<String, Parameter> serverparams = extractServerParameters(parameters);
            if (!(serverparams.isEmpty())) {
                data.getClient().initServer(parameters);
            }
            data.getClient().register(data.getInitParameters());
        } catch (RemoteException e) {
            LOGGER.error("Unable to access the remote environment.");
        } catch (MalformedURLException e) {
            LOGGER.error("The URL provided to connect to the remote environment is invalid..");
        } catch (NotBoundException e) {
            LOGGER.error("Unable to bind to the remote environment.");
        }
    }

    /**
     * Filter out all the parameters for the server from the given set of
     * parameters. We do this by removing all parameters that we can handle as
     * client; the remaining ones must be server parameters.
     * 
     * @param parameters
     *            a set of parameters for both client and server
     * @return parameters for the server
     */
    private Map<String, Parameter> extractServerParameters(
            Map<String, Parameter> parameters) {
        Map<String, Parameter> serverparams = new HashMap<String, Parameter>(
                parameters);
        for (InitParam param : InitParam.values()) {
            serverparams.remove(param.nameLower());
        }
        return serverparams;
    }

    /**
     * Set the initialization parameters
     * 
     * @param parameters
     *            , the new initialization parameters
     */
    public void setInitParameters(Map<String, Parameter> parameters) {
        this.data.setInitParameters(parameters);
    }

    /**
     * Check whether an action is supported by this environment.
     * 
     * @return the result
     */
    protected boolean isSupportedByEnvironment(Action arg0) throws ActException {
        try {
            return data.getClient().isSupportedByEnvironment(arg0);
        } catch (RemoteException e) {
            throw new ActException(ActException.FAILURE,
                    "failed to reach remote env", e);
        }
    }

    /**
     * Check whether an action is supported by a type, for now always returns
     * false as it should not be used
     * 
     * @return the result
     */
    protected boolean isSupportedByType(Action arg0, String arg1) {
        return true;
    }

    /**
     * Get the required version of EIS
     * 
     * @return the required version of EIS
     */
    @Override
    public String requiredVersion() {
        return "0.3";
    }

    /**
     * Gets all percepts for a certain agent for a specified list of entities
     * 
     * @param agent
     *            , the agent's id
     * @param entities
     *            , the list of entities
     * @throws PerceiveException
     *             , if an attempt to perform an action or to retrieve percepts
     *             has failed.
     * @throws NoEnvironmentException
     *             , if an attempt to perform an action or to retrieve percepts
     *             has failed.
     */
    @Override
    public Map<String, Collection<Percept>> getAllPercepts(String agent,
            String... entities) throws PerceiveException,
            NoEnvironmentException {

        // fail if the environment does not run
        EnvironmentState state = getState();
        if (state == EnvironmentState.KILLED) {
            throw new NoEnvironmentException("Environment is dead.");
        }
        if (state != EnvironmentState.RUNNING) {
            throw new PerceiveException("Environment does not run");
        }
        // fail if the agent is not registered
        if (!getAgents().contains(agent)) {
            throw new PerceiveException("Agent \"" + agent
                    + "\" is not registered.");
        }

        // get the associated entities
        Set<String> associatedEntities;
        try {
            associatedEntities = (HashSet<String>) getAssociatedEntities(agent);
        } catch (AgentException e) {
            throw new PerceiveException(
                    "can't get associated entities of agent " + agent, e);
        }

        // fail if there are no associated entities
        if (associatedEntities == null || associatedEntities.isEmpty()) {
            throw new PerceiveException("Agent \"" + agent
                    + "\" has no associated entities.");
        }

        // return value
        Map<String, Collection<Percept>> ret = new HashMap();

        // gather all percepts
        if (entities.length == 0) {

            for (String entity : associatedEntities) {

                // get all percepts
                List<Percept> all = getAllPerceptsFromEntity(entity);

                // add annonation
                for (Percept p : all) {
                    p.setSource(entity);
                }

                // done
                ret.put(entity, all);

            }

        } else {

            for (String entity : entities) {

                if (!associatedEntities.contains(entity)) {
                    throw new PerceiveException("Entity \"" + entity
                            + "\" has not been associated with the agent \""
                            + agent + "\".");
                }

                // get all percepts
                List<Percept> all = getAllPerceptsFromEntity(entity);

                // add annonation
                for (Percept p : all) {
                    p.setSource(entity);
                }

                // done
                ret.put(entity, all);
            }
        }
        return ret;
    }

    /**
     * Get all percepts for a certain entity, is passed through the server
     * 
     * @param entity
     *            , the entity for which all percepts should be gotten
     * @return the list of received percepts, null if an exception occurred
     * @throws PerceiveException
     *             , NoEnvironmentException if the attempt to perform an action
     *             or to retrieve percepts has failed.
     */
    public List<Percept> getAllPerceptsFromEntity(String entity)
            throws PerceiveException {

        try {
            boolean launchGui = "true".equals(((Identifier) data
                    .getInitParameters().get("launchgui")).getValue());
            if (data.isConnectedToGoal() && !"gui".contains(entity)
                    && "human".equals(getType(entity))) {
                return (LinkedList<Percept>) data.getEntityToGUI().get(entity)
                        .getToBePerformedAction();
            } else if (entity.contains("gui")
                    && "human".equals(getType(entity.replace("gui", "")))) {
                return (LinkedList<Percept>) data.getClient()
                        .getAllPerceptsFromEntity(entity.replace("gui", ""));
            } else if (launchGui) {
                if (data.getEntityToGUI().get(entity) == null) {
                    return null;
                }
                List<Percept> percepts = (LinkedList<Percept>) data.getClient()
                        .getAllPerceptsFromEntity(entity);
                BW4TClientGUI tempEntity;
                if (percepts != null) {
                    tempEntity = data.getEntityToGUI().get(entity);
                    ProcessingOperations.processPercepts(percepts,
                            tempEntity.getBW4TClientInfo());
                }
                return percepts;
            } else {
                return (LinkedList<Percept>) data.getClient()
                        .getAllPerceptsFromEntity(entity);
            }
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        } catch (EntityException e) {
            throw new PerceiveException("getAllPerceptsFromEntity failed for "
                    + entity, e);
        }
    }

    /**
     * Check if an action is supported by an entity, is not used so returns true
     * 
     * @param action
     *            , the action
     * @param entity
     *            , the entity
     */
    protected boolean isSupportedByEntity(Action action, String entity) {
        return true;
    }

    /**
     * Check if a certain state transition is valid, always returns true for
     * now.
     * 
     * @param oldState
     *            , the old state of the environment
     * @param newState
     *            , the new state of the environment
     */
    @Override
    public boolean isStateTransitionValid(EnvironmentState oldState,
            EnvironmentState newState) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() throws ManagementException {
        data.getClient().start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() throws ManagementException {
        data.getClient().pause();
    }

    /**
     * {@inheritDoc.
     */
    @Override
    public void kill() throws ManagementException {

        for (BW4TClientGUI renderer : data.getEntityToGUI().values()) {
            if (renderer != null) {
                renderer.getBW4TClientInfo().stop = true;
            }
        }
        // copy list, the localAgents list is going to be changes by removing
        // agents.
        List<String> allAgents = new ArrayList<String>(data.getLocalAgents());
        for (String agentname : allAgents) {
            try {
                unregisterAgent(agentname);
                freeEntity(agentname);
                freeAgent(agentname);
            } catch (AgentException | RelationException | EntityException e) {
                throw new ManagementException(
                        "kill failed because agent could not be freed", e);
            }
        }
        try {
            data.getClient().kill();
            data.setClient(null);
        } catch (Exception e) {
            throw new ManagementException("problem while killing client", e);
        }
    }

    /*
     * Listener functionality. Attaching, detaching, notifying listeners.
     */

    /**
     * {@inheritDoc}
     */
    public void attachEnvironmentListener(EnvironmentListener listener) {

        if (!data.getEnvironmentListeners().contains(listener)) {
            data.getEnvironmentListeners().add(listener);
        }

    }

    /**
     * {@inheritDoc}
     */
    public void detachEnvironmentListener(EnvironmentListener listener) {

        if (data.getEnvironmentListeners().contains(listener)) {
            data.getEnvironmentListeners().remove(listener);
        }

    }

    /**
     * {@inheritDoc}
     */
    public void attachAgentListener(String agent, AgentListener listener) {

        if (!data.getLocalAgents().contains(agent)) {
            return;
        }

        Set<AgentListener> listeners = data.getAgentsToAgentListeners().get(
                agent);

        if (listeners == null) {
            listeners = new HashSet<AgentListener>();
        }

        listeners.add(listener);

        data.getAgentsToAgentListeners().put(agent,
                (HashSet<AgentListener>) listeners);

    }

    /**
     * {@inheritDoc}
     */
    public void detachAgentListener(String agent, AgentListener listener) {
        if (!data.getLocalAgents().contains(agent)) {
            return;
        }
        Set<AgentListener> listeners = data.getAgentsToAgentListeners().get(
                agent);
        if (listeners == null || !listeners.contains(agent)) {
            return;
        }
        listeners.remove(listener);
    }

    /**
     * Used to unregister an agent on the server side
     * 
     * @param agent
     *            , the agent to unregister
     * @throws AgentException
     *             if the attempt failed
     */
    @Override
    public void unregisterAgent(String agent) throws AgentException {
        try {
            LOGGER.debug("Unregistering agent: " + agent);
            data.getLocalAgents().remove(agent);
            data.getClient().unregisterAgent(agent);
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    /**
     * Gets all the entities that exist on the server
     * 
     * @return the list of entity names
     */
    @Override
    public Collection<String> getEntities() {
        try {
            return data.getClient().getEntities();
        } catch (RemoteException e) {
            throw new NoEnvironmentException("can't access environment", e);
        }
    }

    /**
     * Used to free an entity
     * 
     * @param entity
     *            , the entity to free
     * @throws RelationException
     *             , if an attempt to manipulate the agents-entities-relation
     *             has failed.
     * @throws EntityException
     *             , if something unexpected happens when attempting to add or
     *             remove an entity.
     */
    @Override
    public void freeEntity(String entity) throws RelationException,
            EntityException {
        try {
            data.getClient().freeEntity(entity);
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    /**
     * Used to free an agent
     * 
     * @param agent
     *            , the agent to free
     * @throws RelationException
     *             , if an attempt to manipulate the agents-entities-relation
     *             has failed.
     */
    @Override
    public void freeAgent(String agent) throws RelationException {
        try {
            data.getClient().freeAgent(agent);
            // agent is just freed, not removed. Keep it in #localAgents.
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    /**
     * Used to free an agent-entity pair.
     * 
     * @param agent
     *            , the agent
     * @param entity
     *            , the entity
     * @throws RelationException
     *             , if an attempt to manipulate the agents-entities-relation
     *             has failed.
     */
    @Override
    public void freePair(String agent, String entity) throws RelationException {
        try {
            data.getClient().freePair(agent, entity);
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    /**
     * Gets all associated agents for a certain entity
     * 
     * @param entity
     *            , the entity
     * @return a list of agents
     * @throws EntityException
     *             , if something unexpected happens when attempting to add or
     *             remove an entity.
     */
    @Override
    public Collection<String> getAssociatedAgents(String entity)
            throws EntityException {
        try {
            return data.getClient().getAssociatedAgents(entity);
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    /**
     * Gets all free entities
     * 
     * @return a list of free entities
     */
    @Override
    public Collection<String> getFreeEntities() {
        try {
            return data.getClient().getFreeEntities();
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    /**
     * Used to let one or more entities perform an action
     * 
     * @param agent
     *            , the agent that should be connected to the entity
     * @param action
     *            , the action that should be performed
     * @param entities
     *            , the entities that should perform the action
     * @return a map of entityname->percept that was the result of the action
     * @throws ActException
     *             , if an attempt to perform an action has failed.
     */
    @Override
    public Map<String, Percept> performAction(String agent, Action action,
            String... entities) throws ActException {
        try {
            return performAction1(agent, action, entities);
        } catch (ActException e) {
            throw e;
        } catch (Exception e) {
            ActException e1 = new ActException("failed to perform action",
                    ActException.FAILURE);
            e1.initCause(e);
            throw e1;
        }
    }

    /**
     * Internal version that can fully throw exceptions
     * 
     * @param agent
     * @param action
     * @param entities
     * @return
     * @throws ActException
     * @throws AgentException
     */
    private Map<String, Percept> performAction1(String agent, Action action,
            String... entities) throws ActException, AgentException {
        // FIXME this function is way too long.

        // 1. unregistered agents cannot act
        if (!getAgents().contains(agent)) {
            throw new ActException(ActException.NOTREGISTERED);
        }

        // get the associated entities
        Set<String> associatedEntities;
        associatedEntities = (HashSet<String>) getAssociatedEntities(agent);

        // 2. no associated entity/ies -> trivial reject
        if (associatedEntities == null || associatedEntities.isEmpty()) {
            throw new ActException(ActException.NOENTITIES);
        }

        // entities that should perform the action
        Set<String> targetEntities = null;
        if (entities.length == 0) {

            targetEntities = associatedEntities;

        } else {

            targetEntities = new HashSet<String>();

            for (String entity : entities) {

                // 3. provided wrong entity
                if (!associatedEntities.contains(entity)) {
                    throw new ActException(ActException.WRONGENTITY);
                }

                targetEntities.add(entity);

            }

        }

        // 4. action could be not supported by the environment
        if (!isSupportedByEnvironment(action)) {
            throw new ActException(ActException.NOTSUPPORTEDBYENVIRONMENT);
        }

        // 5. action could be not supported by the type of the entities
        for (String entity : entities) {

            String type;
            try {
                type = getType(entity);
            } catch (EntityException e) {
                throw new ActException("can't get entity type", e);
            }

            if (!isSupportedByType(action, type)) {
                throw new ActException(ActException.NOTSUPPORTEDBYTYPE);
            }

        }

        // 6. action could be not supported by the entities themselves
        for (String entity : entities) {

            String type;
            try {
                type = getType(entity);
            } catch (EntityException e) {
                throw new ActException("can't get entity type", e);
            }

            if (!isSupportedByEntity(action, type)) {
                throw new ActException(ActException.NOTSUPPORTEDBYENTITY);
            }

        }

        Map<String, Percept> ret = new HashMap<String, Percept>();

        // 6. action could be not supported by the entities themselves
        for (String entity : targetEntities) {

            // TODO catch and rethrow exceptions //differentiate between
            // actexceptions and others
            // TODO how is ensured that this method is called? ambiguity?

            try {
                Percept p = this.performEntityAction(entity, action);
                if (p != null) {
                    // workaround for #2270
                    ret.put(entity, p);
                }
            } catch (Exception e) {
                throw new ActException(ActException.FAILURE,
                        "performAction failed:", e);
            }

        }

        return ret;
    }

    /**
     * Used to get the current state of the environment
     * 
     * @return the current state of the environment
     */
    @Override
    public EnvironmentState getState() {
        if (data.getClient() != null) {
            try {
                LOGGER.debug("Getting the environment state: "
                        + data.getClient().getState());
                return data.getClient().getState();
            } catch (RemoteException e) {
                LOGGER.warn(
                        "getState detected non-responsive environment. Assuming it's killed.",
                        e);
            }
        }
        return EnvironmentState.KILLED;
    }

    @Override
    public boolean isInitSupported() {
        return true;
    }

    @Override
    public boolean isStartSupported() {
        return true;
    }

    @Override
    public boolean isPauseSupported() {
        return true;
    }

    @Override
    public boolean isKillSupported() {
        return true;
    }

    /**
     * Get a property from the environment
     * 
     * @param property
     *            , the property to query
     * @return the property
     * @throws QueryException
     *             when the query fails
     */
    @Override
    public String queryProperty(String property) throws QueryException {
        try {
            return data.getClient().queryProperty(property);
        } catch (RemoteException e) {
            throw new NoEnvironmentException("can't access environment", e);
        }
    }

    /**
     * Get a property from a certain entity
     * 
     * @param entity
     *            , the entity
     * @param property
     *            , the property to query
     * @return the property
     * @throws QueryException
     *             when the query fails
     */
    @Override
    public String queryEntityProperty(String entity, String property)
            throws QueryException {
        try {
            return data.getClient().queryEntityProperty(entity, property);
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    /**
     * This is called from the server, via the BW4Tclient.
     * 
     * @param newState
     */
    public void handleStateChange(EnvironmentState newState) {
        for (EnvironmentListener listener : data.getEnvironmentListeners()) {
            listener.handleStateChange(newState);
        }
    }

    /**
     * Resets the environment(-interface) with a set of key-value-pairs. to
     * combine properly with BatchRunner, this reset does not entirely reset the
     * env, it does not disconnect the entities. Note that this is NOT the reset
     * attached to the reset button in the {@link ServerContextDisplay}.
     */
    @Override
    public void reset(Map<String, Parameter> params) throws ManagementException {
        data.getClient().resetServer(params);
    }

}
