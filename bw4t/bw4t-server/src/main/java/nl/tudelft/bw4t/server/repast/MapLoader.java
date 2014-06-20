package nl.tudelft.bw4t.server.repast;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Door.Orientation;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.environment.Launcher;
import nl.tudelft.bw4t.server.logging.BotLog;
import nl.tudelft.bw4t.server.model.blocks.Block;
import nl.tudelft.bw4t.server.model.doors.Door;
import nl.tudelft.bw4t.server.model.zone.Blockade;
import nl.tudelft.bw4t.server.model.zone.BlocksRoom;
import nl.tudelft.bw4t.server.model.zone.ChargingZone;
import nl.tudelft.bw4t.server.model.zone.Corridor;
import nl.tudelft.bw4t.server.model.zone.DropZone;
import nl.tudelft.bw4t.server.model.zone.Room;

import org.apache.log4j.Logger;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import repast.simphony.space.continuous.StickyBorders;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;

/**
 * The MapLoader class loads the map from a given map location.
 */
public final class MapLoader {

    /**
     * Identifier used for the space projections, matched in context.xml
     */
    public static final String PROJECTION_ID = "BW4T_Projection";

    /** The GRID_PROJECTION_ID Constant. */
    public static final String GRID_PROJECTION_ID = "BW4T_Grid_Projection";

    /**
     * The log4j Logger which displays logs on console.
     */
    private static final Logger LOGGER = Logger.getLogger(MapLoader.class);

    /**
     * The map.
     */
    private static NewMap map;

    /**
     * Instantiates a new map loader.
     */
    private MapLoader() {
    }

    /**
     * Loads a file containing a map into the context. extends the sequence color list and the room color lists.
     * <ul>
     * <li>The sequence is extended with N random blocks (where N is the number of rooms in the map)
     * <li>These N random blocks are random placed in the rooms
     * <li>An additional 1.5*N random blocks are random placed in the rooms.
     * </ul>
     * 
     * @param tmpLocation
     *            The location of the file
     * @param context
     *            The context to load to.
     * @throws JAXBException
     *             the JAXB exception
     */
    public static void loadMap(String tmpLocation, Context<Object> context) throws JAXBException {
        Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones = 
                new HashMap<String, nl.tudelft.bw4t.server.model.zone.Zone>();
        Map<String, List<BlockColor>> roomBlocks = new HashMap<String, List<BlockColor>>();

        ContinuousSpace<Object> space = initEmptyMap(tmpLocation, context);
        Grid<Object> grid = createGridSpace(context, (int) map.getArea().getX(), (int) map.getArea().getY());

        List<BlockColor> sequence = new ArrayList<BlockColor>(map.getSequence());
        createZones(context, zones, roomBlocks, space, grid, sequence);
        makeBlocks(roomBlocks, sequence);
        placeBlocks(context, zones, roomBlocks, space, grid);

        BW4TEnvironment.getInstance().setMapFullyLoaded();
    }

    /**
     * Make blocks.
     * 
     * @param roomblocks
     *            the roomblocks
     * @param sequence
     *            the sequence
     */
    private static void makeBlocks(Map<String, List<BlockColor>> roomblocks, List<BlockColor> sequence) {
        Random random = new Random();
        List<BlockColor> extraSequenceBlocks = makeRandomSequence(map.getRandomSequence());
        sequence.addAll(extraSequenceBlocks);

        List<BlockColor> extraBlocks = new ArrayList<BlockColor>(extraSequenceBlocks);
        extraBlocks.addAll(makeRandomSequence(map.getRandomBlocks()));
        addExtraBlocks(roomblocks, random, extraBlocks);
    }

    /**
     * Initializes the empty map.
     * 
     * @param mapFilename
     *            the filename of the map file
     * @param context
     *            the context on which the empty should be initialized
     * @return the continuous space
     * @throws JAXBException
     *             the JAXB exception
     */
    private static ContinuousSpace<Object> initEmptyMap(String mapFilename, Context<Object> context)
            throws JAXBException {
        String location = System.getProperty("user.dir") + "/maps/" + mapFilename;
        try {
            map = NewMap.create(new FileInputStream(new File(location)));
        } catch (FileNotFoundException e) {
            LOGGER.error("Unable to read mapFile", e);
        }

        ContinuousSpace<Object> space = createSpace(context, (int) map.getArea().getX(), (int) map.getArea().getY());
        Launcher.getInstance().getEntityFactory().setSpace(space);
        return space;
    }

