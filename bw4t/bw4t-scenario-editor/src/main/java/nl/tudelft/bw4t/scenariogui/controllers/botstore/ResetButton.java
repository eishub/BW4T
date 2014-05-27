package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the resetbutton
 */
class ResetButton implements ActionListener {
    private BotEditorPanel view;

    public ResetButton(BotEditorPanel view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent ae) {
        view.getSpeedSlider().setValue(100);
        view.getSizeSlider().setValue(2);
        view.getBatterySlider().setValue(0);
        view.getSizeSlider().setEnabled(false);
        view.getBatterySlider().setEnabled(false);
        view.getSpeedSlider().setEnabled(false);
        view.getNumberOfGrippersSlider().setEnabled(true);
        view.getGripperCheckbox().setSelected(false);
        view.getColorblindCheckbox().setSelected(false);
        view.getsizeoverloadCheckbox().setSelected(false);
        view.getmovespeedCheckbox().setSelected(false);
        view.getBatteryEnabledCheckbox().setSelected(false);
        view.getNumberOfGrippersSlider().setValue(1);
    }
}
