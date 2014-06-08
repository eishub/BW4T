package nl.tudelft.bw4t.scenariogui.epartner.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * Handles actions of the ApplyButton
 */
class EpartnerSaveButton implements ActionListener {

	private EpartnerFrame view;

	private MainPanel parent;

	/**
	 * The constructor for this action listener.
	 * 
	 * @param pview
	 *            The frame with the button in it.
	 */
	public EpartnerSaveButton(EpartnerFrame pview) {
		this.view = pview;
		this.parent = pview.getEpartnerController().getParent();
	}

	/**
	 * Perform the required action
	 * 
	 * @param ae
	 *            The action event triggering this method.
	 */
	public void actionPerformed(ActionEvent ae) {
		view.getEpartnerController().updateConfig(view);
		updateEpartnerTable();
		view.dispose();
	}

	/**
	 * Updates the epartner list in the scenario editor.
	 */
	private void updateEpartnerTable() {
		DefaultTableModel epartnerTable = parent.getEntityPanel()
				.getEPartnerTableModel();
		epartnerTable.setRowCount(0);
		int rows = view.getModel().getEpartners().size();

		for (int i = 0; i < rows; i++) {
			EPartnerConfig epartnerConfig = view.getModel().getEpartner(i);
			Object[] newEpartnerObject = { epartnerConfig.getEpartnerName(),
					epartnerConfig.getEpartnerAmount() };
			epartnerTable.addRow(newEpartnerObject);
		}
	}
}
