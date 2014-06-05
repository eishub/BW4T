package nl.tudelft.bw4t.eis;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.blocks.EPartner;
import nl.tudelft.bw4t.eis.translators.BlockWithColorTranslator;
import nl.tudelft.bw4t.eis.translators.BoundedMovableObjectTranslator;
import nl.tudelft.bw4t.eis.translators.ColorTranslator;
import nl.tudelft.bw4t.eis.translators.EPartnerTranslator;
import nl.tudelft.bw4t.eis.translators.ObjectInformationTranslator;
import nl.tudelft.bw4t.eis.translators.PointTranslator;
import nl.tudelft.bw4t.eis.translators.ZoneTranslator;
import nl.tudelft.bw4t.handicap.IRobot;
import nl.tudelft.bw4t.robots.NavigatingRobot;
import nl.tudelft.bw4t.robots.AbstractRobot;
import nl.tudelft.bw4t.server.RobotEntityInt;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.util.RoomLocator;
import nl.tudelft.bw4t.util.ZoneLocator;
import nl.tudelft.bw4t.zone.BlocksRoom;
import nl.tudelft.bw4t.zone.Corridor;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;
import nl.tudelft.bw4t.zone.Zone;

import org.apache.log4j.Logger;
import org.omg.CORBA.Environment;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.util.collections.IndexedIterable;
import eis.eis2java.annotation.AsAction;
import eis.eis2java.annotation.AsPercept;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Filter;
import eis.eis2java.translation.Filter.Type;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Parameter;

/**
 * EIS entity for a {@link AbstractRobot}.
 * 
 * @author Lennard de Rijk
 * @modified W.Pasman #2318 #2291 "lock" robot position at start of perception cycle.
 * @modified W.Pasman #2326 robots are injected into EIS only after their entity has been connected with an agent.
 */
public class RobotEntity implements RobotEntityInt {

    static {
        // Register our translators.
        Translator translator = Translator.getInstance();
        translator.registerJava2ParameterTranslator(new BlockWithColorTranslator());
        translator.registerJava2ParameterTranslator(new BoundedMovableObjectTranslator());
        translator.registerJava2ParameterTranslator(new ZoneTranslator());
        translator.registerJava2ParameterTranslator(new PointTranslator());
        translator.registerJava2ParameterTranslator(new ObjectInformationTranslator());
        translator.registerJava2ParameterTranslator(new ColorTranslator());
        translator.registerJava2ParameterTranslator(new EPartnerTranslator());
    }

    /**
     * The log4j logger, logs to the console.
     */
    private static final Logger LOGGER = Logger.getLogger(RobotEntity.class);

    private final IRobot ourRobot;
    private final Context<Object> context;

    /**
     * Here we store data that needs to be locked for a perception cycle. See {@link #initializePerceptionCycle()}.
     */
    private Point2D ourRobotLocation;
    private Point2D spawnLocation;
    private Room ourRobotRoom;

    /**
     * each item in messages is a list with two items: the sender and the messagetext.
     */
    private List<ArrayList<String>> messages = new ArrayList<ArrayList<String>>();

    /**
     * Creates a new {@link RobotEntity} that can be launched by an EIS compatible {@link Environment}.
     * 
     * @param robot
     *            The {@link AbstractRobot} that this entity can put up for controlling in EIS.
     */
    public RobotEntity(IRobot robot) {
        this.ourRobot = robot;
        this.context = ourRobot.getContext();
    }

    /**
     * Connect robot to repast (to be called when an agent is connected to this entity)
     */
    @Override
    public void connect() {
        spawnLocation = new Point2D.Double(ourRobot.getLocation().getX(), ourRobot.getLocation().getY());
        ourRobot.connect();
    }
    
    /**
     * Disconnects the robot from repast.
     */
    public void disconnect(){
        ourRobot.disconnect();
        reset();
    }
    
    /**
     * Reset the robot's location and should set it to its default spawn state.
     */
    public void reset(){
        ourRobot.drop();
        ourRobot.moveTo(this.spawnLocation.getX(), this.spawnLocation.getY());
    }

