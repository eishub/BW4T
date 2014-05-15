package nl.tudelft.bw4t.eis.translators;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.eis.BlockColor;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

/**
 * Translates {@link Block} into a list of {@link Parameter} specifying the id
 * and color of the block.
 * 
 * @author Lennard de Rijk
 */
public class BlockWithColorTranslator implements Java2Parameter<BlockColor> {

	@Override
	public Parameter[] translate(BlockColor blockColor)
			throws TranslationException {
		Block block = blockColor.getBlock();
		// color(id, color)
		Parameter[] params = new Parameter[2];
		params[0] = new Numeral(block.getId());
		params[1] = new Identifier(String.valueOf(block.getColorId()));

		return params;
	}

	@Override
	public Class<? extends BlockColor> translatesFrom() {
		return BlockColor.class;
	}
}
