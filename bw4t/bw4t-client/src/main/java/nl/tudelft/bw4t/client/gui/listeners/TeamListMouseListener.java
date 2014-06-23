package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.menu.PlayerMenu;

/** Listens for mouse events on the player buttons and builds a pop up menu accordingly. */
public class TeamListMouseListener implements MouseListener {
    /** The {@link ClientController} to listen to and interact with. */
    private final ClientController controller;

    /** @param controller - The {@link ClientController} to listen to and interact with. */
    public TeamListMouseListener(ClientController controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        BW4TClientGUI gui = controller.getGui();
        String playerId = gui.getAgentSelector().getSelectedItem().toString();
        PlayerMenu.buildPopUpMenuForRequests(playerId, controller);
        gui.getjPopupMenu().show((JButton) e.getSource(), e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        //Nothing should happen on this mouse event
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
      //Nothing should happen on this mouse event
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
      //Nothing should happen on this mouse event
    }
}
