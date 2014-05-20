package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Created by on 20-5-2014.
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
    
    public static FileNameExtensionFilter mapFilter() {
    	return new FileNameExtensionFilter("MAP file", "map");
    }
}
