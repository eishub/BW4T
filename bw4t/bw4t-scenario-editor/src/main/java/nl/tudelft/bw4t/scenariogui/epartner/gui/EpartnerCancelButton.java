package nl.tudelft.bw4t.scenariogui.epartner.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles actions of the CancelButton.
 */
class EpartnerCancelButton implements ActionListener {

    private EpartnerFrame view;

    /**
     * The constructor for this action listener.
     * 
     * @param view
     *            The EpartnerFrame with the button in it.
     */
    public EpartnerCancelButton(EpartnerFrame view) {
        this.view = view;
    }

    /**
     * Perform the required action (close the EpartnerFrame).
     * 
     * @param ae
     *            The action event triggering this method.
     */
    public void actionPerformed(ActionEvent ae) {
        view.dispose();
    }
}
