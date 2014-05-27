package nl.tudelft.bw4t.map;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import nl.tudelft.bw4t.map.Entity.EntityType;

/**
 * New map structure, using Zones and stronger typechecking
 * 
 * @author W.Pasman 30oct2013
 */
@XmlRootElement
public class NewMap implements Serializable {

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

    private Point area = new Point();
    private List<Zone> zones = new ArrayList<Zone>();

    // sequence. Must contain block colors.
    private List<BlockColor> sequence = new ArrayList<BlockColor>();

    // initial entities
    private List<Entity> entities = new ArrayList<Entity>();

    /**
     * Constructor that creates a map from an inputstream that contains XML.
     * 
     * @param instream
     * @return NewMap
     * @throws JAXBException
     */
    public static NewMap create(InputStream instream) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(NewMap.class);
        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        return (NewMap) jaxbUnmarshaller.unmarshal(instream);

    }

    public List<BlockColor> getSequence() {
        return sequence;
    }

    public void setSequence(List<BlockColor> sequence) {
        this.sequence = sequence;
    }

    public NewMap() {
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
     * Get zones of given type
     * 
     * @param type
     *            {@link EntityType}.
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

    public Zone getZone(String name) {
        // bit stupid implementation but we can't use hashmaps (XML
        // serialization)
        for (Zone z : zones) {
            if (name.equals(z.getName())) {
                return z;
            }
        }
        throw new IllegalArgumentException("zone " + name + " not found");
    }

    public String toString() {
        return "Map[onebotperzone=" + oneBotPerCorridorZone + ", randomblocks="
                + randomBlocks + ",size=" + 0 + ",sequence=" + sequence
                + ",zones=" + zones + "]";
    }

    /**
     * Blocks to be added to the map only, but not to the sequence.
     * 
     * @param randomBlocks
     */
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

    public void addEntity(Entity e) {
        entities.add(e);
    }

    /**
     * Number of blocks to be added to the sequence AND to the map.
     * 
     * @param randomSequence
     */
    public void setRandomSequence(Integer randomSequence) {
        this.randomSequence = randomSequence;
    }

    public Integer getRandomSequence() {
        return randomSequence;
    }

}