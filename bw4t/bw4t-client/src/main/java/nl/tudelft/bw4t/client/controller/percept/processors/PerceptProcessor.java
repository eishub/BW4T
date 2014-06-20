package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import eis.iilang.Parameter;

public interface PerceptProcessor {
    void process(List<Parameter> parameters, ClientMapController clientMapController);
}
