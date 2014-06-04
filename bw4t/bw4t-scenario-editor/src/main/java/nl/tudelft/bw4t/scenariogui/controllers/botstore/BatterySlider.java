package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.JSlider;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the batteryslider
 * @author Arun
 */
class BatterySlider extends MouseAdapter {
    /**
     * The BotEditorPanel to get components from.
     */
    @SuppressWarnings("unused")
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The BotEditorPanel containing this slider.
     */
    public BatterySlider(BotEditorPanel pview) {
        this.view = pview;
    }
    
    /**
     * When the slider is moved, the new setting is saved in the dataobject.
     * @param arg0 The action event caused by releasing the mouse.
     */
    public void mouseReleased(MouseEvent arg0) {
    	view.getDataObject().setBotBatteryCapacity(view.getBatterySlider().getValue());
    }
}
