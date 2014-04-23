package nl.tudelft.bw4t.eis;

public class ObjectInformation {
	private double X, Y;
	private long id;

	public ObjectInformation(double X, double Y, long id) {
		this.X = X;
		this.Y = Y;
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ObjectInformation) {
			ObjectInformation objI = (ObjectInformation) obj;
			if (objI.getId() == getId() && objI.getX() == getX()
					&& objI.getY() == getY())
				return true;
			else
				return false;
		}
		return false;
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
}
