package nl.tudelft.bw4t.scenariogui.epartner.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles actions of the ResetButton.
 */
class EpartnerResetButton implements ActionListener {

    private EpartnerFrame view;
    
    /**
     * The constructor for this action listener.
     * @param pview The frame with the button in it.
     */
    public EpartnerResetButton(EpartnerFrame pview) {
        this.view = pview;
    }
    
    /**
     * Perform the required action (reset to last saved
     * settings).
     * @param ae The action event triggering this method.
     */
    public void actionPerformed(ActionEvent ae) {
        view.updateView();
    }
}
