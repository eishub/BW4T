package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;

public class NegationProcessor implements PerceptProcessor {

    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        Function function = (Function) parameters.get(0);
        if ("occupied".equals(function.getName())) {
            LinkedList<Parameter> paramOcc = function.getParameters();
            clientMapController.removeOccupiedRoom(((Identifier) paramOcc.get(0)).getValue());
        } else if ("holding".equals(function.getName())) {
            clientMapController.getTheBot().getHolding().remove(((Numeral) function.getParameters().get(0)).getValue());
        } else if ("bumped".equals(function.getName())) {
            clientMapController.getTheBot().setCollided(false);
        }
    }

}
