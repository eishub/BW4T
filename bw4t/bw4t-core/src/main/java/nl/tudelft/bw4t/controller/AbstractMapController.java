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
import nl.tudelft.bw4t.view.MapRendererInterface;

import org.apache.log4j.Logger;

/**
 * An abstract {@link MapController} implementation, taking over as much functionality as possible. Without using client
 * or server-specific code.
 */
public abstract class AbstractMapController implements MapController, Runnable {
	/**
	 * The log4j logger which writes logs.
	 */
	private static final Logger LOGGER = Logger.getLogger(AbstractMapController.class);

	/**
	 * The map to be rendered.
	 */
	private NewMap map;
	/**
	 * Various rendering settings.
	 */
	private MapRenderSettings renderSettings;
	/**
	 * True while the thread to update the {@link MapRendererInterface} is running.
	 */
	private boolean running = false;

	/**
	 * The set of all connected {@link MapRendererInterface}s.
	 */
	private final Set<MapRendererInterface> renderers = new HashSet<>();

	public AbstractMapController(NewMap map) {
		renderSettings = new MapRenderSettings();
		this.setMap(map);
		startupUpdateException();
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

	/**
	 * Is the update thread running?
	 * 
	 * @return true iff the thread is running.
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Set the update thread running?
	 * 
	 * @param run
	 *            the value to be set
	 */
	protected void setRunning(boolean run) {
		running = run;
	}

	public Set<MapRendererInterface> getRenderers() {
		return renderers;
	}

	private void startupUpdateException() {
		Thread thread = new Thread(new Updater(this));
		thread.start();
	}

	@Override
	public void addRenderer(MapRendererInterface mri) {
		getRenderers().add(mri);
	}

	@Override
	public void removeRenderer(MapRendererInterface mri) {
		getRenderers().remove(mri);
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

	/**
	 * Called every {@link MapRenderSettings#getUpdateDelay()}, to update the renderers.
	 */
	@Override
	public void run() {
		for (MapRendererInterface mri : getRenderers()) {
			updateRenderer(mri);
		}
	}

	/**
	 * Called every {@link MapRenderSettings#getUpdateDelay()} for every associated {@link MapRendererInterface}.
	 * 
	 * @param mri
	 *            the current renderer
	 */
	protected abstract void updateRenderer(MapRendererInterface mri);

}
