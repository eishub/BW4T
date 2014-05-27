package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the applybutton
 */
class ApplyButton implements ActionListener {
    private BotEditorPanel view;

    public ApplyButton(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {
        view.applyAction();
    }
}
