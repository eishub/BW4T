package nl.tudelft.bw4t.map.view;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.util.OneTimeInitializing;

/**
 * information about an e-partner to be displayed on the map.
 * 
 * Must implement a thread safe version of {@link #equals(Object)} as this is
 * used in a Map in a class that has to be thread safe (ClientMapController).
 */
public class ViewEPartner implements OneTimeInitializing {

	/** Initialize size of EPartner. */
	public static final int EPARTNER_SIZE = 1;

	/** Initialize color of EPartner when offline, namely yellow. */
	public static final Color EPARTNER_OFFLINE = Color.YELLOW;

	/** Initialize color of EPartner when online, namely green. */
	public static final Color EPARTNER_ONLINE = Color.GREEN;

	public static final String GPS = "GPS";

	public static final String FORGET_ME_NOT = "Forget-me-not";

	/** Initialize id of EPartner. */
	private Long id = null;

	/** Initialize location. */
	private Point2D location = new Point2D.Double();

	/** Initialize pickedUp, default false. */
	private boolean pickedUp = false;

	/** Initialize name of EPartner, default " " */
	private String name = "";

	/** Initialize visible boolean. */
	private boolean visible;

	/** ParameterList containing the functionalities. **/
	private List<String> types = new LinkedList<>();

	/** Empty constructor, initialize default ViewEPartner. */
	public ViewEPartner() {
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param location
	 * @param isPickedUp
	 */
	public ViewEPartner(long id, Point2D location, boolean isPickedUp) {
		this.setId(id);
		this.location = location;
		this.pickedUp = isPickedUp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**
	 * The same if the other has the same ID.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViewEPartner other = (ViewEPartner) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public boolean isInitialized() {
		return id != null;
	}

	public Long getId() {
		return id;
	}

	public void setId(long newId) {
		if (id != null) {
			throw new IllegalStateException("ID is already set.");
		}
		id = newId;
	}

	public Point2D getLocation() {
		return location;
	}

	public void setLocation(Point2D location) {
		this.location = location;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

	/**
	 * Get the current color of the e-partner, depending on whether it is online
	 * or not.
	 * 
	 * @return the color of the e-partner
	 */
	public Color getColor() {
		if (isPickedUp()) {
			return EPARTNER_ONLINE;
		} else {
			return EPARTNER_OFFLINE;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getSize() {
		return EPARTNER_SIZE;
	}

	public void setTypes(List<String> t) {
		types = t;
	}

	public List<String> getTypes() {
		return types;
	}

}
