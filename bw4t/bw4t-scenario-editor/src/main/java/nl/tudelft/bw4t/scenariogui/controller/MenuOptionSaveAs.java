package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.scenariogui.gui.MenuBar;

/**
 * Handles the event to save at a chosen location.
 */
public class MenuOptionSaveAs extends AbstractMenuOption {

    /**
     * Constructs a new menu option save as object.
     * @param view The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionSaveAs(final MenuBar view, final Controller mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the menu item save as is pressed.
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        saveFile(true);
        super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
    }
}
