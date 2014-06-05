package nl.tudelft.bw4t.robots;

import eis.exceptions.EntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.blocks.EPartner;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.handicap.HandicapInterface;
import nl.tudelft.bw4t.map.view.Entity;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.util.ZoneLocator;
import nl.tudelft.bw4t.zone.Corridor;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;
import nl.tudelft.bw4t.zone.Zone;
import org.apache.log4j.Logger;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialException;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

/**
 * Represents a robot in the BW4T environment.
 * 
 * @author Lennard de Rijk
 */
public class Robot extends BoundedMoveableObject implements HandicapInterface {
	
	/**
	 * logger used to log in console and in logfile
	 */
	private static final Logger LOGGER = Logger.getLogger(Robot.class);

    /**
     * The distance which it can move per tick. This should never be larger than the door width because that might cause
     * the bot to attempt to jump over a door (which will fail).
     */
    public static final double MAX_MOVE_DISTANCE = .5;
    /**
     * When we are this close or closer, we are effectively at the target position.
     */
    public static final double MIN_MOVE_DISTANCE = .001;
    /** The distance which it can reach with its arm to pick up a block. */
    public static final double ARM_DISTANCE = 1;
    
    /** The width and height of the robot */
    private int size = 2;
    /** The name of the robot */
    private final String name;

    /** The location to which the robot wants to travel. */
    private NdPoint targetLocation;
    /** The list of blocks the robot is holding. */
    private final List<Block> holding;
    /** The max. amount of blocks a robot can hold, default is 1. */
    private int grippercap = 3;
    /**
     * set to true if we have to cancel a motion due to a collision. A collision is caused by an attempt to move into or
     * out of a room
     */
    private boolean collided = false;

    /**
     * set to true when {@link #connect()} is called.
     */
    private boolean connected = false;

    /**
     * true if max 1 bot in a zone.
     */
    private boolean oneBotPerZone;

    /**
     * 
     * a robot has a battery a battery has a power value of how much the capacity should increment or decrement.
     */
    private Battery battery;

    /**
     * 
     * Saves the robots handicap.
     */
    private ArrayList<String> handicapsList;

    /**
     * AgentRecord object for this Robot, needed for logging
     */
    private AgentRecord agentRecord;

    /**
     * Creates a new robot.
     * 
     * @param pname
     *            The "human-friendly" name of the robot.
     * @param space
     *            The space in which the robot operates.
     * @param context
     *            The context in which the robot operates.
     * @param poneBotPerZone
     *            true if max 1 bot in a zone
     * @param cap
     *            The holding capacity of the robot.
     * @param human
     *            True if the bot is human controlled.
     */
    public Robot(String pname, ContinuousSpace<Object> space, Context<Object> context, boolean poneBotPerZone, int cap) {
        super(space, context);

        this.name = pname;
        this.oneBotPerZone = poneBotPerZone;
        setSize(size, size);

        /**
         * This is where the battery value will be fetched from the Bot Store GUI.
         */
        this.battery = new Battery(Integer.MAX_VALUE, Integer.MAX_VALUE, 0);

        /**
         * Here the number of blocks a bot can hold is set.
         */
        this.grippercap = cap;
        this.holding = new ArrayList<Block>(grippercap);
        this.handicapsList = new ArrayList<String>();
        this.agentRecord = new AgentRecord(name);
    }

    /**
     * called when robot becomes connected and should now be injected in repast.
     */
    public void connect() {
    	BW4TEnvironment env = BW4TEnvironment.getInstance();
        HashSet<String> associatedAgents = null;
		try {
			associatedAgents = env.getAssociatedAgents(this.getName());
		} catch (EntityException e) {
			LOGGER.error("Unable to get the associated agent for this entity", e);
		}
        connected = true;
    }

