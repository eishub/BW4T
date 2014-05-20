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
     * Create a controller object.
     * @param newView used to call relevant functions by the event listeners.
     */
    public Controller(final ScenarioEditor newView) {
        this.view = newView;

        /** Create the action listeners for the ConfigurationPanel. */

        /** Listener for the map file chooser */
        getMainView().getMainPanel().getConfigurationPanel().getChooseMapFile()
            .addActionListener(
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

        /** Adds the listeners for the EntitiesPanel */
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
    }

    /**
     * Return the view being controlled.
     * @return The JFrame being controlled.
     */
    public final ScenarioEditor getMainView() {
        return view;
    }
}

/**
 * Handles the event to choose a map file.
 */
class ChooseMapFileListener implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
    private MainPanel view;

    /**
     * Create a ChooseMapFileListener event handler.
     *
     * @param newView The parent view.
     */
    public ChooseMapFileListener(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Listens to the map file chooser.
     * @param actionEvent The event.
     */
    public void actionPerformed(final ActionEvent actionEvent) {
        /**
         * Create a file chooser, opening at the last path location saved in the
         * configuration panel
         */
        JFileChooser fc = view.getConfigurationPanel().getFileChooser();
        /** Create a file name extension filter to filter on MAP files */

        int returnVal = fc.showOpenDialog(view);
        File file = fc.getSelectedFile();
        String mapExtension = ".map";
        /** Makes sure only files with the right extension are accepted */
        if (returnVal == JFileChooser.APPROVE_OPTION
                && file.getName().endsWith(mapExtension)) {
            view.getConfigurationPanel().setMapFile(file.getPath());
        }
        else if (returnVal == JFileChooser.APPROVE_OPTION
                && !file.getName().endsWith(mapExtension)) {
            JOptionPane.showMessageDialog(view,
                    "This is not a valid file.");
        }
    }
}

/**
 * Handles the event to create a new bot.
 */
class AddNewBot implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
    private MainPanel view;

    /**
     * Create an AddNewBot event handler.
     *
     * @param newView The parent view.
     */
    public AddNewBot(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Listens to the add a new bot button.
     * @param ae The action event.
     */
    public void actionPerformed(final ActionEvent ae) {
        view.getEntityPanel().addBotAction();
    }

}

/**
 * Handles the event to modify a bot.
 */
class ModifyBot implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
    private MainPanel view;

    /**
     * Create an ModifyBot event handler.
     *
     * @param newView The parent view.
     */
    public ModifyBot(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Listens to the modify bot button.
     * @param ae The action.
     */
    public void actionPerformed(final ActionEvent ae) {
        view.getEntityPanel().modifyBotAction();
    }
}


/**
 * Handles the event to delete a bot.
 */
class DeleteBot implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
    private MainPanel view;

    /**
     * Create an DeleteBot event handler.
     *
     * @param newView The parent view.
     */
    public DeleteBot(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Gets called when the delete button is pressed.
     * @param ae The action event.
     */
    public void actionPerformed(final ActionEvent ae) {
        view.getEntityPanel().deleteBotAction();
    }
}

/**
 * Handles the event to create a new E-partner.
 */
class AddNewEPartner implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
    private MainPanel view;

    /**
     * Create an AddNewEpartner event handler.
     * @param newView The parent view.
     */
    public AddNewEPartner(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * 
     * @param ae The action 
     */
	public void actionPerformed(ActionEvent ae) {
		view.getEntityPanel().addEPartnerAction();
	}
}

/**
 * Handles the event to modify an E-partner.
 */
class ModifyEPartner implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
    private MainPanel view;

    /**
     * Create an ModifyEPartner event handler.
     * @param newView The parent view.
     */
    public ModifyEPartner(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Gets called when the e-partner is modified.
     * @param ae The action event.
     */
	public void actionPerformed(ActionEvent ae) {
		view.getEntityPanel().modifyEPartnerAction();
	}
}

/**
 * Handles the event to delete an E-partner.
 */
