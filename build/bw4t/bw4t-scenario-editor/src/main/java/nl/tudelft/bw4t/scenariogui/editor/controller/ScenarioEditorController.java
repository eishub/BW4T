package nl.tudelft.bw4t.scenariogui.editor.controller;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MenuBar;

/**
 * The Controller class is in charge of all events that happen on the GUI.
 * It delegates all events to classes implementing ActionListener,
 * sending the view along as an argument.
 *
 * 
 * @version     0.1                
 * @since       12-05-2014        
 */
public class ScenarioEditorController {

    public static final String CONFIRM_SAVE_TXT = "Do you want to save the current configuration?";

    private ScenarioEditor view;
    
    private BW4TClientConfig model;
    
    private MainPanel mainPanel;
    
    private ConfigurationPanel configurationPanel;

    /**
     * Create a controllers object to control all ActionEvents.
     *
     * @param newView used to call relevant functions by the event listeners.
     * @param model The model used.
     */
    public ScenarioEditorController(final ScenarioEditor newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;
        mainPanel = newView.getMainPanel();
        configurationPanel = mainPanel.getConfigurationPanel();

        addConfigurationPanelListeners();
        addMenuBarListeners();
        addEntityPanelListeners();

        getMainView().addWindowListener(new WindowExit(getMainView()));
    }
    
    private void addConfigurationPanelListeners() {
        configurationPanel.addClientIPController(new WriteClientIP(mainPanel));
        configurationPanel.addClientPortController(new WriteClientPort(mainPanel));
        configurationPanel.addServerIPController(new WriteServerIP(mainPanel));
        configurationPanel.addServerPortController(new WriteServerPort(mainPanel));
        configurationPanel.addGUIYesCheckboxController(new SelectLaunchGUIYes(mainPanel));
        configurationPanel.addGUINoCheckboxController(new SelectLaunchGUINo(mainPanel));
        configurationPanel.addPathsYesCheckboxController(new SelectVisualizePathsYes(mainPanel));
        configurationPanel.addPathsNoCheckboxController(new SelectVisualizePathsNo(mainPanel));
        configurationPanel.addCollisionsYesCheckboxController(new SelectEnableCollisionsYes(mainPanel));
        configurationPanel.addCollisionsNoCheckboxController(new SelectEnableCollisionsNo(mainPanel));
        configurationPanel.addMapFileController(new WriteMapFile(mainPanel));
        configurationPanel.addMapFileButtonController(new ChooseMapFileListener(mainPanel));
    }
    
    private void addMenuBarListeners() {
        MenuBar menuBar = view.getTopMenuBar();
        
        menuBar.addExitController(new MenuOptionExit(menuBar, this, getModel()));
        menuBar.addNewController(new MenuOptionNew(menuBar, this, getModel()));
        menuBar.addOpenController(new MenuOptionOpen(menuBar, this, getModel()));
        menuBar.addSaveController(new MenuOptionSave(menuBar, this, getModel()));
        menuBar.addSaveAsController(new MenuOptionSaveAs(menuBar, this, getModel()));
        menuBar.addExportController(new MenuOptionExport(menuBar, this, getModel()));
    }
    
    private void addEntityPanelListeners() {
        EntityPanel entityPanel = mainPanel.getEntityPanel();
        
        entityPanel.addNewBotController(new AddNewBot(mainPanel, getModel()));
        entityPanel.addModifyBotController(new ModifyBot(mainPanel, getModel()));
        entityPanel.addDeleteBotController(new DeleteBot(mainPanel, getModel()));        
        entityPanel.addNewEpartnerController(new AddNewEPartner(mainPanel, getModel()));
        entityPanel.addModifyEpartnerController(new ModifyEPartner(mainPanel, getModel()));
        entityPanel.addDeleteEpartnerController(new DeleteEPartner(mainPanel, getModel()));
        entityPanel.addDropDownController(new BotDropDownButton(mainPanel));

        addStandardBotDropDownListeners();
        
        entityPanel.addBotTableModelController(new UpdateBotCount(mainPanel, getModel()));
        entityPanel.addEpartnerTableModelController(new UpdateEPartnerCount(mainPanel, getModel()));
        entityPanel.addBotTableController(new EditBotTable(getMainView().getMainPanel(), getModel()));
        entityPanel.addEpartnerTableController(new EditEPartnerTable(getMainView().getMainPanel(), getModel()));
    }
    
    /**
     * Adds the listeners for the default bot dropdown menu.
     */
    private void addStandardBotDropDownListeners() {
        EntityPanel entityPanel = mainPanel.getEntityPanel();
        
        entityPanel.addNewStandardBotController(new AddNewBot(mainPanel, getModel()));
        entityPanel.addNewStandardBotBigController(new AddNewStandardBotBig(mainPanel, getModel()));
        entityPanel.addNewStandardBotGripperController(new AddNewStandardBotGripper(mainPanel, getModel()));
        entityPanel.addNewStandardBotBigGripperController(new AddNewStandardBotBigGripper(mainPanel, getModel()));
        entityPanel.addNewStandardBotSeeerController(new AddNewStandardBotSeeer(mainPanel, getModel()));
        entityPanel.addNewStandardBotBigSeeerController(new AddNewStandardBotBigSeeer(mainPanel, getModel()));
        entityPanel.addNewStandardBotCommunicatorController(new AddNewStandardBotCommunicator(mainPanel, getModel()));
        entityPanel.addNewStandardBotBigCommunicatorController(new AddNewStandardBotBigCommunicator(mainPanel, getModel()));        
    }

    /**
     * Return the view being controlled.
     *
     * @return The JFrame being controlled.
     */
    public final ScenarioEditor getMainView() {
        return view;
    }
    
    /**
     * Return the BW4TClientConfig model.
     * 
     * @return The BW4TClientConfig model.
     */
    public final BW4TClientConfig getModel() {
        return model;
    }

    /**
     * Checks if the configuration has been changed.
     * @return returns true if either the configuration, the bot list or the epartners list has been changed.
     */
    public boolean hasConfigBeenModified() {        
        boolean configurationEqual = configurationPanel.getOldValues().equals(configurationPanel.getCurrentValues());
        boolean botsEqual = model.compareBotConfigs(model.getOldBots());
        boolean epartnersEqual = model.compareEpartnerConfigs(model.getOldEpartners());

        return !(configurationEqual && botsEqual && epartnersEqual);
    }

    /**
     * Ask the user if (s)he wishes to save the scenario.
     * @return True if the user wishes to save the scenario.
     */
    public boolean promptUserToSave() {
        // Check if user wants to save current configuration
        int response = ScenarioEditor.getOptionPrompt().showConfirmDialog(
                null, ScenarioEditorController.CONFIRM_SAVE_TXT, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return response == JOptionPane.YES_OPTION;
    }
}


