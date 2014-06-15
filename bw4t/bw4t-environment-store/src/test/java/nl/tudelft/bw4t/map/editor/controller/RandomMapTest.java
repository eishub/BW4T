package nl.tudelft.bw4t.map.editor.controller;

import nl.tudelft.bw4t.environmentstore.editor.model.RandomMapCreator;

import org.junit.Test;

public class RandomMapTest {
	@Test(expected = AssertionError.class)
	public void illegalRowsTest() {
		RandomMapCreator.createRandomGrid(-1, 1, 6);
	}
	
	@Test(expected = AssertionError.class)
	public void illegalColTest() {
		RandomMapCreator.createRandomGrid(1, -1, 0);
	}
	
	@Test(expected = AssertionError.class)
	public void illegalAmountOfRoomsTest() {
		RandomMapCreator.createRandomGrid(1, 1, 100);
	}
	
	@Test(expected = AssertionError.class)
	public void illegalRowAndColTest() {
		RandomMapCreator.createRandomGrid(-1, -1, 20);
	}
	
	@Test
	public void legalMapTest() {
		RandomMapCreator.createRandomGrid(20, 20, 10);
	}
}
