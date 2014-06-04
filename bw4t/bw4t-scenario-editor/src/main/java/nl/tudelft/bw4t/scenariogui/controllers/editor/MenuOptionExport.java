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

/**
 * Handles the event to export the project to mas2g.
  * <p>
 * @author      Calvin Wong Loi Sing
 * @version     0.1                
 * @since       02-06-2014
 */
class MenuOptionExport extends AbstractMenuOption {

    /**
     * Constructs a new menu option export object.
     *
     * @param view     The view.
     * @param mainView The controlling main view.
     */
    public MenuOptionExport(final MenuBar view, final ScenarioEditorController mainView) {
        super(view, mainView);
    }

    /**
     * Gets called when the menu option export is called.
     *
     * @param e The action event.
     */
    //TODO: Split up in multiple shorter methods
    public void actionPerformed(final ActionEvent e) {
        saveFile();
        if(getMenuView().hasLastFileLocation()) {
            File saveLocation = new File(getMenuView().getLastFileLocation());

            JFileChooser filechooser = getCurrentFileChooser();

            filechooser.setCurrentDirectory(new File("."));
            filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            filechooser.setAcceptAllFileFilterUsed(false);

            if (filechooser.showOpenDialog(getController().getMainView()) == JFileChooser.APPROVE_OPTION) {
                String directory = filechooser.getSelectedFile().getAbsolutePath();
                try {
                    String saveDirectory = directory + "/" + saveLocation.getName();

                    saveXMLFile(saveDirectory);
                    BW4TClientConfig configuration = BW4TClientConfig.fromXML(saveDirectory);

                    /* Split the name into two around the ., and pass the name without the extension */
                    ExportToMAS.export(directory, configuration, saveLocation.getName().split("\\.")[0]);
                } catch (JAXBException ex) {
                    ScenarioEditor.handleException(
                            ex, "Error: Saving to XML has failed.");
                } catch (FileNotFoundException ex) {
                    ScenarioEditor.handleException(
                            ex, "Error: No file has been found.");
                }
            }
        } else {
            ScenarioEditor.getOptionPrompt().showMessageDialog(null, "Error: Can not export an unsaved scenario.");
        }

    }
    
}
