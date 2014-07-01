package nl.tudelft.bw4t.scenariogui.editor.controller;

import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Listens to the map file text field and updates
 * the {@link nl.tudelft.bw4t.scenariogui.BW4TClientConfig} object
 * with the update in the text field.
 *
 */
public class WriteMapFile extends WriteConfig {

    private MainPanel view;

    /**
     * Create a new listener for the map text field
     * @param newView The parent view.
     */
    public WriteMapFile(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Stores the map file path in the client config when its field is changed.
     */
    public void handleUpdate() {
        view.getClientConfig().setMapFile(
                view.getConfigurationPanel().getMapFileTextField().getText());
    }
    
}
