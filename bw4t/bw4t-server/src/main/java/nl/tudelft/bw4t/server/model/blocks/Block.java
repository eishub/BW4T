package nl.tudelft.bw4t.server.model.blocks;

import java.awt.geom.Point2D;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;
import repast.simphony.space.continuous.NdPoint;

/**
 * Represents a block in the environment that can be picked up by a {@link AbstractRobot}.
 */
public class Block extends BoundedMoveableObject {

    /**
     * size of the block.
     */
    public static final int SIZE = 1;

    /**
     * the color of the block.
     */
    private final BlockColor colorId;
    
    /**
     * by whom the block is being held.
     */
    private AbstractRobot heldBy;
    
    /**
     * the view of the of the block.
     */
    private final nl.tudelft.bw4t.map.view.ViewBlock view;

    /**
     * Creates a new block with the given color.
     * 
     * @param colorId
     *            The color of the block
     * @param context
     *            The context in which the block will be present.
     */
    public Block(BlockColor colorId, BW4TServerMap context) {
        super(context);
        this.colorId = colorId;
        setSize(SIZE, SIZE);
        this.view = new nl.tudelft.bw4t.map.view.ViewBlock(getId(), getColorId(), new Point2D.Double());
    }

    /**
     * @return the character identifying the color of the block.
     */
    public BlockColor getColorId() {
        return colorId;
    }

    @Override
    public NdPoint getLocation() {
        NdPoint p;
        if (heldBy != null) {
            p = heldBy.getLocation();
        } else {
            p = super.getLocation();
        }
        if (p != null) {
            this.view.setPosition(p.getX(), p.getY());
        }
        return p;
    }

    /**
     * Returns the {@link AbstractRobot} that is holding this block if any.
     * @return bot
     */
    public AbstractRobot getHeldBy() {
        return heldBy;
    }

    /**
     * Sets the {@link AbstractRobot} that is holding this block.
     * 
     * @param heldBy
     *            The Robot that is holding this Block, use null to release the block.
     */
    public void setHeldBy(AbstractRobot heldBy) {
        this.heldBy = heldBy;
    }

    /**
     * Returns true if this block is not held by a {@link AbstractRobot}.
     * @return is free
     */
    public boolean isFree() {
        return getHeldBy() == null;
    }

    /**
     * gets the view of the block
     * @return view
     */
    public nl.tudelft.bw4t.map.view.ViewBlock getView() {
        this.getLocation();
        return this.view;
    }

}
