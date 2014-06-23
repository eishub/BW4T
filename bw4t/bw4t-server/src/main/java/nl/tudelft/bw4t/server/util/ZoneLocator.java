package nl.tudelft.bw4t.server.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.model.zone.Room;
import nl.tudelft.bw4t.server.model.zone.Zone;
import repast.simphony.space.continuous.NdPoint;

/** Utility class to find a {@link Zone} based on location, name or proximity. */
public final class ZoneLocator {
    
    /** Should never be instantiated.*/
    private ZoneLocator() {
}

    /**
     * Find {@link Zone} containing given point.
     * If there are multiple Zones at the given point, the result is one of these.
     * 
     * @param x
     *            - The {@code x} coordinate of a point.
     * @param y
     *            - The {@code y} coordinate of a point.
     * @return {@link Zone} or {@code null} if no Zone contains the given point.
     */
    public static Zone getZoneAt(double x, double y) {
        Point2D location = new Point2D.Double(x, y);
        Iterable<Object> zones = BW4TEnvironment.getInstance().getContext().getObjects(Zone.class);
        for (Object r : zones) {
            Zone zone = (Zone) r;
            if (zone.getBoundingBox().contains(location)) {
                return zone;
            }
        }
        return null;
    }

    /**
     * Find ALL {@link Zone} containing given point.
     * 
     * @param x
     *            - The {@code x} coordinate of a point.
     * @param y
     *            - The {@code y} coordinate of a point.
     * @return A {@link List} of Zones containing the given point.
     */
    public static List<Zone> getZonesAt(double x, double y) {
        Point2D location = new Point2D.Double(x, y);
        List<Zone> zones = new ArrayList<Zone>();
        Iterable<Object> zoneit = BW4TEnvironment.getInstance().getContext().getObjects(Zone.class);
        for (Object r : zoneit) {
            Zone zone = (Zone) r;
            if (zone.getBoundingBox().contains(location)) {
                zones.add(zone);
            }
        }
        return zones;
    }

    /**
     * Find a {@link Zone} with the given name.
     * 
     * @param name
     *            - The name of the Zone to find.
     * @return {@link Zone} or {@code null} if no Zone exists with that name.
     */
    public static Zone getZone(String name) {
        Iterable<Object> zones = BW4TEnvironment.getInstance().getContext().getObjects(Zone.class);
        for (Object r : zones) {
            Zone zone = (Zone) r;
            if (name.equals(zone.getName())) {
                return zone;
            }
        }
        return null;
    }

    /**
     * Get zone at given point. 
     * If there are multiple zones at the given point, the result is one of these.
     * 
     * @param location 
     *            - A {@link NdPoint} containing the {@code x} and {@code y} coordinates.
     * @return {@link Zone} or {@code null} if no Zone contains the given point.
     */
    public static Zone getZoneAt(NdPoint location) {
        return getZoneAt(location.getX(), location.getY());
    }

    /**
     * Find the {@link Zone} that is nearest to given point. If point is in a Zone, we always return that Zone.
     * If the point is not in a zone, we return the nearest Zone.
     * 
     * @param location
     *            - The location to search for nearby Zones.
     * @return The nearest Zone to the given point. {@code null} if there are no Zones whatsoever.
     */
    public static Zone getNearestZone(NdPoint location) {
        Zone z = ZoneLocator.getZoneAt(location);
        if (z != null) {
            return z;
        }
        return getNearestZoneNotNullNotRoom(location);
    }

    /**
     * Find the {@link Zone} that is nearest to given point. See also {@link #getNearestZone(NdPoint)}.
     * 
     * @param location
     *            - The location to search for nearby Zones.
     * @return The nearest Zone to the given point. {@code null} if there are no Zones whatsoever.
     */
    private static Zone getNearestZoneNotNullNotRoom(NdPoint location) {
        Iterable<Zone> zones = BW4TEnvironment.getInstance().getContext().getObjects(Zone.class);
        Zone nearest = null;
        double nearestdist = Double.MAX_VALUE;

        for (Zone zone : zones) {
            if (zone instanceof Room) {
                continue;
            }
            double dist = zone.distanceTo(location);

            if (dist < nearestdist) {
                nearest = zone;
                nearestdist = dist;
            }
        }
        return nearest;
    }

}
