package nl.tudelft.bw4t.handicaps;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;
import nl.tudelft.bw4t.zone.Zone;
import repast.simphony.context.Context;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

public class MovingHandicap extends Handicap {
	private Robot r;
	/**
	 * Constructor without previously defined robot.
	 * @param name Name of robot.
	 * @param space Space in which the robot operates.
	 * @param context Context in which the robot operates.
	 * @param oneBotPerZone True if there is a max of 1 robot per zone.
	 */
	public MovingHandicap(String name, ContinuousSpace<Object> space,
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
	public MovingHandicap(String name, ContinuousSpace<Object> space,
			Context<Object> context, boolean oneBotPerZone, Robot r) {
		super(name, space, context, oneBotPerZone);
		this.r = r;
	}
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
	 * Overridden setTartgetLocation method to make moving impossible.
	 */
	@Override
	public synchronized void setTargetLocation(NdPoint targetLocation) {
		this.targetLocation = null;
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
