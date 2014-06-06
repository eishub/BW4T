package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Handles the event to show the dropdown menu with the standard bots.
 * 
 * @version     0.1                
 * @since       12-05-2014        
 */
class BotDropDownButton implements ActionListener {

    private MainPanel view;

    /**
     * Create the menu drop down listener and set the view.
     *
     * @param newView The parent view, this is the JPanel where the button is on.
     */
    public BotDropDownButton(MainPanel newView) {
        this.view = newView;
    }

    /**
     * Handle the event by showing the dropdown menu
     *
     * @param ae The event
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        view.getEntityPanel().showBotDropDown();
    }
}
