package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to choose a map file.
 */
public class ChooseMapFileListener implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
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
     * Listens to the map file chooser.
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
        if (returnVal == JFileChooser.APPROVE_OPTION && file.getName().endsWith(mapExtension)) {
            view.getConfigurationPanel().setMapFile(file.getPath());
        }
        else if (returnVal == JFileChooser.APPROVE_OPTION && !file.getName().endsWith(mapExtension)) {
            JOptionPane.showMessageDialog(view, "This is not a valid file.");
        }
    }
}
