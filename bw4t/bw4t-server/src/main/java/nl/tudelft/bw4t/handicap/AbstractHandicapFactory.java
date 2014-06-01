package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.blocks.EPartner;
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
    protected Robot robot = null;
    
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
    @Override
    public void setParent(HandicapInterface hI) {
        parent = hI;
    }
    
    /**
     * Returns the parent.
     * @return parent
     */
    @Override
    public HandicapInterface getParent() {
        return parent;
    }
    
    /**
     * GripperHandicap.
     * @param b block that is about to be picked up.
     * @return true if the block can be picked up.
     */
    @Override
    public boolean canPickUp(Block b) {
        return parent.canPickUp(b);
    }
    
    /**
     * MoveSpeedHandicap.
     * The robot is immovable if speed is 0.s
     */
    @Override
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
    @Override
    public MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
        return parent.checkZoneAccess(startzone, endzone, door);
    }

	@Override
	public boolean canPickUpEPartner(EPartner eP) {
		return parent.canPickUpEPartner(eP);
	}

	@Override
	public void pickUpEPartner(EPartner eP) {
		parent.pickUpEPartner(eP);
		
	}

	@Override
	public boolean isHoldingEPartner() {
		return parent.isHoldingEPartner();
	}

	@Override
	public void setHoldingEPartner(boolean isHoldingEPartner) {
		parent.setHoldingEPartner(isHoldingEPartner);
	}
	
	@Override
	public double getSpeedMod() {
		return parent.getSpeedMod();
	}
    
    /**
     * Attention! This method calls itself on the parent until it gets to robot.
     * Please do not edit this.
     * @return robot the handicaps are attached to.
     */
	@Override
    public Robot getSuperParent() {
		if (robot == null) {
			robot = parent.getSuperParent();
		}
		
        return robot;
    }
}
