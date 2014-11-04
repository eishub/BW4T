package nl.tudelft.bw4t.client.environment;

import java.io.File;
import java.io.FileNotFoundException;
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

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.agent.BW4TAgent;
import nl.tudelft.bw4t.client.agent.HumanAgent;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.startup.InitParam;
import nl.tudelft.bw4t.eis.MapParameter;
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
 * <p>
 * This RemoteEnvironment functions multiple roles
 * <ul>
 * <li>as stand-alone client. It just requests the entities as specified in the
 * launch parameters and launches one HumanGUI connecting with the server that
 * connects with this entity. The new-entity calls are just used to check if a
 * humanGUI needs to be connected.
 * <li>as plugin for GOAL. New-entity calls from the server are now also
 * forwarded to GOAL.
 * </ul>
 */
public class RemoteEnvironment implements EnvironmentInterfaceStandard,
		EnvironmentListener {
	/**
	 * The log4j Logger which displays logs on console
	 */
	private static final Logger LOGGER = Logger
			.getLogger(RemoteEnvironment.class);
	private BW4TClient client = null;
	private final List<EnvironmentListener> environmentListeners = new LinkedList<EnvironmentListener>();
	private final Map<String, ClientController> entityToGUI = new HashMap<>();
	/**
	 * This is a list of locally registered agents.
	 * 
	 * Only locally registered agents can act and be associated with entities.
	 */
	private final List<String> localAgents = new LinkedList<String>();
	/**
	 * Stores for each agent (represented by a string) a set of listeners.
	 */
	private final Map<String, HashSet<AgentListener>> agentsToAgentListeners = new HashMap<String, HashSet<AgentListener>>();

	/**
	 * List of all active agents.
	 */
	private final Map<String, BW4TAgent> runningAgents = new HashMap<>();

	private final Map<String, List<Percept>> storedPercepts = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(Map<String, Parameter> parameters)
			throws ManagementException {
		InitParam.setParameters(parameters);
		try {
			LOGGER.info("Connecting to BW4T Server.");
			client = new BW4TClient(this);
			if (!InitParam.KILL.getValue().isEmpty()) {
				getClient().shutdownServer();
				System.exit(0);
			}
			getClient().connectServer();

			sendServerParams();

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
	 * Send the extra parameters to the server.
	 * 
	 * @throws ManagementException
	 *             if we fail to load a local map or there is an error in the
	 *             communication with the server.
	 */
	private void sendServerParams() throws ManagementException {
		Map<String, Parameter> serverparams = InitParam.getServerParameters();
		final boolean mapPresent = !InitParam.MAP.getValue().isEmpty();
		if (!serverparams.isEmpty() || mapPresent) {
			LOGGER.info(String.format("Sending extra parameters to server: %s",
					serverparams));
			if (mapPresent) {
				File mapfile = new File(InitParam.MAP.getValue());
				if (mapfile.exists() && !mapfile.isDirectory()) {
					try {
						serverparams.put(InitParam.MAP.nameLower(),
								new MapParameter(mapfile));
					} catch (FileNotFoundException | JAXBException e) {
						throw new ManagementException(
								"Could not load local Map to send to the server.",
								e);
					}
				} else {
					serverparams.put(InitParam.MAP.nameLower(), new Identifier(
							mapfile.getName()));
				}
			}
			getClient().initServer(serverparams);
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

	/**
	 * We detected that environment suddenly died. Notify our listeners and
	 * return {@link NoEnvironmentException} reporting the problem.
	 * 
	 * @param e
	 *            is the exception from which we detected the death.
	 * @return {@link NoEnvironmentException}
	 */
	public NoEnvironmentException environmentSuddenDeath(Exception e) {
		LOGGER.error("The BW4T Server disconnected unexpectedly.");
		handleStateChange(EnvironmentState.KILLED);
		if (e instanceof NoEnvironmentException) {
			return (NoEnvironmentException) e;
		}
		return new NoEnvironmentException("Unable to access environment.", e);
	}

	/*
	 * Listener functionality. Attaching, detaching, notifying listeners.
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void attachEnvironmentListener(EnvironmentListener listener) {
		if (!getEnvironmentListeners().contains(listener) || listener == this) {
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
	 * Method required for GOAL to work
	 * 
	 * @param entity
	 *            the name of the entity to check
	 * @return the type of entity
	 * @throws EntityException
	 *             thrown if an error occured while getting the type
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
	 *            the agent that should be registered
	 * @throws AgentException
	 *             failed to register the agent
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
			removeRunningAgent(agent);
			getClient().unregisterAgent(agent);

			if (localAgents.isEmpty() && !isConnectedToGoal()) {
				LOGGER.info("Last local agent was removed. Closing the client.");
				try {
					kill();
				} catch (ManagementException e) {
					LOGGER.error("failed to stop the RemoteEnvironment", e);
				}
			}
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

	@Override
	public List<String> getAgents() {
		try {
			return getClient().getAgents();
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
			return getClient().getAssociatedAgents(entity);
		} catch (RemoteException e) {
			throw environmentSuddenDeath(e);
		}
	}

	@Override
	public Set<String> getAssociatedEntities(String agent)
			throws AgentException {
		try {
			return getClient().getAssociatedEntities(agent);
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
	 * {@inheritDoc}
	 */
	@Override
	public void associateEntity(String agentId, String entityId)
			throws RelationException {
		LOGGER.debug("Associating Agent " + agentId + " with Entity "
				+ entityId + ".");
		try {
			getClient().associateEntity(agentId, entityId);
			startEntityGUI(agentId, entityId);
		} catch (RemoteException e) {
			throw environmentSuddenDeath(e);
		} catch (Exception e) {
			throw new RelationException("failed to associate entity", e);
		}
	}

	/**
	 * Startup the GUI by instantiating a {@link ClientController}.
	 * 
	 * @param agentId
	 *            the agent to attach the controller to
	 * @param entityId
	 *            the entity displayed by the controller
	 * @throws EntityException
	 *             if we fail to create the controller or agent
	 * @throws AgentException
	 *             if we fail to register the new human agent
	 * @throws RelationException
	 *             if we fail to associate the new human agent
	 */
	private void startEntityGUI(String agentId, String entityId)
			throws EntityException, AgentException, RelationException {
		if (hasEntityGUI(entityId)) {
			ClientController control = null;
			if ("human".equals(getType(entityId))) {
				HumanAgent agent = (HumanAgent) getRunningAgent(agentId);
				if (agent == null) {
					agent = new HumanAgent("Human" + getAgents().size(), this);
					agent.registerEntity(entityId);
					addRunningAgent(agent);
					associateEntity(agent.getAgentId(), entityId);
					agent.start();
					return;
				}
				control = new ClientController(this, entityId, agent);
			} else {
				control = new ClientController(this, entityId);
			}
			control.startupGUI();
			putEntityController(entityId, control);
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
			getClient().freeEntity(entity);
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
			removeEntityController(entity);
			getClient().freePair(agent, entity);
		} catch (RemoteException e) {
			throw environmentSuddenDeath(e);
		}
	}

	/**
	 * Check whether the given entity has a gui attached or will have one
	 * attached.
	 * 
	 * @param entity
	 *            the entity to check
	 * @return true iff the entity can have a gui
	 */
	public boolean hasEntityGUI(String entity) {
		if (entityToGUI.containsKey(entity)
				|| storedPercepts.containsKey(entity)) {
			return true;
		}
		try {
			String type = getType(entity);
			if ("human".equals(type)) {
				return true;
			} else if ("bot".equals(type)) {
				return !isConnectedToGoal()
						|| InitParam.LAUNCHGUI.getBoolValue();
			}
		} catch (EntityException e) {
			LOGGER.error(String.format(
					"Could not retrieve the type of entity %s!", entity), e);
		}
		return false;
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
	 * @throws RemoteException
	 */
	public Percept performEntityAction(String entity, Action action)
			throws RemoteException, ActException {
		if (isConnectedToGoal() && "sendToGUI".equals(action.getName())) {
			final ClientController entityGUI = getEntityController(entity);
			if (entityGUI == null) {
				ActException e = new ActException("sendToGUI failed:" + entity
						+ " is not connected to a GUI.");
				e.setType(ActException.FAILURE);
				throw e;
			}
			return entityGUI.sendToGUI(action.getParameters());
		} else {
			return getClient().performEntityAction(entity, action);
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
	 * @param arg0
	 *            The action to be checked
	 * @return true if the Action is supported by the environment
	 * @throws ActException
	 */
	public boolean isSupportedByEnvironment(Action arg0) throws ActException {
		try {
			return getClient().isSupportedByEnvironment(arg0);
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
		return "0.4";
	}

	/**
	 * Gets all percepts for a certain agent for a specified list of entities
	 * 
	 * @param agent
	 *            the agent's id
	 * @param entities
	 *            the list of entities
	 * @return a list of Percepts for every entity
	 * @throws PerceiveException
	 *             if an attempt to perform an action or to retrieve percepts
	 *             has failed.
	 * @throws NoEnvironmentException
	 *             if an attempt to perform an action or to retrieve percepts
	 *             has failed.
	 */
	@Override
	public Map<String, Collection<Percept>> getAllPercepts(String agent,
			String... entities) throws PerceiveException,
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
			throw new PerceiveException("Agent \"" + agent
					+ "\" is not registered.");
		}
		/** get the associated entities */
		Set<String> associatedEntities;
		try {
			associatedEntities = getAssociatedEntities(agent);
		} catch (AgentException e) {
			throw new PerceiveException(
					"can't get associated entities of agent " + agent, e);
		}
		// fail if there are no associated entities */
		if ((associatedEntities == null) || associatedEntities.isEmpty()) {
			throw new PerceiveException("Agent \"" + agent
					+ "\" has no associated entities.");
		}
		final Map<String, Collection<Percept>> percepts = gatherPercepts(agent,
				associatedEntities, entities);
		try {
			// allow other threads to be processed
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// Ignore being interupted
		}
		return percepts;
	}

	/**
	 * Gets all percepts for a certain agent for a specified list of entities
	 * 
	 * @param agent
	 *            the agent's name
	 * @param associatedEntities
	 *            the set of attached entities
	 * @param entities
	 *            the entities we want percepts for
	 * @return Returns a map with all percepts
	 * @throws PerceiveException
	 *             unable to get percepts because the entity does not belong to
	 *             this agent
	 */
	Map<String, Collection<Percept>> gatherPercepts(String agent,
			Set<String> associatedEntities, String... entities)
			throws PerceiveException {
		Map<String, Collection<Percept>> perceptsMap = new HashMap<String, Collection<Percept>>();
		if (entities.length == 0) {
			// No entities selected, get percepts for all associated entities
			entities = associatedEntities.toArray(new String[associatedEntities
					.size()]);
		}
		for (String entity : entities) {
			if (!associatedEntities.contains(entity)) {
				throw new PerceiveException("Entity \"" + entity
						+ "\" has not been associated with the agent \""
						+ agent + "\".");
			}
			perceptsMap.put(entity, gatherPercepts(entity));
		}
		return perceptsMap;
	}

	/**
	 * Gather all the new percepts for the given entity from the environment
	 * 
	 * @param name
	 *            name of the entity
	 * @return the percepts for the given entity
	 * @throws PerceiveException
	 *             if we failed to get the entity
	 */
	public List<Percept> gatherPercepts(String name) throws PerceiveException {
		List<Percept> all = PerceptsHandler
				.getAllPerceptsFromEntity(name, this);
		for (Percept p : all) {
			p.setSource(name);
		}
		saveAndSendPercepts(name, all);
		return all;
	}

	/**
	 * Tries to send the percepts to the given entity, if it is not yet present
	 * we will store the percepts in a map.
	 * 
	 * @param entity
	 *            the entity name
	 * @param percepts
	 *            the percepts for the entity
	 */
	protected void saveAndSendPercepts(String entity,
			Collection<Percept> percepts) {
		if (!hasEntityGUI(entity)) {
			return;
		}
		ClientController cc = this.getEntityController(entity);
		if (cc != null) {
			if (isConnectedToGoal() && storedPercepts.containsKey(entity)) {
				cc.handlePercepts(storedPercepts.get(entity));
				storedPercepts.remove(entity);
			}
			cc.handlePercepts(percepts);
		} else {
			storePercepts(entity, percepts);
		}
	}

	/**
	 * Store the current concatenated with the given percepts to the list of
	 * stored percepts
	 * 
	 * @param entity
	 *            the entity to whom the percepts belong
	 * @param percepts
	 *            the percepts to be used
	 */
	public void storePercepts(String entity, Collection<Percept> percepts) {
		List<Percept> tpercepts = new ArrayList<Percept>();
		if (storedPercepts.containsKey(entity)) {
			tpercepts.addAll(storedPercepts.get(entity));
		}
		tpercepts.addAll(percepts);
		storedPercepts.put(entity, tpercepts);
	}

	/**
	 * Check if an action is supported by an entity, is not used so returns true
	 * 
	 * @param action
	 *            the action
	 * @param entity
	 *            the entity
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
	public boolean isStateTransitionValid(EnvironmentState oldState,
			EnvironmentState newState) {
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
	 * {@inheritDoc}
	 */
	@Override
	public void kill() throws ManagementException {
		disposeAllAgents();
		try {
			getClient().kill();
			client = null;
		} catch (Exception e) {
			throw new ManagementException("problem while killing client", e);
		}
	}

	/**
	 * Remove all agents that are local to this RemoteEnvironment.
	 * 
	 * @throws ManagementException
	 */
	private void disposeAllAgents() throws ManagementException {
		// copy list, the localAgents list is going to be changed by removing
		// agents.

		List<String> allAgents = new ArrayList<String>(localAgents);
		for (String agentname : allAgents) {
			try {
				for (String entity : getAssociatedEntities(agentname)) {
					freePair(agentname, entity);
				}
				unregisterAgent(agentname);

			} catch (AgentException | RelationException e) {
				throw new ManagementException(
						"kill failed because agent could not be freed", e);
			}
		}
	}

	/**
	 * Used to kill a human entity. When a GUI is closed, the other GUI's will
	 * remain open and the environment will not be killed.
	 * 
	 * @param entity
	 *            The entity to free
	 * @throws ManagementException
	 */
	public void killHumanEntity(String entity) throws ManagementException {

		try {
			for (String agent : getAssociatedAgents(entity)) {
				if (agent.isEmpty()) {
					freeEntity(entity);
				} else {
					freePair(agent, entity);
					unregisterAgent(agent);
				}
			}
		} catch (AgentException | RelationException | EntityException e) {
			throw new ManagementException(
					"kill failed because agent could not be freed", e);
		}

	}

	public void addRunningAgent(BW4TAgent agent) throws AgentException {
		registerAgent(agent.getAgentId());
		runningAgents.put(agent.getAgentId(), agent);
	}

	/**
	 * Removes the given agent object from the running agents list. If this was
	 * the last running agent, and we are not connected with GOAL, then we close
	 * this client
	 * 
	 * @param agent
	 *            the name of the agent to remove
	 */
	public void removeRunningAgent(String agent) {
		try {
			for (String entity : getAssociatedEntities(agent)) {
				removeEntityController(entity);
			}
		} catch (AgentException e) {
			LOGGER.warn("Unable to get associated entities.", e);
		}
		runningAgents.remove(agent);

	}

	public void removeRunningAgent(BW4TAgent agent) {
		removeRunningAgent(agent.getAgentId());
	}

	public BW4TAgent getRunningAgent(String name) {
		return runningAgents.get(name);
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
	public Map<String, Percept> performAction(String agent, Action action,
			String... entities) throws ActException {
		try {
			return ActionHandler.performActionDelegated(agent, action, this,
					entities);
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
	 * Used to get the current state of the environment
	 * 
	 * @return the current state of the environment
	 */
	@Override
	public EnvironmentState getState() {
		if (getClient() != null) {
			try {
				final EnvironmentState state = getClient().getState();
				LOGGER.debug("Getting the environment state: " + state);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// ignore interruptions
				}
				return state;
			} catch (RemoteException e) {
				LOGGER.warn(
						"getState detected non-responsive environment. Assuming it's killed.",
						e);
			}
		}
		return EnvironmentState.KILLED;
	}

	public NewMap getMap() {
		return getClient().getMap();
	}

	public BW4TClient getClient() {
		return client;
	}

	public boolean isConnectedToGoal() {
		return InitParam.GOAL.getBoolValue();
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
	public String queryEntityProperty(String entity, String property)
			throws QueryException {
		try {
			return getClient().queryEntityProperty(entity, property);
		} catch (RemoteException e) {
			throw environmentSuddenDeath(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleNewEntity(String entity) {
		EntityNotifiers.notifyNewEntity(entity, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleFreeEntity(String entity, Collection<String> agents) {
		EntityNotifiers.notifyFreeEntity(entity, agents, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleDeletedEntity(String entity, Collection<String> agents) {
		EntityNotifiers.notifyDeletedEntity(entity, agents, this);
	}

	/**
	 * This is called from the server, via the BW4T Client.
	 * 
	 * @param newState
	 */
	@Override
	public void handleStateChange(EnvironmentState newState) {
		for (EnvironmentListener listener : getEnvironmentListeners()) {
			listener.handleStateChange(newState);
		}
	}

	/**
	 * Get the gui controller associated with the given entity.
	 * 
	 * @param entity
	 *            the name of the entity
	 * @return the gui controller
	 */
	public ClientController getEntityController(String entity) {
		return entityToGUI.get(entity);
	}

	/**
	 * Add or remove a gui controller to/from the system.
	 * 
	 * @param entity
	 *            the name of the entity
	 * @param control
	 *            the gui controller
	 */
	public void putEntityController(String entity, ClientController control) {
		if (control == null) {
			entityToGUI.remove(entity);
		} else {
			entityToGUI.put(entity, control);
		}
	}

	/**
	 * Stops any associated gui from this client connected to the given entity.
	 * 
	 * @param entity
	 *            the name of the entity to be disconnected
	 */
	public void removeEntityController(String entity) {
		ClientController control = getEntityController(entity);
		if (control != null) {
			control.stop();
			putEntityController(entity, null);
			try {
				// Take some time to let the thread stop itself
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// ignore interruptions
			}
		}
	}

	List<EnvironmentListener> getEnvironmentListeners() {
		return environmentListeners;
	}

	public List<Percept> getStoredPercepts(String selectedEntity) {
		return storedPercepts.get(selectedEntity);
	}
}
