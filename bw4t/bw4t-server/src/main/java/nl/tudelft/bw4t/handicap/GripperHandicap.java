package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.blocks.Block;

/**
 * @author Valentine Mairet & Ruben Starmans
 */
public class GripperHandicap extends AbstractHandicapFactory {
	/**
	 * boolean is true when the handicap is active.
	 */
	private boolean isActive;

	/**
	 * Calls the super method on p,
	 * Sets the handicap to active,
	 * Adds the handicap to the robot handicap storage.
	 * @param p HandicapInterface the GripperHandicap wraps around.
	 */
	public GripperHandicap(HandicapInterface p) {
		super(p);
		isActive = true;
		
		robot.getHandicapsMap().put("Gripper", this);
	}

	/**
	 * Override of the canPickUp method,
	 * The non gripping robot cannot pick up blocks.
	 * @param b block which the robot tries to pick up
	 * @return false
	 */
	@Override
	public boolean canPickUp(Block b) {
		if (isActive) {
		return false;
		}
		else {
			return super.canPickUp(b);
		}
	}
	
	/**
	 * Activate the handicap.
	 */
	public void activate() {
		isActive = true;
	}
	
	/**
	 * Deactivate the handicap.
	 * @param bool
	 */
	public void deactivate() {
		isActive = false;
	}
}
