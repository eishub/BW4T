package nl.tudelft.bw4t.server.model.robots;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;

/**
 * This defines the Factory interface to generate the robot classes with all their required handicaps.
 */
public interface EntityFactory {
    /**
     * sets the server map.
     * @param server map to be set
     */
    void setServerMap(BW4TServerMap serverMap);
    
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
}
