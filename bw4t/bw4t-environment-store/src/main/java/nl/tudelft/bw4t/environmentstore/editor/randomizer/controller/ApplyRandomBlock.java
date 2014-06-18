package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;
import nl.tudelft.bw4t.map.BlockColor;

public class ApplyRandomBlock implements ActionListener{
	
	private RandomizeBlockFrame view;
	
	private RandomizeBlocksController controller;
	
	private int amount;
	
	public ApplyRandomBlock(RandomizeBlockFrame rf, RandomizeBlocksController controller) {
		this.view = rf;
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ArrayList<BlockColor> input = new ArrayList<>();
		amount = view.getNumberOfBlocks();
		
		if(view.isRed()) {
			input.add(BlockColor.RED);
		}
		if(view.isGreen()) {
			input.add(BlockColor.GREEN);
		}
		if(view.isYellow()) {
			input.add(BlockColor.YELLOW);
		}
		if(view.isBlue()) {
			input.add(BlockColor.BLUE);
		}
		if(view.isOrange()) {
			input.add(BlockColor.ORANGE);
		}
		if(view.isWhite()) {
			input.add(BlockColor.WHITE);
		}
		if(view.isPink()) {
			input.add(BlockColor.PINK);
		}
		try {
			controller.getMapController().randomizeColorsInRooms(input, amount);
			view.dispose();
		}
		catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(view, "Warning: Choose atleast 1 color");
		}
	}
	
}