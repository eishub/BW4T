package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;
import nl.tudelft.bw4t.map.BlockColor;

public class RandomizeBlockFrameController {

	private RandomizeBlockFrame view;
	
	private MapPanelController mapController;
	
	private RandomizeFromSettingsBlock randomFromSettings;
	
	/**
	 * The RandomizeController class takes care of all the ActionListeners.
	 * 
	 * @param rf is the Randomize JFrame and main view.
	 */
	public RandomizeBlockFrameController(RandomizeBlockFrame rf, MapPanelController mpc) {
		this.view = rf;
		this.mapController = mpc;
		
		getMainView().getApplyButton().addActionListener(
				this.randomFromSettings = new RandomizeFromSettingsBlock(view, this, null)
		);
		// Cancel Randomizer
		getMainView().getCancelButton().addActionListener(
				new CancelRandomBlocks(getMainView())
		);
	}
	
	public RandomizeBlockFrame getMainView() {
		return view;
	}
	
	public RandomizeFromSettingsBlock getRandomizeFromSettings() {
		return randomFromSettings;
	}
	
	public MapPanelController getMapController() {
		return mapController;
	}
}