    /**
     * Place blocks.
     * 
     * @param context
     *            the context on which the actions should have effect
     * @param zones
     *            the zones including the rooms in which the blocks should be created
     * @param roomblocks
     *            the rooms which should contain blocks
     * @param space
     *            the space
     * @param grid
     *            the grid
     */
    private static void placeBlocks(Context<Object> context, Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones,
            Map<String, List<BlockColor>> roomblocks, ContinuousSpace<Object> space, Grid<Object> grid) {
        for (String room : roomblocks.keySet()) {
            createBlocksForRoom((Room) zones.get(room), context, space, grid, roomblocks.get(room));
        }
    }

    /**
     * Adds the extra blocks.
     * 
     * @param roomblocks
     *            the roomblocks
     * @param random
     *            the random
     * @param extraBlocks
     *            the extra blocks
     */
    private static void addExtraBlocks(Map<String, List<BlockColor>> roomblocks, Random random,
            List<BlockColor> extraBlocks) {
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
    }

    /**
     * Creates the zones.
     * 
     * @param context
     *            the context
     * @param zones
     *            the zones
     * @param roomblocks
     *            the roomblocks
     * @param space
     *            the space
     * @param sequence
     *            the sequence
     * @param grid
     *            the grid
     */
    private static void createZones(Context<Object> context, Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones,
            Map<String, List<BlockColor>> roomblocks, ContinuousSpace<Object> space, Grid<Object> grid,
            List<BlockColor> sequence) {
        createCorridors(context, zones, space, grid);

        createRooms(context, zones, roomblocks, space, grid, sequence);

        createBlockades(context, zones, space, grid);

        createChargingZones(context, zones, space, grid);

        connectAllZones(zones);
    }

    /**
     * Creates the charging zones.
     * 
     * @param context
     *            the context
     * @param zones
     *            the zones
     * @param space
     *            the space
     * @param grid
     *            the grid
     */
    private static void createChargingZones(Context<Object> context,
            Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones, ContinuousSpace<Object> space,
                Grid<Object> grid) {
        for (Zone chargingzone : map.getZones(Zone.Type.CHARGINGZONE)) {
            ChargingZone czone = createChargingZone(context, space, grid, chargingzone);
            zones.put(chargingzone.getName(), czone);
        }
    }

    /**
     * Creates the blockades.
     * 
     * @param context
     *            the context
     * @param zones
     *            the zones
     * @param space
     *            the space
     * @param grid
     *            the grid
     */
    private static void createBlockades(Context<Object> context,
            Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones, ContinuousSpace<Object> space,
                Grid<Object> grid) {
        for (Zone blockzone : map.getZones(Zone.Type.BLOCKADE)) {
            Blockade blockade = createBlockade(context, space, grid, blockzone);
            zones.put(blockzone.getName(), blockade);
        }
    }

    /**
     * Creates the rooms.
     * 
     * @param context
     *            the context
     * @param zones
     *            the zones
     * @param roomblocks
     *            the roomblocks
     * @param space
     *            the space
     * @param sequence
     *            the sequence
     * @param grid
     *            the grid
     */
    private static void createRooms(Context<Object> context, Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones,
            Map<String, List<BlockColor>> roomblocks, ContinuousSpace<Object> space, Grid<Object> grid,
            List<BlockColor> sequence) {
        for (Zone roomzone : map.getZones(Zone.Type.ROOM)) {
            Room room;
            if ("DropZone".equals(roomzone.getName())) {
                room = new DropZone(roomzone, space, grid, context);
                ((DropZone) room).setSequence(sequence);
            } else {
                room = createRoom(context, space, grid, roomzone);
                roomblocks.put(room.getName(), new ArrayList<BlockColor>(roomzone.getBlocks()));
            }

            for (nl.tudelft.bw4t.map.Door door : roomzone.getDoors()) {
                createDoor(context, space, grid, door, room);
            }
            zones.put(roomzone.getName(), room);
        }

    }

