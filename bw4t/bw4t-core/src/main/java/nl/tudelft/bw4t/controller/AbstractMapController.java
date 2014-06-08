package nl.tudelft.bw4t.controller;

import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.*;

import nl.tudelft.bw4t.map.MapFormatException;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.Zone.Type;
import nl.tudelft.bw4t.view.MapRendererInterface;

/**
 * An abstract {@link MapController} implementation, taking over as much functionality as possible. Without using client
 * or server-specific code.
 */
public abstract class AbstractMapController implements MapController, Runnable {
	
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

    /**
     * Creates Default {@link MapRenderSettings}, sets the map and starts the updater.
     * 
     * @param theMap
     *            the map to be used
     */
    public AbstractMapController(NewMap theMap) {
        renderSettings = new MapRenderSettings();
        this.setMap(theMap);
        setRunning(true);
    }

    /**
     * @return the map
     */
    public NewMap getMap() {
        return map;
    }

    /**
     * Set the map and update the world dimensions in the renderer.
     * 
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
    @Override
    public void setRunning(boolean run) {
        if (!run) {
            setForceRunning(run);
        } else if (!running) {
            startupUpdateThread();
        }
    }

    /**
     * Sets the actual variable doesn't start the thread.
     * 
     * @param run
     *            the value to be set
     */
    protected void setForceRunning(boolean run) {
        running = run;
    }

    /**
     * @return the renderers
     */
    public Set<MapRendererInterface> getRenderers() {
        return renderers;
    }

    /**
     * Start the update thread.(will fail if it's already running)
     */
    private void startupUpdateThread() {
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
    public Set<Zone> getZones() {
        return new HashSet<Zone>(map.getZones());
    }

    @Override
    public Set<Zone> getRooms() {
        Set<Zone> rooms = new HashSet<Zone>();

        for (Zone zone : map.getZones()) {
            if (zone.getType() == Type.ROOM || zone.getType() == Type.BLOCKADE) {
            	LOGGER.info("room found");
                rooms.add(zone);
            }
        }

        return rooms;
    }
    
    @Override
    public Set<Zone> getChargingZones() {
    	Set<Zone> chargingzones = new HashSet<Zone>();
    	
    	LOGGER.info("looking for charging zone...");
    	for (Zone zone : map.getZones()) {
    		if (zone.getType() == Type.CHARGINGZONE) {
    			LOGGER.info("charging zone found");
    			chargingzones.add(zone);
    		}
    	}
    	
    	return chargingzones;
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
