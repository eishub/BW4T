package nl.tudelft.bw4t.map.editor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import nl.tudelft.bw4t.map.editor.controller.EnvironmentStoreController;
import nl.tudelft.bw4t.map.editor.controller.MapPanelController;
import nl.tudelft.bw4t.map.editor.controller.MapPreviewController;
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
	 * TODO: to write and implement.
	 */
	public void newMap() {
        boolean doSave = envController.promptUserToSave();

        if (doSave) {
        	saveAsFile();
        } else {
        	// Close Environmenst Store
        	envController.getMainView().dispose();
        	// TODO: Also dispose all other windows and reset value like lastFileLocation
        	SizeDialog dialog = new SizeDialog();
        	dialog.setVisible(true);
        }		
	}
	
	/**
	 * Opens a dialog that allows the user to choose a map to edit.
	 * TODO: to write and implement.
	 */
	public void openMap() {
		System.out.println("Opening a new map.");
	}
	
    /**
     * Save the real map to given file
     * 
     * @param file
     * @throws IOException
     * @throws JAXBException
     */
    public void save(File file) throws IOException, JAXBException {
        System.out.println("SAVE to " + file);

        String error = checkConsistency();
        if (error != null) {
            throw new IllegalStateException("save failed: " + error);
        }

        NewMap map = envController.getMapController().createMap();
        JAXBContext context = JAXBContext.newInstance(NewMap.class);

        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal(map, new FileOutputStream(file));

    }
    
    /**
     * Ask user where to save and then call {@link #save(File)}
     */
    public void saveAsFile() {
        try {
            // check before user puts effort in
            String state = checkConsistency();
            if (state != null) {
                throw new IllegalStateException("Map is not ready for save.\n" + state);
            }
            // TODO Auto-generated method stub
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                save(chooser.getSelectedFile());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            EnvironmentStore.showDialog(e, "Save failed: " + e.getMessage());
        }
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
        if (mapController.getSequence().size() <= 0 && !mapController.getRandomize()) {
            return "Sequence must contain at least 1 block color";
        }

        // check if all blocks for sequence are there.
        // first accumulate all blocks from all rooms
        List<BlockColor> allblocks = new ArrayList<BlockColor>();
        for (int i = 0; i < mapController.getZoneController().length; i++) {
            for (int j = 0; j < mapController.getZoneController()[0].length; j++) {
                allblocks.addAll(mapController.getZoneController()[i][j].getColors());
            }
        }

        // first check if there are blocks while random is on
        if (mapController.getRandomize() && (!allblocks.isEmpty() || !mapController.getSequence().isEmpty())) {
            EnvironmentStore
                    .showDialog("There are blocks on the map\nbut the map is set to random.\nWe proceed anyway.");
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

	
	/**
	 * Gets called when the button associated with this action is pressed.
	 * 
	 * @param e
	 *            The action event.
	 */
	public abstract void actionPerformed(ActionEvent e);
}