    /**
     * Creates the corridors.
     * 
     * @param context
     *            the context
     * @param zones
     *            the zones
     * @param space
     *            the space
     * @param grid
     *            the grid
     */
    private static void createCorridors(Context<Object> context,
            Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones, ContinuousSpace<Object> space,
                Grid<Object> grid) {
        for (Zone corridor : map.getZones(Zone.Type.CORRIDOR)) {
            Corridor corr = createCorridor(context, space, grid, corridor);
            zones.put(corr.getName(), corr);
        }
    }

    /**
     * Connect all the zones according to the map.
     * 
     * @param zones
     *            the map of all zones in repast. We can't yet access the BW4T context, therefore we need to pass this
     *            explicitly..
     */
    private static void connectAllZones(Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones) {
        for (Zone zone : map.getZones()) {
            nl.tudelft.bw4t.server.model.zone.Zone z = zones.get(zone.getName());
            for (Zone mapneigh : zone.getNeighbours()) {
                nl.tudelft.bw4t.server.model.zone.Zone neigh = zones.get(mapneigh.getName());
                z.addNeighbour(neigh);
            }
        }
    }

    /**
     * make a random sequence of <size> colors.
     * 
     * @param num
     *            is required number of blocks in the sequence.
     * @return the list
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
     * @param width
     *            The width of the space
     * @param height
     *            The height of the space
     * @return the created space
     */
    private static ContinuousSpace<Object> createSpace(Context<Object> context, int width, int height) {

        ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
        return spaceFactory.createContinuousSpace(PROJECTION_ID, context, new SimpleCartesianAdder<Object>(),
                new StickyBorders(), width, height);
    }

    /**
     * Creates the {@link Grid} in which all objects will be placed, in conjuction with the continuous space. The grid
     * space allows for querying for Von Neumann and Moore neighborhoods.
     * 
     * @param context
     *            the context in which this space operates.
     * @param mapWidth
     *            The width of the space
     * @param mapHeight
     *            The height of the space
     * @return the create grid for the given context and map dimensions
     */
    private static Grid<Object> createGridSpace(Context<Object> context, int mapWidth, int mapHeight) {

        GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
        GridBuilderParameters params = new GridBuilderParameters(new StrictBorders(), new SimpleGridAdder<Object>(),
                true, mapWidth, mapHeight);

        Grid<Object> grid = gridFactory.createGrid(GRID_PROJECTION_ID, context, params);
        Launcher.getInstance().getEntityFactory().setGrid(grid);

        return grid;
    }

    /**
     * Creates a new EMPTY (no blocks) {@link Room} in the context according to the data in the tokenizer. We make the
     * room empty because there may be extra (random) blocks in addition to the explicit blocks for this room. Therefore
     * the exact blocks for this room have to be determined at a higher level.
     * 
     * @param context
     *            The context in which the room should be placed.
     * @param space
     *            the space in which the room should be placed.
     * @param roomzone
     *            the room {@link Zone}.
     * @param grid
     *            the grid
     * @return the room
     */
    private static Room createRoom(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid,
            Zone roomzone) {
        return new BlocksRoom(space, grid, context, roomzone);
    }

    /**
     * Creates a charging zone where multiple robots can charge.
     * 
     * @param context
     *            The context in which the room should be placed.
     * @param space
     *            the space in which the room should be placed.
     * @param grid
     *            the grid
     * @param chargezone
     *            the room {@link Zone}.
     * @return the charging zone
     */
    private static ChargingZone createChargingZone(Context<Object> context, ContinuousSpace<Object> space,
            Grid<Object> grid, Zone chargezone) {
        return new ChargingZone(chargezone, space, grid, context);
    }

