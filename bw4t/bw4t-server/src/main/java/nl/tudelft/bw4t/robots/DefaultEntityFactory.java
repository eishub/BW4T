package nl.tudelft.bw4t.robots;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import nl.tudelft.bw4t.agent.EntityType;
import nl.tudelft.bw4t.blocks.EPartner;
import nl.tudelft.bw4t.handicap.ColorBlindHandicap;
import nl.tudelft.bw4t.handicap.GripperHandicap;
import nl.tudelft.bw4t.handicap.IRobot;
import nl.tudelft.bw4t.handicap.Human;
import nl.tudelft.bw4t.handicap.MoveSpeedHandicap;
import nl.tudelft.bw4t.handicap.SizeOverloadHandicap;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.server.environment.Launcher;

/**
 * @author Jan Giesenberg
 * @author Valentine Mairet
 */
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
			r.getSuperParent().setGripperCapacity(config.getBotGripperCapacity());
		}
		if (config.getMoveSpeedHandicap()) {
			//TODO figure out if the magic number is correct
			r = new MoveSpeedHandicap(r, config.getBotSpeed() / 100.);
		}
		if (config.getSizeOverloadHandicap()) {
			r = new SizeOverloadHandicap(r, config.getBotSize());
		}
		if (EntityType.HUMAN.isA(config.getBotController())) {
			r = new Human(r);
		}
		r.getSuperParent().setBattery(
				new Battery(config.getBotBatteryCapacity(), config.getBotBatteryDischargeRate()));
		return r;
	}

	@Override
	public EPartner makeDefaultEPartner(String name) {
		return new EPartner(name, space, context);
	}

    @Override
    public EPartner makeEPartner(EPartnerConfig c) {
        EPartner ep = makeDefaultEPartner(c.getName());
        
        if (c.isGps()) {
            ep.getTypeList().add("GPS");
        }
        if (c.isForgotMeNot()) {
            ep.getTypeList().add("Forget-me-not");
        }
        
        return ep;
    }

}
