package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Handles actions of the reset button.
 */
public class ResetButton implements ActionListener {

    private BotEditorPanel view;
    
    /**
     * Constructor.
     * @param pview The panel of the jump box.
     */
    public ResetButton(BotEditorPanel pview) {
        this.view = pview;
    }
    
    /**
     * Resets all parts of the BotEditorPanel
     * to their last saved value.
     * @param ae The action event causing this.
     */
    public void actionPerformed(ActionEvent ae) {
        view.updateView();
    }   
}
