package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the resetbutton
 * @author Arun
 */
class ResetButton implements ActionListener {
    /**
     * The panel containing the button.
     */
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The panel of the jump box.
     */
    public ResetButton(BotEditorPanel pview) {
        this.view = pview;
    }
    /**
     * Resets all parts of the bot editor panel
     * to their standard value.
     * @param ae The action event causing this.
     */
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
        view.getFileNameField().setText(".goal");
        view.getBotNameField().setText("");
        view.getBatteryUseValueLabel().setText("0");
        view.getBotReferenceField().setText("");
        view.getBotAmountTextField().setText("1");
        
    }
}
