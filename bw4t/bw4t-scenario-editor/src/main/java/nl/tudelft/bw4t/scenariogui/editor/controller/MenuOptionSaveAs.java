package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.editor.gui.MenuBar;

/**
 * Handles the event to save at a chosen location.
 * 
 * @version     0.1                
 * @since       12-05-2014        
 */
class MenuOptionSaveAs extends AbstractMenuOption {

    /**
     * Constructs a new menu option save as object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     * @param model    The model.
     */
    public MenuOptionSaveAs(final MenuBar view, final ScenarioEditorController mainView, BW4TClientConfig model) {
        super(view, mainView, model);
    }

    /**
     * Gets called when the menu item save as is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        saveFile(true);
        if (getFileChooserApprove()) {
            updateModelAndView();
        }
    }
}
