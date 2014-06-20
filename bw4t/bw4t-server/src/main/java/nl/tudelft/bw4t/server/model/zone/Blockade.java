package nl.tudelft.bw4t.server.model.zone;

import nl.tudelft.bw4t.map.Zone;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

/**
 * Constructs a blockade for a room.
 */
public class Blockade extends BlocksRoom {

    /**
     * Creates a blockade.
     * 
     * @param roomzone
     *          The {@link Zone} in which the blockade will be located.
     * @param space
     *          The {@link Space} in which the blockade will be located.
     * @param grid
     *          The {@link Grid} in which the blockade will be located.
     * @param context
     *          The {@link Context} in which the blockade will be located.
     */
    public Blockade(Zone roomzone, ContinuousSpace<Object> space, Grid<Object> grid, Context<Object> context) {
        super(space, grid, context, roomzone);
    }

}
