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

import nl.tudelft.bw4t.client.agent.HumanAgent;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.ClientGUI;
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
    private final List<String> botChatHistory = new LinkedList<>();
    private final List<String> epartnerChatHistory = new LinkedList<>();
    
    /** The chat history. */
    private final List<String> chatHistory = new LinkedList<>();

    /** The human agent. */
    private HumanAgent humanAgent;

    /** The to be performed action. */
    private List<Percept> toBePerformedAction = new LinkedList<>();
    
    /** The environment which should be read. */
    private RemoteEnvironment environment;

    /**
     * if set to true the update function of the gui will be called the next frame.
     */
    private boolean updateNextFrame = true;

    /**
     * Instantiates a new client controller.
     * 
     * @param env
     *            the environment
     * @param map
     *            the map
     * @param entityId
     *            the entity id
     */
    public ClientController(RemoteEnvironment env, NewMap map, String entityId) {
        environment = env;
        mapController = new ClientMapController(map, this);
        getMapController().getTheBot().setName(entityId);
        humanAgent = null;
    }

    /**
     * Instantiates a new client controller.
     * 
     * @param env
     *            the environment
     * @param map
     *            the map
     * @param entityId
     *            the entity id
     * @param humanAgent
     *            the human agent
     */
    public ClientController(RemoteEnvironment env, NewMap map, String entityId, HumanAgent humanAgent) {
        this(env, map, entityId);
        this.humanAgent = humanAgent;
    }

    public ClientMapController getMapController() {
        return mapController;
    }

    public Set<String> getOtherPlayers() {
        return otherPlayers;
    }

    public List<String> getBotChatHistory() {
        return botChatHistory;
    }
    
    public List<String> getEpartnerChatHistory() {
        return epartnerChatHistory;
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

        if (message.contains("I want to go")) {
            getEpartnerChatHistory().add(sender + ": " + message);
        } else {
            getBotChatHistory().add(sender + ": " + message);
        }
        
        updateNextFrame = true;
    }
    
    /**
     * the update request was finished.
     */
    void updatedNextFrame() {
        updateNextFrame = false;
    }

    /**
     * Update the renderer.
     * 
     * @param mri
     *            the map renderer interface
     */
    public void updateRenderer(MapRendererInterface mri) {
        if (updateNextFrame && mri instanceof ClientGUI) {
            ClientGUI gui = (ClientGUI) mri;
            gui.update();
        }
    }

}
