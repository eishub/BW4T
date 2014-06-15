package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.editor.model.RandomMapCreator;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;

public class MenuOptionRandomizeRooms extends AbstractMenuOption {

	public MenuOptionRandomizeRooms(MenuBar newView,
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
    	
    	RandomMapCreator rmc = new RandomMapCreator();
        rmc.createRandomGrid(5, 5, 3);
    }

}