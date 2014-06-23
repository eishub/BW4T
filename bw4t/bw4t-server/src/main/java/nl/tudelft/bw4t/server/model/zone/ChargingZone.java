package nl.tudelft.bw4t.server.model.zone;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

/**
 * Charging zones are zones robots can stand in in order to charge. 
 */
public class ChargingZone extends Zone {

    /**
     * Creates a charging zone
     * @param zone which will be charging zone
     * @param space in which this room will be
     * @param grid in which this room will be
     * @param context in which this room will be
     */
    public ChargingZone(nl.tudelft.bw4t.map.Zone zone, ContinuousSpace<Object> space, 
                                Grid<Object> grid, Context<Object> context) {
        super(zone, space, grid, context);
    }
}
