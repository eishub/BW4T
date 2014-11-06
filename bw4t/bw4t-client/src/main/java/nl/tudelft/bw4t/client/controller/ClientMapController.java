package nl.tudelft.bw4t.client.controller;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.bw4t.client.controller.percept.processors.BumpedProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.ColorBlindProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.ColorProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.EPartnerProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.GripperCapacityProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.HoldingBlocksProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.HoldingProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.LocationProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.NegationProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.OccupiedProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.PerceptProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.PositionProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.RobotBatteryProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.RobotOldTargetUnreachableProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.RobotProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.RobotSizeProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.SequenceIndexProcessor;
import nl.tudelft.bw4t.client.controller.percept.processors.SequenceProcessor;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.renderer.AbstractMapController;
import nl.tudelft.bw4t.map.renderer.MapRendererInterface;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;

import org.apache.log4j.Logger;

import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Parameter;

/**
 * The Client Map Controller. This processes incoming percepts and pushes them
 * through {@link PerceptProcessor}s that do the actual updating. If GOAL is
 * connected, the percepts are retrieved only by GOAL getPercept calls. If the
 * controller is running standalone, it calls getPercepts itself. see also
 * {@link #run()}.
 * 
 * The incoming percepts, human GUI clicks and map rendering threads are all
 * running independently. Therefore this class has to be thread safe and not
 * return straight pointers to internal structures.
 * 
 * This class is thread safe. But this does not mean that the objects that are
 * returned are thread safe. Please consult thread safety of the relevant
 * objects as well.
 */
public class ClientMapController extends AbstractMapController {
	/**
	 * The log4j logger which writes logs.
	 */
	private static final Logger LOGGER = Logger
			.getLogger(ClientMapController.class);

	/** The exception string constant. */
	private static final String COULDNOTPOLL = "Could not correctly poll the percepts from the environment.";

	/**
	 * The Client Controller used by this Client Map Controller.
	 */
	private final ClientController clientController;

	/** The occupied rooms. */
	private final Set<Zone> occupiedRooms = new HashSet<Zone>();

	/** The rendered bot. */
	private ViewEntity myBot = new ViewEntity(0);

	/** The color sequence index. */
	private int colorSequenceIndex = 0;

	/** The visible blocks. */
	private final Set<ViewBlock> visibleBlocks = new HashSet<>();

	/** The (at one point) visible e-partners. */
	private final Set<ViewEPartner> knownEPartners = new HashSet<>();

	/** The visible robots. */
	private final Set<ViewEntity> visibleRobots = new HashSet<>();

	/** The visible robots. */
	private final Map<Long, ViewEntity> knownRobots = new HashMap<>();

	/**
	 * All the blocks. This works in a bit of hacky way. We insert
	 * partially-instantiated blocks in this set, waiting for other percepts to
	 * give more information. So if we receive a color, we know the blocks color
	 * but not the location. And when we receive a location, we do not yet know
	 * the block color. In both cases, partially instantiated ViewBlocks end up
	 * in the allBlocks list.
	 */
	private final Map<Long, ViewBlock> allBlocks = new HashMap<>();

	private final Map<String, PerceptProcessor> perceptProcessors;

	/** The color sequence. */
	private final List<BlockColor> colorSequence = new LinkedList<>();

	/**
	 * Instantiates a new client map controller.
	 * 
	 * @param map
	 *            the map
	 * @param controller
	 *            the controller
	 */
	public ClientMapController(NewMap map, ClientController controller) {
		super(map);

		if (controller == null) {
			throw new NullPointerException("controller=null");
		}

		if (!myBot.isInitialized()) {
			throw new IllegalStateException("myBot is not initialized properly");
		}

		clientController = controller;
		perceptProcessors = new HashMap<String, PerceptProcessor>();
		perceptProcessors.put("not", new NegationProcessor());
		perceptProcessors.put("robot", new RobotProcessor());
		perceptProcessors.put("occupied", new OccupiedProcessor());
		perceptProcessors.put("holding", new HoldingProcessor());
		perceptProcessors.put("holdingblocks", new HoldingBlocksProcessor());
		perceptProcessors.put("position", new PositionProcessor());
		perceptProcessors.put("color", new ColorProcessor());
		perceptProcessors.put("epartner", new EPartnerProcessor());
		perceptProcessors.put("sequence", new SequenceProcessor());
		perceptProcessors.put("sequenceIndex", new SequenceIndexProcessor());
		perceptProcessors.put("location", new LocationProcessor());
		perceptProcessors.put("robotSize", new RobotSizeProcessor());
		perceptProcessors.put("bumped", new BumpedProcessor());
		perceptProcessors.put("battery", new RobotBatteryProcessor());
		perceptProcessors.put("oldTargetUnreachable",
				new RobotOldTargetUnreachableProcessor());
		perceptProcessors
				.put("gripperCapacity", new GripperCapacityProcessor());
		perceptProcessors.put("colorblind", new ColorBlindProcessor());
	}

