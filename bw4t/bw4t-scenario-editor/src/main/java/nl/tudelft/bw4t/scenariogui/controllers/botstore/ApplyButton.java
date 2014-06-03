package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.botstore.BotEditorPanel;

/**
 * Handles actions of the applybutton
 *
 * @author Arun
 */
class ApplyButton implements ActionListener {
    /**
     * The BotEditorPanel to request components from.
     */
    private BotEditorPanel view;

    /**
     * Constructor.
     *
     * @param pview The BotEditorPanel in which the button listening
     *              to this listener is situated.
     */
    public ApplyButton(BotEditorPanel pview) {
        this.view = pview;
    }

    /**
     * Adds the given settings to the BotConfig-object,
     * making it ready to be used.
     *
     * @param ae The action event caused by clicking on the button.
     */
    public void actionPerformed(ActionEvent ae) {
    	String fileName = view.getFileNameField().getText();
    	String botName = view.getReferenceNameField().getText();
    	String nonAlphaNumericRegex = "^[a-zA-Z0-9_-]*$";
    	File f;
    	if (fileName.endsWith(".goal")) {
    		if (fileName.length() > 5) {
    			f = new File(fileName);
    			String name = fileName.substring(0, fileName.length() - 5);
    			if (name.matches(nonAlphaNumericRegex) 
    					|| f.exists()) {
	    			if (botName.length() > 0) {
	    				if (botName.matches(nonAlphaNumericRegex)) {
                            view.getDataObject().setBotName(view.getBotNameTextField().getText());
                            view.getDataObject().setBotController((BotConfig.Controller) view.getBotControllerType().getSelectedItem());
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

                            view.getDataObject().setFileName(fileName);
                            view.getDataObject().setReferenceName(botName);

                            ScenarioEditor.getOptionPrompt().showMessageDialog(view,
                                    "Bot configuration succesfully created.");
                        }
                        else {
                            ScenarioEditor.getOptionPrompt().showMessageDialog(view,
                                    "Please specify a reference name consisting "
                                            + "of valid alphanumeric characters."
                            );
                        }
                    }
                    else {
                        ScenarioEditor.getOptionPrompt().showMessageDialog(view,
                                "Please specify a reference name.");
                    }
                }
                else {
                    ScenarioEditor.getOptionPrompt().showMessageDialog(view, "Please specify a file name"
                            + " consisting of valid alphanumeric characters"
                            + " or use an existing file.");
                }
            }
            else {
                ScenarioEditor.getOptionPrompt().showMessageDialog(view, "Please specify a file name.");
            }
        }
        else {
            ScenarioEditor.getOptionPrompt().showMessageDialog(view, "The file name is invalid.\n"
                    + "File names should end in .goal.");
        }
    }
}
