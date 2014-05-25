package nl.tudelft.bw4t.map.view;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import nl.tudelft.bw4t.map.BlockColor;

public class Block {
	/** The width and height of the blocks */
	public final static int BLOCK_SIZE = 1;

	private final long objectId;
	private final BlockColor color;
	private Point2D.Double position;

	public Block(long objectId, BlockColor color, Double position) {
		super();
		this.objectId = objectId;
		this.color = color;
		this.position = position;
	}

	/**
	 * @return the position
	 */
	public Point2D.Double getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point2D.Double position) {
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
