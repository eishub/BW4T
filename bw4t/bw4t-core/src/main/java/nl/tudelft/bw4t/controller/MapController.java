package nl.tudelft.bw4t.controller;

import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.Block;
import nl.tudelft.bw4t.map.view.Entity;

public interface MapController {

	public List<BlockColor> getSequence();

	public int getSequenceIndex();

	public MapRenderSettings getRenderSettings();

	public Set<Zone> getRooms();

	public boolean isOccupied(Zone room);

	public Set<Zone> getZones();

	public Zone getDropZone();

	public Set<Block> getVisibleBlocks();

	public Set<Entity> getVisibleEntities();

}
