package nl.tudelft.bw4t.eis.translators;

import java.awt.geom.Point2D;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

public class PointTranslator implements Java2Parameter<Point2D> {

	@Override
	public Parameter[] translate(Point2D point) throws TranslationException {
		// color(id, color)
		Parameter[] params = new Parameter[2];
		params[0] = new Numeral(point.getX());
		params[1] = new Numeral(point.getY());

		return params;
	}

	@Override
	public Class<? extends Point2D> translatesFrom() {
		return Point2D.class;
	}

}
