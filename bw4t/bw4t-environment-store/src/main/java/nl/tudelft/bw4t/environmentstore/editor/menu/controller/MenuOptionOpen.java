package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.editor.model.EnvironmentMap;
import nl.tudelft.bw4t.environmentstore.editor.model.MapConverter;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;

public class MenuOptionOpen extends AbstractMenuOption {

	public MenuOptionOpen(MenuBar newView, EnvironmentStoreController controller) {
		super(newView, controller);
	}

	/**
	 * Gets called when the menu item save as is pressed.
	 * 
	 * @param e
	 *            The action event.
	 */
	public void actionPerformed(final ActionEvent e) {
		// Open configuration file
		JFileChooser fileChooser = getCurrentFileChooser();
		fileChooser.setFileFilter(FileFilters.xmlFilter());

		EnvironmentStore current = getEnvironmentStoreController()
				.getMainView();

		if (fileChooser.showOpenDialog(current) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				// Get the information from the file.
				NewMap map = NewMap.create(new FileInputStream(file));
				
				int nrows = getRows(map);
				int ncols = getColumns(map);
				List<BlockColor> sequence = map.getSequence();
				List<ZoneModel> data = getZoneData(map);
				
				// Create a new editor with the right size.
				super.getEnvironmentStoreController().getMainView().dispose();
				
				MapPanelController mc = new MapPanelController(nrows, ncols);

				EnvironmentStore es = new EnvironmentStore(mc);
				es.setWindowTitle(es.stripExtension(file.getName()));
		    	es.setVisible(true);
		    	
				// Send this data to the grid that will be edited.
		    	EnvironmentMap model = mc.getEnvironmentMap();
		    	// Set the saved zones.		    	
				for (ZoneModel zModel : data) {
					int rowSet = ZoneModel.calcRow(zModel.getZone());
					int colSet = ZoneModel.calcColumn(zModel.getZone());

					model.getZone(rowSet, colSet).setType(zModel.getType());
					model.getZone(rowSet, colSet).setStartZone(zModel.isStartZone());
					model.getZone(rowSet, colSet).setDropZone(zModel.isDropZone());
					model.getZone(rowSet, colSet).setColors(zModel.getColors());
					
					// Set the doors for this room.
					for(int i = 0; i < zModel.getDoorsBool().length; i++) {
						if(zModel.getDoorsBool()[i] == true) {
							model.getZone(rowSet, colSet).setDoor(i, true);
						}
					}
				}
				
		    	// Set the saved sequence.
		    	model.setSequence(sequence);
		    	mc.setModel(model);
		    	
				
			} catch (FileNotFoundException e1) {
				EnvironmentStore.showDialog("File cannot be found.");
			} catch (JAXBException e1) {
				EnvironmentStore.showDialog("File cannot be read.");
			}
		}
	}

	private List<ZoneModel> getZoneData(NewMap map) {
		List<ZoneModel> data = new ArrayList<ZoneModel>();
		for (Zone zone : map.getZones()) {
			data.add(new ZoneModel(zone));
		}
		return data;
	}
	
	/**
	 * @param map
	 *            The map to be edited.
	 * @return
	 *        The row the zone belongs to. 
	 */
	private int getRows(NewMap map) {
		double height = MapConverter.ROOMHEIGHT;
		double y = map.getArea().getY();

		return (int) ((y - height / 2) / height) + 1;
	}
	
	/**
	 * @param map
	 *            The map to be edited.
	 * @return
	 *        The column the zone belongs to. 
	 */
	private int getColumns(NewMap map) {
		double width = MapConverter.ROOMWIDTH;
		double x = map.getArea().getX();

		return (int) ((x - width / 2) / width) + 1;
	}
}
