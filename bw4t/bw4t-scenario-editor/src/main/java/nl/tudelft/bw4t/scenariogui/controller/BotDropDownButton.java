package nl.tudelft.bw4t.scenariogui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to show the dropdown menu with the standard bots.
 */
public class BotDropDownButton implements ActionListener {

	/** The <code>MainPanel</code> serving as the content pane. */
	private MainPanel view;

	public BotDropDownButton(MainPanel newView) {
		this.view = newView;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		view.getEntityPanel().showBotDropDown();
	}
}
