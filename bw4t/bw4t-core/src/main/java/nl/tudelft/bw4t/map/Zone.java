package nl.tudelft.bw4t.map;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A zone is a square area in the map. The zone also functions as a 'navpoint'
 * in that the bot uses these zones for navigation. The navpoint is the middle
 * of the area. Zones can be rooms or corridor areas.
 */
@SuppressWarnings("serial")
@XmlRootElement
public class Zone implements Serializable {
    public static final String DROP_ZONE_NAME = "DropZone";
    public static final String START_ZONE_NAME = "StartZone";
    public static final String BLOCKADE_NAME = "Blockade";
    public static final String CORRIDOR_NAME = "Corridor";
    public static final String CHARGING_ZONE_NAME = "ChargingZone";
    public static final String ROOM_NAME = "Room";
    
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
     * NAME MUST BE SET UNIQUE. Otherwise XML serialization will fail.
     */
    private String name = "Default";

    private Type type = Type.ROOM;
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

    public static final Color BLOCKADE_COLOR = new Color(0.6f, 0f, 0f);

    public static final Color CHARGING_ZONE_COLOR = new Color(0f, 0.5f, 0f);

    public Zone() {
    }

    public Zone(String nm, Rectangle bbox, Type t) {
        name = nm;
        type = t;
        boundingbox = bbox;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
     * @return
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

    public void setName(String name) {
        this.name = name;
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

}

