package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the gripperbox
 */
class GripperBox implements ActionListener {
    private BotEditorPanel view;

    public GripperBox(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {
    	JSlider gripSlider = view.getNumberOfGrippersSlider();
    	if(view.getGripperCheckbox().isSelected()) {
    		gripSlider.setEnabled(false);
    	}
    	else {
    		gripSlider.setEnabled(true);
    	}
    }
}