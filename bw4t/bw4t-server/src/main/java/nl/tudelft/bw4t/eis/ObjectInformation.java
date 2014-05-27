package nl.tudelft.bw4t.eis;

public class ObjectInformation {
	private double x, y;
	private long id;

	public ObjectInformation(double x, double y, long id) {
		this.x = x;
		this.y = y;
		this.id = id;
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
		ObjectInformation other = (ObjectInformation) obj;
		if (this == obj) {
			return true;
		}
		else if (obj == null || (getClass() != obj.getClass())
				|| (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
				|| (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) || (id != other.id)) {
			return false;
		}
		return true;
	}
}
