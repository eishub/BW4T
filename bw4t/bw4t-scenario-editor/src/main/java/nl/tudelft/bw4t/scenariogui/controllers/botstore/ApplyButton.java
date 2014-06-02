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
     * Constructor.
     * @param pview The BotEditorPanel in which the button listening
     * to this listener is situated.
     */
    public ApplyButton(BotEditorPanel pview) {
        this.view = pview;
    }
    /**
     * Adds the given settings to the BotConfig-object, 
     * making it ready to be used.
     * @param ae The action event caused by clicking on the button.
     */
    public void actionPerformed(ActionEvent ae) {
    	view.getDataObject().setBotName(view.getBotNameTextField().getText());
		view.getDataObject().setBotController((String) view.getBotControllerType().getSelectedItem());
		view.getDataObject().setBotAmount(Integer.parseInt(view.getBotAmountTextField().getText()));
        view.getDataObject().setBotSize(view.getSizeSlider().getValue());
        view.getDataObject().setBotSpeed(view.getSpeedSlider().getValue());
        view.getDataObject().setBotBatteryCapacity(view.getBatterySlider().getValue());
        view.getDataObject().setGrippers(view.getNumberOfGrippersSlider().getValue());
        view.getDataObject().setBatteryEnabled(view.getBatteryEnabledCheckbox().isSelected());
        view.getDataObject().setColorBlindHandicap(view.getColorblindCheckbox().isSelected());
        view.getDataObject().setGripperHandicap(view.getGripperCheckbox().isSelected());
        view.getDataObject().setMoveSpeedHandicap(view.getmovespeedCheckbox().isSelected());
        view.getDataObject().setSizeOverloadHandicap(view.getsizeoverloadCheckbox().isSelected());
    }
}
