package nl.tudelft.bw4t.scenariogui.controllers.menubar;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.controllers.Controller;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;

/**
 * Handles the event to exit the program.
 */
public class MenuOptionExit extends AbstractMenuOption {

    /**
     * Constructs a new menu option exit object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionExit(final MenuBar view, final Controller mainView) {
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
            int response = JOptionPane.showConfirmDialog(
                    null,
                    Controller.CONFIRM_SAVE_TXT,
                    "",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                saveFile();
                super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
            }
        }

        System.exit(0);
    }
}
