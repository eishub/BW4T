package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.scenariogui.gui.MenuBar;

/**
 * Handles the event to save a file.
 * <p>
 * @author      Nick Feddes  
 * @version     0.1                
 * @since       12-05-2014        
 */
class MenuOptionSave extends AbstractMenuOption {

    /**
     * Constructs a new menu option save object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionSave(final MenuBar view, final ScenarioEditorController mainView) {
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
        super.getController().getMainView().getMainPanel().getEntityPanel().updateBotConfigs();
    }
}
