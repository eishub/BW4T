package nl.tudelft.bw4t.scenariogui.editor.controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class handles updating the BW4TClientConfig from the ConfigurationPanel.
 */
public abstract class WriteConfig implements DocumentListener {

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
    
    abstract void handleUpdate();
}
