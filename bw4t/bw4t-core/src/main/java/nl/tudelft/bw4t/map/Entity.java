package nl.tudelft.bw4t.map;

import java.io.Serializable;

/**
 * Initial entity
 * 
 * @author W.Pasman
 */
public class Entity implements Serializable {
    public enum EntityType {
        NORMAL, JAVA
    }

    private String name = "-";
    /**
     * initial position
     */
    private Point position = new Point();
    /**
     * The type of this entity
     */
    private EntityType type = EntityType.NORMAL;

    public Entity() {
    }

    public Entity(String n, Point pos) {
        name = n;
        position = pos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public EntityType getType() {
        return type;
    }
}
