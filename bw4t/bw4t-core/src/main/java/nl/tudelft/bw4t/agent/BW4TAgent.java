package nl.tudelft.bw4t.agent;

import java.rmi.RemoteException;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.BW4TRemoteEnvironment;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageTranslator;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * Java agent that can control an entity
 * 
 * @author trens
 * 
 */
public class BW4TAgent extends Thread implements ActionInterface {

	String agentId, entityId;
	boolean environmentKilled;
	private BW4TRemoteEnvironment bw4tenv;

	/**
	 * Create a new BW4TAgent that can be registered to an entity
	 * 
	 * @param agentId
	 *            , the Id of this agent used for registering to an entity
	 * @param env
	 *            the remote environment
	 */
	public BW4TAgent(String agentId, BW4TRemoteEnvironment env) {
		this.agentId = agentId;
		this.bw4tenv = env;
	}

	public BW4TRemoteEnvironment getEnvironment() {
		return bw4tenv;
	}

	/**
	 * Register an entity to this agent
	 * 
	 * @param entityId
	 *            , the Id of the entity
	 */
	public void registerEntity(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * Get the Id of this agent
	 * 
	 * @return the Id of this agent
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * Run the reasoning process of this agent
	 */
	public void run() {
		if (environmentKilled)
			return;
	}

	/**
	 * Perform a goTo action towards a point in the world
	 * 
	 * @param x
	 *            , the x coordinate of the point
	 * @param y
	 *            , the y coordinate of the point
	 * @throws ActException
	 */
	@Override
	public void goTo(double x, double y) throws ActException {
		try {
			Parameter[] xParam = Translator.getInstance()
					.translate2Parameter(x);
			Parameter[] yParam = Translator.getInstance()
					.translate2Parameter(y);
			Parameter[] parameters = new Parameter[2];
			parameters[0] = xParam[0];
			parameters[1] = yParam[0];
			bw4tenv.performEntityAction(entityId,
					new Action("goTo", parameters));
		} catch (Exception e) {
			ActException ex = new ActException("goTo", e);
			ex.setType(ActException.FAILURE);
			throw ex;
		}
	}

	/**
	 * Perform a goToNavPointAction towards a navpoint in the world
	 * 
	 * @param id
	 *            , the id of the navpoint
	 * @throws ActException
	 */
	@Override
	public void goToBlock(long id) throws ActException {
		try {
			Parameter[] idParam = Translator.getInstance().translate2Parameter(
					id);
			bw4tenv.performEntityAction(entityId, new Action("goToBlock",
					idParam));
		} catch (Exception e) {
			ActException ex = new ActException("goToBlock failed", e);
			ex.setType(ActException.FAILURE);
			throw ex;
		}
	}

	@Override
	public void goTo(String navPoint) throws ActException {
		try {
			Parameter[] idParam = Translator.getInstance().translate2Parameter(
					navPoint);
			bw4tenv.performEntityAction(entityId, new Action("goTo", idParam));
		} catch (Exception e) {
			ActException ex = new ActException("goTo failed", e);
			ex.setType(ActException.FAILURE);
			throw ex;
		}
	}

	/**
	 * Pick up a certain block in the world
	 * 
	 * @param id
	 *            , the id of the block
	 * @throws ActException
	 */
	@Override
	public void pickUp() throws ActException {
		try {
			bw4tenv.performEntityAction(entityId, new Action("pickUp"));
		} catch (Exception e) {
			ActException ex = new ActException("pickUp failed", e);
			ex.setType(ActException.FAILURE);
			throw ex;
		}
	}

	/**
	 * Put down a block in the world
	 * 
	 * @throws ActException
	 */
	@Override
	public void putDown() throws ActException {
		try {
			bw4tenv.performEntityAction(entityId, new Action("putDown"));
		} catch (RemoteException e) {
			ActException ex = new ActException("putDown failed", e);
			ex.setType(ActException.FAILURE);
			throw ex;
		}
	}

	/**
	 * Sends a message to certain other agents after translating it to the right
	 * format
	 * 
	 * @param message
	 *            , a BW4TMessage
	 * @param receiver
	 *            , a receiver (can be either all or the id of another agent)
	 * @throws ActException
	 */
	@Override
	public void sendMessage(String receiver, BW4TMessage message)
			throws ActException {
		this.sendMessage(receiver, MessageTranslator.translateMessage(message));
	}

	/**
	 * Sends a message to certain other agents
	 * 
	 * @param message
	 *            , the translated message (as String)
	 * @param receiver
	 *            , a receiver (can be either all or the id of another agent)
	 * @throws ActException
	 */
	private void sendMessage(String receiver, String message)
			throws ActException {
		try {
			Parameter[] messageParam = Translator.getInstance()
					.translate2Parameter(message);
			Parameter[] receiverParam = Translator.getInstance()
					.translate2Parameter(receiver);
			Parameter[] parameters = new Parameter[2];
			parameters[0] = receiverParam[0];
			parameters[1] = messageParam[0];
			bw4tenv.performEntityAction(entityId, new Action("sendMessage",
					parameters));
		} catch (Exception e) {
			ActException ex = new ActException("putDown failed", e);
			ex.setType(ActException.FAILURE);
			throw ex;
		}
	}

	/**
	 * Get all percepts for the associated entity
	 * 
	 * @return a list of percepts
	 */
	public LinkedList<Percept> getPercepts() throws PerceiveException,
			NoEnvironmentException {
		return bw4tenv.getAllPerceptsFromEntity(entityId);
	}

	public void setKilled() {
		environmentKilled = true;
	}

}
