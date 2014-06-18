package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeSequenceFrame;

public class ApplyRandomSequence implements ActionListener{
	
	private RandomizeSequenceFrame view;
	
	private RandomizeController controller;
	
	public ApplyRandomSequence(RandomizeSequenceFrame rf, RandomizeController rc) {
		this.view = rf;
		this.controller = rc;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		controller.getMapController().setSequence(controller.getRandomizeFromSettings().getResult());
		controller.getMapController().getUpdateableEditorInterface().update();
		view.dispose();
	}
}
