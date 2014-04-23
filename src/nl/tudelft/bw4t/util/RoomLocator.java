package nl.tudelft.bw4t.util;

import java.awt.geom.Point2D;

import nl.tudelft.bw4t.server.BW4TEnvironment;
import nl.tudelft.bw4t.zone.BlocksRoom;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;

/**
 * Utility class to locate in which rooms an object is.
 * 
 * @author Lennard de Rijk
 */
public final class RoomLocator {

	private RoomLocator() {
	}

	/**
	 * Returns the {@link BlocksRoom} the object is in. Note, {@link DropZone}
	 * is not a {@link BlocksRoom}
	 * 
	 * @param o
	 *            The object to get the room for.
	 * @return The {@link BlocksRoom} the given object is in or null if it is in
	 *         the hall.
	 */
	public static BlocksRoom getRoomFor(double x, double y) {
		Room room = getRoomAt(x, y);
		if (room instanceof BlocksRoom) {
			return (BlocksRoom) room;
		}
		return null;
	}

	/**
	 * Find {@link Room} containing given point. Both BlocksRoom and DropZone
	 * are Room.
	 * 
	 * @param x
	 *            is x coord of point
	 * @param y
	 *            is y coord of point
	 * @return {@link Room} or null if no such room.
	 */
	public static Room getRoomAt(double x, double y) {
		Point2D location = new Point2D.Double(x, y);

		Iterable<Object> rooms = BW4TEnvironment.getInstance().getContext()
				.getObjects(Room.class);
		for (Object r : rooms) {
			Room room = (Room) r;

			if (room.getBoundingBox().contains(location)) {
				return room;
			}
		}

		// Iterable<Object> dropzones = BW4TBuilder.getInstance().getContext()
		// .getObjects(DropZone.class);
		// for (Object dz : dropzones) {
		// DropZone dropzone = (DropZone) dz;
		//
		// if (dropzone.getBoundingBox().contains(location)) {
		// return dropzone;
		// }
		// }

		return null;

	}

}
