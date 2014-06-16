package nl.tudelft.bw4t.environmentstore.editor.model;

import java.util.List;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

/**
 * This class is used when reading in a previously made map. Use it to edit the
 * map editor grid accordingly.
 */
public final class ZoneData {
	public final Type type;

	public final int row;
	public final int column;

	public final List<BlockColor> colors;

	public final boolean isStartZone;
	public final boolean isDropZone;

	public ZoneData(Zone zone) {
		this.type = zone.getType();

		this.row = getRow(zone);
		this.column = getColumn(zone);

		this.colors = zone.getBlocks();

		this.isStartZone = isStartZone(zone);
		this.isDropZone = isDropZone(zone);
	}

	/**
	 * @param zone
	 *            The current zone.
	 * @return
	 *        The row the zone belongs to. 
	 */
	private int getRow(Zone zone) {
		double height = MapPanelController.ROOMHEIGHT;
		double y = zone.getBoundingbox().getY();

		return (int) ((y - height / 2) / height);
	}
	
	/**
	 * @param zone
	 *            The current zone.
	 * @return
	 *        The column the zone belongs to. 
	 */
	private int getColumn(Zone zone) {
		double width = MapPanelController.ROOMWIDTH;
		double x = zone.getBoundingbox().getX();

		return (int) ((x - width / 2) / width);
	}

	private boolean isStartZone(Zone zone) {
		return zone.getName().startsWith("StartZone");
	}

	private boolean isDropZone(Zone zone) {
		return zone.getName().startsWith("DropZone");
	}
}
