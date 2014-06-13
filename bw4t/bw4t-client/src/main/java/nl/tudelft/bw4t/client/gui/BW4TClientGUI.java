package nl.tudelft.bw4t.client.gui;

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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import nl.tudelft.bw4t.client.BW4TClientSettings;
import nl.tudelft.bw4t.client.agent.HumanAgent;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.listeners.ChatListMouseListener;
import nl.tudelft.bw4t.client.gui.listeners.EPartnerListMouseListener;
import nl.tudelft.bw4t.client.gui.listeners.TeamListMouseListener;
import nl.tudelft.bw4t.client.gui.menu.ActionPopUpMenu;
import nl.tudelft.bw4t.client.gui.menu.ComboAgentModel;
import nl.tudelft.bw4t.map.renderer.MapRenderSettings;
import nl.tudelft.bw4t.map.renderer.MapRenderer;
import nl.tudelft.bw4t.map.renderer.MapRendererInterface;

import org.apache.log4j.Logger;

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
public class BW4TClientGUI extends JFrame implements MapRendererInterface, ClientGUI {
    private static final long serialVersionUID = 2938950289045953493L;

    /**
     * The log4j Logger which displays logs on console
     */
    private static final Logger LOGGER = Logger.getLogger(BW4TClientGUI.class);

    private final BW4TClientGUI that = this;
    private ClientController controller;

    private JPanel mainPanel = new JPanel();
    private JPanel optionsMessagesPane = new JPanel();
    private JPanel botButtonPanel = new JPanel();
    private JPanel epartnerButtonPanel = new JPanel();
    private JPanel botPanel = new JPanel();
    private JPanel epartnerPanel = new JPanel();
    private JPanel botChatPanel = new JPanel();
    private JPanel epartnerChatPanel = new JPanel();
    
    private JLabel batteryLabel = new JLabel("Bot Battery: ");
    private JLabel botMessageLabel = new JLabel("Send message to: ");
    
    private JTextField botBatteryField = new JTextField();
    
    private JButton botMessageButton = new JButton("Choose message");
    private JButton epartnerMessageButton = new JButton("Choose message");
    
    private JTextArea botChatSession = new JTextArea(18, 1);
    private JTextArea epartnerChatSession = new JTextArea(8, 1);
    
    private JScrollPane botChatPane = new JScrollPane(getBotChatSession());
    private JScrollPane epartnerChatPane = new JScrollPane(getEpartnerChatSession());
    private JScrollPane mapRenderer;

    private JComboBox<ComboAgentModel> agentSelector;

    private JPopupMenu jPopupMenu;

    public JPopupMenu getjPopupMenu() {
        return jPopupMenu;
    }

    public JComboBox<ComboAgentModel> getAgentSelector() {
        return agentSelector;
    }

