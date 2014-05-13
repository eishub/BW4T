package nl.tudelft.bw4t.zone;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.server.BW4TLogger;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;

/**
 * Representation of a room where blocks can be dropped into.
 * 
 * @author Lennard de Rijk
 */
public class DropZone extends Room {

	/** The sequence of blocks that are to be dropped in here */
	private List<BlockColor> sequence = new ArrayList<BlockColor>();

	/**
	 * The current index of the to-be-dropped block.
	 */
	private int sequenceIndex;

	/**
	 * Creates a new dropzone with an empty sequence.
	 * 
	 * @param space
	 *            The space in which the dropzone should be located.
	 * @param context
	 *            The context in which the dropzone should be located.
	 */
	public DropZone(nl.tudelft.bw4t.map.Zone dropzone,
			ContinuousSpace<Object> space, Context<Object> context) {
		super(Color.GRAY, dropzone, space, context);
		sequence = new LinkedList<BlockColor>();
		sequenceIndex = 0;
	}

	/**
	 * set the sequence - the ordered list of objects to be dropped in the
	 * dropzone.
	 * 
	 * @param colors
	 *            list of colors as Strings.
	 */
	public void setSequence(List<BlockColor> colors) {
		sequence = colors;
		try {
			BW4TLogger.getInstance().logSequence(colors);
		} catch (IOException e) {
			System.out.println("WARNING. log file failed to write:"
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Returns the color identifiers of blocks that need to be delivered in
	 * order to this dropzone.
	 */
	public List<BlockColor> getSequence() {
		return sequence;
	}

	public int getSequenceIndex() {
		return sequenceIndex;
	}

	/**
	 * Called when a block is dropped. If the block has been dropped in this
	 * zone the block will be removed from the context and if the block was of
	 * the right color the sequence will advance.
	 * <p>
	 * This function will also log the drop events in the drop zone.
	 * 
	 * @param block
	 *            The block that has been dropped.
	 * @param robot
	 *            The robot that drops the block
	 * @return true if bot is in dropzone, else false.
	 */
	public boolean dropped(Block block, Robot robot) {
		if (!getBoundingBox().intersects(robot.getBoundingBox())) {
			// The block isn't dropped in this zone
			return false;
		}

		if (sequence.size() > 0 && sequenceIndex != sequence.size()) {
			if (sequence.get(sequenceIndex).equals(block.getColorId())) {
				// Correct block has been dropped in
				sequenceIndex++;
				BW4TLogger.getInstance().logGoodDrop(robot.getName());
				if (sequenceIndex == sequence.size()) {
					BW4TLogger.getInstance().logCompletedSequence();
				}
			} else {
				BW4TLogger.getInstance().logWrongDrop(robot.getName());
			}
		}

		return true;
	}

	/**
	 * check if the full sequence has been completed
	 * 
	 * @return true if full sequence has been completed (all required boxes were
	 *         dropped), else false.
	 */
	public boolean sequenceComplete() {
		return sequenceIndex >= sequence.size();
	}
}
