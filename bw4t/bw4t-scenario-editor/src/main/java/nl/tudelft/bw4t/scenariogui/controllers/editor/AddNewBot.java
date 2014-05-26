package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenarioeditor.BotConfig;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to create a new bot.
 * <p>
 * @author        
 * @version     0.1                
 * @since       12-05-2014        
 */
class AddNewBot implements ActionListener {

    /**
     * The <code>MainPanel</code> serving as the content pane.
     */
    private MainPanel view;
    
    /**
     * Keeps track of the amount of bots created
     */
    private int botCount;

    /**
     * Create an AddNewBot event handler.
     *
     * @param newView The parent view.
     */
    public AddNewBot(final MainPanel newView) {
        this.view = newView;
        botCount = 0;
    }

    /**
     * Listens to the add a new bot button.
     * Executes action that needs to happen when the "New bot" button is
     * pressed. Gives default name of "Bot \<n\>" where \<n\> is the n'th bot created.  
     *
     * @param ae The action event.
     */
    public void actionPerformed(final ActionEvent ae) {
        botCount++;
        Object[] newBotObject = {"Bot " + botCount, "Agent", 1};
        view.getEntityPanel().getBotTableModel().addRow(newBotObject);
        view.addBotConfig(new BotConfig());
    }

}
