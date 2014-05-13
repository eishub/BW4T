package nl.tudelft.bw4t.controller;

import nl.tudelft.bw4t.gui.panel.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Calvin on 13/05/2014.
 */
public class Controller {

    private MainPanel view;

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

    public MainPanel getMainView() {
        return view;
    }
}

class ChooseAgentFileListener implements ActionListener {

    private MainPanel view;

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

class ChooseMapFileListener implements ActionListener {

    private MainPanel view;

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
