package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.config.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;

/**
 * The Controller class is in charge of all events that happen on the GUI.
 * It delegates all events to classes implementing ActionListener,
 * sending the view along as an argument.
 *
 * @author Calvin
 */
public class Controller {

    /** The view being controlled. */
    private ScenarioEditor view;
    
    /** The text shown on the dialog when asked whether or not to save
     * the current configuration. */
    public static final String CONFIRM_SAVE_TXT = "Do you want to save the current configuration?";

    /**
     * Create a controller object to control all ActionEvents.
     * @param newView used to call relevant functions by the event listeners.
     */
    public Controller(final ScenarioEditor newView) {
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
     * @return The JFrame being controlled.
     */
    public final ScenarioEditor getMainView() {
        return view;
    }
}


