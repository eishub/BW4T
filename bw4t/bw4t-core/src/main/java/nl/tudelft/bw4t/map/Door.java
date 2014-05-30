package nl.tudelft.bw4t.map;

import java.awt.Color;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A door that can be placed in a {@link Zone} in a {@link NewMap}
 * 
 * @author W.Pasman
 */
@XmlRootElement
public class Door implements Serializable {
  public enum Orientation {
      HORIZONTAL, VERTICAL
  };

    /** width of doors. */
    public static final int DOOR_WIDTH = 4;
    /** Thickness of doors. */
    public static final int DOOR_THICKNESS = 1;
    public static final Color COLOR_CLOSED = Color.RED;
    public static final Color COLOR_OPEN = Color.GREEN;

    private Point position = new Point();
    private Orientation orientation = Orientation.HORIZONTAL;

    public Door() {
    }

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

}
