package nl.tudelft.bw4t.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import nl.tudelft.bw4t.client.BW4TClientSettings;
import nl.tudelft.bw4t.client.agent.HumanAgent;
import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.listeners.BatteryProgressBarListener;
import nl.tudelft.bw4t.client.gui.listeners.ChatListMouseListener;
import nl.tudelft.bw4t.client.gui.listeners.EPartnerListMouseListener;
import nl.tudelft.bw4t.client.gui.listeners.TeamListMouseListener;
import nl.tudelft.bw4t.client.gui.menu.ActionPopUpMenu;
import nl.tudelft.bw4t.client.gui.menu.ComboEntityModel;
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
 * It is possible to set up this renderer as a human GUI as well. In that case, a human can click with the mouse in the
 * GUI. His actions create GOAL percepts:
 *
 * <ul>
 * <li>sendMessage("all",Message). User asked to send given Message.
 * <li>goToBlock(Id). User asked to go to given block id (Id is a numeral).
 * <li>goTo(Id). User asked to go to given block Id (Id is an identifier)
 * <li>goTo(X,Y). user asked to go to given X,Y position (X,Y both numeral)
 * <li>pickUp(). User asked to do the pick up action
 * <li>putDown(). User asked to do the put down action.
 * </ul>
 * 
 * {@link RemoteEnvironment#getAllPerceptsFromEntity(String)} is called by the {@link #run()} repaint scheduler only if
 * we are representing a HumanPlayer. Otherwise the getAllPercepts is done by the agent and we assume processPercepts is
 * called by the {@link RemoteEnvironment} when the agent asked for getAllPercepts.
 * 
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

    private final JPanel optionsMessagesPane = new JPanel();
    private final JPanel botButtonPanel = new JPanel();
    private final JPanel epartnerButtonPanel = new JPanel();
    private final JPanel botPanel = new JPanel();
    private final JPanel epartnerPanel = new JPanel();
    private final JPanel botChatPanel = new JPanel();
    private final JPanel epartnerChatPanel = new JPanel();
    
    /**
     * A reference to the current object for the internal classes to use.
     */
    private final BW4TClientGUI that = this;
    
    private final JLabel batteryLabel = new JLabel("Bot Battery: ");
    private final JLabel botMessageLabel = new JLabel("Send message to: ");
    
    private JProgressBar batteryProgressBar = new JProgressBar(0, 1); 
    
    private final JButton botMessageButton = new JButton("Choose message");
    private final JButton epartnerMessageButton = new JButton("Choose message");
    
    private JTextArea botChatSession = new JTextArea(14, 1);
    private JTextArea epartnerChatSession = new JTextArea(8, 1);
    
    private final JScrollPane botChatPane = new JScrollPane(getBotChatSession());
    private final JScrollPane epartnerChatPane = new JScrollPane(getEpartnerChatSession());

    private JScrollPane mapRenderer;
    
    /** The agent selector. */
    private JComboBox<String> agentSelector;
    
    /** The jpopup menu. */
    private JPopupMenu jPopupMenu;
    
    /** The selected location. */
    private Point selectedLocation;

    /**
     * Instantiates a new bw4t client gui.
     * 
     * @param cc
     *            the client controller
     */
    public BW4TClientGUI(ClientController cc) {
        setController(cc);
        init();
    }

    /**
     * @param env
     *            - The {@link RemoteEnvironment} that we are rendering.
     * @param entityId
     *            - The id of the entity that needs to be displayed.
     */
    public BW4TClientGUI(RemoteEnvironment env, String entityId) {
        this(new ClientController(env, entityId));
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
        this(new ClientController(env, entityId, humanAgent));
    }

    /**
     * Initializes the GUI.
     */
    private void init() {
        addWindowListener(new ClientWindowAdapter(this));
        MapRenderer renderer = new MapRenderer(controller.getMapController());
        mapRenderer = new JScrollPane(renderer);
        
        createOverallFrame();
//        setMinimumSize(new Dimension(700, 600));

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
        LOGGER.debug("Attaching to ClientController");
        final ClientMapController mapController = controller.getMapController();
        String entityId = mapController.getTheBot().getName();
        
        mapController.addRenderer(this);
        
        LOGGER.debug("Initializing agent window for entity: " + entityId);
        setTitle("BW4T - " + entityId);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
        setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
        
        pack();
        setVisible(true);
    }
    
    /**
     * Gets the jpopup menu.
     * 
     * @return the JPopup menu
     */
    public JPopupMenu getjPopupMenu() {
        return jPopupMenu;
    }

    public JComboBox<String> getAgentSelector() {
        return agentSelector;
    }
    
    private void createOverallFrame() {
        setLayout(new BorderLayout());
        
        if(getController().isHuman()) {
            createOptionsMessagesPane();
            add(optionsMessagesPane, BorderLayout.EAST);
        }
        
        add(mapRenderer, BorderLayout.CENTER);
    }
    
    private void createOptionsMessagesPane() {
        optionsMessagesPane.setLayout(new BorderLayout());
        
        createBotPane();
        createEpartnerPane();
        
        optionsMessagesPane.add(botPanel, BorderLayout.NORTH);
        optionsMessagesPane.add(epartnerPanel, BorderLayout.CENTER);
    }
    
    private void createBotPane() {
        botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.Y_AXIS));
        
        createBotOptionsBar();
        createBotChatSection();
        
        botPanel.add(botButtonPanel);
        botPanel.add(botChatPane);
    }
    
    private void createEpartnerPane() {
        epartnerPanel.setLayout(new BoxLayout(epartnerPanel, BoxLayout.Y_AXIS));
        
        createEpartnerOptionsBar();
        createEpartnerChatSection();
        
        epartnerPanel.add(epartnerButtonPanel);
        epartnerPanel.add(epartnerChatPane);
    }
    
    private void createBotOptionsBar() {
        botButtonPanel.setLayout(new BoxLayout(botButtonPanel, BoxLayout.X_AXIS));
        botButtonPanel.setFocusable(false);
        
        batteryProgressBar.setForeground(Color.green);
        batteryProgressBar.setStringPainted(true);
        batteryProgressBar.setMaximum(100);
        batteryProgressBar.setValue(100);

        new BatteryProgressBarListener(batteryProgressBar, this);

        agentSelector = new JComboBox<String>(new ComboEntityModel(getController()));

        botButtonPanel.add(batteryLabel);
        botButtonPanel.add(batteryProgressBar);
        botButtonPanel.add(botMessageLabel);
        botButtonPanel.add(agentSelector);
        botButtonPanel.add(botMessageButton);
    
        botMessageButton.addMouseListener(new TeamListMouseListener(controller));
    }
    
    private void createEpartnerOptionsBar() {
        epartnerButtonPanel.setLayout(new BoxLayout(epartnerButtonPanel, BoxLayout.X_AXIS));
        epartnerButtonPanel.setFocusable(false);

        epartnerButtonPanel.add(epartnerMessageButton);
        
        epartnerMessageButton.setEnabled(false);
        epartnerMessageButton.addMouseListener(new EPartnerListMouseListener(this));
    }
    
    private void createBotChatSection() {
        botChatPanel.setLayout(new BoxLayout(botChatPanel, BoxLayout.Y_AXIS));
        botChatPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        botChatPanel.setFocusable(false);

        getBotChatSession().setFocusable(false);
        
        botChatPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        botChatPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        botChatPane.setEnabled(true);
        botChatPane.setFocusable(false);
        botChatPane.setColumnHeaderView(new JLabel("Bot Chat Session:"));
        
        botChatPanel.add(botChatPane);
    }
    
    private void createEpartnerChatSection() {
        epartnerChatPanel.setLayout(new BoxLayout(epartnerChatPanel, BoxLayout.Y_AXIS));
        epartnerChatPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        epartnerChatPanel.setFocusable(false);
        
        getEpartnerChatSession().setFocusable(false);
        
        epartnerChatPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        epartnerChatPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        epartnerChatPane.setEnabled(true);
        epartnerChatPane.setVisible(false);
        epartnerChatPane.setFocusable(false);
        epartnerChatPane.setColumnHeaderView(new JLabel("E-partner Chat Session:"));
        
        epartnerChatPanel.add(epartnerChatPane);
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
        getBotChatSession().setText(join(getController().getBotChatHistory(), "\n"));
        getBotChatSession().setCaretPosition(getBotChatSession().getDocument().getLength());
        
        getEpartnerChatSession().setText(join(getController().getEpartnerChatHistory(), "\n"));
        getEpartnerChatSession().setCaretPosition(getEpartnerChatSession().getDocument().getLength());
        
        repaint();
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
        
        getBotChatSession().append(sender + " : " + message + "\n");
        getBotChatSession().setCaretPosition(getBotChatSession().getDocument().getLength());
        
        getEpartnerChatSession().append(sender + " : " + message + "\n");
        getEpartnerChatSession().setCaretPosition(getEpartnerChatSession().getDocument().getLength());

        return null;
    }

    public void setSelectedLocation(int x, int y) {
        this.selectedLocation = new Point(x, y);
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


    /**
     * @return the mapController
     */
    public ClientController getController() {
        return controller;
    }
    
    public JProgressBar getBatteryProgressBar() {
        return batteryProgressBar;
    }
    
    public void setBatteryProgressBar(JProgressBar progressBar) {
        batteryProgressBar = progressBar;
    }
        
    public JScrollPane getEpartnerChatPane() {
        return epartnerChatPane;
    }

    private void setController(ClientController c) {
        if (c != null) {
            c.setGui(this);
        }
        controller = c;
    }

}
