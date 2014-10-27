package nl.tudelft.bw4t.map.view;

import java.awt.geom.Point2D;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * information about a block for the map renderer.
 * 
 * ViewBlock must have thread safe {@link #equals(Object)} because it is used in
 * maps that should be thread safe (in ClientMapController).
 */
public class ViewBlock {
	/** The width and height of the blocks */
	public static final int BLOCK_SIZE = 1;

	/** Initialize objectId, default 0. */
	private long objectId = 0;

	/** Initialize color, default null. */
	private BlockColor color = null;

	/** Initialize position. */
	private Point2D position = new Point2D.Double();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (objectId ^ (objectId >>> 32));
		return result;
	}

	/**
	 * Two ViewBlocks are equal if they have the same ID.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViewBlock other = (ViewBlock) obj;
		if (objectId != other.objectId)
			return false;
		return true;
	}

	/**
	 * Empty constructor, caller should init object later.
	 * 
	 * <h1>discussion</h1> This is needed to represent partial block information
	 * received from the server, eg when we get a color percept for a block we
	 * do not get the block position. Maybe we should also wonder about why we
	 * do not just have a block(id,color,loc) percept.
	 */
	public ViewBlock(long id) {
		// super(); // Wouter: there is no super class but Object?
		objectId = id;
	}

	/**
	 * Constructor.
	 * 
	 * @param objectId
	 *            long, id of this object.
	 * @param color
	 *            BlockColor
	 * @param position
	 *            Point2D
	 */
	public ViewBlock(long objectId, BlockColor color, Point2D position) {
		// super(); // Wouter: there is no super class but Object?
		this.objectId = objectId;
		this.color = color;
		this.position = position;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	/**
	 * @param x
	 *            the position to set
	 * @param y
	 *            the position to set
	 */
	public void setPosition(double x, double y) {
		this.position = new Point2D.Double(x, y);
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long id) {
		objectId = id;
	}

	public BlockColor getColor() {
		return color;
	}

	public void setColor(BlockColor c) {
		color = c;
	}

	public String toString() {
		return "block[" + color + "]";
	}
}
