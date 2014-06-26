package nl.tudelft.bw4t.client.gui;

import eis.exceptions.ManagementException;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import nl.tudelft.bw4t.client.controller.ClientController;

import org.apache.log4j.Logger;

/**
 * The Class ClientWindowAdapter.
 */
public final class ClientWindowAdapter extends WindowAdapter {
    
    /** The log4j Logger which displays logs on console. */
    private static final Logger LOGGER = Logger.getLogger(ClientWindowAdapter.class.getName());
    
    /** The bw4t client gui. */
    private final ClientController controller;
    
    /**
     * Instantiates a new client window adapter.
     * 
     * @param bw4tClientGUI
     *            the bw4t client gui
     */
    public ClientWindowAdapter(BW4TClientGUI bw4tClientGUI) {
        this.controller = bw4tClientGUI.getController();
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
     */
    @Override
    public void windowClosing(WindowEvent e) {
        String entity = controller.getMapController().getTheBot().getName();
        LOGGER.info("Exit request received from the Window Manager to close Window of entity: "
                + entity);
      //stop the gui
        controller.stop();
        
        try {
            controller.getEnvironment().killHumanEntity(entity);
        } catch (ManagementException e1) {
            LOGGER.error("Could not correctly kill the environment.", e1);
        }
    }
}
