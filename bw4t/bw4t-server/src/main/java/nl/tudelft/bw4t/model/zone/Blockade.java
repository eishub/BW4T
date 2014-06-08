package nl.tudelft.bw4t.model.zone;

import java.awt.Color;

import nl.tudelft.bw4t.map.Zone;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;

public class Blockade extends BlocksRoom {

    public Blockade(Zone roomzone, ContinuousSpace<Object> space, Context<Object> context) {
        super(space, context, roomzone);
    }

}
