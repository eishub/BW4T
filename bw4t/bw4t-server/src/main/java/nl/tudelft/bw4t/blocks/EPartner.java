package nl.tudelft.bw4t.blocks;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.handicap.HandicapInterface;

/**
 * @author Valentine Mairet & Ruben Starmans
 */
public class EPartner extends BoundedMoveableObject {
    
    /**
     * Human that picked up the E-Partner
     */
    private HandicapInterface holder;

    /**
     * @param space
     * @param context
     */
    public EPartner(ContinuousSpace<Object> space, Context<Object> context) {
        super(space, context);
    }

    public void setHolder(HandicapInterface human){
        this.holder = human;
    }
    
    public HandicapInterface getHolder(){
        return this.holder;
  }
    
}
