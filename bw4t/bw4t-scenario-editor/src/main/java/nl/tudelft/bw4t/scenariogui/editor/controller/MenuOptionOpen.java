package nl.tudelft.bw4t.scenariogui.editor.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.editor.gui.MenuBar;
import nl.tudelft.bw4t.scenariogui.editor.gui.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.editor.gui.EntityPanel;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;

/**
 * Handles the event to open a file.
 * <p>
 * 
 * @version 0.1
 * @since 12-05-2014
 */
class MenuOptionOpen extends AbstractMenuOption {

	/**
	 * Constructs a new menu option open object.
	 * 
	 * @param view
	 *            The view.
	 * @param mainView
	 *            The controlling main view.
	 * @param model           
	 *            The model.
	 */
	public MenuOptionOpen(final MenuBar view,
			final ScenarioEditorController mainView, BW4TClientConfig model) {
		super(view, mainView, model);
	}

	/**
	 * Gets called when the menu option open button is pressed.
	 * 
	 * @param e
	 *            The action event.
	 */
	// TODO: Split up in multiple shorter methods
	public void actionPerformed(final ActionEvent e) {
		ConfigurationPanel configPanel = super.getController().getMainView()
				.getMainPanel().getConfigurationPanel();
		EntityPanel entityPanel = super.getController().getMainView()
				.getMainPanel().getEntityPanel();

		// Check if current config is different from last saved config
		if (getController().hasConfigBeenModified()) {
            boolean doSave = getController().promptUserToSave();

			if (doSave) {
				saveFile();
				getController().getMainView().getMainPanel()
						.getConfigurationPanel().updateOldValues();
				getController().getModel().updateBotConfigs();
				getController().getModel().updateEpartnerConfigs();
			}
		}

		// Open configuration file
		JFileChooser fileChooser = getCurrentFileChooser();
		fileChooser.setFileFilter(FileFilters.xmlFilter());

		if (fileChooser.showOpenDialog(getController().getMainView()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String openedFile = fileChooser.getSelectedFile().toString();

			try {
				BW4TClientConfig configuration = BW4TClientConfig.fromXML(file
						.getAbsolutePath());

                updateConfigurationInModel(configuration);

				// Fill the configuration panel from the panel
                reloadConfiguration(configPanel);

				// clear bots/epartners from the previous config
				resetBotTable(entityPanel);
				resetEpartnerTable(entityPanel);
				
				getModel().getBots().clear();
                getModel().getEpartners().clear();

                // Delete the history as well.
                getModel().updateBotConfigs();
                getModel().updateEpartnerConfigs();


				// Fill the bot panel
				int botRows = configuration.getBots().size();

				for (int i = 0; i < botRows; i++) {
					String botName = configuration.getBot(i).getBotName();
					EntityType botController = configuration.getBot(i)
							.getBotController();
					String botAmount = Integer.toString(configuration.getBot(i)
							.getBotAmount());
					Object[] botObject = {botName, botController, botAmount };
					entityPanel.getBotTableModel().addRow(botObject);
					getModel().getBots().add(configuration.getBot(i));
				}
				
				// Fill the epartner panel
				int epartnerRows = configuration.getEpartners().size();

				for (int i = 0; i < epartnerRows; i++) {
					String epartnerName = configuration.getEpartner(i).getEpartnerName();
					String epartnerAmount = Integer.toString(configuration.getEpartner(i)
							.getEpartnerAmount());
					Object[] epartnerObject = {epartnerName, epartnerAmount };
					entityPanel.getEPartnerTableModel().addRow(epartnerObject);
					getModel().getEpartners().add(configuration.getEpartner(i));
				}
			} catch (JAXBException e1) {
				ScenarioEditor.handleException(e1,
						"Error: Opening the XML has failed.");
			} catch (FileNotFoundException e1) {
				ScenarioEditor.handleException(e1,
						"Error: No file has been found. ");
			}

			// set last file location to the opened file so that the previous
			// saved file won't get
			// overwritten when the new config is saved.
			getMenuView().setLastFileLocation(openedFile);
            getController().getMainView().setWindowTitle(file.getName());
		}
		getController().getMainView().getMainPanel()
				.getConfigurationPanel().updateOldValues();
		getModel().updateBotConfigs();
		getModel().updateEpartnerConfigs();
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

		if (rows > 0) {
			for (int i = rows - 1; i >= 0; i--) {
				botTable.removeRow(i);
			}
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

		if (rows > 0) {
			for (int i = rows - 1; i >= 0; i--) {
				epartnerTable.removeRow(i);
			}
		}
	}

    private void updateConfigurationInModel(BW4TClientConfig loadedModel) {
        getModel().setClientIp(loadedModel.getClientIp());
        getModel().setClientPort(loadedModel.getClientPort());
        getModel().setServerIp(loadedModel.getServerIp());
        getModel().setServerPort(loadedModel.getServerPort());
        getModel().setLaunchGui(loadedModel.isLaunchGui());
        getModel().setMapFile(loadedModel.getMapFile());
    }

    private void reloadConfiguration(ConfigurationPanel configPanel) {
        configPanel.setClientIP(getModel().getClientIp());
        configPanel.setClientPort(getModel().getClientPort() + "");
        configPanel.setServerIP(getModel().getServerIp());
        configPanel.setServerPort(getModel().getServerPort() + "");
        configPanel.setUseGui(getModel().isLaunchGui());
        configPanel.setMapFile(getModel().getMapFile());
    }
}
