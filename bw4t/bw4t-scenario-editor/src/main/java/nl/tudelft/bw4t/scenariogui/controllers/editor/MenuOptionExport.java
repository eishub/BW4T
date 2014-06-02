package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.config.BW4TClientConfigIntegration;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.ExportToGOAL;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;

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
        JFileChooser filechooser = getCurrentFileChooser();

        filechooser.setCurrentDirectory(new File("."));
        filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        filechooser.setAcceptAllFileFilterUsed(false);

        if(filechooser.showOpenDialog(getController().getMainView()) == JFileChooser.APPROVE_OPTION) {
            String directory = filechooser.getSelectedFile().getAbsolutePath();
            try {
                saveXMLFile(directory + "/source.xml");
                BW4TClientConfig configuration = BW4TClientConfig.fromXML(directory  + "/source.xml");

                ExportToGOAL.export(directory, configuration);
                System.out.println(directory + " bots: " + configuration.getBots().size());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (JAXBException e1) {
                e1.printStackTrace();
            }
        }

    }
    
}
