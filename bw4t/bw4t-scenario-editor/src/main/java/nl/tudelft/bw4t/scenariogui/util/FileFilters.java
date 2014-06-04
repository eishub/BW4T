package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Created by on 20-5-2014.
 * @author ???
 * @author Tim
 */
public final class FileFilters {

    /** Prevents this class from being instantiated. */
    private FileFilters() { }

    /**
     * Returns an extension filter for XML.
     * @return an extension filter for XML.
     */
    public static FileNameExtensionFilter xmlFilter() {
        return new FileNameExtensionFilter("xml files (*.xml)", "xml");
    }

    /**
     * Returns an extension filter for the map files.
     * @return an extension filter for the map files.
     */
    public static FileNameExtensionFilter mapFilter() {
        return new FileNameExtensionFilter("MAP file", "map");
    }
    
    /**
     * 
     * Returns an extension filter for GOAL files.
     * @return an extension filter for GOAL files.
     */
    public static FileNameExtensionFilter goalFilter() {
    	return new FileNameExtensionFilter("GOAL File (*.goal)", "goal");
    }
}
