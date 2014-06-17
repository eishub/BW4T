package nl.tudelft.bw4t.client.controller;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.agent.HumanAgent;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.renderer.MapRendererInterface;

/**
 * The Class ClientController.
 */
public class ClientController {
    /**
     * The map controller used by this client controller.
     */
    private final ClientMapController mapController;

    /** The other players. */
    private final Set<String> otherPlayers = new HashSet<>();
    
    /** The chat history. */
    private final List<String> chatHistory = new LinkedList<>();

    /** The human agent. */
    private HumanAgent humanAgent;

    /** The to be performed action. */
    private List<Percept> toBePerformedAction = new LinkedList<>();
    
    /** The environment which should be read. */
    private RemoteEnvironment environment;
    
    /**
     * A reference to the client gui attached to this controller.
     */
    private BW4TClientGUI gui = null;

    /**
     * Instantiates a new client controller.
     * 
     * @param env
     *            the environment
     * @param entityId
     *            the entity id
     */
    public ClientController(RemoteEnvironment env, String entityId) {
        environment = env;
        mapController = new ClientMapController(environment.getClient().getMap(), this);
        getMapController().getTheBot().setName(entityId);
        humanAgent = null;
    }

    /**
     * Instantiates a new client controller.
     * 
     * @param env
     *            the environment
     * @param entityId
     *            the entity id
     * @param humanAgent
     *            the human agent
     */
    public ClientController(RemoteEnvironment env, String entityId, HumanAgent humanAgent) {
        this(env, entityId);
        this.humanAgent = humanAgent;
    }
    
    /**
     * Instantiate a default GUI.
     */
    public void startupGUI() {
        this.setGui(new BW4TClientGUI(this));
    }

    public ClientMapController getMapController() {
        return mapController;
    }

    public Set<String> getOtherPlayers() {
        return otherPlayers;
    }

    public List<String> getChatHistory() {
        return chatHistory;
    }

    public boolean isHuman() {
        return humanAgent != null;
    }

    public HumanAgent getHumanAgent() {
        return humanAgent;
    }

    public RemoteEnvironment getEnvironment() {
        return environment;
    }

    public void setToBePerformedAction(List<Percept> toBePerformedAction) {
        this.toBePerformedAction = toBePerformedAction;
    }

    /**
     * Method used for returning the next action that a human player wants the bot to perform. This is received by the
     * GOAL human bot, and then forwarded to the entity on the server side.
     * 
     * @return a percept containing the next action to be performed
     */
    public List<Percept> getToBePerformedAction() {
        List<Percept> toBePerformedActionClone = toBePerformedAction;
        setToBePerformedAction(new LinkedList<Percept>());
        return toBePerformedActionClone;
    }

    public BW4TClientGUI getGui() {
        return gui;
    }

    public void setGui(BW4TClientGUI gui) {
        this.gui = gui;
    }

    /**
     * Interprets the given list of {@link Percept}s and extracts the required information.
     * 
     * @param percepts
     *            the list of percepts
     */
    public void handlePercepts(List<Percept> percepts) {
        getMapController().getVisibleBlocks().clear();
        for (Percept percept : percepts) {
            String name = percept.getName();
            List<Parameter> parameters = percept.getParameters();
            mapController.handlePercept(name, parameters);
            if (name.equals("player")) {
                getOtherPlayers().add(((Identifier) parameters.get(0)).getValue());
            } else if (name.equals("message")) {
                handleMessagePercept(parameters);
            }
        }
    }

    /**
     * Handles the message percept parameters.
     * 
     * @param parameters
     *            the parameters given to the message percept
     */
    private void handleMessagePercept(List<Parameter> parameters) {
        ParameterList parameterList = ((ParameterList) parameters.get(0));
        Iterator<Parameter> iterator = parameterList.iterator();
        String sender = ((Identifier) iterator.next()).getValue();
        String message = ((Identifier) iterator.next()).getValue();
        getChatHistory().add(sender + ": " + message);
        updateGUI();
    }
    
    /**
     * the update the client GUI.
     */
    protected void updateGUI() {
        BW4TClientGUI gui = this.getGui();
        if(gui != null) {
            gui.update();
        }
    }
    
    /**
     * stop the controller and dispose the GUI.
     */
    public void stop() {
        mapController.setRunning(false);
        if(this.getGui() != null) {
            gui.dispose();
        }
        this.setGui(null);
    }

    public Percept sendToGUI(LinkedList<Parameter> parameters) {
        if(this.getGui() != null) {
            return this.getGui().sendToGUI(parameters);
        }
        return null;
    }

}
