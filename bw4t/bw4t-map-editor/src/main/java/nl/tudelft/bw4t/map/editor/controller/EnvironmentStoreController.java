package nl.tudelft.bw4t.map.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import nl.tudelft.bw4t.map.editor.EnvironmentStore;
import nl.tudelft.bw4t.map.renderer.MapRenderer;

/**
 * The EnvironmentStoreController class serves as a controller for the EnvironmentStore
 *
 */
public class EnvironmentStoreController {
	
	private EnvironmentStore view;
	
	private MapPanelController mapcontroller;
	
	/**
	 * The EnvironmentStoreController class takes care of all the ActionListeners.
	 * 
	 * @param es is the JFrame from the environment store.
	 * @param mc is the map we would like to edit.
	 */
	public EnvironmentStoreController(EnvironmentStore es, MapPanelController mc) {
		this.view = es;
		this.mapcontroller = mc;
		
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

	}
	
    /**
     * Ask the user if (s)he wishes to save the scenario.
     * @return True if the user wishes to save the scenario.
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
    	return mapcontroller;
    }
}
