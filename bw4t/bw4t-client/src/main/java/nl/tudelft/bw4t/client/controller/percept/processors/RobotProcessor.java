package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

public class RobotProcessor implements PerceptProcessor {

    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        clientMapController.getTheBot().setId(((Numeral) parameters.get(0)).getValue().longValue());
    }

}
