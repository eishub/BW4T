package nl.tudelft.bw4t.scenariogui.controller;

import nl.tudelft.bw4t.scenariogui.gui.epartner.EpartnerFrame;

/**
 * EpartnerController is in charge of all events that happen on the EpartnerGUI.
 * 
 * @author Wendy
 */

public class EpartnerController {

	/**
	 * The view being controlled
	 */
	private EpartnerFrame view;

	/**
	 * Create the Epartner controller
	 * 
	 * @param pview
	 *            The parent view, used to call relevant functions by the event
	 *            listeners
	 */
	public EpartnerController(EpartnerFrame view) {
		this.view = view;

		view.getResetButton().addActionListener(
				new EpartnerResetButton(getMainView()));

		view.getCancelButton().addActionListener(
				new EpartnerCancelButton(getMainView()));

		view.getApplyButton().addActionListener(new EpartnerApplyButton(getMainView()));

		view.getLeftAloneCheckbox().addActionListener(
				new LeftAloneCheckBox(getMainView()));

		view.getGPSCheckbox().addActionListener(new gpsCheckBox(getMainView()));
	}

	/**
	 * @return view
	 */
	public EpartnerFrame getMainView() {
		return view;
	}
}
