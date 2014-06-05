package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Handles actions of the numberOfGrippersSlider
 * @author Wendy
 */
public class GripperSlider extends MouseAdapter {
    /**
     * The BotEditorPanel to get components from.
     */
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The BotEditorPanel containing this slider.
     */
    public GripperSlider(BotEditorPanel pview) {
        this.view = pview;
    }
    
    /**
     * When the slider is moved, the new setting is saved in the dataobject.
     * @param arg0 The action event caused by releasing the mouse.
     */
    public void mouseReleased(MouseEvent arg0) {
    	view.getDataObject().setGrippers(view.getNumberOfGrippersSlider().getValue());
    }
}
