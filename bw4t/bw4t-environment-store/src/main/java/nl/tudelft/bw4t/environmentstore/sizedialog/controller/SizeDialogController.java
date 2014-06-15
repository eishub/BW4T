package nl.tudelft.bw4t.environmentstore.sizedialog.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.environmentstore.sizedialog.view.SizeDialog;
import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.map.renderer.MapRenderer;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;


/**
 * The SizeDialogController class serves as a controller for the SizeDialog
 */
public class SizeDialogController {

	/**
     * the view being controlled
     */
	private SizeDialog view;
	
	
	/**
	 * The SizeDialogController class takes care of all the ActionListeners.
	 */
	public SizeDialogController(SizeDialog pview) {
		this.view = pview;
	
		view.getBeginFromScratchButton().addActionListener(
				new BeginFromScratchButtonListener(getMainView()));
		view.getExistingMapButton().addActionListener(
				new EditExistingMapButtonListener(getMainView()));
		view.getStandardBasisButton().addActionListener(
				new StandardBasisButtonListener(getMainView()));
	}
	
    /**
     * Return the view being controlled.
     * @return The JFrame being controlled.
     */
    public final SizeDialog getMainView() {
        return view;
    }
    
    /** 
     * Open an existing map
     * @return MapPanelController
     */
    /*
    public MapPanelController openFile() {
    	// Open configuration file
    	JFileChooser fileChooser = new JFileChooser();
    	
    	FileNameExtensionFilter filter = new FileNameExtensionFilter(".java");
    	fileChooser.setFileFilter(filter);
    	
    	int returnVal = fileChooser.showOpenDialog(view);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	File file = fileChooser.getSelectedFile();
        	String openedFile = fileChooser.getSelectedFile().toString();

        	try {
        		MapPanelController mpc = file;
        		
        	}
        	
        }
    } */
}

   

