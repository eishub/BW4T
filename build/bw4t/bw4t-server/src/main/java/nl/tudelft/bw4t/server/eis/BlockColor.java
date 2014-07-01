package nl.tudelft.bw4t.server.eis;

import nl.tudelft.bw4t.server.model.blocks.Block;

/**
 * Wraps around a block to pass along the color percept
 */
public class BlockColor {

    private final Block block;
    
    /** Boolean for when the robot is colorBlind */
    private final boolean isColorBlind;

    /**
     * Create a new {@link BlockColor} for the given block.
     * 
     * @param b
     *            a {@link Block}.
     * @param isCB
     *            the concerned robot is color blind or not. 
     */
    public BlockColor(Block b, boolean isCB) {
        this.block = b;
        this.isColorBlind = isCB;
    }

    /**
     * @return The block
     */
    public Block getBlock() {
        return block;
    }
    
    public boolean isColorBlind() {
        return isColorBlind;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((block == null) ? 0 : block.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null 
                || !(obj instanceof BlockColor)) {
            return false; 
        }
        BlockColor other = (BlockColor) obj;
        if (this == obj) {
            return true;
        } else if (getClass() != obj.getClass() 
                || (block == null && other.block != null) 
                || !block.equals(other.block)) {
            return false;
        }
        return true;
    }
}
