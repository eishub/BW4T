package nl.tudelft.bw4t.environmentstore.editor.randomizer.controller;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;

/** The controller class for the Randomize Blocks frame. */
public class RandomizeBlocksController {

    /** The view this class controls. */
    private RandomizeBlockFrame view;
    
    /** The map panel controller linked to this class. */
    private MapPanelController mapController;
    
    /** The Apply button. */
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
