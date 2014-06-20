package nl.tudelft.bw4t.server.model.robots;

import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.server.environment.Launcher;
import nl.tudelft.bw4t.server.model.epartners.EPartner;
import nl.tudelft.bw4t.server.model.robots.handicap.ColorBlindHandicap;
import nl.tudelft.bw4t.server.model.robots.handicap.GripperHandicap;
import nl.tudelft.bw4t.server.model.robots.handicap.Human;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;
import nl.tudelft.bw4t.server.model.robots.handicap.SizeOverloadHandicap;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

/**
 * Creates the default entities.
 */
public class DefaultEntityFactory implements EntityFactory {

    /**
     * The context which we will be using.
     */
    private Context<Object> context;
    
    /**
     * The space which we will be using.
     */
    private ContinuousSpace<Object> space;
    
    /**
     * The grid which we will be using.
     */
    private Grid<Object> grid;

    
    @Override
    public void setGrid(Grid<Object> grid) {
        this.grid = grid;
    }

    @Override
    public void setContext(Context<Object> context) {
        this.context = context;
    }

    @Override
    public void setSpace(ContinuousSpace<Object> space) {
        this.space = space;
    }

    @Override
    public IRobot makeDefaultRobot(String name) {
        final NewMap map = Launcher.getEnvironment().getMap();
        return new NavigatingRobot(name, space, grid, context, map.getOneBotPerCorridorZone(), 1);
    }

    @Override
    public IRobot makeRobot(BotConfig config) {
        IRobot r = makeDefaultRobot(config.getBotName());
        if (config.getColorBlindHandicap()) {
            r = new ColorBlindHandicap(r);
        }
        
        setGripperHandicap(config, r);
        
        if (config.getMoveSpeedHandicap()) {
            r.setSpeedMod((double) config.getBotSpeed() / 100.0); 
        }
        if (config.getSizeOverloadHandicap()) {
            r = new SizeOverloadHandicap(r, config.getBotSize());
        }
        if (config.getBotController() == EntityType.HUMAN) {
            r = new Human(r);
        }
        
        enableBattery(config, r);

        return r;
    }

    /**
     * Sets the gripper handicap
     * @param config file which needs to be read
     * @param r robot
     * @return
     */
    private void setGripperHandicap(BotConfig config, IRobot r) {
        if (config.getGripperHandicap()) {
            r = new GripperHandicap(r);
        } else {
            // if the robot does not have a gripper handicap, it grabs the value set in the UI. 
            r.setGripperCapacity(config.getGrippers());
        }
    }

    /**
     * enables the battery
     * @param config file which needs to be read.
     * @param r robot
     */
    private void enableBattery(BotConfig config, IRobot r) {
        if (config.isBatteryEnabled()) {
            r.setBattery(
                    new Battery(config.getBotBatteryCapacity(), 
                            config.getBotBatteryDischargeRate()));            
        } else {
            r.setBattery(new Battery(1, 0));
        }
    }

    @Override
    public EPartner makeDefaultEPartner(String name) {
        return new EPartner(name, space, grid, context);
    }

    @Override
    public EPartner makeEPartner(EPartnerConfig c) {
        EPartner ep = makeDefaultEPartner(c.getEpartnerName());
        
        if (c.isGps()) {
            ep.getTypeList().add("GPS");
        }
        if (c.isForgetMeNot()) {
            ep.getTypeList().add("Forget-me-not");
        }
        
        return ep;
    }

}
