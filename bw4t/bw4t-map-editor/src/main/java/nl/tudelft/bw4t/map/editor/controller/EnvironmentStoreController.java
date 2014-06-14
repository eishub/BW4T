package nl.tudelft.bw4t.map.editor.controller;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.map.Zone.Type;
import nl.tudelft.bw4t.map.editor.EnvironmentStore;

/**
 * The EnvironmentStoreController class serves as a controller for the EnvironmentStore
 *
 */
public class EnvironmentStoreController {
	
	private EnvironmentStore view;
	
	private MapPanelController mapController;
	
	/**
	 * The EnvironmentStoreController class takes care of all the ActionListeners.
	 * 
	 * @param es is the JFrame from the environment store.
	 * @param mc is the map we would like to edit.
	 */
	public EnvironmentStoreController(EnvironmentStore es, MapPanelController mc) {
		this.view = es;
		this.mapController = mc;
		
		/** Create all action listeners for the File Menu */
		// New
		getMainView().getTopMenuBar().getMenuItemFileNew().addActionListener(
				new MenuOptionNew(getMainView().getTopMenuBar(), this)
		);		
		// Open
		getMainView().getTopMenuBar().getMenuItemFileOpen().addActionListener(
				new MenuOptionOpen(getMainView().getTopMenuBar(), this)
        );		
		// Save
		getMainView().getTopMenuBar().getMenuItemFileSave().addActionListener(
				new MenuOptionSave(getMainView().getTopMenuBar(), this)
        );		
		// Save As
		getMainView().getTopMenuBar().getMenuItemFileSaveAs().addActionListener(
				new MenuOptionSaveAs(getMainView().getTopMenuBar(), this)
        );		
		// Preview
		getMainView().getTopMenuBar().getMenuItemPreview().addActionListener(
				new MenuOptionPreview(getMainView().getTopMenuBar(), this)
		);		
		// Exit
		getMainView().getTopMenuBar().getMenuItemFileExit().addActionListener(
				new MenuOptionExit(getMainView().getTopMenuBar(), this)
        );
		// Default Exit button on the right top of the window
        getMainView().addWindowListener(
        		new WindowExit(getMainView())
        );
        
        /** Create all action listeners for the Tools Menu */
        // Randomize Rooms in map
        getMainView().getTopMenuBar().getMenuItemRandomizeRooms().addActionListener(
				new MenuOptionRandomizeRooms(getMainView().getTopMenuBar(), this)
        );
		// Randomize Blocks distributions
		getMainView().getTopMenuBar().getMenuItemRandomizeBlocks().addActionListener(
				new MenuOptionRandomizeBlocks(getMainView().getTopMenuBar(), this)
        );
		// Randomize sequence
		getMainView().getTopMenuBar().getMenuItemRandomizeSequence().addActionListener(
				new MenuOptionRandomizeSequence(getMainView().getTopMenuBar(), this)
        );

	}
	
    /**
     * Checks if the configuration has been changed.
     * @return returns true if either the configuration, the bot list of the epartners list has been changed.
     */
    public boolean notAnEmptyMap() {
    	// If the sequence is not empty, the map from scratch has been modified.
    	if(!(mapController.getSequence().isEmpty())) {
    		return true;
    	}
    	// If not all rooms are corridors, the map from scratch has been modified.
        for (int i = 0; i < mapController.getZoneController().length; i++) {
            for (int j = 0; j < mapController.getZoneController()[0].length; j++) {
            	if(!(mapController.getZoneController()[i][j].getType() == Type.CORRIDOR)) {
            		return true;
            	}                
            }
        }
        return false;
    }
	
    /**
     * Ask the user if (s)he wishes to save the Environment.
     * @return True if the user wishes to save the Environment.
     */
    public boolean promptUserToSave() {
        // Check if user wants to save current configuration
    	int response = EnvironmentStore.getOptionPrompt().showConfirmDialog(
                null, "Do you want to save the current configuration?", "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return response == JOptionPane.YES_OPTION;
    }

    /**
     * Ask the user if (s)he wishes to quit the program.
     * @return True if the user wishes to quit.
     */
    public boolean promptUserToQuit() {
        int response = EnvironmentStore.getOptionPrompt().showConfirmDialog(
                null, "Are you sure you want to exit the program?", "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

       return response == JOptionPane.YES_OPTION;
    }
	
    /**
     * Return the view being controlled.
     * @return The JFrame being controlled.
     */
    public final EnvironmentStore getMainView() {
        return view;
    }
    
    /**
     * Return the map being edited.
     * @return The Map being edited.
     */
    public final MapPanelController getMapController() {
    	return mapController;
    }
}
