package nl.tudelft.bw4t.visualizations.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;

/**
 * Listens for mouse events on the chat text area and builds a pop up menu
 * accordingly
 * 
 * @author trens
 * 
 */
public class ChatListMouseListener implements MouseListener {
    
    private BW4TClientMapRenderer bw4tClientMapRenderer;
    
    public ChatListMouseListener(BW4TClientMapRenderer bw4tClientMapRenderer) {
        this.bw4tClientMapRenderer = bw4tClientMapRenderer;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        bw4tClientMapRenderer.buildPopUpMenuForChat();
        bw4tClientMapRenderer.getjPopupMenu().show(bw4tClientMapRenderer.getChatSession(), e.getX(), e.getY());
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