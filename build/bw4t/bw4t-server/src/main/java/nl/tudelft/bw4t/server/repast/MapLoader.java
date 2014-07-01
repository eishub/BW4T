package nl.tudelft.bw4t.server.repast;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Door.Orientation;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.logging.BotLog;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.BW4TServerMapListerner;
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
public class MapLoader implements BW4TServerMapListerner {

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

    private boolean loaded = false;

    /**
     * Instantiates a new map loader.
     */
    public MapLoader() {
    }

    @Override
    public void contextChange(BW4TServerMap map) {
        loaded = false;
        if (map.hasContext() && map.hasMap()) {
            loadMap(map);
            loaded = true;
        }
    }

    @Override
    public void mapChange(BW4TServerMap map) {
        if (map.hasContext() && map.hasMap()) {
            //Repast leegmaken en dan loadMap
            if (!loaded) {
                loadMap(map);
                loaded = true;
            }
        }
    }

    /**
     * Loads a file containing a map into the context. extends the sequence color list and the room color lists.
     * <ul>
     * <li>The sequence is extended with N random blocks (where N is the number of rooms in the map)
     * <li>These N random blocks are random placed in the rooms
     * <li>An additional 1.5*N random blocks are random placed in the rooms.
     * </ul>
     * 
     * @param context
     *            The context to load to.
     */
    public static void loadMap(BW4TServerMap context) {
        if (context == null || !context.hasContext() || !context.hasMap()) {
            throw new IllegalArgumentException("Cannot load a map that does not have a repast Context and a BW4TMap.");
        }
        Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones = new HashMap<String, nl.tudelft.bw4t.server.model.zone.Zone>();
        Map<String, List<BlockColor>> roomBlocks = new HashMap<String, List<BlockColor>>();

        createSpace(context);
        createGridSpace(context);

        List<BlockColor> sequence = new ArrayList<BlockColor>(context.getMap().getSequence());
        createZones(context, zones, roomBlocks, sequence);
        makeBlocks(context, roomBlocks, sequence);
        placeBlocks(context, zones, roomBlocks);
        
        context.getObjectsFromContext(DropZone.class).iterator().next().setSequence(sequence);

        BW4TEnvironment.getInstance().setMapFullyLoaded();
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
    private static ContinuousSpace<Object> createSpace(BW4TServerMap smap) {
        Context<Object> context = smap.getContext();
        final Point area = smap.getMap().getArea();
        int width = (int) area.getX() + 1;
        int height = (int) area.getY() + 1;
    
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
    private static Grid<Object> createGridSpace(BW4TServerMap smap) {
        Context<Object> context = smap.getContext();
        final Point area = smap.getMap().getArea();
        int width = (int) area.getX() + 1;
        int height = (int) area.getY() + 1;
    
        GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
        GridBuilderParameters params = new GridBuilderParameters(new StrictBorders(), new SimpleGridAdder<Object>(),
                true, width, height);
    
        return gridFactory.createGrid(GRID_PROJECTION_ID, context, params);
    }

    /**
     * Creates the zones.
     * 
     * @param smap
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
    private static void createZones(BW4TServerMap smap, Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones,
            Map<String, List<BlockColor>> roomblocks, List<BlockColor> sequence) {
        createCorridors(smap, zones);

        createRooms(smap, zones, roomblocks, sequence);

        createBlockades(smap, zones);

        createChargingZones(smap, zones);

        connectAllZones(smap, zones);
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
    private static void createCorridors(BW4TServerMap context,
            Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones) {
        for (Zone corridor : context.getMap().getZones(Zone.Type.CORRIDOR)) {
            Corridor corr = new Corridor(corridor, context);
            zones.put(corr.getName(), corr);
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
     * @param sequence
     *            the sequence
     */
    private static void createRooms(BW4TServerMap context, Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones,
            Map<String, List<BlockColor>> roomblocks, List<BlockColor> sequence) {
        for (Zone roomzone : context.getMap().getZones(Zone.Type.ROOM)) {
            Room room;
            if (Zone.DROP_ZONE_NAME.equals(roomzone.getName())) {
                room = new DropZone(roomzone, sequence, context);
            } else {
                room = new BlocksRoom(roomzone, context);
                roomblocks.put(room.getName(), new ArrayList<BlockColor>(roomzone.getBlocks()));
            }

            for (nl.tudelft.bw4t.map.Door door : roomzone.getDoors()) {
                createDoor(context, door, room);
            }
            zones.put(roomzone.getName(), room);
        }

    }

    /**
     * Creates a new {@link Door} in the context according to the {@link nl.tudelft.bw4t.map.Door}.
     * 
     * @param smap
     *            The context in which the room should be placed.
     * @param args
     *            {@link nl.tudelft.bw4t.map.Door} object.
     * @param room
     *            the room
     */
    private static void createDoor(BW4TServerMap smap, nl.tudelft.bw4t.map.Door args, Room room) {
        Door door = new Door(smap);

        double x = args.getPosition().getX();
        double y = args.getPosition().getY();
        Orientation ori = args.getOrientation();

        door.setPos(x, y, ori);

        door.connectTo(room);
    }

    /**
     * Creates the blockades.
     * 
     * @param context
     *            the context
     * @param zones
     *            the zones
     */
    private static void createBlockades(BW4TServerMap context, Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones) {
        for (Zone blockzone : context.getMap().getZones(Zone.Type.BLOCKADE)) {
            zones.put(blockzone.getName(), new Blockade(blockzone, context));
        }
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
    private static void createChargingZones(BW4TServerMap context,
            Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones) {
        for (Zone chargezone : context.getMap().getZones(Zone.Type.CHARGINGZONE)) {
            ChargingZone czone = new ChargingZone(chargezone, context);
            zones.put(chargezone.getName(), czone);
        }
    }

    /**
     * Make blocks.
     * 
     * @param roomblocks
     *            the roomblocks
     * @param sequence
     *            the sequence
     */
    private static void makeBlocks(BW4TServerMap smap, Map<String, List<BlockColor>> roomblocks,
            List<BlockColor> sequence) {
        Random random = new Random();
        List<BlockColor> extraSequenceBlocks = makeRandomSequence(smap.getMap().getRandomSequence());
        sequence.addAll(extraSequenceBlocks);

        List<BlockColor> extraBlocks = new ArrayList<BlockColor>(extraSequenceBlocks);
        extraBlocks.addAll(makeRandomSequence(smap.getMap().getRandomBlocks()));
        addExtraBlocks(roomblocks, random, extraBlocks);
    }

    /**
     * make a random sequence of <size> colors.
     * 
     * @param num
     *            is required number of blocks in the sequence.
     * @return the list
     */
    private static List<BlockColor> makeRandomSequence(int num) {
        List<BlockColor> colors = BlockColor.getAvailableColors();
        List<BlockColor> sequence = new ArrayList<BlockColor>();
        Random random = new Random();
        for (int n = 0; n < num; n++) {
            sequence.add(colors.get(random.nextInt(colors.size())));
        }

        return sequence;

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
     * Connect all the zones according to the map.
     * 
     * @param smap
     * 
     * @param zones
     *            the map of all zones in repast. We can't yet access the BW4T context, therefore we need to pass this
     *            explicitly..
     */
    private static void connectAllZones(BW4TServerMap smap, Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones) {
        for (Zone zone : smap.getMap().getZones()) {
            nl.tudelft.bw4t.server.model.zone.Zone z = zones.get(zone.getName());
            for (Zone mapneigh : zone.getNeighbours()) {
                nl.tudelft.bw4t.server.model.zone.Zone neigh = zones.get(mapneigh.getName());
                z.addNeighbour(neigh);
            }
        }
    }

    /**
     * Place blocks.
     * 
     * @param smap
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
    private static void placeBlocks(BW4TServerMap smap, Map<String, nl.tudelft.bw4t.server.model.zone.Zone> zones,
            Map<String, List<BlockColor>> roomblocks) {
        for (String room : roomblocks.keySet()) {
            createBlocksForRoom((Room) zones.get(room), smap, roomblocks.get(room));
        }
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
    private static void createBlocksForRoom(Room room, BW4TServerMap context, List<BlockColor> args) {
        LOGGER.log(BotLog.BOTLOG, String.format("room %s contains blocks: %s", room.getName(), toString(args)));

        Rectangle2D roomBox = room.getBoundingBox();
        List<Rectangle2D> newblocks = new ArrayList<Rectangle2D>();

        for (BlockColor color : args) {
            Rectangle2D newpos = findFreePlace(roomBox, newblocks);

            Block block = new Block(color, context);

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
     * @param x
     *            the x-coordinate
     * @param y
     *            the y-coordinate
     * @param bl
     *            the block
     * @return check if the block is placed correctly
     */
    private static boolean checkPlacement(boolean blockPlacedOK, double x, double y, Rectangle2D bl) {
        boolean noXoverlap = Math.abs(bl.getCenterX() - x) >= 2;
        boolean noYoverlap = Math.abs(bl.getCenterY() - y) >= 2;
        boolean noOverlap = noXoverlap || noYoverlap;
        blockPlacedOK = blockPlacedOK && noOverlap;
        return blockPlacedOK;
    }
    
    public static String toString(Collection<BlockColor> colors) {
        StringBuilder builder = new StringBuilder();
        for (BlockColor c : colors) {
            builder.append(" ");
            builder.append(c.getLetter());
        }
        return builder.toString();
    }

}
