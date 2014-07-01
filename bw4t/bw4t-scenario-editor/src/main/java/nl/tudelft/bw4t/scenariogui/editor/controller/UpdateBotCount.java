package nl.tudelft.bw4t.scenariogui.editor.controller;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Handles the event to update the bot count.
 * 
 * @version 0.1
 * @since 05-06-2014
 */
public class UpdateBotCount implements TableModelListener {
    
    private MainPanel view;
    
    private BW4TClientConfig model;
    
    /**
     * Create an UpdateBotCount event handler.
     * @param newView The parent view.
     * @param model The model.
     */
    public UpdateBotCount(final MainPanel newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;
    }
    
    /**
     * Executes action that needs to happen when the bot table changes.
     *
     * @param e The action.
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        view.getEntityPanel().updateBotCount(model.getAmountBot());
    }
}
