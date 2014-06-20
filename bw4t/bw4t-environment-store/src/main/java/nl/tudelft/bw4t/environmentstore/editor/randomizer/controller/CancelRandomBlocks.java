package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;

/**
 * This class implements the action listener for the cancel button
 * in the randomize blocks frame. 
 */
public class CancelRandomBlocks implements ActionListener{
    
    /** The frame in question. */
    private RandomizeBlockFrame view;
    
    /**
     * @param rf
     *         The randomize blocks frame.
     */
    public CancelRandomBlocks(RandomizeBlockFrame rf) {
        this.view = rf;
    }

    /** The frame is closed. */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        view.dispose();
    }
}
