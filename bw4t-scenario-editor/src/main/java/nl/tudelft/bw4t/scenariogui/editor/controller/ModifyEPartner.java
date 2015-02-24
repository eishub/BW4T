package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.epartner.controller.EpartnerController;
import nl.tudelft.bw4t.scenariogui.epartner.gui.EpartnerFrame;

/**
 * Handles the event to modify an E-partner.
 * 
 * @version     0.1                
 * @since       12-05-2014        
 */
class ModifyEPartner implements ActionListener {

    private MainPanel view;
    
    private BW4TClientConfig model;

    /**
     * Create an ModifyEPartner event handler.
     *
     * @param newView The parent view.
     * @param model The model.
     */
    public ModifyEPartner(final MainPanel newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;
    }

    /**
     * Executes action that needs to happen when the "Modify E-partner" button is
     * pressed.
     * Gets called when the e-partner is modified.
     *
     * @param ae The action event.
     */
    public void actionPerformed(ActionEvent ae) {
        int row = view.getEntityPanel().getSelectedEPartnerRow();

        if (row == -1) {
            ScenarioEditor.getOptionPrompt().showMessageDialog(null, "Please select the E-partner you want to modify.");
        } else {
            new EpartnerFrame(new EpartnerController(view, row));
            view.getEntityPanel().setEpartnerStore(true);
        }
    }
}
