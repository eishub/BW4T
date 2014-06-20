package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeSequenceFrame;

/** This implements the action listener for the Cancel button in the Randomize Sequence frame. */
public class CancelRandomSequence implements ActionListener{
    
    /** The view the button is in. */
    private RandomizeSequenceFrame view;
    
    /**
     * @param rf
     *         the view
     */
    public CancelRandomSequence(RandomizeSequenceFrame rf) {
        this.view = rf;
    }

    /** Close the view when the button is clicked. */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        view.dispose();
    }
}
