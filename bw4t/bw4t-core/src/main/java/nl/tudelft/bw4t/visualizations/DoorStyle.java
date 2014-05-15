package nl.tudelft.bw4t.visualizations;

import java.awt.Color;

import nl.tudelft.bw4t.doors.Door;

public class DoorStyle extends BoundedMoveableObjectStyle<Door> {
	@Override
	public Color getColor(Door door) {
		return door.getColor();
	}
}
