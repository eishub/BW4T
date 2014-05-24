package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to modify an E-partner.
 * <p>
 * @author        
 * @version     0.1                
 * @since       12-05-2014        
 */
class ModifyEPartner implements ActionListener {

    /**
     * The <code>MainPanel</code> serving as the content pane.
     */
    private MainPanel view;

    /**
     * Create an ModifyEPartner event handler.
     *
     * @param newView The parent view.
     */
    public ModifyEPartner(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Executes action that needs to happen when the "Modify E-partner" button is
     * pressed.
     * TODO Open BotStore window
     * Gets called when the e-partner is modified.
     *
     * @param ae The action event.
     */
    public void actionPerformed(ActionEvent ae) {
        int row = view.getEntityPanel().getSelectedEPartnerRow();

        if (row == -1) {
            ScenarioEditor.option.showMessageDialog(null, "Please select the E-partner you want to modify.");
        } else {
            System.out.println("Go to Bot Store to modify an E-Partner");
        }
    }
}
