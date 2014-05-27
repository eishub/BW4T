package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;

/**
 * Handles the event to exit the program.
 * <p>
 * @author        
 * @version     0.1                
 * @since       12-05-2014        
 */
class MenuOptionExit extends AbstractMenuOption {

    /**
     * Constructs a new menu option exit object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionExit(final MenuBar view, final ScenarioEditorController mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the exit button is pressed.
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

        getController().getMainView().closeScenarioEditor();
    }
}
