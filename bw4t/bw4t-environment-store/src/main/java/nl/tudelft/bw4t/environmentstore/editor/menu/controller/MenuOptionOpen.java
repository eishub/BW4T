package nl.tudelft.bw4t.environmentstore.editor.menu.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.environmentstore.editor.menu.view.MenuBar;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneData;
import nl.tudelft.bw4t.environmentstore.main.controller.EnvironmentStoreController;
import nl.tudelft.bw4t.environmentstore.main.view.EnvironmentStore;
import nl.tudelft.bw4t.environmentstore.util.DefaultOptionPrompt;
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
		openMap();

		// Open configuration file
		JFileChooser fileChooser = getCurrentFileChooser();
		fileChooser.setFileFilter(FileFilters.xmlFilter());

		EnvironmentStore current = getEnvironmentStoreController()
				.getMainView();

		if (fileChooser.showOpenDialog(current) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				NewMap map = NewMap.create(new FileInputStream(file));
				List<BlockColor> sequence = map.getSequence();
				List<ZoneData> data = getZoneData(map);
				// Joost TODO: send sequence and data to the class where the grid will be edited.
			} catch (FileNotFoundException e1) {
				DefaultOptionPrompt prompt = new DefaultOptionPrompt();
				prompt.showMessageDialog(current, "File cannot be found.");
			} catch (JAXBException e1) {
				DefaultOptionPrompt prompt = new DefaultOptionPrompt();
				prompt.showMessageDialog(current, "File cannot be read.");
			}
		}
	}

	private List<ZoneData> getZoneData(NewMap map) {
		List<ZoneData> data = new ArrayList<ZoneData>();
		for (Zone zone : map.getZones()) {
			data.add(new ZoneData(zone));
		}
		return data;
	}
}
