package nl.tudelft.bw4t.server.model.robots;

import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.epartners.EPartner;
import nl.tudelft.bw4t.server.model.robots.handicap.ColorBlindHandicap;
import nl.tudelft.bw4t.server.model.robots.handicap.GripperHandicap;
import nl.tudelft.bw4t.server.model.robots.handicap.Human;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;
import nl.tudelft.bw4t.server.model.robots.handicap.SizeOverloadHandicap;

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
        return new NavigatingRobot(name, serverMap, serverMap.getMap().getOneBotPerCorridorZone(), 1);
    }

    @Override
    public IRobot makeRobot(BotConfig config) {
        IRobot r = makeDefaultRobot(config.getBotName());
        if (config.getColorBlindHandicap()) {
            r = new ColorBlindHandicap(r);
        }

        r = setGripperHandicap(config, r);

        if (config.getMoveSpeedHandicap()) {
            r.setSpeedMod((double) config.getBotSpeed() / 100.0);
        }
        if (config.getSizeOverloadHandicap()) {
            r = new SizeOverloadHandicap(r, config.getBotSize());
        }
        if (config.getBotController() == EntityType.HUMAN) {
            r = new Human(r);
        }

        r = enableBattery(config, r);

        return r;
    }

    /**
     * Sets the gripper handicap
     * 
     * @param config
     *            file which needs to be read
     * @param r
     *            robot
     * @return robot
     */
    private IRobot setGripperHandicap(BotConfig config, IRobot r) {
        IRobot i = r;
        if (config.getGripperHandicap()) {
            i = new GripperHandicap(r);
        } else {
            // if the robot does not have a gripper handicap, it grabs the value set in the UI.
            i.setGripperCapacity(config.getGrippers());
        }
        return i;
    }

    /**
     * enables the battery
     * 
     * @param config
     *            file which needs to be read.
     * @param r
     *            robot
     * @return robot
     */
    private IRobot enableBattery(BotConfig config, IRobot r) {
        IRobot i = r;
        if (config.isBatteryEnabled()) {
            i.setBattery(new Battery(config.getBotBatteryCapacity(), config.getBotBatteryDischargeRate()));
        } else {
            i.setBattery(new Battery(1, 0));
        }
        return i;
    }

    @Override
    public EPartner makeDefaultEPartner(String name) {
        return new EPartner(name, serverMap);
    }

    @Override
    public EPartner makeEPartner(EPartnerConfig c) {
        EPartner ep = makeDefaultEPartner(c.getEpartnerName());

        if (c.isGps()) {
            ep.getTypeList().add(ViewEPartner.GPS);
        }
        if (c.isForgetMeNot()) {
            ep.getTypeList().add(ViewEPartner.FORGET_ME_NOT);
        }

        return ep;
    }

}
