package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.robots.AbstractRobot;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialException;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.NdPoint;
/**
 * 
 * @author Valentine Mairet & Ruben Starmans
 *
 */
public class MoveSpeedHandicap extends AbstractRobotDecorator {
    /**
     * modifier for the distance per tick
     */
    private final double speedMod;

    /**
     * Constructor for the handicap
     * @param p HandicapInterface of the MoveSpeedHandicap Wraps around
     * @param speedModifier modifier of the distance per tick
     */
    public MoveSpeedHandicap(IRobot p, double speedModifier) {
        super(p);
        speedMod = speedModifier;
        
        robot.getHandicapsList().add("MoveSpeed");
    }
    
    /**
     * Overridden method move of Robot which has the speed modifier
     */
    @Override
    @ScheduledMethod(start = 0, duration = 0, interval = 1)
    public synchronized void move() {
        NdPoint targetLocation = robot.getTargetLocation();
        if (targetLocation != null) {
            // Calculate the distance that the robot is allowed to move.
            double distance = robot.distanceTo(targetLocation);
            if (distance < AbstractRobot.MIN_MOVE_DISTANCE) {
                // we're there
                robot.stopRobot(); 
            } 
            else {
                double movingDistance = Math.min(distance, AbstractRobot.MAX_MOVE_DISTANCE * speedMod);
    
                // Angle at which to move
                double angle = SpatialMath.calcAngleFor2DMovement(robot.getSpace(),
                        robot.getLocation(), robot.getTargetLocation());
    
                // The displacement of the robot
                double[] displacement = SpatialMath.getDisplacement(2, 0,
                        movingDistance, angle);
    
                try {
                    // Move the robot to the new position using the displacement
                    robot.moveByDisplacement(displacement[0], displacement[1]);
                    
                    /**
                     * Valentine
                     * The robot's battery discharges when it moves.
                     */
                    robot.getBattery().discharge();
                } catch (SpatialException e) {
                    robot.setCollided(true);
                    robot.stopRobot();
                }
            }
        }
    }
    
    @Override
    public double getSpeedMod() {
        return speedMod;
    }
}
