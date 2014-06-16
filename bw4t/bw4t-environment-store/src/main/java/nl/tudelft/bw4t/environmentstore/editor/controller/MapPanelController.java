package nl.tudelft.bw4t.environmentstore.editor.controller;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.tudelft.bw4t.environmentstore.editor.model.ZoneModel;
import nl.tudelft.bw4t.environmentstore.editor.view.RoomMenu;
import nl.tudelft.bw4t.environmentstore.editor.view.ZoneMenu;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Door;
import nl.tudelft.bw4t.map.Entity;
import nl.tudelft.bw4t.map.MapFormatException;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.map.Rectangle;
import nl.tudelft.bw4t.map.RenderOptions;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

/**
 * This holds the map that the user designed. This is an abstract map contianing
 * only number of rows and columns, do not confuse with {@link NewMap}.
 */
public class MapPanelController implements ChangeListener {

	/** basic size of the map */
	private ZoneController[][] zonecontrollers;

	private ColorSequenceController cscontroller;

	private int numberOfEntities = 0;

	private boolean randomize;

	private ZoneMenuController zmenucontroller;
	private ZoneController selected = null;

	/**
	 * the target sequence.
	 * */
	private List<BlockColor> sequence = new ArrayList<>();

	private List<ZoneController> rooms = new ArrayList<>();

	/**
	 * constants that map rooms to real positions on the map.
	 */
	public static final double ROOMHEIGHT = 10;
	public static final double ROOMWIDTH = 10;
	public static final int CORRIDORWIDTH = 10;
	public static final int CORRIDORHEIGHT = 10;

	/**
	 * bot initial displacements
	 */
	static final int XDISP = 4;
	static final int YDISP = 2;

	public static final int DROP_ZONE_SEQUENCE_LENGTH = 12;

	private boolean isLabelsVisible;

	private UpdateableEditorInterface uei;

	/**
	 * size of map is fixed, you can't change it after construction.
	 * 
	 * @param rows
	 * @param columns
	 * @param isLabelsVisible
	 *            true if labels should be shown by renderers
	 */
	public MapPanelController(int rows, int columns, int entities,
			boolean rand, boolean labelsVisible) {
		isLabelsVisible = labelsVisible;
		if (rows < 1 || rows > 100) {
			throw new IllegalArgumentException("illegal value for row:" + rows);
		}
		if (columns < 1 || columns > 100) {
			throw new IllegalArgumentException("illegal value for columns:"
					+ columns);
		}
		if (entities < 1 || entities > 20) {
			throw new IllegalArgumentException("illegal value for entities:"
					+ entities);
		}

		this.numberOfEntities = entities;
		this.randomize = rand;

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
			if (selected.getType() == Type.ROOM && !selected.isDropZone()
					&& !selected.isStartZone()) {
				rooms.add(selected);
			}
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
		return sequence;
	}

	public void setSequence(List<BlockColor> colorSequence) {
		this.sequence = colorSequence;
	}

