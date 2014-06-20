package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.environmentstore.editor.controller.UpdateableEditorInterface;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeSequenceFrame;

public class ApplyRandomSequence implements ActionListener{
    
    private RandomizeSequenceFrame view;
    
    private RandomizeSequenceController controller;
    
    public ApplyRandomSequence(RandomizeSequenceFrame rf, RandomizeSequenceController rc) {
        this.view = rf;
        this.controller = rc;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        controller.getMapController().setSequence(controller.getRandomizeFromSettings().getResult());
        
        controller.getMapController().setUpdateableEditorInterface(new UpdateableEditorInterface() {
            
            @Override
            public void update() {
                // TODO Auto-generated method stub
                
            }
        });
        
        controller.getMapController().getUpdateableEditorInterface().update();
        view.dispose();
    }
}
