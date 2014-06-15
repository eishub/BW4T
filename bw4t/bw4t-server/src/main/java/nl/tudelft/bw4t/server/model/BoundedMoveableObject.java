package nl.tudelft.bw4t.server.model;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

/**
 * Represents an object in the world that can be moved around if needed. It forms the basis for all kinds of objects
 * like robots, blocks and rooms.
 */
public abstract class BoundedMoveableObject {

    private static final AtomicLong COUNTER = new AtomicLong();

    private final long id;
    public final ContinuousSpace<Object> space;
    public final Grid<Object> grid;
    protected final Context<Object> context;
    protected final Rectangle2D.Double boundingBox;

    /**
     * Creates a new object bounded by a box at (0,0) with size (0,0).
     *  @param space
     *            the space in which the object should be placed.
     * @param grid
     * @param context
     */
    public BoundedMoveableObject(ContinuousSpace<Object> space, Grid<Object> grid, Context<Object> context) {
        if (context.isEmpty()) {
            COUNTER.set(0);
        }
        this.id = COUNTER.getAndIncrement();
        this.space = space;
        this.grid = grid;
        this.context = context;
        this.boundingBox = new Rectangle2D.Double();
        context.add(this);
    }

    /**
     * @return The unique id of the object.
     */
    public long getId() {
        return id;
    }

    /**
     * @return The context in which the object might be placed in.
     */
    public Context<Object> getContext() {
        return context;
    }

    /**
     * @return The location of the object, if currently in a space.
     */
    public NdPoint getLocation() {
        NdPoint location = space.getLocation(this);
        //ugly fix for NullPointerException when adding entity after the display was setup
        if (location == null) {
            return new NdPoint(0, 0);
        }
        return location;
    }

    /**
     *  Returns the location on the grid space.
     * @return The location of the object, if currently in the grid space.
     */
    public GridPoint getGridLocation() {
        GridPoint location = grid.getLocation(this);

        if(location == null) {
            return new GridPoint(0, 0);
        }
        return location;
    }
    
    /**
     * @return The space of an object.
     */
    public ContinuousSpace<Object> getSpace(){
        return this.space;
    }

    /**
     * @return The grid of an object.
     */
    public Grid<Object> getGrid() {
        return this.grid;
    }

    /**
     * @return The bounding box of this object.
     */
    public Rectangle2D getBoundingBox() {
        return boundingBox;
    }

    /**
     * Sets the size of this object.
     * 
     * @param width
     *            the width of the object in unit size.
     * @param height
     *            the height the object in unit size.
     */
    public void setSize(double width, double height) {
        boundingBox.width = width;
        boundingBox.height = height;
    }

    /**
     * Moves this object to a new location. The given location will be the center of the object.
     * 
     * @param x
     *            The x coordinate of the location.
     * @param y
     *            The y coordinate of the location.
     */
    public void moveTo(double x, double y) {
        boundingBox.x = x - boundingBox.width / 2;
        boundingBox.y = y - boundingBox.height / 2;
        space.moveTo(this, x, y);
        grid.moveTo(this, (int)x, (int)y);
    }



    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return 1;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
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
        BoundedMoveableObject other = (BoundedMoveableObject) obj;
        if (boundingBox == null) {
            if (other.boundingBox != null) {
                return false;
            }
        } else if (!boundingBox.equals(other.boundingBox)) {
            return false;
        }
        if (context == null) {
            if (other.context != null) {
                return false;
            }
        } else if (!context.equals(other.context)) {
            return false;
        }
        if (space == null) {
            if (other.space != null) {
                return false;
            }
        } else if (!space.equals(other.space)) {
            return false;
        }
        return true;
    }

    /**
     * Removes this object from the context given at construction.
     */
    public void removeFromContext() {
        context.remove(this);
    }

    /**
     * Calculates the distance between our center poitn and the given point.
     * 
     * @param there
     *            the point to calculate the distance to.
     * @return the distance to the given point.
     */
    public double distanceTo(NdPoint there) {
        NdPoint here = getLocation();
        return Math.sqrt(Math.pow(there.getX() - here.getX(), 2)
                + Math.pow(there.getY() - here.getY(), 2));
    }

    /**
     * As {@link #distanceTo(NdPoint)}
     * n
     * @param o
     *            is the object to compute the distance to
     * @return distance to center of o. Note that the distance to the bounding box of o may be smaller than this.
     */
    public double distanceTo(BoundedMoveableObject o) {
        return distanceTo(o.getLocation());
    }

    /**
     * set the object to visible
    */
    public void addToContext() {
        context.add(this);

    }

    public List<NdPoint> getPointsOccupiedByObject(int padding) {
        List<NdPoint> points = new ArrayList<NdPoint>();

        for(int i = (int) boundingBox.getMinX() - padding; i <= (int) boundingBox.getMaxX() + padding; i++) {
            for(int j = (int) boundingBox.getMinY() - padding; j <= (int) boundingBox.getMaxY() + padding; j++) {
                points.add(new NdPoint(i, j));
            }
        }

        return points;
    }
}
