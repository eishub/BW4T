package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.menu.ChatMenu;

/**
 * Listens for mouse events on the chat text area and builds a pop up menu
 * accordingly
 */
public class ChatListMouseListener implements MouseListener {
    /** The {@link BW4TClientGUI} to listen to and interact with. */
    private final BW4TClientGUI bw4tClientGUI;

    /** @param bw4tClientGUI - The {@link BW4TClientGUI} to listen to and interact with. */
    public ChatListMouseListener(BW4TClientGUI bw4tClientGUI) {
        this.bw4tClientGUI = bw4tClientGUI;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ChatMenu.buildPopUpMenuForChat(bw4tClientGUI);
        bw4tClientGUI.getjPopupMenu().show(bw4tClientGUI.getBotChatSession(), e.getX(), e.getY());
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
