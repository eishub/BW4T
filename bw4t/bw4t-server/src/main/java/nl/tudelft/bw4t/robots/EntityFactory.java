package nl.tudelft.bw4t.robots;

import nl.tudelft.bw4t.blocks.EPartner;
import nl.tudelft.bw4t.handicap.IRobot;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;

/**
 * This defines the Factory interface to generate the robot classes with all their required handicaps.
 */
public interface EntityFactory {
	void setSpace(ContinuousSpace<Object> space);
	void setContext(Context<Object> context);
	
	/**
	 * Create a default robot with one arm, normal size and speed.
	 * @param name the entity name for this robot
	 * @return the created robot
	 */
	IRobot makeDefaultRobot(String name);
	
	/**
	 * Get a robot created according to the given specifications.
	 * @param config the specification for the robot.
	 * @return the created robot
	 */
	IRobot makeRobot(BotConfig config);
	
	/**
	 * Make a new Epartner with the default configuration.
	 * @param name the entity name for this e-partner
	 * @return the e-partner object
	 */
	EPartner makeDefaultEPartner(String name);
	
	/**
	 * Make a EPartner according to the given specifications.
	 * @param c the configuration
	 * @return the new e-partner
	 */
    EPartner makeEPartner(EPartnerConfig c);
}
