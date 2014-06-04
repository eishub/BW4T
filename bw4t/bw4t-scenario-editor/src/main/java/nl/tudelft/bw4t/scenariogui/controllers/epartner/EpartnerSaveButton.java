package nl.tudelft.bw4t.scenariogui.controllers.epartner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.gui.epartner.EpartnerFrame;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles actions of the ApplyButton
 */
class EpartnerSaveButton implements ActionListener {

	private EpartnerFrame view;

	private MainPanel mpanel;

	/**
	 * The constructor for this action listener.
	 * 
	 * @param pview
	 *            The frame with the button in it.
	 */
	public EpartnerSaveButton(EpartnerFrame pview) {
		this.view = pview;
		mpanel = pview.getPanel();
	}

	/**
	 * Perform the required action (save the settings).
	 * 
	 * @param ae
	 *            The action event triggering this method.
	 */
	public void actionPerformed(ActionEvent ae) {
		view.getDataObject().setEpartnerName(view.getEpartnerName().getText());
		view.getDataObject().setEpartnerAmount(
				Integer.parseInt(view.getEpartnerAmount().getText()));
		view.getDataObject().setForgetMeNot(
				view.getLeftAloneCheckbox().isSelected());
		view.getDataObject().setGps(view.getGPSCheckbox().isSelected());

		updateEpartnerTable();

		view.dispose();
	}

	/**
	 * Updates the epartner list in the scenario editor.
	 */
	private void updateEpartnerTable() {
		view.getPanel().getEntityPanel().getEPartnerTableModel().setRowCount(0);
		int rows = view.getPanel().getEntityPanel().getEPartnerConfigs().size();

		for (int i = 0; i < rows; i++) {
			EPartnerConfig epartnerConfig = view.getPanel().getEntityPanel()
					.getEPartnerConfig(i);
			Object[] newEPartnerObject = { epartnerConfig.getEpartnerName(),
					epartnerConfig.getEpartnerAmount() };
			view.getPanel().getEntityPanel().getEPartnerTableModel()
					.addRow(newEPartnerObject);
		}
	}
}
