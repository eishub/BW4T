package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Handles actions of the sizeslider
 * @author Arun
 */
class SizeSlider extends MouseAdapter {
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
    @Override
    public void mouseReleased(MouseEvent arg0) {
    	if (view.getBatteryEnabledCheckbox().isSelected()) {
	        int speed = view.getSpeedSlider().getValue();
	        int size = view.getSizeSlider().getValue();
	        double res = 0.002 * size + 0.000025 * speed;
	        DecimalFormat df = new DecimalFormat("#.######");
	        String value = df.format(res);
	        view.getBatteryUseValueLabel().setText(padString(value));
    	}
    	view.getDataObject().setBotSize(view.getSizeSlider().getValue());
    }
    
    /**
     * Pad the string with zeros (the string with
     * the value for the battery usage is aligned with
     * the sliders, and will cause the sliders to resize
     * when changed. This function keeps the string at a
     * certain length, so the sliders aren't resized anymore).
     * @param value The string to be padded.
     * @return The padded string.
     */
    public String padString(String value) {
        StringBuffer buf = new StringBuffer(); 
        while (value.length() < 8) {
            buf.append("0");
        }
        return buf.toString();
    }
}
