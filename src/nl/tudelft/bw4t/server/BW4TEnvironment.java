package nl.tudelft.bw4t.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.BW4TBuilder;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.visualizations.ServerContextDisplay;
import repast.simphony.context.Context;
import repast.simphony.scenario.ScenarioLoadException;
import eis.eis2java.environment.AbstractEnvironment;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * The central environment which runs the data model and performs actions
 * received from remote environments through the server. Remote environments
 * also poll percepts from this environment. Remote environments are notified of
 * entity and environment events also using the server.
 * <p>
 * This is a singleton. Needed because we store the map info here.
 * 
 * @author trens
 * @modified W.Pasman #1997 #2236 #2422
 * 
 */
public class BW4TEnvironment extends AbstractEnvironment {

	private static final long serialVersionUID = -279637264069930353L;
	public static final String MAPDIR = "maps/";
	private static BW4TEnvironment instance;

	private static String mapName;
	private BW4TServer server;
	private boolean mapFullyLoaded;
	private Stepper stepper;
	private String serverIP;
	private String serverPort;
	private String scenarioLocation;
	private Context context;
	private static NewMap theMap;
	private ServerContextDisplay contextDisplay;

	/**
	 * Create a new instance of this environment
	 * 
	 * @param scenarioLocation
	 *            , the location of the scenario that should be loaded in Repast
	 * @param mapLocation
	 *            , the location of the map file
	 * @param serverIp
	 *            , the ip address the server should listen on
	 * @param serverPort
	 *            , the port the server should listen on
	 * @throws IOException
	 * @throws ManagementException
	 * @throws ScenarioLoadException
	 * @throws JAXBException
	 */
	private BW4TEnvironment(String scenarioLocation, String mapLocation,
			String serverIP, String serverPort) throws IOException,
			ManagementException, ScenarioLoadException, JAXBException {
		super();
		instance = this;
		this.mapName = mapLocation;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.scenarioLocation = scenarioLocation;
		launchAll();
	}

	/**
	 * Launch server and start repast.
	 * 
	 * @throws IOException
	 * @throws ManagementException
	 * @throws ScenarioLoadException
	 * @throws JAXBException
	 */
	private void launchAll() throws IOException, ManagementException,
			ScenarioLoadException, JAXBException {
		launchServer(serverIP, serverPort);
		setState(EnvironmentState.RUNNING);
		launchRepast();
	}

	/**
	 * Notify listeners that a new entity is available, server handles correct
	 * distribution of entities to listeners
	 * 
	 * @param entity
	 *            , the new entity
	 */
	@Override
	public void notifyNewEntity(String entity) {
		server.notifyNewEntity(entity);
	}

	@Override
	public void notifyDeletedEntity(String entity, Collection<String> agents) {
		server.notifyDeletedEntity(entity, agents);
	}

	@Override
	public void setState(EnvironmentState newstate) throws ManagementException {
		super.setState(newstate);
		server.notifyStateChange(getState());
	}

	/**
	 * takes down all entities and agents. Stops repast. Leaves the server open.
	 * Tries to take down in any case, just reports errors and proceeds.
	 */
	public void removeAllEntities() throws ManagementException {
		BW4TLogger.getInstance().closeLog();
		setState(EnvironmentState.KILLED);

		// Remove all entities.
		for (String entity : this.getEntities()) {
			try {
				this.deleteEntity(entity);
			} catch (Exception e) {
				System.err.println("Failure deleting entity " + entity);
				e.printStackTrace();
			}
		}
		// Remove all (remaining) agents.
		for (String agent : this.getAgents()) {
			try {
				this.unregisterAgent(agent);
			} catch (AgentException e) {
				e.printStackTrace();
			}
		}
		mapFullyLoaded = false;
	}

	@Override
	public void pause() throws ManagementException {
		setState(EnvironmentState.PAUSED);
	}

	@Override
	public void start() throws ManagementException {
		setState(EnvironmentState.RUNNING);
	}

	/**
	 * initialize Repast. Does not reset the {@link BW4TServer}.
	 */
	public void init(Map<String, Parameter> parameters)
			throws ManagementException {
		// System.out.println("re-initializing repast simulator");
		setState(EnvironmentState.INITIALIZING);
		takeDownSimulation();

		Parameter map = parameters.get("map");
		if (map != null) {
			mapName = ((Identifier) map).getValue();
		}
		try {
			launchRepast();
		} catch (IOException e) {
			throw new ManagementException("launch of Repast failed", e);
		} catch (ScenarioLoadException e) {
			throw new ManagementException("launch of Repast failed", e);
		} catch (JAXBException e) {
			throw new ManagementException("launch of Repast failed", e);
		}

		setState(EnvironmentState.RUNNING);
		// System.out.println("re-initialised");

	}

