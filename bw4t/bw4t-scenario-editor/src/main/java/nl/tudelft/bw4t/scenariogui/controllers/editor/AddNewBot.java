package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to create a new bot.
 */
class AddNewBot implements ActionListener {

    /**
     * The <code>MainPanel</code> serving as the content pane.
     */
    private MainPanel view;

    /**
     * Create an AddNewBot event handler.
     *
     * @param newView The parent view.
     */
    public AddNewBot(final MainPanel newView) {
        this.view = newView;
    }

    /**
     * Listens to the add a new bot button.
     * Executes action that needs to happen when the "New bot" button is
     * pressed.
     *
     * @param ae The action event.
     */
    public void actionPerformed(final ActionEvent ae) {
        Object[] newBotObject = {"Unnamed Bot", "Agent", 1};
        view.getEntityPanel().getBotTableModel().addRow(newBotObject);
    }

}
