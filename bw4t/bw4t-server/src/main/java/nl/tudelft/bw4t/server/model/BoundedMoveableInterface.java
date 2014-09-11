package nl.tudelft.bw4t.server.model;

import java.awt.geom.Rectangle2D;
import java.util.List;

import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.GridPoint;

public interface BoundedMoveableInterface {
	/**
	 * @return The unique id of the object.
	 */
	public long getId();

	/**
	 * @return The location of the object, if currently in a space.
	 */
	public NdPoint getLocation();

	/**
	 * @return The context in which this object is
	 */
	public BW4TServerMap getServerMap();

	/**
	 * Returns the location on the grid space.
	 * 
	 * @return The location of the object, if currently in the grid space.
	 */
	public GridPoint getGridLocation();

	/**
	 * @return The bounding box of this object.
	 */
	public Rectangle2D getBoundingBox();

	/**
	 * Sets the size of this object.
	 * 
	 * @param width
	 *            the width of the object in unit size.
	 * @param height
	 *            the height the object in unit size.
	 */
	public void setSize(double width, double height);

	/**
	 * Moves this object to a new location. The given location will be the
	 * center of the object.
	 * 
	 * @param x
	 *            The x coordinate of the location.
	 * @param y
	 *            The y coordinate of the location.
	 */
	public void moveTo(double x, double y);

	/**
	 * Removes this object from the context given at construction.
	 */
	public void removeFromContext();

	/**
	 * Calculates the distance between our center poitn and the given point.
	 * 
	 * @param there
	 *            the point to calculate the distance to.
	 * @return the distance to the given point.
	 */
	public double distanceTo(NdPoint there);

	/**
	 * As {@link #distanceTo(NdPoint)} n
	 * 
	 * @param o
	 *            is the object to compute the distance to
	 * @return distance to center of o. Note that the distance to the bounding
	 *         box of o may be smaller than this.
	 */
	public double distanceTo(BoundedMoveableObject o);

	/**
	 * Returns all the points rounded to an integer occupied by the Bounded
	 * Moveable Object, including the given padding.
	 * 
	 * @param padding
	 *            The padding to add around the box.
	 * @return A list of all points occupied by the box.
	 */
	public List<NdPoint> getPointsOccupiedByObject(double padding);

	/**
	 * Whether this bounded moveable object is free of objects that are of the
	 * specified type.
	 * 
	 * @param freeOfType
	 *            The type of objects that this object should be free of.
	 * @return Whether this bounded moveable object is free of object.
	 */
	public boolean isFree(Class<? extends BoundedMoveableObject> freeOfType);

	public int hashCode();

	public boolean equals(Object obj);
}
