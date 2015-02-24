package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Parameter;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;

/**
 * Created by Calvin on 20/06/2014.
 */
public class BumpedProcessor implements PerceptProcessor {
    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        clientMapController.getTheBot().setCollided(true);
    }
}
