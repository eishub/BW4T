package nl.tudelft.bw4t.server.model.epartners;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;
import repast.simphony.space.continuous.NdPoint;

/**
 * This class makes it possible to create an EPartner for the Human.
 */
public class EPartner extends BoundedMoveableObject {    
    /**
     * Human that owns the E-Partner.
     */
    private IRobot holder;
    
    /**
     * Name of the E-Partner.
     */
    private String name;
    
    /**
     * The functionality list of the E-Partner.
     */
    private List<String> funcList;
    
    /**
     * The view of the E-Partner.
     */
    private ViewEPartner view = new ViewEPartner();

    /**
     * @param n
     *            The "human-friendly" name of the e-Partner.
     * @param smap
     *            The context in which the epartner operates.
     */
    public EPartner(String n, BW4TServerMap smap) {
        super(smap);
        
        view.setId(getId());
        this.setName(n);
        setTypeList(new ArrayList<String>());
    }

    public IRobot getHolder() {
        return this.holder;
    }
    
    /**
     * Sets the holder to human and pickedUp to a not null value.
     * @param human
     *          The human which is being held.
     */
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

    /**
     * Returns the view of the current E-Partner.
     * @return
     *      Returns the view.
     */
    public ViewEPartner getView() {
        final NdPoint location = getLocation();
        this.view.setLocation(new Point2D.Double(location.getX(), location.getY()));
        return this.view;
    } 
}
