package nl.tudelft.bw4t.zone;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.robots.Robot;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

/**
 * A zone is a square area on the map with a name, neighbours and access
 * restrictions. This mirrors the {@link nl.tudelft.bw4t.map.Zone} in the map.
 * 
 * @author W.Pasman 4nov2013
 * 
 */
public abstract class Zone extends BoundedMoveableObject {

	private final String name;
	private boolean isLabelVisible = true;

	/**
	 * Neighbours of this Zone.
	 */
	private Set<Zone> neighbours = new HashSet<Zone>();

	public Zone(nl.tudelft.bw4t.map.Zone zone, ContinuousSpace<Object> space,
			Context<Object> context) {
		super(space, context);
		name = zone.getName();
		if (zone.getRenderOptions() != null) {
			isLabelVisible = zone.getRenderOptions().isLabelVisible();
		}
		double x = zone.getBoundingbox().getX();
		double y = zone.getBoundingbox().getY();

		double width = zone.getBoundingbox().getWidth();
		double height = zone.getBoundingbox().getHeight();
		setSize(width, height);
		moveTo(x, y);

	}

	public String getName() {
		return name;
	}

	public void setNeighbours(Set<Zone> neighbours) {
		this.neighbours = neighbours;
	}

	public Set<Zone> getNeighbours() {
		return neighbours;
	}

	/**
	 * Ad a neighbour to the list of neighbours.
	 * 
	 * @param neigh
	 *            new neighbour for this zone.
	 */
	public void addNeighbour(nl.tudelft.bw4t.zone.Zone neigh) {
		neighbours.add(neigh);
	}

	/**
	 * Get occupier of the room
	 * 
	 * @return Robot, or null if no occupier.
	 */
	public Robot getOccupier() {
		for (Object o : context.getObjects(Robot.class)) {
			Robot robot = (Robot) o;
			NdPoint loc = robot.getLocation();
			if (getBoundingBox().contains(loc.getX(), loc.getY())) {
				return robot;
			}
		}
		return null;
	}

	/**
	 * Check that this zone is not occupied by given robot
	 * 
	 * @param robot
	 * @return true if
	 */
	public boolean containsMeOrNothing(Robot robot) {
		Robot occ = getOccupier();
		return occ == null || robot == occ;
	}

	public boolean isLabelVisible() {
		return isLabelVisible;
	}

}