    private Point selectedLocation;
    
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
        controller = new ClientController(env, environment.getClient().getMap(), entityId);
        init();
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
        controller = new ClientController(env, environment.getClient().getMap(), entityId, humanAgent);
        init();
    }

    public BW4TClientGUI(ClientController cc) throws IOException {
        this.controller = cc;
        init();
    }

    /**
     * @param entityId
     *            the id of the entity that needs to be displayed
     * @param humanPlayer
     *            whether a human is supposed to control this panel
     * @throws IOException
     */
    private void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.error("Could not properly set the Native Look and Feel for the BW4T Client", e);
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
                } catch (Exception e1) {
                    LOGGER.error("Could not correctly kill the environment.", e1);
                }
            }
        });

        MapRenderer renderer = new MapRenderer(controller.getMapController());
        mapRenderer = new JScrollPane(renderer);
        
        createOverallFrame();

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
                    if(mouseOver && mwe.isControlDown()){
                        MapRenderSettings settings = that.getController().getMapController().getRenderSettings();
                        if(mwe.getUnitsToScroll() >= 0) {
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

            getBotChatSession().addMouseListener(new ChatListMouseListener(this));
        }

        pack();

        setVisible(true);
    }
    
    /**
     * Creates the overall frame.
     */
    public void createOverallFrame() {
        mainPanel.setLayout(new BorderLayout());
        
        createOptionsMessagesPane();
        
        mainPanel.add(mapRenderer, BorderLayout.CENTER);
        mainPanel.add(optionsMessagesPane, BorderLayout.EAST);
    }
    
    /**
     * Creates the right pane of the GUI, 
     * where the options and messages are shown of bots (and epartner).
     */
    public void createOptionsMessagesPane() {
        optionsMessagesPane.setLayout(new BorderLayout());
        
        createBotPane();
        createEpartnerPane();
        
        optionsMessagesPane.add(botPanel, BorderLayout.NORTH);
        optionsMessagesPane.add(epartnerPanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates the bot pane, where the bot options
     * and bot chat session is being displayed.
     */
    public void createBotPane() {
        botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.Y_AXIS));
        
        createBotOptionsBar();
        createBotChatSection();
        
        botPanel.add(botButtonPanel);
        botPanel.add(botChatPane);
    }
    
    /**
     * Creates the epartner pane, where the epartner options
     * and epartner chat session is being displayed.
     */
    public void createEpartnerPane() {
        epartnerPanel.setLayout(new BoxLayout(epartnerPanel, BoxLayout.Y_AXIS));
        
        createEpartnerOptionsBar();
        createEpartnerChatSection();
        
        epartnerPanel.add(epartnerButtonPanel);
        epartnerPanel.add(epartnerChatPane);
    }
    
    /**
     * Creates the button panel for the bot options.
     */
    public void createBotOptionsBar() {
        botButtonPanel.setLayout(new BoxLayout(botButtonPanel, BoxLayout.X_AXIS));
        botButtonPanel.setFocusable(false);
        botBatteryField.setEditable(false);
        
        agentSelector = new JComboBox<ComboAgentModel>(new ComboAgentModel(this));
        
        botButtonPanel.add(batteryLabel);
        botButtonPanel.add(botBatteryField);
        botButtonPanel.add(botMessageLabel);
        botButtonPanel.add(agentSelector);
        botButtonPanel.add(botMessageButton);
    
        botMessageButton.addMouseListener(new TeamListMouseListener(this));
    }
    
    /**
     * Creates the button panel for the epartner options.
     */
    public void createEpartnerOptionsBar() {
        epartnerButtonPanel.setLayout(new BoxLayout(epartnerButtonPanel, BoxLayout.X_AXIS));
        epartnerButtonPanel.setFocusable(false);

        epartnerButtonPanel.add(epartnerMessageButton);
        
        epartnerMessageButton.setEnabled(false);
        epartnerMessageButton.addMouseListener(new EPartnerListMouseListener(this));
    }
    
    /**
     * Creates the chat section of the bots.
     */
    public void createBotChatSection() {
        botChatPanel.setLayout(new BoxLayout(botChatPanel, BoxLayout.Y_AXIS));
        botChatPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        botChatPanel.setFocusable(false);

        getBotChatSession().setFocusable(false);
        
        botChatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        botChatPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        botChatPane.setEnabled(true);
        botChatPane.setFocusable(false);
        botChatPane.setColumnHeaderView(new JLabel("Bot Chat Session:"));
        
        botChatPanel.add(botChatPane);
    }
    
    /**
     * Creates the chat section of the epartners.
     */
    public void createEpartnerChatSection() {
        epartnerChatPanel.setLayout(new BoxLayout(epartnerChatPanel, BoxLayout.Y_AXIS));
        epartnerChatPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        epartnerChatPanel.setFocusable(false);
        
        getEpartnerChatSession().setFocusable(false);
        
        epartnerChatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        epartnerChatPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        epartnerChatPane.setEnabled(true);
        epartnerChatPane.setFocusable(false);
        epartnerChatPane.setColumnHeaderView(new JLabel("E-partner Chat Session:"));
        
        epartnerChatPanel.add(epartnerChatPane);
    }

    /**
     * Adds a player by adding a new button to the button panel, facilitating sending messages to this player
     * 
     * @param playerId
     *            , the Id of the player to be added
     */
  /*  public void addPlayer(String playerId) {
        if (!playerId.equals(controller.getMapController().getTheBot().getName())) {
            JButton button = new JButton(playerId);
            button.addMouseListener(new TeamListMouseListener(this));
            botButtonPanel.add(button);
        }
    }*/

    @Override
    public void dispose() {
        LOGGER.info("Stopped the BW4T Client Renderer.");
        BW4TClientSettings.setWindowParams(getX(), getY());
        super.dispose();
    }

    @Override
    public void update() {
        getBotChatSession().setText(join(getController().getBotChatHistory(), "\n"));
        getBotChatSession().setCaretPosition(getBotChatSession().getDocument().getLength());
        
        getEpartnerChatSession().setText(join(getController().getEpartnerChatHistory(), "\n"));
        getEpartnerChatSession().setCaretPosition(getEpartnerChatSession().getDocument().getLength());
    }

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

        getBotChatSession().append(sender + " : " + message + "\n");
        getBotChatSession().setCaretPosition(getBotChatSession().getDocument().getLength());
        
        getEpartnerChatSession().append(sender + " : " + message + "\n");
        getEpartnerChatSession().setCaretPosition(getEpartnerChatSession().getDocument().getLength());

        return null;
    }
    
    public JButton getBotMessageButton() {
        return botMessageButton;
    }
    
    public JButton getEpartnerMessageButton() {
        return epartnerMessageButton;
    }

    public JTextArea getBotChatSession() {
        return botChatSession;
    }
    
    public JTextArea getEpartnerChatSession() {
        return epartnerChatSession;
    }

    public void setBotChatSession(JTextArea chatSession) {
        this.botChatSession = chatSession;
    }
    
    public void setEpartnerChatSession(JTextArea chatSession) {
        this.epartnerChatSession = chatSession;
    }

    public Point getSelectedLocation() {
        return selectedLocation;
    }

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
