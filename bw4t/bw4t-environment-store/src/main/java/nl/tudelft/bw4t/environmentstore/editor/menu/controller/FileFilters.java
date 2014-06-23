package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Filters all the files except the ones with the extension needed
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

    /**
     * Returns an extension filter for MAS files.
     * @return an extension filter for MAS files.
     */
    public static FileNameExtensionFilter masFilter() {
        return new FileNameExtensionFilter("mas2g file (*.mas2g)", "mas2g");
    }
}
