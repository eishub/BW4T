package nl.tudelft.bw4t.server.model.zone;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

/**
 * A zone is a square area on the map with a name, neighbours and access restrictions. This mirrors the
 * {@link nl.tudelft.bw4t.map.Zone} in the map.
 */
public abstract class Zone extends BoundedMoveableObject {

    /**
     * name of the zone
     */
    private final String name;
    
    /**
     * sets visibility
     */
    private boolean isLabelVisible = true;

    /**
     * Neighbours of this Zone.
     */
    private Set<Zone> neighbours = new HashSet<Zone>();

    /**
     * Creates a zone
     * @param zone in which this zone needs to be created
     * @param space in which the zone needs to be created
     * @param grid in which the zone needs to be created
     * @param context in which the zone needs to be created
     */
    public Zone(nl.tudelft.bw4t.map.Zone zone, BW4TServerMap context) {
        super(context);
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
    public void addNeighbour(nl.tudelft.bw4t.server.model.zone.Zone neigh) {
        neighbours.add(neigh);
    }

    /**
     * Get occupier of the room
     * 
     * @return Robot, or null if no occupier.
     */
    public AbstractRobot getOccupier() {
        for (Object o : getContext().getObjects(AbstractRobot.class)) {
            AbstractRobot robot = (AbstractRobot) o;
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
     * @param robot that might occupy room
     * @return true if
     */
    public boolean containsMeOrNothing(AbstractRobot robot) {
        AbstractRobot occ = getOccupier();
        return occ == null || robot == occ;
    }

    public boolean isLabelVisible() {
        return isLabelVisible;
    }

}
