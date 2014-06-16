package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.util.AgentFileChecker;
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
    public void actionPerformed(final ActionEvent e) {
        saveFile();
        if (getMenuView().hasLastFileLocation()) {
        	if(allGoalFilesExist()) {
	            File saveLocation = new File(getMenuView().getLastFileLocation());
	
	            JFileChooser filechooser = getCurrentFileChooser();
	
	            filechooser.setSelectedFile(new File(saveLocation.getName().split("\\.")[0]));
	            filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	            filechooser.setAcceptAllFileFilterUsed(false);
	            filechooser.setFileFilter(FileFilters.masFilter());
	
	            if (filechooser.showDialog(getController().getMainView(), "Export MAS project") == JFileChooser.APPROVE_OPTION) {
	                File xmlFile = filechooser.getSelectedFile();
	                exportAsMASProject(xmlFile);
	            }
        	} else {
        		ScenarioEditor.getOptionPrompt().showMessageDialog(null, "Warning: Some goal files are missing.");
        	}
        } else {
            ScenarioEditor.getOptionPrompt().showMessageDialog(null, "Error: Can not export an unsaved scenario.");
        }
    }

    /**
     * Checks wheter all goalfiles exist
     *
     */
    public boolean allGoalFilesExist() {
    	boolean allExist = true;
    	EntityPanel entityPanel = getController().getMainView().getMainPanel().getEntityPanel();
		int numRows = entityPanel.getBotTableModel().getRowCount();
    	
    	for(int i = 0; i < numRows && allExist; i++) {
    		String goalFile = "" + entityPanel.getBotTable().getValueAt(i, 2);
    		allExist = AgentFileChecker.fileNameExists(goalFile);
    	}
    	
    	numRows = entityPanel.getEPartnerTableModel().getRowCount();
    	
    	for(int i = 0; i < numRows && allExist; i++) {
    		String goalFile = "" + entityPanel.getBotTable().getValueAt(i, 2);
    		allExist = AgentFileChecker.fileNameExists(goalFile);
    	}
    	
    	return allExist;
    }

    /**
     * Exports this XML file as a MAS file.
     * @param xmlFile The XML file.
     */
    private void exportAsMASProject(File xmlFile) {
        try {
            String saveDirectory = xmlFile.getAbsolutePath();

            String extension = ".xml";
            if (!saveDirectory.endsWith(extension)) {
                saveDirectory += extension;
            }

            saveConfigAsXMLFile(saveDirectory);
            BW4TClientConfig configuration = BW4TClientConfig.fromXML(saveDirectory);

            /* Split the name into two around the ., and pass the name without the extension */
            ExportToMAS.export(xmlFile.getParent(), configuration, xmlFile.getName().split("\\.")[0]);
        } catch (JAXBException ex) {
            ScenarioEditor.handleException(
                    ex, "Error: Saving to XML has failed.");
        } catch (FileNotFoundException ex) {
            ScenarioEditor.handleException(
                    ex, "Error: No file has been found.");
        }
    }
    
}
