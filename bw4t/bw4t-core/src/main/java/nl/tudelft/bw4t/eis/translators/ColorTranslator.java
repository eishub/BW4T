package nl.tudelft.bw4t.eis.translators;

import nl.tudelft.bw4t.map.BlockColor;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

/**
 * Translates a pure {@link BlockColor} to EIS
 * 
 * @author W.Pasman
 * 
 */
public class ColorTranslator implements Java2Parameter<BlockColor> {

	@Override
	public Parameter[] translate(BlockColor o) throws TranslationException {
		return new Parameter[] { new Identifier(o.toString()) };
	}

	@Override
	public Class<? extends BlockColor> translatesFrom() {
		return BlockColor.class;
	}

}
