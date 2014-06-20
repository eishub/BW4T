package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

public class HoldingProcessor implements PerceptProcessor {

    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        Long blockId = ((Numeral) parameters.get(0)).getValue().longValue();
        clientMapController.getTheBot().getHolding().put(blockId, clientMapController.getBlock(blockId));
    }

}
