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
 * @author Arun
 */
class ApplyButton implements ActionListener {
	/**
	 * The BotEditorPanel to request components from.
	 */
    private BotEditorPanel view;
    /**
     * The MainPanel to request components from.
     */
    private MainPanel mp;
    
    /**
     * Constructor.
     * @param pview The BotEditorPanel in which the button listening
     * to this listener is situated.
     */
    public ApplyButton(BotEditorPanel pview) {
        this.view = pview;
        BotEditor be = (BotEditor) SwingUtilities.getWindowAncestor(view);
        mp = be.getParent();
    }
    /**
     * Adds the given settings to the BotConfig-object, 
     * making it ready to be used.
     * @param ae The action event caused by clicking on the button.
     */
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
        System.out.println("Getting data object completed. Bot config size: " + mp.getBotConfig().size());
        System.out.println("New config placed at index: " + index);
    }
}