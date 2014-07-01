package nl.tudelft.bw4t.scenariogui.util;

import java.io.File;

/**
 * This class contains functions to check agent files.
 */
public class AgentFileChecker {
    
    /** Prevents this class from being instantiated. */
    private AgentFileChecker() {}

    /**
     * Returns whether a file exists
     * @param filename to check
     * @return Returns whether a file exists.
     */
    public static boolean fileNameExists(String filename) {
        File f = new File(filename);
        return f.exists();
    }

}
