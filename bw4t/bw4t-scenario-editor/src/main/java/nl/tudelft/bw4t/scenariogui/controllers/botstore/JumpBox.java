package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the jumpingcheckbox
 * @author Arun
 */
class JumpBox implements ActionListener {
	/**
	 * The panel containing the check box.
	 */
    @SuppressWarnings("unused")
	private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The panel of the jump box.
     */
    public JumpBox(BotEditorPanel pview) {
        this.view = pview;
    }
    /**
     * Performs no action at the moment
     * NOTE: This was intended to be used as a switch
     * to enable bots to walk over others, but the idea seems to
     * be discarded at the moment.
     * @param ae The action event belonging to the action.
     */
    public void actionPerformed(ActionEvent ae) {

    }
}
