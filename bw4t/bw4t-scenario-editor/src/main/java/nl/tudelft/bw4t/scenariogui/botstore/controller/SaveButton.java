package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.SwingUtilities;

import nl.tudelft.bw4t.agent.EntityType;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.util.FileUtils;

/**
 * Handles actions of the apply button.
 */
class SaveButton implements ActionListener {

	private static final String GOAL_EXTENSION = ".goal";
    private static final String ALPHA_NUMERIC_REGEX = "^[ a-zA-Z0-9_-]*$";

    private BotEditorPanel view;

	private MainPanel mp;

	/**
	 * Constructor.
	 * 
	 * @param pview
	 *            The BotEditorPanel in which the button listening to this
	 *            listener is situated.
	 */
	public SaveButton(BotEditorPanel pview) {
		this.view = pview;
		BotEditor be = (BotEditor) SwingUtilities.getWindowAncestor(view);
		mp = be.getParent();
	}

	/**
	 * Adds the given settings to the BotConfig-object, making it ready to be
	 * used.
	 * 
	 * @param ae
	 *            The action event caused by clicking on the button.
	 */
	public void actionPerformed(ActionEvent ae) {
        String fileName = view.getFileNameField().getText();
        String botName = view.getBotNameField().getText();
	    if (!hasValidExtensionAndNonEmptyFileName(fileName, GOAL_EXTENSION)
	            || !isAlphaNumericFileName(fileName)
	            || !isValidBotName(botName))
	        return;
	    fillModelFromPartsOfViewWithoutListener();
	    
	    /** Use the current temp bot config as the real one: */
        view.getModel().setBot(view.getBotEditor().getRow(), view.getTempBotConfig());
        
        updateBotTableFromCurrentModel();
        view.getBotEditor().dispose();
	}
	
	/**
	 * Fills the fields of the model with its corresponding parts of the view, but only
	 * if those parts of the view don't have a listener yet.
	 */
	private void fillModelFromPartsOfViewWithoutListener() {
	    view.getTempBotConfig().setBotName(
                view.getBotNameField().getText());
        view.getTempBotConfig().setBotController(
                EntityType.getType((String) view.getBotControllerSelector().getSelectedItem()));
        view.getTempBotConfig().setBotAmount(
                        Integer.parseInt(view.getBotAmountTextField().getText()));
        view.getTempBotConfig().setReferenceName(
                view.getBotReferenceField().getText());
	}
	
	/**
	 * Returns whether this file name has the correct extension and a non-empty file
	 * name. A message box is shown if either is not the case.
	 * @param fileName The file name including extension.
	 * @param extensionReq The required extension of the file name.
	 * @return Whether this file name has the required extension and has a non-empty
	 * file name.
	 */
	private boolean hasValidExtensionAndNonEmptyFileName(String fileName, String extensionReq) {
        if (!FileUtils.hasRequiredExtension(fileName, extensionReq)) {
            ScenarioEditor.getOptionPrompt().showMessageDialog(view,
                    "The file name is invalid.\nFile names should end in " + extensionReq + ".");
            return false;
        }
        if (fileName.length() <= extensionReq.length()) {
            ScenarioEditor.getOptionPrompt().showMessageDialog(view,
                    "Please specify a file name.");
            return false;
        }
        return true;
	}
	
	/**
	 * Returns whether the file name is alpha numeric. If not, a message box
	 * is shown.
	 * @param fileName The file name including extension.
	 * @param extension The extension of the file name.
	 * @return Whether the file name is alpha numeric.
	 */
	private boolean isAlphaNumericFileName(String fileName) {
        if (!FileUtils.getFileNameWithoutExtension(fileName).
                matches(ALPHA_NUMERIC_REGEX)
                && !new File(fileName).exists()) {
            ScenarioEditor
            .getOptionPrompt()
            .showMessageDialog(view,
                    "Please specify a file name"
                            + " consisting of valid alphanumeric characters"
                            + " or use an existing file.");
            return false;
        }
        return true;
	}

	/**
	 * Checks whether a valid bot name has been selected before saving.
	 * Show a message box if this is not the case.
	 * @return Whether a valid bot name has been selected.
	 */
    private boolean isValidBotName(String botName) {
        if (botName.length() == 0) {
            ScenarioEditor.getOptionPrompt().showMessageDialog(
                    view, "Please specify a reference name.");
            return false;
        }
        if (!botName.matches(ALPHA_NUMERIC_REGEX)) {
            ScenarioEditor
            .getOptionPrompt()
            .showMessageDialog(
                    view,
                    "Please specify a reference name consisting "
                            + "of valid alphanumeric characters.");
            return false;
        }
        return true;
    }
    
    /**
     * Updates the bot list in the scenario editor.
     */
    private void updateBotTableFromCurrentModel() {
        view.getBotEditor().getParent().getEntityPanel().getBotTableModel()
                .setRowCount(0);
        int rows = view.getModel().getBots().size();
        for (int i = 0; i < rows; i++) {
            BotConfig botConfig = view.getModel().getBot(i);
            Object[] newBotObject = {botConfig.getBotName(),
                    botConfig.getBotController().toString(),
                    botConfig.getBotAmount()};
            view.getBotEditor().getParent().getEntityPanel().getBotTableModel()
                    .addRow(newBotObject);
        }
    }
	
}
