package nl.tudelft.bw4t.server.eis.translators;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import nl.tudelft.bw4t.server.eis.BlockColor;
import nl.tudelft.bw4t.server.model.blocks.Block;


/**
 * Translates {@link Block} into a list of {@link Parameter} specifying the id and color of the block.
 */
public class BlockWithColorTranslator implements Java2Parameter<BlockColor> {

    @Override
    public Parameter[] translate(BlockColor blockColor) throws TranslationException {
        Block block = blockColor.getBlock();
        // color(id, color)
        Parameter[] params = new Parameter[2];
        params[0] = new Numeral(block.getId());
        if (!blockColor.isColorBlind()) {
            params[1] = new Identifier(String.valueOf(block.getColorId()));
        } else {
            params[1] = new Identifier(String.valueOf(nl.tudelft.bw4t.map.BlockColor.DARK_GRAY));
        }
        
        return params;
    }

    @Override
    public Class<? extends BlockColor> translatesFrom() {
        return BlockColor.class;
    }
}
