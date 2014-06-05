package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
/**
 * Enables the speed slider.
 * @author Tim
 */
public class SpeedBox implements ActionListener {
    /**
     * The panel containing the check box.
     */
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The panel of the jump box.
     */
    public SpeedBox(BotEditorPanel pview) {
        this.view = pview;
    }
    @Override
    public void actionPerformed(ActionEvent arg0) {
        JSlider speedSlider = view.getSpeedSlider();
        if (view.getmovespeedCheckbox().isSelected()) {
            speedSlider.setEnabled(true);
        }
        else {
            speedSlider.setEnabled(false);
            speedSlider.setValue(100);
        }
        view.getDataObject().setMoveSpeedHandicap(view.getmovespeedCheckbox().isSelected());
    }
}
