package nl.tudelft.bw4t.scenariogui.editor.controller;

import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Listens to the client port text field and updates
 * the {@link nl.tudelft.bw4t.scenariogui.BW4TClientConfig} object
 * with the update in the text field.
 *
 */
public class WriteClientPort extends WriteConfig {
    
    private MainPanel view;

    /**
     * Create a new listener to the client port text field
     * @param newView The parent view.
     */
    public WriteClientPort(final MainPanel newView) {
        this.view = newView;
    }
    
    /**
     * Stores the client port in the BW4TClientConfig when its field is changed.
     */
    public void handleUpdate() {
        int clientPort = view.getConfigurationPanel().getClientPortTextField().getText().equals("") ? 0 :
            view.getConfigurationPanel().getClientPort();
        view.getClientConfig().setClientPort(clientPort);
    }
    
}
