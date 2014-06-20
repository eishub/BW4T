package nl.tudelft.bw4t.map;

import java.io.Serializable;

/**
 * Initial entity
 */
public class Entity implements Serializable {
    
    /** Serialization id. */
    private static final long serialVersionUID = -2928851566063046519L;
    
    /** Possible entityTypes. */
    public enum EntityType {
        /** EntityType is either NORMAL or JAVA. */
        NORMAL, JAVA
    }

    /** Name of entity, default -. */
    private String name = "-";
    
    /** Initial position */
    private Point position = new Point();
   
    /** The type of this entity */
    private EntityType type = EntityType.NORMAL;

    /** 
     * Empty constructor, initialize default variables.
     */
    public Entity() {
    }

    /**
     *  Constructor. 
     * 
     * @param n String, name of entity
     * @param pos Point, position of entity
     */
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
