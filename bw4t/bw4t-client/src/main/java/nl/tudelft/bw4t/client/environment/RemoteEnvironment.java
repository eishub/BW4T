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
import java.util.concurrent.ConcurrentHashMap;

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.environment.handlers.ActionHandler;
import nl.tudelft.bw4t.client.environment.handlers.PerceptsHandler;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.startup.InitParam;
import nl.tudelft.bw4t.client.startup.Launcher;

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
    private static final Logger LOGGER = Logger.getLogger(RemoteEnvironment.class);
    private final RemoteEnvironmentData data = new RemoteEnvironmentData(null, new LinkedList<EnvironmentListener>(),
            new HashMap<String, BW4TClientGUI>(), false, new ArrayList<String>(),
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
        LOGGER.debug("Registering new agent:" + agentId + ".");
        try {
            data.getClient().registerAgent(agentId);
            data.getLocalAgents().add(agentId);
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    @Override
    public List<String> getAgents() {
        LOGGER.debug("Getting agents.");
        try {
            return data.getClient().getAgents();
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    @Override
    public Set<String> getAssociatedEntities(String agent) throws AgentException {
        try {
            return data.getClient().getAssociatedEntities(agent);
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
    public NoEnvironmentException environmentSuddenDeath(Exception e) {
        LOGGER.error("The BW4T Server disconnected unexpectedly.");
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
    public Percept performEntityAction(String entity, Action action) throws RemoteException, ActException {
        if (data.isConnectedToGoal() && "sendToGUI".equals(action.getName())) {
            if (data.getEntityToGUI().get(entity) == null) {
                ActException e = new ActException("sendToGUI failed:" + entity + " is not connected to a GUI.");
                e.setType(ActException.FAILURE);
                throw e;
            }
            return data.getEntityToGUI().get(entity).sendToGUI(action.getParameters());
        } else {
            return data.getClient().performEntityAction(entity, action);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void associateEntity(String agentId, String entityId) throws RelationException {
        LOGGER.debug("Associating Agent " + agentId + " with Entity " + entityId + ".");
        try {
            if (data.isConnectedToGoal() && "human".equals(getType(entityId))) {
                data.getClient().associateEntity(agentId, entityId);
                BW4TClientGUI renderer = new BW4TClientGUI(this, entityId, true, true);
                data.getEntityToGUI().put(entityId, renderer);
                LOGGER.debug("Branch 1");
            } else if (data.isConnectedToGoal()
                    && "true".equals(((Identifier) data.getInitParameters().get(InitParam.LAUNCHGUI.nameLower()))
                            .getValue())) {
                data.getClient().associateEntity(agentId, entityId);
                BW4TClientGUI renderer = new BW4TClientGUI(this, entityId, true, false);
                data.getEntityToGUI().put(entityId, renderer);
                LOGGER.debug("Branch 2");
            } else if ("bot".equals(getType(entityId))
                    && "true".equals(((Identifier) data.getInitParameters().get(InitParam.LAUNCHGUI.nameLower()))
                            .getValue())) {
                data.getClient().associateEntity(agentId, entityId);
                BW4TClientGUI renderer = new BW4TClientGUI(this, entityId, false, false);
                data.getEntityToGUI().put(entityId, renderer);
                LOGGER.debug("Branch 3");
            } else {
                data.getClient().associateEntity(agentId, entityId);
                data.getEntityToGUI().put(entityId, null);
                LOGGER.debug("Branch 4");
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
    public void init(Map<String, Parameter> parameters) throws ManagementException {
        this.data.setInitParameters(parameters);
        Parameter goal = data.getInitParameters().get(InitParam.GOAL.nameLower());
        data.setConnectedToGoal(Boolean.parseBoolean(((Identifier) goal).getValue()));
        try {
            LOGGER.info("Connecting to BW4T Server.");
            data.setClient(new BW4TClient(this));
            if (!((Identifier) data.getInitParameters().get(InitParam.KILL.nameLower())).getValue().isEmpty()) {
                data.getClient().shutdownServer(data.getInitParameters());
                System.exit(0);
            }
            data.getClient().connectServer(data.getInitParameters());

            Map<String, Parameter> serverparams = extractServerParameters(parameters);
            if (!(serverparams.isEmpty())) {
                data.getClient().initServer(parameters);
            }
            data.getClient().register(data.getInitParameters());

        } catch (RemoteException e) {
            LOGGER.error("Unable to access the remote environment.");
        } catch (MalformedURLException e) {
            LOGGER.error("The URL provided to connect to the remote environment is invalid.");
        } catch (NotBoundException e) {
            LOGGER.error("Unable to bind to the remote environment.");
        }
    }

    /**
     * Unfortunately goal requires the main class to be implementing the
     * {@link EnvironmentInterfaceStandard}, that is why we had to reroute the
     * main method through here.
     * 
     * @see Launcher#launch(String[])
     * @param args
     *            the commandline arguments
     */
    public static void main(String[] args) {
        Launcher.launch(args);
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
    private Map<String, Parameter> extractServerParameters(Map<String, Parameter> parameters) {
        Map<String, Parameter> serverparams = new HashMap<String, Parameter>(parameters);
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
    public boolean isSupportedByEnvironment(Action arg0) throws ActException {
        try {
            return data.getClient().isSupportedByEnvironment(arg0);
        } catch (RemoteException e) {
            throw new ActException(ActException.FAILURE, "failed to reach remote env", e);
        }
    }

    /**
     * Check whether an action is supported by a type, for now always returns
     * false as it should not be used
     * 
     * @return the result
     */
    public boolean isSupportedByType(Action arg0, String arg1) {
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
    public Map<String, Collection<Percept>> getAllPercepts(String agent, String... entities) throws PerceiveException,
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
            throw new PerceiveException("Agent \"" + agent + "\" is not registered.");
        }
        // get the associated entities
        Set<String> associatedEntities;
        try {
            associatedEntities = getAssociatedEntities(agent);
        } catch (AgentException e) {
            throw new PerceiveException("can't get associated entities of agent " + agent, e);
        }
        // fail if there are no associated entities
        if ((associatedEntities == null) || associatedEntities.isEmpty()) {
            throw new PerceiveException("Agent \"" + agent + "\" has no associated entities.");
        }
        // return value
        Map<String, Collection<Percept>> ret = new HashMap();
        // gather all percepts
        if (entities.length == 0) {
            for (String entity : associatedEntities) {
                // get all percepts
                List<Percept> all = PerceptsHandler.getAllPerceptsFromEntity(entity, this);
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
                    throw new PerceiveException("Entity \"" + entity + "\" has not been associated with the agent \""
                            + agent + "\".");
                }
                // get all percepts
                List<Percept> all = PerceptsHandler.getAllPerceptsFromEntity(entity, this);
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
     * Check if an action is supported by an entity, is not used so returns true
     * 
     * @param action
     *            , the action
     * @param entity
     *            , the entity
     */
    public boolean isSupportedByEntity(Action action, String entity) {
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
    public boolean isStateTransitionValid(EnvironmentState oldState, EnvironmentState newState) {
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
                renderer.setStop(true);
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
                throw new ManagementException("kill failed because agent could not be freed", e);
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
    @Override
    public void attachEnvironmentListener(EnvironmentListener listener) {
        if (!data.getEnvironmentListeners().contains(listener)) {
            data.getEnvironmentListeners().add(listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachEnvironmentListener(EnvironmentListener listener) {
        if (data.getEnvironmentListeners().contains(listener)) {
            data.getEnvironmentListeners().remove(listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attachAgentListener(String agent, AgentListener listener) {
        if (!data.getLocalAgents().contains(agent)) {
            return;
        }
        Set<AgentListener> listeners = data.getAgentsToAgentListeners().get(agent);
        if (listeners == null) {
            listeners = new HashSet<AgentListener>();
        }
        listeners.add(listener);
        data.getAgentsToAgentListeners().put(agent, (HashSet<AgentListener>) listeners);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachAgentListener(String agent, AgentListener listener) {
        if (!data.getLocalAgents().contains(agent)) {
            return;
        }
        Set<AgentListener> listeners = data.getAgentsToAgentListeners().get(agent);
        if ((listeners == null) || !listeners.contains(agent)) {
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
    public void freeEntity(String entity) throws RelationException, EntityException {
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
    public Collection<String> getAssociatedAgents(String entity) throws EntityException {
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
    public Map<String, Percept> performAction(String agent, Action action, String... entities) throws ActException {
        try {
            return ActionHandler.performActionDelegated(agent, action, this, entities);
        } catch (ActException e) {
            throw e;
        } catch (Exception e) {
            ActException e1 = new ActException("failed to perform action", ActException.FAILURE);
            e1.initCause(e);
            throw e1;
        }
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
                LOGGER.debug("Getting the environment state: " + data.getClient().getState());
                return data.getClient().getState();
            } catch (RemoteException e) {
                LOGGER.warn("getState detected non-responsive environment. Assuming it's killed.", e);
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
    public String queryEntityProperty(String entity, String property) throws QueryException {
        try {
            return data.getClient().queryEntityProperty(entity, property);
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    /**
     * This is called from the server, via the BW4T Client.
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
