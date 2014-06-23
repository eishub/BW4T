package nl.tudelft.bw4t.server.model.zone;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.server.logging.BW4TFileAppender;
import nl.tudelft.bw4t.server.logging.BotLog;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.blocks.Block;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;

import org.apache.log4j.Logger;

/**
 * Representation of a room where blocks can be dropped into.
 */
public class DropZone extends Room {

    /**
     * The log4j logger, logs to the console.
     */
    private static final Logger LOGGER = Logger.getLogger(DropZone.class);

    /** The sequence of blocks that are to be dropped in here */
    private List<BlockColor> sequence = new ArrayList<BlockColor>();

    /**
     * The current index of the to-be-dropped block.
     */
    private int sequenceIndex;

    /**
     * Creates a new dropzone with an empty sequence.
     * 
     * @param dropzone
     *            The zone in which the dropzone should be located.
     * @param sequence2
     *            the sequence of blocks that need to be brought to this dropzone
     * @param context
     *            The context in which the dropzone should be located.
     */
    public DropZone(Zone dropzone, List<BlockColor> sequence2, BW4TServerMap context) {
        super(Color.GRAY, dropzone, context);
        this.setSequence(sequence);
        sequenceIndex = 0;
    }

    /**
     * set the sequence - the ordered list of objects to be dropped in the dropzone.
     * 
     * @param colors
     *            list of colors as Strings.
     */
    public void setSequence(List<BlockColor> colors) {
        sequence = colors;
        
        StringBuilder s = new StringBuilder();
        s.append("sequence");
        for (BlockColor col : sequence) {
            s.append(" " + col.getLetter());
        }
        String message = s.toString();        
        LOGGER.log(BotLog.BOTLOG, message);
        
    }

    /**
     * Returns the color identifiers of blocks that need to be delivered in order to this dropzone.
     * 
     * @return List<BlockColor>
     */
    public List<BlockColor> getSequence() {
        return sequence;
    }

    public int getSequenceIndex() {
        return sequenceIndex;
    }

    /**
     * Called when a block is dropped. If the block has been dropped in this zone the block will be removed from the
     * context and if the block was of the right color the sequence will advance.
     * 
     * This function will also log the drop events in the drop zone.
     * 
     * @param block
     *            The block that has been dropped.
     * @param robot
     *            The robot that drops the block
     * @return true if bot is in dropzone, else false.
     */
    public boolean dropped(Block block, AbstractRobot robot) {
        if (!getBoundingBox().intersects(robot.getBoundingBox())) {
            // The block isn't dropped in this zone
            return false;
        }

        if (!sequence.isEmpty() && sequenceIndex != sequence.size()) {
            if (sequence.get(sequenceIndex).equals(block.getColorId())) {
                // Correct block has been dropped in
                sequenceIndex++;
                robot.getAgentRecord().addGoodDrop();
                
                if (sequenceIndex == sequence.size()) {
                     BW4TFileAppender.logFinish(System.currentTimeMillis(), "Time to finish sequence is ");
                }
            } else {
                robot.getAgentRecord().addWrongDrop();
            }
        }

        return true;
    }

    /**
     * check if the full sequence has been completed
     * 
     * @return true if full sequence has been completed (all required boxes were dropped), else false.
     */
    public boolean sequenceComplete() {
        return sequenceIndex >= sequence.size();
    }
}
