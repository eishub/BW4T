package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to modify a bot.
 */
public class ModifyBot implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
    private MainPanel view;

    /**
     * Create an ModifyBot event handler.
     *
     * @param newView The parent view.
     */
    public ModifyBot(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Listens to the modify bot button.
     * @param ae The action.
     */
    public void actionPerformed(final ActionEvent ae) {
        view.getEntityPanel().modifyBotAction();
    }
}
