package nl.tudelft.bw4t.client.gui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import nl.tudelft.bw4t.client.gui.data.structures.BW4TClientInfo;
import nl.tudelft.bw4t.client.gui.menu.ChatMenu;

/**
 * Listens for mouse events on the chat text area and builds a pop up menu
 * accordingly
 * 
 * @author trens
 */
public class ChatListMouseListener implements MouseListener {

    private BW4TClientInfo bw4tClientInfo;

    public ChatListMouseListener(BW4TClientInfo bw4tClientInfo) {
        this.bw4tClientInfo = bw4tClientInfo;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ChatMenu.buildPopUpMenuForChat(bw4tClientInfo);
        bw4tClientInfo.jPopupMenu.show(bw4tClientInfo.chatSession, e.getX(),
                e.getY());
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