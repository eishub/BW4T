package nl.tudelft.bw4t.server.model.zone;

import java.awt.Color;

import nl.tudelft.bw4t.server.model.BW4TServerMap;

/**
 * A room which might have block in it on initialization.
 */
public class BlocksRoom extends Room {

    /** Variables to keep track of the color cycle for this type of room */
    private static int count;
    /**
     * The initial colors
     */
    private static Color[] cycle = {Color.YELLOW, Color.GREEN, Color.PINK};

    /**
     * Creates a new room in which block might be placed.
     * 
     * @param context
     *            The context in which the room will be located.
     * @param roomzone
     *            The room in which the room will be located.
     */
    public BlocksRoom(nl.tudelft.bw4t.map.Zone roomzone, BW4TServerMap context) {
        super(cycle[count % cycle.length], roomzone, context);
        setCount(); 
    }
    
    /**
     * Increases the counter which keeps track of the color cycle
     */
    private static void setCount() {
        count++; 
    }
}
