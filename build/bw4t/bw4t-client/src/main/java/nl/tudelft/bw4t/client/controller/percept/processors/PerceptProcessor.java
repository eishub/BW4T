package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Parameter;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;

public interface PerceptProcessor {
    void process(List<Parameter> parameters, ClientMapController clientMapController);
}
