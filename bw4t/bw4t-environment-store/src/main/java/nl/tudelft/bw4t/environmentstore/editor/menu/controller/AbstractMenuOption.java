package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.controller.MapPreviewController;
import nl.tudelft.bw4t.environmentstore.editor.controller.ZoneController;
import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.editor.model.MapConverter;
import nl.tudelft.bw4t.environmentstore.editor.model.SolvabilityAlgorithm;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.environmentstore.sizedialog.view.SizeDialog;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.renderer.MapRenderer;

public abstract class AbstractMenuOption implements ActionListener {

    /** the menu the option is on */
    private MenuBar view;
    
    /** the environment controller */
    private EnvironmentStoreController envController;
    
    /** the mappanel controller being used */
    private MapPanelController mapController;
    
    // made a variable for this so we can call it during testing
    private JFileChooser currentFileChooser;
    
    /**
     * The constructor for AbstractMenuOption
     * @param newView the file menu
     * @param envController the controller of environmentstore
     */
    public AbstractMenuOption(final MenuBar newView, final EnvironmentStoreController envController) {
        this.view = newView;
        this.envController = envController;
        
        this.mapController = envController.getMapController();
        /*
         * Set the intial file chooser and option prompt, can eventually be
         * changed when tests need to be ran.
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
     * @param newFileChooser
     *            The new file chooser to set.
     */
    public void setCurrentFileChooser(final JFileChooser newFileChooser) {
        currentFileChooser = newFileChooser;
    }
    
    /**
     * Opens a new size Dialog that lets the user start from scratch.
     */
    public void newMap() {
        if (envController.notAnEmptyMap()) {
            boolean doSave = envController.promptUserToSave();

            if (doSave) {
                saveFile(true);
            }
        }
           // Close Environmenst Store
        envController.getMainView().dispose();
        // Open new Size Dialog
        SizeDialog dialog = new SizeDialog();
        dialog.setVisible(true);
    }
    
    /**
     * Saves a file.
     */
    public void saveFile() {
        saveFile(!view.hasLastFileLocation());
    }
    

    /**
     * Saves the configuration to Map. When the configuration hasn't been saved
     * before an file chooser is opened.
     * 
     * @param saveAs
     *            Whether or not to open a file chooser.
     */
    public void saveFile(final boolean saveAs) {
        String error = checkConsistency();
        if (error != null) {
            EnvironmentStore.showDialog("Save failed: " + error);
            return;
        }
        NewMap map = MapConverter.createMap(mapController.getEnvironmentMap());
        String mapSolve = SolvabilityAlgorithm.mapIsSolvable(map);
        if (mapSolve != null) {
            int response = JOptionPane
                    .showConfirmDialog(null, "The map is unsolvable. The reason is as follows:\n"
                            + mapSolve + "\n"
                    + "Are you sure you want to save this map?",
                    "Unsolvable map", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == JOptionPane.NO_OPTION) {
                return;
            }
        }  
        String path = view.getLastFileLocation();
    
        if (view.hasLastFileLocation() && !new File(path).exists()) {
            view.setLastFileLocation(null);
            currentFileChooser.setCurrentDirectory(new File("."));
        }
    
        if (saveAs || !view.hasLastFileLocation()) {
            currentFileChooser = getCurrentFileChooser();
            currentFileChooser.setFileFilter(FileFilters.mapFilter());
            
            int returnVal = currentFileChooser.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = currentFileChooser.getSelectedFile();
    
                path = file.getAbsolutePath();
    
                String extension = ".map";
                if (!path.endsWith(extension)) {
                    path += extension;
                    file = new File(path);
                }
                envController.getMainView().setWindowTitle(file.getName());
            } else {
                return;
            }
        }
        try {
            // Check if the file path was not externally deleted.
            saveMapFile(path);
        } catch (JAXBException e) {
            EnvironmentStore.showDialog("Saving the Map to file has failed.");
        } catch (FileNotFoundException e) {
            EnvironmentStore.showDialog("No file has been found.");
        }    
    }
    

    /** 
     * Saves the map to that location
     * @param path the location where it should be saved
     * @throws JAXBException
     * @throws FileNotFoundException
     */
    public void saveMapFile(String path) throws JAXBException,
            FileNotFoundException {
        
        NewMap map = MapConverter.createMap(envController.getMapController().getEnvironmentMap());
        
        NewMap.toXML(map, new FileOutputStream(new File(path)));
        view.setLastFileLocation(path);
    }
    
    /**
     * Preview the map that is currently being edited.
     */
    public void previewMap() {
        JFrame preview = new JFrame("Map Preview");
        preview.add(new JScrollPane(new MapRenderer(new MapPreviewController(mapController))));
        preview.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        preview.pack();
        preview.setVisible(true);
    }
    
    /**
     * Exit the editor with a check whether the user really wants to quit.
     */
    public void exitEditor() {
        if (envController.notAnEmptyMap()) {
            boolean doSave = envController.promptUserToSave();

            if (doSave) {
                saveFile(true);
            } else {
                envController.getMainView().closeEnvironmentStore();
            }
        } else {
            envController.getMainView().closeEnvironmentStore();
        }
    }
    
    
    /**
     * check consistency of the map. The sequence length must be 1 at least. returns string with error message, or null
     * if all ok
     * 
     * @return null, or string with error message.
     */
    public String checkConsistency() {
        boolean foundDropZone = false;
        boolean foundStartZone = false;
        // check if all blocks for sequence are there.
        // first accumulate all blocks from all rooms
        List<BlockColor> allblocks = new ArrayList<BlockColor>();
        for (int i = 0; i < mapController.getZoneControllers().length; i++) {
            for (int j = 0; j < mapController.getZoneControllers()[0].length; j++) {
                ZoneController zone = mapController.getZoneController(i, j);
                allblocks.addAll(zone.getColors());
                if (zone.isDropZone()) {
                    if (foundDropZone) {
                        return ("Map can only contain one drop zone.");
                    }
                    foundDropZone = true;
                }
                if (zone.isStartZone()) {
                    foundStartZone = true;
                }
            }
        }
        
        if (!foundStartZone && !foundDropZone) {
            return ("The map must contain one starting zone and drop zone.");
        } else if (!foundStartZone) {
            return ("There should be at least one start zone.");
        } else if (!foundDropZone) {
            return ("There should be one drop zone.");
        }
        
        if (mapController.getSequence().size() <= 0) {
            return ("Sequence must contain at least one block color.");
        }

        // remove all colors from the sequence. That will throw exception if
        // impossible.
        try {
            allblocks.removeAll(mapController.getSequence());
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        return null;

    }
    
    public MapPanelController getMapController() {
        return mapController;
    }
    
    public EnvironmentStoreController getEnvironmentStoreController() {
        return envController;
    }
    
    public MenuBar getView() {
        return view;
    }

    
    /**
     * Gets called when the button associated with this action is pressed.
     * 
     * @param e
     *            The action event.
     */
    public abstract void actionPerformed(ActionEvent e);
}
