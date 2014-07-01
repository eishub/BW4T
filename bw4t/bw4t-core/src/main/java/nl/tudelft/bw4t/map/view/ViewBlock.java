package nl.tudelft.bw4t.map.view;

import java.awt.geom.Point2D;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * information about a block for the map renderer
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
    
    /** 
     * Empty constructor, initialize object.
     */
    public ViewBlock() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param objectId long, id of this object.
     * @param color BlockColor
     * @param position Point2D
     */
    public ViewBlock(long objectId, BlockColor color, Point2D position) {
        super();
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
     * @param x the position to set
     * @param y the position to set
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
}
