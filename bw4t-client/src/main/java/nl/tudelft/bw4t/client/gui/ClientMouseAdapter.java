package nl.tudelft.bw4t.client.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import nl.tudelft.bw4t.client.gui.menu.ActionPopUpMenu;

/**
 * The Class ClientMouseAdapter.
 */
public final class ClientMouseAdapter extends MouseAdapter {
    
    /** The bw4t client gui. */
    private BW4TClientGUI bw4tClientGUI;
    
    /**
     * Instantiates a new client mouse adapter.
     * 
     * @param bw4tClientGUI
     *            the bw4t client gui
     */
    public ClientMouseAdapter(BW4TClientGUI bw4tClientGUI){
        this.bw4tClientGUI = bw4tClientGUI;
        bw4tClientGUI.getController().getMapController();
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        bw4tClientGUI.setSelectedLocation(mouseX, mouseY);
        ActionPopUpMenu.buildPopUpMenu(bw4tClientGUI);
    }
}
