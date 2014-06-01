package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.blocks.Block;

/**
 * @author Valentine Mairet & Ruben Starmans
 */
public class GripperHandicap extends AbstractHandicapFactory {

    /**
     * Calls the super method on p,
     * Sets the handicap to active,
     * Adds the handicap to the robot handicap storage.
     * @param p HandicapInterface the GripperHandicap wraps around.
     */
    public GripperHandicap(HandicapInterface p) {
        super(p);
        robot.getHandicapsMap().put("Gripper", this);
        robot.setCapacity(0);
    }

    /**
     * Override of the canPickUp method,
     * The non gripping robot cannot pick up blocks.
     * @param b block which the robot tries to pick up
     * @return false
     */
    @Override
    public boolean canPickUp(Block b) {
    	return false;
    }
    
    @Override
    public int getCapacity() {
    	return 0;
    }
}
