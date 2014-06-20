package nl.tudelft.bw4t.client.controller.percept.processors;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

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
        
        ViewEntity theBot = clientMapController.getTheBot();
        ViewEPartner epartner = clientMapController.getKnownEPartner(id);
        
        epartner = initializeEPartner(clientMapController, id, entityId, holderId, epartner);
        
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
            long holderId, ViewEPartner epartner) {
        if (epartner == null) {
            epartner = clientMapController.addEPartner(id, holderId);
        }
        epartner.setName(entityId);
        epartner.setVisible(true);
        return epartner;
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
