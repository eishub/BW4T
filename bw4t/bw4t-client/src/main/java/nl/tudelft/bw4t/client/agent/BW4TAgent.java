package nl.tudelft.bw4t.client.agent;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nl.tudelft.bw4t.client.environment.PerceptsHandler;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageTranslator;
import nl.tudelft.bw4t.map.view.ViewEntity;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.EntityException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * Java agent that can control an entity
 */
public class BW4TAgent extends Thread implements ActionInterface {

	/**
	 * Information storage about the agent.
	 */
    protected String agentId, entityId;
    protected boolean environmentKilled;
    private RemoteEnvironment bw4tenv;
    
    private BotConfig botConfig;
    private EPartnerConfig epartnerConfig;
    
    private final Map<BW4TAgent, BW4TClientGUI> allAgents;

    /**
     * Create a new BW4TAgent that can be registered to an entity.
     * 
     * @param agentId
     *            , the id of this agent used for registering to an entity.
     * @param env
     *            the remote environment.
     */
    public BW4TAgent(String agentId, RemoteEnvironment env, Map<BW4TAgent, BW4TClientGUI> allAgents) {
        this.agentId = agentId;
        this.bw4tenv = env;
        this.allAgents = allAgents;
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
     * Run the reasoning process of this agent
     */
    public void run() {
        if (environmentKilled) {
            return;
        }
    }
    
    /**
     * Gets an agent from the name.
     * @param name The name of the agent.
     * @return The agent.
     */
    public BW4TAgent getAgentFromName(String name) {
        for (BW4TAgent agent : allAgents.keySet()) {
            if (agent.getName().equals(name)) {
                return agent;
            }
        }
        return null;
    }
    
    /**
     * Gets all agent with this type.
     * @param type The type of the agent.
     * @return A list with the agents.
     */
    public LinkedList<BW4TAgent> getAgentsWithType(String type) {
        LinkedList<BW4TAgent> res = new LinkedList<BW4TAgent>();
        for (BW4TAgent agent : allAgents.keySet()) {
            try {
                if (agent.getEnvironment().getType(agent.getAgentId()).equals(type)) {
                    res.add(agent);
                }
            } catch (EntityException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goTo(double x, double y) throws ActException {
        try {
            Parameter[] xParam = Translator.getInstance().translate2Parameter(x);
            Parameter[] yParam = Translator.getInstance().translate2Parameter(y);
            Parameter[] parameters = new Parameter[2];
            parameters[0] = xParam[0];
            parameters[1] = yParam[0];
            bw4tenv.performEntityAction(entityId, new Action("goTo", parameters));
        } catch (RemoteException | TranslationException e) {
            ActException ex = new ActException("goTo", e);
            ex.setType(ActException.FAILURE);
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToBlock(long id) throws ActException {
        try {
            Parameter[] idParam = Translator.getInstance().translate2Parameter(id);
            bw4tenv.performEntityAction(entityId, new Action("goToBlock", idParam));
        } catch (TranslationException | RemoteException e) {
            ActException ex = new ActException("goToBlock failed", e);
            ex.setType(ActException.FAILURE);
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goTo(String navPoint) throws ActException {
        try {
            Parameter[] idParam = Translator.getInstance().translate2Parameter(navPoint);
            bw4tenv.performEntityAction(entityId, new Action("goTo", idParam));
        } catch (TranslationException | RemoteException e) {
            ActException ex = new ActException("goTo failed", e);
            ex.setType(ActException.FAILURE);
            throw ex;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void navigateObstacles() throws ActException {
        try {
            bw4tenv.performEntityAction(entityId, new Action("navigateObstacles"));
        } catch (RemoteException e) {
            ActException ex = new ActException("navigateObstacles failed", e);
            ex.setType(ActException.FAILURE);
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pickUp() throws ActException {
        try {
            bw4tenv.performEntityAction(entityId, new Action("pickUp"));
        } catch (RemoteException e) {
            ActException ex = new ActException("pickUp failed", e);
            ex.setType(ActException.FAILURE);
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(String receiver, BW4TMessage message) throws ActException {
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
     * 			  , if an attempt to perform an action has failed.
     */
    private void sendMessage(String receiver, String message) throws ActException {
        try {
            Parameter[] messageParam = Translator.getInstance().translate2Parameter(message);
            Parameter[] receiverParam = Translator.getInstance().translate2Parameter(receiver);
            Parameter[] parameters = new Parameter[2];
            parameters[0] = receiverParam[0];
            parameters[1] = messageParam[0];
            bw4tenv.performEntityAction(entityId, new Action("sendMessage", parameters));
        } catch (RemoteException | TranslationException e) {
            ActException ex = new ActException("putDown failed", e);
            ex.setType(ActException.FAILURE);
            throw ex;
        }
    }

    /**
	 * Get all percepts for the associated entity
	 * 
	 * @return a list of percepts
	 * @throws PerceiveException if there was a problem retrieving the percepts.
	 */
	public LinkedList<Percept> getPercepts() throws PerceiveException {
	    return (LinkedList<Percept>) PerceptsHandler.getAllPerceptsFromEntity(entityId, bw4tenv);
	}

	public void setKilled() {
        environmentKilled = true;
    }
	
	public String getAgentId() {
	    return agentId;
	}

	public RemoteEnvironment getEnvironment() {
	    return bw4tenv;
	}
	
	/**
	 * Whether this agent can pick up another object (box/e-partner) based
	 * on their gripper capacity and the amount of objects they're already
	 * holding. 
	 * @param sameEntity The {@link ViewEntity} type of this agent.
	 * @return Whether this agent can pick up another object.
	 */
	public boolean canPickupAnotherObject(ViewEntity sameEntity) {
	    if (getBotConfig() == null) {
	        return true;
	    }
	    if (getBotConfig().getGripperHandicap()) {
	        return false;
	    }
	    int grippersTotal = getBotConfig().getGrippers();
	    int grippersInUse = sameEntity.getHolding().size();
	    if (sameEntity.getHoldingEpartner() != -1) {
	        grippersInUse++;
	    }
	    return grippersInUse < grippersTotal;
	}
	
	public boolean canPickupAnotherObject(BW4TClientGUI gui) {
	    return canPickupAnotherObject(gui.getController().getMapController().getTheBot());
	}
	
	public boolean isColorBlind() {
	    return getBotConfig() != null && getBotConfig().getColorBlindHandicap();
	}

    public BotConfig getBotConfig() {
        return botConfig;
    }

    public void setBotConfig(BotConfig botConfig) {
        this.botConfig = botConfig;
    }
    
    public boolean isGps() {
        return getEpartnerConfig() != null && getEpartnerConfig().isGps();
    }

    public EPartnerConfig getEpartnerConfig() {
        return epartnerConfig;
    }

    public void setEpartnerConfig(EPartnerConfig epartnerConfig) {
        this.epartnerConfig = epartnerConfig;
    }

}
