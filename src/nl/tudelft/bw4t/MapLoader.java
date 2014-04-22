package nl.tudelft.bw4t;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.eis.RobotEntity;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Door.Orientation;
import nl.tudelft.bw4t.map.Entity;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.robots.NavigatingRobot;
import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.server.BW4TEnvironment;
import nl.tudelft.bw4t.server.BW4TLogger;
import nl.tudelft.bw4t.zone.BlocksRoom;
import nl.tudelft.bw4t.zone.Corridor;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import repast.simphony.space.continuous.StickyBorders;
import eis.exceptions.EntityException;

/**
 * @author Lennard
 * @author W.Pasman added doors 15aug2011
 * @author W.Pasman added navpoints 22aug
 * 
 */
public final class MapLoader {

	/** Identifier used for the space projections, matched in context.xml */
	private static final String PROJECTION_ID = "BW4T_Projection";

	private static NewMap map;

	private MapLoader() {
	}

	/**
	 * Loads a file containing a map into the context. extends the sequence
	 * color list and the room color lists.
	 * <ul>
	 * <li>The sequence is extended with N random blocks (where N is the number
	 * of rooms in the map)
	 * <li>These N random blocks are random placed in the rooms
	 * <li>An additional 1.5*N random blocks are random placed in the rooms.
	 * </ul>
	 * 
	 * 
	 * @param location
	 *            The location of the file
	 * @param context
	 *            The context to load to.
	 * @throws IOException
	 *             if the file can not be read from.
	 * @throws JAXBException
	 */
	public static void loadMap(String location, Context<Object> context)
			throws IOException, JAXBException {

		/**
		 * Temp list of all zones, because we can not yet use
		 * BW4TEnvironment.getInstance().getContext() ... (returns null)
		 */
		Map<String, nl.tudelft.bw4t.zone.Zone> zones = new HashMap<String, nl.tudelft.bw4t.zone.Zone>();

		/**
		 * List of planned blocks for a room. We copy the map params and add the
		 * extra blocks to this map.
		 */
		AbstractMap<String, List<BlockColor>> roomblocks = new HashMap<String, List<BlockColor>>();
		Random random = new Random();

		map = NewMap.create(new FileInputStream(new File(BW4TEnvironment.MAPDIR
				+ location)));

		ContinuousSpace<Object> space = createSpace(context, (int) map
				.getArea().getX(), (int) map.getArea().getY());

		// make the extra random blocks.
		List<BlockColor> extraSequenceBlocks = makeRandomSequence(map
				.getRandomSequence());

		// determine all the extra blocks: the extra sequence blocks and more
		List<BlockColor> extraBlocks = new ArrayList<BlockColor>(
				extraSequenceBlocks);
		extraBlocks.addAll(makeRandomSequence(map.getRandomBlocks()));

		// make the target sequence.
		List<BlockColor> sequence = new ArrayList<BlockColor>(map.getSequence());
		sequence.addAll(extraSequenceBlocks);

		for (Zone corridor : map.getZones(Zone.Type.CORRIDOR)) {
			Corridor corr = createCorridor(context, space, corridor);
			zones.put(corr.getName(), corr);
		}

		for (Zone roomzone : map.getZones(Zone.Type.ROOM)) {
			Room room;
			if (roomzone.getName().equals("DropZone")) {
				room = new DropZone(roomzone, space, context);
				((DropZone) room).setSequence(sequence);
			} else {
				room = createRoom(context, space, roomzone);
				roomblocks.put(room.getName(), new ArrayList<BlockColor>(
						roomzone.getBlocks()));
			}

			for (nl.tudelft.bw4t.map.Door door : roomzone.getDoors()) {
				createDoor(context, space, door, room);
			}

			zones.put(roomzone.getName(), room);

		}

		connectAllZones(zones);

		// FIXME we may want to check if there is enough space at all for the
		// requested number of blocks. If not, we will hang in the next loop.

		// add the extra blocks to the rooms if there are any
		List<String> rooms = new ArrayList<String>(roomblocks.keySet());
		for (BlockColor extraBlock : extraBlocks) {
			// find the blocks of a room where it can be added.
			List<BlockColor> blocks;
			do {
				String room = rooms.get(random.nextInt(rooms.size()));
				blocks = roomblocks.get(room);
			} while (blocks.size() >= 10);
			blocks.add(extraBlock);
		}

		// place the blocks in the rooms.
		for (String room : roomblocks.keySet()) {
			createBlocksForRoom((Room) zones.get(room), context, space,
					roomblocks.get(room));

		}

		for (Entity entityparams : map.getEntities()) {
			if (entityparams.getType() == Entity.EntityType.NORMAL) {
				createEisEntityRobot(context, space, entityparams);
			} else {
				createJavaRobot(context, space, entityparams);
			}
		}

		BW4TEnvironment.getInstance().setMapFullyLoaded();
	}

