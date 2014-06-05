package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;

/**
 * Handles the event to save a file.
 * <p>
 * @author      Nick Feddes 
 * @author      Xander Zonneveld 
 * @version     0.1                
 * @since       12-05-2014        
 */
class MenuOptionSave extends AbstractMenuOption {

    /**
     * Constructs a new menu option save object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     * @param model    The model.
     */
    public MenuOptionSave(final MenuBar view, final ScenarioEditorController mainView, BW4TClientConfig model) {
        super(view, mainView, model);
    }

    /**
     * Gets called when the save menu option gets called.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        saveFile();
        super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
        super.getModel().updateBotConfigs();
        super.getController().getMainView().getMainPanel().getEntityPanel().updateEPartnerCount(
        		super.getModel().getAmountEPartner());
        super.getModel().updateEpartnerConfigs();
    }
}
