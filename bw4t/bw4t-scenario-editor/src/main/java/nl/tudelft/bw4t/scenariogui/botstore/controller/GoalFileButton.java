package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;
/**
 * The action listener for the GOAL file chooser button.
 */
public class GoalFileButton implements ActionListener {
	/**
	 * The panel containing the button.
	 */
	private BotEditorPanel view;
	/**
	 * The file chooser.
	 */
	private JFileChooser jfc;
	/**
	 * Constructor.
	 * @param _view The panel containing the button.
	 */
	public GoalFileButton(BotEditorPanel _view) {
		view = _view;
	}
	/**
	 * Performs the action.
	 * @param arg0 The action event.
	 */
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		jfc = new JFileChooser();
		jfc.setFileFilter(FileFilters.goalFilter());
		if (jfc.showOpenDialog(view) == jfc.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();
			String path = f.getAbsolutePath();
			view.getFileNameField().setText(path);
            view.getTempBotConfig().setFileName(path);
		}
	}

}
