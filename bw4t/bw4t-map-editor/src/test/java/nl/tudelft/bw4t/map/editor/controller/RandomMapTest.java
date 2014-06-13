package nl.tudelft.bw4t.map.editor.controller;

import org.junit.Test;

public class RandomMapTest {
	private MapPanelController m = new MapPanelController(10, 10, 10, true, true);
	
	@Test(expected = AssertionError.class)
	public void illegalRowsTest() {
		m.createRandomGrid(-1, 1, 6);
	}
	
	@Test(expected = AssertionError.class)
	public void illegalColTest() {
		m.createRandomGrid(1, -1, 0);
	}
	
	@Test(expected = AssertionError.class)
	public void illegalAmountOfRoomsTest() {
		m.createRandomGrid(1, 1, 100);
	}
	
	@Test(expected = AssertionError.class)
	public void illegalRowAndColTest() {
		m.createRandomGrid(-1, -1, 20);
	}
	
	@Test
	public void legalMapTest() {
		m.createRandomGrid(20, 20, 10);
	}
}
