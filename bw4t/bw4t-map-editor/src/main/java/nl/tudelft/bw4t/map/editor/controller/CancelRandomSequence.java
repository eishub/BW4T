package nl.tudelft.bw4t.map.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.map.editor.gui.RandomizeFrame;

public class CancelRandomSequence implements ActionListener{
	
	private RandomizeFrame view;
	
	public CancelRandomSequence(RandomizeFrame rf) {
		this.view = rf;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		view.dispose();
	}
}
