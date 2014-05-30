package nl.tudelft.bw4t.blocks;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.robots.Robot;

/**
 * @author Valentine Mairet & Ruben Starmans
 */
public class EPartner extends BoundedMoveableObject {
    
    /**
     * Robot that picked up the E-Partner
     */
    private Robot holder;

    /**
     * 
     * @param type
     *            Type of E-Partner
     * @param space
     * @param context
     */
    public EPartner(ContinuousSpace<Object> space, Context<Object> context) {
        super(space, context);
    }

    /**
     * @param holder
     *            Robot that picked up the E-Partner
     */
    public void setHolder(Robot robot){
        this.holder = robot;
    }
    
    public Robot getHolder(){
        return this.holder;
  }
    
}
