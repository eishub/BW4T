package nl.tudelft.bw4t.scenariogui.epartner.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.epartner.gui.EpartnerFrame;

/**
 * Handles actions of the LeftAloneCheckBox
 */
class LeftAloneCheckBox implements ActionListener {

	private EpartnerFrame view;

	/**
	 * The constructor for this action listener.
	 * 
	 * @param pview
	 *            The frame with the button in it.
	 */
	public LeftAloneCheckBox(EpartnerFrame pview) {
		this.view = pview;
	}

	/**
	 * Perform the required action (save the setting).
	 * 
	 * @param ae
	 *            The action event triggering this method.
	 */
	public void actionPerformed(ActionEvent ae) {
		view.getDataObject().setForgetMeNot(
				view.getLeftAloneCheckbox().isSelected());
	}
}
