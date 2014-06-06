package nl.tudelft.bw4t.scenariogui.editor.controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Listens to the client port text field and updates
 * the {@link nl.tudelft.bw4t.scenariogui.BW4TClientConfig} object
 * with the update in the text field.
 *
 */
public class WriteClientPort implements DocumentListener {
    
    private MainPanel view;

    /**
     * Create a new listener to the client port text field
     * @param newView The parent view.
     */
    public WriteClientPort(final MainPanel newView) {
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
     * Stores the client port in the client config when its field is changed.
     */
    private void handleUpdate() {
        int clientPort = view.getConfigurationPanel().getClientPortTextField().getText().equals("") ? 0 :
            view.getConfigurationPanel().getClientPort();
        view.getClientConfig().setClientPort(clientPort);
    }
    
}
