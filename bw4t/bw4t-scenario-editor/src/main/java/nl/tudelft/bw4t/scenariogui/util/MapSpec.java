package nl.tudelft.bw4t.scenariogui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.map.NewMap;

/**
 * Holds some of the map specifications that are useful to know, such
 * as the amount of entities allowed in a map.
 * 
 * @author Nick
 *
 */
public class MapSpec {
    
    /** The path to the map file. */
    private String mapFileLocation;
    /** The amount of entities that are allowed in a map. */
    private int entitiesAllowedInMap;
    
    /**
     * Creates a new map specification object.
     * @param newMapFileLocation The path to the map file.
     */
    public MapSpec(String newMapFileLocation) {
        setMapFileLocation(newMapFileLocation);
    }
    
    /**
     * Reads the map file and sets the specifications accordingly.
     */
    private void readMapSpecifications() {
        try {
            NewMap map = NewMap.create(new FileInputStream(new File(mapFileLocation)));
            entitiesAllowedInMap = map.getEntities().size();
        } catch (FileNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sets the new map file location.
     * @param newMapFileLocation The new path to the map file.
     */
    public void setMapFileLocation(String newMapFileLocation) {
        if (isSet(newMapFileLocation)) {
            if (!this.mapFileLocation.equals(newMapFileLocation)) {
                this.mapFileLocation = newMapFileLocation;
                readMapSpecifications();
            }
        }
        else {
            this.mapFileLocation = newMapFileLocation;
        }
    }

    /**
     * Gets the amount of entities allowed in this map.
     * @return The amount of entities allowed in this map.
     */
    public int getEntitiesAllowedInMap() {
        return entitiesAllowedInMap;
    }
    
    /**
     * Checks whether the path to the map file of this map specification is set.
     * @return Whether the path to the map file is set.
     */
    public boolean isSet() {
        return isSet(mapFileLocation);
    }
    
    /**
     * Checks whether the given map path is set.
     * @param mapFileLocation The map path.
     * @return Whether the given map path is set.
     */
    public boolean isSet(String mapFileLocation) {
        return mapFileLocation != null && !mapFileLocation.equals("");
    }

}
