package nl.tudelft.bw4t.map;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

import nl.tudelft.bw4t.map.Door.Orientation;
import nl.tudelft.bw4t.util.OneTimeInitializing;

import org.mockito.internal.matchers.Equals;

/**
 * A zone is a square area in the map. The zone also functions as a 'navpoint'
 * in that the bot uses these zones for navigation. The navpoint is the middle
 * of the area. Zones can be rooms or corridor areas.
 * 
 * This class is not thread safe. However it is required that
 * {@link #hashCode()} and {@link Equals} are thread safe.
 */
@SuppressWarnings("serial")
@XmlRootElement
public class Zone implements Serializable, OneTimeInitializing {
	/**
	 * Type of the zone
	 */
	public enum Type {
		/** Room. Only 1 bot allowed */
		ROOM,
		/** Corridor. Adjustable 1 bot or multiple bots allowed */
		CORRIDOR,
		/** Charging zone. Where robots charge */
		CHARGINGZONE,
		/** Blockade. Obstacle in the path of robots */
		BLOCKADE
	}

	/**
	 * Set name of dropzone, startzone, blockade, corridor, charging zone and
	 * room
	 */
	public static final String DROP_ZONE_NAME = "DropZone";
	public static final String START_ZONE_NAME = "StartZone";
	public static final String BLOCKADE_NAME = "Blockade";
	public static final String CORRIDOR_NAME = "Corridor";
	public static final String CHARGING_ZONE_NAME = "ChargeZone";
	public static final String ROOM_NAME = "Room";

	/** Set color of blockade and chargingzone. */
	public static final Color BLOCKADE_COLOR = new Color(0.6f, 0f, 0f);
	public static final Color CHARGING_ZONE_COLOR = new Color(0f, 0.5f, 0f);

	/**
	 * NAME MUST BE SET UNIQUE. Default null otherwise XML serialization will
	 * fail.
	 */
	private String name = null;

	/**
	 * defult type null otherwise XML serialization will fail.
	 */
	private Type type = null;
	private List<Door> doors = new ArrayList<Door>();
	private List<BlockColor> blocks = new ArrayList<BlockColor>();
	private Rectangle boundingbox = new Rectangle();

	/**
	 * In the XML file, we refer to the name as unique ID.
	 */
	private List<Zone> neighbours = new ArrayList<Zone>();

	/**
	 * Default render options: null.
	 */
	private RenderOptions renderOptions = null;

	/**
	 * Empty constructor, initialize Zone.
	 */
	public Zone() {
	}

	/**
	 * Constructor.
	 * 
	 * @param nm
	 *            name of zone
	 * @param bbox
	 *            boundingbox
	 * @param t
	 *            type
	 */
	public Zone(String nm, Rectangle bbox, Type t) {
		name = nm;
		type = t;
		boundingbox = bbox;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type newType) {
		if (type != null) {
			throw new IllegalStateException("type has already been set.");
		}
		type = newType;
	}

	public Rectangle getBoundingbox() {
		return boundingbox;
	}

	public void setBoundingbox(Rectangle boundingbox) {
		this.boundingbox = boundingbox;
	}

	public List<Door> getDoors() {
		return doors;
	}

	public void setDoors(List<Door> doors) {
		this.doors = doors;
	}

	public List<BlockColor> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<BlockColor> blocks) {
		this.blocks = blocks;
	}

	/**
	 * XmlIDREF annotation indicates XML serializer to use only the IDs and not
	 * the full elements in the list
	 * 
	 * @return List<Zone> containing neighbours
	 */
	@XmlIDREF
	public List<Zone> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(List<Zone> neighbours) {
		this.neighbours = neighbours;
	}

	/**
	 * XmlID hints the XML serializer that this field can be used as unique ID
	 * for a zone.
	 * 
	 * @return name of this zone. It's also the name of the "navpoint"
	 */
	@XmlID
	public String getName() {
		return name;
	}

	public void setName(String newName) {
		if (name != null) {
			throw new IllegalStateException("name has already been set.");
		}
		name = newName;
	}

	/**
	 * Add new {@link Door}
	 * 
	 * @param door
	 *            {@link Door}
	 */
	public void addDoor(Door door) {
		doors.add(door);
	}

	/**
	 * Add new {@link BlockColor}
	 * 
	 * @param block
	 *            {@link BlockColor}
	 */
	public void addBlock(BlockColor block) {
		blocks.add(block);
	}

	/**
	 * @param zone
	 *            {@link Zone} to be added as Neighbour
	 */
	public void addNeighbour(Zone zone) {
		neighbours.add(zone);
	}

	@Override
	public String toString() {
		List<String> neighbournames = new ArrayList<String>();
		for (Zone neighbour : neighbours) {
			neighbournames.add(neighbour.getName());
		}
		return "Zone[" + name + "," + boundingbox + "," + type + "," + doors
				+ "," + blocks + "," + neighbournames + "]";
	}

	/**
	 * @return {@link RenderOptions}. null if default should be used.
	 */
	public RenderOptions getRenderOptions() {
		return renderOptions;
	}

	public void setRenderOptions(RenderOptions renderOptions) {
		this.renderOptions = renderOptions;
	}

	public boolean isOpenSpace() {
		return getType() == Type.CORRIDOR || getType() == Type.CHARGINGZONE;
	}

	/**
	 * 
	 * @return true if the zone has a door on its north side.
	 */
	public boolean hasNorth() {
		boolean temp = false;
		for (Door d : doors) {
			if (d.getOrientation() == Orientation.HORIZONTAL) {
				temp = (d.getPosition().getY() < boundingbox.getY());
			}
		}
		return temp;
	}

	/**
	 * 
	 * @return true if the zone has a door on its east side.
	 */
	public boolean hasEast() {
		boolean temp = false;
		for (Door d : doors) {
			if (d.getOrientation() == Orientation.VERTICAL) {
				temp = (d.getPosition().getX() > boundingbox.getX());
			}
		}
		return temp;
	}

	/**
	 * 
	 * @return true if the zone has a door on its south side.
	 */
	public boolean hasSouth() {
		boolean temp = false;
		for (Door d : doors) {
			if (d.getOrientation() == Orientation.HORIZONTAL) {
				temp = (d.getPosition().getY() > boundingbox.getY());
			}
		}
		return temp;
	}

	/**
	 * 
	 * @return true if the zone has a door on its west side.
	 */
	public boolean hasWest() {
		boolean temp = false;
		for (Door d : doors) {
			if (d.getOrientation() == Orientation.VERTICAL) {
				temp = (d.getPosition().getX() < boundingbox.getX());
			}
		}
		return temp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * Two zones are equal if name and type are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zone other = (Zone) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public boolean isInitialized() {
		return null != type && null != name;
	}

}
