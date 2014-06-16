package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;

public class ApplyRandomBlocks implements ActionListener{
	
	private RandomizeBlockFrame view;
	
	private RandomizeBlockFrameController controller;
	
	public ApplyRandomBlocks(RandomizeBlockFrame rf, RandomizeBlockFrameController mpc) {
		this.view = rf;
		this.controller = mpc;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		view.dispose();
	}
}
