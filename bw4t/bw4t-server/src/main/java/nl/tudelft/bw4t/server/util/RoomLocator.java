package nl.tudelft.bw4t.server.util;

import java.awt.geom.Point2D;

import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.model.zone.BlocksRoom;
import nl.tudelft.bw4t.server.model.zone.DropZone;
import nl.tudelft.bw4t.server.model.zone.Room;

/**
 * Utility class to locate in which rooms an object is.
 */
public final class RoomLocator {

    /**
     * Utility class, cannot be instantiated.
     */
    private RoomLocator() {
    }

    /**
     * Returns the {@link BlocksRoom} the object is in. Note, {@link DropZone} is not a {@link BlocksRoom}
     * 
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     * @return The {@link BlocksRoom} the given object is in or null if it is in the hall.
     */
    public static BlocksRoom getRoomFor(double x, double y) {
        Room room = getRoomAt(x, y);
        if (room instanceof BlocksRoom) {
            return (BlocksRoom) room;
        }
        return null;
    }

    /**
     * Find {@link Room} containing given point. Both BlocksRoom and DropZone are Room.
     * 
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     * @return {@link Room} or null if no such room.
     */
    public static Room getRoomAt(double x, double y) {
        Point2D location = new Point2D.Double(x, y);
        Iterable<Object> rooms = BW4TEnvironment.getInstance().getContext().getObjects(Room.class);
        for (Object r : rooms) {
            Room room = (Room) r;

            if (room.getBoundingBox().contains(location)) {
                return room;
            }
        }
        return null;

    }

}
