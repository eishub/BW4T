package nl.tudelft.bw4t.map.renderer;

/** Interface for MapRenderer. */
public interface MapRendererInterface {

    public abstract void requestFocus();
    public abstract void validate();
    public abstract void repaint();

}
