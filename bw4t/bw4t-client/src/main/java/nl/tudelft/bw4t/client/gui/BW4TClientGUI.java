package nl.tudelft.bw4t.client.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import nl.tudelft.bw4t.RendererMapLoader;
import nl.tudelft.bw4t.agent.HumanAgent;
import nl.tudelft.bw4t.client.BW4TClientSettings;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.environment.handlers.PerceptsHandler;
import nl.tudelft.bw4t.client.gui.data.EnvironmentDatabase;
import nl.tudelft.bw4t.client.gui.data.structures.BW4TClientInfo;
import nl.tudelft.bw4t.client.gui.listeners.ChatListMouseListener;
import nl.tudelft.bw4t.client.gui.listeners.TeamListMouseListener;
import nl.tudelft.bw4t.client.gui.menu.ActionPopUpMenu;
import nl.tudelft.bw4t.client.gui.operations.ProcessingOperations;

import org.apache.log4j.Logger;

import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * Render the current state of the world at a fixed rate (10 times per second,
 * see run()) for a client. It connects to the given {@link RemoteEnvironment}
 * on behalf of a given eis entityId. This allows fetching the latest percepts
 * and uses these percepts to track the world state.
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
 * {@link RemoteEnvironment#getAllPerceptsFromEntity(String)} is called by the
 * {@link #run()} repaint scheduler only if we are representing a HumanPlayer.
 * Otherwise the getAllPercepts is done by the agent and we assume
 * processPercepts is called by the {@link RemoteEnvironment} when the agent
 * asked for getAllPercepts.
 * <p>
 * The BW4TRenderer has a list {@link #toBePerformedAction} which is polled by
 * {@link RemoteEnvironment#getAllPerceptsFromEntity(String)} at every call, and
 * merged into the regular percepts. So user mouse clicks are stored there until
 * it's time for perceiving.
 */
public class BW4TClientGUI extends JPanel implements Runnable, MouseListener {
    private static final long serialVersionUID = 2938950289045953493L;
    /**
     * The log4j Logger which displays logs on console
     */
    private final static Logger LOGGER = Logger.getLogger(BW4TClientGUI.class);

    private BW4TClientInfo bw4tClientInfo = new BW4TClientInfo(new JTextArea(8,
            1));

    public BW4TClientInfo getBW4TClientInfo() {
        return bw4tClientInfo;
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
    public BW4TClientGUI(RemoteEnvironment env, String entityId, boolean goal,
            boolean humanPlayer) throws IOException {
        bw4tClientInfo.environment = env;
        init(entityId, humanPlayer);
        this.bw4tClientInfo.goal = goal;
        this.bw4tClientInfo.humanPlayer = humanPlayer;
    }

    /**
     * @param entityId
     *            , the id of the entity that needs to be displayed
     * @param humanAgent
     *            , whether a human is supposed to control this panel
     * @throws IOException
     *             if map can't be loaded.
     */
    public BW4TClientGUI(RemoteEnvironment env, String entityId,
            HumanAgent humanAgent) throws IOException {
        bw4tClientInfo.environment = env;
        bw4tClientInfo.humanAgent = humanAgent;
        this.bw4tClientInfo.humanPlayer = true;
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
            LOGGER.error(
                    "Could not properly set the Native Look and Feel for the BW4T Client",
                    e);
        }
        // Initialize variables
        LOGGER.debug("Initializing agent window for entity: " + entityId);
        bw4tClientInfo.environmentDatabase = new EnvironmentDatabase();
        bw4tClientInfo.environmentDatabase.setEntityId(entityId);
        bw4tClientInfo.buttonPanel = new JPanel();

        JButton jButton = new JButton("all");
        bw4tClientInfo.buttonPanel.add(jButton);
        jButton.addMouseListener(new TeamListMouseListener(this));

        RendererMapLoader.loadMap(bw4tClientInfo.environment.getData()
                .getClient().getMap(), this);

        // Initialize graphics

        bw4tClientInfo.jFrame = new JFrame(entityId);
        bw4tClientInfo.jFrame.setSize(VisualizerSettings.worldX
                * VisualizerSettings.scale + 10, VisualizerSettings.worldY
                * VisualizerSettings.scale + 250);
        bw4tClientInfo.jFrame.setLocation(BW4TClientSettings.getX(),
                BW4TClientSettings.getY());
        bw4tClientInfo.jFrame.setLocation(BW4TClientSettings.getX(),
                BW4TClientSettings.getY());
        bw4tClientInfo.jFrame.setResizable(true);
        bw4tClientInfo.jFrame
                .setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        bw4tClientInfo.jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                LOGGER.info("Exit request received from the Window Manager to close Window of entity: "
                        + entityId);
                bw4tClientInfo.stop = true;
                try {
                    bw4tClientInfo.environment.kill();
                } catch (Exception e1) {
                    LOGGER.error("Could not correctly kill the environment.",
                            e1);
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

        bw4tClientInfo.chatSession.setFocusable(false);
        bw4tClientInfo.chatPane = new JScrollPane(bw4tClientInfo.chatSession);
        chatPanel.add(bw4tClientInfo.chatPane);
        bw4tClientInfo.chatPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        bw4tClientInfo.chatPane
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bw4tClientInfo.chatPane.setEnabled(true);
        bw4tClientInfo.chatPane.setFocusable(false);
        bw4tClientInfo.chatPane
                .setColumnHeaderView(new JLabel("Chat Session:"));

        jPanel.add(bw4tClientInfo.buttonPanel, BorderLayout.NORTH);
        jPanel.add(this, BorderLayout.CENTER);
        jPanel.add(bw4tClientInfo.chatPane, BorderLayout.SOUTH);

        bw4tClientInfo.jFrame.add(jPanel);

        // Initialize mouse listeners for human controller
        if (humanPlayer) {
            this.bw4tClientInfo.jPopupMenu = new JPopupMenu();
            addMouseListener(this);

            bw4tClientInfo.chatSession
                    .addMouseListener(new ChatListMouseListener(bw4tClientInfo));
        }

        bw4tClientInfo.jFrame.setVisible(true);
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
        if (!playerId.equals(bw4tClientInfo.environmentDatabase.getEntityId())) {
            JButton button = new JButton(playerId);
            button.addMouseListener(new TeamListMouseListener(this));
            bw4tClientInfo.buttonPanel.add(button);
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
        ProcessingOperations.processRooms(g2d, bw4tClientInfo);
        ProcessingOperations.processLabels(g2d, bw4tClientInfo);
        ProcessingOperations.processDropZone(g2d, bw4tClientInfo);
        ProcessingOperations.processBlocks(g2d, bw4tClientInfo);
        ProcessingOperations.processEntity(g2d, bw4tClientInfo);
        ProcessingOperations.processSequence(g2d, bw4tClientInfo);
    }

    /**
     * Poll percepts every tick, process them and repaint this panel.
     */
    @Override
    public void run() {
        while (!bw4tClientInfo.stop) {
            if (bw4tClientInfo.humanPlayer) {
                List<Percept> percepts;
                try {
                    percepts = PerceptsHandler.getAllPerceptsFromEntity(
                            bw4tClientInfo.environmentDatabase.getEntityId()
                                    + "gui", bw4tClientInfo.environment);
                    if (percepts != null) {
                        ProcessingOperations.processPercepts(percepts,
                                bw4tClientInfo);
                    }
                } catch (PerceiveException e) {
                    LOGGER.error(
                            "Could not correctly poll the percepts from the environment.",
                            e);
                } catch (NoEnvironmentException e) {
                    LOGGER.error(
                            "Could not correctly poll the percepts from the environment. No connection could be made to the environment",
                            e);
                    bw4tClientInfo.stop = true;
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
                LOGGER.error(
                        "The system ignored the interrupted rendering delay.",
                        e);
            }
        }

        LOGGER.info("Stopped the BW4T Client Renderer.");
        BW4TClientSettings.setWindowParams(bw4tClientInfo.jFrame.getX(),
                bw4tClientInfo.jFrame.getY());
        bw4tClientInfo.jFrame.setVisible(false);
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

        bw4tClientInfo.selectedLocation = new Integer[] { mouseX, mouseY };

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
    public List<Percept> getToBePerformedAction() {
        List<Percept> toBePerformedActionClone = (LinkedList<Percept>) bw4tClientInfo.environmentDatabase
                .getToBePerformedAction().clone();
        bw4tClientInfo.environmentDatabase
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
    public Percept sendToGUI(List<Parameter> parameters) {
        String sender = ((Identifier) parameters.get(0)).getValue();
        String message = ((Identifier) parameters.get(1)).getValue();

        bw4tClientInfo.chatSession.append(sender + " : " + message + "\n");
        bw4tClientInfo.chatSession.setCaretPosition(bw4tClientInfo.chatSession
                .getDocument().getLength());

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
        bw4tClientInfo.environmentDatabase.getRoomLabels().put(label, point);
    }
}