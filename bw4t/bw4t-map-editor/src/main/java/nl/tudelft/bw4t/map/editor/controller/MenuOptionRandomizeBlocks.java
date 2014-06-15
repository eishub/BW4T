package nl.tudelft.bw4t.map.editor.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.map.editor.gui.MenuBar;

public class MenuOptionRandomizeBlocks extends AbstractMenuOption {

	public MenuOptionRandomizeBlocks(MenuBar newView,
			EnvironmentStoreController controller) {
		super(newView, controller);
	}
	
    /**
     * Gets called when the menu item Randomize Blocks is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
    	getMapController().randomizeColorsInRooms();
    }

}