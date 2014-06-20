package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;

/**
 * Handles the event to open a file.
 * 
 *
 * @version 0.1
 * @since 12-05-2014
 */
class MenuOptionOpen extends AbstractMenuOption {

    /**
     * Constructs a new menu option open object.
     *
     * @param view
     *            The view.
     * @param mainView
     *            The controlling main view.
     * @param model
     *            The model.
     */
    public MenuOptionOpen(final MenuBar view,
                          final ScenarioEditorController mainView, BW4TClientConfig model) {
        super(view, mainView, model);
    }

    /**
     * Gets called when the menu option open button is pressed.
     *
     * @param e
     *            The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        ScenarioEditorController controller = getController();
        MainPanel mainPanel = controller.getMainView().getMainPanel();
        ConfigurationPanel configPanel = mainPanel.getConfigurationPanel();
        EntityPanel entityPanel = mainPanel.getEntityPanel();

        // Check if current config is different from last saved config
        if (controller.hasConfigBeenModified()) {
            if (controller.promptUserToSave()) {
                saveFile();
                updateOldConfig();
            }
        }
        openFile(configPanel, entityPanel);
        updateOldConfig();
    }

    /**
     * Updates the old config values.
     */
    private void updateOldConfig() {
        ScenarioEditor mainView = getController().getMainView();
        MainPanel mainPanel = mainView.getMainPanel();
        ConfigurationPanel configurationPanel = mainPanel.getConfigurationPanel();
        configurationPanel.updateOldValues();
        BW4TClientConfig model = getController().getModel();
        model.updateOldBotConfigs();
        model.updateOldEpartnerConfigs();
    }

    /**
     * Shows an open dialogue and proceeds with loading the file chosen
     * into the GUI if the user confirmed to load in their chosen file.
     * @param configPanel The configuration panel of the GUI.
     * @param entityPanel The entity panel of the GUI.
     */
    private void openFile(ConfigurationPanel configPanel,
                          EntityPanel entityPanel) {
        // Open configuration file
        JFileChooser fileChooser = getCurrentFileChooser();
        fileChooser.setFileFilter(FileFilters.xmlFilter());

        ScenarioEditor mainView = getController().getMainView();
        if (fileChooser.showOpenDialog(mainView) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String openedFile = fileChooser.getSelectedFile().toString();

            parseXMLFileIntoGUI(configPanel, entityPanel, file);

            // set last file location to the opened file so that the previous
            // saved file won't get
            // overwritten when the new config is saved.
            getMenuView().setLastFileLocation(openedFile);
            mainView.setWindowTitle(file.getName());
        }
    }

    /**
     * Loads an XML file into the GUI.
     * @param configPanel The configuration panel of the GUI.
     * @param entityPanel The entity panel of the GUI.
     * @param file The XML file to load into the GUI.
     */
    private void parseXMLFileIntoGUI(ConfigurationPanel configPanel,
                                     EntityPanel entityPanel, File file) {
        try {
            BW4TClientConfig configuration = BW4TClientConfig.fromXML(file
                    .getAbsolutePath());

            updateConfigurationInModel(configuration);

            // Fill the configuration panel from the panel
            reloadConfiguration(configPanel);

            // clear bots/epartners from the previous config
            resetBotTable(entityPanel);
            resetEpartnerTable(entityPanel);

            getModel().clearBotsAndEpartners();


            fillPanelWithBots(entityPanel, configuration);
            fillPanelWithEPartners(entityPanel, configuration);
        } catch (JAXBException e1) {
            ScenarioEditor.handleException(e1,
                    "Error: Opening the XML has failed.");
        } catch (FileNotFoundException e1) {
            ScenarioEditor.handleException(e1,
                    "Error: No file has been found. ");
        }
    }

    /**
     * Fills the bot panel with bots that are in the configuration
     * to load in.
     * @param entityPanel The entity panel.
     * @param configuration The configuration to load in.
     */
    private void fillPanelWithBots(EntityPanel entityPanel,
                                   BW4TClientConfig configuration) {
        // Fill the bot panel
        int botRows = configuration.getBots().size();

        for (int i = 0; i < botRows; i++) {
            String botName = configuration.getBot(i).getBotName();
            EntityType botController = configuration.getBot(i)
                    .getBotController();
            String agentFileName = configuration.getBot(i).getFileName();
            String botAmount = Integer.toString(configuration.getBot(i)
                    .getBotAmount());
            entityPanel.getBotTableModel().update();
            getModel().getBots().add(configuration.getBot(i));
        }
    }

    /**
     * Fills the entity panel with e-partners that are in the configuration
     * to load in.
     * @param entityPanel The entity panel.
     * @param configuration The configuration to load in.
     */
    private void fillPanelWithEPartners(EntityPanel entityPanel,
                                        BW4TClientConfig configuration) {
        // Fill the epartner panel
        int epartnerRows = configuration.getEpartners().size();

        for (int i = 0; i < epartnerRows; i++) {
            String epartnerName = configuration.getEpartner(i).getEpartnerName();
            String epartnerAmount = Integer.toString(configuration.getEpartner(i)
                    .getEpartnerAmount());
            String epartnerFileName = configuration.getEpartner(i).getFileName();
            Object[] epartnerObject = {epartnerName, epartnerFileName, epartnerAmount };
            entityPanel.getEPartnerTableModel().addRow(epartnerObject);
            getModel().getEpartners().add(configuration.getEpartner(i));
        }
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

        if (rows > 0) {
            for (int i = rows - 1; i >= 0; i--) {
                epartnerTable.removeRow(i);
            }
        }
    }

    private void updateConfigurationInModel(BW4TClientConfig loadedModel) {
        getModel().setClientIp(loadedModel.getClientIp());
        getModel().setClientPort(loadedModel.getClientPort());
        getModel().setServerIp(loadedModel.getServerIp());
        getModel().setServerPort(loadedModel.getServerPort());
        getModel().setLaunchGui(loadedModel.isLaunchGui());
        getModel().setVisualizePaths(loadedModel.isVisualizePaths());
        getModel().setCollisionEnabled(loadedModel.isCollisionEnabled());
        getModel().setMapFile(loadedModel.getMapFile());
    }

    private void reloadConfiguration(ConfigurationPanel configPanel) {
        configPanel.setClientIP(getModel().getClientIp());
        configPanel.setClientPort(getModel().getClientPort() + "");
        configPanel.setServerIP(getModel().getServerIp());
        configPanel.setServerPort(getModel().getServerPort() + "");
        configPanel.setUseGui(getModel().isLaunchGui());
        configPanel.setVisualizePaths(getModel().isVisualizePaths());
        configPanel.setEnableCollisions(getModel().isCollisionEnabled());
        configPanel.setMapFile(getModel().getMapFile());
    }
}
