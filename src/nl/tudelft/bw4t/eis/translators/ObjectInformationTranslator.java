package nl.tudelft.bw4t.eis.translators;

import nl.tudelft.bw4t.eis.ObjectInformation;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

public class ObjectInformationTranslator implements
		Java2Parameter<ObjectInformation> {

	@Override
	public Parameter[] translate(ObjectInformation objI)
			throws TranslationException {
		// color(id, color)
		Parameter[] params = new Parameter[3];
		params[0] = new Numeral(objI.getId());
		params[1] = new Numeral(objI.getX());
		params[2] = new Numeral(objI.getY());

		return params;
	}

	@Override
	public Class<? extends ObjectInformation> translatesFrom() {
		return ObjectInformation.class;
	}

}
