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
    
    /** Initialize color for robot to black. */
    public final static Color ROBOT_COLOR = Color.BLACK;

    /** Initialize id, default 0 */
    private long id = 0;
    
    /** Initialize name, default empty. */
    private String name = "";

    /** Initialize Map which can store which blocks the robot holds. */
    private final Map<Long, ViewBlock> holding = new HashMap<>();

    /** Initialize location */
    private Point2D location = new Point2D.Double();
    
    /** Initialize size of robot */
    private int robotsize = 2;
    
    /** Initialize holdingEpartner, default -1 */
    private long holdingEpartner = -1;
    
    /** Initialize collided to false. */
    private boolean collided = false;

    /** Initialize batteryLevel to 0. */
    private double batteryLevel = 0.0;

    /** 
     * Empty constructor, initialize default object.
     */
    public ViewEntity() {
    }

    /**
     * Constructor.
     * 
     * @param id long
     * @param name String
     * @param x coordinate location
     * @param y coordinate location
     * @param holding blocks
     * @param robotsize int
     */
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

    /**
     * @param holding
     *             remove current holdings, set to new holding.
     */
    public void setHolding(Collection<ViewBlock> holding) {
        this.getHolding().clear();
        for (ViewBlock block : holding) {
            this.getHolding().put(block.getObjectId(), block);
        }
    }

    /**
     * @return ViewBlock which is hold first, null if robot holds no blocks
     */
    public ViewBlock getFirstHolding() {
        Iterator<ViewBlock> blocks = holding.values().iterator();
        if (blocks.hasNext()) {
            return blocks.next();
        }
        return null;
    }

    /**
     * @return Color of first block holding;
     *                 When no block is hold, return color of robot.
     */
    public Color getColor() {
        if (holding.isEmpty()) {
            return ROBOT_COLOR;
        }
        return getFirstHolding().getColor().getColor();
    }

    public Point2D getLocation() {
        return location;
    }

    /**
     * @param x coordinate of location
     * @param y coordinate of location
     */
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

    public void setCollided(boolean collided) { 
        this.collided = collided; 
    }

    public double getBatteryLevel() { 
        return batteryLevel;   
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
