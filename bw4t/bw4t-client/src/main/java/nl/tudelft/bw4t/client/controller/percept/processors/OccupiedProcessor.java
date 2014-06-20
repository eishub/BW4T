package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import eis.iilang.Identifier;
import eis.iilang.Parameter;

public class OccupiedProcessor implements PerceptProcessor {

    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        clientMapController.addOccupiedRoom(((Identifier) parameters.get(0)).getValue());
    }

}
