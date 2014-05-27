package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

public class SpeedBox implements ActionListener {
	private BotEditorPanel view;
	public SpeedBox(BotEditorPanel view) {
		this.view = view;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JSlider speedSlider = view.getSpeedSlider();
		if(view.getmovespeedCheckbox().isSelected()) {
			speedSlider.setEnabled(true);
		}
		else {
			speedSlider.setEnabled(false);
			speedSlider.setValue(100);
		}
	}
}
