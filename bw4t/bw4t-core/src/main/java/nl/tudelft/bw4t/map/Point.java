package nl.tudelft.bw4t.map;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Stupid coverr around Point2D.Double. The problem with Point2D.Double is that
 * the XML serializer can not handle it, it gets stuck in an infinite recursion
 * if you try to serialize it.
 */
public class Point implements Serializable {

    /**
     * Serial id.
     */
    private static final long serialVersionUID = -7842093296385905576L;
    
    /** 
     * Initialize point.
     */
    private Point2D.Double point = new Point2D.Double();

    /** 
     * Empty constructor, intialize point.
     */
    public Point() {
    }

    /** Constructor 
     * 
     * @param newx double
     *         x coordinate
     * @param newy double
     *         y coordinate
     */
    public Point(double newx, double newy) {
        point = new Point2D.Double(newx, newy);
    }

    public double getX() {
        return point.x;
    }

    public void setX(double x) {
        point.x = x;
    }

    public double getY() {
        return point.y;
    }

    public void setY(double y) {
        point.y = y;
    }

    public Point2D getPoint2D() {
        return point;
    }

    @Override
    public int hashCode() {
        return point.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Point other = (Point) obj;
        if (!point.equals(other.point)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return point.toString();
    }

}
