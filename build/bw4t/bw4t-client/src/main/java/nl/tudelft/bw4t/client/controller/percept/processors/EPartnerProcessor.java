package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;

import org.apache.log4j.Logger;

/**
 * The Class EPartnerProcessor.
 */
public class EPartnerProcessor implements PerceptProcessor {
    
    /** The log4j Logger which displays logs on console. */
    private static final Logger LOGGER = Logger.getLogger(EPartnerProcessor.class.getName());

    /* (non-Javadoc)
     * @see nl.tudelft.bw4t.client.controller.percept.processors.PerceptProcessor#process(java.util.List,
     *  nl.tudelft.bw4t.client.controller.ClientMapController)
     */
    @Override
    public void process(List<Parameter> parameters, ClientMapController clientMapController) {
        long id = ((Numeral) parameters.get(0)).getValue().longValue();
        String entityId = ((Identifier) parameters.get(1)).getValue();
        long holderId = ((Numeral) parameters.get(2)).getValue().longValue();
        ParameterList types = ((ParameterList) parameters.get(3));
        
        ViewEntity theBot = clientMapController.getTheBot();
        ViewEPartner epartner = clientMapController.getKnownEPartner(id);
        
        epartner = initializeEPartner(clientMapController, id, entityId, holderId, epartner, types);
        
        checkHoldingEPartner(id, holderId, theBot);
        if (clientMapController.containsBlock(id)) {
            epartner.setLocation(clientMapController.getBlock(id).getPosition());
        }
        epartner.setPickedUp(holderId >= 0);
    }

    /**
     * Initialize e partner.
     * 
     * @param clientMapController
     *            the client map controller
     * @param id
     *            the epartner id
     * @param entityId
     *            the entity id
     * @param holderId
     *            the holder id
     * @param epartner
     *            the epartner
     * @return the view e partner
     */
    private ViewEPartner initializeEPartner(ClientMapController clientMapController, long id, String entityId,
            long holderId, ViewEPartner epartner, ParameterList types) {
        if (epartner == null) {
            epartner = clientMapController.addEPartner(id, holderId);
        }
        epartner.setName(entityId);
        epartner.setVisible(true);
        List<String> stringTypes = getStringTypes(types);
        epartner.setTypes(stringTypes);
        return epartner;
    }

    /** 
     * Returns a string list of the parameter functionality list.
     * @param types the parameter list
     * @return Returns the String representation of the parameters list.
     */
    private List<String> getStringTypes(ParameterList types) {
        List<String> res = new ArrayList<String>();
        for (Parameter t : types) {
            res.add(((Identifier) t).getValue());
        }
        return res;
    }

    /**
     * Check holding e partner.
     * 
     * @param id
     *            of the epartner
     * @param holderId
     *            the id of the bot which holds the epartner
     * @param theBot
     *            the bot object
     */
    private void checkHoldingEPartner(long id, long holderId, ViewEntity theBot) {
        if (holderId == theBot.getId()) {
            if (id != theBot.getHoldingEpartner()) {
                LOGGER.info("We are now holding the e-partner: " + id);
            }
            theBot.setHoldingEpartner(id);
        } else if (id == theBot.getHoldingEpartner()) {
            theBot.setHoldingEpartner(-1);
        }
    }

}
