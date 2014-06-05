package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.util.ExportToMAS;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;

/**
 * Handles the event to export the project to mas2g.
 * <p>
 * @version     0.1                
 * @since       02-06-2014
 */
class MenuOptionExport extends AbstractMenuOption {

    /**
     * Constructs a new menu option export object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     * @param model    The model.
     */
    public MenuOptionExport(final MenuBar view, final ScenarioEditorController mainView, BW4TClientConfig model) {
        super(view, mainView, model);
    }

    /**
     * Gets called when the menu option export is called.
     *
     * @param e The action event.
     */
    //TODO: Split up in multiple shorter methods
    public void actionPerformed(final ActionEvent e) {
        saveFile();
        if (getMenuView().hasLastFileLocation()) {
            File saveLocation = new File(getMenuView().getLastFileLocation());

            JFileChooser filechooser = getCurrentFileChooser();

            filechooser.setSelectedFile(new File(saveLocation.getName().split("\\.")[0]));
            filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            filechooser.setAcceptAllFileFilterUsed(false);
            filechooser.setFileFilter(FileFilters.masFilter());

            if (filechooser.showDialog(getController().getMainView(), "Export MAS project") == JFileChooser.APPROVE_OPTION) {
                File masFile = filechooser.getSelectedFile();
                try {
                    String saveDirectory = masFile.getAbsolutePath();

                    String extension = ".xml";
                    if (!saveDirectory.endsWith(extension)) {
                        saveDirectory += extension;
                    }

                    saveXMLFile(saveDirectory);
                    BW4TClientConfig configuration = BW4TClientConfig.fromXML(saveDirectory);

                    /* Split the name into two around the ., and pass the name without the extension */
                    ExportToMAS.export(masFile.getParent(), configuration, masFile.getName().split("\\.")[0]);
                } catch (JAXBException ex) {
                    ScenarioEditor.handleException(
                            ex, "Error: Saving to XML has failed.");
                } catch (FileNotFoundException ex) {
                    ScenarioEditor.handleException(
                            ex, "Error: No file has been found.");
                }
            }

        } 
        else {
            ScenarioEditor.getOptionPrompt().showMessageDialog(null, "Error: Can not export an unsaved scenario.");
        }

    }
    
}
