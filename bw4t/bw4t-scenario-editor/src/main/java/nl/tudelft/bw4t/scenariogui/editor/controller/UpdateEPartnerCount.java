package nl.tudelft.bw4t.scenariogui.editor.controller;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Handles the event to update the epartner count.
 * 
 * @version 0.1
 * @since 05-06-2014
 */
public class UpdateEPartnerCount implements TableModelListener {

    private MainPanel view;
    
    private BW4TClientConfig model;
    
    private boolean hasShownEpartnerWarning = false;
    
    /**
     * Create an UpdateEPartnerCount event handler.
     * @param newView The parent view.
     * @param model The model.
     */
    public UpdateEPartnerCount(final MainPanel newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;
    }
    
    /**
     * Executes action that needs to happen when the epartner table changes.
     *
     * @param e The action.
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        view.getEntityPanel().updateEPartnerCount(model.getAmountEPartner());
        if (model.getAmountEPartner() > model.getAmountBot()) {
            if (!hasShownEpartnerWarning) {
                hasShownEpartnerWarning = true;
                ScenarioEditor.getOptionPrompt().showMessageDialog(
                        view,
                        "You are using more e-Partners than bots, "
                        + "which might not be supported by your map.");
            }
        } else {
            hasShownEpartnerWarning = false;
        }
    }

}
