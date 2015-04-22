package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.map.view.ViewBlock;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;

/**
 * Processes incoming holdingblocks percepts
 * 
 * @author W.Pasman 21oct14
 *
 */
public class HoldingBlocksProcessor implements PerceptProcessor {

	@Override
	public void process(List<Parameter> parameters,
			ClientMapController clientMapController) {

		Stack<ViewBlock> blockstack = new Stack<ViewBlock>();

		// notice, first element in list should have been pushed last.
		// but we push it first now.
		for (Parameter arg : ((ParameterList) (parameters.get(0)))) {
			long id = ((Numeral) arg).getValue().longValue();
			blockstack.push(clientMapController.getBlock(id));
		}
		// to compensate for the wrong push order, we reverse
		Collections.reverse(blockstack);

		clientMapController.getTheBot().setHoldingStack(blockstack);
	}

}
