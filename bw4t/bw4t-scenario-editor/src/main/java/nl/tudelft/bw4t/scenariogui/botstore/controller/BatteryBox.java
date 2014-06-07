package nl.tudelft.bw4t.scenariogui.botstore.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
/**
 * Handles actions of the battery checkbox.
 */
public class BatteryBox implements ActionListener {
    /**
     * The BotEditorPanel to get components from.
     */
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The BotEditorPanel containing the source
     * of the actions.
     */
    public BatteryBox(BotEditorPanel pview) {
        this.view = pview;
    }
    /**
     * Performs the necessary action of the battery-enabling checkbox.
     * @param e The action event caused by checking or unchecking
     * the checkbox.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JSlider batterySlider = view.getBotBatteryCapacity();
        if (view.isBatteryEnabled()) {
            batterySlider.setEnabled(true);
            view.getBatteryUseValueLabel().setText("0,1");
        }
        else {
            batterySlider.setEnabled(false);
            view.getBatteryUseValueLabel().setText("0");
        }
        view.getDataObject().setBatteryEnabled(view.isBatteryEnabled());
    }
}
