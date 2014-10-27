package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.map.BlockColor;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;

public class SequenceProcessor implements PerceptProcessor {

	@Override
	public void process(List<Parameter> parameters,
			ClientMapController clientMapController) {
		clientMapController.getSequence().clear();
		for (Parameter i : parameters) {
			ParameterList list = (ParameterList) i;
			for (Parameter j : list) {
				char letter = ((Identifier) j).getValue().charAt(0);
				clientMapController.addSequenceColor(BlockColor
						.toAvailableColor(letter));
			}
		}
	}

}
