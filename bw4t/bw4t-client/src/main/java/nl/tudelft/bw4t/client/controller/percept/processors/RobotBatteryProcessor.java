package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.gui.listeners.BatteryProgressBarListener;

public class RobotBatteryProcessor implements PerceptProcessor {

    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        double battery = ((Numeral) parameters.get(0)).getValue().doubleValue();
        clientMapController.getTheBot().setBatteryLevel(battery);

        for (BatteryProgressBarListener listener : BatteryProgressBarListener.getListeners()) {
            listener.update();
        }
    }

}
