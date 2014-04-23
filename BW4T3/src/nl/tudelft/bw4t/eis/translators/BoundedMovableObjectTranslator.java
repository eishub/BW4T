package nl.tudelft.bw4t.eis.translators;

import nl.tudelft.bw4t.BoundedMoveableObject;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

/**
 * Translates {@link BoundedMoveableObject} into a list of {@link Parameter}.
 * 
 * @author Lennard de Rijk
 */
public class BoundedMovableObjectTranslator implements
		Java2Parameter<BoundedMoveableObject> {

	@Override
	public Parameter[] translate(BoundedMoveableObject object)
			throws TranslationException {
		// at(id, x, y)
		Parameter[] params = new Parameter[3];
		params[0] = new Numeral(object.getId());
		params[1] = new Numeral(object.getLocation().getX());
		params[2] = new Numeral(object.getLocation().getY());

		return params;
	}

	@Override
	public Class<? extends BoundedMoveableObject> translatesFrom() {
		return BoundedMoveableObject.class;
	}
}
