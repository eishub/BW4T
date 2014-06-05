package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

/**
 * Handles the event to delete a bot.
 * 
 * @version     0.1                
 * @since       12-05-2014        
 */
class DeleteBot implements ActionListener {

    /**
     * The <code>MainPanel</code> serving as the content pane.
     */
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
     * Executes action that needs to happen when the "Delete bot" button is
     * pressed.
     * Gets called when the delete button is pressed.
     *
     * @param ae The action event.
     */
    public void actionPerformed(final ActionEvent ae) {
        int row = view.getEntityPanel().getSelectedBotRow();

        if (row == -1) {
            ScenarioEditor.getOptionPrompt().showMessageDialog(null, "Please select the bot you want to delete.");
            return;
        }

        int response = ScenarioEditor.getOptionPrompt().showConfirmDialog(null,
                "Are you sure you want to delete this bot?", "",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            view.getEntityPanel().getBotTableModel().removeRow(row);
            view.getEntityPanel().getBotConfigs().remove(row);
        }
    }
}
