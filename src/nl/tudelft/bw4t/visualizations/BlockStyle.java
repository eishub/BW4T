package nl.tudelft.bw4t.visualizations;

import java.awt.Color;

import nl.tudelft.bw4t.blocks.Block;

public class BlockStyle extends BoundedMoveableObjectStyle<Block> {
  @Override
  public Color getColor(Block block) {
    return block.getColor();
  }
}
