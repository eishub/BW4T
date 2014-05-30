package nl.tudelft.bw4t.eis;

public class ObjectInformation {
    private double X, Y;
    private long id;

    public ObjectInformation(double X, double Y, long id) {
        this.X = X;
        this.Y = Y;
        this.id = id;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(X);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(Y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        ObjectInformation other = (ObjectInformation) obj;
        if (Double.doubleToLongBits(X) != Double.doubleToLongBits(other.X))
            return false;
        if (Double.doubleToLongBits(Y) != Double.doubleToLongBits(other.Y))
            return false;
        if (id != other.id)
            return false;
        return true;
    }
}
