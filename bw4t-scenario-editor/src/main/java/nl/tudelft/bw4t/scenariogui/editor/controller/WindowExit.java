package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

/**
 * The WindowExit class makes sure that on clicking the default exit button,
 * a warning pops up.
 * 
 * @version     0.1
 * @since       26-05-2014
 *
 */
class WindowExit extends WindowAdapter {
    
    private ScenarioEditor view;

    /**
     * Creates a WindowExit object.
     * @param newView The view.
     */
    public WindowExit(ScenarioEditor newView) {
        this.view = newView;
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        if (!showUnsavedDataConfirmation()) {
            view.closeScenarioEditor();
        }
    }

    /**
     * Shows a warning message if there is unsaved data.
     * @return Whether the warning message was shown.
     */
    private boolean showUnsavedDataConfirmation() {
        // Check if current config is different from last saved config
        if (view.getController().hasConfigBeenModified()) {
            int response = ScenarioEditor.getOptionPrompt().showConfirmDialog(
                    null,
                    "You have not saved your current configuration. \n"
                            +
                            "If you exit now, your changes will be lost. \n"
                            +
                            "Are you sure you want to exit the program? \n",
                    "",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            
            if (response == JOptionPane.YES_OPTION) {
                view.closeScenarioEditor();
            }
            return true;
        }
        return false;
    }
}
