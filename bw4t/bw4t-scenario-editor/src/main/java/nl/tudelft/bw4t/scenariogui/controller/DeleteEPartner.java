package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to delete an E-partner.
 */
public class DeleteEPartner implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
	private MainPanel view;

	/**
	 * Create an DeleteEPartner event handler.
	 * @param newView The parent view.
	 */
	public DeleteEPartner(MainPanel newView) {
		this.view = newView;
	}

	/**
	 * Gets called when an e-partner is deleted.
	 * @param ae The action event.
	 */
	public void actionPerformed(ActionEvent ae) {
		view.getEntityPanel().deleteEPartnerAction();
	}
}
