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
import nl.tudelft.bw4t.handicap.IRobot;
import nl.tudelft.bw4t.map.view.ViewEntity;
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
 */
public abstract class AbstractRobot extends BoundedMoveableObject implements IRobot {
	private static final Logger LOGGER = Logger.getLogger(AbstractRobot.class);

    /**
     * AgentRecord object for this Robot, needed for logging. It needs to be set up at the initialization of the object,
     * because we otherwise get an Exception when adding Robots after we have added the rooms to the environment.
     */
	AgentRecord agentRecord = new AgentRecord("");

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
    
    /** The name of the robot. */
    private final String name;

    /** The width and height of the robot. */
	private int size = 2;
	
	/** The speed modifier of the robot, default being 1. */
    private double speedMod = 1;

	/** The max. amount of blocks a robot can hold, default is 1. */
	private int grippercap = 1;

	/**
	 * a robot has a battery a battery has a power value of how much the capacity should increment or decrement.
	 */
	private Battery battery;

	/**
	 * 
	 * Saves the robots handicap.
	 */
	private ArrayList<String> handicapsList;

	/** The list of blocks the robot is holding. */
    private final List<Block> holding;
    /**
     * set to true if we have to cancel a motion due to a collision. A collision is caused by an attempt to move into or
     * out of a room
     */
    private boolean collided = false;

    /** The location to which the robot wants to travel. */
	private NdPoint targetLocation;

	/**
     * set to true when {@link #connect()} is called.
     */
    private boolean connected = false;

    /**
     * true if max 1 bot in a zone.
     */
    private boolean oneBotPerZone;
    
    private IRobot topMostHandicap = this;

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
     */
    public AbstractRobot(String pname, ContinuousSpace<Object> space, Context<Object> context, boolean poneBotPerZone, int cap) {
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

	public void setTopMostHandicap(IRobot topMostHandicap) {
		this.topMostHandicap = topMostHandicap;
	}

	@Override
	public String getName() {
	    return name;
	}

    @Override
    public void connect() {
    	BW4TEnvironment env = BW4TEnvironment.getInstance();
        HashSet<String> associatedAgents = null;
		try {
			associatedAgents = env.getAssociatedAgents(this.getName());
		} catch (EntityException e) {
			LOGGER.error("Unable to get the associated agent for this entity", e);
		}
        connected = true;
        String agent = "no agents";
        if (associatedAgents != null && !associatedAgents.isEmpty()) {
            agent = associatedAgents.iterator().next();
        }
        agentRecord = new AgentRecord(agent);

    }

    @Override
    public void disconnect() {
        connected = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractRobot) {
            return super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public List<Block> isHolding() {
        return holding;
    }

    @Override
    public synchronized NdPoint getTargetLocation() {
        return targetLocation;
    }

    @Override
    public synchronized void setTargetLocation(NdPoint ptargetLocation) {
        this.targetLocation = ptargetLocation;
        collided = false;
    }

    @Override
    public boolean canPickUp(BoundedMoveableObject obj) {
        if(obj instanceof Block) {
            Block b = (Block) obj;
            LOGGER.info("gripcap" + grippercap);
            return (distanceTo(obj.getLocation()) <= ARM_DISTANCE) && b.isFree() && (holding.size() < grippercap);
        }
        return false;
    }

    @Override
    public void pickUp(Block b) {
        holding.add(b);
        b.setHeldBy(this);
        b.removeFromContext();
        LOGGER.info("blocks held: " + holding.size());
    }

    @Override
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

    @Override
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

    @Override
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
            result = result.merge(topMostHandicap.checkZoneAccess(startzone, endzone, door));
        }
        return result;

    }

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
        

