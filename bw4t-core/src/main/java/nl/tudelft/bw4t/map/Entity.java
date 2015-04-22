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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entity other = (Entity) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (type != other.type)
            return false;
        return true;
    }
}
