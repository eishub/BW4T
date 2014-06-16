package nl.tudelft.bw4t.environmentstore.editor.randomizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.controller.RandomizeController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeFrame;
import nl.tudelft.bw4t.map.BlockColor;

/**
 * This tests the map controller class.
 */
public class RandomizeBlocksTest {

	private MapPanelController map;
	private RandomizeFrame rframe;
	
	private RandomizeController random;
	
	private List<BlockColor> colors;

	@Before
	public void setUp() {
		map = new MapPanelController(1, 1, 5, false, false);
		rframe = new RandomizeFrame("Pédé", map);
		
		random = new RandomizeController(rframe, map);
	}

	/**
	 * Testing the randomizeSequence method: 0 blocks.
	 */
	@Test
	public void randomizeSequenceTest1() {
		colors = new ArrayList<BlockColor>();
		colors.add(BlockColor.BLUE);
		colors.add(BlockColor.GREEN);
		colors.add(BlockColor.RED);
		map.setSequence(random.randomizeSequence(colors, 0));
		assertTrue(map.getSequence().isEmpty());
	}

	/**
	 * Testing the randomizeSequence method: 5 blocks.
	 */
	@Test
	public void randomizeSequenceTest2() {
		colors = new ArrayList<BlockColor>();
		colors.add(BlockColor.BLUE);
		colors.add(BlockColor.GREEN);
		colors.add(BlockColor.RED);
		map.setSequence(random.randomizeSequence(colors, 5));
		assertEquals(map.getSequence().size(), 5);
	}

	/**
	 * Testing the randomizeSequence method: 5 blocks but no available colors.
	 * This throws a IllegalArgumentException because random.nextInt(0) throws it. 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void randomizeSequenceTest3() {
		colors = new ArrayList<BlockColor>();
		random.randomizeSequence(colors, 5);
	}
}
