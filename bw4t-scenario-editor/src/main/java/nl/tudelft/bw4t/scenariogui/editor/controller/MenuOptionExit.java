package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MenuBar;

/**
 * Handles the event to exit the program.
 * 
 *
 * @version 0.1
 * @since 12-05-2014
 */
class MenuOptionExit extends AbstractMenuOption {

    /**
     * Constructs a new menu option exit object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     * @param model    The model.
     */
    public MenuOptionExit(final MenuBar view,
                          final ScenarioEditorController mainView, BW4TClientConfig model) {
        super(view, mainView, model);
    }

    /**
     * Gets called when the exit button is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        ConfigurationPanel configPanel = super.getController().getMainView()
                .getMainPanel().getConfigurationPanel();

        // Check if current config is different from last saved config
        if (getController().hasConfigBeenModified()) {
            // Check if user wants to save current configuration
            boolean doSave = getController().promptUserToSave();

            if (doSave) {
                saveFile();
                getController().getMainView().closeScenarioEditor();
            } else {
                getController().getMainView().closeScenarioEditor();
            }
        } else {
            getController().getMainView().closeScenarioEditor();
        }
    }
}
