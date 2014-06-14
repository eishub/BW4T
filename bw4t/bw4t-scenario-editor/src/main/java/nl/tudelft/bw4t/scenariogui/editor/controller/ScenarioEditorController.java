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
    
    private ConfigurationPanel configurationPanel;
    
    private MainPanel mainPanel;
    
    private EntityPanel entityPanel;

    /**
     * Create a controllers object to control all ActionEvents.
     *
     * @param newView used to call relevant functions by the event listeners.
     * @param model The model used.
     */
    public ScenarioEditorController(final ScenarioEditor newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;
        this.mainPanel = view.getMainPanel();
        this.configurationPanel = mainPanel.getConfigurationPanel();
        this.entityPanel = mainPanel.getEntityPanel();
        
        addListeners();    
    }

    /**
     * Adds all listeners the Scenario Editor requires.
     */
    private void addListeners() {
        addChoostMapFileListeners();
        addServerSettingListeners();
        addMenuBarListeners();
        addEntityPanelListeners();
        addWindowListener();
        addTableModelListeners();
        addTableListeners();   
    }

    /**
     * Adds the listeners for the table model.
     */
    private void addTableModelListeners() {
        entityPanel.getBotTableModel().addTableModelListener(new UpdateBotCount(mainPanel, model));
        entityPanel.getEPartnerTableModel().addTableModelListener(new UpdateEPartnerCount(mainPanel, model));        
    }

    /** 
     * Adds the listener for the bot and e-partner table: 
     */ 
    private void addTableListeners() {
        entityPanel.getBotTable().getModel().addTableModelListener(new EditBotTable(mainPanel, model));
        entityPanel.getEPartnerTable().getModel().addTableModelListener(new EditEPartnerTable(mainPanel, model));
    }

    /**
     * Adds the listener for the Scenario Editor.
     */
    private void addWindowListener() {
        view.addWindowListener(new WindowExit(view));
    }

    /**
     * Adds the listeners for the Entity Panel.
     */
    private void addEntityPanelListeners() {
        entityPanel.getNewBotButton().addActionListener(new AddNewBot(mainPanel, model));
        entityPanel.getModifyBotButton().addActionListener(new ModifyBot(mainPanel, model));
        entityPanel.getDeleteBotButton().addActionListener(new DeleteBot(mainPanel, model));
        entityPanel.getDropDownButton().addActionListener(new BotDropDownButton(mainPanel));
        
        entityPanel.getNewEPartnerButton().addActionListener(new AddNewEPartner(mainPanel, model));
        entityPanel.getModifyEPartnerButton().addActionListener(new ModifyEPartner(mainPanel, model));
        entityPanel.getDeleteEPartnerButton().addActionListener(new DeleteEPartner(mainPanel, model));        
    }

    /**
     * Add the listeners for the menu bar.
     */
    private void addMenuBarListeners() {
        MenuBar topMenuBar = view.getTopMenuBar();
        
        topMenuBar.getMenuItemFileExit().addActionListener(new MenuOptionExit(topMenuBar, this, model));
        topMenuBar.getMenuItemFileNew().addActionListener(new MenuOptionNew(topMenuBar, this, model));
        topMenuBar.getMenuItemFileOpen().addActionListener(new MenuOptionOpen(topMenuBar, this, model));
        topMenuBar.getMenuItemFileSave().addActionListener(new MenuOptionSave(topMenuBar, this, model));
        topMenuBar.getMenuItemFileSaveAs().addActionListener(new MenuOptionSaveAs(topMenuBar, this, model));
        topMenuBar.getMenuItemFileExport().addActionListener(new MenuOptionExport(topMenuBar, this, model));    
        
    }

    /**
     * Adds the listeners for the server settings.
     */
    private void addServerSettingListeners() {
        configurationPanel.getClientIPTextField().getDocument().addDocumentListener(new WriteClientIP(mainPanel));
        configurationPanel.getClientPortTextField().getDocument().addDocumentListener(new WriteClientPort(mainPanel));
        configurationPanel.getServerIPTextField().getDocument().addDocumentListener(new WriteServerIP(mainPanel));
        configurationPanel.getServerPortTextField().getDocument().addDocumentListener(new WriteServerPort(mainPanel));
        configurationPanel.getMapFileTextField().getDocument().addDocumentListener(new WriteMapFile(mainPanel));
        configurationPanel.getGUIYesCheckbox().addItemListener(new SelectLaunchGUIYes(mainPanel));
        configurationPanel.getGUINoCheckbox().addItemListener(new SelectLaunchGUINo(mainPanel));
    }
    
    /**
     * Adds the listener for the map file chooser.
     */
    private void addChoostMapFileListeners() {
        configurationPanel.getChooseMapFile().addActionListener(new ChooseMapFileListener(mainPanel));        
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


