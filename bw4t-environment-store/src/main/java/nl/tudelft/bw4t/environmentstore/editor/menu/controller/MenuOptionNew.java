package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;

/**
 * MenuOption to create a new map
 */
public class MenuOptionNew extends AbstractMenuOption {

    /**
     * Constructor for MenuOptionNew
     * @param newView the menu the option is on
     * @param controller environment controller
     */
    public MenuOptionNew(MenuBar newView,
            EnvironmentStoreController controller) {
        super(newView, controller);
    }
    
    /**
     * Gets called when the menu item new is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        newMap();
    }

}
