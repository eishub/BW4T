package nl.tudelft.bw4t.scenariogui.editor.controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

/**
 * Listens to the client ip text field and updates
 * the {@link nl.tudelft.bw4t.scenariogui.BW4TClientConfig} object
 * with the update in the text field.
 *
 */
public class WriteClientIP implements DocumentListener {
    
    private MainPanel view;

    /**
     * Create a new listener to the client ip text field
     * @param newView The parent view.
     */
    public WriteClientIP(final MainPanel newView) {
        this.view = newView;
    }

    @Override
    public void changedUpdate(DocumentEvent arg0) {
        handleUpdate();
    }

    @Override
    public void insertUpdate(DocumentEvent arg0) {
        handleUpdate();
    }

    @Override
    public void removeUpdate(DocumentEvent arg0) {
        handleUpdate();
    }
    
    /**
     * Stores the client ip in the client config when its field is changed.
     */
    private void handleUpdate() {
        view.getClientConfig().setClientIp(
                view.getConfigurationPanel().getClientIPTextField().getText());
    }
    
}
