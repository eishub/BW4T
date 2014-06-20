package nl.tudelft.bw4t.scenariogui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.map.NewMap;

import org.apache.log4j.Logger;

/**
 * Holds some of the map specifications that are useful to know, such
 * as the amount of entities allowed in a map.
 */
public class MapSpec {
    
    private String mapFileLocation;
    private int entitiesAllowedInMap;
    
    private static final Logger LOGGER = Logger.getLogger(MapSpec.class);

    /**
     * Creates a new MapSpec object.
     * @param newMapFileLocation The path to the map file.
     */
    public MapSpec(String newMapFileLocation) {
        setMapFileLocation(newMapFileLocation);
    }
    
    private void readMapSpecifications() {
        try {
            NewMap map = NewMap.create(new FileInputStream(new File(mapFileLocation)));
            entitiesAllowedInMap = map.getEntities().size();
        } catch (FileNotFoundException | JAXBException e) {
            LOGGER.error(e);
        }
    }
    
    public void setMapFileLocation(String newMapFileLocation) {
        if (isSet(newMapFileLocation)) {
            if (!this.mapFileLocation.equals(newMapFileLocation)) {
                this.mapFileLocation = newMapFileLocation;
                readMapSpecifications();
            }
        } else {
            this.mapFileLocation = newMapFileLocation;
        }
    }

    public int getEntitiesAllowedInMap() {
        return entitiesAllowedInMap;
    }
    
    public boolean isSet() {
        return isSet(mapFileLocation);
    }
    
    public boolean isSet(String mapPath) {
        return mapPath != null && !mapPath.equals("");
    }

}
