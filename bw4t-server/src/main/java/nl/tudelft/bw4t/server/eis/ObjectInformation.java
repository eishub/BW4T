package nl.tudelft.bw4t.server.eis;

import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import repast.simphony.space.continuous.NdPoint;

/**
 * location and id of the object 
 */
public class ObjectInformation {
    private double x, y;
    private long id;

    /**
     * 
     * @param x - position
     * @param y - position
     * @param id 
     */
    public ObjectInformation(double x, double y, long id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
    
    /**
     * 
     * @param obj where informations is from requested 
     */
    public ObjectInformation(BoundedMoveableObject obj) {
        final NdPoint loc = obj.getLocation();
        this.x = loc.getX();
        this.y = loc.getY();
        this.id = obj.getId();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ObjectInformation)) {
            return false; 
        }
        ObjectInformation other = (ObjectInformation) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
              return false;
        }
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
             return false;
        }
        if (id != other.id) {
            return false;
        }  
        return true;
    }
}
