package nl.tudelft.bw4t.map.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import nl.tudelft.bw4t.map.editor.EnvironmentStore;
import nl.tudelft.bw4t.map.renderer.MapRenderer;

public class EnvironmentStoreController {
	
	private EnvironmentStore view;
	
	private MapPanelController map;
	
	/**
	 * The EnvironmentStoreController class takes care of all the ActionListeners.
	 * 
	 * @param theView is the JFrame from the environment store.
	 * @param theMap is the map we would like to edit.
	 */
	public EnvironmentStoreController(EnvironmentStore theView, MapPanelController theMap) {
		this.view = theView;
		this.map = theMap;
		
		// Save As
		// TODO: Move actionlistener to new class (MenuOptionSaveAs) for MVC
		getMainView().getTopMenuBar().getMenuItemFileSaveAs().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		    	map.saveAsFile();
			}
        });
		
		// Preview
		// TODO: Move actionlistener to new class (MenuOptionPreview) for MVC
		getMainView().getTopMenuBar().getMenuItemPreview().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Preview");
		    	JFrame preview = new JFrame("Map Preview");
		    	preview.add(new MapRenderer(new MapPreviewController(map)));
		    	preview.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    	preview.pack();
		    	preview.setVisible(true);
			}
		});
		
		// Change Area to corridor
		// TODO: Move actionlistener to new class (AreaChangeToCorridor) for MVC
		getMainView().getRightClickPopup().getMenuItemAreaCorridor().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
		    	System.out.println("Change to Corridor");
			}
		});

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
    	return map;
    }
}
