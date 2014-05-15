package nl.tudelft.bw4t.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.server.BW4TEnvironment;
import nl.tudelft.bw4t.zone.Corridor;
import nl.tudelft.bw4t.zone.Zone;
import repast.simphony.space.continuous.NdPoint;

/**
 * Util class to find back zones based on location, name etc
 * 
 * @author W.Pasman 4nov2013
 * 
 */
public class ZoneLocator {
	/**
	 * Find {@link Zone} containing given point.
	 * 
	 * @param x
	 *            is x coord of point
	 * @param y
	 *            is y coord of point
	 * @return {@link Zone} or null if no such zone.
	 */
	public static Zone getZoneAt(double x, double y) {
		Point2D location = new Point2D.Double(x, y);

		Iterable<Object> zones = BW4TEnvironment.getInstance().getContext()
				.getObjects(Zone.class);
		for (Object r : zones) {
			Zone zone = (Zone) r;

			if (zone.getBoundingBox().contains(location)) {
				return zone;
			}
		}

		return null;
	}

	/**
	 * get ALL zones containing given position.
	 * 
	 * @param endx
	 * @param endy
	 * @return
	 */
	public static List<Zone> getZonesAt(double x, double y) {
		Point2D location = new Point2D.Double(x, y);
		List<Zone> zones = new ArrayList<Zone>();
		Iterable<Object> zoneit = BW4TEnvironment.getInstance().getContext()
				.getObjects(Zone.class);
		for (Object r : zoneit) {
			Zone zone = (Zone) r;

			if (zone.getBoundingBox().contains(location)) {
				zones.add(zone);
			}
		}
		return zones;

	}

	/**
	 * Find {@link Zone} of given name.
	 * 
	 * @param name
	 *            the required name.
	 * @return {@link Zone} or null if no such zone.
	 */
	public static Zone getZone(String name) {

		Iterable<Object> zones = BW4TEnvironment.getInstance().getContext()
				.getObjects(Zone.class);
		for (Object r : zones) {
			Zone zone = (Zone) r;

			if (name.equals(zone.getName())) {
				return zone;
			}
		}

		return null;
	}

	/**
	 * Get zone at given location. If there are multiple zones at given
	 * location, the result is one of these.
	 * 
	 * @param location
	 * @return
	 */

	public static Zone getZoneAt(NdPoint location) {
		return getZoneAt(location.getX(), location.getY());
	}

	/**
	 * Find the {@link Zone} that is nearest to given point. If point is in a
	 * zone, we always return that zone. If point is not in a zone, we return
	 * the nearest Corridor.
	 * 
	 * @param location
	 *            the location for which nearby Zone is needed
	 * @return nearest navpoint in the room.
	 */

	public static Zone getNearestZone(NdPoint location) {
		Zone z = ZoneLocator.getZoneAt(location);
		if (z != null)
			return z;

		return getNearestCorridorZone(location);
	}

	/**
	 * Find the {@link Corridor} that is nearest to given point. It is assumed
	 * that the point is NOT inside a zone. See also
	 * {@link #getNearestZone(NdPoint)}
	 * 
	 * @param location
	 *            the location for which nearby navpoint is needed
	 * @return nearest Corridor. Null if there are no Corridors available
	 *         (should never happen?).
	 */

	private static Corridor getNearestCorridorZone(NdPoint location) {
		Iterable<Object> corridors = BW4TEnvironment.getInstance().getContext()
				.getObjects(Corridor.class);
		Corridor nearest = null;
		double nearestdist = Double.MAX_VALUE;

		for (Object o : corridors) {
			Corridor corridor = (Corridor) o;
			Point2D p = new Point2D.Double(corridor.getLocation().getX(),
					corridor.getLocation().getY());
			double dist = corridor.distanceTo(location);

			if (dist < nearestdist) {
				nearest = corridor;
				nearestdist = dist;
			}
		}
		return nearest;
	}

}
