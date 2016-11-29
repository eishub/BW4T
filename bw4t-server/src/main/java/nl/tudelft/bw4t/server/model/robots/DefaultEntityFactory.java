package nl.tudelft.bw4t.server.model.robots;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;

/**
 * Creates the default entities.
 */
public class DefaultEntityFactory implements EntityFactory {

    /**
     * The context which we will be using.
     */
    private BW4TServerMap serverMap;

    @Override
    public void setServerMap(BW4TServerMap serverMap) {
        this.serverMap = serverMap;
    }

    @Override
    public IRobot makeDefaultRobot(String name) {
        return new NavigatingRobot(name, serverMap);
    }

    @Override
    public IRobot makeRobot(BotConfig config) {
        return makeDefaultRobot(config.getBotName());
    }
}
