package nl.tudelft.bw4t.eis;

import nl.tudelft.bw4t.blocks.Block;

/**
 * Wraps around a block to pass along the color percept
 */
public class BlockColor {

    private final Block block;

    /**
     * Create a new {@link BlockColor} for the given block.
     * 
     * @param b
     *            a {@link Block}.
     */
    public BlockColor(Block b) {
        this.block = b;
    }

    /**
     * @return The block
     */
    public Block getBlock() {
        return block;
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
        BlockColor other = (BlockColor) obj;
        if (this == obj) {
            return true;
        } else if (obj == null 
                || getClass() != obj.getClass() 
                || (block == null && other.block != null) 
                || !block.equals(other.block)) {
            return false;
        }
        return true;
    }
}
