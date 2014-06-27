package nl.tudelft.bw4t.map.renderer;

import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Path;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;

/** Interface that defines the MapController */
public interface MapController extends Runnable {

    /**
     * Get the sequence of {@link BlockColor}s required to finish the simulation.
     * 
     * @return the sequence as list
     */
    List<BlockColor> getSequence();

    /**
     * The current position in the sequence of {@link BlockColor}s.
     * 
     * @return the index
     */
    int getSequenceIndex();

    /**
     * Get the rendering settings, like size of the world or scale.
     * 
     * @return the render settings
     */
    MapRenderSettings getRenderSettings();

    /**
     * Get the {@link Zone} to where the blocks need to be delivered to.
     * 
     * @return the zone
     */
    Zone getDropZone();

    /**
     * Get all the {@link Zone}s that are Rooms, that can contain Blocks.
     * 
     * @return the set of zones
     */
    Set<Zone> getRooms();
    
    /**
     * Get all the {@link Zone}s that are Charging Zones, that can recharge robots.
     * 
     * @return the set of zones
     */
    Set<Zone> getChargingZones();
    
    /**
     * Get all the {@link Zone} that are blockades, that block the way of robots. 
     * 
     * @return the set of blockades
     */
    Set<Zone> getBlockades();

    /**
     * Get all the {@link Zone}s.
     * 
     * @return the set of zones
     */
    Set<Zone> getZones();

    /**
     * Check whether the given room is currently occupied.
     * 
     * @param room
     *            the room to be checked
     * @return true iff the room is free
     */
    boolean isOccupied(Zone room);

    /**
     * Get the set of {@link ViewBlock}s currently visible.
     * 
     * @return the set of blocks
     */
    Set<ViewBlock> getVisibleBlocks();

    /**
     * Get the set of {@link ViewEntity}s currently visible.
     * 
     * @return the set of visible entities
     */
    Set<ViewEntity> getVisibleEntities();
    
    /**
     * Get the set of e-Partners currently visible.
     * 
     * @return the set of visible e-Partners.
     */
    Set<ViewEPartner> getVisibleEPartners();

    /**
     * Get the set of paths currently visible.
     *
     * @return the set of visible paths.
     */
    Set<Path> getPaths();
    

    /**
     * Adds an {@link MapRendererInterface} to the list of renderers to be updated every 100ms.
     * 
     * @param mri
     *            the renderer
     */
    void addRenderer(MapRendererInterface mri);

    /**
     * Removes an {@link MapRendererInterface} from the list of renderers.
     * 
     * @param mri
     *            the renderer
     */
    void removeRenderer(MapRendererInterface mri);

    /**
     * Use this function to stop the update thread.
     * 
     * @param run
     *            set to false to stop the thread.
     */
    void setRunning(boolean run);
}