	/**
	 * Launch the server
	 * 
	 * @param serverIp
	 *            , the ip address that the server should listen to
	 * @param serverPort
	 *            , the port that the server should listen to
	 * @throws RemoteException
	 * @throws ManagementException
	 * @throws MalformedURLException
	 */
	private void launchServer(String serverIp, String serverPort)
			throws RemoteException, ManagementException, MalformedURLException {
		server = new BW4TServer(serverIp, serverPort);
		setState(EnvironmentState.INITIALIZING);
		System.out.println("BW4TServer bound");
	}

	/**
	 * Launches the Repast framework and GUI. Does not return until there is an
	 * exception or getState()==KILLED. After stopping, runner is set back to
	 * null.
	 * 
	 * @throws IOException
	 * @throws ScenarioLoadException
	 * @throws JAXBException
	 * 
	 * @throws Exception
	 */
	private void launchRepast() throws IOException, ScenarioLoadException,
			JAXBException {
		theMap = NewMap.create(new FileInputStream(new File(MAPDIR
				+ this.mapName)));
		stepper = new Stepper(scenarioLocation, this);
		new Thread(stepper).start();
	}

	/**
	 * Get the instance of this environment
	 * 
	 * @return the instance
	 */
	public static BW4TEnvironment getInstance() {
		if (instance == null) {
			throw new IllegalStateException(
					"BW4TEnvironment has not been initialized");
		}
		return instance;
	}

	public static String getMapLocation() {
		return mapName;
	}

	/**
	 * Check whether an action is supported by this environment.
	 * 
	 * @param arg0
	 *            , the action that should be checked
	 * @return true if there is an entity, a dropzone and sequence not yet
	 *         complete
	 */
	@Override
	protected boolean isSupportedByEnvironment(Action arg0) {

		return !getEntities().isEmpty();
	}

	/**
	 * Check whether an action is supported by an entity type, always returns
	 * true for now
	 * 
	 * @param arg0
	 *            , the action that should be checked
	 * @param arg1
	 *            , the type of entity
	 * @return the result
	 */
	@Override
	protected boolean isSupportedByType(Action arg0, String arg1) {
		return true;
	}

	/**
	 * Check whether a state transition is valid, for now always returns true
	 * 
	 * @param oldState
	 *            , the old state of the environment
	 * @param newState
	 *            , the new state of the environment
	 * @return the result
	 */
	@Override
	public boolean isStateTransitionValid(EnvironmentState oldState,
			EnvironmentState newState) {
		return true;
	}

	/**
	 * Runs the server environment and launches repast. Should be used for both
	 * GOAL and Java.
	 * 
	 * @param args
	 *            , the initialization parameters
	 * @throws IOException
	 * @throws ManagementException
	 * @throws ScenarioLoadException
	 * @throws JAXBException
	 */
	public static void main(String[] args) throws IOException,
			ManagementException, ScenarioLoadException, JAXBException {
		String scenario = "";
		scenario = findArgument(args, scenario, "-scenario");
		String map = "";
		map = findArgument(args, map, "-map");
		String serverIp = "localhost";
		serverIp = findArgument(args, serverIp, "-serverip");
		String serverPort = "8080";
		serverPort = findArgument(args, serverPort, "-serverport");

		BW4TEnvironment environment = new BW4TEnvironment(scenario, map,
				serverIp, serverPort);
		// main just exits but we continue to run because GUI is open.
	}

	/**
	 * Find a certain argument in the string array and return its setting
	 * 
	 * @param args
	 *            , the string array containing all the arguments
	 * @param result
	 *            , where to save the result
	 * @param checkFor
	 *            , what to check for
	 * @return the result
	 */
	private static String findArgument(String[] args, String result,
			String checkFor) {
		for (int i = 0; i < args.length - 1; i++) {
			if (args[i].equalsIgnoreCase(checkFor)) {
				result = args[i + 1];
				break;
			}
		}
		return result;
	}

