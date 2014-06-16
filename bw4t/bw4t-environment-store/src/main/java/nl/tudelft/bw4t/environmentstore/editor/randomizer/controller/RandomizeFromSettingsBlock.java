package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;
import nl.tudelft.bw4t.map.BlockColor;

public class RandomizeFromSettingsBlock implements ActionListener{
	
	private RandomizeBlockFrame view;
	
	private RandomizeBlockFrameController controller;
	
	private ArrayList<BlockColor> result = null;
	
	private int amount;
	
	public RandomizeFromSettingsBlock(RandomizeBlockFrame rf, RandomizeBlockFrameController controller,ArrayList<BlockColor> res) {
		this.view = rf;
		this.result = res;
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
		controller.getMapController().randomizeColorsInRooms(input, amount);
	}
	
	public void setResult(ArrayList<BlockColor> res) {
		result = res;
	}
	
	public ArrayList<BlockColor> getResult() {
		return result;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
