package nl.tudelft.bw4t.environmentstore.editor.randomizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.environmentstore.editor.controller.MapPanelController;
import nl.tudelft.bw4t.environmentstore.editor.randomizer.view.RandomizeBlockFrame;

import org.junit.Before;
import org.junit.Test;


/**
 * Test the view of the randomize blocks frame.
 *
 */
public class RandomizerBlockFrameTest {
	
	/**
	 * This is the controller where the sequence gets generated, and blocks in
	 * all rooms.
	 */
	private MapPanelController map;

	/** This is the frame needed to instantiate the RandomizeController */
	private RandomizeBlockFrame frame;
	
	/**
	 * Setup method used to initialize the frame.
	 */
	@Before
	public void setUp() {
		map = new MapPanelController(2, 1);

		frame = new RandomizeBlockFrame("Sequence", map);
	}
	/**
	 * Test whether from the beginning all block colors are checked.
	 */
	@Test
	public void allCheckboxesSelectedOnStartTest() {
		assertTrue(frame.isRed());
		assertTrue(frame.isGreen());
		assertTrue(frame.isYellow());
		assertTrue(frame.isBlue());
		assertTrue(frame.isOrange());
		assertTrue(frame.isWhite());
		assertTrue(frame.isPink());
	}
	
	/**
	 * Test whether the number of blocks initially has been set to 8.
	 */
	@Test 
	public void checkOfBlocksOnStartTest() {
		assertEquals((Integer) 8, frame.getNumberOfBlocks());
	}
	
	/**
	 * Test whether we can set the spinner.
	 */
	@Test
	public void setSpinnerNumberOfBlocksTest() {
		frame.setSpinnerModel(9);
		assertEquals((Integer) 9, frame.getNumberOfBlocks());
	}
	
	/**
	 * Test whether the apply button has one listener.
	 */
	@Test
	public void applyButtonListenerTest() {
		int number = frame.getApplyButton().getActionListeners().length;
		int expect = 1;
		assertEquals(number, expect);
	}
	
	/**
	 * Test whether the cancel button has one listener.
	 */
	@Test
	public void cancelButtonListenerTest() {
		int number = frame.getCancelButton().getActionListeners().length;
		int expect = 1;
		assertEquals(number, expect);
	}
	

}
