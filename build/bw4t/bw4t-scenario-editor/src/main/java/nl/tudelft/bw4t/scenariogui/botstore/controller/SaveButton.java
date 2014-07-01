package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.util.FileUtils;

/**
 * Handles actions of the save button.
 */
public class SaveButton implements ActionListener {

    private static final String GOAL_EXTENSION = ".goal";
    private static final String ALPHA_NUMERIC_REGEX = "^[ a-zA-Z0-9_-]*$";

    private BotEditorPanel view;

    /**
     * Constructor.
     *
     * @param pview
     *            The BotEditorPanel in which the button listening to this
     *            listener is situated.
     */
    public SaveButton(BotEditorPanel pview) {
        this.view = pview;
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

        view.getBotController().updateConfig(view);
        updateBotTableFromCurrentModel();
        
        view.getBotEditor().dispose();
    }
    
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

    private void updateBotTableFromCurrentModel() {
        view.getMainPanel().refreshBotTableModel();
    }
}
