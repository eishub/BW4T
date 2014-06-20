package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.DefaultConfigurationValues;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;
import nl.tudelft.bw4t.scenariogui.util.MapSpec;

/**
 * Handles the event of the menu.
 */
public abstract class AbstractMenuOption implements ActionListener {

    private MenuBar view;
    
    private BW4TClientConfig model;

    private ScenarioEditorController controller;

    // made a variable for this so we can call it during testing
    private JFileChooser currentFileChooser;
    
    private boolean fileChooserApprove = false;

    /**
     * Constructs a menu option object.
     * 
     * @param newView
     *            The new view.
     * @param mainView
     *            The main view controllers.
     * @param model
     *            The model.
     */
    public AbstractMenuOption(final MenuBar newView,
            final ScenarioEditorController mainView, BW4TClientConfig model) {
        this.view = newView;
        this.setController(mainView);
        this.model = model;

        /*
         * Set the intial file chooser and option prompt, can eventually be
         * changed when tests need to be ran.
         */
        setCurrentFileChooser(new JFileChooser());
    }

    public JFileChooser getCurrentFileChooser() {
        return currentFileChooser;
    }

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
     * Saves the configuration to XML. When the configuration hasn't been saved
     * before an file chooser is opened.
     * 
     * @param saveAs
     *            Whether or not to open a file chooser.
     */
    public void saveFile(final boolean saveAs) {
        if (validateBotCount() && verifyMapSelected()) {

            String path = view.getLastFileLocation();
            // Check if the previous save location exists.
            if (wasPreviousSaveRemoved(path)) {
                view.setLastFileLocation(null);
                currentFileChooser.setCurrentDirectory(new File("."));
            }

            if (saveAs || !view.hasLastFileLocation()) {
                path = getPathToSaveFromUser();
                if (path == null) {
                    return;
                }
            }
            saveConfigAsXMLFile(path);
        }
    }

    private boolean wasPreviousSaveRemoved(String path) {
        return view.hasLastFileLocation() && !new File(path).exists();
    }

    public void setFileChooserApprove() {
        currentFileChooser = getCurrentFileChooser();
        
        /** Adds an xml filter for the file chooser: */
        currentFileChooser.setFileFilter(FileFilters.xmlFilter());
        
        fileChooserApprove = currentFileChooser
                .showDialog(getController().getMainView(), "Save Scenario") == JFileChooser.APPROVE_OPTION;
    }
    
    public boolean getFileChooserApprove() {
        return fileChooserApprove;
    }
    
    private String getPathToSaveFromUser() {
        String path = null;

        setFileChooserApprove();
        if (fileChooserApprove) {
            File file = currentFileChooser.getSelectedFile();

            path = file.getAbsolutePath();

            String extension = ".xml";
            if (!path.endsWith(extension)) {
                path += extension;
                file = new File(path);
            }
            controller.getMainView().setWindowTitle(file.getName());
        }
        return path;
    }

    private boolean validateBotCount() {
        ScenarioEditor se = controller.getMainView();
        MainPanel mp = se.getMainPanel();
        ConfigurationPanel cp = mp.getConfigurationPanel();
        MapSpec map = cp.getMapSpecifications();
        int botCount = getModel().getAmountBot();
        if (map.isSet() && botCount > map.getEntitiesAllowedInMap()) {
            ScenarioEditor.getOptionPrompt().showMessageDialog(
                    view,
                    "The selected map can only hold "
                            + map.getEntitiesAllowedInMap()
                            + " bots. Please delete some first.");
            return false;
        }
        return true;
    }

    private boolean verifyMapSelected() {
        ScenarioEditor se = controller.getMainView();
        MainPanel mp = se.getMainPanel();
        ConfigurationPanel cp = mp.getConfigurationPanel();
        String map = cp.getMapFile();
        if(map.trim().isEmpty()) {
            int response = ScenarioEditor.getOptionPrompt().showConfirmDialog(getController().getMainView(),
                    "Warning: No map file has been selected. Press OK to continue.",
                    "",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            return response == JOptionPane.OK_OPTION;
        }
        return true;
    }

    /**
     * Saves the config at the path specified as an XML file.
     * @param destination The path.
     */
    public void saveConfigAsXMLFile(String destination) {
        try {
            BW4TClientConfig configuration = getModel();
            configuration.setFileLocation(destination);
            configuration.setUseGoal(DefaultConfigurationValues.USE_GOAL.getBooleanValue());
    
            configuration.toXML();
            view.setLastFileLocation(destination);
        } catch (JAXBException e) {
            ScenarioEditor.handleException(e,
                    "Error: Saving to XML has failed.");
        } catch (FileNotFoundException e) {
            ScenarioEditor.handleException(e, "Error: No file has been found.");
        }
    }

    /**
     * Update the model with the new bot and epartners, and update the counts in the view.
     */
    public void updateModelAndView() {
        ScenarioEditor se = controller.getMainView();
        MainPanel mp = se.getMainPanel();
        ConfigurationPanel cp = mp.getConfigurationPanel();
        cp.updateOldValues();
        getModel().updateOldBotConfigs();
        EntityPanel ep = mp.getEntityPanel();
        ep.updateEPartnerCount(getModel().getAmountEPartner());
        getModel().updateOldEpartnerConfigs();
    }

    public MenuBar getMenuView() {
        return this.view;
    }

    /**
     * Gets called when the button associated with this action is pressed.
     * 
     * @param e The action event.
     */
    public abstract void actionPerformed(ActionEvent e);

    public ScenarioEditorController getController() {
        return controller;
    }

    public void setController(final ScenarioEditorController newController) {
        controller = newController;
    }
    
    public BW4TClientConfig getModel() {
        return model;
    }

}
