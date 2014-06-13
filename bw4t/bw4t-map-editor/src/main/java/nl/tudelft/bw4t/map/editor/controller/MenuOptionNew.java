package nl.tudelft.bw4t.map.editor.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.map.editor.gui.AbstractMenuOption;
import nl.tudelft.bw4t.map.editor.gui.MenuBar;

public class MenuOptionNew extends AbstractMenuOption {

	public MenuOptionNew(MenuBar newView,
			EnvironmentStoreController controller) {
		super(newView, controller);
	}
	
    /**
     * Gets called when the menu item save as is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        newMap();
    }

}