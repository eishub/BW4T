package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

/**
 * Processes incoming gripperCapacity percepts.
 * 
 * @author W.Pasman 22oct14
 *
 */
public class GripperCapacityProcessor implements PerceptProcessor {

	@Override
	public void process(List<Parameter> parameters,
			ClientMapController clientMapController) {
		int capacity = ((Numeral) parameters.get(0)).getValue().intValue();

		clientMapController.getTheBot().setGripperCapacity(capacity);
	}

}