	@Override
	public synchronized List<BlockColor> getSequence() {
		return new ArrayList<BlockColor>(colorSequence);
	}

	/**
	 * Sets the sequence to given list.
	 * 
	 * @param sequence
	 *            new sequence to use.
	 */
	public synchronized void setSequence(List<BlockColor> sequence) {
		colorSequence.clear();
		colorSequence.addAll(sequence);
	}

	@Override
	public synchronized int getSequenceIndex() {
		return colorSequenceIndex;
	}

	public synchronized void setSequenceIndex(int sequenceIndex) {
		this.colorSequenceIndex = sequenceIndex;
	}

	/**
	 * returns (copy of) the set of occupied rooms.
	 * 
	 * @return
	 */
	public synchronized Set<Zone> getOccupiedRooms() {
		return new HashSet<Zone>(occupiedRooms);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.tudelft.bw4t.map.renderer.MapController#isOccupied(nl.tudelft.bw4t
	 * .map.Zone)
	 */
	@Override
	public synchronized boolean isOccupied(Zone room) {
		if (!room.isInitialized()) {
			return false;
		}
		return occupiedRooms.contains(room);
	}

	/**
	 * Adds an occupied room to the list of occupied rooms.
	 * 
	 * @param name
	 *            the name
	 */
	public synchronized void addOccupiedRoom(Zone zone) {
		if (!zone.isInitialized()) {
			throw new IllegalStateException("zone is not initialized:" + zone);
		}
		occupiedRooms.add(zone);
	}

	/**
	 * Removes the occupied room from the list of occupied rooms.
	 * 
	 * @param name
	 *            the name
	 */
	public synchronized void removeOccupiedRoom(Zone zone) {
		if (!zone.isInitialized()) {
			throw new IllegalStateException("zone is not initialized:" + zone);
		}
		occupiedRooms.remove(zone);
	}

	@Override
	public synchronized Set<ViewBlock> getVisibleBlocks() {
		return new HashSet<ViewBlock>(visibleBlocks);
	}

	@Override
	public synchronized void addVisibleBlock(ViewBlock b) {
		if (!b.isInitialized()) {
			throw new IllegalStateException("block is not initialized:" + b);
		}
		visibleBlocks.add(b);
	}

	public synchronized void clearVisible() {
		visibleBlocks.clear();
	}

	@Override
	public synchronized Set<ViewEntity> getVisibleEntities() {
		return new HashSet<ViewEntity>(visibleRobots);
	}

	/**
	 * Get all the known e-partners.
	 * 
	 * @return (copy of) the set of known e-partners
	 */
	public synchronized Set<ViewEPartner> getEPartners() {
		return new HashSet<ViewEPartner>(knownEPartners);
	}

	public synchronized void clearVisiblePositions() {
		visibleRobots.clear();
		visibleRobots.add(myBot);
	}

	/**
	 * Returns an e-partner with this id that at one point has ever been
	 * visible.
	 * 
	 * @param id
	 *            The id.
	 * @return The e-partner found. null if not found.
	 */
	public synchronized ViewEPartner getKnownEPartner(long id) {
		// fake epartner, so that we can use equals (which is guaranteed stable
		// after init).
		ViewEPartner equalp = new ViewEPartner(id, new Point2D.Double(), false);
		for (ViewEPartner ep : knownEPartners) {
			if (ep.equals(equalp)) {
				return ep;
			}
		}
		return null;
	}

	/**
	 * Makes a robot visible. Maybe this should be changed into an attribute
	 * attached to a Robot? We do not even check if given entity is a robot.
	 * 
	 * @param bot
	 *            robot to be added. Actually any {@link ViewEntity}
	 */
	public synchronized void addVisibleRobot(ViewEntity bot) {
		if (!bot.isInitialized()) {
			throw new IllegalStateException("entity is not initialized: " + bot);
		}
		visibleRobots.add(bot);
	}

	/**
	 * Utility function.
	 * 
	 * @param id
	 * @return
	 */
	public synchronized ViewEntity getKnownRobot(long id) {
		return knownRobots.get(id);
	}

	/**
	 * Get existing robot with given id, or return a new one with that id.
	 * 
	 * @param id
	 * @return
	 */
	public synchronized ViewEntity getCreateRobot(long id) {
		ViewEntity bot = getKnownRobot(id);
		if (bot != null) {
			return bot;
		}
		bot = new ViewEntity(id);
		knownRobots.put(id, bot);
		return bot;
	}

	/**
	 * get our own robot. Initially this returns a FAKE {@link ViewEntity},
	 * because we need to store incoming percepts about it until we receive the
	 * real robot ID.
	 * 
	 * @return ViewEntity that represents our robot. This ViewEntity may have
	 *         the incorrect ID and settings as long as we did not receive a
	 *         robot percept from the server.
	 */
	public ViewEntity getTheBot() {
		return myBot;
	}

