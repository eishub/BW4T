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
    private BW4TClientGUI bw4tClientGUI;
    
    /**
     * Instantiates a new client window adapter.
     * 
     * @param bw4tClientGUI
     *            the bw4t client gui
     */
    public ClientWindowAdapter(BW4TClientGUI bw4tClientGUI) {
        this.bw4tClientGUI = bw4tClientGUI;
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
     */
    @Override
    public void windowClosing(WindowEvent e) {
        ClientController controller = bw4tClientGUI.getController();
        LOGGER.info("Exit request received from the Window Manager to close Window of entity: "
                + controller.getMapController().getTheBot().getName());
        controller.getMapController().setRunning(false);
        bw4tClientGUI.dispose();
        controller.getMapController().removeRenderer(bw4tClientGUI);
        try {
            bw4tClientGUI.environment.kill();
        } catch (ManagementException e1) {
            LOGGER.error("Could not correctly kill the environment.", e1);
        }
    }
}
