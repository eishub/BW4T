package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.menu.EPartnerMenu;
import nl.tudelft.bw4t.map.view.ViewEPartner;

/**
 * Listens for mouse events on the epartner buttons and builds a pop up menu accordingly
 */
public class EPartnerListMouseListener implements MouseListener {
    private final BW4TClientGUI clientRenderer;

    public EPartnerListMouseListener(BW4TClientGUI clientRenderer) {
        this.clientRenderer = clientRenderer;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ClientMapController cmc = clientRenderer.getController().getMapController();
        ViewEPartner ep = cmc.getViewEPartner(cmc.getTheBot().getHoldingEpartner());
        if (cmc.getTheBot().getHoldingEpartner() != -1) {
            clientRenderer.getEpartnerMessageButton().setEnabled(true);
            EPartnerMenu.buildPopUpMenuForEPartner(ep, clientRenderer);
            clientRenderer.getjPopupMenu().show((JButton) e.getSource(), e.getX(), e.getY());
        } else {
            clientRenderer.getEpartnerMessageButton().setEnabled(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }
}
