package nl.tudelft.bw4t.handicap;
/**
 * 
 * @author Valentine Mairet & Ruben Starmans
 *
 */
public class ColorBlindHandicap extends AbstractHandicapFactory {
    
    /**
     * Sets the handicap to active,
     * Adds the handicap to the robot handicap storage.
     * @param p is the HandicapInterface the ColorBlindHandicap wraps around.
     */
    public ColorBlindHandicap(HandicapInterface p) {
        super(p);
        robot.getHandicapsList().add("ColorBlind");
    }
}
