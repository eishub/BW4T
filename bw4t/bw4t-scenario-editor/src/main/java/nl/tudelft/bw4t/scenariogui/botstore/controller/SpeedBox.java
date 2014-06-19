package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
/**
 * Enables the speed slider.
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
    
    /**
     * Performs the necessary action of the speed-enabling checkbox.
     * @param arg0 The action event caused by checking or unchecking
     * the checkbox.
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
<<<<<<< HEAD
        JSlider speedSlider = view.getSpeedSlider();
        if (view.getmovespeedCheckbox().isSelected()) {
            speedSlider.setEnabled(true);
        }
        else {
            speedSlider.setEnabled(false);
            speedSlider.setValue(100);
        }
        view.getTempBotConfig().setMoveSpeedHandicap(view.getmovespeedCheckbox().isSelected());
=======
    	view.setSpeedSliderEnabled(view.getMoveSpeedHandicap());
>>>>>>> master
    }
}

