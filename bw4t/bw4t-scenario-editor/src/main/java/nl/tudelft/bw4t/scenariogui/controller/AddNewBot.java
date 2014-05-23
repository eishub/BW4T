package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to create a new bot.
 */
public class AddNewBot implements ActionListener {

    /** The <code>MainPanel</code> serving as the content pane.*/
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
     * @param ae The action event.
     */
    public void actionPerformed(final ActionEvent ae) {
        view.getEntityPanel().addBotAction();
        Object[] newBotObject = {"Unnamed Bot", "Agent", 1};
        ((DefaultTableModel) view.getEntityPanel().getBotTable().getModel()).addRow(newBotObject);
    }

}