	/**
	 * get block with given ID, or create it if it does not yet exist.
	 * 
	 * @param id
	 *            block id
	 * @return {@link ViewBlock} that has given id.
	 */
	public synchronized ViewBlock getBlock(Long id) {
		ViewBlock b = allBlocks.get(id);
		if (b == null) {
			b = new ViewBlock(id);
			allBlocks.put(id, b);
		}
		return b;
	}

	/**
	 * @param id
	 *            of the block to be checked
	 * @return true iff the block is in in the environment
	 */
	public synchronized boolean containsBlock(Long id) {
		return allBlocks.containsKey(id);
	}

	/**
	 * Add an e-partner.
	 * 
	 * @param id
	 * @param holderId
	 * @return
	 */
	public synchronized void addEPartner(ViewEPartner epartner) {
		if (!epartner.isInitialized()) {
			throw new IllegalArgumentException("epartner not initialized:"
					+ epartner);
		}
		knownEPartners.add(epartner);
	}

	/******************************************************************************/
	/**************** utility functions (may be not thread safe) ******************/
	/******************************************************************************/
	@Override
	public Set<ViewEPartner> getVisibleEPartners() {
		// notice, not synchronized, this is just a utility function that is not
		// thread safe because it calls isVisible()
		Set<ViewEPartner> visibleEPartners = new HashSet<ViewEPartner>();
		for (ViewEPartner ep : getEPartners()) {
			if (ep.isVisible()) {
				visibleEPartners.add(ep);
			}
		}
		return visibleEPartners;
	}

	/**
	 * Returns an e-partner with this id that is currently visible. This is just
	 * a utility function and may be not thread safe.
	 * 
	 * @param id
	 *            The id.
	 * @return The e-partner found. null if no such e-partner.
	 */
	public ViewEPartner getViewEPartner(long id) {
		for (ViewEPartner ep : getVisibleEPartners()) {
			if (ep.getId() == id) {
				return ep;
			}
		}
		return null;
	}

	/**
	 * Handle all the percepts. Utility function. May be not thread safe as we
	 * can not guarantee thread safety of processors (they are poking into other
	 * structures).
	 * 
	 * @param name
	 *            the name of the percept
	 * @param perceptParameters
	 *            the percept parameters
	 */

	public void handlePercept(String name, List<Parameter> perceptParameters) {
		StringBuilder sb = new StringBuilder("Handling percept: ");
		sb.append(name);
		sb.append(" => [");
		for (Parameter p : perceptParameters) {
			sb.append(p.toProlog());
			sb.append(", ");
		}
		sb.append("]");
		LOGGER.debug(sb.toString());
		PerceptProcessor processor = perceptProcessors.get(name);
		if (processor != null) {
			processor.process(perceptParameters, this);
		}
	}

	/**
	 * utility function. Probably not thread safe.
	 * 
	 * @see nl.tudelft.bw4t.map.renderer.AbstractMapController#updateRenderer
	 *      (nl.tudelft.bw4t.map.renderer.MapRendererInterface)
	 */
	@Override
	protected void updateRenderer(MapRendererInterface mri) {
		mri.validate();
		mri.repaint();
	}

	/*
	 * (non-Javadoc) Utility function. Probably not thread safe.
	 * 
	 * @see nl.tudelft.bw4t.map.renderer.AbstractMapController#run()
	 */
	@Override
	public void run() {
		/**
		 * If GOAL is not connected we need to fetch the percepts ourselves.
		 * Otherwise, GOAL will fetch them and we just reuse them.
		 */
		if (clientController.isHuman()
				&& !clientController.getEnvironment().isConnectedToGoal()) {
			try {
				clientController.getEnvironment().gatherPercepts(
						getTheBot().getName());
			} catch (PerceiveException e) {
				LOGGER.error(COULDNOTPOLL, e);
			} catch (NoEnvironmentException | NullPointerException e) {
				LOGGER.fatal(COULDNOTPOLL
						+ " No connection could be made to the environment", e);
				setRunning(false);
			}
		}
		super.run();
	}

	/**
	 * This is called when we hear our own bot ID from the server. This needs
	 * special treatment because we already may have received info about this
	 * bot, which was stored so far in a fake {@link #myBot}. Also there may
	 * already exist a bot with the real ID (if there was a robotSize percept ).
	 * 
	 * FIXME we do not check here if this is called twice. It really should not
	 * happen but some unit tests do this wrong.
	 * 
	 * @param longValue
	 */
	public void setTheBotId(long id) {
		ViewEntity fakeBot = myBot;
		myBot = getCreateRobot(id);
		myBot.merge(fakeBot);
	}

}
