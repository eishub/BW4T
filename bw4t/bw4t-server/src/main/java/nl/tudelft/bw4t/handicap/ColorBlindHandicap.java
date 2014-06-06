package nl.tudelft.bw4t.handicap;

public class ColorBlindHandicap extends AbstractRobotDecorator {
    
    /**
     * Sets the handicap to active,
     * Adds the handicap to the robot handicap storage.
     * @param p is the HandicapInterface the ColorBlindHandicap wraps around.
     */
    public ColorBlindHandicap(IRobot p) {
        super(p);
        robot.getHandicapsList().add("ColorBlind");
    }
}
