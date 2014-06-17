package nl.tudelft.bw4t.environmentstore.editor.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone.Type;

import org.junit.Before;
import org.junit.Test;

public class SolvableMapTest {
	private EnvironmentMap em;
	
	@Before
	public void setUp() {
		em = new EnvironmentMap(5, 5);
		initRooms(em);
		List<BlockColor> l = new ArrayList<BlockColor>();
		for (int i = 0; i < 5; i++) {
			l.add(BlockColor.RED);
		}
		em.setSequence(l);
		ZoneModel zm = new ZoneModel();
		zm.setType(Type.CORRIDOR);
		zm.setStartZone(true);
		em.setZone(4, 2, zm);
	}
	
	public void initRooms(EnvironmentMap em) {
		ZoneModel zm = new ZoneModel();
		zm.setType(Type.ROOM);
		zm.setDoor(3, true);
		em.setZone(1, 1, zm);
		zm = new ZoneModel();
		zm.setType(Type.ROOM);
		zm.setDoor(0, true);
		em.setZone(1, 2, zm);
		zm = new ZoneModel();
		zm.setType(Type.ROOM);
		zm.setDoor(1, true);
		em.setZone(1, 3, zm);
		zm = new ZoneModel();
		zm.setType(Type.ROOM);
		zm.setDoor(2, true);
		em.setZone(3, 1, zm);
		zm = new ZoneModel();
		zm.setType(Type.ROOM);
		zm.setName("DropZone");
		zm.setDropZone(true);
		zm.setDoor(2, true);
		em.setZone(3, 2, zm);
		zm = new ZoneModel();
		zm.setType(Type.ROOM);
		zm.setDoor(2, true);
		em.setZone(3, 3, zm);
	}
	
	@Test
	public void solvableMapTest() {
		List<BlockColor> l2 = new ArrayList<BlockColor>();
		l2.add(BlockColor.RED);
		em.generateRandomBlocks(l2, 8);
		NewMap m = MapConverter.createMap(em);
		assertTrue(SolvabilityAlgorithm.mapIsSolvable(m));
	}
	
	@Test
	public void unsolvableMapNotEnoughBlocksTest() {
		List<BlockColor> l2 = new ArrayList<BlockColor>();
		em.generateRandomBlocks(l2, 1);
		NewMap m = MapConverter.createMap(em);
		assertFalse(SolvabilityAlgorithm.mapIsSolvable(m));
	}
	
	@Test
	public void unsolvableMapBlockedRoomTest() {
		ZoneModel zm = new ZoneModel();
		zm.setType(Type.BLOCKADE);
		em.setZone(1, 0, zm);
		List<BlockColor> l2 = new ArrayList<BlockColor>();
		l2.add(BlockColor.RED);
		em.generateRandomBlocks(l2, 1);
		NewMap m = MapConverter.createMap(em);
		assertFalse(SolvabilityAlgorithm.mapIsSolvable(m));
		zm.setType(Type.ROOM);
		em.setZone(1, 0, zm);
	}
	
	@Test
	public void unsolvableMapBlockedStartTest() {
		ZoneModel zm = new ZoneModel();
		zm.setType(Type.BLOCKADE);
		em.setZone(4, 1, zm);
		em.setZone(4, 3, zm);
		List<BlockColor> l2 = new ArrayList<BlockColor>();
		l2.add(BlockColor.RED);
		em.generateRandomBlocks(l2, 1);
		NewMap m = MapConverter.createMap(em);
		assertFalse(SolvabilityAlgorithm.mapIsSolvable(m));
		zm.setType(Type.ROOM);
		em.setZone(4, 1, zm);
		em.setZone(4, 3, zm);
	}
	
	@Test
	public void unsolvableMapNoBlocks() {
		NewMap m = MapConverter.createMap(em);
		assertFalse(SolvabilityAlgorithm.mapIsSolvable(m));
	}
}
