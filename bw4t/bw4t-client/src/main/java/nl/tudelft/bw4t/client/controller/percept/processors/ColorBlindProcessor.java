package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import eis.iilang.Parameter;
import eis.iilang.TruthValue;

/**
 * @author W.Pasman 5nov14.
 */
public class ColorBlindProcessor implements PerceptProcessor {
	@Override
	public void process(List<Parameter> parameters,
			ClientMapController clientMapController) {

		clientMapController.getTheBot().setColorBlind(
				((TruthValue) parameters.get(0)).getBooleanValue());
	}
}
