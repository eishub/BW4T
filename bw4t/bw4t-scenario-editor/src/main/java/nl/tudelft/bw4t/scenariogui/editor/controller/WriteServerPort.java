package nl.tudelft.bw4t.scenariogui.editor.controller;

import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Listens to the server port text field and updates
 * the {@link nl.tudelft.bw4t.scenariogui.BW4TClientConfig} object
 * with the update in the text field.
 *
 */
public class WriteServerPort extends WriteConfig {
    
    private MainPanel view;

    /**
     * Create a new listener to the server port text field
     * @param newView The parent view.
     */
    public WriteServerPort(final MainPanel newView) {
        this.view = newView;
    }
    
    /**
     * Stores the server port in the BW4TClientConfig when its field is changed.
     */
    public void handleUpdate() {
        int serverPort = view.getConfigurationPanel().getServerPortTextField().getText().isEmpty() ? 0 :
            view.getConfigurationPanel().getServerPort();
        view.getClientConfig().setServerPort(serverPort);
    }
    
}
