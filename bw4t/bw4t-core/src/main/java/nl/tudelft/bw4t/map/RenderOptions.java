package nl.tudelft.bw4t.map;

import java.io.Serializable;

/**
 * Render options for map elements.
 */
@SuppressWarnings("serial")
public class RenderOptions implements Serializable {
    /**
     * True if we should render a label for this element.
     */
    private Boolean labelVisible = true;

    public Boolean isLabelVisible() {
        return labelVisible;
    }

    public void setLabelVisible(Boolean labelVisible) {
        this.labelVisible = labelVisible;
    }

}
