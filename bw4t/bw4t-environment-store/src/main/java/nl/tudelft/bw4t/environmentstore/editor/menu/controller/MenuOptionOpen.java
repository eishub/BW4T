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
import nl.tudelft.bw4t.map.MapFormatException;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.renderer.MapController;

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
		fileChooser.setFileFilter(FileFilters.mapFilter());

		EnvironmentStore current = getEnvironmentStoreController()
				.getMainView();

		if (fileChooser.showOpenDialog(current) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
	            MapPanelController mc = super.getEnvironmentStoreController().getMapController();
	            
		    	mc.setModel(MapConverter.loadMap(file));
			} catch (MapFormatException e1) {
				EnvironmentStore.showDialog("File cannot be read.");
			}
		}
	}
}
