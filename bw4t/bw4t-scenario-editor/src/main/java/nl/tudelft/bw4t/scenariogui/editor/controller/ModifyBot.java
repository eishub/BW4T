package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Handles the event to modify a bot.
 *
 * @version     0.1
 * @since       12-05-2014        
 */
class ModifyBot implements ActionListener {

    private MainPanel view;
    
    private BW4TClientConfig model;

    /**
     * Create an ModifyBot event handler.
     *
     * @param newView The parent view.
     * @param model The model.
     */
    public ModifyBot(final MainPanel newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;
    }

    /**
     * Executes action that needs to happen when the "Modify bot" button is
     * pressed.
     *
     * @param ae The action.
     */
    public void actionPerformed(final ActionEvent ae) {
        int row = view.getEntityPanel().getSelectedBotRow();

        if (row == -1) {
            ScenarioEditor.getOptionPrompt().showMessageDialog(null, "Please select the bot you want to modify.");
            return;
        }
        new BotEditor(view, row, model);
        
        view.getEntityPanel().setBotStore(true);
    }
}
