package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.view.ViewBlock;

public class ColorProcessor implements PerceptProcessor {

    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        long id = ((Numeral) parameters.get(0)).getValue().longValue();
        char color = ((Identifier) parameters.get(1)).getValue().charAt(0);

        ViewBlock b = clientMapController.getBlock(id);
        b.setColor(BlockColor.toAvailableColor(color));
        clientMapController.getVisibleBlocks().add(b);
    }

}
