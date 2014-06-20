package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Handles the event to create a new bot.    
 */
class AddNewStandardBotGripper implements ActionListener {

    private MainPanel view;
    
    private int botCount;
    
    private BW4TClientConfig model;

    /**
     * Create an AddNewStandardBotGripper event handler.
     *
     * @param newView The parent view.
     * @param model The model.
     */
    public AddNewStandardBotGripper(final MainPanel newView, BW4TClientConfig model) {
        this.view = newView;
        this.model = model;
    }

    /**
     * Listens to the add gripper bot button.
     * Executes action that needs to happen when the "Gripper Bot" button is
     * pressed. Gives default name of "Gripper Bot &lt;n&gt;" where &lt;n&gt; is the n'th bot created.  
     *
     * @param ae The action event.
     */
    public void actionPerformed(final ActionEvent ae) {
        BotConfig newBotConfig = new BotConfig();
        newBotConfig.setColorBlindHandicap(true);
        newBotConfig.setFileName(BotConfig.DEFAULT_GOAL_FILENAME);
        newBotConfig.setReferenceName(BotConfig.DEFAULT_GOAL_FILENAME_REFERENCE);
        
        botCount = model.getBots().size() + 1;
        newBotConfig.setBotName("Gripper Bot " + botCount);
        
        model.addBot(newBotConfig);
        view.getEntityPanel().getBotTableModel().update();
    }

}
