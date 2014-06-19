package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeFrame;
import nl.tudelft.bw4t.map.BlockColor;

public class RandomizeController {

	private RandomizeFrame view;
	
	private MapPanelController mapController;
	
	private RandomizeFromSettings randomFromSettings;
	
	/**
	 * The RandomizeController class takes care of all the ActionListeners.
	 * 
	 * @param rf is the Randomize JFrame and main view.
	 */
	public RandomizeController(RandomizeFrame rf, MapPanelController mpc) {
		this.view = rf;
		this.mapController = mpc;
		
		/** Create all action listeners for the File Menu */
		// Randomize from settings
		getMainView().getRandomizeButton().addActionListener(
				this.randomFromSettings = new RandomizeFromSettings(getMainView(), this, null)
		);
		// Apply randomly generated blocks to room or sequence
		getMainView().getApplyButton().addActionListener(
				new ApplyRandomSequence(getMainView(), this)
		);
		// Cancel Randomizer
		getMainView().getCancelButton().addActionListener(
				new CancelRandomSequence(getMainView())
		);
	}
	
	public RandomizeFrame getMainView() {
		return view;
	}
	
	public RandomizeFromSettings getRandomizeFromSettings() {
		return randomFromSettings;
	}
	
	public MapPanelController getMapController() {
		return mapController;
	}
	
    /**
     * Creates a random sequence
     * @param input	the list of available colors
     * @param sequencelength how long you want the sequence to be
     * @return the random sequence
     */
    public List<BlockColor> randomizeSequence(List<BlockColor> input, int sequencelength) {
        Random random = new Random();
        List<BlockColor> sequence = new ArrayList<>();

        for (int n = 0; n < sequencelength; n++) {
            sequence.add(input.get(random.nextInt(input.size())));
        }
        
        return sequence;
    }
}
