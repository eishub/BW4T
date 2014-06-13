package nl.tudelft.bw4t.map.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import nl.tudelft.bw4t.map.editor.EnvironmentStore;
import nl.tudelft.bw4t.map.renderer.MapRenderer;

/**
 * The EnvironmentStoreController class serves as a controller for the EnvironmentStore
 *
 */
public class EnvironmentStoreController {
	
	private EnvironmentStore view;
	
	private MapPanelController mapcontroller;
	
	/**
	 * The EnvironmentStoreController class takes care of all the ActionListeners.
	 * 
	 * @param es is the JFrame from the environment store.
	 * @param mc is the map we would like to edit.
	 */
	public EnvironmentStoreController(EnvironmentStore es, MapPanelController mc) {
		this.view = es;
		this.mapcontroller = mc;
		
		// New
		// TODO: Move actionlistener to new class (MenuOptionSaveAs) for MVC
		getMainView().getTopMenuBar().getMenuItemFileNew().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		    	view.closeEnvironmentStore();
		    	// TODO: add check and start new sizeDialog
			}
        });
		
		// Save As
		// TODO: Move actionlistener to new class (MenuOptionSaveAs) for MVC
		getMainView().getTopMenuBar().getMenuItemFileSaveAs().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		    	mapcontroller.saveAsFile();
			}
        });
		
		// Preview
		// TODO: Move actionlistener to new class (MenuOptionPreview) for MVC
		getMainView().getTopMenuBar().getMenuItemPreview().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		    	JFrame preview = new JFrame("Map Preview");
		    	preview.add(new MapRenderer(new MapPreviewController(mapcontroller)));
		    	preview.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    	preview.pack();
		    	preview.setVisible(true);
			}
		});
		
		// Exit
		// TODO: Move actionlistener to new class (MenuOptionSaveAs) for MVC
		getMainView().getTopMenuBar().getMenuItemFileExit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            boolean doQuit = promptUserToQuit();

	            if (doQuit) {
	                view.closeEnvironmentStore();
	            }
			}
        });

	}
	
    /**
     * Ask the user if (s)he wishes to save the scenario.
     * @return True if the user wishes to save the scenario.
     */
    public boolean promptUserToSave() {
        // Check if user wants to save current configuration
        int response = EnvironmentStore.getOptionPrompt().showConfirmDialog(
                null, "Do you want to save the current configuration?", "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return response == JOptionPane.YES_OPTION;
    }

    /**
     * Ask the user if (s)he wishes to quit the program.
     * @return True if the user wishes to quit.
     */
    public boolean promptUserToQuit() {
        int response = EnvironmentStore.getOptionPrompt().showConfirmDialog(
                null, "Are you sure you want to exit the program?", "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

       return response == JOptionPane.YES_OPTION;
    }
	
    /**
     * Return the view being controlled.
     * @return The JFrame being controlled.
     */
    public final EnvironmentStore getMainView() {
        return view;
    }
    
    /**
     * Return the map being edited.
     * @return The Map being edited.
     */
    public final MapPanelController getMap() {
    	return mapcontroller;
    }
}
