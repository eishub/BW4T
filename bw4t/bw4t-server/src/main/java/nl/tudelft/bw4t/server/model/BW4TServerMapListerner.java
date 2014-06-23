package nl.tudelft.bw4t.server.model;

import repast.simphony.context.Context;
import nl.tudelft.bw4t.map.NewMap;

/**
 * This interface allows a class to listen to change in a {@link BW4TServerMap}.
 */
public interface BW4TServerMapListerner {
    /**
     * This function is called when the repast {@link Context} is changed.
     * @param map the server map that was changed
     */
    void contextChange(BW4TServerMap map);
    
    /**
     * This function is called when the {@link NewMap} is changed.
     * @param map the server map that was changed
     */
    void mapChange(BW4TServerMap map);
}
