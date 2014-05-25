package nl.tudelft.bw4t.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.MapFormatException;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;

/**
 * An abstract {@link MapController} implementation, taking over as much functionality as possible. Without using client
 * or server-specific code.
 */
public abstract class AbstractMapController implements MapController {
	/**
	 * The map to be rendered.
	 */
	private NewMap map;
	/**
	 * Various rendering settings.
	 */
	private MapRenderSettings renderSettings;

	public AbstractMapController(NewMap map) {
		renderSettings = new MapRenderSettings();
		this.setMap(map);
	}

	/**
	 * @return the map
	 */
	public NewMap getMap() {
		return map;
	}

	/**
	 * @param themap
	 *            the map to set
	 */
	public void setMap(NewMap themap) {
		this.map = themap;

		Point size = map.getArea();
		renderSettings.setWorldDimensions((int) size.getX(), (int) size.getY());
	}

	/**
	 * @return the renderSettings
	 */
	@Override
	public MapRenderSettings getRenderSettings() {
		return renderSettings;
	}

	/**
	 * @param theRenderSettings
	 *            the renderSettings to set
	 */
	public void setRenderSettings(MapRenderSettings theRenderSettings) {
		this.renderSettings = theRenderSettings;
	}

	@Override
	public List<BlockColor> getSequence() {
		return map.getSequence();
	}

	@Override
	public Set<Zone> getZones() {
		return new HashSet<Zone>(map.getZones());
	}

	@Override
	public Set<Zone> getRooms() {
		Set<Zone> rooms = new HashSet<Zone>();

		for (Zone zone : map.getZones()) {
			if (zone.getType() == Type.ROOM) {
				rooms.add(zone);
			}
		}

		return rooms;
	}

	@Override
	public Zone getDropZone() {
		for (Zone zone : map.getZones()) {
			if (zone.getName().equals(Zone.DROP_ZONE_NAME)) {
				return zone;
			}
		}
		throw new MapFormatException("The map does not include a dropzone!");
	}

}