	/**
	 * Helper method to allow the server to call actions received from attached
	 * clients
	 * 
	 * @param entity
	 *            , the entity that should perform the action
	 * @param action
	 *            , the action that should be performed
	 * @return the percept received after performing the action
	 */
	public Percept performClientAction(String entity, Action action)
			throws ActException {
		BW4TLogger.getInstance().logAction(entity, action.toProlog());
		return performEntityAction(entity, action);
	}

	/**
	 * Helper method to allow the server to get all percepts for a connected
	 * client.
	 * <p>
	 * This function is synchronized to ensure that multiple calls are properly
	 * sequenced. This is important because getAllPercepts must 'lock' the
	 * environment and parallel calls would cause overlapping 'locks' taken at
	 * different moments in time.
	 * <p>
	 * Actually, locking the environment is done by copying the current location
	 * of the entity.
	 * <p>
	 * It seems that this new function is created because
	 * {@link AbstractEnvironment#getAllPerceptsFromEntity(String)} is final.
	 * 
	 * @param entity
	 *            , the entity for which all percepts should be gotten
	 * @return all percepts for the entity
	 */
	public synchronized LinkedList<Percept> getAllPerceptsFrom(String entity) {
		try {
			if (this.isMapFullyLoaded()) {
				((RobotEntityInt) getEntity(entity))
						.initializePerceptionCycle();
				return getAllPerceptsFromEntity(entity);
			}
		} catch (PerceiveException e) {
			e.printStackTrace();
		} catch (NoEnvironmentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Check if the map was fully loaded. When this is true, all entities also
	 * have been processed and the environment is ready to run. Note that
	 * because of #2016 there may be an async between Repast and this
	 * BW4TEnvironment, causing this flag to remain true while repast has in
	 * fact stopped. We detect this only when the user turns 'on' the Repast
	 * environment again, in {@link BW4TBuilder#build()}.
	 * 
	 * @return
	 */
	public boolean isMapFullyLoaded() {
		return mapFullyLoaded;
	}

	public void setMapFullyLoaded() {
		mapFullyLoaded = true;
	}

	/**
	 * get our custom runner.
	 * 
	 * @return
	 * @deprecated TODO remove
	 */
	public BW4TRunner getRunner() {
		return stepper.runner;
	}

	/**
	 * set new delay value. Lower is faster animation speed.
	 * 
	 * @param value
	 *            the value for the delay. Should be at least {@link #MIN_DELAY}
	 *            .
	 */
	public void setDelay(int value) {
		stepper.setDelay(value);

	}

	/**
	 * reset using parameters for initial situation. Does not kill the server.
	 */
	@Override
	public void reset(Map<String, Parameter> parameters)
			throws ManagementException {
		try {
			this.mapName = ((Identifier) parameters.get("map")).getValue();
			if (this.mapName == null) {
				this.mapName = "Random";
			}
			// this.mapLocation = "Maps/" + this.mapLocation;
			reset();
		} catch (Exception e) {
			throw new ManagementException("reset failed", e);
		}
	}

	/**
	 * reset to initial situation.
	 * 
	 * @throws NotBoundException
	 * @throws IOException
	 * @throws ScenarioLoadException
	 * @throws ManagementException
	 * @throws JAXBException
	 */
	public void reset() throws NotBoundException, IOException,
			ScenarioLoadException, ManagementException, JAXBException {
		takeDownSimulation();
		if (server != null) {
			server.takeDown();
			server = null;
		}
		// System.out.println("server is taken down");
		launchAll();
	}

	/**
	 * Take down the simulation: remove all entities, stop the stepper. Stop the
	 * {@link ServerContextDisplay}.
	 * 
	 * @throws ManagementException
	 */
	private void takeDownSimulation() throws ManagementException {
		// this should set state->KILLED which stops stepper.
		removeAllEntities();

		stepper.terminate();
		contextDisplay.close();
		contextDisplay = null;
		context = null;
	}

	/**
	 * get the repast current context. May be null if Repast not running now.
	 * Called from {@link BW4TBuilder} when repast gives us context.
	 * 
	 * @return Repast {@link Context}.
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * Set a new repast Context. May be null if Repast sstopped running.
	 * 
	 * @param c
	 */
	public void setContext(Context c) {
		context = c;
		// the display closes itself when you click on the close button.
		try {
			contextDisplay = new ServerContextDisplay(context);
		} catch (Exception e) {
			System.err.println("BW4T started ok but failed to launch display");
			e.printStackTrace();
		}
	}

	/**
	 * get the current {@link NewMap}
	 * 
	 * @return the {@link NewMap}
	 */
	public static NewMap getMap() {
		return theMap;
	}

}
