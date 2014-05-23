package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to delete a bot.
 */
public class DeleteBot implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
    private MainPanel view;

    /**
     * Create an DeleteBot event handler.
     *
     * @param newView The parent view.
     */
    public DeleteBot(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Gets called when the delete button is pressed.
     * @param ae The action event.
     */
    public void actionPerformed(final ActionEvent ae) {
        view.getEntityPanel().deleteBotAction();
    }
}
