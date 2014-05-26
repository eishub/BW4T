package nl.tudelft.bw4t.client.gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import nl.tudelft.bw4t.agent.HumanAgent;
import nl.tudelft.bw4t.client.BW4TClientSettings;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.environment.handlers.PerceptsHandler;
import nl.tudelft.bw4t.client.gui.data.EnvironmentDatabase;
import nl.tudelft.bw4t.client.gui.listeners.ChatListMouseListener;
import nl.tudelft.bw4t.client.gui.listeners.TeamListMouseListener;
import nl.tudelft.bw4t.client.gui.menu.ActionPopUpMenu;
import nl.tudelft.bw4t.client.gui.menu.ComboAgentModel;
import nl.tudelft.bw4t.client.gui.operations.ProcessingOperations;
import nl.tudelft.bw4t.view.MapRenderer;

import org.apache.log4j.Logger;

import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * Render the current state of the world at a fixed rate (10 times per second, see run()) for a client. It connects to
 * the given {@link RemoteEnvironment} on behalf of a given eis entityId. This allows fetching the latest percepts and
 * uses these percepts to track the world state.
 * <p>
 * It is possible to set up this renderer as a human GUI as well. In that case, a human can click with the mouse in the
 * GUI. His actions create GOAL percepts:
 * <ul>
 * <li>sendMessage("all",Message). User asked to send given Message.
 * <li>goToBlock(Id). User asked to go to given block id (Id is a numeral).
 * <li>goTo(Id). User asked to go to given block Id (Id is an identifier)
 * <li>goTo(X,Y). user asked to go to given X,Y position (X,Y both numeral)
 * <li>pickUp(). User asked to do the pick up action
 * <li>putDown(). User asked to do the put down action.
 * </ul>
 * <p>
 * {@link RemoteEnvironment#getAllPerceptsFromEntity(String)} is called by the {@link #run()} repaint scheduler only if
 * we are representing a HumanPlayer. Otherwise the getAllPercepts is done by the agent and we assume processPercepts is
 * called by the {@link RemoteEnvironment} when the agent asked for getAllPercepts.
 * <p>
 * The BW4TRenderer has a list {@link #toBePerformedAction} which is polled by
 * {@link RemoteEnvironment#getAllPerceptsFromEntity(String)} at every call, and merged into the regular percepts. So
 * user mouse clicks are stored there until it's time for perceiving.
 */
public class BW4TClientGUI extends JFrame implements Runnable {
	private static final long serialVersionUID = 2938950289045953493L;

	/**
	 * The log4j Logger which displays logs on console
	 */
	private static final Logger LOGGER = Logger.getLogger(BW4TClientGUI.class);
	private final BW4TClientGUI that = this;
	/**
	 * Data needed for updating the graphical representation of the world
	 */
	private EnvironmentDatabase environmentDatabase;
	public boolean stop;
	private JPanel buttonPanel;
	private JTextArea chatSession = new JTextArea(8, 1);
	private JScrollPane chatPane;
	private JScrollPane mapRenderer;
	private JComboBox<ComboAgentModel> agentSelector;

	private final ClientMapController mapController;

	/**
	 * Private variables only used for human player
	 */
	private HumanAgent humanAgent;
	private JPopupMenu jPopupMenu;

	public JPopupMenu getjPopupMenu() {
		return jPopupMenu;
	}

	public JComboBox<ComboAgentModel> getAgentSelector() {
		return agentSelector;
	}

	private Point selectedLocation;
	private boolean goal = false;
	private final boolean humanPlayer;
	/**
	 * Most of the server interfacing goes through the std eis percepts
	 */
	public RemoteEnvironment environment;

	/**
	 * @param env
	 *            the BW4TRemoteEnvironment that we are rendering
	 * @param entityId
	 *            , the id of the entity that needs to be displayed
	 * @param goal
	 *            , if this gui is for displaying a goal agent
	 * @throws IOException
	 *             if map can't be loaded.
	 */
	public BW4TClientGUI(RemoteEnvironment env, String entityId, boolean goal, boolean humanPlayer) throws IOException {
		environment = env;
		mapController = new ClientMapController(environment.getClient().getMap());
		init(entityId, humanPlayer);
		this.setGoal(goal);
		this.humanPlayer = humanPlayer;
	}

	/**
	 * @param entityId
	 *            , the id of the entity that needs to be displayed
	 * @param humanAgent
	 *            , whether a human is supposed to control this panel
	 * @throws IOException
	 *             if map can't be loaded.
	 */
	public BW4TClientGUI(RemoteEnvironment env, String entityId, HumanAgent humanAgent) throws IOException {
		environment = env;
		this.humanAgent = humanAgent;
		this.humanPlayer = true;
		mapController = new ClientMapController(environment.getClient().getMap());
		init(entityId, true);
	}

	/**
	 * @param entityId
	 *            , the id of the entity that needs to be displayed
	 * @param humanPlayer
	 *            , whether a human is supposed to control this panel
	 * @throws IOException
	 */
	private void init(final String entityId, boolean humanPlayer) throws IOException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			LOGGER.error("Could not properly set the Native Look and Feel for the BW4T Client", e);
		}
		// Initialize variables
		LOGGER.debug("Initializing agent window for entity: " + entityId);
		setEnvironmentDatabase(new EnvironmentDatabase());
		getEnvironmentDatabase().setEntityId(entityId);
		buttonPanel = new JPanel();

		JLabel jLabelMessage = new JLabel("Send message to:");
		JButton jButton = new JButton("Choose Message");
		buttonPanel.add(jLabelMessage);
		agentSelector = new JComboBox<ComboAgentModel>(new ComboAgentModel(this));
		buttonPanel.add(agentSelector);
		buttonPanel.add(jButton);
		jButton.addMouseListener(new TeamListMouseListener(this));

		MapRenderer renderer = new MapRenderer(mapController);
		mapRenderer = new JScrollPane(renderer);

		// Initialize graphics

		setTitle("BW4T - " + entityId);
		setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
		setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				LOGGER.info("Exit request received from the Window Manager to close Window of entity: " + entityId);
				setStop(true);
				try {
					environment.kill();
				} catch (Exception e1) {
					LOGGER.error("Could not correctly kill the environment.", e1);
				}
			}
		});

		JPanel mainPanel = new JPanel(new BorderLayout());

		// create short chat history window
		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		chatPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		chatPanel.setFocusable(false);

		getChatSession().setFocusable(false);
		chatPane = new JScrollPane(getChatSession());
		chatPanel.add(chatPane);
		chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chatPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatPane.setEnabled(true);
		chatPane.setFocusable(false);
		chatPane.setColumnHeaderView(new JLabel("Chat Session:"));

		mainPanel.add(buttonPanel, BorderLayout.NORTH);
		mainPanel.add(mapRenderer, BorderLayout.CENTER);
		mainPanel.add(chatPane, BorderLayout.SOUTH);

		add(mainPanel);

		// Initialize mouse listeners for human controller
		if (humanPlayer) {
			this.jPopupMenu = new JPopupMenu();
			renderer.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					// Get coordinates of mouse click
					int mouseX = e.getX();

					int mouseY = e.getY();

					setSelectedLocation(mouseX, mouseY);

					ActionPopUpMenu.buildPopUpMenu(that);
				}
			});

			getChatSession().addMouseListener(new ChatListMouseListener(this));
		}

		pack();

		setVisible(true);
		// Start repainting graphics
		Thread paintThread = new Thread(this);
		paintThread.start();
	}

	/**
	 * Adds a player by adding a new button to the button panel, facilitating sending messages to this player
	 * 
	 * @param playerId
	 *            , the Id of the player to be added
	 */
	public void addPlayer(String playerId) {
		if (!playerId.equals(getEnvironmentDatabase().getEntityId())) {
			JButton button = new JButton(playerId);
			button.addMouseListener(new TeamListMouseListener(this));
			buttonPanel.add(button);
		}
	}

	/**
	 * Poll percepts every tick, process them and repaint this panel.
	 */
	@Override
	public void run() {
		while (!isStop()) {
			if (humanPlayer) {
				List<Percept> percepts;
				try {
					percepts = PerceptsHandler.getAllPerceptsFromEntity(getEnvironmentDatabase().getEntityId() + "gui",
							environment);
					if (percepts != null) {
						ProcessingOperations.processPercepts(percepts, this);
						handlePercepts(percepts);
					}
				} catch (PerceiveException e) {
					LOGGER.error("Could not correctly poll the percepts from the environment.", e);
				} catch (NoEnvironmentException e) {
					LOGGER.error(
							"Could not correctly poll the percepts from the environment. No connection could be made to the environment",
							e);
					setStop(true);
				}
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					validate();
					repaint();
				}
			});
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				LOGGER.error("The system ignored the interrupted rendering delay.", e);
			}
		}

		LOGGER.info("Stopped the BW4T Client Renderer.");
		BW4TClientSettings.setWindowParams(getX(), getY());
		dispose();
	}

	/**
	 * Method used for returning the next action that a human player wants the bot to perform. This is received by the
	 * GOAL human bot, and then forwarded to the entity on the server side.
	 * 
	 * @return a percept containing the next action to be performed
	 */
	public List<Percept> getToBePerformedAction() {
		List<Percept> toBePerformedActionClone = (LinkedList<Percept>) getEnvironmentDatabase()
				.getToBePerformedAction().clone();
		getEnvironmentDatabase().setToBePerformedAction(new LinkedList<Percept>());
		return toBePerformedActionClone;
	}

	/**
	 * When a GOAL agent performs the sendToGUI action it is forwarded to this method, the message is then posted on the
	 * chat window contained in the GUI.
	 * 
	 * @param parameters
	 *            , the action parameters containing the message sender and the message itself.
	 * @return a null percept as no real percept should be returned
	 */
	public Percept sendToGUI(List<Parameter> parameters) {
		String sender = ((Identifier) parameters.get(0)).getValue();
		String message = ((Identifier) parameters.get(1)).getValue();

		getChatSession().append(sender + " : " + message + "\n");
		getChatSession().setCaretPosition(getChatSession().getDocument().getLength());

		return null;
	}

	/**
	 * Adds a label with a corresponding point near which it should be drawn to the label list.
	 * 
	 * @param label
	 *            , the label
	 * @param point
	 *            , the point
	 */
	public void addLabel(String label, Point point) {
		getEnvironmentDatabase().getRoomLabels().put(label, point);
	}

	public JTextArea getChatSession() {
		return chatSession;
	}

	public void setChatSession(JTextArea chatSession) {
		this.chatSession = chatSession;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public EnvironmentDatabase getEnvironmentDatabase() {
		return environmentDatabase;
	}

	public void setEnvironmentDatabase(EnvironmentDatabase environmentDatabase) {
		this.environmentDatabase = environmentDatabase;
	}

	public boolean isGoal() {
		return goal;
	}

	public void setGoal(boolean goal) {
		this.goal = goal;
	}

	public HumanAgent getHumanAgent() {
		return humanAgent;
	}

	public void setHumanAgent(HumanAgent humanAgent) {
		this.humanAgent = humanAgent;
	}

	public Point getSelectedLocation() {
		return selectedLocation;
	}

	public void setSelectedLocation(int x, int y) {
		this.selectedLocation = new Point(x, y);
	}

	public void handlePercepts(List<Percept> percepts) {
		mapController.handlePercepts(percepts);
	}

	/**
	 * @return the mapController
	 */
	public ClientMapController getMapController() {
		return mapController;
	}
}
