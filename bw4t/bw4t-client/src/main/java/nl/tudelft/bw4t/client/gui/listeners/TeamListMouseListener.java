package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.menu.PlayerMenu;

/**
 * Listens for mouse events on the player buttons and builds a pop up menu accordingly
 */
public class TeamListMouseListener implements MouseListener {
	private final BW4TClientGUI clientRenderer;

	public TeamListMouseListener(BW4TClientGUI clientRenderer) {
		this.clientRenderer = clientRenderer;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		String playerId = clientRenderer.getAgentSelector().getSelectedItem().toString();
		PlayerMenu.buildPopUpMenuForRequests(playerId, clientRenderer);
		clientRenderer.getjPopupMenu().show((JButton) e.getSource(), e.getX(), e.getY());
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