	public boolean getRandomize() {
		return randomize;
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

	/**
	 * get the number of entities on the map. *
	 * 
	 * @return the number of entities in the map.
	 */
	public int getNumberOfEntities() {
		return numberOfEntities;
	}

	/**
	 * Set the maximum number of entities in the map.
	 * 
	 * @param numberOfEntities
	 *            is the maximum number of entities.
	 */
	public void setNumberOfEntities(int numberOfEntities) {
		this.numberOfEntities = numberOfEntities;
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
	 * Add entities to map
	 * 
	 * @param map
	 * @param centerx
	 *            the position of first entity
	 * @param centery
	 *            the position of first entity
	 */
	void addEntities(NewMap map, double centerx, double centery) {
		for (int n=1; n <= numberOfEntities; n++) {
			if (n%4 == 1) {
				map.addEntity(new Entity("Bot" + n, new Point(centerx-2.5, centery-2.5)));
			} else if(n%4 == 2) {
				map.addEntity(new Entity("Bot" + n, new Point(centerx+2.5, centery-2.5)));
			} else if(n%4 == 3) {
				map.addEntity(new Entity("Bot" + n, new Point(centerx-2.5, centery+2.5)));
			} else {
				map.addEntity(new Entity("Bot" + n, new Point(centerx+2.5, centery+2.5)));
			}
		}

	}

	/**
	 * Create the real map object using the settings
	 * 
	 * @return NewMap the new map that has been created.
	 * @throws MapFormatException
	 *             if no dropZone or no startZone is found.
	 */
	public NewMap createMap() throws MapFormatException {
		NewMap map = new NewMap();

		// set the general fields of the map
		map.setArea(new Point(getColumns() * ROOMWIDTH, getRows() * ROOMHEIGHT));
		map.setSequence(sequence);
		
		//Check for dropzones/startzones
		//For each zonemodel, add the corresponding Zone
		boolean foundDropzone = false;
		boolean foundStartzone = false;
		Zone[][] output = new Zone[getRows()][getColumns()];
		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getColumns(); col++) {
				ZoneController room = getZoneController(row, col);
				if (room.isDropZone()) {
					if (foundDropzone) {
						throw new MapFormatException(
								"Only one DropZone allowed per map!");
					}
					foundDropzone = true;
				}
				if (room.isStartZone()) {
					if (foundStartzone) {
						throw new MapFormatException(
								"Only one StartZone allowed per map!");
					}
					foundStartzone = true;
				}
				output[row][col] = new Zone(room.getName(), new Rectangle(
						col * ROOMWIDTH + ROOMWIDTH / 2, row * ROOMHEIGHT + ROOMHEIGHT / 2, ROOMWIDTH, ROOMHEIGHT),
						room.getType());
				int x = (int) output[row][col].getBoundingbox().getX();
				int y = (int) output[row][col].getBoundingbox().getY();
				if (output[row][col].getType() == Type.ROOM) {
					if (room.getZoneModel().hasDoor(ZoneModel.NORTH)) {
						output[row][col].addDoor(new Door(new Point(x, y - ROOMHEIGHT/2),
								Door.Orientation.HORIZONTAL));
					}
					if (room.getZoneModel().hasDoor(ZoneModel.EAST)) {
						output[row][col].addDoor(new Door(new Point(x
								+ ROOMWIDTH/2, y),
								Door.Orientation.VERTICAL));
					}
					if (room.getZoneModel().hasDoor(ZoneModel.SOUTH)) {
						output[row][col].addDoor(new Door(new Point(x, y + ROOMHEIGHT/2),
								Door.Orientation.HORIZONTAL));
					}
					if (room.getZoneModel().hasDoor(ZoneModel.WEST)) {
						output[row][col].addDoor(new Door(new Point(x - ROOMWIDTH/2, 
								y), Door.Orientation.VERTICAL));
					}
				}
				if (room.isStartZone()) {
					addEntities(map, x, y);
				}
				map.addZone(output[row][col]);

				output[row][col].setBlocks(room.getColors());
			}
		}
		// connect all the zones
		connect(output);
		if (!foundDropzone) {
			throw new MapFormatException("No DropZone found on the map!");
		}
		if (!foundStartzone) {
			throw new MapFormatException("No StartZone found on the map!");
		}

		setRenderOptions(map);
		return map;
	}


	/**
	 * Check what type of zone the current zone is. Then call the correct
	 * connect method.
	 * 
	 * @param zones
	 *            - matrix of the map
	 */
	private void connect(Zone[][] zones) {
		for (int row = 0; row < zones.length; row++) {
			for (int col = 0; col < zones[0].length; col++) {
				if (zones[row][col].getType() == Type.CORRIDOR
						|| zones[row][col].getType() == Type.CHARGINGZONE) {
					connectCorridor(zones, row, col);
				} else if (zones[row][col].getType() == Type.ROOM) {
					connectRoom(zones, row, col);
				} else {
					// it is a blockade and thus it should not be connected.
				}
			}
		}
	}

	/**
	 * For a corridor all adjacent zones should be added.
	 * 
	 * @param zones
	 *            - matrix of the map
	 * @param row
	 *            indicates where the current zone is
	 * @param col
	 *            indicates where the current zone is
	 */
	private void connectCorridor(Zone[][] zones, int row, int col) {
		connectWest(zones, row, col);
		connectNorth(zones, row, col);
		connectEast(zones, row, col);
		connectSouth(zones, row, col);
	}

