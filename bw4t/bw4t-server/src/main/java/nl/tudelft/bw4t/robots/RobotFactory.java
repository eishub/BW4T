package nl.tudelft.bw4t.robots;

import nl.tudelft.bw4t.handicap.HandicapInterface;
import nl.tudelft.bw4t.scenariogui.BotConfig;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;

/**
 * This defines the Factory interface to generate the robot classes with all their required handicaps.
 */
public interface RobotFactory {
	void setSpace(ContinuousSpace<Object> space);
	void setContext(Context<Object> context);
	/**
	 * Create a default robot with one arm, normal size and speed.
	 * @return the created robot
	 */
	HandicapInterface getDefaultRobot(String name);
	
	/**
	 * Get a robot created according to the given specifications.
	 * @param config the specification for the robot.
	 * @return the created robot
	 */
	HandicapInterface getRobot(BotConfig config);
}
