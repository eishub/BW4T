package nl.tudelft.bw4t.server.model.zone;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

/**
 * A corridor is a zone outside the rooms.
 */
public class Corridor extends Zone {

    /**
     * Creates the corridor
     * @param zone in which the corridor will be placed
     * @param space in which the corridor will be placed
     * @param grid in which the corridor will be placed
     * @param context in which the corridor will be placed
     */
    public Corridor(nl.tudelft.bw4t.map.Zone zone, ContinuousSpace<Object> space, 
                            Grid<Object> grid, Context<Object> context) {
        super(zone, space, grid, context);
    }

}
