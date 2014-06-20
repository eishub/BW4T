package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeSequenceFrame;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;

/**
 * Menu option to randomize the sequence, opens a new panel
 */
public class MenuOptionRandomizeSequence extends AbstractMenuOption {

    /**
     * Constructor for MenuOptionRandomizeSequence
     * @param newView the  menu the option is on
     * @param controller environment controller
     */
    public MenuOptionRandomizeSequence(MenuBar newView,
            EnvironmentStoreController controller) {
        super(newView, controller);
    }
    
    /**
     * Gets called when the menu item Randomize Sequence as is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        RandomizeSequenceFrame frame = new RandomizeSequenceFrame("Sequence", super.getMapController());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
