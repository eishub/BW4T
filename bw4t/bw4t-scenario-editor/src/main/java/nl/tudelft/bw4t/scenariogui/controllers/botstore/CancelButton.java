package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the cancelbutton
 */
class CancelButton implements ActionListener {
    private BotEditorPanel view;

    public CancelButton(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {
        view.cancelAction();
    }
}
