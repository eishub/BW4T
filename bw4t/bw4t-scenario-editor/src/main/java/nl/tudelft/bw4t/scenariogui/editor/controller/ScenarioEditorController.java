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
 * <p>
 * @version     0.1                
 * @since       12-05-2014        
 */
public class ScenarioEditorController {

    public static final String CONFIRM_SAVE_TXT = "Do you want to save the current configuration?";

    private ScenarioEditor view;
    
    private BW4TClientConfig model;
    
    private MainPanel mp;

    /**
     * Create a controllers object to control all ActionEvents.
     *
     * @param newView used to call relevant functions by the event listeners.
     * @param model The model used.
     */
    public ScenarioEditorController(final ScenarioEditor newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;
        mp = newView.getMainPanel();

        addConfigurationPanelListeners();
        addMenuBarListeners();
        addEntityPanelListeners();

        getMainView().addWindowListener(new WindowExit(getMainView()));
    }
    
    private void addConfigurationPanelListeners() {
    	ConfigurationPanel configurationPanel = mp.getConfigurationPanel();
    	
    	configurationPanel.addClientIPController(new WriteClientIP(mp));
    	configurationPanel.addClientPortController(new WriteClientPort(mp));
        configurationPanel.addServerIPController(new WriteServerIP(mp));
        configurationPanel.addServerPortController(new WriteServerPort(mp));
        configurationPanel.addGUIYesCheckboxController(new SelectLaunchGUIYes(mp));
        configurationPanel.addGUINoCheckboxController(new SelectLaunchGUINo(mp));
        configurationPanel.addMapFileController(new WriteMapFile(mp));
		configurationPanel.addMapFileButtonController(new ChooseMapFileListener(mp));
    }
    
    private void addMenuBarListeners() {
    	MenuBar menuBar = view.getTopMenuBar();
    	
		menuBar.addExitController(new MenuOptionExit(getMainView().getTopMenuBar(), this, getModel()));
    	menuBar.addNewController(new MenuOptionNew(getMainView().getTopMenuBar(), this, getModel()));
    	menuBar.addOpenController(new MenuOptionOpen(getMainView().getTopMenuBar(), this, getModel()));
    	menuBar.addSaveController(new MenuOptionSave(getMainView().getTopMenuBar(), this, getModel()));
    	menuBar.addSaveAsController(new MenuOptionSaveAs(getMainView().getTopMenuBar(), this, getModel()));
    	menuBar.addExportController(new MenuOptionExport(getMainView().getTopMenuBar(), this, getModel()));
    }
    
    private void addEntityPanelListeners() {
    	EntityPanel entityPanel = mp.getEntityPanel();
    	
		entityPanel.addNewBotController(new AddNewBot(mp, getModel()));
		entityPanel.addModifyBotController(new ModifyBot(mp, getModel()));
		entityPanel.addDeleteBotController(new DeleteBot(mp, getModel()));		
		entityPanel.addNewEpartnerController(new AddNewEPartner(mp, getModel()));
		entityPanel.addModifyEpartnerController(new ModifyEPartner(mp, getModel()));
		entityPanel.addDeleteEpartnerController(new DeleteEPartner(mp, getModel()));
		entityPanel.addDropDownController(new BotDropDownButton(mp));
		entityPanel.addBotTableModelController(new UpdateBotCount(mp, getModel()));
        entityPanel.addEpartnerTableModelController(new UpdateEPartnerCount(mp, getModel()));
        entityPanel.addBotTableController(new EditBotTable(getMainView().getMainPanel(), getModel()));
        entityPanel.addEpartnerTableController(new EditEPartnerTable(getMainView().getMainPanel(), getModel()));
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
        boolean configurationEqual = getMainView().getMainPanel().getConfigurationPanel().getOldValues()
                .equals(getMainView().getMainPanel().getConfigurationPanel().getCurrentValues());
        boolean botsEqual = getModel().compareBotConfigs(getModel().getOldBots());
        boolean epartnersEqual = getModel().compareEpartnerConfigs(getModel().getOldEpartners());

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


