package nl.tudelft.bw4t.scenariogui.controllers;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.controllers.configurationpanel.ChooseMapFileListener;
import nl.tudelft.bw4t.scenariogui.controllers.entitypanel.AddNewBot;
import nl.tudelft.bw4t.scenariogui.controllers.entitypanel.AddNewEPartner;
import nl.tudelft.bw4t.scenariogui.controllers.entitypanel.BotDropDownButton;
import nl.tudelft.bw4t.scenariogui.controllers.entitypanel.DeleteBot;
import nl.tudelft.bw4t.scenariogui.controllers.entitypanel.DeleteEPartner;
import nl.tudelft.bw4t.scenariogui.controllers.entitypanel.ModifyBot;
import nl.tudelft.bw4t.scenariogui.controllers.entitypanel.ModifyEPartner;
import nl.tudelft.bw4t.scenariogui.controllers.menubar.MenuOptionExit;
import nl.tudelft.bw4t.scenariogui.controllers.menubar.MenuOptionNew;
import nl.tudelft.bw4t.scenariogui.controllers.menubar.MenuOptionOpen;
import nl.tudelft.bw4t.scenariogui.controllers.menubar.MenuOptionSave;
import nl.tudelft.bw4t.scenariogui.controllers.menubar.MenuOptionSaveAs;

/**
 * The Controller class is in charge of all events that happen on the GUI.
 * It delegates all events to classes implementing ActionListener,
 * sending the view along as an argument.
 *
 * @author Calvin
 */
public class ScenarioEditorController {

    /**
     * The text shown on the dialog when asked whether or not to save
     * the current configuration.
     */
    public static final String CONFIRM_SAVE_TXT = "Do you want to save the current configuration?";

    /**
     * The view being controlled.
     */
    private ScenarioEditor view;

    /**
     * Create a controllers object to control all ActionEvents.
     *
     * @param newView used to call relevant functions by the event listeners.
     */
    public ScenarioEditorController(final ScenarioEditor newView) {
        this.view = newView;

        /** Create the action listeners for the ConfigurationPanel. */

        /** Listener for the map file chooser */
        getMainView().getMainPanel().getConfigurationPanel().getChooseMapFile().addActionListener(
                new ChooseMapFileListener(getMainView().getMainPanel())
        );

        /** Adds the listeners for the items in the MenuBar: */
        getMainView().getTopMenuBar().getMenuItemFileExit().addActionListener(
                new MenuOptionExit(getMainView().getTopMenuBar(), this)
        );

        getMainView().getTopMenuBar().getMenuItemFileNew().addActionListener(
                new MenuOptionNew(getMainView().getTopMenuBar(), this)
        );
        getMainView().getTopMenuBar().getMenuItemFileOpen().addActionListener(
                new MenuOptionOpen(getMainView().getTopMenuBar(), this)
        );
        getMainView().getTopMenuBar().getMenuItemFileSave().addActionListener(
                new MenuOptionSave(getMainView().getTopMenuBar(), this)
        );
        getMainView().getTopMenuBar().getMenuItemFileSaveAs().addActionListener(
                new MenuOptionSaveAs(getMainView().getTopMenuBar(), this)
        );

        /** Adds the listeners for the EntityPanel */
        getMainView().getMainPanel().getEntityPanel().getNewBotButton().
                addActionListener(
                        new AddNewBot(getMainView().getMainPanel())
                );
        getMainView().getMainPanel().getEntityPanel().getModifyBotButton().
                addActionListener(
                        new ModifyBot(getMainView().getMainPanel())
                );
        getMainView().getMainPanel().getEntityPanel().getDeleteBotButton().
                addActionListener(
                        new DeleteBot(getMainView().getMainPanel())
                );

        getMainView().getMainPanel().getEntityPanel().getNewEPartnerButton().addActionListener(
                new AddNewEPartner(getMainView().getMainPanel())
        );
        getMainView().getMainPanel().getEntityPanel().getModifyEPartnerButton().addActionListener(
                new ModifyEPartner(getMainView().getMainPanel())
        );
        getMainView().getMainPanel().getEntityPanel().getDeleteEPartnerButton().addActionListener(
                new DeleteEPartner(getMainView().getMainPanel())
        );
        getMainView().getMainPanel().getEntityPanel().getDropDownButton().addActionListener(
                new BotDropDownButton(getMainView().getMainPanel())
        );
    }

    /**
     * Return the view being controlled.
     *
     * @return The JFrame being controlled.
     */
    public final ScenarioEditor getMainView() {
        return view;
    }
}


