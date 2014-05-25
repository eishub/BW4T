package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;

/**
 * Handles the event to start a new file.
 * <p>
 * @author        
 * @version     0.1                
 * @since       12-05-2014        
 */
class MenuOptionNew extends AbstractMenuOption {

    /**
     * Constructs a new menu option new object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionNew(final MenuBar view, final ScenarioEditorController mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the new file menu item is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        ConfigurationPanel configPanel = super.getController().getMainView().getMainPanel().getConfigurationPanel();

        // Check if current config is different from last saved config
        if (!configPanel.getOldValues().equals(configPanel.getCurrentValues())) {
            // Check if user wants to save current configuration
            int response = ScenarioEditor.getOptionPrompt().showConfirmDialog(
                    null,
                    ScenarioEditorController.CONFIRM_SAVE_TXT,
                    "",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                saveFile();
                super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
            }
        }

        // Reset the config panel
        configPanel.setClientIP(ConfigurationPanel.DEFAULT_VALUES.DEFAULT_CLIENT_IP.getValue());
        configPanel.setClientPort(ConfigurationPanel.DEFAULT_VALUES.DEFAULT_CLIENT_PORT.getValue());
        configPanel.setServerIP(ConfigurationPanel.DEFAULT_VALUES.DEFAULT_SERVER_IP.getValue());
        configPanel.setServerPort(ConfigurationPanel.DEFAULT_VALUES.DEFAULT_SERVER_PORT.getValue());
        configPanel.setUseGui(ConfigurationPanel.DEFAULT_VALUES.USE_GUI.getBooleanValue());
//        configPanel.setUseGoal(ConfigurationPanel.DEFAULT_VALUES.USE_GOAL.getBooleanValue());
        configPanel.setMapFile(ConfigurationPanel.DEFAULT_VALUES.MAP_FILE.getValue());

        //save the default values as the "old" values
        super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();

        //set last file location to null so that the previous saved file won't get
        //overwritten when the new config is saved.
        super.getMenuView().setLastFileLocation(null);

        // Reset the bot panel
        //TODO reset botPanel
    }
}
