package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.scenariogui.gui.MenuBar;

/**
 * Handles the event to save at a chosen location.
 * <p>
 * @author        
 * @version     0.1                
 * @since       12-05-2014        
 */
class MenuOptionSaveAs extends AbstractMenuOption {

    /**
     * Constructs a new menu option save as object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionSaveAs(final MenuBar view, final ScenarioEditorController mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the menu item save as is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        saveFile(true);
        super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
    }
}
