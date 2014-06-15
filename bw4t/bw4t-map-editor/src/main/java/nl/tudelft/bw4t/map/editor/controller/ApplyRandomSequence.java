package nl.tudelft.bw4t.map.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.map.editor.gui.RandomizeFrame;

public class ApplyRandomSequence implements ActionListener{
	
	private RandomizeFrame view;
	
	private RandomizeController controller;
	
	public ApplyRandomSequence(RandomizeFrame rf, RandomizeController rc) {
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
