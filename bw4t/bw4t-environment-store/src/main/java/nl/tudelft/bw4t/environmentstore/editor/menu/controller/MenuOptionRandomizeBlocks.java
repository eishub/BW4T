package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;

/**
 * Menu option to randomize blocks on the map, opens a new panel
 */
public class MenuOptionRandomizeBlocks extends AbstractMenuOption {

    /**
     * Constructor for MenuOptionRandomizeBlocks
     * @param newView the menu the option is on
     * @param controller environment controller
     */
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
        RandomizeBlockFrame frame = new RandomizeBlockFrame("Blocks", super.getMapController());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
