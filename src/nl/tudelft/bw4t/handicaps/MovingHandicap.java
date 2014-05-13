package nl.tudelft.bw4t.handicaps;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;
import nl.tudelft.bw4t.zone.Zone;
import repast.simphony.context.Context;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

public class MovingHandicap extends Handicap {

	public MovingHandicap(String name, ContinuousSpace<Object> space,
			Context<Object> context, boolean oneBotPerZone) {
		super(name, space, context, oneBotPerZone);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canPickUp(Block b) {
		double distance = distanceTo(b.getLocation());

		if (distance <= ARM_DISTANCE && b.isFree())
			return true;
		else
			return false;
	}

	@Override
	public void pickUp(Block b) {
		drop();
		holding = b;
		b.setHeldBy(this);
		b.removeFromContext();
	}

	@Override
	public void drop() {
		if (holding != null) {
			// First check if dropped in dropzone, then it won't need to be
			// added to the context again
			DropZone dropZone = (DropZone) context.getObjects(DropZone.class)
					.get(0);
			if (!dropZone.dropped(holding, this)) {
				// bot was not in the dropzone.. Are we in a room?
				Zone ourzone = getZone();
				if (ourzone instanceof Room) {
					// We are in a room so can drop the block
					holding.setHeldBy(null);
					holding.addToContext();
					// Slightly jitter the location where the box is
					// dropped
					double x = ourzone.getLocation().getX();
					double y = ourzone.getLocation().getY();
					holding.moveTo(RandomHelper.nextDoubleFromTo(x - 5, x + 5),
							RandomHelper.nextDoubleFromTo(y - 5, y + 5));
					holding = null;
					return;

				}
			}
			holding = null;
		}
	}

	@Override
	public void moveTo(double x, double y) {
	}

	@Override
	public MoveType getMoveType(double endx, double endy) {
		return MoveType.ENTERING_FREESPACE;
	}

	@Override
	protected MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
		return null;
	}
	/**
	 * Overridden setTartgetLocation method to make moving impossible.
	 */
	@Override
	public synchronized void setTargetLocation(NdPoint targetLocation) {
		this.targetLocation = null;
	}

}