    /**
     * This function should be called before perception cycle is started, so that we can lock the relevant data from the
     * environment.
     */
    @Override
    public void initializePerceptionCycle() {
        ourRobotLocation = new Point2D.Double(ourRobot.getLocation().getX(), ourRobot.getLocation().getY());
        ourRobotRoom = RoomLocator.getRoomAt(ourRobotLocation.getX(), ourRobotLocation.getY());
    }

    /**
     * @return All blocks that are visible to the robot, excluding the one the robot is holding
     */
    private Set<Block> getVisibleBlocks() {
        Set<Block> blocks = new HashSet<Block>();

        if (ourRobotRoom != null) {
            // Add all blocks in the same room as the robot.
            Iterable<Object> allBlocks = context.getObjects(Block.class);
            for (Object b : allBlocks) {
                Block aBlock = (Block) b;
                Double p = new Point2D.Double(aBlock.getLocation().getX(), aBlock.getLocation().getY());
                if (ourRobotRoom.getBoundingBox().contains(p)) {
                    blocks.add(aBlock);
                }
            }
        }

        return blocks;
    }
    
    /**
     * Percepts for the sizes of all the robots.
     */
    @AsPercept(name = "robotSize", multiplePercepts = true, filter = Filter.Type.ON_CHANGE)
    public List<ObjectInformation> getSizes() {
        List<ObjectInformation> sizes = new ArrayList<ObjectInformation>();
        
        IndexedIterable<Object> allRobots = context.getObjects(IRobot.class);
        
        for (Object obj : allRobots) {
            IRobot robot = (IRobot) obj;
            sizes.add(new ObjectInformation(robot.getSize(), robot.getSize(), robot.getId()));
        }
        
        return sizes;       
    }

    /**
     * Percepts for the location of rooms and the dropzone and the blocks Send on change
     */
    @AsPercept(name = "position", multiplePercepts = true, filter = Filter.Type.ON_CHANGE)
    public List<ObjectInformation> getLocations() {
        List<ObjectInformation> objects = new ArrayList<ObjectInformation>();

        // Add the dropzone
        DropZone dropZone = (DropZone) context.getObjects(DropZone.class).get(0);
        objects.add(new ObjectInformation(dropZone));

        // Add rooms
        IndexedIterable<Object> allRooms = context.getObjects(BlocksRoom.class);
        for (Object object : allRooms) {
            Room r = (Room) object;
            objects.add(new ObjectInformation(r));
        }

        // Add blocks
        for (Block block : getVisibleBlocks()) {
            objects.add(new ObjectInformation(block));
        }
        
        // Add EPartners
        IndexedIterable<Object> allEPartners = context.getObjects(EPartner.class);
        for (Object object : allEPartners) {
            EPartner ep = (EPartner) object;
            objects.add(new ObjectInformation(ep));
        }

        // #2830 add robots own position
        objects.add(new ObjectInformation(ourRobotLocation.getX(), ourRobotLocation.getY(), ourRobot.getId()));

        return objects;
    }

    /**
     * Percept for navpoints the robot is at. Send on change. If robot is in a {@link Zone}, that zone name is returned.
     * If not, the nearest {@link Corridor} name is returned.
     * 
     * @return a list of blockID
     * @throws PerceiveException
     */
    @AsPercept(name = "at", multiplePercepts = false, filter = Filter.Type.ON_CHANGE)
    public String getAt() throws PerceiveException {

        Zone navpt = ZoneLocator.getNearestZone(ourRobot.getLocation());
        if (navpt == null) {
            throw new PerceiveException(
                    "perceiving 'at' percept failed, because map has no suitable navpoint for position "
                            + ourRobotLocation);
        }
        return navpt.getName();
    }

    /**
     * Percept for blocks the robot is at Send on change
     * 
     * @return a list of blockID
     */
    @AsPercept(name = "atBlock", multiplePercepts = true, filter = Filter.Type.ON_CHANGE_NEG)
    public List<Long> getAtBlock() {
        List<Long> result = new ArrayList<Long>();
        IndexedIterable<Object> allBlocks = context.getObjects(Block.class);
        for (Object object : allBlocks) {
            Block b = (Block) object;
            if (ourRobot.distanceTo(b) <= 1 && ourRobot.isHolding() == null || !ourRobot.isHolding().contains(b)) {
                result.add(b.getId());
                return result;
            }
        }
        return result;
    }

