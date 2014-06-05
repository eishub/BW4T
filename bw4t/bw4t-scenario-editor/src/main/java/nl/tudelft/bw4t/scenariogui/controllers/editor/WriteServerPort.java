package nl.tudelft.bw4t.scenariogui.controllers.editor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Listens to the server port text field and updates
 * the {@link nl.tudelft.bw4t.scenariogui.BW4TClientConfig} object
 * with the update in the text field.
 * 
 * @author Nick
 *
 */
public class WriteServerPort implements DocumentListener {
    
    /**
     * The {@link MainPanel} serving as the content pane.
     */
    private MainPanel view;

    /**
     * Create a new listener to the client ip text field
     * @param newView The parent view.
     */
    public WriteServerPort(final MainPanel newView) {
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
     * Stores the server port in the client config when its field is changed.
     */
    private void handleUpdate() {
        int serverPort = view.getConfigurationPanel().getServerPortTextField().getText().equals("") ? 0 :
            view.getConfigurationPanel().getServerPort();
        view.getClientConfig().setServerPort(serverPort);
    }
    
}
