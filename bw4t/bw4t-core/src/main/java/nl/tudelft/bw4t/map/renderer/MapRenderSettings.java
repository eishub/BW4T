package nl.tudelft.bw4t.map.renderer;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * Saves values used by the {@link MapRenderer} configuring the way the map is drawn. Also has an option to change the
 * delay between consecutive updates.
 */
public class MapRenderSettings {
    
    /** Initialization of final int's, all default values */
    public static final int BLOCK_SEQUENCE_SIZE_MAXIMUM = 100;
    public static final int BLOCK_SEQUENCE_SIZE_MINIMUM = 1;
    public static final int DELAY_MAXIMUM = 1000;
    public static final int DELAY_MINIMUM = 10;
    public static final int SCALE_MINIMUM = 3;
    public static final int SCALE_MAXIMUM = 15;
    
    /** Initialize default settings. */
    private int sequenceBlockSize = 20;
    private int worldWidth = 7;
    private int worldHeight = 7;
    private double scale = 7;
    private int roomTextOffset = 25;
    private boolean renderEntityName = false;
    private int entityNameOffset = 20;
    
    /**
     * We want to achieve roughly 24fps = 1000/24 = 41.6666
     */
    private int updateDelay = 41;

    /**
     * Initialize with default values.
     */
    public MapRenderSettings() {
    }

    public int getWorldWidth() {
        return worldWidth;
    }

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

    public int getRoomTextOffset() {
        return roomTextOffset;
    }

    public void setRoomTextOffset(int roomTextOffset) {
        this.roomTextOffset = roomTextOffset;
    }

    public double getScale() {
        return scale;
    }

    /**
     * Set the scale of the map in the renderer. The scale has an upper and lower limit.
     * 
     * @see #SCALE_MINIMUM
     * @see #SCALE_MAXIMUM
     * @param scale
     *            the new scale
     */
    public void setScale(double scale) {
        if (scale < SCALE_MINIMUM || scale > SCALE_MAXIMUM) {
            return;
        }
        this.scale = scale;
    }

    public int getSequenceBlockSize() {
        return sequenceBlockSize;
    }

    /**
     * Set the size of the blocks when drawing the sequence. The size has an upper and lower limit.
     * 
     * @see #BLOCK_SEQUENCE_SIZE_MINIMUM
     * @see #BLOCK_SEQUENCE_SIZE_MAXIMUM
     * @param sequenceBlockSize
     *            the new size
     */
    public void setSequenceBlockSize(int sequenceBlockSize) {
        if (sequenceBlockSize < BLOCK_SEQUENCE_SIZE_MINIMUM || sequenceBlockSize > BLOCK_SEQUENCE_SIZE_MAXIMUM) {
            return;
        }
        this.sequenceBlockSize = sequenceBlockSize;
    }

    public int getEntityNameOffset() {
        return entityNameOffset;
    }

    public void setEntityNameOffset(int entityNameOffset) {
        this.entityNameOffset = entityNameOffset;
    }

    public boolean isRenderEntityName() {
        return renderEntityName;
    }

    public void setRenderEntityName(boolean renderEntityName) {
        this.renderEntityName = renderEntityName;
    }

    public int getUpdateDelay() {
        return updateDelay;
    }

    /**
     * Set the updateDelay, enforced a limit.
     * 
     * @see #DELAY_MINIMUM
     * @see #DELAY_MAXIMUM
     * @param updateDelay
     *            the new update delay
     */
    public void setUpdateDelay(int updateDelay) {
        if (updateDelay < DELAY_MINIMUM || updateDelay > DELAY_MAXIMUM) {
            return;
        }
        this.updateDelay = updateDelay;
    }

    /**
     * Scales the given value with the scaling factor.
     * 
     * @param value
     *            to be scaled value
     * @return the scaled value
     */
    public int scale(int value) {
        return (int) (value * getScale());
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
     * Scales and centers(moves it by half it width to the left and half its size to the top) the given Rectangle.
     * 
     * @param rect
     *            the position and size
     * @return the centered and scaled rectangle
     */
    public Rectangle2D transformCenterRectangle(Rectangle2D rect) {
        Rectangle2D ret = transformRectangle(rect);
        double xPos = ret.getX() - ret.getWidth() / 2.;
        double yPos = ret.getY() - ret.getHeight() / 2.;

        return new Rectangle.Double(xPos, yPos, ret.getWidth(), ret.getHeight());

    }
}
