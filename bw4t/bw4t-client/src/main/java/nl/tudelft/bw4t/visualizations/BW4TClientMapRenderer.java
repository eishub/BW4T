package nl.tudelft.bw4t.visualizations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import nl.tudelft.bw4t.RendererMapLoader;
import nl.tudelft.bw4t.agent.HumanAgent;
import nl.tudelft.bw4t.client.BW4TClientSettings;
import nl.tudelft.bw4t.client.BW4TRemoteEnvironment;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.map.Constants;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageTranslator;
import nl.tudelft.bw4t.message.MessageType;
import nl.tudelft.bw4t.visualizations.data.BW4TClientMapRendererData;
import nl.tudelft.bw4t.visualizations.data.DataProcessing;
import nl.tudelft.bw4t.visualizations.data.DoorInfo;
import nl.tudelft.bw4t.visualizations.data.DropZoneInfo;
import nl.tudelft.bw4t.visualizations.data.EnvironmentDatabase;
import nl.tudelft.bw4t.visualizations.data.PerceptsInfo;
import nl.tudelft.bw4t.visualizations.data.RoomInfo;
import nl.tudelft.bw4t.visualizations.listeners.ChatListMouseListener;
import nl.tudelft.bw4t.visualizations.listeners.GoToBlockActionListener;
import nl.tudelft.bw4t.visualizations.listeners.GoToRoomActionListener;
import nl.tudelft.bw4t.visualizations.listeners.GotoPositionActionListener;
import nl.tudelft.bw4t.visualizations.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.visualizations.listeners.PickUpActionListener;
import nl.tudelft.bw4t.visualizations.listeners.PutdownActionListener;
import nl.tudelft.bw4t.visualizations.listeners.TeamListMouseListener;
import nl.tudelft.bw4t.visualizations.menu.ActionPopUpMenu;
import nl.tudelft.bw4t.visualizations.menu.BasicMenuOperations;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * Render the current state of the world at a fixed rate (10 times per second,
 * see run()) for a client. It connects to the given
 * {@link BW4TRemoteEnvironment} on behalf of a given eis entityId. This allows
 * fetching the latest percepts and uses these percepts to track the world
 * state.
 * <p>
 * It is possible to set up this renderer as a human GUI as well. In that case,
 * a human can click with the mouse in the GUI. His actions create GOAL
 * percepts:
 * <ul>
 * <li>sendMessage("all",Message). User asked to send given Message.
 * <li>goToBlock(Id). User asked to go to given block id (Id is a numeral).
 * <li>goTo(Id). User asked to go to given block Id (Id is an identifier)
 * <li>goTo(X,Y). user asked to go to given X,Y position (X,Y both numeral)
 * <li>pickUp(). User asked to do the pick up action
 * <li>putDown(). User asked to do the put down action.
 * </ul>
 * <p>
 * {@link BW4TRemoteEnvironment#getAllPerceptsFromEntity(String)} is called by
 * the {@link #run()} repaint scheduler only if we are representing a
 * HumanPlayer. Otherwise the getAllPercepts is done by the agent and we assume
 * processPercepts is called by the {@link BW4TRemoteEnvironment} when the agent
 * asked for getAllPercepts.
 * <p>
 * The BW4TRenderer has a list {@link #toBePerformedAction} which is polled by
 * {@link BW4TRemoteEnvironment#getAllPerceptsFromEntity(String)} at every call,
 * and merged into the regular percepts. So user mouse clicks are stored there
 * until it's time for perceiving.
 */
public class BW4TClientMapRenderer extends JPanel implements Runnable,
        MouseListener {
    private static final long serialVersionUID = 2938950289045953493L;
    /**
     * The log4j Logger which displays logs on console
     */
    private static Logger logger = Logger
            .getLogger(BW4TClientMapRenderer.class);

    private BW4TClientMapRendererData data = new BW4TClientMapRendererData(
            new JTextArea(8, 1));

    public BW4TClientMapRendererData getData() {
        return data;
    }

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
    public BW4TClientMapRenderer(BW4TRemoteEnvironment env, String entityId,
            boolean goal, boolean humanPlayer) throws IOException {
        data.environment = env;
        init(entityId, humanPlayer);
        this.data.goal = goal;
        this.data.humanPlayer = humanPlayer;
    }

    /**
     * @param entityId
     *            , the id of the entity that needs to be displayed
     * @param humanAgent
     *            , whether a human is supposed to control this panel
     * @throws IOException
     *             if map can't be loaded.
     */
    public BW4TClientMapRenderer(BW4TRemoteEnvironment env, String entityId,
            HumanAgent humanAgent) throws IOException {
        data.environment = env;
        data.humanAgent = humanAgent;
        this.data.humanPlayer = true;
        init(entityId, true);
    }

    /**
     * @param entityId
     *            , the id of the entity that needs to be displayed
     * @param humanPlayer
     *            , whether a human is supposed to control this panel
     * @throws IOException
     */
    private void init(final String entityId, boolean humanPlayer)
            throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Initialize variables
        logger.debug("Initializing agent window for entity: " + entityId);

        data.environmentDatabase = new EnvironmentDatabase();
        data.perceptsInfo = new PerceptsInfo(data.environmentDatabase,
                data.chatSession);

        data.environmentDatabase.setEntityId(entityId);

        data.buttonPanel = new JPanel();
        // buttonPanel.setBackground(Color.BLACK);
        JButton jButton = new JButton("all");
        data.buttonPanel.add(jButton);
        jButton.addMouseListener(new TeamListMouseListener(this));

        RendererMapLoader.loadMap(data.environment.getMap(), this);

        // Initialize graphics

        data.jFrame = new JFrame(entityId);
        data.jFrame.setSize(VisualizerSettings.worldX
                * VisualizerSettings.scale + 10, VisualizerSettings.worldY
                * VisualizerSettings.scale + 250);
        data.jFrame.setLocation(BW4TClientSettings.getX(),
                BW4TClientSettings.getY());
        data.jFrame.setLocation(BW4TClientSettings.getX(),
                BW4TClientSettings.getY());
        data.jFrame.setResizable(true);
        data.jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        data.jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();
                logger.info("Exit request received from the Window Manager to close Window of entity: "
                        + entityId);
                data.stop = true;
                try {
                    data.environment.kill();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel jPanel = new JPanel(new BorderLayout());

        // create short chat history window
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel
                .setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        chatPanel.setFocusable(false);

        data.chatSession.setFocusable(false);
        data.chatPane = new JScrollPane(data.chatSession);
        chatPanel.add(data.chatPane);
        data.chatPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        data.chatPane
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        data.chatPane.setEnabled(true);
        data.chatPane.setFocusable(false);
        data.chatPane.setColumnHeaderView(new JLabel("Chat Session:"));

        jPanel.add(data.buttonPanel, BorderLayout.NORTH);
        jPanel.add(this, BorderLayout.CENTER);
        jPanel.add(data.chatPane, BorderLayout.SOUTH);

        data.jFrame.add(jPanel);

        // Initialize mouse listeners for human controller
        if (humanPlayer) {
            this.data.jPopupMenu = new JPopupMenu();
            addMouseListener(this);

            data.chatSession.addMouseListener(new ChatListMouseListener(this));
        }

        data.jFrame.setVisible(true);
        // Start repainting graphics
        Thread paintThread = new Thread(this);
        paintThread.start();
    }

    /**
     * Adds a player by adding a new button to the button panel, facilitating
     * sending messages to this player
     * 
     * @param playerId
     *            , the Id of the player to be added
     */
    public void addPlayer(String playerId) {
        if (!playerId.equals(data.environmentDatabase.getEntityId())) {
            JButton button = new JButton(playerId);
            button.addMouseListener(new TeamListMouseListener(this));
            data.buttonPanel.add(button);
        }
    }

    /**
     * Processes all objects to display them on the panel
     * 
     * @param g
     *            , the graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        DataProcessing.processRooms(g2d, data);
        DataProcessing.processLabels(g2d, data);
        DataProcessing.processDropZone(g2d, data);
        DataProcessing.processBlocks(g2d, data);
        DataProcessing.processEntity(g2d, data);
        DataProcessing.processSequence(g2d, data);
    }

    /**
     * Poll percepts every tick, process them and repaint this panel.
     */
    @Override
    public void run() {
        while (!data.stop) {
            if (data.humanPlayer) {
                LinkedList<Percept> percepts;
                try {
                    percepts = data.environment
                            .getAllPerceptsFromEntity(data.environmentDatabase
                                    .getEntityId() + "gui");
                    if (percepts != null) {
                        data.perceptsInfo.processPercepts(percepts);
                    }
                } catch (PerceiveException e) {
                    e.printStackTrace();
                } catch (NoEnvironmentException e) {
                    e.printStackTrace();
                    data.stop = true; // stop and exit, can't handle this.
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
                System.err.println("ignoring interupted rendering delay");
            }
        }

        System.out.println("stopped BW4T renderer");
        BW4TClientSettings.setWindowParams(data.jFrame.getX(),
                data.jFrame.getY());
        data.jFrame.setVisible(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Get coordinates of mouse click
        int mouseX = e.getX();

        int mouseY = e.getY();

        data.selectedLocation = new Integer[] { mouseX, mouseY };

        ActionPopUpMenu.buildPopUpMenu(this);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Method used for returning the next action that a human player wants the
     * bot to perform. This is received by the GOAL human bot, and then
     * forwarded to the entity on the server side.
     * 
     * @return a percept containing the next action to be performed
     */
    public LinkedList<Percept> getToBePerformedAction() {
        LinkedList<Percept> toBePerformedActionClone = (LinkedList<Percept>) data.environmentDatabase
                .getToBePerformedAction().clone();
        data.environmentDatabase
                .setToBePerformedAction(new LinkedList<Percept>());
        return toBePerformedActionClone;
    }

    /**
     * When a GOAL agent performs the sendToGUI action it is forwarded to this
     * method, the message is then posted on the chat window contained in the
     * GUI.
     * 
     * @param parameters
     *            , the action parameters containing the message sender and the
     *            message itself.
     * @return a null percept as no real percept should be returned
     */
    public Percept sendToGUI(LinkedList<Parameter> parameters) {
        String sender = ((Identifier) parameters.get(0)).getValue();
        String message = ((Identifier) parameters.get(1)).getValue();

        data.chatSession.append(sender + " : " + message + "\n");
        data.chatSession.setCaretPosition(data.chatSession.getDocument()
                .getLength());

        return null;
    }

    /**
     * Adds a label with a corresponding point near which it should be drawn to
     * the label list.
     * 
     * @param label
     *            , the label
     * @param point
     *            , the point
     */
    public void addLabel(String label, Point point) {
        data.environmentDatabase.getRoomLabels().put(label, point);
    }
}