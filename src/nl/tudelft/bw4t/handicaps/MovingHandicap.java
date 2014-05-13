package nl.tudelft.bw4t.handicaps;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.zone.Zone;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;

public class MovingHandicap extends Handicap {

	public MovingHandicap(String name, ContinuousSpace<Object> space,
			Context<Object> context, boolean oneBotPerZone) {
		super(name, space, context, oneBotPerZone);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canPickUp(Block b) {
		return false;
	}

	@Override
	public void pickUp(Block b) {
		
	}

	@Override
	public void drop() {
		
	}

	@Override
	public void moveTo(double x, double y) {
	}

	@Override
	public MoveType getMoveType(double endx, double endy) {
		return null;
	}

	@Override
	protected MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
		return null;
	}

}
