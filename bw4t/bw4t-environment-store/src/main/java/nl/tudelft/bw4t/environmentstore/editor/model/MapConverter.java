package nl.tudelft.bw4t.environmentstore.editor.model;

import nl.tudelft.bw4t.map.Door;
import nl.tudelft.bw4t.map.Entity;
import nl.tudelft.bw4t.map.MapFormatException;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.map.Rectangle;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

public class MapConverter {

	/**
	 * bot initial displacements
	 */
	static final int XDISP = 4;
	static final int YDISP = 2;
	/* constants that map rooms to real positions on the map. */
	public static final int ROOMHEIGHT = 10;
	public static final int ROOMWIDTH = 10;

	private MapConverter() {
	}

	/**
	 * Create the real map object using the settings
	 * 
	 * @return NewMap the new map that has been created.
	 * @throws MapFormatException
	 *             if no dropZone or no startZone is found.
	 */
	public static NewMap createMap(EnvironmentMap model)
			throws MapFormatException {
		NewMap map = new NewMap();

		// set the general fields of the map
		map.setArea(new Point(model.getColumns() * MapConverter.ROOMWIDTH,
				model.getRows() * MapConverter.ROOMHEIGHT));
		map.setSequence(model.getSequence());

		// Check for dropzoness
		// For each zonemodel, add the corresponding Zone
		boolean foundDropzone = false;

		Zone[][] output = new Zone[model.getRows()][model.getColumns()];
		for (int row = 0; row < model.getRows(); row++) {
			for (int col = 0; col < model.getColumns(); col++) {
				ZoneModel room = model.getZone(row, col);

				if (room.isDropZone()) {
					if (foundDropzone) {
						throw new MapFormatException(
								"Only one DropZone allowed per map!");
					}
					foundDropzone = true;
				}

				output[row][col] = new Zone(room.getName(), new Rectangle(col
						* ROOMWIDTH + ROOMWIDTH / 2, row * ROOMHEIGHT
						+ ROOMHEIGHT / 2, ROOMWIDTH, ROOMHEIGHT),
						room.getType());
				int x = (int) output[row][col].getBoundingbox().getX();
				int y = (int) output[row][col].getBoundingbox().getY();

				if (output[row][col].getType() == Type.ROOM) {
					if (room.hasDoor(ZoneModel.NORTH)) {
						output[row][col]
								.addDoor(new Door(new Point(x, y - ROOMHEIGHT
										/ 2), Door.Orientation.HORIZONTAL));
					}
					if (room.hasDoor(ZoneModel.EAST)) {
						output[row][col]
								.addDoor(new Door(new Point(x + ROOMWIDTH / 2,
										y), Door.Orientation.VERTICAL));
					}
					if (room.hasDoor(ZoneModel.SOUTH)) {
						output[row][col]
								.addDoor(new Door(new Point(x, y + ROOMHEIGHT
										/ 2), Door.Orientation.HORIZONTAL));
					}
					if (room.hasDoor(ZoneModel.WEST)) {
						output[row][col]
								.addDoor(new Door(new Point(x - ROOMWIDTH / 2,
										y), Door.Orientation.VERTICAL));
					}
				}

				if (room.isStartZone()) {
					addEntities(map, x, y, room.getSpawnCount());
				}

				map.addZone(output[row][col]);

				output[row][col].setBlocks(room.getColors());
			}
		}
		// connect all the zones
		connect(output);

		return map;
	}

	/**
	 * Check what type of zone the current zone is. Then call the correct
	 * connect method.
	 * 
	 * @param zones
	 *            - matrix of the map
	 */
	private static void connect(Zone[][] zones) {
		for (int row = 0; row < zones.length; row++) {
			for (int col = 0; col < zones[0].length; col++) {
				if (zones[row][col].isOpenSpace()) {
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
	private static void connectCorridor(Zone[][] zones, int row, int col) {
		connectCorridor(zones, row, col, row, col - 1);
		connectCorridor(zones, row, col, row + 1, col);
		connectCorridor(zones, row, col, row, col + 1);
		connectCorridor(zones, row, col, row - 1, col);
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
	private static void connectRoom(Zone[][] zones, int row, int col) {
		if (zones[row][col].hasNorth()) {
			connectZone(zones, row, col, row - 1, col);
		}
		if (zones[row][col].hasEast()) {
			connectZone(zones, row, col, row, col + 1);
		}
		if (zones[row][col].hasSouth()) {
			connectZone(zones, row, col, row + 1, col);
		}
		if (zones[row][col].hasWest()) {
			connectZone(zones, row, col, row, col - 1);
		}
	}

	/**
	 * Checks whether two given positions in the grid are open space and
	 * connects them.
	 * 
	 * @param zones
	 *            matrix of the map
	 * @param row
	 *            indicates where the current zone is
	 * @param col
	 *            indicates where the current zone is
	 * @param row2
	 *            indicates where the other zone is
	 * @param col2
	 *            indicates where the other zone is
	 */
	private static void connectCorridor(Zone[][] zones, int row, int col,
			int row2, int col2) {
		try {
			final Zone zone1 = zones[row][col];
			final Zone zone2 = zones[row2][col2];
			if (zone1.isOpenSpace() && zone2.isOpenSpace()) {
				connectZone(zones, row, col, row2, col2);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// ignore trying to connect non-existent zones
		}
	}

	/**
	 * Connects two positions in the grid if they are valid.
	 * 
	 * @param zones
	 *            matrix of the map
	 * @param row
	 *            indicates where the current zone is
	 * @param col
	 *            indicates where the current zone is
	 * @param row2
	 *            indicates where the other zone is
	 * @param col2
	 *            indicates where the other zone is
	 */
	private static void connectZone(Zone[][] zones, int row, int col, int row2,
			int col2) {
		try {
			final Zone zone1 = zones[row][col];
			final Zone zone2 = zones[row2][col2];
			if (!zone1.getNeighbours().contains(zone2)) {
				zone1.addNeighbour(zone2);
			}
			if (!zone2.getNeighbours().contains(zone1)) {
				zone2.addNeighbour(zone1);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// ignore trying to connect non-existent zones
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
	private static void addEntities(NewMap map, double centerx, double centery,
			int numberOfEntities) {
		for (int n = 1; n <= numberOfEntities; n++) {
			final int n4 = n % 4;
			if (n4 == 1) {
				map.addEntity(new Entity("Bot" + n, new Point(centerx - 2.5,
						centery - 2.5)));
			} else if (n4 == 2) {
				map.addEntity(new Entity("Bot" + n, new Point(centerx + 2.5,
						centery - 2.5)));
			} else if (n4 == 3) {
				map.addEntity(new Entity("Bot" + n, new Point(centerx - 2.5,
						centery + 2.5)));
			} else {
				map.addEntity(new Entity("Bot" + n, new Point(centerx + 2.5,
						centery + 2.5)));
			}
		}
	}

}
