package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the colorblindcheckbox
 * @author Arun
 */
class ColorBox implements ActionListener {
    /**
     * The panel containing the check box.
     */
    @SuppressWarnings("unused")
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The panel with the checkbox in it.
     */
    public ColorBox(BotEditorPanel pview) {
        this.view = pview;
    }
    /**
     * Makes the selected bot color blind.
     * NOTE: This is very hard to do because the
     * colors are very deep in the EIS.
     * @param ae The action event invoking this method.
     */
    public void actionPerformed(ActionEvent ae) {
        view.getDataObject().setColorBlindHandicap(view.getColorblindCheckbox().isSelected());
    }
}
