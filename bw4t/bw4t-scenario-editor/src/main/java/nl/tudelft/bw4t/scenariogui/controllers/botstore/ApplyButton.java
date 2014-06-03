package nl.tudelft.bw4t.scenariogui.controllers.botstore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.SwingUtilities;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
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
    	String fileName = view.getFileNameField().getText();
    	String botName = view.getBotNameField().getText();
    	String nonAlphaNumericRegex = "^[ a-zA-Z0-9_-]*$";
    	File f;
    	if (fileName.endsWith(".goal")) {
    		if (fileName.length() > 5) {
    			f = new File(fileName);
    			String name = fileName.substring(0, fileName.length() - 5);
    			if (name.matches(nonAlphaNumericRegex) 
    					|| f.exists()) {
	    			if (botName.length() > 0) {
	    				if (botName.matches(nonAlphaNumericRegex)) {
	    					view.getDataObject().setBotGripperCapacity(
	    							view.getNumberOfGrippersSlider().getValue());
		    				view.getDataObject().setBotSize(view.getSizeSlider().getValue());
				    		view.getDataObject().setBotSpeed(view.getSpeedSlider().getValue());
				    		view.getDataObject().setBotBatteryCapacity(
				    				view.getBatterySlider().getValue());
				    		view.getDataObject().setColorBlindHandicap(
				    				view.getColorblindCheckbox().isEnabled());
				    		view.getDataObject().setGripperHandicap(
				    				view.getGripperCheckbox().isEnabled());
				    		view.getDataObject().setMoveSpeedHandicap(
				    				view.getmovespeedCheckbox().isEnabled());
				    		view.getDataObject().setSizeOverloadHandicap(
				    				view.getsizeoverloadCheckbox()
				    				.isEnabled());
				    		view.getDataObject().setFileName(fileName);
				    		view.getDataObject().setReferenceName(botName);
				        	BotConfig data = view.getDataObject();
				        	int index = mp.getEntityPanel().getSelectedBotRow();
				        	// Overwrite the existing config
				        	// (there is always a basic config present for every bot).
				        	mp.getEntityPanel().getBotConfigs().set(index, data);
				        	ScenarioEditor.getOptionPrompt().showMessageDialog(view, 
		        					"Bot configuration succesfully created.");
	    				}
	    				else {
	    					ScenarioEditor.getOptionPrompt().showMessageDialog(view, 
		        					"Please specify a reference name consisting "
		        					+ "of valid alphanumeric characters.");
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
