package nl.tudelft.bw4t.client.startup;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.util.XMLManager;

/**
 * Reads the config file that can be passed to the client to
 * overwrite the InitParam variables.
 */
public final class ConfigFile {
    private static final Logger LOGGER = Logger.getLogger(ConfigFile.class);
    
    private static BW4TClientConfig config;
    
    /** Should never be instantiated. */
    private ConfigFile() { 
        
    }
    
    /**
     * Reads the init config file and sets the init parameters accordingly.
     * @param pathToConfigFile - The path to the config file.
     * @throws JAXBException When there is a problem reading the config file.
     */
    public static void readConfigFile(String pathToConfigFile) throws JAXBException {
        
        // Reads the configuration file and constructs a BW4TClientConfig from it:
        config = (BW4TClientConfig) XMLManager.
                fromXML(pathToConfigFile, BW4TClientConfig.class);
        
        // Sets the default values for all init params based on the configuration file:
        for (InitParam param : InitParam.values()) {
            setDefaultValueForInitParam(param);
        }
    }
    
    /**
     * Sets the default value for an init param based on the info read
     * from the init configuration file.
     * @param param The init param.
     */
    private static void setDefaultValueForInitParam(InitParam param) {
        switch (param) {
        case CLIENTIP:
            param.setDefaultValue(config.getClientIp());
            break;
        case CLIENTPORT:
            param.setDefaultValue(((Integer) config.getClientPort()).toString());
            break;
        case GOAL:
            param.setDefaultValue(((Boolean) config.isUseGoal()).toString());
            break;
        case LAUNCHGUI:
            param.setDefaultValue(((Boolean) config.isLaunchGui()).toString());
            break;
        case SERVERIP:
            param.setDefaultValue(config.getServerIp());
            break;
        case SERVERPORT:
            param.setDefaultValue(((Integer) config.getServerPort()).toString());
            break;
        case MAP:
            param.setDefaultValue(config.getMapFile());
            break;
        default:
            break;
        }
        LOGGER.info(param.nameLower() + " =D " + param.getDefaultValue());
    }
    
    /**
     * Gets the map file that was read from the init configuration file.
     * 
     * @return The location to the map file or {@code null} if no init file was read.
     */
    public static String getMapFile() {
        return hasReadInitFile() ? config.getMapFile() : null;
    }
    
    /**
     * Whether an init configuration file has been read.
     * @return Returns {@code true} if an init configuration file has been read.
     */
    public static boolean hasReadInitFile() {
        return config != null;
    }

    public static BW4TClientConfig getConfig() {
        return config;
    }

}
