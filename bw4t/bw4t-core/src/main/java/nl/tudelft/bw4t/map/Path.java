package nl.tudelft.bw4t.map;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import repast.simphony.space.continuous.NdPoint;

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
    private List<NdPoint> path;
    
    /**
     * {@link Color} of path.
     */
    private Color color;

    /** 
     * Initialize Path.
     */
    public Path() {
        path = new ArrayList<NdPoint>();
    }

    /**
     * @param path to set 
     *             if there was another list of point it will be overrided.
     */
    public void setPath(List<NdPoint> path) {
        Random r = new Random();
        this.path = path;
        this.color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
    }

    public List<NdPoint> getPath() {
        return path;
    }

    public Color getColor() { 
        return color; 
        }

}
