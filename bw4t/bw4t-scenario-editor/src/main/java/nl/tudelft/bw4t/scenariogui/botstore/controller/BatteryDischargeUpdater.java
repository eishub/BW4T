package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Handles actions of the size slider.
 */
public class BatteryDischargeUpdater extends MouseAdapter implements ActionListener {

    private BotEditorPanel view;
    
    /**
     * Constructor.
     * @param pview The panel containing the slider.
     */
    public BatteryDischargeUpdater(BotEditorPanel pview) {
        this.view = pview;
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        view.updateDischargeRate();
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        view.updateDischargeRate();
    }

}