        /**
         * Both sides are not a room. Check if target accesible
         */
        } else if (endzone instanceof Corridor) {
            if (!oneBotPerZone || endzone.containsMeOrNothing(this)) {
                return MoveType.ENTER_CORRIDOR;
            }
            return MoveType.HIT_OCCUPIED_ZONE;
        }
        return MoveType.ENTERING_FREESPACE;
    }

    @Override
    public Door getCurrentDoor(double x, double y) {
        for (Object o : context.getObjects(Door.class)) {
            Door door = (Door) o;
            if (door.getBoundingBox().contains(x, y)) {
                return door;
            }
        }
        return null;
    }

    @Override
    public Room getCurrentRoom(double x, double y) {
        for (Object o : context.getObjects(Room.class)) {
            Room room = (Room) o;
            if (room.getBoundingBox().contains(x, y)) {
                return room;
            }
        }
        return null;
    }

	@Override
	public Zone getZone() {
	    return ZoneLocator.getZoneAt(getLocation());
	}


    @Override
    public void moveByDisplacement(double x, double y) {
        moveTo(getLocation().getX() + x, getLocation().getY() + y);
    }

    @Override
    @ScheduledMethod(start = 0, duration = 0, interval = 1)
    public synchronized void move() {
    	if (battery.getCurrentCapacity() > 0) {
		    if (targetLocation != null) {
		        // Calculate the distance that the robot is allowed to move.
		        double distance = distanceTo(targetLocation);
		        if (distance < MIN_MOVE_DISTANCE) {
		            // we're there
		            stopRobot();
		        }
		        else {
		            double movingDistance = Math.min(distance, MAX_MOVE_DISTANCE * speedMod);
		
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
		                LOGGER.info("The current battery level is: " + this.battery.getCurrentCapacity());
		                LOGGER.info("the robot is human: " + this.isHuman());
		                
		                if (topMostHandicap.isHuman() && topMostHandicap.isHoldingEPartner()) {
		                	NdPoint location = topMostHandicap.getLocation();
		                	topMostHandicap.getEPartner().moveTo(location.getX() + 1, location.getY() + 1);
		                	LOGGER.info("e-Partner on the move");
		                }
		            } catch (SpatialException e) {
		                collided = true;
		                stopRobot();
		            }
		        }
		    }
    	} else {
    		stopRobot();
    	}
    }


    @Override
    public synchronized void stopRobot() {
        this.targetLocation = null;
        agentRecord.setStoppedMoving();
    }

    @Override
	public boolean isCollided() {
	    return this.collided;
	}

	@Override
    public void setCollided(boolean collided) {
        this.collided = collided;
    }


	@Override
	public void clearCollided() {
	    collided = false;
	}

	@Override
	public boolean isConnected() {
	    return this.connected;
	}

	@Override
	public boolean isOneBotPerZone() {
	    return this.oneBotPerZone;
	}


	@Override
	public int getSize() {
	    return this.size;
	}


    /**
     * Sets the size of a robot to a certain integer
     * 
     * @param s int
     */
    @Override
    public void setSize(int s) {
        this.size = s;
        setSize(s, s);
    }

    @Override
    public ViewEntity getView() {
        Collection<nl.tudelft.bw4t.map.view.ViewBlock> bs = new HashSet<>();
        for (Block block : holding) {
            bs.add(block.getView());
        }
        NdPoint loc = getSpace().getLocation(this);
        return new ViewEntity(getId(), getName(), loc.getX(), loc.getY(), bs, getSize());
    }
    
	@Override
	public AgentRecord getAgentRecord() {
	    return agentRecord;
	
	}

	@Override
	public Battery getBattery() {
	    return this.battery;
	}

	@Override
	public void setBattery(Battery battery) {
	    this.battery = battery;
	}

	@Override
	public int getBatteryPercentage() {
	    return this.battery.getPercentage();
	}

	@Override
	public double getDischargeRate() {
	    return this.battery.getDischargeRate();
	}

	@Override
	public void recharge() {
	    if ("chargingzone".equals(this.getZone().getName())) {
	        this.battery.recharge();
	    }
	}
	@Override
	public IRobot getParent() {
	    return null;
	}

	@Override
	public void setParent(IRobot hI) {
	    // does not do anything because Robot is the super parent
	}

	@Override
	public ArrayList<String> getHandicapsList() {
	    return this.handicapsList;
	}

	@Override
	public int getGripperCapacity() {
	    return grippercap;
	}

	@Override
	public void setGripperCapacity(int newcap) {
		this.grippercap = newcap;
	}

	@Override
	public double getSpeedMod() {
		return speedMod;
	}
	
	@Override
	public void setSpeedMod(double speedMod) {
		this.speedMod = speedMod;
	}

	@Override
	public boolean isHuman() {
		return handicapsList.contains("Human");
	}

	@Override
	public EPartner getEPartner() {
		return null;
	}

	@Override
	public boolean isHoldingEPartner() {
		return false;
	}

	@Override
	public void pickUpEPartner(EPartner eP) {
	}

	@Override
	public void dropEPartner() {
	}
	
	@Override
    public double distanceTo(BoundedMoveableObject b) {
        return super.distanceTo(b);
    }

	@Override
	public AbstractRobot getSuperParent() {
	    return this;
	}
}