    /**
     * Percept for the room that the player is in, null if not in a room. Send on change
     */
    @AsPercept(name = "in", multiplePercepts = false, filter = Filter.Type.ON_CHANGE_NEG)
    public String getRoom() {

        if (ourRobotRoom == null) {
            return null;
        }
        return ourRobotRoom.getName();
    }

    /**
     * Percept for the location of this robot Send on change
     */
    @AsPercept(name = "location", multiplePercepts = false, filter = Filter.Type.ON_CHANGE)
    public Point2D getLocation() {
        return new Point2D.Double(ourRobotLocation.getX(), ourRobotLocation.getY());
    }

    /**
     * Percept for the places in the world. Send at the beginning
     */
    @AsPercept(name = "place", multiplePercepts = true, filter = Filter.Type.ONCE)
    public List<String> getRooms() {
        List<String> places = new ArrayList<String>();
        for (Object o : context.getObjects(Zone.class)) {
            Zone zone = (Zone) o;
            places.add(zone.getName());
        }

        return places;
    }

    /**
     * Percept of the id of the robot Send at the beginning
     */
    @AsPercept(name = "robot", filter = Filter.Type.ONCE)
    public long getRobot() {
        return ourRobot.getId();
    }

    /**
     * The names of other players Send at the beginning. We are assuming that each agent controls one entity.
     * 
     * @return the names of the other players
     */
    @AsPercept(name = "player", multiplePercepts = true, filter = Filter.Type.ON_CHANGE_NEG)
    public List<String> getPlayers() {
        BW4TEnvironment env = BW4TEnvironment.getInstance();
        List<String> agents = env.getAgents();
        List<String> result = new ArrayList<String>();

        for (String agt : agents) {
            try {
                Set<String> entities = env.getAssociatedEntities(agt);
                if (!entities.contains(ourRobot.getName())) {
                    result.addAll(env.getAssociatedEntities(agt));
                }
            } catch (AgentException e) {
                LOGGER.error("Ignoring an Agent's percept problem", e);
            }
        }
        return result;
    }

    /**
     * The names of other players Send at the beginning
     * 
     * @return the names of the other players
     */
    @AsPercept(name = "ownName", multiplePercepts = false, filter = Filter.Type.ONCE)
    public String getOwnName() {
        return ourRobot.getName();
    }

    /**
     * Percept for the colors of all blocks that are visible. Send always. They are visible only when inside a room.
     */
    @AsPercept(name = "color", multiplePercepts = true, filter = Filter.Type.ALWAYS)
    public List<BlockColor> getColor() {
        Set<Block> blocks = getVisibleBlocks();

        List<BlockColor> colors = new ArrayList<BlockColor>();
        for (Block block : blocks) {
            colors.add(new BlockColor(block));
        }

        return colors;
    }

    /**
     * Percept if the robot is holding something. Send if it becomes true, and
     * send negated if it becomes false again.
     */
    @AsPercept(name = "holding", multiplePercepts = true, filter = Filter.Type.ON_CHANGE_NEG)
    public List<Block> getHolding() {
        return ourRobot.isHolding();
    }

    /**
     * The sequence in which the blocks should be returned. Send at the beginning
     */
    @AsPercept(name = "sequence", filter = Filter.Type.ONCE)
    public List<nl.tudelft.bw4t.map.BlockColor> getSequence() {
        DropZone dropZone = (DropZone) context.getObjects(DropZone.class).get(0);
        return dropZone.getSequence();
    }

    /**
     * The index of the block that needs to be brought back now. Send on change
     */
    @AsPercept(name = "sequenceIndex", filter = Filter.Type.ON_CHANGE)
    public Integer getSequenceIndex() {
        DropZone dropZone = (DropZone) context.getObjects(DropZone.class).get(0);
        return dropZone.getSequenceIndex();
    }

    /**
     * occupied percept, tells which rooms are occupied by robot. Send if true and send negated if no longer true
     * 
     * @return list of occupied room IDs
     */
    @AsPercept(name = "occupied", multiplePercepts = true, filter = Filter.Type.ON_CHANGE_NEG)
    public List<String> getOccupied() {
        List<String> rooms = new ArrayList<String>();
        for (Object r : context.getObjects(Room.class)) {
            Room room = (Room) r;
            if (room.getOccupier() != null) {
                rooms.add(room.getName());
            }
        }

        return rooms;

    }

