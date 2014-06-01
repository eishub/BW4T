package nl.tudelft.bw4t.blocks;

import java.util.ArrayList;
import java.util.HashSet;

import org.apache.log4j.Logger;

import eis.exceptions.EntityException;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.handicap.HandicapInterface;
import nl.tudelft.bw4t.robots.AgentRecord;
import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;

/**
 * @author Valentine Mairet
 */
public class EPartner extends BoundedMoveableObject {
	
	private static final Logger LOGGER = Logger.getLogger(EPartner.class);
	
    /**
     * set to true when {@link #connect()} is called.
     */
    private boolean connected = false;
    
    /**
     * Human that picked up the E-Partner
     */
    private HandicapInterface holder;
    
    private String name;
    private ArrayList<String> typeList;

    /**
     * @param n
     *            The "human-friendly" name of the e-Partner.
     * @param space
     *            The space in which the robot operates.
     * @param context
     *            The context in which the robot operates.
     */
    public EPartner(String n, ContinuousSpace<Object> space, Context<Object> context) {
        super(space, context);
        
        this.name = n;
        typeList = new ArrayList<String>();
    }
    
    /**
     * called when e-Partner becomes connected and should now be injected in repast.
     */
    public void connect() {
        connected = true;
    }

    /**
     * called when e-Partner should be disconnected.
     */
    public void disconnect() {
        connected = false;
    }

    public HandicapInterface getHolder() {
        return this.holder;
    }
    
    public void setHolder(HandicapInterface human) {
        this.holder = human;
    }

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
