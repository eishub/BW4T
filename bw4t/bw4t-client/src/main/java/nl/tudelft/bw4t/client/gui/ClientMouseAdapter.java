package nl.tudelft.bw4t.client.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import nl.tudelft.bw4t.client.gui.menu.ActionPopUpMenu;
import nl.tudelft.bw4t.map.renderer.MapRenderSettings;

/**
 * The Class ClientMouseAdapter.
 */
public final class ClientMouseAdapter extends MouseAdapter {
    
    /** The mouse over state. */
    private boolean mouseOver = false;
    
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
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent arg0) {
        super.mouseEntered(arg0);
        mouseOver = true;
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent arg0) {
        super.mouseExited(arg0);
        mouseOver = false;
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

    /* (non-Javadoc)
     * @see java.awt.event.MouseAdapter#mouseWheelMoved(java.awt.event.MouseWheelEvent)
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if (mouseOver && mwe.isControlDown()) {
            MapRenderSettings settings = bw4tClientGUI.getController().getMapController().getRenderSettings();
            if (mwe.getUnitsToScroll() >= 0) {
                settings.setScale(settings.getScale() + 0.1);
            } else {
                settings.setScale(settings.getScale() - 0.1);
            }
        } else {
            super.mouseWheelMoved(mwe);
        }
    }
}
