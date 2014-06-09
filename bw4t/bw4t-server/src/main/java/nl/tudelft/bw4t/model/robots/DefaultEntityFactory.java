package nl.tudelft.bw4t.model.robots;

import nl.tudelft.bw4t.agent.EntityType;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.model.epartners.EPartner;
import nl.tudelft.bw4t.model.robots.handicap.ColorBlindHandicap;
import nl.tudelft.bw4t.model.robots.handicap.GripperHandicap;
import nl.tudelft.bw4t.model.robots.handicap.Human;
import nl.tudelft.bw4t.model.robots.handicap.IRobot;
import nl.tudelft.bw4t.model.robots.handicap.SizeOverloadHandicap;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.server.environment.Launcher;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;

public class DefaultEntityFactory implements EntityFactory {

	private Context<Object> context;
	private ContinuousSpace<Object> space;

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
		return new NavigatingRobot(name, space, context, map.getOneBotPerCorridorZone(), 1);
	}

	@Override
	public IRobot makeRobot(BotConfig config) {
		IRobot r = makeDefaultRobot(config.getBotName());
		if (config.getColorBlindHandicap()) {
			r = new ColorBlindHandicap(r);
		}
		if (config.getGripperHandicap()) {
			r = new GripperHandicap(r);
		} else {
			// if the robot does not have a gripper handicap, it grabs the value set in the UI. 
			r.setGripperCapacity(config.getGrippers());
		}
		if (config.getMoveSpeedHandicap()) {
			r.setSpeedMod((double) config.getBotSpeed() / 100.0); 
		}
		if (config.getSizeOverloadHandicap()) {
			r = new SizeOverloadHandicap(r, config.getBotSize());
		}
		if (config.getBotController() == EntityType.HUMAN) {
			r = new Human(r);
		}
		if (config.isBatteryEnabled()) {
			r.setBattery(
					new Battery(config.getBotBatteryCapacity(), 
							config.getBotBatteryDischargeRate()));			
		} else {
			r.setBattery(new Battery(1, 0));
		}

		return r;
	}

	@Override
	public EPartner makeDefaultEPartner(String name) {
		return new EPartner(name, space, context);
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
