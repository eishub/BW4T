package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to modify an E-partner.
 */
public class ModifyEPartner implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
    private MainPanel view;

    /**
     * Create an ModifyEPartner event handler.
     * @param newView The parent view.
     */
    public ModifyEPartner(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Gets called when the e-partner is modified.
     * @param ae The action event.
     */
	public void actionPerformed(ActionEvent ae) {
		view.getEntityPanel().modifyEPartnerAction();
	}
}
