package nl.tudelft.bw4t.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import repast.simphony.space.continuous.NdPoint;

/**
 */
public class Path implements Serializable {

    private List<NdPoint> path;

    public Path() {
        path = new ArrayList<NdPoint>();
    }

    public void setPath(List<NdPoint> path) {
        this.path = path;
    }

    public List<NdPoint> getPath() {
        return path;
    }

}
