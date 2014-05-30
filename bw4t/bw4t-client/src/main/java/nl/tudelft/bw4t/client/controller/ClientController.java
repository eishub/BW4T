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

public class ClientController {
    /**
     * The map controller used by this clientcontroller.
     */
    private final ClientMapController mapController;

    private final Set<String> otherPlayers = new HashSet<>();
    private final List<String> chatHistory = new LinkedList<>();

    private HumanAgent humanAgent;

    private List<Percept> toBePerformedAction = new LinkedList<>();

    /**
     * if set to true the update function of the gui will be called the next frame.
     */
    private boolean updateNextFrame = true;

    public ClientController(NewMap map, String entityId) {
        mapController = new ClientMapController(map, this);
        getMapController().getTheBot().setName(entityId);
        humanAgent = null;
    }

    public ClientController(NewMap map, String entityId, HumanAgent humanAgent2) {
        this(map, entityId);
        humanAgent = humanAgent2;
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
            }
            // Update chat history
            else if (name.equals("message")) {
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
     * the update request was finished.
     */
    void updatedNextFrame() {
        updateNextFrame = false;
    }

    public void updateRenderer(MapRendererInterface mri) {
        if (updateNextFrame && mri instanceof ClientGUI) {
            ClientGUI gui = (ClientGUI) mri;
            if (gui != null) {
                gui.update();
            }
        }
    }

}
