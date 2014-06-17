package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Handles actions of the sizeslider
 */
public class SizeSlider extends MouseAdapter {
    /**
     * The panel containing the slider.
     */
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The panel containing the slider.
     */
    public SizeSlider(BotEditorPanel pview) {
        this.view = pview;
    }
    
    /**
     * Update the BatteryUseValueLabel with the correct value when the slider is moved.
     * @param arg0 MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent arg0) {
    	BotController currentController = view.getBotController();
    	currentController.getBotConfig().setBotSize(view.getSizeSlider().getValue());
    	currentController.setNewBatteryValue(view);
    }
}
