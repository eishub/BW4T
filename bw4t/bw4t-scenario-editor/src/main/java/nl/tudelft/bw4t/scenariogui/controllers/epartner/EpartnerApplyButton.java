package nl.tudelft.bw4t.scenariogui.controllers.epartner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.epartner.EpartnerFrame;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles actions of the ApplyButton
 */
class EpartnerApplyButton implements ActionListener {

	private EpartnerFrame view;

	private MainPanel mpanel;

	/**
	 * The constructor for this action listener.
	 * 
	 * @param pview
	 *            The frame with the button in it.
	 */
	public EpartnerApplyButton(EpartnerFrame pview) {
		this.view = pview;
		mpanel = pview.getPanel();
	}

	/**
	 * Perform the required action (save the settings).
	 * 
	 * @param ae
	 *            The action event triggering this method.
	 */
	public void actionPerformed(ActionEvent ae) {
		view.getDataObject().setEpartnerName(view.getEpartnerName().getText());
		view.getDataObject().setEpartnerAmount(
				Integer.parseInt(view.getEpartnerAmount().getText()));
		view.dispose();
	}
}