    /**
     * called when robot should be disconnected.
     */
    public void disconnect() {
        connected = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Robot) {
            return super.equals(obj);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * @return The name of the robot.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The block the robot is holding, null if holding none.
     */
    public List<Block> isHolding() {
        return holding;
    }

    /**
     * @return The targetlocation of the robot
     */
    public synchronized NdPoint getTargetLocation() {
        return targetLocation;
    }

    /**
     * Sets the location to which the robot should move. This also clears the {@link #collided} flag.
     * 
     * @param ptargetLocation
     *            the location to move to.
     */
    public synchronized void setTargetLocation(NdPoint ptargetLocation) {
        this.targetLocation = ptargetLocation;
        collided = false;
    }

    /**
     * Check if robot can pick up a block.
     * 
     * @param b
     *            the block to check
     * @return true if the block is within reach and if the bot isn't holding a block already.
     */
    @Override
    public boolean canPickUp(Block b) {
        return (distanceTo(b.getLocation()) <= ARM_DISTANCE) && b.isFree() && (holding.size() < grippercap);
    }

    /**
     * Pick up a block
     * 
     * @param b
     *            , the block to pick up
     * TODO: Do we want the robot to drop a block if he already is holding one or just ignore the block?
     * 		Shouldn't it then throw an exception when the robot tries to pick up something it cannot?
     */
    public void pickUp(Block b) {
    	if (this.grippercap == 1) {
    		drop();
    	}
        holding.add(b);
        b.setHeldBy(this);
        b.removeFromContext();
    }

    /**
     * Get current zone that the robot is in.
     * 
     * @return zone the bot is in.
     */
    public Zone getZone() {
        return ZoneLocator.getZoneAt(getLocation());
    }

    /**
     * Drops the block the robot is holding on the current location. TODO: What if multiple blocks dropped at same spot?
     */
    public void drop() {
        if (!holding.isEmpty()) {
            // First check if dropped in dropzone, then it won't need to be
            // added to the context again
            DropZone dropZone = (DropZone) context.getObjects(DropZone.class).get(0);
            Block b = holding.get(0);
            if (!dropZone.dropped(b, this)) {
                // bot was not in the dropzone.. Are we in a room?
                Zone ourzone = getZone();
                if (ourzone instanceof Room) {
                    // We are in a room so can drop the block
                    b.setHeldBy(null);
                    b.addToContext();
                    // Slightly jitter the location where the box is
                    // dropped
                    double x = ourzone.getLocation().getX();
                    double y = ourzone.getLocation().getY();
                    b.moveTo(RandomHelper.nextDoubleFromTo(x - 5, x + 5), RandomHelper.nextDoubleFromTo(y - 5, y + 5));
                    holding.remove(0);
                    return;

                }
            }
            holding.remove(0);
        }
    }

    /**
     * A method for dropping multiple blocks at once.
     * 
     * @param amount
     *            The amount of blocks that have to be dropped, needs to be less than the amount of blocks in the list.
     */
    public void drop(int amount) {
        assert amount <= holding.size();
        for (int i = 0; i < amount; i++) {
            drop();
        }
    }

    @Override
    public void moveTo(double x, double y) {
        // the check for getLocation is to always allow the initial moveTo
        if (getLocation() != null) {
            switch (getMoveType(x, y)) {
            case ENTER_CORRIDOR:
            case ENTERING_FREESPACE:
            case ENTERING_ROOM:
                agentRecord.addEnteredRoom();
                break;
            case SAME_AREA:
            	break;
            case HIT_CLOSED_DOOR:
            case HIT_WALL:
            case HIT_OCCUPIED_ZONE:
                throw new SpatialException("robot bumped: " + getMoveType(x, y));
            default:
                throw new IllegalStateException();
            }
        }
        super.moveTo(x, y);
    }

    public enum MoveType {
        /**
         * start and end point are in same room/corridor
         */
        SAME_AREA,
        /**
         * move is attempting to go through a wall
         */
        HIT_WALL,
        /**
         * Entering room (through open door).
         */
        ENTERING_ROOM,
        /**
         * bumped into closed door
         */
        HIT_CLOSED_DOOR,
        /**
         * bumped into an occupied zone
         */
        HIT_OCCUPIED_ZONE,
        /**
         * Going from a Zone into free unzoned space.
         */
        ENTERING_FREESPACE,
        /**
         * Entering a corridor
         * */
        ENTER_CORRIDOR;

        /**
         * Merge the move type if multiple zones are entered at once. The result is the 'worst' event that happens
         * 
         * @param other
         * @return MoveType
         */
        public MoveType merge(MoveType other) {
            if (this.isHit())
                return this;
            if (other.isHit())
                return other;
            if (this == SAME_AREA || this == ENTERING_FREESPACE)
                return other;
            return this;
        }

        public boolean isHit() {
            return this == HIT_CLOSED_DOOR || this == HIT_WALL || this == HIT_OCCUPIED_ZONE;
        }
    }

    /**
     * Check motion type for robot to move to <endx, endy>. The {@link #MoveType} gives the actual type / possibility of
     * the move, plus the details why it is (not) possible.
     * 
     * @param end
     *            is x position of target
     * @param endy
     *            is y position of target
     */

    public MoveType getMoveType(double endx, double endy) {
        double startx = getLocation().getX();
        double starty = getLocation().getY();
        Door door = getCurrentDoor(startx, starty);

        /**
         * if start and end are both in the same 'room' (outside is the 'null' room). Then free walk always possible.
         */
        List<Zone> endzones = ZoneLocator.getZonesAt(endx, endy);
        Zone startzone = ZoneLocator.getZoneAt(startx, starty);

        /**
         * If there is overlap in zones, ALL zones must be clear. Note, entering a free space is always ok.
         */
        MoveType result = MoveType.ENTERING_FREESPACE;

        for (Zone endzone : endzones) {
            result = result.merge(checkZoneAccess(startzone, endzone, door));
        }
        return result;

    }

    /**
     * check if we can access endzone from startzone.
     * 
     * @param startzone
     * @param endzone
     * @return
     */
    @Override
    public MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
        if (startzone == endzone) {
            return MoveType.SAME_AREA;
        }

        /**
         * A zone switch is attempted as either startzone or endzone is not null.
         */
        /**
         * If one of the sides is a room, we require a door
         */

        if (endzone instanceof Room) {
            /**
             * Start position must be ON a door to enable the switch. Check if bot is going INTO the room, and if so, if
             * the door is open.
             */
            if (door == null) {
                return MoveType.HIT_WALL;
            }
            /**
             * If there is a door, we just check that other end is accesible
             */
            if (endzone.containsMeOrNothing(this)) {
                return MoveType.ENTERING_ROOM;
            }

            return MoveType.HIT_CLOSED_DOOR;
        }

        /**
         * Both sides are not a room. Check if target accesible
         */
        else if (endzone instanceof Corridor) {
            if (!oneBotPerZone || endzone.containsMeOrNothing(this)) {
                return MoveType.ENTER_CORRIDOR;
            }
            return MoveType.HIT_OCCUPIED_ZONE;
        }
        return MoveType.ENTERING_FREESPACE;
    }

