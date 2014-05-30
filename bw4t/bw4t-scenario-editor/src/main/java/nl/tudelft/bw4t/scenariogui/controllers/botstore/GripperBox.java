package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the gripperbox
 * @author Arun
 */
class GripperBox implements ActionListener {
	/**
	 * The panel containing this checkbox.
	 */
    private BotEditorPanel view;
    /**
     * Constructor
     * @param pview The panel that contains the button and the
     * required sliders.
     */
    public GripperBox(BotEditorPanel pview) {
        this.view = pview;
    }
    /**
     * Performs the following action: disables the gripper slider
     * when enabled.
     * @param ae The action event belonging to the check box.
     */
    public void actionPerformed(ActionEvent ae) {
    	JSlider gripSlider = view.getNumberOfGrippersSlider();
    	if (view.getGripperCheckbox().isSelected()) {
    		gripSlider.setEnabled(false);
    	}
    	else {
    		gripSlider.setEnabled(true);
    	}
    }
}
