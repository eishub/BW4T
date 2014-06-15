package nl.tudelft.bw4t.environmentstore.sizedialog.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.environmentstore.sizedialog.view.SizeDialog;

public class EditExistingMapButtonListener implements ActionListener {

	private SizeDialog view;

	/**
	 * The constructor for this action listener.
	 * 
	 * @param pview
	 *            The frame with the button in it.
	 */
	
	public EditExistingMapButtonListener(SizeDialog pview) {
		this.view = pview;
	}

	/**
	 * Perform the required action 
	 * 
	 * @param ae
	 *            The action event triggering this method.
	 */
	public void actionPerformed(ActionEvent ae) {
		//TODO uncomment this (and delete the other MapPanelController) when openFile() works
		//SizeDialogController sdc = view.getSizeDialogController();
		//MapPanelController mc = sdc.openFile();
		
		MapPanelController mc = new MapPanelController(view.getRows(),
    			view.getColumns(),
                view.getEntities(), true,
                view.isLabelsVisible());
		
		EnvironmentStore es = new EnvironmentStore(mc);
    	es.setVisible(true);
    	view.setVisible(false);
	}
}