    /**
     * get door at a given position. Note that you can be in a door and at the same time in a room. This is because
     * rooms and doors partially overlap usually.
     * 
     * @param x
     *            is x coord of position
     * @param y
     *            is y coord of position
     * @return Door or null if not on a door
     */
    public Door getCurrentDoor(double x, double y) {
        for (Object o : context.getObjects(Door.class)) {
            Door door = (Door) o;
            if (door.getBoundingBox().contains(x, y)) {
                return door;
            }
        }
        return null;
    }

    /**
     * get room at a given position. CHECK maybe move this to RoomLocator?
     * 
     * @param x
     *            is x coord of position
     * @param y
     *            is y coord of position
     * @return Room or null if not inside a room
     */
    public Room getCurrentRoom(double x, double y) {
        for (Object o : context.getObjects(Room.class)) {
            Room room = (Room) o;
            if (room.getBoundingBox().contains(x, y)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Moves the robot by displacing it for the given amount. If the robot collides with something, the movement target
     * is cancelled to avoid continuous bumping.
     * 
     * @param x
     *            the displacement in the x-dimension.
     * @param y
     *            the displacement in the y-dimension.
     */
    public void moveByDisplacement(double x, double y) {
        moveTo(getLocation().getX() + x, getLocation().getY() + y);
    }

    @Override
    @ScheduledMethod(start = 0, duration = 0, interval = 1)
    public synchronized void move() {
        if (targetLocation != null) {
            // Calculate the distance that the robot is allowed to move.
            double distance = distanceTo(targetLocation);
            if (distance < MIN_MOVE_DISTANCE) {
                // we're there
                stopRobot();
            }
            else {
                double movingDistance = Math.min(distance, MAX_MOVE_DISTANCE);

                // Angle at which to move
                double angle = SpatialMath.calcAngleFor2DMovement(space, getLocation(), targetLocation);

                // The displacement of the robot
                double[] displacement = SpatialMath.getDisplacement(2, 0, movingDistance, angle);

                try {
                    // Move the robot to the new position using the displacement
                    moveByDisplacement(displacement[0], displacement[1]);
                    agentRecord.setStartedMoving();

                    /**
                     * The robot's battery discharges when it moves.
                     */
                    this.battery.discharge();
                } catch (SpatialException e) {
                    collided = true;
                    stopRobot();
                }
            }
        }
    }

    /**
     * Stop the motion of the robot. Effectively sets the target location to null. You can override this to catch this
     * event.
     */
    public synchronized void stopRobot() {
        this.targetLocation = null;
        agentRecord.setStoppedMoving();
    }

    /**
     * clear the collision flag. You can use this to reset the flag after you took notice of the collision.
     * 
     */
    public void clearCollided() {
        collided = false;
    }

    /**
     * Check if robot collided.
     * 
     * @return true if robot collided with wall, else false.
     */
    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public boolean isConnected() {
        return connected;
    }

    /**
     * @return This method returns the battery percentage.
     */
    public int getBatteryPercentage() {
        return this.battery.getPercentage();
    }

    /**
     * 
     * @return discharge rate of battery.
     */
    public double getDischargeRate() {
        return this.battery.getDischargeRate();
    }

    /**
     * The robot is in a charging zone. The robot charges.
     */
    public void recharge() {
        if ("chargingzone".equals(this.getZone().getName())) {
            this.battery.recharge();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParent(HandicapInterface hI) {
        // does not do anything because Robot is the super parent
    }

    /**
     * get the parent, returns null because Robot is the super parent
     * 
     * @return null
     */
    @Override
    public HandicapInterface getParent() {
        return null;
    }

    /**
     * returns this Robot
     * 
     * @return this
     */
    @Override
    public Robot getSuperParent() {
        return this;
    }

    public ArrayList<String> getHandicapsList() {
        return this.handicapsList;
    }

    public void setHandicapsMap(ArrayList<String> handicapsList) {
        this.handicapsList = handicapsList;
    }

    /**
     * Sets the size of a robot to a certain integer
     * 
     * @param s
     */
    public void setSize(int s) {
        this.size = s;
        setSize(s, s);
    }

    /**
     * Gets the size of the robot
     * 
     * @return size
     */
    public int getSize() {
        return this.size;
    }

    @Override
    public int getGripperCapacity() {
        return grippercap;
    }
    
    public void setGripperCapacity(int newcap) {
    	this.grippercap = newcap;
    }

    public Battery getBattery() {
        return this.battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public boolean getOneBotPerZone() {
        return this.oneBotPerZone;
    }

    public boolean getConnected() {
        return this.connected;
    }

    public boolean getCollided() {
        return this.collided;
    }

    /**
     * gets AgentRecord
     * 
     * @return AgentRecord
     */
    public AgentRecord getAgentRecord() {
        return agentRecord;

    }

    public Entity getView() {
        Collection<nl.tudelft.bw4t.map.view.Block> bs = new HashSet<>();
        for (Block block : holding) {
            bs.add(block.getView());
        }
        NdPoint loc = getSpace().getLocation(this);
        return new Entity(getName(), loc.getX(), loc.getY(), bs);
    }
    
    public boolean isOneBotPerZone() {
        return oneBotPerZone;
    }

	@Override
	public double getSpeedMod() {
		return 1;
	}

	/**
	 * Only a human has the possibility to use these functions. 
	 */
	@Override
	public boolean canPickUpEPartner(EPartner eP) {
		return false;
	}
	@Override
	public void pickUpEPartner(EPartner eP) {
	}
	@Override
	public void dropEPartner() {
	}
	@Override
	public boolean isHoldingEPartner() {
		return false;
	}
	@Override
	public EPartner getEPartner() {
		return null;
	}
	
	public boolean isHuman() {
		return handicapsList.contains("Human");
	}
}
