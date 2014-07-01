package nl.tudelft.bw4t.map;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The path if a robot is stored in this class which is then
 * drawn unto the map.
 */
public class Path implements Serializable {

    /**
     * Serial id.
     */
    private static final long serialVersionUID = 6352875233093411878L;
    
    /**
     * List of point wich form a path.
     */
    private List<Point> path;
    
    /**
     * {@link Color} of path.
     */
    private Color color;

    /** 
     * Initialize Path.
     */
    public Path() {
        path = new ArrayList<Point>();
    }

    /**
     * @param path to set 
     *             if there was another list of point it will be overrided.
     */
    public void setPath(List<Point> path) {
        Random r = new Random();
        this.path = path;
        this.color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
    }

    public List<Point> getPath() {
        return path;
    }

    public Color getColor() { 
        return color; 
        }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
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
        Path other = (Path) obj;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        return true;
    }

}
