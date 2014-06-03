package nl.tudelft.bw4t.map.view;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Entity {
    

    private String name = "";

    private final Map<Long, Block> holding = new HashMap<>();

    private Point2D location;

    /** The width and height of the robot */
    public final static int ROBOT_SIZE = 2;
    public final static Color ROBOT_COLOR = Color.BLACK;
    
    public Entity(){
        location = new Point2D.Double();
    }

    public Entity(String name, double x, double y, Collection<Block> holding) {
        this.setName(name);
        this.setLocation(x, y);
        this.setHolding(holding);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, Block> getHolding() {
        return holding;
    }

    public void setHolding(Collection<Block> holding) {
        this.getHolding().clear();
        for (Block block : holding) {
            this.getHolding().put(block.getObjectId(), block);
        }
    }

    public Block getFirstHolding() {
        Iterator<Block> blocks = holding.values().iterator();
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
}
