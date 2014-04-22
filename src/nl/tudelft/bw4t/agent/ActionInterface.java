package nl.tudelft.bw4t.agent;

import nl.tudelft.bw4t.message.BW4TMessage;
import eis.exceptions.ActException;

/**
 * Interface for the actions that an agent should be able to perform
 * 
 * @author trens
 * 
 */
public interface ActionInterface {

	public void goTo(double x, double y) throws ActException;

	public void goToBlock(long id) throws ActException;

	public void goTo(String navPointId) throws ActException;

	public void pickUp() throws ActException;

	public void putDown() throws ActException;

	public void sendMessage(String receiver, BW4TMessage message)
			throws ActException;

}
