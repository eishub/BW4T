package nl.tudelft.bw4t.blocks;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.robots.Robot;

/**
 * @author Valentine Mairet & Ruben Starmans
 */
public class EPartner extends BoundedMoveableObject{

	/**
	 * type of the E-Partner
	 */
	private String eType;
	
	/**
	 * Robot that picked up the E-Partner
	 */
	private Robot holder;
	/**
	 * 
	 * @param type Type of E-Partner
	 * @param space
	 * @param context
	 */
	public EPartner(String type, ContinuousSpace<Object> space, Context<Object> context) {
		super(space, context);
		eType = type;
	}
	
	
	
	/**
	 * @param holder Robot that picked up the E-Partner
	 */
	public void setHolder(Robot hold){
		this.holder = hold;
	}
	
}
