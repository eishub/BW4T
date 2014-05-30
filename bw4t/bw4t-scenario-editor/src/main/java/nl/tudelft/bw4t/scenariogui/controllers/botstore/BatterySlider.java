package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the batteryslider
 * @author Arun
 */
class BatterySlider implements MouseListener {
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

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }
}
