package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.blocks.EPartner;
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
     * @param hI sets the parent of a handicap to hI
     */
    void setParent(HandicapInterface hI);
    /**
     * @return the parent of a handicap
     */
    HandicapInterface getParent();
    
    /**
     * GripperHandicap. Check if robot can pick up a block.
     * @param b Block the block to check
     * @return false if it cannot pick it up, true if it can
     */
    boolean canPickUp(Block b);
    
    /**
     * Method to move the robot
     * MoveSpeedHandicap.
     * The robot is immovable if speed is 0.
     */
    void move();
    
    /**
     * SizeOverloadHandicap
     * @param startzone current zone
     * @param endzone zone where the robot tries to move to
     * @param door which is between the two zones
     * @return true if the endzone can be accessed
     */
    MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door);
    
    /**
     * Can we pick the given e-partner.
     * @param eP the e-partner to be picked up
     * @return true if it is possible to pickup the e-partner
     */
    boolean canPickUpEPartner(EPartner eP);

    /**
     * Pick the given e-partner.
     * @param eP the e-partner to be picked up
     */
    void pickUpEPartner(EPartner eP);
    
    /**
     * drops the e-Partner the human is holding.
     */
    void dropEPartner();
    
    /**
     * @return if the human is holding an e-Partner.
     */
	boolean isHoldingEPartner();
	
	/**
	 * @return the e-Partner the human is holding. 
	 */
	EPartner getEPartner();
	
    /**
     * @return the size of the robot.
     */
    int getSize();
    
    /**
     * @return the gripper capacity of the robot.
     */
    int getGripperCapacity();
    
    /**
     * @return the speed of the robot.
     */
    double getSpeedMod();
	
    /**
     * @return the Robot which is the super parent of the current handicap
     */
    Robot getSuperParent(); 
}
