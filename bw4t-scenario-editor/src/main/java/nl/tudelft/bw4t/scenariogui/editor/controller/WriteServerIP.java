package nl.tudelft.bw4t.scenariogui.editor.controller;

import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Listens to the server ip text field and updates
 * the {@link nl.tudelft.bw4t.scenariogui.BW4TClientConfig} object
 * with the update in the text field.
 *
 */
public class WriteServerIP extends WriteConfig {
    
    private MainPanel view;

    /**
     * Create a new listener to the server ip text field
     * @param newView The parent view.
     */
    public WriteServerIP(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Stores the server ip in the BW4TClientConfig when its field is changed.
     */
    public void handleUpdate() {
        view.getClientConfig().setServerIp(
                view.getConfigurationPanel().getServerIP());
    }
    
}
