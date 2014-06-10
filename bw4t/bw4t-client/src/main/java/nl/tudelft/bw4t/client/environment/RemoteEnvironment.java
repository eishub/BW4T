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

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.startup.InitParam;
import nl.tudelft.bw4t.client.startup.Launcher;

import org.apache.log4j.BasicConfigurator;
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
 */
public class RemoteEnvironment implements EnvironmentInterfaceStandard {
    /**
     * The log4j Logger which displays logs on console
     */
    private static final Logger LOGGER = Logger.getLogger(RemoteEnvironment.class.getName());
    private BW4TClient client = null;
    private final List<EnvironmentListener> environmentListeners = new LinkedList<EnvironmentListener>();
    private final Map<String, BW4TClientGUI> entityToGUI = new HashMap<String, BW4TClientGUI>();
    private boolean connectedToGoal = false;
    /**
     * This is a list of locally registered agents.
     * <p/>
     * Only locally registered agents can act and be associated with entities.
     */
    private final List<String> localAgents = new LinkedList<String>();
    /**
     * Stores for each agent (represented by a string) a set of listeners.
     */
    private final Map<String, HashSet<AgentListener>> agentsToAgentListeners = new HashMap<String, HashSet<AgentListener>>();

    /**
     * Method required for GOAL to work
     */
    @Override
    public String getType(String entity) throws EntityException {
        try {
            return getClient().getType(entity);
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
            getClient().registerAgent(agentId);
            localAgents.add(agentId);
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    @Override
    public List<String> getAgents() {
        try {
            return getClient().getAgents();
        } catch (RemoteException e) {
            throw environmentSuddenDeath(e);
        }
    }

    @Override
    public Set<String> getAssociatedEntities(String agent) throws AgentException {
        try {
            return getClient().getAssociatedEntities(agent);
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
        if (isConnectedToGoal() && "sendToGUI".equals(action.getName())) {
            if (getEntityToGUI().get(entity) == null) {
                ActException e = new ActException("sendToGUI failed:" + entity + " is not connected to a GUI.");
                e.setType(ActException.FAILURE);
                throw e;
            }
            return getEntityToGUI().get(entity).sendToGUI(action.getParameters());
        } else {
            return getClient().performEntityAction(entity, action);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void associateEntity(String agentId, String entityId) throws RelationException {
        LOGGER.debug("Associating Agent " + agentId + " with Entity " + entityId + ".");
        try {
            boolean launchGUI = "true".equals(InitParam.LAUNCHGUI.getValue());
            BW4TClientGUI renderer = null;
            getClient().associateEntity(agentId, entityId);
            if (isConnectedToGoal() && "human".equals(getType(entityId))) {
                renderer = new BW4TClientGUI(this, entityId, true, true);
            } else if (isConnectedToGoal() && launchGUI) {
                renderer = new BW4TClientGUI(this, entityId, true, false);
            } else if ("bot".equals(getType(entityId)) && launchGUI) {
                renderer = new BW4TClientGUI(this, entityId, false, false);
            }
            getEntityToGUI().put(entityId, renderer);
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
        InitParam.setParameters(parameters);
        connectedToGoal = Boolean.parseBoolean(InitParam.GOAL.getValue());
        if (connectedToGoal) {
            BasicConfigurator.configure();
        }
        try {
            LOGGER.info("Connecting to BW4T Server.");
            client = new BW4TClient(this);
            if (!InitParam.KILL.getValue().isEmpty()) {
                getClient().shutdownServer();
                System.exit(0);
            }
            getClient().connectServer();

            Map<String, Parameter> serverparams = InitParam.getServerParameters();
            if (!(serverparams.isEmpty())) {
                getClient().initServer(serverparams);
            }
            getClient().register();

        } catch (RemoteException e) {
            LOGGER.error("Unable to access the remote environment.", e);
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
     * Check whether an action is supported by this environment.
     * 
     * @return the result
     */
    public boolean isSupportedByEnvironment(Action arg0) throws ActException {
        try {
            return getClient().isSupportedByEnvironment(arg0);
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
        /** fail if the environment does not run */
        EnvironmentState state = getState();
        if (state == EnvironmentState.KILLED) {
            throw new NoEnvironmentException("Environment is dead.");
        }
        if (state != EnvironmentState.RUNNING) {
            throw new PerceiveException("Environment does not run");
        }
        /** fail if the agent is not registered */
        if (!getAgents().contains(agent)) {
            throw new PerceiveException("Agent \"" + agent + "\" is not registered.");
        }
        /** get the associated entities */
        Set<String> associatedEntities;
        try {
            associatedEntities = getAssociatedEntities(agent);
        } catch (AgentException e) {
            throw new PerceiveException("can't get associated entities of agent " + agent, e);
        }
        // fail if there are no associated entities */
        if ((associatedEntities == null) || associatedEntities.isEmpty()) {
            throw new PerceiveException("Agent \"" + agent + "\" has no associated entities.");
        }
        return gatherPercepts(agent, associatedEntities, entities);
    }

    /**
     * TODO: Function written by a lunatic, please check this.
     * 
     * @param associatedEntities
     * @return
     * @throws PerceiveException
     */
    Map<String, Collection<Percept>> gatherPercepts(String agent, Set<String> associatedEntities, String... entities)
            throws PerceiveException {
        Map<String, Collection<Percept>> perceptsMap = new HashMap<String, Collection<Percept>>();
        if (entities.length == 0) {
            for (String entity : associatedEntities) {
                List<Percept> all = PerceptsHandler.getAllPerceptsFromEntity(entity, this);
                for (Percept p : all) {
                    p.setSource(entity);
                }
                perceptsMap.put(entity, all);
            }
        } else {
            for (String entity : entities) {
                if (!associatedEntities.contains(entity)) {
                    throw new PerceiveException("Entity \"" + entity + "\" has not been associated with the agent \""
                            + agent + "\".");
                }
                List<Percept> all = PerceptsHandler.getAllPerceptsFromEntity(entity, this);
                for (Percept p : all) {
                    p.setSource(entity);
                }
                perceptsMap.put(entity, all);
            }
        }
        return perceptsMap;
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
        getClient().start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() throws ManagementException {
        getClient().pause();
    }

    /**
     * {@inheritDoc.

     */
    @Override
    public void kill() throws ManagementException {
        for (BW4TClientGUI renderer : getEntityToGUI().values()) {
            /*if (renderer != null) {
                //FIXME renderer.setStop(true);
            }*/
            //renderer.getController().getMapController().setRunning(false);
        }
        // copy list, the localAgents list is going to be changes by removing
        // agents.
        List<String> allAgents = new ArrayList<String>(localAgents);
        for (String agentname : allAgents) {
            try {
                //unregisterAgent(agentname);
                for (String entity : getAssociatedEntities(agentname)) {
                    //freePair(agentname, entity);
                    freePair(agentname,entity);
                }
                /*freeEntity(agentname);*/
                //freeAgent(agentname);
                unregisterAgent(agentname);
                
            } catch (AgentException | RelationException e) {
                throw new ManagementException("kill failed because agent could not be freed", e);
            }
        }
        try {
            getClient().kill();
            client = null;
            
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
        if (!getEnvironmentListeners().contains(listener)) {
            getEnvironmentListeners().add(listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachEnvironmentListener(EnvironmentListener listener) {
        if (getEnvironmentListeners().contains(listener)) {
            getEnvironmentListeners().remove(listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attachAgentListener(String agent, AgentListener listener) {
        if (!localAgents.contains(agent)) {
            return;
        }
        Set<AgentListener> listeners = agentsToAgentListeners.get(agent);
        if (listeners == null) {
            listeners = new HashSet<AgentListener>();
        }
        listeners.add(listener);
        agentsToAgentListeners.put(agent, (HashSet<AgentListener>) listeners);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachAgentListener(String agent, AgentListener listener) {
        if (!localAgents.contains(agent)) {
            return;
        }
        Set<AgentListener> listeners = agentsToAgentListeners.get(agent);
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
            localAgents.remove(agent);
            getClient().unregisterAgent(agent);
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
            return getClient().getEntities();
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
            getClient().freeEntity(entity);
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
            getClient().freeAgent(agent);
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
            getClient().freePair(agent, entity);
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
            return getClient().getAssociatedAgents(entity);
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
            return getClient().getFreeEntities();
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
        if (getClient() != null) {
            try {
                LOGGER.debug("Getting the environment state: " + getClient().getState());
                return getClient().getState();
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
            return getClient().queryProperty(property);
        } catch (RemoteException e) {
            LOGGER.error("Failed to read property from server.", e);
            return "";
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
            return getClient().queryEntityProperty(entity, property);
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
        for (EnvironmentListener listener : getEnvironmentListeners()) {
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
        getClient().resetServer(params);
    }

    public BW4TClient getClient() {
        return client;
    }

    public boolean isConnectedToGoal() {
        return connectedToGoal;
    }

    public Map<String, BW4TClientGUI> getEntityToGUI() {
        return entityToGUI;
    }

    public List<EnvironmentListener> getEnvironmentListeners() {
        return environmentListeners;
    }
}
