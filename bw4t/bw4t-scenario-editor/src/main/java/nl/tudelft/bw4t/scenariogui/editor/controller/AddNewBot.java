package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.agent.EntityType;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

/**
 * Handles the event to create a new bot.
 * 
 * @version     0.1                
 * @since       12-05-2014        
 */
class AddNewBot implements ActionListener {

    private MainPanel view;
    
    private int botCount;
    
    private BW4TClientConfig model;

    /**
     * Create an AddNewBot event handler.
     *
     * @param newView The parent view.
     * @param model The model.
     */
    public AddNewBot(final MainPanel newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;
    }

    /**
     * Listens to the add a new bot button.
     * Executes action that needs to happen when the "New bot" button is
     * pressed. Gives default name of "Bot &lt;n&gt;" where &lt;n&gt; is the n'th bot created.  
     *
     * @param ae The action event.
     */
    public void actionPerformed(final ActionEvent ae) {
        botCount = model.getBots().size() + 1;
        Object[] newBotObject = {"Bot" + " " + botCount, EntityType.AGENT.toString(), 1};
        BotConfig newBotConfig = new BotConfig();

        newBotConfig.setFileName(BotConfig.DEFAULT_GOAL_FILENAME);
        newBotConfig.setReferenceName(BotConfig.DEFAULT_GOAL_FILENAME_REFERENCE);
        newBotConfig.setBotName("Bot " + botCount);
        
        model.addBot(newBotConfig);
        view.getEntityPanel().getBotTableModel().addRow(newBotObject);
    }

}
