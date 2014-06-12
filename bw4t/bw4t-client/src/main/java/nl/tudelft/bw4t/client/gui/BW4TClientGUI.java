package nl.tudelft.bw4t.client.gui;

import eis.exceptions.ManagementException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.listeners.ChatListMouseListener;
import nl.tudelft.bw4t.client.gui.listeners.TeamListMouseListener;
import nl.tudelft.bw4t.client.gui.menu.ActionPopUpMenu;
import nl.tudelft.bw4t.client.gui.menu.ComboAgentModel;
import nl.tudelft.bw4t.map.renderer.MapRenderSettings;
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
public class BW4TClientGUI extends JFrame implements MapRendererInterface, ClientGUI {
    private static final long serialVersionUID = 2938950289045953493L;

    /** The log4j Logger which displays logs on console */
    private static final Logger LOGGER = Logger.getLogger(BW4TClientGUI.class);

    private final BW4TClientGUI that = this;
    private ClientController controller;

    private JPanel buttonPanel;
    private JTextArea chatSession = new JTextArea(8, 1);
    private JScrollPane chatPane;
    private JScrollPane mapRenderer;

    private JComboBox<ComboAgentModel> agentSelector;

    private JPopupMenu jPopupMenu;

    private Point selectedLocation;
    /** Most of the server interfacing goes through the std EIS Percepts. */
    public RemoteEnvironment environment;

    /**
     * @param env
     *            - The {@link RemoteEnvironment} that we are rendering.
     * @param entityId
     *            - The id of the entity that needs to be displayed.
     * @param goal
     *            - {@code true} if this GUI is for displaying a goal agent, otherwise {@code false}.
     * @param humanPlayer
     *            - {@code true} if the entity is human, otherwise {@code false}.
     * @throws IOException
     *             Thrown if map can't be loaded.
     */
    public BW4TClientGUI(RemoteEnvironment env, String entityId, boolean goal, boolean humanPlayer) throws IOException {
        environment = env;
        controller = new ClientController(env, environment.getClient().getMap(), entityId);
        init();
    }

    /**
     * @param env
     *            - The {@link RemoteEnvironment} that we are rendering.
     * @param entityId
     *            - The id of the entity that needs to be displayed.
     * @param humanAgent
     *            - Whether a human is supposed to control this panel.
     * @throws IOException
     *             Thrown if map can't be loaded.
     */
    public BW4TClientGUI(RemoteEnvironment env, String entityId, HumanAgent humanAgent) throws IOException {
        environment = env;
        controller = new ClientController(env, environment.getClient().getMap(), entityId, humanAgent);
        init();
    }

    /**
     * @param cc
     *            - The {@link ClientController} that we are rendering.
     * @throws IOException
     *             Thrown if map can't be loaded.
     */
    public BW4TClientGUI(ClientController cc) throws IOException {
        environment = cc.getEnvironment();
        this.controller = cc;
        init();
    }

