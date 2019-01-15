package nl.tudelft.bw4t.client.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import eis.exceptions.EntityException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;
import nl.tudelft.bw4t.client.agent.HumanAgent;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.menu.EntityComboModelProvider;
import nl.tudelft.bw4t.map.view.ViewEPartner;

/**
 * The Class ClientController. DOC What is this?
 */
public class ClientController implements EntityComboModelProvider {
	private static final Logger LOGGER = Logger.getLogger(ClientController.class);
	/**
	 * The map controller used by this client controller.
	 */
	private final ClientMapController mapController;

	/** The other players. */
	private final Set<String> otherPlayers = new HashSet<>();

	/** The bot chat history. */
	private final List<String> botChatHistory = new LinkedList<>();
	/** The epartner chat history. */
	private final List<String> epartnerChatHistory = new LinkedList<>();

	/** The human agent. */
	private HumanAgent humanAgent;

	/** The to be performed action. */
	private List<Percept> toBePerformedAction = new LinkedList<>();

	/** The environment which should be read. */
	private final RemoteEnvironment environment;

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
		this.environment = env;
		this.mapController = new ClientMapController(this.environment.getMap(), this);
		getMapController().getTheBot().setName(entityId);
		this.humanAgent = null;
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
		setGui(new BW4TClientGUI(this));
	}

	public ClientMapController getMapController() {
		return this.mapController;
	}

	public Set<String> getOtherPlayers() {
		return this.otherPlayers;
	}

	public List<String> getBotChatHistory() {
		return this.botChatHistory;
	}

	public List<String> getEpartnerChatHistory() {
		return this.epartnerChatHistory;
	}

	public boolean isHuman() {
		return this.humanAgent != null;
	}

	public HumanAgent getHumanAgent() {
		return this.humanAgent;
	}

	public RemoteEnvironment getEnvironment() {
		return this.environment;
	}

	/**
	 * Add a new percept (containing an user action) to the list of percepts to
	 * be returned in the next getPercept cycle
	 *
	 * @param toBePerformedAction
	 */
	public void addToBePerformedAction(Percept toBePerformedAction) {
		this.toBePerformedAction.add(toBePerformedAction);
	}

	protected void setToBePerformedAction(List<Percept> toBePerformedAction) {
		this.toBePerformedAction = toBePerformedAction;
	}

	/**
	 * Method used for returning the next action that a human player wants the
	 * bot to perform. This is received by the GOAL human bot, and then
	 * forwarded to the entity on the server side.
	 *
	 * @return a percept containing the next action to be performed
	 */
	public List<Percept> getToBePerformedAction() {
		List<Percept> toBePerformedActionClone = this.toBePerformedAction;
		setToBePerformedAction(new LinkedList<Percept>());
		return toBePerformedActionClone;
	}

	public BW4TClientGUI getGui() {
		return this.gui;
	}

	public void setGui(BW4TClientGUI gui) {
		this.gui = gui;
	}

	/**
	 * Interprets the given list of {@link Percept}s and extracts the required
	 * information.
	 *
	 * @param percepts
	 *            the list of percepts
	 */
	public void handlePercepts(Collection<Percept> percepts) {
		for (ViewEPartner ep : getMapController().getEPartners()) {
			ep.setVisible(false);
		}
		ClientMapController controller = getMapController();
		controller.clearVisible();
		controller.clearOccupiedRooms();
		controller.clearBumped();

		boolean clearedPositions = false;
		for (Percept percept : percepts) {
			String name = percept.getName();
			List<Parameter> parameters = percept.getParameters();

			if (name.equals("position") && !clearedPositions) {
				getMapController().clearVisiblePositions();
				clearedPositions = true;
			}

			this.mapController.handlePercept(name, parameters);
			if ("player".equals(name)) {
				getOtherPlayers().add(((Identifier) parameters.get(0)).getValue());
			} else if ("message".equals(name)) {
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
		ParameterList parameterList = (ParameterList) parameters.get(0);
		Iterator<Parameter> iterator = parameterList.iterator();
		String sender = ((Identifier) iterator.next()).getValue();
		String message = ((Identifier) iterator.next()).getValue();

		if (message.contains("I want to go") || message.contains("You forgot me")) {
			getEpartnerChatHistory().add(sender + ": " + message);
		} else if (!message.equalsIgnoreCase("ok")) { // ignore stub messages
			getBotChatHistory().add(sender + ": " + message);
		}

		updateGUI();
	}

	/**
	 * the update the client GUI.
	 */
	protected void updateGUI() {
		BW4TClientGUI currentGui = getGui();
		if (currentGui != null) {
			currentGui.update();
		}
	}

	/**
	 * stop the controller and dispose the GUI.
	 */
	public void stop() {
		this.mapController.setRunning(false);
		if (getGui() != null) {
			final BW4TClientGUI theGUI = this.gui; // copy for in Runnable
			setGui(null);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					theGUI.dispose();
				}
			});
		}
	}

	/**
	 * Send paramenters to the GUI to be displayed in the chat window
	 *
	 * @param parameters
	 * @return a null percept as no real percept should be returned
	 */
	public Percept sendToGUI(List<Parameter> parameters) {
		if (getGui() != null) {
			return getGui().sendToGUI(parameters);
		}
		return null;
	}

	@Override
	public Collection<String> getEntities() {
		Collection<String> ents = getEnvironment().getEntities();
		Collection<String> ret = new ArrayList<>(ents.size());
		String me = getMapController().getTheBot().getName();
		for (String entity : ents) {
			try {
				if (!me.equals(entity) && !"epartner".equals(getEnvironment().getType(entity))) {
					ret.add(entity);
				}
			} catch (EntityException e) {
				LOGGER.warn("Unable to get the type of entity: " + entity, e);
			}
		}

		return ret;
	}
}
