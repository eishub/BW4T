package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;
/**
 * Menu option to close the whole map editor
 */
public class MenuOptionExit extends AbstractMenuOption {

    /**
     * Constructor for MenuOptionExit
     * @param newView the menu the option is on
     * @param controller the environment controller
     */
    public MenuOptionExit(MenuBar newView,
            EnvironmentStoreController controller) {
        super(newView, controller);
    }
    
    /**
     * Gets called when the menu item save as is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        exitEditor();
    }

}
