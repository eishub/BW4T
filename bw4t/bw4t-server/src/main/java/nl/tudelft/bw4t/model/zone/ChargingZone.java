package nl.tudelft.bw4t.model.zone;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;

/**
 * Charging zones are zones robots can stand in in order to charge. 
 */
public class ChargingZone extends Zone {

    public ChargingZone(nl.tudelft.bw4t.map.Zone zone, ContinuousSpace<Object> space, Context<Object> context) {
        super(zone, space, context);
    }
}
