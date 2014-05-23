package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to create a new E-partner.
 */
public class AddNewEPartner implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
    private MainPanel view;

    /**
     * Create an AddNewEpartner event handler.
     * @param newView The parent view.
     */
    public AddNewEPartner(final MainPanel newView) {
        this.view = newView;
    }

    /**
     *
     * @param ae The action
     */
	public void actionPerformed(ActionEvent ae) {
		view.getEntityPanel().addEPartnerAction();
	}
}
