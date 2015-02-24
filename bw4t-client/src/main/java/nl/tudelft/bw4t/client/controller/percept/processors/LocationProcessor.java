package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;

public class LocationProcessor implements PerceptProcessor {

    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        double x = ((Numeral) parameters.get(0)).getValue().doubleValue();
        double y = ((Numeral) parameters.get(1)).getValue().doubleValue();
        clientMapController.getTheBot().setLocation(x, y);
    }

}
