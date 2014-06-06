package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.DefaultConfigurationValues;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.panel.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.panel.gui.EntityPanel;

/**
 * Handles the event to start a new file.
 * <p>
 *
 * @version 0.1
 * @since 12-05-2014
 */
class MenuOptionNew extends AbstractMenuOption {

	/**
	 * Constructs a new menu option new object.
	 *
	 * @param view
	 *            The view.
	 * @param mainView
	 *            The controlling main view.
	 * @param model
	 *            The model.
	 */
	public MenuOptionNew(final MenuBar view,
			final ScenarioEditorController mainView, BW4TClientConfig model) {
		super(view, mainView, model);
	}

	/**
	 * Gets called when the new file menu item is pressed.
	 *
	 * @param e
	 *            The action event.
	 */
	public void actionPerformed(final ActionEvent e) {
		ConfigurationPanel configPanel = getController().getMainView()
				.getMainPanel().getConfigurationPanel();
		EntityPanel entityPanel = getController().getMainView()
				.getMainPanel().getEntityPanel();

		// Check if current config is different from last saved config
		if (getController().hasConfigBeenModified()) {
			// Check if user wants to save current configuration
			int response = ScenarioEditor.getOptionPrompt().showConfirmDialog(
					null, ScenarioEditorController.CONFIRM_SAVE_TXT, "",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (response == JOptionPane.YES_OPTION) {
				saveFile();
				getController().getMainView().getMainPanel()
						.getConfigurationPanel().updateOldValues();
				getModel().updateBotConfigs();
			}
		}

		resetConfigPanel(configPanel);
		getModel().getBots().clear();
		getModel().getEpartners().clear();
        getModel().updateBotConfigs();
        getModel().updateEpartnerConfigs();

		// set last file location to null so that the previous saved file won't
		// get
		// overwritten when the new config is saved.
		getMenuView().setLastFileLocation(null);

		// Reset the bot panel
		resetBotTable(entityPanel);

		// reset the epartner panel
		resetEpartnerTable(entityPanel);

        getController().getMainView().setWindowTitle("Untitled");
	}

	/**
	 * Resets the given configPanel to it's default values.
	 *
	 * @param configPanel
	 *            The configPanel to be reset.
	 */
	public void resetConfigPanel(ConfigurationPanel configPanel) {
		// Reset the config panel
		configPanel
				.setClientIP(DefaultConfigurationValues.DEFAULT_CLIENT_IP
                        .getValue());
		configPanel
				.setClientPort(DefaultConfigurationValues.DEFAULT_CLIENT_PORT
						.getValue());
		configPanel
				.setServerIP(DefaultConfigurationValues.DEFAULT_SERVER_IP
						.getValue());
		configPanel
				.setServerPort(DefaultConfigurationValues.DEFAULT_SERVER_PORT
						.getValue());
		configPanel.setUseGui(DefaultConfigurationValues.USE_GUI
				.getBooleanValue());
		configPanel.setMapFile(DefaultConfigurationValues.MAP_FILE
				.getValue());

	}

	/**
	 * Reset the list with bots.
	 *
	 * @param entityPanel
	 *            The EntityPanel which contains the bot list.
	 */
	public void resetBotTable(EntityPanel entityPanel) {
		DefaultTableModel botTable = entityPanel.getBotTableModel();
		int rows = botTable.getRowCount();

		for (int i = rows - 1; i >= 0; i--) {
			botTable.removeRow(i);
		}
	}

	/**
	 * Reset the list with epartners.
	 *
	 * @param entityPanel
	 *            The EntityPanel which contains the epartner list.
	 */
	public void resetEpartnerTable(EntityPanel entityPanel) {
		DefaultTableModel epartnerTable = entityPanel.getEPartnerTableModel();
		int rows = epartnerTable.getRowCount();

		for (int i = rows - 1; i >= 0; i--) {
			epartnerTable.removeRow(i);
		}
	}
}
