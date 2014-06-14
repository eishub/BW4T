package nl.tudelft.bw4t.map.editor.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.map.editor.EnvironmentStore;

class WindowExit extends WindowAdapter {
	
	private EnvironmentStore view;
	
	public WindowExit(EnvironmentStore newView) {
		this.view = newView;
	}
	
    @Override
    public void windowClosing(WindowEvent e) {
    	boolean doQuit = view.getEnvironmentStoreController().promptUserToQuit();
    	
    	if(doQuit) {
    		view.closeEnvironmentStore();
    	}
    }

}
