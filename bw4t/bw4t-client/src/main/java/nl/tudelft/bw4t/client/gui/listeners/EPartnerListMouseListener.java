package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.menu.EPartnerMenu;
import nl.tudelft.bw4t.map.view.ViewEPartner;

/**
 * Listens for mouse events on the epartner buttons and builds a pop up menu accordingly
 */
public class EPartnerListMouseListener extends MouseAdapter {
    private final BW4TClientGUI clientRenderer;

    public EPartnerListMouseListener(BW4TClientGUI clientRenderer) {
        this.clientRenderer = clientRenderer;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        final ClientMapController cmc = clientRenderer.getController().getMapController();
        final long holdingEpartner = cmc.getTheBot().getHoldingEpartner();
        ViewEPartner ep = cmc.getViewEPartner(holdingEpartner);
        
        if (holdingEpartner != -1) {
            EPartnerMenu.buildPopUpMenuForEPartner(ep, clientRenderer);
            clientRenderer.getjPopupMenu().show((JButton) e.getSource(), e.getX(), e.getY());
        } 
    }
    
    @Override
    public void mouseReleased(MouseEvent arg0) {
        // Nothing should happen when mouse is released
    }
}
