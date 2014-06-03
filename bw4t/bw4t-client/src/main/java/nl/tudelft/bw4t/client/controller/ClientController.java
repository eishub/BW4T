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

import nl.tudelft.bw4t.agent.HumanAgent;
import nl.tudelft.bw4t.client.gui.ClientGUI;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.view.MapRendererInterface;

/**
 * The client controller of the MVC pattern used to create the BW4T Client.
 */
public class ClientController {
    /**
     * The map controller used by this Client Controller.
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

    /**
     * If set to true, the update function of the GUI will be called the next frame.
     */
    private boolean updateNextFrame = true;

    /**
     * Instantiates a new client controller.
     *
     * @param map the map
     * @param entityId the entity id
     */
    public ClientController(NewMap map, String entityId) {
        mapController = new ClientMapController(map, this);
        getMapController().getTheBot().setName(entityId);
        humanAgent = null;
    }

    /**
     * Instantiates a new client controller.
     *
     * @param map the map
     * @param entityId the entity id
     * @param humanAgent2 the human agent2
     */
    public ClientController(NewMap map, String entityId, HumanAgent humanAgent2) {
        this(map, entityId);
        humanAgent = humanAgent2;
    }

    /**
     * Gets the map controller.
     *
     * @return the map controller
     */
    public ClientMapController getMapController() {
        return mapController;
    }

    /**
     * Gets the other players.
     *
     * @return the other players
     */
    public Set<String> getOtherPlayers() {
        return otherPlayers;
    }

    /**
     * Gets the chat history.
     *
     * @return the chat history
     */
    public List<String> getChatHistory() {
        return chatHistory;
    }

    /**
     * Checks if is human.
     *
     * @return true, if is human
     */
    public boolean isHuman() {
        return humanAgent != null;
    }

    /**
     * Gets the human agent.
     *
     * @return the human agent
     */
    public HumanAgent getHumanAgent() {
        return humanAgent;
    }

    /**
     * Sets the to be performed action.
     *
     * @param toBePerformedAction the new to be performed action
     */
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

            // Initialize room ids in all rooms gotten from the map loader
            // Should only be done one time
            if (name.equals("player")) {
                getOtherPlayers().add(((Identifier) parameters.get(0)).getValue());
            
            // Update chat history
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

        updateNextFrame = true;
    }
    
    /**
     * The update request was finished.
     */
    void updatedNextFrame() {
        updateNextFrame = false;
    }

    /**
     * Update renderer.
     *
     * @param mri the Map Renderer, used to render the client GUI.
     */
    public void updateRenderer(MapRendererInterface mri) {
        if (updateNextFrame && mri instanceof ClientGUI) {
            ClientGUI gui = (ClientGUI) mri;
            gui.update();
        }
    }

}
