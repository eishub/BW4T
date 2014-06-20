package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.map.view.ViewEntity;

public class RobotOldTargetUnreachableProcessor implements PerceptProcessor {

    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        long id = ((Numeral) parameters.get(0)).getValue().longValue();
        boolean oldTargetUnreachable = ((Numeral) parameters.get(1)).getValue().intValue() == 1;
        ViewEntity theBot = clientMapController.getTheBot();
        if (id == theBot.getId()) {
            if (oldTargetUnreachable) {
                theBot.setCollided(false);
            }
        }
    }

}
