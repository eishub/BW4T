package nl.tudelft.bw4t.zone;

import java.awt.Color;

import nl.tudelft.bw4t.map.Zone;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;

public class Blockade extends Room {

    public Blockade(Color c, Zone roomzone, ContinuousSpace<Object> space, Context<Object> context) {
        super(c, roomzone, space, context);
    }

}