    /**
     * navpoint percept, tells which {@link Zone}s there are in the world and their neighbours. Send at the beginning
     * 
     * @return list of navpoints
     */
    @AsPercept(name = "zone", multiplePercepts = true, filter = Filter.Type.ONCE)
    public List<Zone> getNavPoints() {
        List<Zone> zones = new ArrayList<Zone>();
        for (Object o : context.getObjects(Zone.class)) {
            Zone zone = (Zone) o;
            zones.add(zone);
        }

        return zones;
    }

    /**
     * Returns all messages received by the player, Send on change
     * 
     * @return the messages that were received
     */
    @AsPercept(name = "message", multiplePercepts = true, filter = Filter.Type.ALWAYS)
    public List<ArrayList<String>> getMessages() {
        List<ArrayList<String>> msg = messages;
        messages = new ArrayList<ArrayList<String>>();
        return msg;
    }

    /**
     * The current state of the robot. See {@link NavigatingRobot#State}. Send on change
     */
    @AsPercept(name = "state", filter = Filter.Type.ON_CHANGE)
    public String getState() {
        return ourRobot.getState().toString().toLowerCase();
    }

    /**
     * Instructs the robot to move to the given location.
     * 
     * @param x
     *            The X coordinate of the location.
     * @param y
     *            The Y coordinate of the location.
     */
    @AsAction(name = "goTo")
    public void goTo(double x, double y) {
        ourRobot.setTargetLocation(new NdPoint(x, y));
    }

    /**
     * Instructs the robot to move to the given object. Only works if we are in the room containing the block - the
     * block must be visible.
     * 
     * @param targetid
     *            is the target object id. This can be the id of any object in the map (not a free x,y location)
     * @throws IllegalArgumentException
     *             if no block of given id visible
     */
    @AsAction(name = "goToBlock")
    public void goTo(long targetid) {
        BoundedMoveableObject target = null;
        for (Block b : getVisibleBlocks()) {
            if (b.getId() == targetid) {
                target = b;
            }
        }
        for (Object obj : context.getObjects(EPartner.class)) {
            EPartner ep = (EPartner) obj;
            if (targetid == ep.getId()) {
                target = ep;
            }
        }
        if (target == null) {
            throw new IllegalArgumentException("there is no block with id=" + targetid + " in the room");
        }

        ourRobot.setTarget(target);
    }

    /**
     * Instructs the robot to move to the given navpoint
     * 
     * @param navPoint
     *            , the navpoint the robot should move to
     */
    @AsAction(name = "goTo")
    public void goTo(String navPoint) {
        Zone target = ZoneLocator.getZone(navPoint);
        if (target == null) {
            throw new IllegalArgumentException("unknown place " + navPoint);
        }
        ourRobot.setTarget(target);
    }

    /**
     * Instructs the robot to pick up a block.
     * 
     * @param id
     *            The identifier of the block.
     */
    @AsAction(name = "pickUp")
    public void pickUp() {
        LOGGER.debug(String.format("%s is trying to pick up a block.", ourRobot.getName()));

        Block nearest = getClosest(Block.class);
        // Pick up closest block in canPickUp list
        if (nearest == null) {
        	LOGGER.debug(String.format("%s can not pickup any blocks.", ourRobot.getName()));
            return;
        }
    	LOGGER.debug(String.format("%s will pickup block %d.", ourRobot.getName(), nearest.getId()));
    	ourRobot.pickUp(nearest);
    }

