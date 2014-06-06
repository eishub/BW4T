package nl.tudelft.bw4t.scenariogui.controllers.epartner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.epartner.EpartnerFrame;

/**
 * Handles actions of the ResetButton
 */
class EpartnerResetButton implements ActionListener {
    /**
     * The frame containing the button.
     */
    private EpartnerFrame view;
    /**
     * The constructor for this action listener.
     * @param pview The frame with the button in it.
     */
    public EpartnerResetButton(EpartnerFrame pview) {
        this.view = pview;
    }
    /**
     * Perform the required action (reset to default
     * settings).
     * @param ae The action event triggering this method.
     */
    public void actionPerformed(ActionEvent ae) {
    	view.getEpartnerName().setText(view.getDataObject().getEpartnerName());
    	view.getEpartnerAmount().setText(""+ view.getDataObject().getEpartnerAmount());
    	view.getGPSCheckbox().setSelected(view.getDataObject().isGps());
    	view.getForgetCheckbox().setSelected(view.getDataObject().isForgetMeNot());
    	view.getEpartnerReferenceField().setText(view.getDataObject().getReferenceName());
    	view.getEpartnerGoalFileField().setText(view.getDataObject().getFileName());
    }
}