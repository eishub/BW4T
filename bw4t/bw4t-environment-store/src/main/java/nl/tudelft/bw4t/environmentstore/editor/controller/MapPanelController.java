package nl.tudelft.bw4t.environmentstore.editor.controller;

import java.awt.Component;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.tudelft.bw4t.environmentstore.editor.model.EnvironmentMap;
import nl.tudelft.bw4t.environmentstore.editor.model.MapConverter;
import nl.tudelft.bw4t.environmentstore.editor.model.RandomMapCreator;
import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.environmentstore.editor.view.RoomMenu;
import nl.tudelft.bw4t.environmentstore.editor.view.ZoneMenu;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.MapFormatException;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone.Type;

/**
 * This holds the map that the user designed. This is an abstract map contianing
 * only number of rows and columns, do not confuse with {@link NewMap}.
 */
public class MapPanelController implements ChangeListener {

	/** basic size of the map */
	private ZoneController[][] zonecontrollers;
	
	private EnvironmentMap model;

	private ColorSequenceController cscontroller;

	private ZoneMenuController zmenucontroller;
	private ZoneController selected = null;

	private UpdateableEditorInterface uei;
	
	public MapPanelController(EnvironmentMap map) {
	    this.model = map;
	}

	/**
	 * size of map is fixed, you can't change it after construction.
	 * 
	 * @param rows
	 * @param columns
	 * @param isLabelsVisible
	 *            true if labels should be shown by renderers
	 */
	public MapPanelController(int rows, int columns) {
		

		cscontroller = new ColorSequenceController();
		zonecontrollers = new ZoneController[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				zonecontrollers[i][j] = new ZoneController(this, i, j,
						new nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel());
			}
		}
		
		zmenucontroller = new ZoneMenuController();
	}

	/**
	 * The createZone method creates a Zone and updates the
	 * UpdateableEditorInterface.
	 * 
	 * @param t
	 *            is the type of Zone that we would like to create.
	 * @param isDropZone
	 *            is true if the Zone that we are creating is a dropZone.
	 * @param isStartZone
	 *            is true if the Zone that we are creating is a startZone.
	 */
	public void createZone(Type t, boolean isDropZone, boolean isStartZone) {
		if (selected != null) {
			selected.setType(t);
			selected.setDropZone(isDropZone);
			selected.setStartZone(isStartZone);
			selected.getUpdateableEditorInterface().update();
		}
		selected = null;
	}

	public int getRows() {
		return zonecontrollers.length;
	}

	public int getColumns() {
		return zonecontrollers[0].length;
	}

	public List<BlockColor> getSequence() {
		return model.getSequence();
	}

	public void setSequence(List<BlockColor> colorSequence) {
	    model.setSequence(colorSequence);
	}

	public ZoneController[][] getZoneControllers() {
		return zonecontrollers;
	}

	/**
	 * Check that given row and column are inside the actual map.
	 * 
	 * @param r
	 *            is row
	 * @param c
	 *            is column
	 * @throws IllegalArgumentException
	 *             if r<0, c<0, r>=rows or c>=columns
	 */
	public void checkCoord(int r, int c) throws IllegalArgumentException {
		if (r < 0) {
			throw new IllegalArgumentException("row is negative");
		}
		if (c < 0) {
			throw new IllegalArgumentException("column is negative");
		}
		if (r >= zonecontrollers.length) {
			throw new IllegalArgumentException("rownr is too high");
		}
		if (c >= zonecontrollers[0].length) {
			throw new IllegalArgumentException("columnnr is too high");
		}
	}

    public EnvironmentMap getEnvironmentMap() {
        return model;
    }

	/**
	 * get the number of entities on the map. *
	 * 
	 * @return the number of entities in the map.
	 */
	public int getNumberOfEntities() {
		return model.getSpawnCount();
	}

	public UpdateableEditorInterface getUpdateableEditorInterface() {
		return uei;
	}

	public void setUpdateableEditorInterface(UpdateableEditorInterface ui) {
		uei = ui;
	}

	public ColorSequenceController getCSController() {
		return cscontroller;
	}

	public ZoneController getSelected() {
		return selected;
	}

	public void setSelected(ZoneController selected) {
		this.selected = selected;
	}

	public ZoneMenu getZoneMenu() {
		if (selected.getType() == Type.ROOM) {
			RoomMenu menu = new RoomMenu(this);
			zmenucontroller.attachListenersToRoomMenu(menu, this);
			return menu;
		} else {
			ZoneMenu menu = new ZoneMenu(this);
			zmenucontroller.attachListenersToZoneMenu(menu, this);
			return menu;
		}
	}

	/**
	 * Create a random map object using the given settings.
	 * 
	 * @return NewMap the new map that has been created.
	 * @throws MapFormatException
	 *             if no dropZone or no startZone is found.
	 */
	public NewMap createRandomMap(int roomCount) throws MapFormatException {
		NewMap map = new NewMap();
		ZoneModel[][] models = RandomMapCreator.createRandomGrid(
				zonecontrollers.length, zonecontrollers[0].length, roomCount);
		for (int i = 0; i < zonecontrollers.length; i++) {
			for (int j = 0; j < zonecontrollers[0].length; j++) {
				zonecontrollers[i][j] = new ZoneController(this, i, j,
						models[i][j]);
			}
		}
		
		return MapConverter.createMap(this.model);
	}

	public ZoneController getZoneController(int row, int col) {
		if (model.isValidZone(row, col)) {
			return zonecontrollers[row][col];
		}
		return null;
	}

	/**
	 * Show the ZoneMenu popup at given cordinates.
	 * 
	 * @param component
	 * @param x
	 * @param y
	 */
	public void showPopup(Component component, int x, int y) {
		getZoneMenu().update();
		getZoneMenu().show(component, x, y);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		this.setSequence(((ColorSequenceEditor) e.getSource()).getSequence());

	}

	public void randomizeColorsInRooms() {
		model.generateRandomBlocks();
	}

}
