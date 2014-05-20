package nl.tudelft.bw4t.scenariogui.util;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Created by on 20-5-2014.
 */
public class FileFilters {

    public static FileNameExtensionFilter XMLFilter() {
        return new FileNameExtensionFilter("xml files (*.xml)", "xml");
    }
}
