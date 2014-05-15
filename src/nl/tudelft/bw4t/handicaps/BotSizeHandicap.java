package nl.tudelft.bw4t.handicaps;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.zone.Zone;
import repast.simphony.context.Context;
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

	@Override
	public boolean canPickUp(Block b) {
		return r.canPickUp(b);
	}

	@Override
	public void pickUp(Block b) {
		r.pickUp(b);
	}

	@Override
	public void drop() {
		r.drop();
	}

	@Override
	public void moveTo(double x, double y) {
	}

	@Override
	public MoveType getMoveType(double endx, double endy) {
		return MoveType.ENTERING_FREESPACE;
	}

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

