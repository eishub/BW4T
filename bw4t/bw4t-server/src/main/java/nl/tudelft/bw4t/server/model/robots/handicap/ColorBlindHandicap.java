package nl.tudelft.bw4t.server.model.robots.handicap;

public class ColorBlindHandicap extends AbstractRobotDecorator {
    
    public static final String COLOR_BLIND_HANDICAP = "ColorBlind";
    
    /**
     * Sets the handicap to active,
     * Adds the handicap to the robot handicap storage.
     * @param p is the HandicapInterface the ColorBlindHandicap wraps around.
     */
    public ColorBlindHandicap(IRobot p) {
        super(p);
        robot.getHandicapsList().add(COLOR_BLIND_HANDICAP);
    }
}
