package nl.tudelft.bw4t.map.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.editor.EnvironmentStore;
import nl.tudelft.bw4t.map.editor.gui.MenuBar;
import nl.tudelft.bw4t.map.editor.gui.SizeDialog;
import nl.tudelft.bw4t.map.renderer.MapRenderer;

public abstract class AbstractMenuOption implements ActionListener {

	private MenuBar view;
	
	private EnvironmentStoreController envController;
	
	private MapPanelController mapController;
	
	// made a variable for this so we can call it during testing
	private JFileChooser currentFileChooser;
	
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
	 * TODO: Also dispose ALL OTHER windows and reset value like lastFileLocation
	 */
	public void newMap() {
		if(envController.notAnEmptyMap()) {
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
	 * Opens a dialog that allows the user to choose a map to edit.
	 * TODO: to write and implement.
	 */
	public void openMap() {
		System.out.println("Opening a new map.");
		
		// set opened file for save button
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
        String error = checkConsistency();
        if (error != null) {
        	EnvironmentStore.showDialog("Save failed: " + error);
            return;
        }

		String path = view.getLastFileLocation();

        if (view.hasLastFileLocation() && !new File(path).exists()) {
            view.setLastFileLocation(null);
            currentFileChooser.setCurrentDirectory(new File("."));
        }

		if (saveAs || !view.hasLastFileLocation()) {
			currentFileChooser = getCurrentFileChooser();

            int returnVal = currentFileChooser.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = currentFileChooser.getSelectedFile();

				path = file.getAbsolutePath();

				String extension = ".xml";
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
            saveXMLFile(path);
        } catch (JAXBException e) {
        	EnvironmentStore.showDialog("Saving the map to XML has failed.");
		} catch (FileNotFoundException e) {
			EnvironmentStore.showDialog("No file has been found.");
		}
	}
	

	public void saveXMLFile(String path) throws JAXBException,
			FileNotFoundException {
		
        NewMap map = envController.getMapController().createMap();
        JAXBContext context = JAXBContext.newInstance(NewMap.class);

        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        File file = new File(path);
        m.marshal(map, new FileOutputStream(file));
		view.setLastFileLocation(path);
	}
    
	/**
	 * Preview the map that is currently being edited.
	 */
	public void previewMap() {
    	JFrame preview = new JFrame("Map Preview");
    	preview.add(new MapRenderer(new MapPreviewController(mapController)));
    	preview.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	preview.pack();
    	preview.setVisible(true);
	}
	
	/**
	 * Exit the editor with a check whether the user really wants to quit.
	 */
	public void exitEditor() {
        boolean doQuit = envController.promptUserToQuit();

        if (doQuit) {
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
		if (mapController.getNumberOfEntities() < 1) {
            return "There should be at least 1 entity";
        }
        if (mapController.getSequence().size() <= 0) {
            return "Sequence must contain at least 1 block color";
        }
        
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
                    if (foundStartZone) {
                        return ("Map can only contain one starting zone.");
                    }
                    foundStartZone = true;
                }
                //TODO check if can be removed: allblocks.addAll(mapController.getZoneControllers()[i][j].getColors());
            }
        }
        if(!foundStartZone || !foundDropZone) {
        	return ("The map must contain one starting zone and drop zone.");
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
