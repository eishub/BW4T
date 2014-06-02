package nl.tudelft.bw4t.robots;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import nl.tudelft.bw4t.handicap.ColorBlindHandicap;
import nl.tudelft.bw4t.handicap.GripperHandicap;
import nl.tudelft.bw4t.handicap.HandicapInterface;
import nl.tudelft.bw4t.handicap.Human;
import nl.tudelft.bw4t.handicap.MoveSpeedHandicap;
import nl.tudelft.bw4t.handicap.SizeOverloadHandicap;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.server.environment.Launcher;

public class DefaultRobotFactory implements RobotFactory {

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
	public HandicapInterface getDefaultRobot(String name) {
		final NewMap map = Launcher.getEnvironment().getMap();
		return new NavigatingRobot(name, space, context, map.getOneBotPerCorridorZone(), 1);
	}

	@Override
	public HandicapInterface getRobot(BotConfig config) {
		HandicapInterface r = getDefaultRobot(config.getBotName());
		if (config.getColorBlindHandicap()) {
			r = new ColorBlindHandicap(r);
		}
		if (config.getGripperHandicap()) {
			r = new GripperHandicap(r);
		}
		if (config.getMoveSpeedHandicap()) {
			//TODO figure out if the magic number is correct
			r = new MoveSpeedHandicap(r, config.getBotSpeed() / 100.);
		}
		if (config.getSizeOverloadHandicap()) {
			r = new SizeOverloadHandicap(r, config.getBotSize());
		}
		if (config.getBotController() == BotConfig.Controller.HUMAN) {
			r = new Human(r);
		}
		r.getSuperParent().setBattery(new Battery(config.getBotBatteryCapacity(), config.getBotBatteryDischargeRate()));
		return r;
	}

}
