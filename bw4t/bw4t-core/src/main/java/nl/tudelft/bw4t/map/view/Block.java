package nl.tudelft.bw4t.map.view;

import java.awt.geom.Point2D;

import nl.tudelft.bw4t.map.BlockColor;

public class Block {
	/** The width and height of the blocks */
	public final static int BLOCK_SIZE = 1;

	private final long objectId;
	private final BlockColor color;
	private Point2D position;

	public Block(long objectId, BlockColor color, Point2D position) {
		super();
		this.objectId = objectId;
		this.color = color;
		this.position = position;
	}

	/**
	 * @return the position
	 */
	public Point2D getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point2D position) {
		this.position = position;
	}

	/**
	 * @return the objectId
	 */
	public long getObjectId() {
		return objectId;
	}

	/**
	 * @return the color
	 */
	public BlockColor getColor() {
		return color;
	}
}
