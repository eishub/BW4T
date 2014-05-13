package nl.tudelft.bw4t.controller;

import nl.tudelft.bw4t.gui.panel.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * The Controller class is in charge of all events that happen on the GUI. It delegates all events
 * to classes implementing ActionListener, sending the view along as an argument.
 * @author Calvin
 */
public class Controller {

    /** The view being controlled */
    private MainPanel view;

    /**
     * Create a controller object.
     * @param view The parent view, used to call relevant functions by the event listeners.
     */
    public Controller(MainPanel view) {
        this.view = view;

        /** Create the action listeners for the ConfigurationPanel. */

        /** Listener for the agent file choser */
        getMainView().getConfigurationPanel().getChooseAgentFile().addActionListener(
                new ChooseAgentFileListener(getMainView())
        );

        getMainView().getConfigurationPanel().getChooseMapFile().addActionListener(
                new ChooseMapFileListener(getMainView())
        );
    }

    /**
     * Return the view being controlled.
     * @return The MainPanel being controlled.
     */
    public MainPanel getMainView() {
        return view;
    }
}

/**
 * Handles the event to choose an agent file.
 */
class ChooseAgentFileListener implements ActionListener {

    private MainPanel view;

    /**
     * Create a ChooseAgentFileListener event handler.
     * @param view The parent view.
     */
    public ChooseAgentFileListener(MainPanel view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /** Create a file chooser, opening at the last path location saved in the configuration panel */
        JFileChooser fc = view.getConfigurationPanel().getFileChooser();


        int returnVal = fc.showOpenDialog(view);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            view.getConfigurationPanel().setAgentClassFile(file.getPath());
        }
    }
}

/**
 * Handles the event to choose a map file.
 */
class ChooseMapFileListener implements ActionListener {

    private MainPanel view;

    /**
     * Create a ChooseMapFileListener event handler.
     * @param view The parent view.
     */
    public ChooseMapFileListener(MainPanel view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /** Create a file chooser, opening at the last path location saved in the configuration panel */
        JFileChooser fc = view.getConfigurationPanel().getFileChooser();

        int returnVal = fc.showOpenDialog(view);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            view.getConfigurationPanel().setMapFile(file.getPath());
        }
    }
}
