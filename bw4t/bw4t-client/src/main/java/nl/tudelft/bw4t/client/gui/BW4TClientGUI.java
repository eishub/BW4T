package nl.tudelft.bw4t.client.gui;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.io.IOException;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;

import nl.tudelft.bw4t.client.BW4TClientSettings;
import nl.tudelft.bw4t.client.agent.HumanAgent;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.listeners.ChatListMouseListener;
import nl.tudelft.bw4t.client.gui.listeners.TeamListMouseListener;
import nl.tudelft.bw4t.client.gui.menu.ComboAgentModel;
import nl.tudelft.bw4t.map.renderer.MapRenderer;
import nl.tudelft.bw4t.map.renderer.MapRendererInterface;

import org.apache.log4j.Logger;

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
public class BW4TClientGUI extends JFrame implements MapRendererInterface {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2938950289045953493L;

    /** The log4j Logger which displays logs on console. */
    private static final Logger LOGGER = Logger.getLogger(BW4TClientGUI.class);
    
    /** The client controller. */
    private ClientController controller;

    /** The button panel. */
    private JPanel buttonPanel;
    
    /** The chat session. */
    private JTextArea chatSession = new JTextArea(8, 1);
    
    /** The chat pane. */
    private JScrollPane chatPane;
    
    /** The map renderer. */
    private JScrollPane mapRenderer;
    
    /** The agent selector. */
    private JComboBox<ComboAgentModel> agentSelector;
    
    /** The jpopup menu. */
    private JPopupMenu jPopupMenu;

    /**
     * Gets the jpopup menu.
     * 
     * @return the JPopup menu
     */
    public JPopupMenu getjPopupMenu() {
        return jPopupMenu;
    }

    public JComboBox<ComboAgentModel> getAgentSelector() {
        return agentSelector;
    }

    /** The selected location. */
    private Point selectedLocation;
    
    /** Most of the server interfacing goes through the std eis percepts. */
    public RemoteEnvironment environment;

    /**
     * Instantiates a new bw4t client gui.
     * 
     * @param cc
     *            the client controller
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public BW4TClientGUI(ClientController cc) throws IOException {
        this.controller = cc;
        init();
    }

    /**
     * Instantiates a new bw4t client gui.
     * 
     * @param env
     *            the env
     * @param entityId
     *            , the id of the entity that needs to be displayed
     * @param humanAgent
     *            , whether a human is supposed to control this panel
     * @throws IOException
     *             if map can't be loaded.
     */
    public BW4TClientGUI(RemoteEnvironment env, String entityId, HumanAgent humanAgent) throws IOException {
        environment = env;
        controller = new ClientController(env, environment.getClient().getMap(), entityId, humanAgent);
        init();
    }

    /**
     * Instantiates a new bw4t client gui.
     * 
     * @param env
     *            the BW4TRemoteEnvironment that we are rendering
     * @param entityId
     *            , the id of the entity that needs to be displayed
     * @param goal
     *            , if this gui is for displaying a goal agent
     * @param humanPlayer
     *            the human player
     * @throws IOException
     *             if map can't be loaded.
     */
    public BW4TClientGUI(RemoteEnvironment env, String entityId, boolean goal, boolean humanPlayer) throws IOException {
        environment = env;
        controller = new ClientController(env, environment.getClient().getMap(), entityId);
        init();
    }

    /**
     * Initializes the GUI.
     */
    private void init() {
        initializeEmptyWindow();
        addWindowListener(new ClientWindowAdapter(this));

        JPanel mainPanel = new JPanel(new BorderLayout());
        initializeMouseListeners();
        initializeMainPanel(mainPanel);
        add(mainPanel);
        
        pack();
        setVisible(true);
    }

