package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.menu.PlayerMenu;

/** Listens for mouse events on the player buttons and builds a pop up menu accordingly. */
public class TeamListMouseListener implements MouseListener {
	/** The {@link BW4TClientGUI} to listen to and interact with. */
    private final BW4TClientGUI bw4tClientGUI;

    /** @param bw4tClientGUI - The {@link BW4TClientGUI} to listen to and interact with. */
    public TeamListMouseListener(BW4TClientGUI bw4tClientGUI) {
        this.bw4tClientGUI = bw4tClientGUI;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        String playerId = bw4tClientGUI.getAgentSelector().getSelectedItem().toString();
        PlayerMenu.buildPopUpMenuForRequests(playerId, bw4tClientGUI);
        bw4tClientGUI.getjPopupMenu().show((JButton) e.getSource(), e.getX(), e.getY());
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
