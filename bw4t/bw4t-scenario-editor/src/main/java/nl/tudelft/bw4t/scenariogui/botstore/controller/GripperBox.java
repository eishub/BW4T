package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Handles actions of the gripperbox
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
     * when enabled and sends the new setting to BotConfig.
     * @param ae The action event belonging to the check box.
     */
    public void actionPerformed(ActionEvent ae) {
        JSlider gripSlider = view.getNumberOfGrippersSlider();
        boolean enabled = view.getGripperCheckbox().isSelected();
        if (enabled) {
            gripSlider.setEnabled(false);
        }
        else {
            gripSlider.setEnabled(true);
        }
        
        BotConfig config = view.getTempBotConfig();
        config.setGripperHandicap(enabled);
    }
}
