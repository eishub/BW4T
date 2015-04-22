package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import eis.iilang.Parameter;

/**
 * PerceptProcessors implement handling of an incoming percept such as holding,
 * bumped, robot, sequenceIndex. The actual calls to PerceptProcessors are made
 * from the {@link ClientMapController}.
 * 
 */
public interface PerceptProcessor {
	void process(List<Parameter> parameters,
			ClientMapController clientMapController);
}
