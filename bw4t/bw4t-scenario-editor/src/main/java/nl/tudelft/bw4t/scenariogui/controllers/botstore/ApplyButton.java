package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles actions of the applybutton
 */
class ApplyButton implements ActionListener {
    private BotEditorPanel view;
    private MainPanel mp;

    public ApplyButton(BotEditorPanel view) {
        this.view = view;
        BotEditor be = (BotEditor) SwingUtilities.getWindowAncestor(view);
        mp = be.getParent();
    }

    public void actionPerformed(ActionEvent ae) {
        view.getDataObject().setBotSize(view.getSizeSlider().getValue());
        view.getDataObject().setBotSpeed(view.getSpeedSlider().getValue());
        view.getDataObject().setBotBatteryCapacity(view.getBatterySlider().getValue());
        view.getDataObject().setColorBlindHandicap(view.getColorblindCheckbox().isEnabled());
        view.getDataObject().setGripperHandicap(view.getGripperCheckbox().isEnabled());
        view.getDataObject().setMoveSpeedHandicap(view.getmovespeedCheckbox().isEnabled());
        view.getDataObject().setSizeOverloadHandicap(view.getsizeoverloadCheckbox().isEnabled());
        BotConfig data = view.getDataObject();
        int index = mp.getEntityPanel().getSelectedBotRow();
        // Overwrite the existing config
        // (there is always a basic config present for every bot).
        mp.addBotConfig(index, data);
        // Debugging purposes.
        System.out.println("Getting data object completed. Bot config size: " + mp.getBotConfig().size());
        System.out.println("New config placed at index: " + index);
    }
}
