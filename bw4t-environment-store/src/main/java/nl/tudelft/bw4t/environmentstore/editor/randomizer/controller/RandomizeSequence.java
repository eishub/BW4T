package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeSequenceFrame;
import nl.tudelft.bw4t.map.BlockColor;

/** This class implements the action listener for the randomize sequence button. */
public class RandomizeSequence implements ActionListener {

    /** The randomize sequence frame. */
    private RandomizeSequenceFrame view;

    /** The controller for this frame. */
    private RandomizeSequenceController controller;

    /** The list of block colours the randomizer will compute. */
    private List<BlockColor> result = null;

    /**
     * @param rf
     *             the frame
     * @param rc
     *          the controller for this frame 
     * @param res
     *          the list of block colors to return
     */
    public RandomizeSequence(RandomizeSequenceFrame rf,
            RandomizeSequenceController rc, List<BlockColor> res) {
        this.view = rf;
        this.controller = rc;
        this.result = res;
    }

    /**
     * Depending on which checkboxes are checked, add block colors to the list,
     * after they have gone through the randomizer. 
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        ArrayList<BlockColor> input = new ArrayList<>();
        int amount = view.getNumberOfBlocks();

        if (view.isRed()) {
            input.add(BlockColor.RED);
        }
        if (view.isGreen()) {
            input.add(BlockColor.GREEN);
        }
        if (view.isYellow()) {
            input.add(BlockColor.YELLOW);
        }
        if (view.isBlue()) {
            input.add(BlockColor.BLUE);
        }
        if (view.isOrange()) {
            input.add(BlockColor.ORANGE);
        }
        if (view.isWhite()) {
            input.add(BlockColor.WHITE);
        }
        if (view.isPink()) {
            input.add(BlockColor.PINK);
        }
        try {
            List<BlockColor> result = controller.randomizeSequence(input,
                    amount);
            setResult(result);
            view.getRandomizedSequence().setText(result.toString());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view,
                    "Warning: Choose atleast 1 color");
        }
    }

    public void setResult(List<BlockColor> res) {
        result = res;
    }

    public List<BlockColor> getResult() {
        return result;
    }
}
