package nl.tudelft.bw4t.server.model.zone;

import nl.tudelft.bw4t.server.model.BW4TServerMap;

/**
 * A corridor is a zone outside the rooms.
 */
public class Corridor extends Zone {

    /**
     * Creates the corridor
     * @param zone in which the corridor will be placed
     * @param context in which the corridor will be placed
     */
    public Corridor(nl.tudelft.bw4t.map.Zone zone, BW4TServerMap context) {
        super(zone, context);
    }

}
