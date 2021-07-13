package nl.tudelft.bw4t.server.util;

import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import nl.tudelft.bw4t.server.model.blocks.Block;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;

/**
 * Tests various functions of the MapLoader. The class is rather complex, so not
 * everything can be properly tested.
 */
@RunWith(MockitoJUnitRunner.class)
public class MapUtilTest {

	@Mock
	private ContinuousSpace<Object> space;
	@Mock
	private Context<Object> context;

	/**
	 * Tests whether it correctly prevents vertical overlapping of the blocks.
	 */
	@Test
	public void createBlocksForRoomVerticalOverlapTest() {
		Rectangle2D room = new Rectangle(0, 0, 2, 10);
		List<Rectangle2D> blockList = new ArrayList<Rectangle2D>();
		blockList.add(new Rectangle(0, 0, Block.SIZE, Block.SIZE));
		blockList.add(new Rectangle(1, 0, Block.SIZE, Block.SIZE));
		blockList.add(new Rectangle(0, 9, Block.SIZE, Block.SIZE));
		blockList.add(new Rectangle(1, 9, Block.SIZE, Block.SIZE));

		// As there is a random element in placement, we test multiple times to
		// be sure
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			Rectangle2D block = MapUtils.findFreePlace(room, blockList, random);
			assertTrue(block.getMinY() >= 1);
			assertTrue(block.getMaxY() <= 9);
		}
	}

	/**
	 * Tests whether it correctly prevents horizontal overlapping of the blocks.
	 */
	@Test
	public void createBlocksForRoomHorizontalOverlapTest() {
		Rectangle2D room = new Rectangle(0, 0, 10, 2);
		List<Rectangle2D> blockList = new ArrayList<Rectangle2D>();
		blockList.add(new Rectangle(0, 0, Block.SIZE, Block.SIZE));
		blockList.add(new Rectangle(0, 1, Block.SIZE, Block.SIZE));
		blockList.add(new Rectangle(9, 0, Block.SIZE, Block.SIZE));
		blockList.add(new Rectangle(9, 1, Block.SIZE, Block.SIZE));

		// As there is a random element in placement, we test multiple times to
		// be sure
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			Rectangle2D block = MapUtils.findFreePlace(room, blockList, random);
			assertTrue(block.getMinX() >= 1);
			assertTrue(block.getMaxX() <= 9);
		}
	}

	/**
	 * Checks if the right exception is thrown when it tries to add a block room
	 * with not enough space. We check for InvocationTargetException because
	 * that is thrown before IllegalStateException
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void createBlocksForRoomNoRoomTest() {
		Rectangle2D room = new Rectangle(0, 0, 0, 0);
		List<Rectangle2D> blockList = new ArrayList<Rectangle2D>();

		MapUtils.findFreePlace(room, blockList, new Random());
	}

	/**
	 * Checks we get a block placed in overlap with other blocks if we place new
	 * block in full room.
	 */
	@Test
	public void createBlocksForRoomFullRoomTest() {
		Rectangle2D room = new Rectangle(0, 0, 2, 2);
		List<Rectangle2D> blockList = new ArrayList<Rectangle2D>();
		blockList.add(new Rectangle(0, 0, Block.SIZE, Block.SIZE));
		blockList.add(new Rectangle(0, 1, Block.SIZE, Block.SIZE));
		blockList.add(new Rectangle(1, 0, Block.SIZE, Block.SIZE));
		blockList.add(new Rectangle(1, 1, Block.SIZE, Block.SIZE));

		// Check block is placed not dead on some other block (must be to work
		// around ENV-1341) As there is a random element in placement, we test
		// multiple times to
		// be sure.
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			Rectangle2D block = MapUtils.findFreePlace(room, blockList, random);
			assertTrue(room.contains(block));
			for (Rectangle2D b : blockList) {
				assertTrue(Math.abs(b.getCenterX() - block.getCenterX()) > 0.0000000001);
				assertTrue(Math.abs(b.getCenterY() - block.getCenterY()) > 0.0000000001);
			}
		}

	}
}
