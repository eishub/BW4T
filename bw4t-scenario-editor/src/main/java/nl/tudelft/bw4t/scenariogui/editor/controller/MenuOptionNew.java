package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.DefaultConfigurationValues;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MenuBar;

/**
 * Handles the event to start a new file.
 * 
 *
 * @version 0.1
 * @since 12-05-2014
 */
class MenuOptionNew extends AbstractMenuOption {

    /**
     * Constructs a new menu option new object.
     *
     * @param view
     *            The view.
     * @param mainView
     *            The controlling main view.
     * @param model
     *            The model.
     */
    public MenuOptionNew(final MenuBar view,
            final ScenarioEditorController mainView, BW4TClientConfig model) {
        super(view, mainView, model);
    }

    /**
     * Gets called when the new file menu item is pressed.
     *
     * @param e
     *            The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        ScenarioEditor mainView = getController().getMainView();
        MainPanel mainPanel = mainView.getMainPanel();
        ConfigurationPanel configPanel = mainPanel.getConfigurationPanel();
        EntityPanel entityPanel = mainPanel.getEntityPanel();

        // Check if current config is different from last saved config
        if (getController().hasConfigBeenModified()) {
            showSaveDialogOption();
        }

        resetConfigPanel(configPanel);
        getModel().clearBotsAndEpartners();

        // set last file location to null so that the previous saved file won't
        // get
        // overwritten when the new config is saved.
        getMenuView().setLastFileLocation(null);

        // Reset the bot panel
        resetBotTable(entityPanel);

        // reset the epartner panel
        resetEpartnerTable(entityPanel);

        mainView.setWindowTitle("Untitled");
    }

    /**
     * Asks the user if they want to save and proceeds to do so
     * if the user selects yes.
     */
    private void showSaveDialogOption() {
        // Check if user wants to save current configuration
        int response = ScenarioEditor.getOptionPrompt().showConfirmDialog(
                null, ScenarioEditorController.CONFIRM_SAVE_TXT, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            saveFile();
            ScenarioEditor mainView = getController().getMainView();
            MainPanel mainPanel = mainView.getMainPanel();
            ConfigurationPanel configurationPanel = mainPanel.getConfigurationPanel();
            configurationPanel.updateOldValues();
            getModel().updateOldBotConfigs();
        }
    }

    /**
     * Resets the given configPanel to it's default values.
     *
     * @param configPanel
     *            The configPanel to be reset.
     */
    public void resetConfigPanel(ConfigurationPanel configPanel) {
        // Reset the config panel
        configPanel
                .setClientIP(DefaultConfigurationValues.DEFAULT_CLIENT_IP
                        .getValue());
        configPanel
                .setClientPort(DefaultConfigurationValues.DEFAULT_CLIENT_PORT
                        .getValue());
        configPanel
                .setServerIP(DefaultConfigurationValues.DEFAULT_SERVER_IP
                        .getValue());
        configPanel
                .setServerPort(DefaultConfigurationValues.DEFAULT_SERVER_PORT
                        .getValue());
        configPanel.setUseGui(DefaultConfigurationValues.USE_GUI
                .getBooleanValue());
        configPanel.setVisualizePaths(DefaultConfigurationValues.VISUALIZE_PATHS
                .getBooleanValue());
        configPanel.setEnableCollisions(DefaultConfigurationValues.ENABLE_COLLISIONS
                .getBooleanValue());
        configPanel.setMapFile(DefaultConfigurationValues.MAP_FILE
                .getValue());

    }

    /**
     * Reset the list with bots.
     *
     * @param entityPanel
     *            The EntityPanel which contains the bot list.
     */
    public void resetBotTable(EntityPanel entityPanel) {
        entityPanel.getBotTableModel().update();
    }

    /**
     * Reset the list with epartners.
     *
     * @param entityPanel
     *            The EntityPanel which contains the epartner list.
     */
    public void resetEpartnerTable(EntityPanel entityPanel) {
        DefaultTableModel epartnerTable = entityPanel.getEPartnerTableModel();
        int rows = epartnerTable.getRowCount();

        for (int i = rows - 1; i >= 0; i--) {
            epartnerTable.removeRow(i);
        }
    }
}
