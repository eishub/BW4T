package nl.tudelft.bw4t.scenariogui.editor.controller;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

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

    /**
     * TODO: Split up into multiple methods
     * Create a controllers object to control all ActionEvents.
     *
     * @param newView used to call relevant functions by the event listeners.
     * @param model The model used.
     */
    public ScenarioEditorController(final ScenarioEditor newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;

        /** Create the action listeners for the ConfigurationPanel. */

        /** Listener for the map file chooser */
        getMainView().getMainPanel().getConfigurationPanel().getChooseMapFile().addActionListener(
                new ChooseMapFileListener(getMainView().getMainPanel())
        );
        
        /** Adds the listeners for the items on the configuration panel: */
        getMainView().getMainPanel().getConfigurationPanel().getClientIPTextField().getDocument().addDocumentListener(
                new WriteClientIP(getMainView().getMainPanel())
        );
        getMainView().getMainPanel().getConfigurationPanel().getClientPortTextField().getDocument().addDocumentListener(
                new WriteClientPort(getMainView().getMainPanel())
        );
        getMainView().getMainPanel().getConfigurationPanel().getServerIPTextField().getDocument().addDocumentListener(
                new WriteServerIP(getMainView().getMainPanel())
        );
        getMainView().getMainPanel().getConfigurationPanel().getServerPortTextField().getDocument().addDocumentListener(
                new WriteServerPort(getMainView().getMainPanel())
        );
        getMainView().getMainPanel().getConfigurationPanel().getGUIYesCheckbox().addItemListener(
                new SelectLaunchGUIYes(getMainView().getMainPanel())
        );
        getMainView().getMainPanel().getConfigurationPanel().getGUINoCheckbox().addItemListener(
                new SelectLaunchGUINo(getMainView().getMainPanel())
        );
        getMainView().getMainPanel().getConfigurationPanel().getMapFileTextField().getDocument().addDocumentListener(
                new WriteMapFile(getMainView().getMainPanel())
        );

        /** Adds the listeners for the items in the MenuBar: */
        getMainView().getTopMenuBar().getMenuItemFileExit().addActionListener(
                new MenuOptionExit(getMainView().getTopMenuBar(), this, getModel())
        );

        getMainView().getTopMenuBar().getMenuItemFileNew().addActionListener(
                new MenuOptionNew(getMainView().getTopMenuBar(), this, getModel())
        );
        getMainView().getTopMenuBar().getMenuItemFileOpen().addActionListener(
                new MenuOptionOpen(getMainView().getTopMenuBar(), this, getModel())
        );
        getMainView().getTopMenuBar().getMenuItemFileSave().addActionListener(
                new MenuOptionSave(getMainView().getTopMenuBar(), this, getModel())
        );
        getMainView().getTopMenuBar().getMenuItemFileSaveAs().addActionListener(
                new MenuOptionSaveAs(getMainView().getTopMenuBar(), this, getModel())
        );
        getMainView().getTopMenuBar().getMenuItemFileExport().addActionListener(
                new MenuOptionExport(getMainView().getTopMenuBar(), this, getModel())
        );

        /** Adds the listeners for the EntityPanel */
        getMainView().getMainPanel().getEntityPanel().getNewBotButton().
                addActionListener(
                        new AddNewBot(getMainView().getMainPanel(), getModel())
                );
        getMainView().getMainPanel().getEntityPanel().getModifyBotButton().
                addActionListener(
                        new ModifyBot(getMainView().getMainPanel(), getModel())
                );
        getMainView().getMainPanel().getEntityPanel().getDeleteBotButton().
                addActionListener(
                        new DeleteBot(getMainView().getMainPanel(), getModel())
                );

        getMainView().getMainPanel().getEntityPanel().getNewEPartnerButton().addActionListener(
                new AddNewEPartner(getMainView().getMainPanel(), getModel())
        );
        getMainView().getMainPanel().getEntityPanel().getModifyEPartnerButton().addActionListener(
                new ModifyEPartner(getMainView().getMainPanel(), getModel())
        );
        getMainView().getMainPanel().getEntityPanel().getDeleteEPartnerButton().addActionListener(
                new DeleteEPartner(getMainView().getMainPanel(), getModel())
        );
        getMainView().getMainPanel().getEntityPanel().getDropDownButton().addActionListener(
                new BotDropDownButton(getMainView().getMainPanel())
        );
        
        getMainView().addWindowListener(
                new WindowExit(getMainView())
        );
        
        getMainView().getMainPanel().getEntityPanel().getBotTableModel().
        addTableModelListener(
                new UpdateBotCount(getMainView().getMainPanel(), getModel())
        );
        getMainView().getMainPanel().getEntityPanel().getEPartnerTableModel().
        addTableModelListener(
                new UpdateEPartnerCount(getMainView().getMainPanel(), getModel())
        );
        
        /** Adds the listener for the bot and e-partner table: */
        getMainView().getMainPanel().getEntityPanel().getBotTable().getModel().addTableModelListener(
                new EditBotTable(getMainView().getMainPanel(), getModel()));
        getMainView().getMainPanel().getEntityPanel().getEPartnerTable().getModel().addTableModelListener(
                new EditEPartnerTable(getMainView().getMainPanel(), getModel()));
        
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
     * @return returns true if either the configuration, the bot list of the epartners list has been changed.
     */
    public boolean hasConfigBeenModified() {
        boolean configurationEqual = getMainView().getMainPanel().getConfigurationPanel().getOldValues()
                .equals(getMainView().getMainPanel().getConfigurationPanel().getCurrentValues());
        boolean botsEqual = getModel().compareBotConfigs(getModel().getOldBots());
        boolean epartnersEqual= getModel().compareEpartnerConfigs(getModel().getOldEpartners());

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

    /**
     * Ask the user if (s)he wishes to quit the program.
     * @return True if the user wishes to quit.
     */
    public boolean promptUserToQuit() {
        int response = ScenarioEditor.getOptionPrompt().showConfirmDialog(
                null, "Are you sure you want to exit the program?", "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

       return response == JOptionPane.YES_OPTION;
    }

}


