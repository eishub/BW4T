package nl.tudelft.bw4t.scenariogui.controllers.editor;


import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

/**
 * Listens to the map file text field and updates
 * the {@link nl.tudelft.bw4t.scenariogui.BW4TClientConfig} object
 * with the update in the text field.
 *
 */
public class WriteMapFile implements DocumentListener {

    private MainPanel view;

    /**
     * Create a new listener to the server ip text field
     * @param newView The parent view.
     */
    public WriteMapFile(final MainPanel newView) {
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
     * Stores the map file path in the client config when its field is changed.
     */
    private void handleUpdate() {
        view.getClientConfig().setMapFile(
                view.getConfigurationPanel().getMapFileTextField().getText());
    }
    
}
