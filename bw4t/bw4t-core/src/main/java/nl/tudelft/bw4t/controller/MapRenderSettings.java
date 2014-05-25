package nl.tudelft.bw4t.controller;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class MapRenderSettings {
	private int sequenceBlockSize = 20;
	private int worldWidth = 7;
	private int worldHeight = 7;
	private int scale = 7;
	private int roomTextOffset = 25;

	/**
	 * Initialize with default values.
	 */
	public MapRenderSettings() {
	}

	/**
	 * @return the worldWidth
	 */
	public int getWorldWidth() {
		return worldWidth;
	}

	/**
	 * @param worldWidth
	 *            the worldWidth to set
	 */
	public void setWorldWidth(int worldWidth) {
	}

	/**
	 * @return the worldHeight
	 */
	public int getWorldHeight() {
		return worldHeight;
	}

	/**
	 * Change the size of the world.
	 * 
	 * @param x
	 *            size in horizontal direction
	 * @param y
	 *            size in vertical direction
	 */
	public void setWorldDimensions(int x, int y) {
		this.worldWidth = x;
		this.worldHeight = y;
	}

	/**
	 * @return the roomTextOffset
	 */
	public int getRoomTextOffset() {
		return roomTextOffset;
	}

	/**
	 * @param roomTextOffset
	 *            the roomTextOffset to set
	 */
	public void setRoomTextOffset(int roomTextOffset) {
		this.roomTextOffset = roomTextOffset;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getSequenceBlockSize() {
		return sequenceBlockSize;
	}

	public void setSequenceBlockSize(int sequenceBlockSize) {
		this.sequenceBlockSize = sequenceBlockSize;
	}

	/**
	 * Scales the given value with the scaling factor.
	 * 
	 * @param value
	 *            to be scaled value
	 * @return the scaled value
	 */
	public double scale(double value) {
		return value * getScale();
	}

	/**
	 * Scales the given Rectangle.
	 * 
	 * @param rect
	 *            the bounding box
	 * @return the scaled rectangle
	 */
	public Rectangle2D transformRectangle(Rectangle2D rect) {
		return new Rectangle.Double(scale(rect.getX()), scale(rect.getY()), scale(rect.getWidth()),
				scale(rect.getHeight()));

	}

	/**
	 * Scales and centers the given Rectangle.
	 * 
	 * @param rect
	 *            the position and size
	 * @return the centered and scaled rectangle
	 */
	public Rectangle2D transformCenterRectangle(Rectangle2D rect) {
		Rectangle2D ret = transformRectangle(rect);
		double xPos = rect.getX() - ret.getWidth() / 2.;
		double yPos = rect.getY() - ret.getHeight() / 2.;

		return new Rectangle.Double(xPos, yPos, ret.getWidth(), ret.getHeight());

	}
}