    /**
     * Initialize main panel.
     * 
     * @param mainPanel
     *            the main panel
     */
    private void initializeMainPanel(JPanel mainPanel) {
        buttonPanel = new JPanel();

        JLabel jLabelMessage = new JLabel("Send message to:");
        JButton jButton = new JButton("Choose Message");
        buttonPanel.add(jLabelMessage);
        agentSelector = new JComboBox<ComboAgentModel>(new ComboAgentModel(this));
        buttonPanel.add(agentSelector);
        buttonPanel.add(jButton);
        jButton.addMouseListener(new TeamListMouseListener(this));

        buildChatPanel();

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(mapRenderer, BorderLayout.CENTER);
        mainPanel.add(chatPane, BorderLayout.SOUTH);
    }

    /**
     * Initialize mouse listeners.
     */
    private void initializeMouseListeners() {
        MapRenderer renderer = new MapRenderer(controller.getMapController());
        mapRenderer = new JScrollPane(renderer);
        if (controller.isHuman()) {
            this.jPopupMenu = new JPopupMenu();
            MouseAdapter mouseAdapter = new ClientMouseAdapter(this);
            renderer.addMouseListener(mouseAdapter);
            renderer.addMouseWheelListener(mouseAdapter);
            chatSession.addMouseListener(new ChatListMouseListener(this));
        }
    }

    /**
     * Builds the chat panel.
     */
    private void buildChatPanel() {
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        chatPanel.setFocusable(false);

        chatSession.setFocusable(false);
        chatPane = new JScrollPane(chatSession);
        chatPanel.add(chatPane);
        chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatPane.setEnabled(true);
        chatPane.setFocusable(false);
        chatPane.setColumnHeaderView(new JLabel("Chat Session:"));
    }

    /**
     * Initialize an empty window.
     */
    private void initializeEmptyWindow() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            LOGGER.error("Could not properly set the Native Look and Feel for the BW4T Client", e);
        }
        LOGGER.debug("Attaching to ClientController");
        final ClientMapController mapController = controller.getMapController();
        String entityId = mapController.getTheBot().getName();
        
        mapController.addRenderer(this);
        
        LOGGER.debug("Initializing agent window for entity: " + entityId);
        setTitle("BW4T - " + entityId);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
        setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
    }

    /**
     * Adds a player by adding a new button to the button panel, facilitating sending messages to this player.
     * 
     * @param playerId
     *            , the Id of the player to be added
     */
    public void addPlayer(String playerId) {
        final ClientMapController mapController = controller.getMapController();
        if (!playerId.equals(mapController.getTheBot().getName())) {
            JButton button = new JButton(playerId);
            button.addMouseListener(new TeamListMouseListener(this));
            buttonPanel.add(button);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.Window#dispose()
     */
    @Override
    public void dispose() {
        LOGGER.info("Stopped the BW4T Client Renderer.");
        BW4TClientSettings.setWindowParams(getX(), getY());
        super.dispose();
    }

    /**
     * Update the chat session.
     */
    public void update() {
        chatSession.setText(join(controller.getChatHistory(), "\n"));
        chatSession.setCaretPosition(chatSession.getDocument().getLength());
    }

    /**
     * Join.
     * 
     * @param chatHistory
     *            the chat history
     * @param filler
     *            the filler
     * @return the string
     */
    private String join(Iterable<String> chatHistory, String filler) {
        StringBuilder sb = new StringBuilder();
        for (String string : chatHistory) {
            sb.append(string).append(filler);
        }
        return sb.toString();
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
        chatSession.append(sender + " : " + message + "\n");
        chatSession.setCaretPosition(chatSession.getDocument().getLength());
        return null;
    }

    public JTextArea getChatSession() {
        return chatSession;
    }

    public void setChatSession(JTextArea chatSession) {
        this.chatSession = chatSession;
    }

    public Point getSelectedLocation() {
        return selectedLocation;
    }

    /**
     * Sets the selected location.
     * 
     * @param x
     *            the x
     * @param y
     *            the y
     */
    public void setSelectedLocation(int x, int y) {
        this.selectedLocation = new Point(x, y);
    }

    /**
     * @return the mapController
     */
    public ClientController getController() {
        return controller;
    }
}
