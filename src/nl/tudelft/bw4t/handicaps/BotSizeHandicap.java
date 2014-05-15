package nl.tudelft.bw4t.handicaps;

import java.util.List;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.robots.Robot.MoveType;
import nl.tudelft.bw4t.server.BW4TLogger;
import nl.tudelft.bw4t.util.ZoneLocator;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;
import nl.tudelft.bw4t.zone.Zone;
import repast.simphony.context.Context;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialException;
import repast.simphony.space.continuous.ContinuousSpace;

/**
 * 
 * @author Wendy
 * The bot size has effect on multiple features:
 * - A bot bigger than size 4 can't enter rooms (the doors are 4 width)
 * - The bigger the bot, the slower the maximum speed
 * - The bigger the bot, the faster his battery will discharge
 *
 */

public class BotSizeHandicap extends Handicap {
	private Robot r;
	/**
	 * Constructor without previously defined robot.
	 * @param name Name of robot.
	 * @param space Space in which the robot operates.
	 * @param context Context in which the robot operates.
	 * @param oneBotPerZone True if there is a max of 1 robot per zone.
	 */
	public BotSizeHandicap(String name, ContinuousSpace<Object> space,
			Context<Object> context, boolean oneBotPerZone) {
		super(name, space, context, oneBotPerZone);
		r = new Robot(name, space, context, oneBotPerZone);
	}
	
	/**
	 * Constructor with previously defined robot.
	 * @param name Name of robot.
	 * @param space Space in which the robot operates.
	 * @param context Context in which the robot operates.
	 * @param oneBotPerZone True if there is a max of 1 robot per zone.
	 * @param r The previously defined robot.
	 */
	public BotSizeHandicap(String name, ContinuousSpace<Object> space,
			Context<Object> context, boolean oneBotPerZone, Robot r) {
		super(name, space, context, oneBotPerZone);
		this.r = r;
	}
	
	/**
	 * @author Wendy
	 * @return true if the bot fits through the door, otherwise false
	 */
	
	public boolean canGoTroughDoor() {
		if (r.getSize() > 4) 
			return false;
		else
			return true;
	}

	
	// Nog geen idee of ik onderstaande functies nodig ga hebben hier (gecopy-past van Tim's MovingHandicap)

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
				BW4TLogger.getInstance().logEnteredRoom(this.r.getName());
				break;
			case HIT_CLOSED_DOOR:
			case HIT_WALL:
			case HIT_OCCUPIED_ZONE:
				throw new SpatialException("robot bumped: " + getMoveType(x, y));
			}
		}
		//fix why this doesn't work.
		//super.moveTo(x, y);
	}

	@Override
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
	 * Valentine
	 * This ensures that the robot cannot enter any zone.
	 * There should be an exception for charging zones.
	 */
	@Override
	protected MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
		return null;
	}
	
	/** 
	 * Returns currently used robot.
	 * @return The robot this class wraps around.
	 */
	public Robot getRobot() {
		return r;
	}
	/**
	 * Sets a different robot.
	 * @param r The robot.
	 */
	public void setRobot(Robot r) {
		this.r = r;
	}


}

