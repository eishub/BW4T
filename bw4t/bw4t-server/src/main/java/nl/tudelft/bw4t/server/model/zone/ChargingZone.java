package nl.tudelft.bw4t.server.model.zone;

import nl.tudelft.bw4t.server.model.BW4TServerMap;

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
    public ChargingZone(nl.tudelft.bw4t.map.Zone zone, BW4TServerMap context) {
        super(zone, context);
    }
}
