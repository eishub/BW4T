package nl.tudelft.bw4t.map;

import java.awt.Color;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A door that can be placed in a {@link Zone} in a {@link NewMap}
 */
@XmlRootElement
public class Door implements Serializable {
  
    /** Serialization id. */
    private static final long serialVersionUID = -4173196906728316076L;

    /** Define possible orientation. */
    public enum Orientation {
        /** Orientation either HORIZONTAL or VERTICAL. */
       HORIZONTAL, VERTICAL
  }

    /** Width of doors. */
    public static final int DOOR_WIDTH = 4;
   
    /** Thickness of doors. */
    public static final int DOOR_THICKNESS = 1;
    
    /** Color of closed door, default red. */
    public static final Color COLOR_CLOSED = Color.RED;
    
    /** Color of open door, default green. */
    public static final Color COLOR_OPEN = Color.GREEN;

    /** Position of the door. */
    private Point position = new Point();
    
    /** Orientation of door, default Horizontal. */
    private Orientation orientation = Orientation.HORIZONTAL;

    /**
     * Empty constructor, initialize default variables.
     */
    public Door() {
    }

    /** Constructor. 
     * 
     * @param pos Point which is the position of the door
     * @param or Orientation, either Horizontal of Vertical
     */
    public Door(Point pos, Orientation or) {
        position = pos;
        orientation = or;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public double getWidth() {
        return (orientation == Orientation.HORIZONTAL) ? DOOR_WIDTH : DOOR_THICKNESS;
    }

    public double getHeight() {
        return (orientation == Orientation.HORIZONTAL) ? DOOR_THICKNESS : DOOR_WIDTH;
    }

    @Override
    public String toString() {
        return "Door[" + position + "," + orientation + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
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
        Door other = (Door) obj;
        if (orientation != other.orientation)
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }

}
