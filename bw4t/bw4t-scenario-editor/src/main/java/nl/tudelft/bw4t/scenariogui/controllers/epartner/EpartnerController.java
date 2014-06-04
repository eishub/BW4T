package nl.tudelft.bw4t.scenariogui.controllers.epartner;

import nl.tudelft.bw4t.scenariogui.gui.epartner.EpartnerFrame;

/**
 * EpartnerController is in charge of all events that happen on the EpartnerGUI.
 * 
 * @author Wendy
 */

public class EpartnerController {

	private EpartnerFrame view;

	/**
	 * Create the Epartner controller
	 * 
	 * @param view
	 *            The parent view, used to call relevant functions by the event
	 *            listeners
	 */
	public EpartnerController(EpartnerFrame view) {
		this.view = view;

		view.getResetButton().addActionListener(
				new EpartnerResetButton(getMainView()));

		view.getCancelButton().addActionListener(
				new EpartnerCancelButton(getMainView()));

		view.getApplyButton().addActionListener(
				new EpartnerSaveButton(getMainView()));

		view.getFileButton().addActionListener(
				new EGoalFileButton(getMainView()));

		view.getLeftAloneCheckbox().addActionListener(
				new LeftAloneCheckBox(getMainView()));

		view.getGPSCheckbox().addActionListener(new gpsCheckBox(getMainView()));
	}

	/**
	 * Returns the epartnerframe.
	 * 
	 * @return view The epartner frame.
	 */
	public EpartnerFrame getMainView() {
		return view;
	}
}
