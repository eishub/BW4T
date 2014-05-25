package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.config.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.config.BotConfig;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;

/**
 * Handles the event to open a file.
  * <p>
 * @author        
 * @version     0.1                
 * @since       12-05-2014        
 */
class MenuOptionOpen extends AbstractMenuOption {

    /**
     * Constructs a new menu option open object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionOpen(final MenuBar view, final ScenarioEditorController mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the menu option open button is pressed.
     *
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        ConfigurationPanel configPanel = super.getController().getMainView().getMainPanel().getConfigurationPanel();
        EntityPanel entityPanel = super.getController().getMainView().getMainPanel().getEntityPanel();


        // Check if current config is different from last saved config
        if (!configPanel.getOldValues().equals(configPanel.getCurrentValues())) {
            // Check if user wants to save current configuration
            int response = ScenarioEditor.getOptionPrompt().showConfirmDialog(
                    null,
                    ScenarioEditorController.CONFIRM_SAVE_TXT,
                    "",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                saveFile();
                super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
            }
        }

        // Open configuration file
        JFileChooser fileChooser = getCurrentFileChooser();
        fileChooser.setFileFilter(FileFilters.xmlFilter());

        if (fileChooser.showOpenDialog(getController().getMainView()) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String openedFile = fileChooser.getSelectedFile().toString();

            try {
                BW4TClientConfig configuration= BW4TClientConfig.fromXML(file.getAbsolutePath());

                // Fill the configuration panel
                configPanel.setClientIP(configuration.getClientIp());
                configPanel.setClientPort("" + configuration.getClientPort());
                configPanel.setServerIP(configuration.getServerIp());
                configPanel.setServerPort("" + configuration.getServerPort());
                configPanel.setUseGui(configuration.isLaunchGui());
//                configPanel.setUseGoal(temp.isUseGoal());
                configPanel.setMapFile(configuration.getMapFile());

                // Fill the bot panel

                for(BotConfig bot : configuration.getBots()) {
                    // TODO: LOAD AND FILL BOT LIST HERE
                }
            } catch (JAXBException e1) {
                ScenarioEditor.handleException(e1, "Error: Opening the XML has failed.");
            } catch (FileNotFoundException e1) {
                ScenarioEditor.handleException(e1, "Error: No file has been found. ");
            }

            //set last file location to the opened file so that the previous saved file won't get
            //overwritten when the new config is saved.
            super.getMenuView().setLastFileLocation(openedFile);
        }
        super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
    }
}
