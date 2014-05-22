package nl.tudelft.bw4t.map;

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

    public String toString() {
        return "Door[" + position + "," + orientation + "]";
    }

}
