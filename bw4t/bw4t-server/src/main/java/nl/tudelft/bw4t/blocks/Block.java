package nl.tudelft.bw4t.blocks;

import java.awt.Color;
import java.awt.geom.Point2D;

import nl.tudelft.bw4t.BoundedMoveableObject;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.robots.Robot;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

/**
 * Represents a block in the environment that can be picked up by a {@link Robot}.
 * 
 * @author Lennard de Rijk
 */
public class Block extends BoundedMoveableObject {

	public static final int SIZE = 1;

	private final BlockColor colorId;
	private Color color;
	private Robot heldBy;
	private final nl.tudelft.bw4t.map.view.Block view;

	/**
	 * Creates a new block with the given color.
	 * 
	 * @param colorId
	 *            The color of the block
	 * @param space
	 *            The space the block will be in.
	 * @param context
	 *            The context in which the block will be present.
	 */
	public Block(BlockColor colorId, ContinuousSpace<Object> space, Context<Object> context) {
		super(space, context);
		this.colorId = colorId;
		this.color = colorId.getColor();
		setSize(SIZE, SIZE);
		this.view = new nl.tudelft.bw4t.map.view.Block(getId(), getColorId(), new Point2D.Double());
	}

	/**
	 * @return the character identifying the color of the block.
	 */
	public BlockColor getColorId() {
		return colorId;
	}

	/**
	 * @return the representation color of the block.
	 */
	public Color getColor() {
		// TODO: Can be refactored to only exist in the visualization?
		return color;
	}

	/**
	 * @return set the color of the block
	 */
	public void setColorGrey() {
		Color c = getColorId().getColor();
		// The sRGB color space is defined in terms of the CIE 1931 linear luminance Y
		int bright = (int) Math.round(0.2126 * c.getRed() + 0.7152 * c.getBlue() + 0.0722 * c.getGreen());
		this.color = new Color(bright, bright, bright);
	}

	@Override
	public NdPoint getLocation() {
		NdPoint p;
		if (heldBy != null) {
			p = heldBy.getLocation();
		}
		else {
			p = super.getLocation();
		}
		if (p != null) {
			this.view.setPosition(p.getX(), p.getY());
		}
		return p;
	}

	/**
	 * Returns the {@link Robot} that is holding this block if any.
	 */
	public Robot getHeldBy() {
		return heldBy;
	}

	/**
	 * Sets the {@link Robot} that is holding this block.
	 * 
	 * @param heldBy
	 *            The Robot that is holding this Block, use null to release the block.
	 */
	public void setHeldBy(Robot heldBy) {
		this.heldBy = heldBy;
	}

	/**
	 * Returns true if this block is not held by a {@link Robot}.
	 */
	public boolean isFree() {
		return getHeldBy() == null;
	}

	public nl.tudelft.bw4t.map.view.Block getView() {
		return this.view;
	}

}