    /**
     * Creates and initializes all neccessary elements of the GUI.
     */
    private void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e1) {
            LOGGER.error("Could not properly set the Native Look and Feel for the BW4T Client", e1);
        }
        LOGGER.debug("Attaching to ClientController");
        controller.getMapController().addRenderer(this);

        // Initialize variables
        String entityId = controller.getMapController().getTheBot().getName();
        LOGGER.debug("Initializing agent window for entity: " + entityId);

        // Initialize graphics

        setTitle("BW4T - " + entityId);
        setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
        setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LOGGER.info("Exit request received from the Window Manager to close Window of entity: "
                        + controller.getMapController().getTheBot().getName());
                controller.getMapController().setRunning(false);
                dispose();
                controller.getMapController().removeRenderer(that);
                try {
                    environment.kill();
                } catch (ManagementException e2) {
                    LOGGER.error("Could not correctly kill the environment.", e2);
                }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());

        buttonPanel = new JPanel();

        JLabel jLabelMessage = new JLabel("Send message to:");
        JButton jButton = new JButton("Choose Message");
        buttonPanel.add(jLabelMessage);
        agentSelector = new JComboBox<ComboAgentModel>(new ComboAgentModel(this));
        buttonPanel.add(agentSelector);
        buttonPanel.add(jButton);
        jButton.addMouseListener(new TeamListMouseListener(this));

        MapRenderer renderer = new MapRenderer(controller.getMapController());
        mapRenderer = new JScrollPane(renderer);

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
        if (controller.isHuman()) {
            this.jPopupMenu = new JPopupMenu();
            MouseAdapter ma = new MouseAdapter() {
                private boolean mouseOver = false;

                @Override
                public void mouseEntered(MouseEvent arg0) {
                    super.mouseEntered(arg0);
                    mouseOver = true;
                }

                @Override
                public void mouseExited(MouseEvent arg0) {
                    super.mouseExited(arg0);
                    mouseOver = false;
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // Get coordinates of mouse click
                    int mouseX = e.getX();

                    int mouseY = e.getY();

                    setSelectedLocation(mouseX, mouseY);

                    ActionPopUpMenu.buildPopUpMenu(that);
                }

                @Override
                public void mouseWheelMoved(MouseWheelEvent mwe) {
                    if (mouseOver && mwe.isControlDown()) {
                        MapRenderSettings settings = that.getController().getMapController().getRenderSettings();
                        if (mwe.getUnitsToScroll() >= 0) {
                            settings.setScale(settings.getScale() + 0.1);
                        } else {
                            settings.setScale(settings.getScale() - 0.1);
                        }
                    } else {
                        super.mouseWheelMoved(mwe);
                    }
                }
            };
            renderer.addMouseListener(ma);
            renderer.addMouseWheelListener(ma);

            getChatSession().addMouseListener(new ChatListMouseListener(this));
        }

        pack();

        setVisible(true);
    }

    /**
     * Adds a player by adding a new button to the button panel, facilitating sending messages to this player
     * 
     * @param playerId
     *            , the Id of the player to be added
     */
    public void addPlayer(String playerId) {
        if (!playerId.equals(controller.getMapController().getTheBot().getName())) {
            JButton button = new JButton(playerId);
            button.addMouseListener(new TeamListMouseListener(this));
            buttonPanel.add(button);
        }
    }

    @Override
    public void dispose() {
        LOGGER.info("Stopped the BW4T Client Renderer.");
        BW4TClientSettings.setWindowParams(getX(), getY());
        super.dispose();
    }

    @Override
    public void update() {
        getChatSession().setText(join(getController().getChatHistory(), "\n"));
        getChatSession().setCaretPosition(getChatSession().getDocument().getLength());
    }

    /**
     * Creates a string containing each element of chatHistory with the filler appended to them.
     * 
     * @param chatHistory A String {@link Iterator} to containing the elements to combine.
     * @param filler A filler String to append after each String.
     * @return The String elements with fillers.
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
     *            - The action parameters containing the message sender and the message itself.
     * @return a null percept as no real percept should be returned
     */
    public Percept sendToGUI(List<Parameter> parameters) {
        String sender = ((Identifier) parameters.get(0)).getValue();
        String message = ((Identifier) parameters.get(1)).getValue();

        getChatSession().append(sender + " : " + message + "\n");
        getChatSession().setCaretPosition(getChatSession().getDocument().getLength());

        return null;
    }

    public void setChatSession(JTextArea chatSession) {
        this.chatSession = chatSession;
    }

    public void setSelectedLocation(int x, int y) {
        this.selectedLocation = new Point(x, y);
    }

    public JTextArea getChatSession() {
        return chatSession;
    }

    public Point getSelectedLocation() {
        return selectedLocation;
    }

    public JPopupMenu getjPopupMenu() {
        return jPopupMenu;
    }

    public JComboBox<ComboAgentModel> getAgentSelector() {
        return agentSelector;
    }

    /**
     * @return the mapController
     */
    public ClientController getController() {
        return controller;
    }
}
