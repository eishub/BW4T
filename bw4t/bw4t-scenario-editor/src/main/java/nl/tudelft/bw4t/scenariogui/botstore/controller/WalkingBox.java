package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Handles actions of the walkingcheckbox
 * @author Arun
 */
class WalkingBox implements ActionListener {
    /**
     * The panel containing the check box.
     */
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The panel of the jump box.
     */
    public WalkingBox(BotEditorPanel pview) {
        this.view = pview;
    }
    /**
     * Enables the size slider when checked.
     * @param ae The action event leading to execution of this method.
     */
    public void actionPerformed(ActionEvent ae) {
        JSlider sizeSlider = view.getSizeSlider();
        if (view.getsizeoverloadCheckbox().isSelected()) {
            sizeSlider.setEnabled(true);
        }
        else {
            sizeSlider.setEnabled(false);
            sizeSlider.setValue(2);
        }
        view.getDataObject().setSizeOverloadHandicap(view.getsizeoverloadCheckbox().isSelected());
    }
}
