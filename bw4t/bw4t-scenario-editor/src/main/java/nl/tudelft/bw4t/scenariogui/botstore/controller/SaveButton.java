package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;

/**
 * Handles actions of the SaveButton
 */
class SaveButton implements ActionListener {

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
		String fileName = view.getFileName();
		String botName = view.getBotName();
		String nonAlphaNumericRegex = "^[ a-zA-Z0-9_-]*$";
		File f;
		if (fileName.endsWith(".goal")) {
			if (fileName.length() > 5) {
				f = new File(fileName);
				String name = fileName.substring(0, fileName.length() - 5);
				if (name.matches(nonAlphaNumericRegex) || f.exists()) {
					if (botName.length() > 0) {
						if (botName.matches(nonAlphaNumericRegex)) {

							view.getBotController().updateConfig(view);
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

		view.dispose();
	}

	/**
	 * Updates the bot list in the scenario editor.
	 */
	private void updateBotTable() {
		view.getMainPanel().getEntityPanel().getBotTableModel().setRowCount(0);

		int rows = view.getBW4TClientConfig().getBots().size();

		for (int i = 0; i < rows; i++) {
			BotConfig botConfig = view.getBW4TClientConfig().getBot(i);
			Object[] newBotObject = {botConfig.getBotName(),
					botConfig.getBotController().toString(),
					botConfig.getBotAmount()};
			view.getMainPanel().getEntityPanel().getBotTableModel()
					.addRow(newBotObject);
		}
	}
}