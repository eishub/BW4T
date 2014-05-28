package nl.tudelft.bw4t.handicap;
/**
 * 
 * @author Valentine Mairet & Ruben Starmans
 *
 */
public class ColorBlindHandicap extends AbstractHandicapFactory {
	/**
	 * boolean is true if the handicap is active.
	 */
	private boolean isActive;
	
	/**
	 * Sets the handicap to active,
	 * Adds the handicap to the robot handicap storage.
	 * @param p is the HandicapInterface the ColorBlindHandicap wraps around.
	 */
	public ColorBlindHandicap(HandicapInterface p) {
		super(p);
		setActive(true);
		robot.getHandicapsMap().put("ColorBlind", this);
	}
	
	/**
	 * Activate the handicap.
	 */
	public void activate() {
		setActive(true);
	}
	
	/**
	 * Deactivate the handicap.
	 */
	public void deactivate() {
		setActive(false);
	}

	/**
	 * @return if the handicap is active or not
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param act true when handicap is active.
	 */
	public void setActive(boolean act) {
		this.isActive = act;
	}

	
}
