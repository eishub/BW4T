package nl.tudelft.bw4t.scenariogui.epartner.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Handles actions of the SaveButton.
 */
class EpartnerSaveButton implements ActionListener {

    private EpartnerFrame view;

    private MainPanel parent;

    /**
     * The constructor for this action listener.
     * 
     * @param pview
     *            The frame with the button in it.
     */
    public EpartnerSaveButton(EpartnerFrame pview) {
        this.view = pview;
        this.parent = pview.getEpartnerController().getParent();
    }

    /**
     * Perform the required action (save the e-partner).
     * 
     * @param ae
     *            The action event triggering this method.
     */
    public void actionPerformed(ActionEvent ae) {
        view.getEpartnerController().updateConfig(view);
        parent.refreshEPartnerTableModel();
        view.dispose();
    }
}
