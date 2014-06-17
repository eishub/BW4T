package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Handles actions of the gripperbox
 */
public class GripperBox implements ActionListener {
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
     * Performs the necessary action of the gripper-enabling checkbox.
     * @param arg0 The action event caused by checking or unchecking
     * the checkbox.
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
    	view.setGripperSliderEnabled(!(view.getGripperHandicap()));
    }
}
