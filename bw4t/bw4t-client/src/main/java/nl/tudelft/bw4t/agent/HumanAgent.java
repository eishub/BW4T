package nl.tudelft.bw4t.agent;

import java.rmi.RemoteException;

import eis.exceptions.ActException;
import eis.iilang.Action;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

/**
 * Class that represents an agent that is controlled via the BW4TRenderer
 */
public class HumanAgent extends BW4TAgent {

    /**
	 * Create a new human agent that should be linked to a BW4TRenderer
	 * 
	 * @param agentId
	 *            , the id of this agent
	 * @param env
	 *            , the remote environment to which the human agent should connect.
	 */
    public HumanAgent(String agentId, RemoteEnvironment env) {
        super(agentId, env);
    }

    /**
     * Pick up a certain epartner in the world
     * 
     * @throws ActException
     */
    public void pickUpEPartner() throws ActException {
        try {
            getEnvironment().performEntityAction(entityId, new Action("pickUpEPartner"));
        } catch (Exception e) {
            ActException ex = new ActException("pickUpEPartner failed", e);
            ex.setType(ActException.FAILURE);
            throw ex;
        }
    }

    /**
     * Put down a block in the world
     * 
     * @throws ActException
     */
    public void putDownEPartner() throws ActException {
        try {
            getEnvironment().performEntityAction(entityId, new Action("putDownEPartner"));
        } catch (RemoteException e) {
            ActException ex = new ActException("putDownEPartner failed", e);
            ex.setType(ActException.FAILURE);
            throw ex;
        }
    }
}
