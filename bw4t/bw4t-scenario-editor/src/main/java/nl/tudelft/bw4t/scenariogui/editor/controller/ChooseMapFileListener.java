package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Handles the event to choose a map file.
 * 
 * @version     0.1                
 * @since       12-05-2014        
 */
class ChooseMapFileListener implements ActionListener {

    private MainPanel view;

    /**
     * Create a <code>ChooseMapFileListener</code> event handler.
     *
     * @param newView The parent view.
     */
    public ChooseMapFileListener(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Listens to the map file chooser and sets a map file
     * 
     * Actionhandler that listens to the map file chooser. 
     * It creates a filter for MAP files in the file dialog and makes sure only MAP files are accepted and set.
     *
     * @param actionEvent is the event.
     */
    public void actionPerformed(final ActionEvent actionEvent) {
        /**
         * Create a file chooser, opening at the last path location saved in the
         * configuration panel
         */
        JFileChooser fc = view.getConfigurationPanel().getFileChooser();
        
        /** Create a file name extension filter to filter on MAP files */
        int returnVal = fc.showOpenDialog(view);
        File file = fc.getSelectedFile();
        String mapExtension = ".map";

        /** Makes sure only files with the right extension are accepted */
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if (file.getName().endsWith(mapExtension)) {
                view.getConfigurationPanel().setMapFile(file.getPath());
            } else {
                ScenarioEditor.getOptionPrompt().showMessageDialog(view, "This is not a valid file.");
            }
        }
    }
    
}
