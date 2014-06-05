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
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

/**
 * Handles actions of the applybutton
 * 
 * @author Arun
 */
class SaveButton implements ActionListener {

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
		String nonAlphaNumericRegex = "^[ a-zA-Z0-9_-]*$";
		File f;
		if (fileName.endsWith(".goal")) {
			if (fileName.length() > 5) {
				f = new File(fileName);
				String name = fileName.substring(0, fileName.length() - 5);
				if (name.matches(nonAlphaNumericRegex) || f.exists()) {
					if (botName.length() > 0) {
						if (botName.matches(nonAlphaNumericRegex)) {
							view.getDataObject().setBotName(
									view.getBotNameField().getText());
							view.getDataObject().setBotController(
									EntityType.getType((String) view
											.getBotControllerSelector()
											.getSelectedItem()));
							view.getDataObject()
									.setBotAmount(
											Integer.parseInt(view
													.getBotAmountTextField()
													.getText()));
							view.getDataObject().setBotSize(
									view.getSizeSlider().getValue());
							view.getDataObject().setBotSpeed(
									view.getSpeedSlider().getValue());
							view.getDataObject().setBotBatteryCapacity(
									view.getBatterySlider().getValue());
							view.getDataObject()
									.setGrippers(
											view.getNumberOfGrippersSlider()
													.getValue());
							view.getDataObject().setBatteryEnabled(
									view.getBatteryEnabledCheckbox()
											.isSelected());
							view.getDataObject().setColorBlindHandicap(
									view.getColorblindCheckbox().isSelected());
							view.getDataObject().setGripperHandicap(
									view.getGripperCheckbox().isSelected());
							view.getDataObject().setMoveSpeedHandicap(
									view.getmovespeedCheckbox().isSelected());
							view.getDataObject()
									.setSizeOverloadHandicap(
											view.getsizeoverloadCheckbox()
													.isSelected());
							view.getDataObject().setReferenceName(
									view.getBotReferenceField().getText());
							view.getDataObject().setFileName(
									view.getFileNameField().getText());

							updateBotTable();

							view.getBotEditor().dispose();
						} else {
							ScenarioEditor
									.getOptionPrompt()
									.showMessageDialog(
											view,
											"Please specify a reference name consisting "
													+ "of valid alphanumeric characters.");
						}
					} else {
						ScenarioEditor.getOptionPrompt().showMessageDialog(
								view, "Please specify a reference name.");
					}
				} else {
					ScenarioEditor
							.getOptionPrompt()
							.showMessageDialog(
									view,
									"Please specify a file name"
											+ " consisting of valid alphanumeric characters"
											+ " or use an existing file.");
				}
			} else {
				ScenarioEditor.getOptionPrompt().showMessageDialog(view,
						"Please specify a file name.");
			}
		} else {
			ScenarioEditor.getOptionPrompt().showMessageDialog(
					view,
					"The file name is invalid.\n"
							+ "File names should end in .goal.");
		}
	}

	/**
	 * Updates the bot list in the scenario editor.
	 */
	private void updateBotTable() {
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
