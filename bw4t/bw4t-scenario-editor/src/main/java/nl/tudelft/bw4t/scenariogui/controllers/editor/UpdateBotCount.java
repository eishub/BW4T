package nl.tudelft.bw4t.scenariogui.controllers.editor;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * 
 * @author smto
 *
 */
public class UpdateBotCount implements TableModelListener {
	private MainPanel view;
	private BW4TClientConfig model;
	
	public UpdateBotCount(final MainPanel newView, BW4TClientConfig model) {
		this.view = newView;
		this.model = model;
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		model.updateAmountBot();
		System.out.println("updateBotCount");
		view.getEntityPanel().updateBotCount(model.getAmountBot());
		System.out.println(model.getAmountBot());
	}
}