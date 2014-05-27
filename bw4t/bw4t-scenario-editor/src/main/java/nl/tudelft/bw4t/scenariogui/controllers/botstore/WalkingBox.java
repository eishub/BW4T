package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the walkingcheckbox
 */
class WalkingBox implements ActionListener {
    private BotEditorPanel view;

    public WalkingBox(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {
    	JSlider sizeSlider = view.getSizeSlider();
    	if(view.getsizeoverloadCheckbox().isSelected()) {
    		sizeSlider.setEnabled(true);
    	}
    	else {
    		sizeSlider.setEnabled(false);
    		sizeSlider.setValue(2);
    	}
    }
}
