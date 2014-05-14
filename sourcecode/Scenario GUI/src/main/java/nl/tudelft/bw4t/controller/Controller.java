package nl.tudelft.bw4t.controller;

import nl.tudelft.bw4t.ScenarioEditor;
import nl.tudelft.bw4t.config.BW4TClientConfig;
import nl.tudelft.bw4t.gui.MenuBar;
import nl.tudelft.bw4t.gui.panel.BotPanel;
import nl.tudelft.bw4t.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.gui.panel.MainPanel;
import nl.tudelft.bw4t.util.XMLManager;

import javax.swing.*;
import javax.xml.bind.JAXBException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

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
        
        /** Adds the listeners for the items in the MenuBar: */
        MenuOptions menuOptions = new MenuOptions(getMainView().getTopMenuBar(), this);
        getMainView().getTopMenuBar().getMenuItemFileExit().addActionListener(menuOptions);
        getMainView().getTopMenuBar().getMenuItemFileNew().addActionListener(menuOptions);
        getMainView().getTopMenuBar().getMenuItemFileOpen().addActionListener(menuOptions);
        getMainView().getTopMenuBar().getMenuItemFileSave().addActionListener(menuOptions);
        
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

/**
 * Handles the menu options.
 * 
 * @author Nick
 *
 */
class MenuOptions implements ActionListener {
	
	private MenuBar view;
	private Controller controller;
	
	public MenuOptions(MenuBar view, Controller mainView) {
		this.view = view;
		this.controller = mainView;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getMenuItemFileExit()) {
			System.exit(0);
		} else if (e.getSource() == view.getMenuItemFileNew()) {
			//TODO
		} else if (e.getSource() == view.getMenuItemFileOpen()) {
			JFileChooser fileChooser = new JFileChooser();
        	if (fileChooser.showOpenDialog(controller.getMainView()) == JFileChooser.APPROVE_OPTION) {
        		File file = fileChooser.getSelectedFile();
        		
        		try {
					BW4TClientConfig temp = BW4TClientConfig.fromXML(file.getAbsolutePath());
										
					// Fill the config panel
					ConfigurationPanel configPanel = controller.getMainView().getMainPanel().getConfigurationPanel();
					
					configPanel.setClientIP(temp.getClientIp());
					configPanel.setClientPort(""+temp.getClientPort());
					configPanel.setServerIP(temp.getServerIp());
					configPanel.setServerPort(""+temp.getServerPort());
					configPanel.setUseGui(temp.isLaunchGui());
					configPanel.setUseGoal(temp.isUseGoal());
					configPanel.setAgentClassFile(temp.getAgentClass());
					configPanel.setMapFile(temp.getMapFile());
					
					// Fill the bot panel
					BotPanel botPanel = controller.getMainView().getMainPanel().getBotPanel();
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (JAXBException e1) {
					e1.printStackTrace();
				}
        		
        	}
		} else if (e.getSource() == view.getMenuItemFileSave()) {
        	JFileChooser fileChooser = new JFileChooser();
        	if (fileChooser.showSaveDialog(controller.getMainView()) == JFileChooser.APPROVE_OPTION) {
        		File file = fileChooser.getSelectedFile();
                try {
        			new BW4TClientConfig((MainPanel) (controller.getMainView()).getContentPane(), file.getAbsolutePath()).toXML();
        		} catch (FileNotFoundException e1) {
        			e1.printStackTrace();
        		} catch (JAXBException e1) {
        			e1.printStackTrace();
        		}
        	}
		}
	}
	
}

