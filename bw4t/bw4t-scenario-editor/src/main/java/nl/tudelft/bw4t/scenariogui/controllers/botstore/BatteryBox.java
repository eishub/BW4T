package nl.tudelft.bw4t.scenariogui.controllers.botstore;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

public class BatteryBox implements ActionListener {
	private BotEditorPanel view;
	public BatteryBox(BotEditorPanel view) {
		this.view = view;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JSlider batterySlider = view.getBatterySlider();
    	if(view.getBatteryEnabledCheckbox().isSelected()) {
    		batterySlider.setEnabled(true);
    	}
    	else {
    		batterySlider.setEnabled(false);
    	}
	}
}