	/**
	 * Connect all the zones according to the map.
	 * 
	 * @param zones
	 *            the map of all zones in repast. We can't yet access the BW4T
	 *            context, therefore we need to pass this explicitly..
	 */
	private static void connectAllZones(
			Map<String, nl.tudelft.bw4t.zone.Zone> zones) {
		for (Zone zone : map.getZones()) {
			nl.tudelft.bw4t.zone.Zone z = zones.get(zone.getName());
			for (Zone mapneigh : zone.getNeighbours()) {
				nl.tudelft.bw4t.zone.Zone neigh = zones.get(mapneigh.getName());
				z.addNeighbour(neigh);
			}
		}
	}

	/**
	 * make a random sequence of <size> colors.
	 * 
	 * @param num
	 *            is required number of blocks in the sequence.
	 */
	private static List<BlockColor> makeRandomSequence(int num) {
		BlockColor[] colors = BlockColor.values();
		List<BlockColor> sequence = new ArrayList<BlockColor>();
		Random random = new Random();
		for (int n = 0; n < num; n++) {
			sequence.add(colors[random.nextInt(colors.length)]);
		}

		return sequence;

	}

	/**
	 * Creates the {@link ContinuousSpace} in which all objects will be placed.
	 * 
	 * @param context
	 *            the context in which this space operates.
	 * @param size
	 *            the unparsed size from the input file.
	 * @return the created space
	 */
	private static ContinuousSpace<Object> createSpace(Context<Object> context,
			int width, int height) {

		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder
				.createContinuousSpaceFactory(null);
		return spaceFactory.createContinuousSpace(PROJECTION_ID, context,
				new SimpleCartesianAdder<Object>(), new StickyBorders(), width,
				height);
	}

	/**
	 * Creates a new {@link DropZone} in the context according to the data in
	 * the tokenizer.
	 * 
	 * @param context
	 *            The context in which the room should be placed.
	 * @param space
	 *            the space in which the bin should be placed.
	 * @param tokenizer
	 *            StringTokenizer containing the description of the room.
	 * @throws IllegalStateException
	 *             if a drop off location has already been defined in the given
	 *             context.
	 */
	private static void createDropZone(Context<Object> context,
			ContinuousSpace<Object> space, Zone dropzone,
			List<BlockColor> sequence) {

		if (context.getObjects(DropZone.class).size() > 0) {
			throw new IllegalStateException(
					"A drop zone has already been defined");
		}

		DropZone dropZone = new DropZone(dropzone, space, context);

		dropZone.setSequence(sequence);

	}

	/**
	 * Creates a new {@link Robot} in the context according to the data in the
	 * tokenizer and adds it to the EIS environment.
	 * 
	 * @param context
	 *            The context in which the robot should be placed.
	 * @param space
	 *            the space in which the robot should be placed.
	 * @param mapentity
	 *            The {@link Entity} on the map.
	 * @return The created robot
	 */
	private static void createEisEntityRobot(Context<Object> context,
			ContinuousSpace<Object> space, Entity mapentity) {
		BW4TEnvironment environment = BW4TEnvironment.getInstance();

		if (environment == null) {
			throw new IllegalStateException(
					"Tried to create a Robot to put in an EIS environment but no EIS environment was launched");
		}

		NavigatingRobot robot = createJavaRobot(context, space, mapentity);
		RobotEntity entity = new RobotEntity(robot);
		try {
			environment.registerEntity(robot.getName(), entity);
		} catch (EntityException e) {
			throw new IllegalStateException("Error when registering an entity",
					e);
		}
	}

