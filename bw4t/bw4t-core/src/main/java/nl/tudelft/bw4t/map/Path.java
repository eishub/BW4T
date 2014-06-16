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

    private List<NdPoint> path;
    private Color color;

    public Path() {
        path = new ArrayList<NdPoint>();
    }

    public void setPath(List<NdPoint> path) {
        Random r = new Random();
        this.path = path;
        this.color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
    }

    public List<NdPoint> getPath() {
        return path;
    }

    public Color getColor() { return color; }

}
