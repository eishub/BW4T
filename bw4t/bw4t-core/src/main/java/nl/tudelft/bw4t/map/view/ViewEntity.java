package nl.tudelft.bw4t.map.view;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * information about an robot for the map renderer.
 */
public class ViewEntity {
    /** The width and height of the robot */

    public final static Color ROBOT_COLOR = Color.BLACK;

    private long id = 0;
    
    private String name = "";

    private final Map<Long, ViewBlock> holding = new HashMap<>();

    private Point2D location;
    
    private int robotsize = 2;
    
    private long holdingEpartner = -1;
    
    private boolean collided = false;

    private double batteryLevel = 0.0;

    public ViewEntity(){
        location = new Point2D.Double();
    }

    public ViewEntity(long id, String name, double x, double y, Collection<ViewBlock> holding, int robotsize) {
    	this.setId(id);
        this.setName(name);
        this.setLocation(x, y);
        this.setHolding(holding);
        this.setSize(robotsize);
    }
    
    public long getId() {
    	return id;
    }
    
    public void setId(long id) {
    	this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, ViewBlock> getHolding() {
        return holding;
    }

    public void setHolding(Collection<ViewBlock> holding) {
        this.getHolding().clear();
        for (ViewBlock block : holding) {
            this.getHolding().put(block.getObjectId(), block);
        }
    }

    public ViewBlock getFirstHolding() {
        Iterator<ViewBlock> blocks = holding.values().iterator();
        if (blocks.hasNext()) {
            return blocks.next();
        }
        return null;
    }

    public Color getColor() {
        if (holding.isEmpty()) {
            return ROBOT_COLOR;
        }
        return getFirstHolding().getColor().getColor();
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(double x, double y) {
        location = new Point2D.Double(x, y);
    }
    
    public int getRobotSize() {
    	return robotsize;
    }
    
    public void setSize(int robotsize) {
    	this.robotsize = robotsize;
    }

    public long getHoldingEpartner() {
        return holdingEpartner;
    }

    public void setHoldingEpartner(long holdingEpartner) {
        this.holdingEpartner = holdingEpartner;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) { this.collided = collided; }

    public double getBatteryLevel() { return batteryLevel;   }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
