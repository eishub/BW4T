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
public abstract class Handicap implements HandicapInterface {
	HandicapInterface parent;
	Robot robot;
	
	/**
	 * Sets the parent to p,
	 * Sets the robot to the super parent (last parent).
	 * @param p HandicapInterface the Handicap wraps around.
	 */
	public Handicap(HandicapInterface p) {
		parent = p;
		robot = getSuperParent();
	}
	
	/**
	 * Sets the parent to a new parent.
	 */
	public void setParent(HandicapInterface hI) {
		parent = hI;
	}
	
	/**
	 * Returns the parent.
	 */
	public HandicapInterface getParent() {
		return parent;
	}
	
	/**
	 * GripperHandicap.
	 * @param b
	 * @return
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
	 * @param startzone
	 * @param endzone
	 * @param door
	 * @return
	 */
	public MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
		return parent.checkZoneAccess(startzone, endzone, door);
	}
	
	/**
	 * Returns the robot the handicaps are attached to.
	 */
	public Robot getSuperParent() {
		HandicapInterface temp = parent;
		if (!(temp instanceof Robot)) {
			return temp.getSuperParent();
		}
		return (Robot) temp;
	}
}
