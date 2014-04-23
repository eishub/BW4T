package nl.tudelft.bw4t.robots;

import java.util.List;

import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.server.BW4TLogger;
import nl.tudelft.bw4t.util.ZoneLocator;
import nl.tudelft.bw4t.zone.Corridor;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;
import nl.tudelft.bw4t.zone.Zone;
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
public class Robot extends BoundedMoveableObject {

	/**
	 * The distance which it can move per tick. This should never be larger than
	 * the door width because that might cause the bot to attempt to jump over a
	 * door (which will fail).
	 */
	private static final double MAX_MOVE_DISTANCE = .5;
	/**
	 * When we are this close or closer, we are effectively at the target
	 * position.
	 */
	private static final double MIN_MOVE_DISTANCE = .001;
	/** The distance which it can reach with its arm to pick up a block. */
	private static final double ARM_DISTANCE = 1;
	/** The width and height of the robot */
	public final static int SIZE = 2;

	/** The name of the robot */
	private final String name;

	/** The location to which the robot wants to travel. */
	private NdPoint targetLocation;
	/** The block the robot is holding, null if none. */
	private Block holding;

	/**
	 * set to true if we have to cancel a motion due to a collision. A collision
	 * is caused by an attempt to move into or out of a room
	 */
	private boolean collided = false;

	/**
	 * set to true when {@link #connect()} is called.
	 */
	private boolean connected = false;
	private boolean oneBotPerZone;

	/**
	 * Creates a new robot.
	 * 
	 * @param name
	 *            The "human-friendly" name of the robot.
	 * @param space
	 *            The space in which the robot operates.
	 * @param context
	 *            The context in which the robot operates.
	 * @param oneBotPerZone
	 *            true if max 1 bot in a zone
	 */
	public Robot(String name, ContinuousSpace<Object> space,
			Context<Object> context, boolean oneBotPerZone) {
		super(space, context);

		this.name = name;
		this.oneBotPerZone = oneBotPerZone;
		setSize(SIZE, SIZE);
	}

	/**
	 * called when robot becomes connected and should now be injected in repast.
	 */
	public void connect() {
		connected = true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Robot)
			return super.equals(obj);
		else
			return false;
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
	public Block isHolding() {
		return holding;
	}

	/**
	 * Sets the location to which the robot should move. This also clears the
	 * {@link #collided} flag.
	 * 
	 * @param targetLocation
	 *            the location to move to.
	 */
	public synchronized void setTargetLocation(NdPoint targetLocation) {
		this.targetLocation = targetLocation;
		collided = false;
	}

	/**
	 * Check if robot can pick up a block.
	 * 
	 * @param b
	 *            the block to check
	 */
	public boolean canPickUp(Block b) {
		double distance = distanceTo(b.getLocation());

		if (distance <= ARM_DISTANCE && b.isFree())
			return true;
		else
			return false;
	}

	/**
	 * Pick up a block
	 * 
	 * @param b
	 *            , the block to pick up
	 */
	public void pickUp(Block b) {
		drop();
		holding = b;
		b.setHeldBy(this);
		b.removeFromContext();
	}

	/**
	 * Get current zone that the robot is in.
	 * 
	 * @return
	 */
	public Zone getZone() {
		return ZoneLocator.getZoneAt(getLocation());
	}

