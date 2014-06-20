package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Handles actions of the cancel button.
 */
public class CancelButton implements ActionListener {

    private BotEditorPanel view;

    /**
     * Constructor.
     * @param pview The BotEditorPanel to be disposed.
     */
    public CancelButton(BotEditorPanel pview) {
        this.view = pview;
    }
    
    /**
     * Dispose the BotEditorPanel.
     * @param ae The action event causing the method to be invoked.
     */
    public void actionPerformed(ActionEvent ae) {
        view.getBotController().getBotConfig().setBotSpeed(100);
        view.getBotController().getBotConfig().setBotSize(2);
        view.getBotEditor().dispose();
    }
}
