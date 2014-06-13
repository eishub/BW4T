package nl.tudelft.bw4t.scenariogui.botstore.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import nl.tudelft.bw4t.scenariogui.botstore.controller.BotController;

/**
 * Handles actions of the speedslider
 */
class SpeedSlider extends MouseAdapter {
    /**
     * The panel containing the slider.
     */
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The panel containing the slider.
     */
    public SpeedSlider(BotEditorPanel pview) {
        this.view = pview;
    }
    
    /**
     * Update the BatteryUseValueLabel with the correct value when the slider is moved.
     * @param arg0 MouseEvent
     */
    @Override 
    public void mouseReleased(MouseEvent arg0) {
    	BotController currentController = view.getBotController();
    	currentController.setNewBatteryValue(view.getBotSpeed(), view.getBotSize(), view);
    }
}
