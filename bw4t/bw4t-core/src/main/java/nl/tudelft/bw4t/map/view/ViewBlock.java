package nl.tudelft.bw4t.map.view;

import java.awt.geom.Point2D;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * information about a block for the map renderer
 */
public class ViewBlock {
    /** The width and height of the blocks */
    public final static int BLOCK_SIZE = 1;

    private long objectId = 0;
    private BlockColor color = null;
    private Point2D position = new Point2D.Double();
    
    public ViewBlock() {
        super();
    }

    public ViewBlock(long objectId, BlockColor color, Point2D position) {
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
     * @param x the position to set
     * @param y the position to set
     */
    public void setPosition(double x, double y) {
        this.position = new Point2D.Double(x, y);
    }

    /**
     * @return the objectId
     */
    public long getObjectId() {
        return objectId;
    }

    /**
     * @param id the objectId
     */
    public void setObjectId(long id) {
        objectId = id;
    }

    /**
     * @return the color
     */
    public BlockColor getColor() {
        return color;
    }

    public void setColor(BlockColor c) {
        color = c;
    }
}
