package nl.tudelft.bw4t.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.ScenarioEditor;
import nl.tudelft.bw4t.config.BW4TClientConfig;
import nl.tudelft.bw4t.gui.MenuBar;
import nl.tudelft.bw4t.gui.panel.BotPanel;
import nl.tudelft.bw4t.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.gui.panel.MainPanel;

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

abstract class MenuOption implements ActionListener {

    protected MenuBar view;
    protected Controller controller;

    public MenuOption(MenuBar view, Controller mainView) {
        this.view = view;
        this.controller = mainView;
    }

    public void saveFile() {
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

    abstract public void actionPerformed(ActionEvent e);
}

class MenuOptionOpen extends MenuOption {

    public MenuOptionOpen(MenuBar view, Controller mainView) {
        super(view, mainView);
    }

    public void actionPerformed(ActionEvent e) {
        ConfigurationPanel configPanel = super.controller.getMainView().getMainPanel().getConfigurationPanel();
        BotPanel botPanel = super.controller.getMainView().getMainPanel().getBotPanel();

        // Check if current config is different to default config
        if(!configPanel.isDefault()) {
            // Check if user wants to save current configuration
            int response = JOptionPane.showConfirmDialog(null, "Do you want to save the current configuration?", "", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                saveFile();
            }
        }

        // Open configuration file
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(controller.getMainView()) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                BW4TClientConfig temp = BW4TClientConfig.fromXML(file.getAbsolutePath());

                // Fill the config panel
                configPanel.setClientIP(temp.getClientIp());
                configPanel.setClientPort(""+temp.getClientPort());
                configPanel.setServerIP(temp.getServerIp());
                configPanel.setServerPort(""+temp.getServerPort());
                configPanel.setUseGui(temp.isLaunchGui());
                configPanel.setUseGoal(temp.isUseGoal());
                configPanel.setAgentClassFile(temp.getAgentClass());
                configPanel.setMapFile(temp.getMapFile());

                // Fill the bot panel
                //TODO fill botPanel
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (JAXBException e1) {
                e1.printStackTrace();
            }

        }
    }
}


class MenuOptionExit extends MenuOption {

    public MenuOptionExit(MenuBar view, Controller mainView) {
        super(view, mainView);
    }

    public void actionPerformed(ActionEvent e) {
        ConfigurationPanel configPanel = super.controller.getMainView().getMainPanel().getConfigurationPanel();

        // Check if current config is different to default config
        if(!configPanel.isDefault()) {
            // Check if user wants to save current configuration
            int response = JOptionPane.showConfirmDialog(null, "Do you want to save the current configuration?", "", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                saveFile();
            }
        }

        System.exit(0);
    }
}


class MenuOptionSave extends MenuOption {

    public MenuOptionSave(MenuBar view, Controller mainView) {
        super(view, mainView);
    }

    public void actionPerformed(ActionEvent e) {
        saveFile();
    }
}

class MenuOptionNew extends MenuOption {

    public MenuOptionNew(MenuBar view, Controller mainView) {
        super(view, mainView);
    }

    public void actionPerformed(ActionEvent e) {
        ConfigurationPanel configPanel = super.controller.getMainView().getMainPanel().getConfigurationPanel();

        // Check if current config is different to default config
        if(!configPanel.isDefault()) {
            // Check if user wants to save current configuration
            int response = JOptionPane.showConfirmDialog(null, "Do you want to save the current configuration?", "", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                saveFile();
            }
        }

        // Reset the config panel
        configPanel.setClientIP(ConfigurationPanel.DEFAULT_VALUES.DEFAULT_IP.getValue());
        configPanel.setClientPort(ConfigurationPanel.DEFAULT_VALUES.DEFAULT_PORT.getValue());
        configPanel.setServerIP(ConfigurationPanel.DEFAULT_VALUES.DEFAULT_IP.getValue());
        configPanel.setServerPort(ConfigurationPanel.DEFAULT_VALUES.DEFAULT_PORT.getValue());
        configPanel.setUseGui(ConfigurationPanel.DEFAULT_VALUES.USE_GUI.getBooleanValue());
        configPanel.setUseGoal(ConfigurationPanel.DEFAULT_VALUES.USE_GOAL.getBooleanValue());
        configPanel.setAgentClassFile(ConfigurationPanel.DEFAULT_VALUES.AGENT_CLASS.getValue());
        configPanel.setMapFile(ConfigurationPanel.DEFAULT_VALUES.MAP_FILE.getValue());

        // Reset the bot panel
        //TODO reset botPanel
    }
}
