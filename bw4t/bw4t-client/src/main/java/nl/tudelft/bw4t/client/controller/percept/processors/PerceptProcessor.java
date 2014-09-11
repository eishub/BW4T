package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Parameter;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;

/**
 * PerceptProcessors implement handling of an incoming percept such as holding, bumped, robot, sequenceIndex.
 * The actual calls to PerceptProcessors are made from the {@link ClientMapController}.
 */
public interface PerceptProcessor {
    void process(List<Parameter> parameters, ClientMapController clientMapController);
}
