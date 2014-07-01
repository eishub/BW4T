package nl.tudelft.bw4t.scenariogui.editor.controller;

import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Listens to the client ip text field and updates
 * the {@link nl.tudelft.bw4t.scenariogui.BW4TClientConfig} object
 * with the update in the text field.
 *
 */
public class WriteClientIP extends WriteConfig {
    
    private MainPanel view;

    /**
     * Create a new listener to the client ip text field.
     * @param newView The parent view.
     */
    public WriteClientIP(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Stores the client ip in the BW4TClientConfig when its field is changed.
     */
    public void handleUpdate() {
        view.getClientConfig().setClientIp(
                view.getConfigurationPanel().getClientIPTextField().getText());
    }
    
}
