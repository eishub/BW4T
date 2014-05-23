package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.config.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.util.DefaultOptionPrompt;
import nl.tudelft.bw4t.scenariogui.util.OptionPrompt;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;

/**
 * Handles the event of the menu.
 */
public abstract class AbstractMenuOption implements ActionListener {

    /**
     * The menu bar as view.
     */
    private MenuBar view;

    /**
     * The current controllers that is controlling this.
     */
    private ScenarioEditorController controller;

    //made a variable for this so we can call it during testing

    /**
     * The file chooser.
     */
    private JFileChooser currentFileChooser;


    /**
     * Constructs a menu option object.
     *
     * @param newView  The new view.
     * @param mainView The main view controllers.
     */
    public AbstractMenuOption(final MenuBar newView, final ScenarioEditorController mainView) {
        this.view = newView;
        this.setController(mainView);

        /* Set the intial file chooser and option prompt, can eventually be changed when tests
         * need to be ran.
         */
        setCurrentFileChooser(new JFileChooser());
    }

    /**
     * Gets the current file chooser.
     *
     * @return The current file chooser.
     */
    public JFileChooser getCurrentFileChooser() {
        return currentFileChooser;
    }

    /**
     * Sets the new current file chooser.
     *
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
     *
     * @param saveAs Whether or not to open a file chooser.
     */
    public void saveFile(final boolean saveAs) {
        String path = view.getLastFileLocation();
        if (saveAs || !view.hasLastFileLocation()) {
            currentFileChooser = getCurrentFileChooser();

            /** Adds an xml filter for the file chooser: */
            currentFileChooser.setFileFilter(FileFilters.xmlFilter());

            if (currentFileChooser.showSaveDialog(getController().getMainView()) == JFileChooser.APPROVE_OPTION) {
                File file = currentFileChooser.getSelectedFile();

                path = file.getAbsolutePath();

                String extension = ".xml";
                if (!path.endsWith(extension)) {
                    path += extension;
                }
            } else {
                return;
            }
        }
        try {
            new BW4TClientConfig((MainPanel) (getController().getMainView()).getContentPane(), path).toXML();
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
     * Returns the MenuBar
     *
     * @return The MenuBar
     */
    public MenuBar getMenuView() {
        return this.view;
    }

    /**
     * Gets called when the button associated with this action
     * is pressed.
     *
     * @param e The action event.
     */
    public abstract void actionPerformed(ActionEvent e);

    /**
     * Gets the controllers.
     *
     * @return The controllers.
     */
    public ScenarioEditorController getController() {
        return controller;
    }

    /**
     * Sets the controllers.
     *
     * @param newController The new controllers.
     */
    public void setController(final ScenarioEditorController newController) {
        controller = newController;
    }

}


