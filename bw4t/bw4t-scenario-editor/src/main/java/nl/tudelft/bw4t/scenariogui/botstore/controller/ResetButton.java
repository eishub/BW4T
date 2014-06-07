package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.agent.EntityType;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Handles actions of the resetbutton
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
        view.getSpeedSlider().setValue(view.getTempBotConfig().getBotSpeed());
        view.getSizeSlider().setValue(view.getTempBotConfig().getBotSize());
        view.getBatterySlider().setValue(view.getTempBotConfig().getBotBatteryCapacity());
        view.getSizeSlider().setEnabled(view.getTempBotConfig().getSizeOverloadHandicap());
        view.getBatterySlider().setEnabled(view.getTempBotConfig().isBatteryEnabled());
        view.getSpeedSlider().setEnabled(view.getTempBotConfig().getMoveSpeedHandicap());
        if(view.getTempBotConfig().getGripperHandicap()){
            view.getGripperCheckbox().setSelected(true);
            view.getNumberOfGrippersSlider().setEnabled(false);
        }
        else {
        	view.getGripperCheckbox().setSelected(false);
        	view.getNumberOfGrippersSlider().setEnabled(true);
        }
        view.getColorblindCheckbox().setSelected(view.getTempBotConfig().getColorBlindHandicap());
        view.getsizeoverloadCheckbox().setSelected(view.getTempBotConfig().getSizeOverloadHandicap());
        view.getmovespeedCheckbox().setSelected(view.getTempBotConfig().getMoveSpeedHandicap());
        view.getBatteryEnabledCheckbox().setSelected(view.getTempBotConfig().isBatteryEnabled());
        view.getNumberOfGrippersSlider().setValue(view.getTempBotConfig().getGrippers());
        view.getFileNameField().setText(view.getTempBotConfig().getFileName());
        view.getBotNameField().setText(view.getTempBotConfig().getBotName());
        view.getBatteryUseValueLabel().setText("0");
        view.getBotReferenceField().setText(view.getTempBotConfig().getReferenceName());
        view.getBotAmountTextField().setText(""+view.getTempBotConfig().getBotAmount());  
        if (view.getTempBotConfig().getBotController().equals(EntityType.HUMAN)){
        	view.getBotControllerSelector().setSelectedIndex(1);
        } else {
        	view.getBotControllerSelector().setSelectedIndex(0);
        }
        
    }
}
