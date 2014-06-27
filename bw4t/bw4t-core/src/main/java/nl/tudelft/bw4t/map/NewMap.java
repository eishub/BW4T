package nl.tudelft.bw4t.map;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

/**
 * New map structure, using Zones and stronger typechecking
 */
@XmlRootElement
public class NewMap implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(NewMap.class);

    /** Serialization id. */
    private static final long serialVersionUID = -1346330091943903326L;
    
    /** 
     * Boolean, true when there is one Bot per Corridor Zone
     *  default false 
     */
    private Boolean oneBotPerCorridorZone = false;
   
    /**
     * The number of random colored blocks to be added to the map (not to the
     * sequence)
     */
    private Integer randomBlocks = 0;

    /**
     * Number of random colored blocks to be added to the sequence AND to the
     * map.
     */
    private Integer randomSequence = 0;

    /** 
     * Initial point for an area. 
     */
    private Point area = new Point();
    
    /** 
     * List containing all zones at the map. 
     */
    private List<Zone> zones = new ArrayList<Zone>();

    /** 
     * Sequence, a list of BlockColor.
     * This sequence defines what kind of blocks the bot needs to pick up.
     */
    private List<BlockColor> sequence = new ArrayList<BlockColor>();

    /**
     *  Initial entities.
     */
    private List<Entity> entities = new ArrayList<Entity>();

    /** 
     * Empty Constructor, initialize newMap.
     */
    public NewMap() {
    }
    
    /**
     * Constructor that creates a map from an inputstream that contains XML.
     * 
     * @param instream InputStream, contains XML
     * @return NewMap
     * @throws JAXBException 
     */
    public static NewMap create(InputStream instream) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(NewMap.class);
        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        return (NewMap) jaxbUnmarshaller.unmarshal(instream);

    }
    
    /**
     * Convert the given {@link NewMap} object to a XML string
     * @param map the map to convert
     * @return the created XML string
     * @throws JAXBException if an error occured during conversion
     */
    public static String toXML(NewMap map) throws JAXBException {
        OutputStream baos = new ByteArrayOutputStream();

        toXML(map, baos);
        
        return baos.toString();
    }
    
    /**
     * Convert the given {@link NewMap} object to XML and write it to the given outputstream.
     * @param map the map to convert
     * @param os the outputstream to write to
     * @throws JAXBException if we fail to convert the map object
     */
    public static void toXML(NewMap map, OutputStream os) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(NewMap.class);

        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        m.marshal(map, os);
    }

    public List<BlockColor> getSequence() {
        return sequence;
    }

    public void setSequence(List<BlockColor> sequence) {
        this.sequence = sequence;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public Boolean getOneBotPerCorridorZone() {
        return oneBotPerCorridorZone;
    }

    public void setOneBotPerCorridorZone(Boolean oneBotPerCorridorZone) {
        this.oneBotPerCorridorZone = oneBotPerCorridorZone;
    }

    /**
     * Add new zone
     * 
     * @param zone
     *            new zone to add
     */
    public void addZone(Zone zone) {
        zones.add(zone);
    }

    /**
     * @param type
     *            {@link EntityType}.
     * @return List of {@Link Zone} of given type
     */
    public List<Zone> getZones(Zone.Type type) {
        List<Zone> list = new ArrayList<Zone>();
        for (Zone z : zones) {
            if (z.getType() == type) {
                list.add(z);
            }
        }
        return list;
    }

    public Zone getZone(String name) throws IllegalArgumentException {
        for (Zone z : zones) {
            if (name.equals(z.getName())) {
                return z;
            }
        }
        throw new IllegalArgumentException("zone " + name + " not found");
    }

    @Override
    public String toString() {
        return "Map[onebotperzone=" + oneBotPerCorridorZone + ", randomblocks="
                + randomBlocks + ",size=" + 0 + ",sequence=" + sequence
                + ",zones=" + zones + "]";
    }

    public void setRandomBlocks(Integer randomBlocks) {
        this.randomBlocks = randomBlocks;
    }

    public Integer getRandomBlocks() {
        return randomBlocks;
    }

    public void setArea(Point area) {
        this.area = area;
    }

    public Point getArea() {
        return area;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    /** 
     * Add @param e Entity to map 
     */
    public void addEntity(Entity e) {
        entities.add(e);
    }

    /**
     * @param randomSequence 
     *             Number of blocks to be added to the sequence AND to the map.
     */
    public void setRandomSequence(Integer randomSequence) {
        this.randomSequence = randomSequence;
    }

    public Integer getRandomSequence() {
        return randomSequence;
    }

    @Override
    public int hashCode() {
        try {
            return NewMap.toXML(this).hashCode();
        } catch (JAXBException e) {
            LOGGER.info("Failed to create a stable hash code, reverting back to java jvm unstable." , e);
            final int prime = 31;
            int result = 1;
            result = prime * result + ((area == null) ? 0 : area.hashCode());
            result = prime * result + ((entities == null) ? 0 : entities.hashCode());
            result = prime * result + ((randomBlocks == null) ? 0 : randomBlocks.hashCode());
            result = prime * result + ((randomSequence == null) ? 0 : randomSequence.hashCode());
            result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
            result = prime * result + ((zones == null) ? 0 : zones.hashCode());
            return result;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NewMap other = (NewMap) obj;
        if (area == null) {
            if (other.area != null)
                return false;
        } else if (!area.equals(other.area))
            return false;
        if (entities == null) {
            if (other.entities != null)
                return false;
        } else if (!entities.equals(other.entities))
            return false;
        if (randomBlocks == null) {
            if (other.randomBlocks != null)
                return false;
        } else if (!randomBlocks.equals(other.randomBlocks))
            return false;
        if (randomSequence == null) {
            if (other.randomSequence != null)
                return false;
        } else if (!randomSequence.equals(other.randomSequence))
            return false;
        if (sequence == null) {
            if (other.sequence != null)
                return false;
        } else if (!sequence.equals(other.sequence))
            return false;
        if (zones == null) {
            if (other.zones != null)
                return false;
        } else if (!zones.equals(other.zones))
            return false;
        return true;
    }

}
