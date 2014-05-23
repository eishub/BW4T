package nl.tudelft.bw4t.scenariogui.controllers.entitypanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to modify a bot.
 */
public class ModifyBot implements ActionListener {

    /**
     * The <code>MainPanel</code> serving as the content pane.
     */
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
     * Executes action that needs to happen when the "Modify bot" button is
     * pressed. TODO Open BotStore window
     *
     * @param ae The action.
     */
    public void actionPerformed(final ActionEvent ae) {
        System.out.println("Go to Bot Store (modify)");
    }
}
