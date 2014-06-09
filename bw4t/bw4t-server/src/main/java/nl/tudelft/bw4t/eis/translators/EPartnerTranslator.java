package nl.tudelft.bw4t.eis.translators;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import nl.tudelft.bw4t.model.blocks.EPartner;
import nl.tudelft.bw4t.model.robots.handicap.IRobot;

/**
 * Translates {@link EPartner} objects into parameters that can be used in eis.
 */
public class EPartnerTranslator implements Java2Parameter<EPartner> {

    @Override
    public Parameter[] translate(EPartner o) throws TranslationException {
        Parameter[] params = new Parameter[2];
        params[0] = new Numeral(o.getId());
        final IRobot holder = o.getHolder();
        if (holder == null) {
            params[1] = new Numeral(-1);
        } else {
            params[1] = new Numeral(holder.getId());
        }
        return params;
    }

    @Override
    public Class<? extends EPartner> translatesFrom() {
        return EPartner.class;
    }

}
