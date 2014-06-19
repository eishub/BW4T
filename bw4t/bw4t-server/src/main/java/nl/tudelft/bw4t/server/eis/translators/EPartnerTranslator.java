package nl.tudelft.bw4t.server.eis.translators;

import nl.tudelft.bw4t.server.model.epartners.EPartner;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

/**
 * Translates {@link EPartner} objects into parameters that can be used in eis.
 */
public class EPartnerTranslator implements Java2Parameter<EPartner> {

    @Override
    public Parameter[] translate(EPartner o) throws TranslationException {
        Parameter[] params = new Parameter[3];
        params[0] = new Numeral(o.getId());
        params[1] = new Identifier(o.getName());
        final IRobot holder = o.getHolder();
        if (holder == null) {
            params[2] = new Numeral(-1);
        } else {
            params[2] = new Numeral(holder.getId());
        }
        return params;
    }

    @Override
    public Class<? extends EPartner> translatesFrom() {
        return EPartner.class;
    }

}
