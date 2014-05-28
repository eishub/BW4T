package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.robots.Robot.MoveType;
import nl.tudelft.bw4t.zone.Zone;

/**
 * 
 * @author Valentine Mairet & Ruben Starmans
 *
 */
public abstract class AbstractHandicapFactory implements HandicapInterface {
	/**
	 * Handicap which the current handicap is wrapped around.
	 */
	private HandicapInterface parent;
	/**
	 * Robot to which this handicap belongs to.
	 */
	private Robot robot;
	
	/**
	 * Sets the parent to p,
	 * Sets the robot to the super parent (last parent).
	 * @param p HandicapInterface the Handicap wraps around.
	 */
	public AbstractHandicapFactory(HandicapInterface p) {
		parent = p;
		robot = getSuperParent();
	}
	
	/**
	 * Sets the parent to a new parent.
	 * @param hI sets the parent handicap.
	 */
	public void setParent(HandicapInterface hI) {
		parent = hI;
	}
	
	/**
	 * Returns the parent.
	 * @return parent
	 */
	public HandicapInterface getParent() {
		return parent;
	}
	
	/**
	 * GripperHandicap.
	 * @param b block that is about to be picked up.
	 * @return true if the block can be picked up.
	 */
	public boolean canPickUp(Block b) {
		return parent.canPickUp(b);
	}
	
	/**
	 * MoveSpeedHandicap.
	 * The robot is immovable if speed is 0.s
	 */
	public void move() {
		parent.move();
	}
	
	/**
	 * SizeOverloadHandicap
	 * @param startzone current zone where the robot is now.
	 * @param endzone target zone.
	 * @param door which is in between the two zones.
	 * @return return true if the robot can access the target zone.
	 */
	public MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
		return parent.checkZoneAccess(startzone, endzone, door);
	}
	
	/**
	 * @return robot the handicaps are attached to.
	 */
	public Robot getSuperParent() {
		return robot;
	}
}
