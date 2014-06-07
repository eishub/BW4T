package nl.tudelft.bw4t.scenariogui.botstore.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles actions of the cancelbutton
 */
class CancelButton implements ActionListener {
    /**
     * The GUI to be disposed.
     */
    private BotEditorPanel view;

    /**
     * Constructor.
     * @param pview The GUI to be disposed.
     */
    public CancelButton(BotEditorPanel pview) {
        this.view = pview;
    }
    /**
     * Dispose the BotEditor GUI.
     * @param ae the action event causing the method to be invoked.
     */
    public void actionPerformed(ActionEvent ae) {
        view.dispose();
    }
}
