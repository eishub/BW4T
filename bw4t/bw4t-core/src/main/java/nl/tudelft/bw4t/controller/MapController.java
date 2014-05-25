package nl.tudelft.bw4t.controller;

import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.Block;
import nl.tudelft.bw4t.map.view.Entity;

public interface MapController {
	/**
	 * Get the sequence of {@link BlockColor}s required to finish the simulation.
	 * 
	 * @return the sequence as list
	 */
	public List<BlockColor> getSequence();

	/**
	 * The current position in the sequence of {@link BlockColor}s.
	 * 
	 * @return the index
	 */
	public int getSequenceIndex();

	/**
	 * Get the rendering settings, like size of the world or scale.
	 * 
	 * @return the render settings
	 */
	public MapRenderSettings getRenderSettings();

	/**
	 * Get the {@link Zone} to where the blocks need to be delivered to.
	 * 
	 * @return the zone
	 */
	public Zone getDropZone();

	/**
	 * Get all the {@link Zone}s that are Rooms, that can contain Blocks.
	 * 
	 * @return the set of zones
	 */
	public Set<Zone> getRooms();

	/**
	 * Get all the {@link Zone}s.
	 * 
	 * @return the set of zones
	 */
	public Set<Zone> getZones();

	/**
	 * Check whether the given room is currently occupied.
	 * @param room the room to be checked
	 * @return true iff the room is free
	 */
	public boolean isOccupied(Zone room);

	/**
	 * Get the set of {@link Block}s currently visible.
	 * @return the set of blocks
	 */
	public Set<Block> getVisibleBlocks();

	/**
	 * Get the set of {@link Entity}s currently visible.
	 * @return the set of visible entities
	 */
	public Set<Entity> getVisibleEntities();

}
