package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.epartner.EpartnerFrame;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to modify an E-partner.
 * <p>
 * @author      Seu Man To
 * @version     0.1                
 * @since       12-05-2014        
 */
class ModifyEPartner implements ActionListener {

    /**
     * The <code>MainPanel</code> serving as the content pane.
     */
    private MainPanel view;
    
    private BW4TClientConfig model;

    /**
     * Create an ModifyEPartner event handler.
     *
     * @param newView The parent view.
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
        } 
        else {
        	String data = (String) view.getEntityPanel().getEPartnerTable().getModel().getValueAt(row, 0);
            new EpartnerFrame(view, row, model);
        }
    }
}
