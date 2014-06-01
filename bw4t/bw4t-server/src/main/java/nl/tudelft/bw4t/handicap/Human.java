package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.blocks.EPartner;

/**
 * @author Valentine Mairet
 */
public class Human extends AbstractHandicapFactory {
	
    /**
     * Sets the handicap to active,
     * Adds the handicap to the robot handicap storage.
     * @param p is the HandicapInterface the Human wraps around.
     */
    public Human(HandicapInterface p) {
        super(p);
        robot.getHandicapsMap().put("ColorBlind", this);
    }
    
    public boolean canPickUpEPartner(EPartner eP) {
    	return false;
    }
    public void pickUpEPartner(EPartner eP) {
    	robot.setHoldingEPartner(true);
    }
}
