package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.robots.MoveType;
import nl.tudelft.bw4t.zone.Corridor;
import nl.tudelft.bw4t.zone.Zone;

public class SizeOverloadHandicap extends Handicap {
	
	public boolean isActive;
	
	/**
	 * Calls the super method on p,
	 * Sets the handicap to active,
	 * Adds the handicap to the robot handicap storage.
	 * @param p HandicapInterface the SizeOverloadHandicap wraps around.
	 */
	public SizeOverloadHandicap(HandicapInterface p, int s) {
		super(p);
		isActive = true;
		robot.setSize(s);
		robot.getHandicapsMap().put("SizeOverload", this);
	}
	
	/**
	 * Override of the checkZoneAccess method,
	 * The robot cannot access zones anymore.
	 */
	@Override
	public MoveType checkZoneAccess(Zone startzone, Zone endzone, Door door) {
		if (isActive && robot.getSize() >= 4) {
			if (startzone == endzone) {
				return MoveType.SAME_AREA;
			}
		
			/**
			 * A zone switch is attempted as either startzone or endzone is not
			 * null.
			 */
			// Removed the case where the endzone would be a room.
			// Disabling moving through doors means that the bot cannot enter rooms.
			/**
			 * Both sides are not a room. Check if target accesible
			 */
			else if (endzone instanceof Corridor) {
				if (!robot.isOneBotPerZone() || endzone.containsMeOrNothing(robot)) {
					return MoveType.ENTER_CORRIDOR;
				}
				return MoveType.HIT_OCCUPIED_ZONE;
			}
		}
		else {
			super.checkZoneAccess(startzone, endzone, door);
		}
		return MoveType.ENTERING_FREESPACE;
	}
	
	/**
	 * Activate the handicap.
	 */
	public void activate()
	{
		isActive = true;
	}
	
	/**
	 * Deactivate the handicap.
	 */
	public void deactivate()
	{
		isActive = false;
	}
}
