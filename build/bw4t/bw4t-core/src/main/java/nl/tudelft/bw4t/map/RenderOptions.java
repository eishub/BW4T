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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((labelVisible == null) ? 0 : labelVisible.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RenderOptions other = (RenderOptions) obj;
        if (labelVisible == null) {
            if (other.labelVisible != null)
                return false;
        } else if (!labelVisible.equals(other.labelVisible))
            return false;
        return true;
    }

}
