package nl.tudelft.bw4t.scenariogui.controllers.menubar;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.scenariogui.controllers.Controller;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;

/**
 * Handles the event to save a file.
 */
public class MenuOptionSave extends AbstractMenuOption {

    /**
     * Constructs a new menu option save object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionSave(final MenuBar view, final Controller mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the save menu option gets called.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        saveFile();
        super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
    }
}