class DeleteEPartner implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
	private MainPanel view;
	
	/**
	 * Create an DeleteEPartner event handler.
	 * @param newView The parent view.
	 */
	public DeleteEPartner(MainPanel newView) {
		this.view = newView;
	}

	/**
	 * Gets called when an e-partner is deleted.
	 * @param ae The action event.
	 */
	public void actionPerformed(ActionEvent ae) {
		view.getEntityPanel().deleteEPartnerAction();
	}
}

/**
 * Handles the event of the menu.
 */
abstract class AbstractMenuOptionFactory implements ActionListener {

    /** The menu bar as view. */
    private MenuBar view;
    /** The current controller that is controlling this. */
    private Controller controller;

    //made a variable for this so we can call it during testing
    /** The file chooser. */
    private JFileChooser currentFileChooser;
    
    /**
     * Constructs a menu option object.
     * @param newView The new view.
     * @param mainView The main view controller.
     */
    public AbstractMenuOptionFactory(final MenuBar newView, final Controller mainView) {
        this.view = newView;
        this.setController(mainView);
    }

    /**
     * Gets the current file chooser.
     * @return The current file chooser.
     */
    public JFileChooser getCurrentFileChooser() {
        return currentFileChooser;
    }

    /**
     * Sets the new current file chooser.
     * @param newFileChooser The new file chooser to set.
     */
    public void setCurrentFileChooser(final JFileChooser newFileChooser) {
        currentFileChooser = newFileChooser;
    }

    /**
     * Saves a file.
     */
    public void saveFile() {
        saveFile(!view.hasLastFileLocation());
    }

    /**
     * Saves a file.
     * @param saveAs Whether or not to open a file chooser.
     */
    public void saveFile(final boolean saveAs) {
        String path = view.getLastFileLocation();
        if (saveAs || !view.hasLastFileLocation()) {
            currentFileChooser = new JFileChooser();

            /** Adds an xml filter for the file chooser: */
            currentFileChooser.setFileFilter(FileFilters.xmlFilter());

            if (currentFileChooser.showSaveDialog(getController().getMainView())
                    == JFileChooser.APPROVE_OPTION) {
                File file = currentFileChooser.getSelectedFile();

                path = file.getAbsolutePath();
            }
            else {
                return;
            }
        }
        try {
            new BW4TClientConfig((MainPanel)
                    (getController().getMainView()).getContentPane(),
                    path).toXML();
            view.setLastFileLocation(path);
        } catch (JAXBException e) {
            ScenarioEditor.handleException(
                    e, "Error: Saving to XML has failed.");
        } catch (FileNotFoundException e) {
            ScenarioEditor.handleException(
                    e, "Error: No file has been found.");
        }
    }

    /**
     * Gets called when the button associated with this action
     * is pressed.
     * @param e The action event.
     */
    public abstract void actionPerformed(ActionEvent e);

    /**
     * Gets the controller.
     * @return The controller.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Sets the controller.
     * @param newController The new controller.
     */
    public void setController(final Controller newController) {
        controller = newController;
    }
}

/**
 * Handles the event to open a file.
 */
class MenuOptionOpen extends AbstractMenuOptionFactory {

    /**
     * Constructs a new menu option open object.
     * @param view The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionOpen(final MenuBar view, final Controller mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the menu option open button is pressed.
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        ConfigurationPanel configPanel = super.getController().
                getMainView().getMainPanel().getConfigurationPanel();
        EntityPanel entityPanel = super.getController().
                getMainView().getMainPanel().getEntityPanel();

        // Check if current config is different from last saved config
        if (!configPanel.getOldValues().equals(configPanel.getCurrentValues())) {
            // Check if user wants to save current configuration
            int response = JOptionPane.showConfirmDialog(
                    null,
                    Controller.CONFIRM_SAVE_TXT,
                    "",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                saveFile();
                super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
            }
        }

        // Open configuration file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(FileFilters.xmlFilter());

        if (fileChooser.showOpenDialog(getController().getMainView())
                == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                BW4TClientConfig temp = BW4TClientConfig.fromXML(
                        file.getAbsolutePath());

                // Fill the configuration panel
                configPanel.setClientIP(temp.getClientIp());
                configPanel.setClientPort("" + temp.getClientPort());
                configPanel.setServerIP(temp.getServerIp());
                configPanel.setServerPort("" + temp.getServerPort());
                configPanel.setUseGui(temp.isLaunchGui());
//                configPanel.setUseGoal(temp.isUseGoal());
                configPanel.setMapFile(temp.getMapFile());

                // Fill the bot panel
                //TODO fill botPanel
            } catch (JAXBException e1) {
                ScenarioEditor.handleException(
                        e1, "Error: Opening the XML has failed.");
            } catch (FileNotFoundException e1) {
                ScenarioEditor.handleException(
                        e1, "Error: No file has been found. ");
            }
        }

        super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
    }
}

/**
 * Handles the event to exit the program.
 */
class MenuOptionExit extends AbstractMenuOptionFactory {

