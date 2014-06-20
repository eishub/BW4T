package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;

public class RandomizeBlocksController {

    private RandomizeBlockFrame view;
    
    private MapPanelController mapController;
    
    private ApplyRandomBlock applyRandomBlock;
    
    /**
     * The RandomizeController class takes care of all the ActionListeners.
     * 
     * @param rf is the Randomize JFrame and main view.
     */
    public RandomizeBlocksController(RandomizeBlockFrame rf, MapPanelController mpc) {
        this.view = rf;
        this.mapController = mpc;
        
        getMainView().getApplyButton().addActionListener(
                this.applyRandomBlock = new ApplyRandomBlock(view, this)
        );

        getMainView().getCancelButton().addActionListener(
                new CancelRandomBlocks(getMainView())
        );
    }
    
    public RandomizeBlockFrame getMainView() {
        return view;
    }
    
    public MapPanelController getMapController() {
        return mapController;
    }
}
