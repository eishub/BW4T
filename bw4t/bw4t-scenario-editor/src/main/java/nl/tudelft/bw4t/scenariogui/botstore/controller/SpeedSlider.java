package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.util.Format;

/**
 * Handles actions of the speedslider
 */
public class SpeedSlider extends MouseAdapter {
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
    
<<<<<<< HEAD
    @Override
    public void mouseReleased(MouseEvent arg0) {
    	if (view.getBatteryEnabledCheckbox().isSelected()) {
	        int speed = view.getSpeedSlider().getValue();
	        int size = view.getSizeSlider().getValue();
	        double res = 0.002 * size + 0.000025 * speed;
	        DecimalFormat df = new DecimalFormat("#.######");
	        String value = df.format(res);
	        view.getBatteryUseValueLabel().setText(Format.padString(value, 8));
    	}
    	view.getTempBotConfig().setBotSpeed(view.getSpeedSlider().getValue());
=======
    /**
     * Update the BatteryUseValueLabel with the correct value when the slider is moved.
     * @param arg0 MouseEvent
     */
    @Override 
    public void mouseReleased(MouseEvent arg0) {
    	BotController currentController = view.getBotController();
    	currentController.getBotConfig().setBotSpeed(view.getSpeedSlider().getValue());
    	currentController.setNewBatteryValue(view);
>>>>>>> master
    }

}
