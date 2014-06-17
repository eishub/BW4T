package nl.tudelft.bw4t.map.view;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * information about an e-partner to be displayed on the map.
 */
public class ViewEPartner {
    
    public static final int EPARTNER_SIZE = 1;
    public static final Color EPARTNER_OFFLINE = Color.YELLOW;
    public static final Color EPARTNER_ONLINE = Color.GREEN; 
    
    private long id = 0;
    private Point2D location = new Point2D.Double();
    private boolean pickedUp = false;
    //private String name = "";

    public ViewEPartner() {
    }

    public ViewEPartner(long id, Point2D location, boolean isPickedUp/*, String name*/) {
        this.setId(id);
        this.location = location;
        this.pickedUp = isPickedUp;
        //this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }
    
    /**
     * Get the current color of the e-partner, depending on whether it is online or not.
     * @return the color of the e-partner
     */
    public Color getColor() {
        if(isPickedUp()){
            return EPARTNER_ONLINE;
        } else {
            return EPARTNER_OFFLINE;
        }
    }
    
  /*  public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }*/

}
