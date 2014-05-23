package nl.tudelft.bw4t.scenariogui.controllers.entitypanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to create a new E-partner.
 */
public class AddNewEPartner implements ActionListener {

    /**
     * The <code>MainPanel</code> serving as the content pane.
     */
    private MainPanel view;

    /**
     * Create an AddNewEpartner event handler.
     *
     * @param newView The parent view.
     */
    public AddNewEPartner(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Executes action that needs to happen when the "New E-partner" button is
     * pressed.
     *
     * @param ae The action
     */
    public void actionPerformed(ActionEvent ae) {
        Object[] newEPartnerObject = {"Unnamed E-partner", 1};
        view.getEntityPanel().getEPartnerTableModel().addRow(newEPartnerObject);
    }
}
