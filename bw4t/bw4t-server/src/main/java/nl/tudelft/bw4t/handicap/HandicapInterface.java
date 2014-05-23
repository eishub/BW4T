package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.robots.Robot.MoveType;
import nl.tudelft.bw4t.zone.Zone;
/**
 * This interface contains all the methods in Robot that the handicaps will affect. 
 * @author Valentine Mairet & Ruben Starmans
 *
 */
public interface HandicapInterface {
	/**
	 * Set the parent Handicap.
	 * @param hI sets the parent of a handicap to hI
	 */
	public void setParent(HandicapInterface hI);
	/**
	 * Get the parent Handicap.
	 * @return the parent of a handicap
	 */
	public HandicapInterface getParent();
	
	/**
	 * GripperHandicap. Check if robot can pick up a block.
	 * @param b Block the block to check
	 * @return false if it cannot pick it up, true if it can
	 */
	public boolean canPickUp(Block b);
	
	/**
	 * Method to move the robot
	 * MoveSpeedHandicap.
	 * The robot is immovable if speed is 0.
	 */
	public void move();
	
	/**
	 * SizeOverloadHandicap
	 * @param startzone current zone
	 * @param endzone zone where the robot tries to move to
	 * @param door which is between the two zones
	 * @return true if the endzone can be accessed
	 */
	public MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door);
	/**
	 * Get the robot at the top of the Handicaps.
	 * @return the Robot which is the super parent of the current handicap
	 */
	public Robot getSuperParent();	
}
