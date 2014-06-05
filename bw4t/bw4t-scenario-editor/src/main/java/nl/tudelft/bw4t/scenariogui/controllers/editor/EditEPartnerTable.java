package nl.tudelft.bw4t.scenariogui.controllers.editor;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Handles the event when a cell in the bot table is edited.
 * <p>
 * 
 * @version 0.1
 * @since 27-05-2014
 */
public class EditEPartnerTable implements TableModelListener {

	private MainPanel view;
	
	private BW4TClientConfig model;

	/**
	 * Create a EditEPartnerTable event handler.
	 * 
	 * @param newView
	 *            The parent view.
	 * @param model           
	 *            The model.
	 */
	public EditEPartnerTable(final MainPanel newView, BW4TClientConfig model) {
		this.view = newView;
		this.model = model;
	}

	/**
	 * Gets called when a cell in the table was changed. Updates the config
	 * object with the new data.
	 * 
	 * @param event
	 *            The event.
	 */
	@Override
	public void tableChanged(TableModelEvent event) {
		if (event.getColumn() == -1)
			return;
		model.getAmountEPartner();
		EPartnerConfig config = model.getEpartners()
				.get(event.getFirstRow());
		String value = ""
				+ view.getEntityPanel().getEPartnerTable()
						.getValueAt(event.getFirstRow(), event.getColumn());
		switch (event.getColumn()) {
		case 0:
			config.setEpartnerName(value);
			break;
		case 1:
			config.setEpartnerAmount(Integer.parseInt(value));
			break;
		default:
			break;
		}
	}

}
