package nl.tudelft.bw4t.scenariogui.controllers.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event to create a new E-partner.
 * 
 * @version 0.1
 * @since 12-05-2014
 */
class AddNewEPartner implements ActionListener {

	private MainPanel view;

	private int eCount;
	
	private BW4TClientConfig model;

	/**
	 * Create an AddNewEpartner event handler.
	 * 
	 * @param newView
	 *            The parent view.
	 */
	public AddNewEPartner(final MainPanel newView, BW4TClientConfig model) {
		this.view = newView;
		this.model = model;
	}

	/**
	 * Executes action that needs to happen when the "New E-partner" button is
	 * pressed. Gives default name of "E-partner &lt;n&gt;" where &lt;n&gt; is
	 * the n'th e-Parnter created.
	 * 
	 * @param ae
	 *            The action
	 */
	public void actionPerformed(ActionEvent ae) {
		eCount = model.getEpartners().size() + 1;
		Object[] newEPartnerObject = { "E-partner " + eCount, 1 };
		view.getEntityPanel().getEPartnerTableModel().addRow(newEPartnerObject);
		EPartnerConfig config = new EPartnerConfig();
		config.setEpartnerName("E-Partner " + eCount);
		
		model.addEpartner(config);
		//view.getEntityPanel().getEPartnerConfigs().add(config);
	}
}