    /**
     * Constructs a new menu option exit object.
     * @param view The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionExit(final MenuBar view, final Controller mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the exit button is pressed.
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        ConfigurationPanel configPanel =
                super.getController().getMainView().getMainPanel().
                getConfigurationPanel();

        // Check if current config is different from last saved config
        if (!configPanel.getOldValues().equals(configPanel.getCurrentValues())) {
            // Check if user wants to save current configuration
            int response = JOptionPane.showConfirmDialog(
                    null,
                    Controller.CONFIRM_SAVE_TXT,
                    "",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                saveFile();
                super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
            }
        }

        System.exit(0);
    }
}

/**
 * Handles the event to save a file.
 */
class MenuOptionSave extends AbstractMenuOptionFactory {

    /**
     * Constructs a new menu option save object.
     * @param view The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionSave(final MenuBar view, final Controller mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the save menu option gets called.
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        saveFile();
        super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
    }
}

/**
 * Handles the event to save at a chosen location.
 */
class MenuOptionSaveAs extends AbstractMenuOptionFactory {

    /**
     * Constructs a new menu option save as object.
     * @param view The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionSaveAs(final MenuBar view, final Controller mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the menu item save as is pressed.
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        saveFile(true);
        super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
    }
}

/**
 * Handles the event to start a new file.
 */
class MenuOptionNew extends AbstractMenuOptionFactory {

    /**
     * Constructs a new menu option new object.
     * @param view The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionNew(final MenuBar view, final Controller mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the new file menu item is pressed.
     * @param e The action event.
     */
    public void actionPerformed(final ActionEvent e) {
        ConfigurationPanel configPanel = super.getController().getMainView().
                getMainPanel().getConfigurationPanel();

        // Check if current config is different from last saved config
        if (!configPanel.getOldValues().equals(configPanel.getCurrentValues())) {
            // Check if user wants to save current configuration
            int response = JOptionPane.showConfirmDialog(null,
                    Controller.CONFIRM_SAVE_TXT, "",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                saveFile();
                super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();
            }
        }

        // Reset the config panel
        configPanel.setClientIP(ConfigurationPanel.DEFAULT_VALUES.
                DEFAULT_CLIENT_IP.getValue());
        configPanel.setClientPort(ConfigurationPanel.DEFAULT_VALUES.
                DEFAULT_CLIENT_PORT.getValue());
        configPanel.setServerIP(ConfigurationPanel.DEFAULT_VALUES.
                DEFAULT_SERVER_IP.getValue());
        configPanel.setServerPort(ConfigurationPanel.DEFAULT_VALUES.
                DEFAULT_SERVER_PORT.getValue());
        configPanel.setUseGui(ConfigurationPanel.DEFAULT_VALUES.
                USE_GUI.getBooleanValue());
//        configPanel.setUseGoal(ConfigurationPanel.DEFAULT_VALUES.
            //USE_GOAL.getBooleanValue());
        configPanel.setMapFile(ConfigurationPanel.DEFAULT_VALUES.
                MAP_FILE.getValue());
        
        super.getController().getMainView().getMainPanel().getConfigurationPanel().updateOldValues();

        // Reset the bot panel
        //TODO reset botPanel
    }
}
