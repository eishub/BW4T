package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.agent.EntityType;
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
        view.getSpeedSlider().setValue(view.getDataObject().getBotSpeed());
        view.getSizeSlider().setValue(view.getDataObject().getBotSize());
        view.getBatterySlider().setValue(view.getDataObject().getBotBatteryCapacity());
        view.getSizeSlider().setEnabled(view.getDataObject().getSizeOverloadHandicap());
        view.getBatterySlider().setEnabled(view.getDataObject().isBatteryEnabled());
        view.getSpeedSlider().setEnabled(view.getDataObject().getMoveSpeedHandicap());
        if(view.getDataObject().getGripperHandicap()){
            view.getGripperCheckbox().setSelected(true);
            view.getNumberOfGrippersSlider().setEnabled(false);
        }
        else {
        	view.getGripperCheckbox().setSelected(false);
        	view.getNumberOfGrippersSlider().setEnabled(true);
        }
        view.getColorblindCheckbox().setSelected(view.getDataObject().getColorBlindHandicap());
        view.getsizeoverloadCheckbox().setSelected(view.getDataObject().getSizeOverloadHandicap());
        view.getmovespeedCheckbox().setSelected(view.getDataObject().getMoveSpeedHandicap());
        view.getBatteryEnabledCheckbox().setSelected(view.getDataObject().isBatteryEnabled());
        view.getNumberOfGrippersSlider().setValue(view.getDataObject().getGrippers());
        view.getFileNameField().setText(view.getDataObject().getFileName());
        view.getBotNameField().setText(view.getDataObject().getBotName());
        view.getBatteryUseValueLabel().setText("0");
        view.getBotReferenceField().setText(view.getDataObject().getReferenceName());
        view.getBotAmountTextField().setText(""+view.getDataObject().getBotAmount());  
        if (view.getDataObject().getBotController().equals(EntityType.HUMAN)){
        	view.getBotControllerSelector().setSelectedIndex(1);
        } else {
        	view.getBotControllerSelector().setSelectedIndex(0);
        }
        
    }
}
