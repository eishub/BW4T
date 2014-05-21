package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.menu.PlayerMenu;

/**
 * Listens for mouse events on the player buttons and builds a pop up menu
 * accordingly
 */
public class TeamListMouseListener implements MouseListener {
    private BW4TClientGUI clientRenderer;
    public TeamListMouseListener(BW4TClientGUI clientRenderer) {
        this.clientRenderer = clientRenderer;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object value = e.getSource();
        if (value != null) {
            String playerId = ((JButton) value).getText();
            PlayerMenu.buildPopUpMenuForRequests(playerId, clientRenderer);
            clientRenderer.getBW4TClientInfo().jPopupMenu.show((JButton) value, e.getX(), e.getY());
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
