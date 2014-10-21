package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

/**
 * Processes holding percept.
 * 
 * 
 */
public class HoldingProcessor implements PerceptProcessor {

	@Override
	public void process(List<Parameter> parameters,
			ClientMapController clientMapController) {
		Long blockId = ((Numeral) parameters.get(0)).getValue().longValue();
		/*
		 * @modified W.Pasman 21oct14 disabled, we now use the holdingblocks
		 * percept.
		 */

		// clientMapController.getTheBot().getHolding().put(blockId,
		// clientMapController.getBlock(blockId));
	}

}
