package nl.tudelft.bw4t.scenariogui.util;

import java.io.File;

public class AgentFileChecker {

    
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
