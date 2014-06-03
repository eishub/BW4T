package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the batteryslider
 * @author Arun
 */
class BatterySlider extends MouseAdapter {
    /**
     * The BotEditorPanel to get components from.
     */
    @SuppressWarnings("unused")
    private BotEditorPanel view;
    /**
     * Constructor.
     * @param pview The BotEditorPanel containing this slider.
     */
    public BatterySlider(BotEditorPanel pview) {
        this.view = pview;
    }
}
