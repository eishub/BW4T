package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.panel.gui.ConfigurationPanel;

/**
 * Handles the event to exit the program.
 * <p>
 * 
 * @version 0.1
 * @since 12-05-2014
 */
class MenuOptionExit extends AbstractMenuOption {

	/**
	 * Constructs a new menu option exit object.
	 * 
	 * @param view
	 *            The view.
	 * @param mainView
	 *            The controlling main view.
	 * @param model           
	 *            The model.
	 */
	public MenuOptionExit(final MenuBar view,
			final ScenarioEditorController mainView, BW4TClientConfig model) {
		super(view, mainView, model);
	}

	/**
	 * Gets called when the exit button is pressed.
	 * 
	 * @param e
	 *            The action event.
	 */
	public void actionPerformed(final ActionEvent e) {
		ConfigurationPanel configPanel = super.getController().getMainView()
				.getMainPanel().getConfigurationPanel();

		// Check if current config is different from last saved config
		if (!configPanel.getOldValues().equals(configPanel.getCurrentValues())
				|| !super.getModel().compareBotConfigs(super.getModel().getOldBots())
				|| !super.getModel().compareEpartnerConfigs(
								super.getModel().getOldEpartners())) {
			// Check if user wants to save current configuration
			int response = ScenarioEditor.getOptionPrompt().showConfirmDialog(
					null, ScenarioEditorController.CONFIRM_SAVE_TXT, "",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (response == JOptionPane.YES_OPTION) {
				saveFile();
				super.getController().getMainView().getMainPanel()
						.getConfigurationPanel().updateOldValues();
				super.getModel().updateBotConfigs();
				super.getModel().updateEpartnerConfigs();
				getController().getMainView().closeScenarioEditor();
			} else {
				getController().getMainView().closeScenarioEditor();
			}
		} else {
			int response = ScenarioEditor.getOptionPrompt().showConfirmDialog(
					null, "Are you sure you want to exit the program?", "",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (response == JOptionPane.YES_OPTION) {
				getController().getMainView().closeScenarioEditor();
			}
		}
		getController().getMainView().closeScenarioEditor();
	}
}
