package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;

public class CancelRandomBlocks implements ActionListener{
    
    private RandomizeBlockFrame view;
    
    public CancelRandomBlocks(RandomizeBlockFrame rf) {
        this.view = rf;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        view.dispose();
    }
}