    /**
     * Instructs the robot to send a message
     * 
     * @param message
     *            , the message that should be sent
     * @param receiver
     *            , the receiver of the message (can be all or the id of another robot
     * @throws ActException
     *             if the action fails
     */
    @AsAction(name = "sendMessage")
    public void sendMessage(String receiver, String message) throws ActException {
        ourRobot.getAgentRecord().addSentMessage();

        // Translate the message into parameters
        Parameter[] parameters = new Parameter[2];
        try {
            parameters[0] = Translator.getInstance().translate2Parameter(ourRobot.getName())[0];
            parameters[1] = Translator.getInstance().translate2Parameter(message)[0];
        } catch (TranslationException e) {
            throw new ActException("translating of message failed:" + message, e);
        }

        // Send to all other entities (except self)
        if ("all".equals(receiver)) {
            for (String entity : BW4TEnvironment.getInstance().getEntities()) {
                BW4TEnvironment.getInstance().performClientAction(entity, new Action("receiveMessage", parameters));
            }
            // Send to a single entity
        } else {
            BW4TEnvironment.getInstance().performClientAction(receiver, new Action("receiveMessage", parameters));
        }
    }

    /**
     * Instructs the robot to receive a certain message, should only be used internally in the server environment
     * 
     * @param message
     *            , the message to be received
     * @param sender
     *            , the sender of the message
     */
    @AsAction(name = "receiveMessage")
    public void receiveMessage(String sender, String message) {
        // Add message to messageArray
        List<String> messageArray = new ArrayList<String>();
        messageArray.add(sender);
        messageArray.add(message);

        messages.add((ArrayList<String>) messageArray);
    }

    /**
     * Instructs the robot to drop the block it is holding.
     */
    @AsAction(name = "putDown")
    public void putDown() {
        ourRobot.drop();
    }

    /**
     * Hack. Seems we need to get context through an entity... #1955
     * 
     * @return true if sequence is finished.
     */
    @AsAction(name = "checkSequenceDone")
    public boolean checkSequenceDone() {
        DropZone dropzone = (DropZone) context.getObjects(DropZone.class).get(0);
        if (dropzone == null) {
            return false;
        }
        return dropzone.sequenceComplete();
    }
    
    /**
     * Give the robot a list of all the EPartners on the map.
     * @return the list of EPartners
     */
    @AsPercept(name = "epartner", multiplePercepts = true, filter = Type.ON_CHANGE)
    public List<EPartner> getEPartners() {
        if (!ourRobot.isHuman()) {
            return new ArrayList<>();
        }
        List<EPartner> eps = new ArrayList<>();
        for (Object obj : context.getObjects(EPartner.class)) {
            eps.add((EPartner) obj);
        }
        return eps;
    }
    
    /**
     * Only available for the human:
     * picks up the e-Partner. 
     */
    @AsAction(name = "pickUpEPartner")
    public void pickUpEPartner() {
    	if(ourRobot.isHuman()) {
	        LOGGER.debug(String.format("%s is trying to pick up an e-partner.", ourRobot.getName()));
	
	        EPartner nearest = getClosest(EPartner.class);
	        if (nearest == null) {
	            LOGGER.debug(String.format("%s can not pickup any e-partners.", ourRobot.getName()));
	            return;
	        }
	        LOGGER.debug(String.format("%s will pickup e-partner %d.", ourRobot.getName(), nearest.getId()));
	        ourRobot.pickUpEPartner(nearest);
    	}
    }
    
    /**
     * Only available for the human:
     * drops the e-Partner they are currently holding. 
     */
    @AsAction(name = "dropEPartner")
    public void dropEPartner() {
    	if (ourRobot.isHuman()) {
    		ourRobot.dropEPartner();
    	}
    }
    
    /**
     * Find the closest {@link BoundedMoveableObject} that can be picked up by the Robot.
     * @param type the type of {@link BoundedMoveableObject} we are looking for 
     * @return null if non were found, otherwise the closest
     */
    private <T extends BoundedMoveableObject> T getClosest(Class<T> type) {
        Iterable<Object> allBlocks = context.getObjects(type);
        T nearest = null;
        double nearestDistance = Integer.MAX_VALUE;
        for (Object o : allBlocks) {
            T aBlock = (T) o;
            double distance = ourRobot.distanceTo(aBlock);

            LOGGER.trace(String.format("%s is %f units away from %d.", ourRobot.getName(), distance, aBlock.getId()));
            if (ourRobot.canPickUp(aBlock)) {
                LOGGER.trace(String.format("%s can pick up %d checking distance.", ourRobot.getName(), aBlock.getId()));
                if (nearest == null || distance < nearestDistance) {
                    nearest = aBlock;
                    nearestDistance = distance;
                }
            }
        }
        return nearest;
    }

}
