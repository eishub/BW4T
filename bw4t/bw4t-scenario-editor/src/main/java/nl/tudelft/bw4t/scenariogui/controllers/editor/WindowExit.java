package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;

/**
 * The WindowExit class makes sure that on clicking the default exit button,
 * a warning pops up.
 * <p>
 * @author 		Seu Man To
 * @version 	0.1
 * @since		26-05-2014
 *
 */
class WindowExit extends WindowAdapter {
	
	/**
	 * The view being controlled.
	 */
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
		ConfigurationPanel configPanel = 
				view.getController().getMainView().getMainPanel().getConfigurationPanel();
		
		// Check if current config is different from last saved config
        if (!configPanel.getOldValues().equals(configPanel.getCurrentValues())) {
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
        }
        else {
        	int response = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to exit the program?",
                    "",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

        	if (response == JOptionPane.YES_OPTION) {
                view.closeScenarioEditor();
        	}

        }
	}
}
