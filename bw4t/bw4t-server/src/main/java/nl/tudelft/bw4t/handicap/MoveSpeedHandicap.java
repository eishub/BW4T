package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.server.logging.BW4TLogger;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialException;
import repast.simphony.space.SpatialMath;
/**
 * 
 * @author Valentine Mairet & Ruben Starmans
 *
 */
public class MoveSpeedHandicap extends AbstractHandicapFactory {
	/**
	 * modifier for the distance per tick
	 */
	private double speedMod;
	/**
	 * boolean value to check if the handicap is active or not
	 */
	private boolean isActive;
	/**
	 * Constructor for the handicap
	 * @param p HandicapInterface of the MoveSpeedHandicap Wraps around
	 * @param speedModifier modifier fo the distance per tick
	 */
	public MoveSpeedHandicap(HandicapInterface p, double speedModifier) {
		super(p);
		speedMod = speedModifier;
		isActive = true;
		
		robot.getHandicapsMap().put("MoveSpeed", this);
	}
	/**
	 * Overridden method move of Robot which has the speed modifier
	 */
	@ScheduledMethod(start = 0, duration = 0, interval = 1)
	public synchronized void move() {
		if (isActive) {
			if (robot.getTargetLocation() != null) {
				// Calculate the distance that the robot is allowed to move.
				double distance = robot.distanceTo(robot.getTargetLocation());
				if (distance < Robot.MIN_MOVE_DISTANCE) {
					robot.stopRobot(); 
				} 
				else {
					double movingDistance = Math.min(distance, Robot.MAX_MOVE_DISTANCE * speedMod);
		
					// Angle at which to move
					double angle = SpatialMath.calcAngleFor2DMovement(robot.space,
							robot.getLocation(), robot.getTargetLocation());
		
					// The displacement of the robot
					double[] displacement = SpatialMath.getDisplacement(2, 0,
							movingDistance, angle);
		
					try {
						// Move the robot to the new position using the displacement
						robot.moveByDisplacement(displacement[0], displacement[1]);
						BW4TLogger.getInstance().logMoving(robot.getName());
						
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
		else {
			super.move();
		}
	}
	/**
	 * set the handicap to active
	 */
	public void activate() {
		isActive = true;
	}
	/**
	 * set the handicap to inactive
	 */
	public void deactivate() {
		isActive = false;
	}
	
	/**
	 * @return speedMod
	 */	
	public double getSpeedMod() {
		return speedMod;
	}
}
