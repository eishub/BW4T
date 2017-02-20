package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.LinkedList;
import java.util.List;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.map.BlockColor;

public class SequenceProcessor implements PerceptProcessor {

	@Override
	public void process(List<Parameter> parameters,
			ClientMapController clientMapController) {
		List<BlockColor> sequence = new LinkedList<>();
		for (Parameter i : parameters) {
			ParameterList list = (ParameterList) i;
			for (Parameter j : list) {
				char letter = ((Identifier) j).getValue().charAt(0);
				sequence.add(BlockColor.toAvailableColor(letter));
			}
		}
		clientMapController.setSequence(sequence);
	}

}
