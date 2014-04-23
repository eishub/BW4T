package nl.tudelft.bw4t.visualizations;

import java.awt.Color;

import nl.tudelft.bw4t.zone.Room;

public class RoomStyle extends BoundedMoveableObjectStyle<Room> {
	@Override
	public Color getColor(Room room) {
		return room.getColor();
	}
}
