package nl.tudelft.bw4t.client.controller.percept.processors;

import java.util.List;

import eis.iilang.Parameter;
import nl.tudelft.bw4t.client.controller.ClientMapController;

// REMOVE THIS
public class NegationProcessor implements PerceptProcessor {

	@Override
	public void process(List<Parameter> parameters, ClientMapController clientMapController) {
		// Function function = (Function) parameters.get(0);
		// if ("occupied".equals(function.getName())) {
		// List<Parameter> paramOcc = function.getParameters();
		// Zone zone = clientMapController.getMap().getZone(
		// ((Identifier) paramOcc.get(0)).getValue());
		// if (zone == null) {
		// throw new IllegalArgumentException("Unknown zone "
		// + paramOcc.get(0));
		// }
		// clientMapController.removeOccupiedRoom(zone);
		// } else if ("holding".equals(function.getName())) {
		// clientMapController
		// .getTheBot()
		// .getHolding()
		// .remove(((Numeral) function.getParameters().get(0))
		// .getValue());
		// } else if ("bumped".equals(function.getName())) {
		// clientMapController.getTheBot().setCollided(false);
		// }
	}

}
