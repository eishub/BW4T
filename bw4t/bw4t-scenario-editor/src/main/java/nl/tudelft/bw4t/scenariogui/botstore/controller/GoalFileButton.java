package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
/**
 * The action listener for the GOAL file chooser button.
 */
public class GoalFileButton implements ActionListener {
	/**
	 * The panel containing the button.
	 */
	private BotEditorPanel view;

	/**
	 * Constructor.
	 * @param _view The panel containing the button.
	 */
	public GoalFileButton(BotEditorPanel _view) {
		view = _view;
	}
	/**
	 * Performs the action (open a goal file)
	 * @param arg0 The action event.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		BotController currentController = view.getBotController();
		currentController.openGoalFile(view);
		}
}
