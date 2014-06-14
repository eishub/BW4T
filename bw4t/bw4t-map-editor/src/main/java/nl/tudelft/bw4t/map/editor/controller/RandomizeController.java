package nl.tudelft.bw4t.map.editor.controller;

import nl.tudelft.bw4t.map.editor.EnvironmentStore;
import nl.tudelft.bw4t.map.editor.gui.RandomizeFrame;

public class RandomizeController {

	private RandomizeFrame view;
	
	/**
	 * The RandomizeController class takes care of all the ActionListeners.
	 * 
	 * @param rf is the Randomize JFrame and main view.
	 */
	public RandomizeController(RandomizeFrame rf) {
		this.view = rf;
		
		/** Create all action listeners for the File Menu */
		// Randomize from settings
		getMainView().getRandomizeButton().addActionListener(
				new RandomizeFromSettings(getMainView(), this)
		);
		// Apply randomly generated blocks to room or sequence
		getMainView().getApplyButton().addActionListener(
				new ApplyRandomSequence(getMainView(), this)
		);
		// Cancel Randomizer
		getMainView().getCancelButton().addActionListener(
				new CancelRandomSequence(getMainView(), this)
		);
	}
	
	public RandomizeFrame getMainView() {
		return view;
	}
}
