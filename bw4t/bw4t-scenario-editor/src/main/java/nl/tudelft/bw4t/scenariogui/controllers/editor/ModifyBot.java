package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditor;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to modify a bot.
 * <p>
 * @author      Seu Man To 
 * @version     0.1                
 * @since       12-05-2014        
 */
class ModifyBot implements ActionListener {

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
     * pressed. 
     * TODO Open BotStore window
     * Gets called when the bot is modified.
     *
     * @param ae The action event.
     */
    public void actionPerformed(final ActionEvent ae) {
    	int row = view.getEntityPanel().getSelectedBotRow();
    	
    	if (row == -1) {
            ScenarioEditor.getOptionPrompt().showMessageDialog(null, "Please select the bot you want to modify.");
        } 
    	new BotEditor(view);
    }
}
