package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.blocks.EPartner;
import nl.tudelft.bw4t.robots.Robot;

/**
 * @author Valentine Mairet
 */
public class Human extends AbstractHandicapFactory {
	
    /**
     * True if the human is holding an e-Partner.
     */
    private boolean isHoldingEPartner = false;
	
    /**
     * Sets the handicap to active,
     * Adds the handicap to the robot handicap storage.
     * @param p is the HandicapInterface the Human wraps around.
     */
    public Human(HandicapInterface p) {
        super(p);
        robot.getHandicapsMap().put("Human", this);
    }
    
    /**
     * @param eP
     * 			The ePartner to be picked up.
     * @return
     * 			True if the human can pick it up. 
     */
    public boolean canPickUpEPartner(EPartner eP) {
    	return (robot.distanceTo(eP.getLocation()) <= Robot.ARM_DISTANCE);
    }
    
    /**
     * @param eP
     * 			The picked up e-Partner. 
     */
    public void pickUpEPartner(EPartner eP) {
    	//if (this.canPickUpEPartner(eP)) {
        	this.setHoldingEPartner(true);
    	//}
    }

	public boolean isHoldingEPartner() {
		return isHoldingEPartner;
	}

	public void setHoldingEPartner(boolean isHoldingEPartner) {
		this.isHoldingEPartner = isHoldingEPartner;
	}
}
