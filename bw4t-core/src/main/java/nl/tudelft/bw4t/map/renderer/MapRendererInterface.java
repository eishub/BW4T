package nl.tudelft.bw4t.map.renderer;

import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;

/** Interface for MapRenderer. */
public interface MapRendererInterface {
    /**
     * Attach a mouse listener to this renderer
     * 
     * @param listener
     *            the listener to be attached to this renderer
     * @see java.awt.Component#addMouseListener(MouseListener)
     */
    void addMouseListener(MouseListener listener);

    /**
     * Attach a mouse wheel listener to this renderer
     * 
     * @param listener
     *            the listener to be attached to this renderer
     * @see java.awt.Component#addMouseWheelListener(MouseWheelListener)
     */
    void addMouseWheelListener(MouseWheelListener listener);
    
    /**
     * Removes a mouse listener from this renderer
     * 
     * @param listener
     *            the listener to be detached from this renderer
     * @see java.awt.Component#removeMouseListener(MouseListener)
     */
    void removeMouseListener(MouseListener listener);

    /**
     * Remove a mouse wheel listener from this renderer
     * 
     * @param listener
     *            the listener to be detached from this renderer
     * @see java.awt.Component#removeMouseWheelListener(MouseWheelListener)
     */
    void removeMouseWheelListener(MouseWheelListener listener);


    /**
     * Request this renderer to be focused, if possible.
     * 
     * @see java.awt.Component#requestFocus()
     */
    void requestFocus();

    /**
     * Validates this renderer and all its subcomponents.
     * 
     * @see java.awt.Container#validate()
     */
    void validate();

    /**
     * Repaint this renderer.
     * 
     * @see java.awt.Component#repaint()
     */
    void repaint();

}