	/**
	 * Creates a new {@link Robot} in the context according to the data in the
	 * tokenizer.
	 * 
	 * @param context
	 *            The context in which the robot should be placed.
	 * @param space
	 *            the space in which the robot should be placed.
	 * @param mapentity
	 *            The {@link Entity} on the map.
	 * @return The created robot
	 */
	private static NavigatingRobot createJavaRobot(Context<Object> context,
			ContinuousSpace<Object> space, Entity mapentity) {
		String name = mapentity.getName();

		NavigatingRobot robot = new NavigatingRobot(name, space, context,
				map.getOneBotPerCorridorZone());

		double x = mapentity.getPosition().getX();
		double y = mapentity.getPosition().getY();

		// we hoped to move this to Robot#connect() but failed #2346
		robot.moveTo(x, y);

		return robot;
	}

	/**
	 * Creates a new EMPTY (no blocks) {@link Room} in the context according to
	 * the data in the tokenizer. We make the room empty because there may be
	 * extra (random) blocks in addition to the explicit blocks for this room.
	 * Therefore the exact blocks for this room have to be determined at a
	 * higher level.
	 * 
	 * @param context
	 *            The context in which the room should be placed.
	 * @param space
	 *            the space in which the room should be placed.
	 * @param roomzone
	 *            the room {@link Zone}.
	 */
	private static Room createRoom(Context<Object> context,
			ContinuousSpace<Object> space, Zone roomzone) {
		Room room = new BlocksRoom(space, context, roomzone);

		return room;
	}

	/**
	 * Add navpoint to the context. Neighbours are NOT set at this point.
	 * 
	 * @param context
	 * @param space
	 */
	private static Corridor createCorridor(Context<Object> context,
			ContinuousSpace<Object> space, Zone zone) {
		Corridor np = new Corridor(zone, space, context);
		return np;
	}

	/**
	 * Creates a new {@link Door} in the context according to the
	 * {@link nl.tudelft.bw4t.map.Door}.
	 * 
	 * @param context
	 *            The context in which the room should be placed.
	 * @param space
	 *            the space in which the room should be placed.
	 * @param args
	 *            {@link nl.tudelft.bw4t.map.Door} object.
	 */
	private static void createDoor(Context<Object> context,
			ContinuousSpace<Object> space, nl.tudelft.bw4t.map.Door args,
			Room room) {
		Door door = new Door(space, context);

		double x = args.getPosition().getX();
		double y = args.getPosition().getY();
		Orientation ori = args.getOrientation();

		door.setPos(x, y, ori);

		door.connectTo(room);
	}

	/**
	 * Add given colors to the room.
	 * 
	 * @param room
	 * @param context
	 * @param space
	 * @param args
	 *            is a list of colors to be added.
	 */
	private static void createBlocksForRoom(Room room, Context<Object> context,
			ContinuousSpace<Object> space, List<BlockColor> args) {
		String roomName = room.getName();
		BW4TLogger.getInstance().logRoomBlocks(roomName, args);

		Rectangle2D roomBox = room.getBoundingBox();
		List<Rectangle2D> newblocks = new ArrayList<Rectangle2D>();

		for (BlockColor color : args) {
			Rectangle2D newpos = findFreePlace(roomBox, newblocks);

			Block block = new Block(color, space, context);

			block.moveTo(newpos.getCenterX(), newpos.getCenterY());
			newblocks.add(newpos);
		}
	}

	/**
	 * find an unoccupied position for a new block in the given room, where the
	 * given list of blocks are already in that room. Basically this algorithm
	 * picks random points till a free position is found.
	 * 
	 * @param room
	 * @param blocks
	 */
	private static Rectangle2D findFreePlace(Rectangle2D room,
			List<Rectangle2D> blocks) {
		Rectangle2D block = null;
		int retryCounter = 100; // max number of retries
		boolean blockPlacedOK = false;
		while (!blockPlacedOK) {
			double x = room.getMinX() + room.getWidth() * Math.random();
			double y = room.getMinY() + room.getHeight() * Math.random();
			block = new Rectangle2D.Double(x, y, Block.SIZE, Block.SIZE);

			blockPlacedOK = room.contains(block);
			for (Rectangle2D bl : blocks) {
				/*
				 * 2 blocks don't overlap if either their X or Y pos differs at
				 * least 2*SIZE.
				 */
				boolean noXoverlap = Math.abs(bl.getCenterX() - x) >= 2;
				boolean noYoverlap = Math.abs(bl.getCenterY() - y) >= 2;
				boolean noOverlap = noXoverlap || noYoverlap;
				blockPlacedOK = blockPlacedOK && noOverlap;
			}
			if (retryCounter-- == 0 && !blockPlacedOK) {
				throw new IllegalStateException(
						"room is too small to fit more blocks");
			}
		}
		return block;
	}

}
