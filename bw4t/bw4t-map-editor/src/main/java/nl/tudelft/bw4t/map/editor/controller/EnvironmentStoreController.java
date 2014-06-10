package nl.tudelft.bw4t.map.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import nl.tudelft.bw4t.map.editor.EnvironmentStore;
import nl.tudelft.bw4t.view.MapRenderer;

public class EnvironmentStoreController {
	
	private EnvironmentStore view;
	
	private Map map;
	
	public EnvironmentStoreController(EnvironmentStore theView, Map theMap) {
		this.view = theView;
		this.map = theMap;
		
		// Save As
		// TODO: Move actionlistener to new class (MenuOptionSaveAs) for MVC
		getMainView().getTopMenuBar().getMenuItemFileSaveAs().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
		    	map.saveAsFile();
			}
        });
		
		// Preview Map
		// TODO: Move actionlistener to new class (MenuOptionPreview) for MVC
		getMainView().getTopMenuBar().getMenuItemPreview().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Preview");
		    	JFrame preview = new JFrame("Map Preview");
		    	preview.add(new MapRenderer(new MapPreviewController(map)));
		    	preview.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    	preview.pack();
		    	preview.setVisible(true);
			}
		});
	}
	
    /**
     * Return the view being controlled.
     *
     * @return The JFrame being controlled.
     */
    public final EnvironmentStore getMainView() {
        return view;
    }
    
    /**
     * Return the Map model.
     * 
     * @return The Map model.
     */
    public final Map getMap() {
    	return map;
    }
}
