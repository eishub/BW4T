package nl.tudelft.bw4t.client.controller.percept.processors;

import java.awt.geom.Point2D;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

public class PositionProcessor implements PerceptProcessor {

	@Override
	public void process(List<Parameter> parameters,
			ClientMapController clientMapController) {
		long id = ((Numeral) parameters.get(0)).getValue().longValue();
		double x = ((Numeral) parameters.get(1)).getValue().doubleValue();
		double y = ((Numeral) parameters.get(2)).getValue().doubleValue();

		ViewBlock b = clientMapController.getBlock(id);
		b.setPosition(new Point2D.Double(x, y));
		ViewEPartner ep = clientMapController.getKnownEPartner(id);
		if (ep != null) {
			ep.setLocation(b.getPosition());
		} else {
			ViewEntity bot = clientMapController.getKnownRobot(id);
			if (bot != null) {
				bot.setLocation(b.getPosition());
				clientMapController.addVisibleRobot(bot);
			}
		}
	}

}
