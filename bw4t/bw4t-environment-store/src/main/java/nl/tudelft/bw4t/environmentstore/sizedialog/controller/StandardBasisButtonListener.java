package nl.tudelft.bw4t.environmentstore.sizedialog.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.environmentstore.sizedialog.view.SizeDialog;

public class StandardBasisButtonListener implements ActionListener {

	private SizeDialog view;

	/**
	 * The constructor for this action listener.
	 * 
	 * @param pview
	 *            The frame with the button in it.
	 */
	
	public StandardBasisButtonListener(SizeDialog pview) {
		this.view = pview;
	}

	/**
	 * Perform the required action 
	 * 
	 * @param ae
	 *            The action event triggering this method.
	 */
	public void actionPerformed(ActionEvent ae) {
		// TODO fill in the right frame that has to be opened
		MapPanelController mc = new MapPanelController(view.getRows(),
    			view.getColumns(),
                view.getEntities(), true,
                true);
		
		EnvironmentStore es = new EnvironmentStore(mc);
    	es.setVisible(true);
    	view.setVisible(false);
	}
}
