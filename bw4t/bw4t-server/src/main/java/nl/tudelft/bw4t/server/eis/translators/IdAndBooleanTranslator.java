package nl.tudelft.bw4t.server.eis.translators;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import nl.tudelft.bw4t.server.eis.IdAndBoolean;

public class IdAndBooleanTranslator implements Java2Parameter<IdAndBoolean> {

    @Override
    public Parameter[] translate(IdAndBoolean idNBool) throws TranslationException {
        Parameter[] params = new Parameter[2];
        params[0] = new Numeral(idNBool.getId());
        params[1] = new Numeral(idNBool.getBool() ? 1 : 0);
        return params;
    }

    @Override
    public Class<? extends IdAndBoolean> translatesFrom() {
        return IdAndBoolean.class;
    }

}
