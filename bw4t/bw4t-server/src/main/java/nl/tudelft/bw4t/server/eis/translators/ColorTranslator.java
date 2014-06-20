package nl.tudelft.bw4t.server.eis.translators;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

import nl.tudelft.bw4t.map.BlockColor;

/**
 * Translates a pure {@link BlockColor} to EIS
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
