package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.editor.model.EnvironmentMap;
import nl.tudelft.bw4t.environmentstore.editor.model.RandomMapCreator;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;

/**
 * Menu option to randomize the rooms on the map
 */
public class MenuOptionRandomizeZones extends AbstractMenuOption {

    /**
     * Constructor for MenuOptionRandomizeRooms
     * @param newView the menu this option is on
     * @param controller environment controller
     */
    public MenuOptionRandomizeZones(MenuBar newView,
            EnvironmentStoreController controller) {
        super(newView, controller);
    }
    
    /**
     * Gets called when the menu item Randomize Rooms is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        MapPanelController mpc = super.getMapController();
        int amountRooms = 0;
        int rows = mpc.getRows();
        int cols = mpc.getColumns();
        int maxRooms = RandomMapCreator.maxRoomsPossible(rows, cols);
        amountRooms = (int) (Math.random() * (maxRooms * 0.8 - maxRooms * 0.5) + maxRooms * 0.5);
        
        ZoneModel[][] grid = RandomMapCreator.createRandomGrid(rows, cols, amountRooms);

        EnvironmentMap model = mpc.getEnvironmentMap();
        model.setZones(grid);
        //make sure we update the view
        mpc.setModel(model);
        mpc.setStartzone(true);
        mpc.setDropzone(true);     
    }

}
