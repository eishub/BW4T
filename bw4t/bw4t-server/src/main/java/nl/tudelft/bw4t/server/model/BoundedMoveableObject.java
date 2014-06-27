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
    
    /**
     * The counter which represents the id of a BMO.
     */
    private static final AtomicLong COUNTER = new AtomicLong();

    /**
     * The id of the BMO.
     */
    private final long id;
    
    /**
     * The server context this object is attached to.
     */
    private BW4TServerMap serverMap;
    
    /**
     * The box for the BMO.
     */
    protected final Rectangle2D.Double boundingBox;

    /**
     * Creates a new object bounded by a box at (0,0) with size (0,0).
     *  @param space
     *            the space in which the object should be placed.
     * @param grid
     *            the grid in which the object should be placed.
     * @param context
     *            the context in which the object should be placed.
     */
    public BoundedMoveableObject(BW4TServerMap smap) {
        assert smap != null;
        assert smap.hasMap();
        assert smap.hasContext();
        setServerMap(smap);
        if (getContext().isEmpty()) {
            COUNTER.set(0);
        }
        this.id = COUNTER.getAndIncrement();
        this.boundingBox = new Rectangle2D.Double();
        addToContext();
    }

    /**
     * @return The unique id of the object.
     */
    public long getId() {
        return id;
    }

    public BW4TServerMap getServerMap() {
        return serverMap;
    }

    private void setServerMap(BW4TServerMap serverMap) {
        this.serverMap = serverMap;
    }

    /**
     * @return The context in which the object might be placed in.
     */
    public Context<Object> getContext() {
        return getServerMap().getContext();
    }

    /**
     * @return The location of the object, if currently in a space.
     */
    public NdPoint getLocation() {
        NdPoint location = getSpace().getLocation(this);
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
        GridPoint location = getGrid().getLocation(this);

        if (location == null) {
            return new GridPoint(0, 0);
        }
        return location;
    }
    
    /**
     * @return The space of an object.
     */
    public ContinuousSpace<Object> getSpace() {
        return getServerMap().getContinuousSpace();
    }

    /**
     * @return The grid of an object.
     */
    public Grid<Object> getGrid() {
        return getServerMap().getGridSpace();
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
        getSpace().moveTo(this, x, y);
        getGrid().moveTo(this, (int) x, (int) y);
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
        if (serverMap == null) {
            if (other.serverMap != null) {
                return false;
            }
        } else if (!serverMap.equals(other.serverMap)) {
            return false;
        }
        return true;
    }

    /**
     * Removes this object from the context given at construction.
     */
    public void removeFromContext() {
        getContext().remove(this);
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
        getContext().add(this);

    }

    /**
     * Returns all the points rounded to an integer occupied by the Bounded Moveable Object,
     * including the given padding.
     * @param padding The padding to add around the box.
     * @return A list of all points occupied by the box.
     */
    public List<NdPoint> getPointsOccupiedByObject(double padding) {
        List<NdPoint> points = new ArrayList<NdPoint>();

        double halfPad = Math.ceil(padding / 2);

        double x =  Math.floor(getBoundingBox().getX());
        double y =  Math.floor(getBoundingBox().getY());

        double width = Math.ceil(getBoundingBox().getWidth());
        double height = Math.ceil(getBoundingBox().getHeight());

        double x2  = x + width;
        double y2 = y + height;

        x -= halfPad;
        y -= halfPad;

        x2 += halfPad;
        y2 += halfPad;

        for(double i = x; i <= x2; i++) {
            for(double j = y; j <= y2; j++) {
                points.add(new NdPoint(i, j));
            }
        }

        return points;
    }
    
    /**
     * Whether this bounded moveable object is free of objects that are
     * of the specified type.
     * @param freeOfType The type of objects that this object should be free of.
     * @return Whether this bounded moveable object is free of object.
     */
    public boolean isFree(Class<? extends BoundedMoveableObject> freeOfType) {
        for (NdPoint point : getPointsOccupiedByObject(0)) {
            for (Object o : getSpace().getObjectsAt(point.getX(), point.getY())) {
                if (this != o) {
                    return false;
                }
            }
        }
        return true;
    }
    
}
