package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;

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
        RandomizeBlockFrame frame = new RandomizeBlockFrame("Blocks", super.getMapController());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
