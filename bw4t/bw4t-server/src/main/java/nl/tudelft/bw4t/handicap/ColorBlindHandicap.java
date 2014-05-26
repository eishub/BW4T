package nl.tudelft.bw4t.handicap;
/**
 * 
 * @author Ruben Starmans
 *
 */
public class ColorBlindHandicap extends Handicap{
	
	public boolean isActive;
	
	/**
	 * Sets the handicap to active,
	 * Adds the handicap to the robot handicap storage.
	 * @param p is the HandicapInterface the ColorBlindHandicap wraps around.
	 */
	public ColorBlindHandicap(HandicapInterface p) {
		super(p);
		isActive = true;
		robot.getHandicapsMap().put("ColorBlind", this);
	}
	
	/**
	 * Activate the handicap.
	 */
	public void activate()
	{
		isActive = true;
	}
	
	/**
	 * Deactivate the handicap.
	 */
	public void deactivate()
	{
		isActive = false;
	}

	
}
