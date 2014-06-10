package nl.tudelft.bw4t.scenariogui.botstore.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles actions of the resetbutton
 */
class ResetButton implements ActionListener {
    /**
     * The panel containing the button.
     */
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The panel of the jump box.
     */
    public ResetButton(BotEditorPanel pview) {
        this.view = pview;
    }
    /**
     * Resets all parts of the bot editor panel
     * to their standard value.
     * @param ae The action event causing this.
     */
    public void actionPerformed(ActionEvent ae) {
    	view.updateView();
    }   
}
