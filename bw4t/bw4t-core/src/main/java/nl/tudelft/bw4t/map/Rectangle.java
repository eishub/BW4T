package nl.tudelft.bw4t.map;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Stupid replacement for Rectangle2D.Double. The problem with Rectangle2D.Double is that the XML serializer can not
 * handle it, it gets stuck in an infinite recursion if you try to serialize it.
 */
public class Rectangle implements Serializable {
    
    /** 
     * Serialization id. 
     */
    private static final long serialVersionUID = 24724175226119920L;
    
    /**
     * Initialize rectangle.
     */
    private final Rectangle2D.Double rectangle = new Rectangle2D.Double();

    /** 
     * Empty constructor, initialize Rectangle.
     */
    public Rectangle() {
    }

    /**
     * @param x
     *            CENTER x
     * @param y
     *            CENTER y
     * @param w
     *             Width
     * @param h
     *             Height
     */
    public Rectangle(double x, double y, double w, double h) {
        setWidth(w);
        setHeight(h);
        setX(x);
        setY(y);
    }

    /**
     * Get a copy of the underlying Rectangle.
     * 
     * @return the rectangle.
     */
    public Rectangle2D getRectangle() {
        Rectangle2D copy = new Rectangle2D.Double();
        copy.setRect(rectangle);
        return copy;
    }

    public double getX() {
        return rectangle.getCenterX();
    }

    public void setX(double x) {
        // can't set center directly..
        rectangle.x = x - getWidth() / 2;
    }

    public double getY() {
        return rectangle.getCenterY();
    }

    public void setY(double y) {
        // can't set center directly..
        rectangle.y = y - getHeight() / 2;
    }

    public double getWidth() {
        return rectangle.getWidth();
    }

    /**
     * @param w width
     *             Set width to w. 
     *             Moves the left side such that center remains at same place.
     */
    public void setWidth(double w) {
        double oldwidth = getWidth();
        rectangle.width = w;
        setX(getX() - (w - oldwidth) / 2.);
    }

    public double getHeight() {
        return rectangle.getHeight();
    }

    /**
     * @param h height
     *             Set height to h.
     */
    public void setHeight(double h) {
        double oldheight = getHeight();
        rectangle.height = h;
        setY(getY() - (h - oldheight) / 2.);
    }

    /** 
     * @param p point
     * @return true when p is in the rectangle. 
     */
    public boolean contains(Point p) {
        return rectangle.contains(p.getPoint2D());
    }

    @Override
    public int hashCode() {
        return rectangle.hashCode();
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
        Rectangle other = (Rectangle) obj;
        if (!rectangle.equals(other.rectangle)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return rectangle.toString();
    }

}
