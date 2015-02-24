package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Handles the event to delete an E-partner.
 * 
 * @version 0.1
 * @since 12-05-2014
 */
class DeleteEPartner implements ActionListener {

    private MainPanel view;
    
    private BW4TClientConfig model;

    /**
     * Create an DeleteEPartner event handler.
     * 
     * @param newView
     *            The parent view.
     * @param model           
     *            The model.
     */
    public DeleteEPartner(MainPanel newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;
    }

    /**
     * Executes action that needs to happen when the "Delete E-partner" button
     * is pressed. Gets called when an e-partner is deleted.
     * 
     * @param ae
     *            The action event.
     */
    public void actionPerformed(ActionEvent ae) {
        int row = view.getEntityPanel().getSelectedEPartnerRow();

        if (row == -1) {
            ScenarioEditor.getOptionPrompt().showMessageDialog(null,
                    "Please select the E-partner you want to delete.");
        } else {
            int response = ScenarioEditor.getOptionPrompt()
                    .showConfirmDialog(null,
                            "Are you sure you want to delete this E-partner?",
                            "", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                 model.removeEpartner(model.getEpartner(row));
                 view.getEntityPanel().getEPartnerTableModel().removeRow(row);
            }
        }
    }
}