	/**
	 * Drops the block the robot is holding on the current location. TODO: What
	 * if multiple blocks dropped at same spot?
	 */
	public void drop() {
		if (holding != null) {
			// First check if dropped in dropzone, then it won't need to be
			// added to the context again
			DropZone dropZone = (DropZone) context.getObjects(DropZone.class)
					.get(0);
			if (!dropZone.dropped(holding, this)) {
				// bot was not in the dropzone.. Are we in a room?
				Zone ourzone = getZone();
				if (ourzone instanceof Room) {
					// We are in a room so can drop the block
					holding.setHeldBy(null);
					holding.addToContext();
					// Slightly jitter the location where the box is
					// dropped
					double x = ourzone.getLocation().getX();
					double y = ourzone.getLocation().getY();
					holding.moveTo(RandomHelper.nextDoubleFromTo(x - 5, x + 5),
							RandomHelper.nextDoubleFromTo(y - 5, y + 5));
					holding = null;
					return;

				}
			}
			holding = null;
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
				BW4TLogger.getInstance().logEnteredRoom(name);
				break;
			case HIT_CLOSED_DOOR:
			case HIT_WALL:
			case HIT_OCCUPIED_ZONE:
				throw new SpatialException("robot bumped: " + getMoveType(x, y));
			}
		}
		super.moveTo(x, y);
	}

	enum MoveType {
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
		 * Merge the move type if multiple zones are entered at once. The result
		 * is the 'worst' event that happens
		 * 
		 * @param other
		 * @return
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
			return this == HIT_CLOSED_DOOR || this == HIT_WALL
					|| this == HIT_OCCUPIED_ZONE;
		}
	}

	/**
	 * Check motion type for robot to move to <endx, endy>. The
	 * {@link #MoveType} gives the actual type / possibility of the move, plus
	 * the details why it is (not) possible.
	 * 
	 * @param endx
	 *            is x position of target
	 * @param endy
	 *            is y position of target
	 */

	public MoveType getMoveType(double endx, double endy) {
		double startx = getLocation().getX();
		double starty = getLocation().getY();
		Door door = getCurrentDoor(startx, starty);

		/*
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

	/**
	 * check if we can access endzone from startzone.
	 * 
	 * @param startzone
	 * @param endzone
	 * @return
	 */
	private MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
		if (startzone == endzone) {
			return MoveType.SAME_AREA;
		}

		/**
		 * A zone switch is attempted as either startzone or endzone is not
		 * null.
		 */
		/**
		 * If one of the sides is a room, we require a door
		 */

		if (endzone instanceof Room) {
			/**
			 * Start position must be ON a door to enable the switch. Check if
			 * bot is going INTO the room, and if so, if the door is open.
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
	 * get door at a given position. Note that you can be in a door and at the
	 * same time in a room. This is because rooms and doors partially overlap
	 * usually.
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
	 * Moves the robot by displacing it for the given amount. If the robot
	 * collides with something, the movement target is cancelled to avoid
	 * continuous bumping.
	 * 
	 * @param x
	 *            the displacement in the x-dimension.
	 * @param y
	 *            the displacement in the y-dimension.
	 */
	public void moveByDisplacement(double x, double y) {
		moveTo(getLocation().getX() + x, getLocation().getY() + y);
	}

	@ScheduledMethod(start = 0, duration = 0, interval = 1)
	public synchronized void move() {
		if (targetLocation != null) {
			// Calculate the distance that the robot is allowed to move.
			double distance = distanceTo(targetLocation);
			if (distance < MIN_MOVE_DISTANCE) {
				stopRobot(); // we're there
			} else {
				double movingDistance = Math.min(distance, MAX_MOVE_DISTANCE);

				// Angle at which to move
				double angle = SpatialMath.calcAngleFor2DMovement(space,
						getLocation(), targetLocation);

				// The displacement of the robot
				double[] displacement = SpatialMath.getDisplacement(2, 0,
						movingDistance, angle);

				try {
					// Move the robot to the new position using the displacement
					moveByDisplacement(displacement[0], displacement[1]);
					BW4TLogger.getInstance().logMoving(name);
				} catch (SpatialException e) {
					collided = true;
					stopRobot();
				}
			}
		}
	}

	/**
	 * Stop the motion of the robot. Effectively sets the target location to
	 * null. You can override this to catch this event.
	 */
	public synchronized void stopRobot() {
		this.targetLocation = null;
		BW4TLogger.getInstance().logStopMoving(name);
	}

	/**
	 * clear the collision flag. You can use this to reset the flag after you
	 * took notice of the collision.
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

	public boolean isConnected() {
		return connected;
	}
}
