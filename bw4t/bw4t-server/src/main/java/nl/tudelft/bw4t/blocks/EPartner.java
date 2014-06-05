package nl.tudelft.bw4t.blocks;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.handicap.IRobot;
import nl.tudelft.bw4t.map.view.ViewEPartner;

import org.apache.log4j.Logger;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

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
     * Human that owns the E-Partner
     */
    private IRobot holder;
    
    private String name;
    private List<String> funcList;
    
    private ViewEPartner view = new ViewEPartner();

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
        
        view.setId(getId());
        this.setName(n);
        setTypeList(new ArrayList<String>());
    }

    public IRobot getHolder() {
        return this.holder;
    }
    
    public void setHolder(IRobot human) {
        this.holder = human;
        view.setPickedUp(holder != null);
    }

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTypeList() {
		return this.funcList;
	}

	public void setTypeList(ArrayList<String> fList) {
		this.funcList = fList;
	}
	
	public boolean isDropped() {
		return this.holder == null;
	}

    public ViewEPartner getView() {
        final NdPoint location = getLocation();
        this.view.setLocation(new Point2D.Double(location.getX(), location.getY()));
        return this.view;
    }
    
}