	/**
	 * For a room, only adjacent zones where a door is positioned should be
	 * added.
	 * 
	 * @param zones
	 *            - matrix of the map
	 * @param row
	 *            indicates where the current zone is
	 * @param col
	 *            indicates where the current zone is
	 */
	private void connectRoom(Zone[][] zones, int row, int col) {
		if (zones[row][col].hasEast()) {
			connectEast(zones, row, col);
		} else if (zones[row][col].hasNorth()) {
			connectNorth(zones, row, col);
		} else if (zones[row][col].hasWest()) {
			connectWest(zones, row, col);
		} else if (zones[row][col].hasSouth()) {
			connectSouth(zones, row, col);
		}
	}

	/**
	 * Connect the west neighbour
	 * 
	 * @param zones
	 *            - matrix of the map
	 * @param row
	 *            indicates where the current zone is
	 * @param col
	 *            indicates where the current zone is
	 */
	private void connectWest(Zone[][] zones, int row, int col) {
		try {
			if ((zones[row][col - 1].getType() == Type.ROOM && zones[row][col - 1]
					.hasEast())
					|| zones[row][col - 1].getType() == Type.CORRIDOR
					|| zones[row][col - 1].getType() == Type.CHARGINGZONE) {
				zones[row][col].addNeighbour(zones[row][col - 1]);
			}
		} catch (IndexOutOfBoundsException e) {
			// Do nothing.
		}
	}

	/**
	 * Connect the north neighbour
	 * 
	 * @param zones
	 *            - matrix of the map
	 * @param row
	 *            indicates where the current zone is
	 * @param col
	 *            indicates where the current zone is
	 */
	private void connectNorth(Zone[][] zones, int row, int col) {
		try {
			if ((zones[row - 1][col].getType() == Type.ROOM && zones[row - 1][col]
					.hasSouth())
					|| zones[row - 1][col].getType() == Type.CORRIDOR
					|| zones[row - 1][col].getType() == Type.CHARGINGZONE) {
				zones[row][col].addNeighbour(zones[row - 1][col]);
			}
		} catch (IndexOutOfBoundsException e) {
			// Do nothing.
		}
	}

	/**
	 * Connect the east neighbour
	 * 
	 * @param zones
	 *            - matrix of the map
	 * @param row
	 *            indicates where the current zone is
	 * @param col
	 *            indicates where the current zone is
	 */
	private void connectEast(Zone[][] zones, int row, int col) {
		try {
			if ((zones[row][col + 1].getType() == Type.ROOM && zones[row][col + 1]
					.hasWest())
					|| zones[row][col + 1].getType() == Type.CORRIDOR
					|| zones[row][col + 1].getType() == Type.CHARGINGZONE) {
				zones[row][col].addNeighbour(zones[row][col + 1]);
			}
		} catch (IndexOutOfBoundsException e) {
			// Do nothing.
		}
	}

	/**
	 * Connect the south neighbour
	 * 
	 * @param zones
	 *            - matrix of the map
	 * @param row
	 *            indicates where the current zone is
	 * @param col
	 *            indicates where the current zone is
	 */
	private void connectSouth(Zone[][] zones, int row, int col) {
		try {
			if ((zones[row + 1][col].getType() == Type.ROOM && zones[row + 1][col]
					.hasNorth())
					|| zones[row + 1][col].getType() == Type.CORRIDOR
					|| zones[row + 1][col].getType() == Type.CHARGINGZONE) {
				zones[row][col].addNeighbour(zones[row + 1][col]);
			}
		} catch (IndexOutOfBoundsException e) {
			// Do nothing.
		}
	}

	/**
	 * Set all the render options of the map.
	 * 
	 * @param map
	 */
	private void setRenderOptions(NewMap map) {
		// do we need the render options? Check against defaults.
		if (isLabelsVisible) {
			return;
		}
		RenderOptions opts = new RenderOptions();
		opts.setLabelVisible(isLabelsVisible);

		for (Zone zone : map.getZones()) {
			zone.setRenderOptions(opts);
		}

	}

	public ZoneController getZoneController(int row, int col) {
		if (isValidZone(row, col)) {
			return zonecontrollers[row][col];
		}
		return null;
	}

	/**
	 * Checks if there is a Zone at certain row,col cordinates.
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isValidZone(int row, int col) {
		return row >= 0 && col >= 0 && row < getRows() && col < getColumns();
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
		this.sequence = ((ColorSequenceEditor) e.getSource()).getSequence();

	}

	public void randomizeColorsInRooms(ArrayList<BlockColor> colors, int amount) {
		for (ZoneController zc : rooms) {
			zc.randomizeColors(amount, colors);
		}
	}

}
