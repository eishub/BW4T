package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class holds several FileNameExtensionFilters.
 */
public final class FileFilters {

    /** Prevents this class from being instantiated. */
    private FileFilters() { }

    /**
     * Returns an extension filter for XML.
     * @return An extension filter for XML.
     */
    public static FileNameExtensionFilter xmlFilter() {
        return new FileNameExtensionFilter("XML Files (*.xml)", "xml");
    }

    /**
     * Returns an extension filter for the map files.
     * @return An extension filter for the map files.
     */
    public static FileNameExtensionFilter mapFilter() {
        return new FileNameExtensionFilter("MAP Files (*.map)", "map");
    }
    
    /**
     * 
     * Returns an extension filter for GOAL files.
     * @return An extension filter for GOAL files.
     */
    public static FileNameExtensionFilter goalFilter() {
        return new FileNameExtensionFilter("GOAL Files (*.goal)", "goal");
    }

    /**
     * Returns an extension filter for MAS files.
     * @return An extension filter for MAS files.
     */
    public static FileNameExtensionFilter masFilter() {
        return new FileNameExtensionFilter("MAS2G Files (*.mas2g)", "mas2g");
    }
}
