package nl.tudelft.bw4t.server.model.robots;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import eis.exceptions.EntityException;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEntity;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.logging.BotLog;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.blocks.Block;
import nl.tudelft.bw4t.server.model.doors.Door;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;
import nl.tudelft.bw4t.server.model.zone.Corridor;
import nl.tudelft.bw4t.server.model.zone.DropZone;
import nl.tudelft.bw4t.server.model.zone.Room;
import nl.tudelft.bw4t.server.model.zone.Zone;
import nl.tudelft.bw4t.server.util.ZoneLocator;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialException;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.NdPoint;

/**
 * Represents a robot in the BW4T environment.
 */
public abstract class AbstractRobot extends BoundedMoveableObject implements
		IRobot {
	/**
	 * The logger which will be used.
	 */
	private static final Logger LOGGER = Logger.getLogger(AbstractRobot.class);

	/**
	 * AgentRecord object for this Robot, needed for logging. It needs to be set
	 * up at the initialization of the object, because we otherwise get an
	 * Exception when adding Robots after we have added the rooms to the
	 * environment.
	 */
	private AgentRecord agentRecord = new AgentRecord("");

	/**
	 * The distance which it can move per tick. This should never be larger than
	 * the door width because that might cause the bot to attempt to jump over a
	 * door (which will fail).
	 */
	public static final double MAX_MOVE_DISTANCE = .5;
	/**
	 * When we are this close or closer, we are effectively at the target
	 * position.
	 */
	public static final double MIN_MOVE_DISTANCE = .001;
	/** The distance which it can reach with its arm to pick up a block. */
	public static final double ARM_DISTANCE = 1;

	/**
	 * The amount of padding between bots moving around the map.
	 */
	public static final double MOVEMENT_CLEARANCE = 1;

	/** The name of the robot. */
	private final String name;

	/** The width and height of the robot. */
	private int size = 2;

	/**
	 * The speed modifier of the robot, default 0.5. #3198 NEVER set this > 1
	 */
	private double speedMod = 0.5;

	/**
	 * The stack of blocks the robot is holding. Notice: Stack has the last
	 * element of the list as 'top'. This is the reverse from the way we
	 * perceive stacks..
	 */
	private final Stack<Block> holding;

	/** The location to which the robot wants to travel. */
	private NdPoint targetLocation;

	/**
	 * set to true when {@link #connect()} is called.
	 */
	private boolean connected = false;
	
	private boolean hasBeenFree = false;

	/**
	 * Creates a new robot.
	 * 
	 * @param pname
	 *            The "human-friendly" name of the robot.
	 * @param space
	 *            The space in which the robot operates.
	 * @param grid
	 *            The grid in which the robot operates.
	 * @param context
	 *            The context in which the robot operates.
	 */
	public AbstractRobot(String pname, BW4TServerMap context) {
		super(context);

		this.name = pname;
		setSize(size, size);

		/**
		 * Here the number of blocks a bot can hold is set.
		 */
		this.holding = new Stack<>();
		this.agentRecord = new AgentRecord(name);
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
			LOGGER.error("Unable to get the associated agent for this entity",
					e);
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
			final AbstractRobot other = (AbstractRobot) obj;
			if (this.name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!this.name.equals(other.name)) {
				return false;
			}
			return super.equals(obj);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public Stack<Block> getHolding() {
		Stack<Block> copy = new Stack<Block>();
		copy.addAll(holding);
		return copy;
	}

	@Override
	public synchronized NdPoint getTargetLocation() {
		return targetLocation;
	}

	@Override
	public synchronized void setTargetLocation(NdPoint ptargetLocation) {
		this.targetLocation = ptargetLocation;
	}

	@Override
	public boolean canPickUp(BoundedMoveableObject obj) {
		if (obj instanceof Block) {
			Block b = (Block) obj;
			return (distanceTo(obj.getLocation()) <= ARM_DISTANCE)
					&& b.isFree() && (holding.size() < 1);
		}
		return false;
	}

	@Override
	public void pickUp(Block b) {
		if (holding.size() >= 1) {
			throw new IllegalStateException(
					"block stack is full, failed to pick up another block");
		}
		holding.push(b);
		b.setHeldBy(this);
		b.removeFromContext();
	}

	@Override
	public void drop() {
		if (holding.empty()) {
			throw new IllegalStateException("bot is not holding any block");
		}
		if (!holding.empty()) {
			Block b = holding.pop();
			// First check if dropped in dropzone, then it won't need to be
			// added to the context again
			DropZone dropZone = (DropZone) getContext().getObjects(
					DropZone.class).get(0);
			if (!dropZone.dropped(b, this)) {
				// bot was not in the dropzone.. Are we in a room?
				Zone ourzone = getZone();
				if (ourzone instanceof Room) {
					// We are in a room so can drop the block
					b.setHeldBy(null);
					b.addToContext();
					double x = ourzone.getLocation().getX();
					double y = ourzone.getLocation().getY();
					b.moveTo(x, y);
				}
			}
		}
	}

	@Override
	public void drop(int amount) {
		for (int i = 0; i < amount; i++) {
			drop();
		}
	}

	@Override
	public void moveTo(double x, double y) {
		// the check for getLocation is to always allow the initial moveTo
		if (getLocation() != null) {
			switch (getMoveType(x, y)) {
			case ENTERING_ROOM:
				agentRecord.addEnteredRoom(ZoneLocator.getZoneAt(x, y));
				break;
			case ENTER_CORRIDOR:
			case ENTERING_FREESPACE:
			case SAME_AREA:
				break;
			case HIT_CLOSED_DOOR:
			case HIT_WALL:
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
		 * if start and end are both in the same 'room' (outside is the 'null'
		 * room). Then free walk always possible.
		 */
		List<Zone> endzones = ZoneLocator.getZonesAt(endx, endy);
		Zone startzone = ZoneLocator.getZoneAt(startx, starty);

		/**
		 * If there is overlap in zones, ALL zones must be clear. Note, entering
		 * a free space is always ok.
		 */
		MoveType result = MoveType.ENTERING_FREESPACE;
		for (Zone endzone : endzones) {
			result = result.merge(checkZoneAccess(startzone, endzone, door));
		}
		return result;
	}

	@Override
	public MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
		if (startzone == endzone) {
			return MoveType.SAME_AREA;
		}
		// If one of the sides is a room, we require a door
		if (endzone instanceof Room) {

			// Start position must be ON a door to enable the switch.
			// Check if bot is going INTO the room, and if so, if the door is
			// open.
			if (door == null) {
				return MoveType.HIT_WALL;
			}
			// If there is a door, we just check that other end is accesible
			if (endzone.containsMeOrNothing(this)) {
				return MoveType.ENTERING_ROOM;
			}
			return MoveType.HIT_CLOSED_DOOR;
			// Both sides are not a room. Check if target accesible
		} else if (endzone instanceof Corridor) {
			return MoveType.ENTER_CORRIDOR;
		}
		return MoveType.ENTERING_FREESPACE;
	}

	@Override
	public Door getCurrentDoor(double x, double y) {
		for (Object o : getContext().getObjects(Door.class)) {
			Door door = (Door) o;
			if (door.getBoundingBox().contains(x, y)) {
				return door;
			}
		}
		return null;
	}

	@Override
	public Room getCurrentRoom(double x, double y) {
		for (Object o : getContext().getObjects(Room.class)) {
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
	@ScheduledMethod(start = 0, duration = 0, interval = 1)
	public synchronized void move() {
		if (targetLocation != null) {
			// Calculate the distance that the robot is allowed to move.
			double distance = distanceTo(targetLocation);
			if (distance < MIN_MOVE_DISTANCE) {
				// we're there
				stopRobot();
			} else {
				moveBot(distance);
			}
		}
	}

	/**
	 * Actually moves the bot.
	 * 
	 * @param distance
	 *            distance over which it must move.
	 */
	private void moveBot(double distance) {
		double movingDistance = Math
				.min(distance, MAX_MOVE_DISTANCE * speedMod);

		// Angle at which to move
		double angle = SpatialMath.calcAngleFor2DMovement(getSpace(),
				getLocation(), targetLocation);

		// The displacement of the robot
		double[] displacement = SpatialMath.getDisplacement(2, 0,
				movingDistance, angle);

		try {
			// Check if the robot is alone on its map point
			if (!hasBeenFree) {
				hasBeenFree = isFree(AbstractRobot.class);
			}

			// Move the robot to the new position using the displacement
			moveTo(getLocation().getX() + displacement[0], getLocation().getY() + displacement[1]);
			agentRecord.setStartedMoving();
		} catch (SpatialException e) {
			LOGGER.log(BotLog.BOTLOG, "Bot " + this.name + " collided.");
			stopRobot();
		}
	}

	@Override
	public synchronized void stopRobot() {
		this.targetLocation = null;
		agentRecord.setStoppedMoving();
	}

	@Override
	public boolean isConnected() {
		return this.connected;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	/**
	 * Sets the size of a robot to a certain integer
	 * 
	 * @param s
	 *            int
	 */
	@Override
	public void setSize(int s) {
		this.size = s;
		setSize(s, s);
	}

	@Override
	public ViewEntity getView() {
		Stack<ViewBlock> bs = new Stack<ViewBlock>();
		for (Block block : holding) {
			bs.add(block.getView());
		}
		NdPoint loc = getSpace().getLocation(this);
		return new ViewEntity(getId(), getName(), loc.getX(), loc.getY(), bs,
				getSize());
	}

	@Override
	public AgentRecord getAgentRecord() {
		return agentRecord;

	}

	@Override
	public IRobot getParent() {
		return null;
	}

	@Override
	public IRobot getEarliestParent() {
		return null;
	}

	@Override
	public void setParent(IRobot hI) {
		// does not do anything because Robot is the super parent
	}
	
	@Override
	public double getSpeedMod() {
		return speedMod;
	}

	@Override
	public void setSpeedMod(double speedMod) {
		if (speedMod > 1.0 || speedMod < 0) {
			throw new IllegalArgumentException(
					"speedMod must be in [0,1] but is " + speedMod);
		}
		this.speedMod = speedMod;
	}

	@Override
	public AbstractRobot getSuperParent() {
		return this;
	}
}