    /**
     * Creates a blockade to block the robots' path.
     * 
     * @param context
     *            The context in which the room should be placed.
     * @param space
     *            the space in which the room should be placed.
     * @param chargezone
     *            the room {@link Zone}.
     * @param grid
     *            the grid
     * @return the blockade
     */

    private static Blockade createBlockade(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid,
            Zone chargezone) {
        return new Blockade(chargezone, space, grid, context);
    }

    /**
     * Add navpoint to the context. Neighbours are NOT set at this point.
     * 
     * @param context
     *            the context
     * @param space
     *            the space
     * @param zone
     *            the zone
     * @param grid
     *            the grid
     * @return the corridor
     */
    private static Corridor createCorridor(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid,
            Zone zone) {
        return new Corridor(zone, space, grid, context);
    }

    /**
     * Creates a new {@link Door} in the context according to the {@link nl.tudelft.bw4t.map.Door}.
     * 
     * @param context
     *            The context in which the room should be placed.
     * @param space
     *            the space in which the room should be placed.
     * @param args
     *            {@link nl.tudelft.bw4t.map.Door} object.
     * @param grid
     *            the grid
     * @param room
     *            the room
     */
    private static void createDoor(Context<Object> context, ContinuousSpace<Object> space, Grid<Object> grid,
            nl.tudelft.bw4t.map.Door args, Room room) {
        Door door = new Door(space, grid, context);

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
     *            the room
     * @param context
     *            the context
     * @param space
     *            the space
     * @param grid
     *            the grid
     * @param args
     *            is a list of colors to be added.
     */
    private static void createBlocksForRoom(Room room, Context<Object> context, ContinuousSpace<Object> space,
            Grid<Object> grid, List<BlockColor> args) {
        StringBuilder builder = new StringBuilder();
        for (BlockColor c : args) {
            builder.append(" ");
            builder.append(c.getLetter().toString());
        }
        String letter = builder.toString();

        LOGGER.log(BotLog.BOTLOG, String.format("room %s contains blocks: %s", room.getName(), letter));

        Rectangle2D roomBox = room.getBoundingBox();
        List<Rectangle2D> newblocks = new ArrayList<Rectangle2D>();

        for (BlockColor color : args) {
            Rectangle2D newpos = findFreePlace(roomBox, newblocks);

            Block block = new Block(color, space, grid, context);

            block.moveTo(newpos.getCenterX(), newpos.getCenterY());
            newblocks.add(newpos);
        }
    }

    /**
     * find an unoccupied position for a new block in the given room, where the given list of blocks are already in that
     * room. Basically this algorithm picks random points till a free position is found.
     * 
     * @param room
     *            the room
     * @param blocks
     *            the blocks
     * @return the rectangle2 d
     */

    private static Rectangle2D findFreePlace(Rectangle2D room, List<Rectangle2D> blocks) {
        Rectangle2D block = null;
        // max number of retries
        int retryCounter = 100;
        boolean blockPlacedOK = false;
        while (!blockPlacedOK) {
            double x = room.getMinX() + room.getWidth() * Math.random();
            double y = room.getMinY() + room.getHeight() * Math.random();
            block = new Rectangle2D.Double(x, y, Block.SIZE, Block.SIZE);

            blockPlacedOK = room.contains(block);
            for (Rectangle2D bl : blocks) {
                blockPlacedOK = checkPlacement(blockPlacedOK, x, y, bl);
            }
            if (retryCounter-- == 0 && !blockPlacedOK) {
                throw new IllegalStateException("room is too small to fit more blocks");
            }
        }
        return block;
    }

    /**
     * @param blockPlacedOK 
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param bl the block
     * @return check if the block is placed correctly
     */
    private static boolean checkPlacement(boolean blockPlacedOK, double x, double y, Rectangle2D bl) {
        boolean noXoverlap = Math.abs(bl.getCenterX() - x) >= 2;
        boolean noYoverlap = Math.abs(bl.getCenterY() - y) >= 2;
        boolean noOverlap = noXoverlap || noYoverlap;
        blockPlacedOK = blockPlacedOK && noOverlap;
        return blockPlacedOK;
    }

}
