package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.map.BlockColor;

public class SequenceProcessor implements PerceptProcessor {

    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        clientMapController.getSequence().clear();
        for (Parameter i : parameters) {
            ParameterList list = (ParameterList) i;
            for (Parameter j : list) {
                char letter = ((Identifier) j).getValue().charAt(0);
                clientMapController.getSequence().add(BlockColor.toAvailableColor(letter));
            }
        }
    }

}
