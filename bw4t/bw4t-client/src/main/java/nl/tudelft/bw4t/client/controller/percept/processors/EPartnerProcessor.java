package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.List;

import org.apache.log4j.Logger;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;

public class EPartnerProcessor implements PerceptProcessor {
    
    /**
     * The log4j Logger which displays logs on console
     */
    private static final Logger LOGGER = Logger.getLogger(EPartnerProcessor.class.getName());

    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        long id = ((Numeral) parameters.get(0)).getValue().longValue();
        long holderId = ((Numeral) parameters.get(1)).getValue().longValue();
        ViewEntity theBot = clientMapController.getTheBot();
        
        ViewEPartner epartner = clientMapController.getViewEPartner(id);
        if (epartner == null) {
            epartner = clientMapController.addEPartner(id, holderId);
        }
        if (holderId == theBot.getId()) {
            if (id != theBot.getHoldingEpartner()){
                LOGGER.info("We are now holding the e-partner: " + id);
            }
            theBot.setHoldingEpartner(id);
        } else if (id == theBot.getHoldingEpartner()) {
            theBot.setHoldingEpartner(-1);
        }
        if (clientMapController.containsBlock(id)) {
            epartner.setLocation(clientMapController.getBlock(id).getPosition());
        }
        epartner.setPickedUp(holderId >= 0);
    }

}
