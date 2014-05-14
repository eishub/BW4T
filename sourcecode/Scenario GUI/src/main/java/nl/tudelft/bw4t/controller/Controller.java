package nl.tudelft.bw4t.controller;

import nl.tudelft.bw4t.ScenarioEditor;
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
    private ScenarioEditor view;

    /**
     * Create a controller object.
     * @param view The parent view, used to call relevant functions by the event listeners.
     */
    public Controller(ScenarioEditor view) {
        this.view = view;

        /** Create the action listeners for the ConfigurationPanel. */

        /** Listener for the agent file chooser */
        getMainView().getMainPanel().getConfigurationPanel().getChooseAgentFile().addActionListener(
                new ChooseAgentFileListener(getMainView().getMainPanel())
        );

        getMainView().getMainPanel().getConfigurationPanel().getChooseMapFile().addActionListener(
                new ChooseMapFileListener(getMainView().getMainPanel())
        );
        
        /** Listeners for the bot option buttons. */
        getMainView().getMainPanel().getBotPanel().getNewBot().addActionListener(
        		new AddNewBot(getMainView().getMainPanel())
        );
        
        getMainView().getMainPanel().getBotPanel().getModifyBot().addActionListener(
        		new ModifyBot(getMainView().getMainPanel())
        );
        
        getMainView().getMainPanel().getBotPanel().getRenameBot().addActionListener(
        		new RenameBot(getMainView().getMainPanel())
        );
        
        getMainView().getMainPanel().getBotPanel().getDuplicateBot().addActionListener(
        		new DuplicateBot(getMainView().getMainPanel())
        );
        
        getMainView().getMainPanel().getBotPanel().getDeleteBot().addActionListener(
        		new DeleteBot(getMainView().getMainPanel())
        );
    }

    /**
     * Return the view being controlled.
     * @return The JFrame being controlled.
     */
    public ScenarioEditor getMainView() {
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

/**
 * Handles the event to create a new bot.
 */
class AddNewBot implements ActionListener {
	
	private MainPanel view;
	
	/**
	 * Create an AddNewBot event handler.
	 * @param view The parent view.
	 */
	public AddNewBot(MainPanel view){
		this.view = view;
	}

	public void actionPerformed(ActionEvent ae) {
		view.getBotPanel().addNewAction();
	}
	
}

/**
 * Handles the event to modify a bot.
 */
class ModifyBot implements ActionListener {
	
	private MainPanel view;
	
	/**
	 * Create an ModifyBot event handler.
	 * @param view The parent view.
	 */
	public ModifyBot(MainPanel view){
		this.view = view;
	}

	public void actionPerformed(ActionEvent ae) {
		view.getBotPanel().modifyAction();
	}
	
}

/**
 * Handles the event to rename a bot.
 */
class RenameBot implements ActionListener {
	
	private MainPanel view;
	
	/**
	 * Create an RenameBot event handler.
	 * @param view The parent view.
	 */
	public RenameBot(MainPanel view){
		this.view = view;
	}

	public void actionPerformed(ActionEvent ae) {
		view.getBotPanel().renameAction();
	}
	
}

/**
 * Handles the event to duplicate a bot.
 */
class DuplicateBot implements ActionListener {
	
	private MainPanel view;
	
	/**
	 * Create an DuplicateBot event handler.
	 * @param view The parent view.
	 */
	public DuplicateBot(MainPanel view){
		this.view = view;
	}

	public void actionPerformed(ActionEvent ae) {
		view.getBotPanel().duplicateAction();
	}
	
}

/**
 * Handles the event to delete a bot.
 */
class DeleteBot implements ActionListener {
	
	private MainPanel view;
	
	/**
	 * Create an DeleteBot event handler.
	 * @param view The parent view.
	 */
	public DeleteBot(MainPanel view){
		this.view = view;
	}

	public void actionPerformed(ActionEvent ae) {
		view.getBotPanel().deleteAction();
	}
	
}
