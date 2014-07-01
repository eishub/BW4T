package nl.tudelft.bw4t.environmentstore.main.controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.MenuOptionExit;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.MenuOptionNew;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.MenuOptionOpen;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.MenuOptionPreview;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.MenuOptionRandomizeBlocks;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.MenuOptionRandomizeZones;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.MenuOptionRandomizeSequence;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.MenuOptionSave;
import nl.tudelft.bw4t.environmentstore.editor.menu.controller.MenuOptionSaveAs;
import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.map.Zone.Type;

/**
 * The EnvironmentStoreController class serves as a controller for the EnvironmentStore
 *
 */
public class EnvironmentStoreController extends ComponentAdapter {
    
    /** The view of our Environment Store. */
    private EnvironmentStore view;
    
    /** The controller for the Map Panel. */
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
        
        getMainView().addComponentListener(this);
        
        final MenuBar bar = getMainView().getTopMenuBar();
        
        /** Create all action listeners for the File Menu */
        // New
        bar.getMenuItemFileNew().addActionListener(
                new MenuOptionNew(bar, this)
        );        
        // Open
        bar.getMenuItemFileOpen().addActionListener(
                new MenuOptionOpen(bar, this)
        );        
        // Save
        bar.getMenuItemFileSave().addActionListener(
                new MenuOptionSave(bar, this)
        );        
        // Save As
        bar.getMenuItemFileSaveAs().addActionListener(
                new MenuOptionSaveAs(bar, this)
        );        
        // Preview
        bar.getMenuItemPreview().addActionListener(
                new MenuOptionPreview(bar, this)
        );        
        // Exit
        bar.getMenuItemFileExit().addActionListener(
                new MenuOptionExit(bar, this)
        );
        // Default Exit button on the right top of the window
        getMainView().addWindowListener(
                new WindowExit(getMainView())
        );
        
        /** Create all action listeners for the Tools Menu */
        // Randomize Rooms in map
        bar.getMenuItemRandomizeZones().addActionListener(
                new MenuOptionRandomizeZones(bar, this)
        );
        // Randomize Blocks distributions
        bar.getMenuItemRandomizeBlocks().addActionListener(
                new MenuOptionRandomizeBlocks(bar, this)
        );
        // Randomize sequence
        bar.getMenuItemRandomizeSequence().addActionListener(
                new MenuOptionRandomizeSequence(bar, this)
        );

    }
    
    @Override
    public void componentMoved(ComponentEvent arg0) {
        mapController.getCSController().updatePosition();
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
        for (int i = 0; i < mapController.getZoneControllers().length; i++) {
            for (int j = 0; j < mapController.getZoneControllers()[0].length; j++) {
                if(!(mapController.getZoneControllers()[i][j].getType() == Type.CORRIDOR)) {
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
