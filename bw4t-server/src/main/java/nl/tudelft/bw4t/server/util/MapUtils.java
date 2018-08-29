package nl.tudelft.bw4t.server.util;

import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import nl.tudelft.bw4t.server.model.blocks.Block;

public class MapUtils {

	/**
	 * Distances between blocks. Either non-occluding or virtually none. We need
	 * marginal distance because of ENV-1341
	 */
	private static final double DOUBLE_BLOCK_SIZE = 2 * Block.SIZE;
	private static final double MARGINAL = 0.00001;

	/**
	 * @param map
	 *            a map to search
	 * @param value
	 *            the value to find.
	 * @return keys that have given value as value in the given map
	 */
	public static <K, V> Set<K> getKeys(Map<K, V> map, V value) {
		Set<K> keys = new HashSet<>();

		for (K key : map.keySet()) {
			if (map.get(key).equals(value)) {
				keys.add(key);
			}
		}
		return keys;
	}

	/**
	 * Tries to find an unoccupied position for a new block in the given room,
	 * where the given list of blocks are already in that room. Basically this
	 * algorithm picks random points till a free position is found. If no such
	 * place can found, a place is returned that overlaps with other blocks but
	 * at least 0.00001 away from it (to work around ENV-1342).
	 * 
	 * @param room
	 *            the room
	 * @param blocks
	 *            the blocks already in the room.
	 * @param Random
	 *            a properly seeded instance of {@link Random} for random number
	 *            generation.
	 * @return the rectangle2d with a suitable block.
	 */

	public static Rectangle2D findFreePlace(Rectangle2D room, List<Rectangle2D> blocks, Random random) {
		if (room.getWidth() <= Block.SIZE || room.getHeight() <= 0) {
			throw new IllegalArgumentException("Room " + room + " must have width and height >0");
		}
		Rectangle2D block = null;
		int retryCounter = 100; // regular trying while positive,
								// minimum-displacement when negative.
		boolean blockPlacedOK = false;

		while (!blockPlacedOK) {
			// choose random position such that room.contains(block)
			double x = room.getMinX() + (room.getWidth() - Block.SIZE) * random.nextDouble();
			double y = room.getMinY() + (room.getHeight() - Block.SIZE) * random.nextDouble();
			block = new Rectangle2D.Double(x, y, Block.SIZE, Block.SIZE);

			blockPlacedOK = true;
			for (Rectangle2D bl : blocks) {
				blockPlacedOK = blockPlacedOK
						&& checkPlacement(x, y, bl, retryCounter > 0 ? DOUBLE_BLOCK_SIZE : MARGINAL);
			}
			retryCounter--;
		}
		return block;
	}

	/**
	 * @param blockPlacedOK
	 * @param x
	 *            the x-coordinate
	 * @param y
	 *            the y-coordinate
	 * @param bl
	 *            the block
	 * @param minDelta
	 *            the minimum distance (both in x and y) between the new block
	 *            and existing blocks. Use {@link Block#SIZE} to ensure no
	 *            overlap, or smaller if overlap is ok.
	 * @return true iff the distance between bl and x is &ge; minDelta (both in
	 *         x and y direction)
	 */
	public static boolean checkPlacement(double x, double y, Rectangle2D bl, double minDelta) {
		return Math.abs(bl.getCenterX() - x) >= minDelta || Math.abs(bl.getCenterY() - y) >= minDelta;
	}
}
