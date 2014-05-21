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
     * Data needed for updating the graphical representation of the world
     */
    private EnvironmentDatabase environmentDatabase;
    private PerceptsInfo perceptsInfo;

    /**
     * The log4j Logger which displays logs on console
     */
    private static Logger logger = Logger
            .getLogger(BW4TClientMapRenderer.class);

    private boolean stop;

    private JFrame jFrame;
    private JPanel buttonPanel;
    private final JTextArea chatSession = new JTextArea(8, 1);
    private JScrollPane chatPane;

    /**
     * Private variables only used for human player
     */
    private HumanAgent humanAgent;
    private JPopupMenu jPopupMenu;
    private Integer[] selectedLocation;
    private boolean goal;

    private boolean humanPlayer;

    /**
     * Most of the server interfacing goes through the std eis percepts
     */
    private BW4TRemoteEnvironment environment;

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
        environment = env;
        init(entityId, humanPlayer);
        this.goal = goal;
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
    public BW4TClientMapRenderer(BW4TRemoteEnvironment env, String entityId,
            HumanAgent humanAgent) throws IOException {
        environment = env;
        this.setHumanAgent(humanAgent);
        this.humanPlayer = true;
        init(entityId, true);
    }

    /**
     * Add door to the renderer
     * 
     * @param x
     *            center x
     * @param y
     *            center y
     * @param width
     *            width of door
     * @param height
     *            height of door
     * @param roomname
     *            name of room in which door is placed
     */
    public void addDoor(double x, double y, double width, double height,
            String roomname) {
        DoorInfo info = new DoorInfo(x, y, width, height, environmentDatabase);
        for (RoomInfo room : environmentDatabase.getRooms()) {
            if (room.getName().equals(roomname)) {
                room.addDoor(info);
                return;
            }
        }

        if (roomname.equals("DropZone")) {
            environmentDatabase.getDropZone().addDoor(info);
        }
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

        environmentDatabase = new EnvironmentDatabase();
        perceptsInfo = new PerceptsInfo(environmentDatabase, getChatSession());

        environmentDatabase.setEntityId(entityId);

        buttonPanel = new JPanel();
        // buttonPanel.setBackground(Color.BLACK);
        JButton jButton = new JButton("all");
        buttonPanel.add(jButton);
        jButton.addMouseListener(new TeamListMouseListener(this));

        RendererMapLoader.loadMap(environment.getMap(), this);

        // Initialize graphics

        jFrame = new JFrame(entityId);
        jFrame.setSize(VisualizerSettings.worldX * VisualizerSettings.scale
                + 10, VisualizerSettings.worldY * VisualizerSettings.scale
                + 250);
        jFrame.setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
        jFrame.setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
        jFrame.setResizable(true);
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();
                logger.info("Exit request received from the Window Manager to close Window of entity: "
                        + entityId);
                setStop();
                try {
                    environment.kill();
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

        getChatSession().setFocusable(false);
        chatPane = new JScrollPane(getChatSession());
        chatPanel.add(chatPane);
        chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatPane.setEnabled(true);
        chatPane.setFocusable(false);
        chatPane.setColumnHeaderView(new JLabel("Chat Session:"));

        jPanel.add(buttonPanel, BorderLayout.NORTH);
        jPanel.add(this, BorderLayout.CENTER);
        jPanel.add(chatPane, BorderLayout.SOUTH);

        jFrame.add(jPanel);

        // Initialize mouse listeners for human controller
        if (humanPlayer) {
            this.jPopupMenu = new JPopupMenu();
            addMouseListener(this);

            getChatSession().addMouseListener(new ChatListMouseListener(this));
        }

        jFrame.setVisible(true);
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
        if (!playerId.equals(environmentDatabase.getEntityId())) {
            JButton button = new JButton(playerId);
            button.addMouseListener(new TeamListMouseListener(this));
            buttonPanel.add(button);
        }
    }

    /**
     * @param worldX
     *            , the x dimension of the world as specified in the map file
     * @param worldY
     *            , the y dimension of the world as specified in the map file
     */
    public void setWorldDimensions(int worldX, int worldY) {
        VisualizerSettings.worldX = worldX;
        VisualizerSettings.worldY = worldY;
    }

    /**
     * @param room
     *            , add a representation of a room (Integer[4] with x, y, width
     *            and height)
     */
    public void addRoom(double x, double y, double width, double height,
            String name) {
        environmentDatabase.getRooms().add(
                new RoomInfo(x, y, width, height, name));
    }

    /**
     * @param dropZone
     *            , add a representation of the dropzone (Integer[4] with x, y,
     *            width and height)
     */
    public void addDropZone(Integer[] dropZone) {
        environmentDatabase.setDropZone(new DropZoneInfo(dropZone[0],
                dropZone[1], dropZone[2], dropZone[3]));
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
        processRooms(g2d);
        processLabels(g2d);
        processDropZone(g2d);
        processBlocks(g2d);
        processEntity(g2d);
        processSequence(g2d);
    }

    /**
     * Process all rooms and their connected doors, and display them in the
     * panel with an outline
     * 
     * @param g2d
     *            , the graphics2d object
     */
    private void processRooms(Graphics2D g2d) {
        for (RoomInfo room : environmentDatabase.getRooms()) {
            // first paint the doors. Matches the {@link ServerMapRenderer}
            if (environmentDatabase.getOccupiedRooms().contains(
                    findLabelForRoom(room))) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.GREEN);
            }

            for (DoorInfo door : room.getDoors()) {
                g2d.fill(transformRectangle(new Rectangle2D.Double(door.getX(),
                        door.getY(), door.getWidth(), door.getHeight())));
            }

            // paint the room
            g2d.setColor(Color.GRAY);
            Shape roomDisplayCoordinates = transformRectangle(new Rectangle2D.Double(
                    room.getX(), room.getY(), room.getWidth(), room.getHeight()));
            g2d.fill(roomDisplayCoordinates);
            g2d.setColor(Color.BLACK);
            g2d.draw(roomDisplayCoordinates);

        }
    }

    /**
     * Process the labels for the different areas
     * 
     * @param g2d
     *            , the graphics2d object
     */
    private void processLabels(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        HashMap<String, Point> roomLabels = environmentDatabase.getRoomLabels();
        for (String label : roomLabels.keySet()) {
            g2d.drawString(label, roomLabels.get(label).x
                    * VisualizerSettings.scale
                    - VisualizerSettings.roomTextOffset,
                    roomLabels.get(label).y * VisualizerSettings.scale);
        }
    }

    /**
     * Process the drop zone and connected doors and display them in the panel
     * 
     * @param g2d
     *            , the graphics2d object
     */
    private void processDropZone(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        DropZoneInfo dropZone = environmentDatabase.getDropZone();
        g2d.fill(transformRectangle(new Rectangle2D.Double(dropZone.getX(),
                dropZone.getY(), dropZone.getWidth(), dropZone.getHeight())));
        g2d.setColor(Color.BLACK);
        g2d.draw(transformRectangle(new Rectangle2D.Double(dropZone.getX(),
                dropZone.getY(), dropZone.getWidth(), dropZone.getHeight())));

        if (environmentDatabase.getOccupiedRooms().contains(
                VisualizerSettings.DROPZONE_NAME)) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(Color.GREEN);
        }

        for (DoorInfo door : dropZone.getDoors()) {
            g2d.fill(transformRectangle(new Rectangle2D.Double(door.getX(),
                    door.getY(), door.getWidth(), door.getHeight())));
        }
    }

    /**
     * Process all blocks that are visible and display them with their color
     * 
     * @param g2d
     *            , the graphics2d object
     */
    private void processBlocks(Graphics2D g2d) {
        HashMap<Long, BlockColor> allBlocks = environmentDatabase
                .getAllBlocks();
        for (Long box : allBlocks.keySet()) {
            if (environmentDatabase.getVisibleBlocks().contains(box)) {
                HashMap<Long, java.awt.geom.Point2D.Double> objectPositions = environmentDatabase
                        .getObjectPositions();
                if (objectPositions.get(box) != null) {
                    g2d.setColor(allBlocks.get(box).getColor());
                    g2d.fill(transformRectangle(new Rectangle2D.Double(
                            objectPositions.get(box).getX(), objectPositions
                                    .get(box).getY(), Constants.BLOCK_SIZE,
                            Constants.BLOCK_SIZE)));
                }
            }
        }
    }

    /**
     * Display the robot this panel represents. The color is adapted depending
     * on whether it holds a block or not.
     * 
     * @param g2d
     *            , the graphics2d object
     */
    private void processEntity(Graphics2D g2d) {
        g2d.setColor(environmentDatabase.getEntityColor());
        Double[] entityLocation = environmentDatabase.getEntityLocation();
        g2d.fill(transformRectangle(new Rectangle2D.Double(entityLocation[0],
                entityLocation[1], Constants.ROBOT_SIZE, Constants.ROBOT_SIZE)));
    }

    /**
     * Display the goal sequence
     * 
     * @param g2d
     *            , the graphics2d object
     */
    private void processSequence(Graphics2D g2d) {
        int startPosX = 0;
        for (BlockColor color : environmentDatabase.getSequence()) {
            g2d.setColor(color.getColor());
            g2d.fill(new Rectangle2D.Double(startPosX,
                    VisualizerSettings.worldY * VisualizerSettings.scale, 20,
                    20));
            if (environmentDatabase.getSequenceIndex() > (startPosX / 20)) {
                g2d.setColor(Color.BLACK);
                int[] xpoints = new int[] { startPosX, startPosX,
                        startPosX + 20 };
                int[] ypoints = new int[] {
                        VisualizerSettings.worldY * VisualizerSettings.scale,
                        VisualizerSettings.worldY * VisualizerSettings.scale
                                + 20,
                        VisualizerSettings.worldY * VisualizerSettings.scale
                                + 10 };
                g2d.fillPolygon(xpoints, ypoints, 3);
            }
            startPosX += 20;
        }
    }

    /**
     * Transform world coordinates to our coordinates.
     * 
     * @param boundingBox
     *            , a rectangle in the world coordinate system
     * @return The new rectangle in our coordinate system.
     */
    private Shape transformRectangle(Rectangle2D boundingBox) {
        int width = (int) (boundingBox.getWidth() * VisualizerSettings.scale);
        int height = (int) (boundingBox.getHeight() * VisualizerSettings.scale);
        int xPos = (int) (boundingBox.getX() * VisualizerSettings.scale)
                - (width / 2);
        int yPos = (int) (boundingBox.getY() * VisualizerSettings.scale)
                - (height / 2);

        return new Rectangle(xPos, yPos, width, height);
    }

    /**
     * Poll percepts every tick, process them and repaint this panel.
     */
    @Override
    public void run() {
        while (!stop) {
            if (humanPlayer) {
                LinkedList<Percept> percepts;
                try {
                    percepts = environment
                            .getAllPerceptsFromEntity(environmentDatabase
                                    .getEntityId() + "gui");
                    if (percepts != null) {
                        perceptsInfo.processPercepts(percepts);
                    }
                } catch (PerceiveException e) {
                    e.printStackTrace();
                } catch (NoEnvironmentException e) {
                    e.printStackTrace();
                    setStop(); // stop and exit, can't handle this.
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
        BW4TClientSettings.setWindowParams(jFrame.getX(), jFrame.getY());
        jFrame.setVisible(false);
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

        selectedLocation = new Integer[] { mouseX, mouseY };

        buildPopUpMenu();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Used for building the pop up menu that displays the actions a user can
     * undertake
     */
    private void buildPopUpMenu() {
        // Check if pressing on a color in the goal sequence list
        int startPosX = 0;
        for (BlockColor color : environmentDatabase.getSequence()) {
            Shape colorBounds = new Rectangle2D.Double(startPosX,
                    VisualizerSettings.worldY * VisualizerSettings.scale, 20,
                    20);
            if (colorBounds.contains(new Point(selectedLocation[0],
                    selectedLocation[1]))) {
                buildPopUpMenuForGoalColor(color);
                getjPopupMenu().show(this, selectedLocation[0],
                        selectedLocation[1]);
                return;
            }
            startPosX += 20;
        }

        // Check if pressing on a room
        for (RoomInfo room : environmentDatabase.getRooms()) {
            Shape roomBoundaries = transformRectangle(new Rectangle2D.Double(
                    room.getX(), room.getY(), room.getWidth(), room.getHeight()));
            if (roomBoundaries.contains(new Point(selectedLocation[0],
                    selectedLocation[1]))) {
                // Check if pressing on a block
                for (Long boxID : environmentDatabase.getVisibleBlocks()) {
                    HashMap<Long, java.awt.geom.Point2D.Double> objectPositions = environmentDatabase
                            .getObjectPositions();
                    Shape boxBoundaries = transformRectangle(new Rectangle2D.Double(
                            objectPositions.get(boxID).getX(), objectPositions
                                    .get(boxID).getY(), Constants.BLOCK_SIZE,
                            Constants.BLOCK_SIZE));
                    if (boxBoundaries.contains(new Point(selectedLocation[0],
                            selectedLocation[1]))) {
                        if (closeToBox(boxID)) {
                            buildPopUpMenuForBeingAtBlock(boxID, room);
                            getjPopupMenu().show(this, selectedLocation[0],
                                    selectedLocation[1]);
                        } else {
                            buildPopUpMenuForBlock(boxID, room);
                            getjPopupMenu().show(this, selectedLocation[0],
                                    selectedLocation[1]);
                        }
                        return;
                    }
                }
                buildPopUpMenuRoom(room);
                getjPopupMenu().show(this, selectedLocation[0],
                        selectedLocation[1]);
                return;
            }
        }

        // Check for dropzone
        DropZoneInfo dropZone = environmentDatabase.getDropZone();
        Shape dropZoneBoundaries = transformRectangle(new Rectangle2D.Double(
                dropZone.getX(), dropZone.getY(), dropZone.getWidth(),
                dropZone.getHeight()));
        if (dropZoneBoundaries.contains(new Point(selectedLocation[0],
                selectedLocation[1]))) {
            buildPopUpMenuRoom(dropZone);
            getjPopupMenu()
                    .show(this, selectedLocation[0], selectedLocation[1]);
            return;
        }

        // Otherwise it is a hallway
        buildPopUpMenuForHallway();
        getjPopupMenu().show(this, selectedLocation[0], selectedLocation[1]);
    }

    /**
     * Builds a pop up menu for when the player clicked on a hallway
     */
    public void buildPopUpMenuForHallway() {
        getjPopupMenu().removeAll();
        long holdingID = environmentDatabase.getHoldingID();
        Color entityColor = environmentDatabase.getEntityColor();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to: ", jPopupMenu);

        JMenuItem menuItem = new JMenuItem("Go to here");
        menuItem.addActionListener(new GotoPositionActionListener(new Point(
                selectedLocation[0] / VisualizerSettings.scale,
                selectedLocation[1] / VisualizerSettings.scale), this));
        getjPopupMenu().add(menuItem);

        if (holdingID != Long.MAX_VALUE) {
            menuItem = new JMenuItem("Put down box");
            menuItem.addActionListener(new PutdownActionListener(this));
            getjPopupMenu().add(menuItem);
        }

        getjPopupMenu().addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ", jPopupMenu);

        for (RoomInfo room : environmentDatabase.getRooms()) {
            addMenuItemToPopupMenu(new BW4TMessage(
                    MessageType.amWaitingOutsideRoom, findLabelForRoom(room),
                    null, null));
        }

        addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.amWaitingOutsideRoom,
                findLabelForRoom(environmentDatabase.getDropZone()), null, null));

        if (holdingID != Long.MAX_VALUE) {
            addMenuItemToPopupMenu(new BW4TMessage(MessageType.hasColor, null,
                    ColorTranslator.translate2ColorString(entityColor), null));

            JMenu submenu = addSubMenuToPopupMenu("I have a "
                    + ColorTranslator.translate2ColorString(entityColor)
                    + " block from ");

            for (RoomInfo roomInfo : environmentDatabase.getRooms()) {
                String label = findLabelForRoom(roomInfo);
                menuItem = new JMenuItem(Long.toString(roomInfo.getId()));
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.hasColor, label,
                                ColorTranslator
                                        .translate2ColorString(entityColor),
                                null), this));
                submenu.add(menuItem);
            }
        }

        getjPopupMenu().addSeparator();
        menuItem = new JMenuItem("Close menu");
        getjPopupMenu().add(menuItem);
    }

    /**
     * Build the pop up menu for sending chat messages to all players
     */
    public void buildPopUpMenuForChat() {
        getjPopupMenu().removeAll();

        BasicMenuOperations.addSectionTitleToPopupMenu("Answer:", jPopupMenu);

        addMenuItemToPopupMenu(new BW4TMessage(MessageType.yes));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.no));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDoNotKnow));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.ok));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDo));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDoNot));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.wait));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.onTheWay));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.almostThere));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.farAway));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.delayed));

        getjPopupMenu().addSeparator();
        JMenuItem menuItem = new JMenuItem("Close menu");
        getjPopupMenu().add(menuItem);

    }

    /**
     * Build the pop up menu for clicking on a group goal color
     * 
     * @param color
     *            , the color that was clicked
     */
    public void buildPopUpMenuForGoalColor(BlockColor color) {
        long holdingID = environmentDatabase.getHoldingID();
        getjPopupMenu().removeAll();

        JMenuItem menuItem = new JMenuItem(color.getName());
        getjPopupMenu().add(menuItem);
        getjPopupMenu().addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:", jPopupMenu);

        if (holdingID != Long.MAX_VALUE) {
            menuItem = new JMenuItem("Put down block");
            menuItem.addActionListener(new PutdownActionListener(this));
            getjPopupMenu().add(menuItem);
        }

        getjPopupMenu().addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ", jPopupMenu);

        addMenuItemToPopupMenu(new BW4TMessage(MessageType.lookingFor, null,
                color.getName(), null));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.willGetColor, null,
                color.getName(), null));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.droppedOffBlock,
                null, color.getName(), null));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.weNeed, null,
                color.getName(), null));

        if (holdingID != Long.MAX_VALUE) {
            addMenuItemToPopupMenu(new BW4TMessage(MessageType.hasColor, null,
                    color.getName(), null));

            JMenu submenu = addSubMenuToPopupMenu("I have a " + color
                    + " block from room");

            for (RoomInfo room : environmentDatabase.getRooms()) {
                String label = findLabelForRoom(room);
                menuItem = new JMenuItem(Long.toString(room.getId()));
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.hasColorFromRoom, label,
                                color.getName(), null), this));
                submenu.add(menuItem);
            }
        }

        getjPopupMenu().addSeparator();
        BasicMenuOperations.addSectionTitleToPopupMenu("Ask: ", jPopupMenu);

        addMenuItemToPopupMenu(new BW4TMessage(MessageType.whereIsColor, null,
                color.getName(), null));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.whoHasABlock, null,
                color.getName(), null));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.whereShouldIGo));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.whatColorShouldIGet));

        getjPopupMenu().addSeparator();
        menuItem = new JMenuItem("Close menu");
        getjPopupMenu().add(menuItem);
    }

    /**
     * Used for building the pop up menu when clicking on the agent while it is
     * near a box
     * 
     * @param box
     *            , the box that the robot is at.
     */
    private void buildPopUpMenuForBeingAtBlock(Long boxID, RoomInfo room) {
        String label = findLabelForRoom(room);

        getjPopupMenu().removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:", jPopupMenu);

        HashMap<Long, BlockColor> allBlocks = environmentDatabase
                .getAllBlocks();

        JMenuItem menuItem = new JMenuItem("Pick up " + allBlocks.get(boxID)
                + " block");
        menuItem.addActionListener(new PickUpActionListener(this));
        getjPopupMenu().add(menuItem);

        // Message sending
        getjPopupMenu().addSeparator();
        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ", jPopupMenu);
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.roomContains, label,
                allBlocks.get(boxID).getName(), null));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.amGettingColor,
                label, allBlocks.get(boxID).getName(), null));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.atBox, null,
                allBlocks.get(boxID).getName(), null));

        getjPopupMenu().addSeparator();
        menuItem = new JMenuItem("Close menu");
        getjPopupMenu().add(menuItem);
    }

    /**
     * Used for building a pop up menu when clicking on a box
     * 
     * @param box
     *            , the box that was clicked on
     */
    private void buildPopUpMenuForBlock(Long boxID, RoomInfo room) {
        String label = findLabelForRoom(room);

        getjPopupMenu().removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:", jPopupMenu);
        HashMap<Long, BlockColor> allBlocks = environmentDatabase
                .getAllBlocks();

        JMenuItem menuItem = new JMenuItem("Go to " + allBlocks.get(boxID)
                + " block");
        menuItem.addActionListener(new GoToBlockActionListener(boxID, this));
        getjPopupMenu().add(menuItem);

        // Message sending
        getjPopupMenu().addSeparator();
        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ", jPopupMenu);
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.roomContains, label,
                allBlocks.get(boxID).getName(), null));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.amGettingColor,
                label, allBlocks.get(boxID).getName(), null));

        getjPopupMenu().addSeparator();
        menuItem = new JMenuItem("Close menu");
        getjPopupMenu().add(menuItem);
    }

    /**
     * Used for building a pop up menu when clicking on a room
     * 
     * @param room
     *            , the room that was clicked on
     */
    private void buildPopUpMenuRoom(RoomInfo room) {
        long holdingID = environmentDatabase.getHoldingID();
        Color entityColor = environmentDatabase.getEntityColor();
        String label = findLabelForRoom(room);
        getjPopupMenu().removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to: ", jPopupMenu);

        JMenuItem menuItem = new JMenuItem("Go to " + label);
        menuItem.addActionListener(new GoToRoomActionListener(label, this));
        getjPopupMenu().add(menuItem);

        if (holdingID != Long.MAX_VALUE) {
            menuItem = new JMenuItem("Put down box");
            menuItem.addActionListener(new PutdownActionListener(this));
            getjPopupMenu().add(menuItem);
        }

        getjPopupMenu().addSeparator();

        // Message sending
        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ", jPopupMenu);

        addMenuItemToPopupMenu(new BW4TMessage(MessageType.inRoom, label, null,
                null));

        JMenu submenu = addSubMenuToPopupMenu(label + " contains ");

        for (String color : ColorTranslator.getAllColors()) {
            menuItem = new JMenuItem(color);
            menuItem.addActionListener(new MessageSenderActionListener(
                    new BW4TMessage(MessageType.roomContains, label, color,
                            null), this));
            submenu.add(menuItem);
        }

        submenu = addSubMenuToPopupMenu(label + " contains ");

        for (int i = 0; i < 6; i++) {
            JMenu submenuColor = new JMenu("" + i);
            submenu.add(submenuColor);

            for (String color : ColorTranslator.getAllColors()) {
                menuItem = new JMenuItem(color);
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.roomContainsAmount, label,
                                color, i), this));
                submenuColor.add(menuItem);
            }
        }

        addMenuItemToPopupMenu(new BW4TMessage(MessageType.checked, label,
                null, null));

        submenu = addSubMenuToPopupMenu(label + " has been checked by ");

        ArrayList<String> otherPlayers = environmentDatabase.getOtherPlayers();
        for (int i = 0; i < otherPlayers.size(); i++) {
            menuItem = new JMenuItem("" + otherPlayers.get(i));
            menuItem.addActionListener(new MessageSenderActionListener(
                    new BW4TMessage(MessageType.checked, label, null,
                            otherPlayers.get(i)), this));
            submenu.add(menuItem);
        }

        addMenuItemToPopupMenu(new BW4TMessage(MessageType.roomIsEmpty, label,
                null, null));

        if (holdingID != Long.MAX_VALUE) {
            addMenuItemToPopupMenu(new BW4TMessage(
                    MessageType.aboutToDropOffBlock, null,
                    ColorTranslator.translate2ColorString(entityColor), null));
        } else {
            addMenuItemToPopupMenu(new BW4TMessage(MessageType.droppedOffBlock,
                    null, null, null));
        }

        if (holdingID != Long.MAX_VALUE) {
            addMenuItemToPopupMenu(new BW4TMessage(MessageType.hasColor, null,
                    ColorTranslator.translate2ColorString(entityColor), null));

            submenu = addSubMenuToPopupMenu("I have a "
                    + ColorTranslator.translate2ColorString(entityColor)
                    + " block from ");

            for (RoomInfo roomInfo : environmentDatabase.getRooms()) {
                String labelT = findLabelForRoom(roomInfo);
                menuItem = new JMenuItem(labelT);
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.hasColor, labelT,
                                ColorTranslator
                                        .translate2ColorString(entityColor),
                                null), this));
                submenu.add(menuItem);
            }
        }

        BasicMenuOperations.addSectionTitleToPopupMenu("Ask:", jPopupMenu);
        addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.isAnybodyGoingToRoom, label, null, null));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.whatIsInRoom, label,
                null, null));
        addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.hasAnybodyCheckedRoom, label, null, null));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.whoIsInRoom, label,
                null, null));

        getjPopupMenu().addSeparator();
        menuItem = new JMenuItem("Close menu");
        getjPopupMenu().add(menuItem);
    }

    /**
     * Builds pop up menu for sending requests to a certain player, is called
     * when a player button is pressed
     * 
     * @param playerId
     *            , the playerId that the request should be sent to
     */
    public void buildPopUpMenuForRequests(String playerId) {
        getjPopupMenu().removeAll();
        BasicMenuOperations.addSectionTitleToPopupMenu("Request:", jPopupMenu);

        // Check if the playerId is a specific player
        String receiver = "Somebody";
        if (!playerId.equalsIgnoreCase("all"))
            receiver = playerId;

        addMenuItemToPopupMenu(new BW4TMessage(MessageType.putDown, null, null,
                receiver));

        JMenu submenu = addSubMenuToPopupMenu(receiver + " go to room");

        for (RoomInfo room : environmentDatabase.getRooms()) {
            String label = findLabelForRoom(room);
            JMenuItem menuItem = new JMenuItem(label);
            menuItem.addActionListener(new MessageSenderActionListener(
                    new BW4TMessage(MessageType.goToRoom, label, null, receiver),
                    this));
            submenu.add(menuItem);
        }

        submenu = addSubMenuToPopupMenu(receiver + " find a color");

        for (String color : ColorTranslator.getAllColors()) {
            JMenuItem menuItem = new JMenuItem(color);
            menuItem.addActionListener(new MessageSenderActionListener(
                    new BW4TMessage(MessageType.findColor, null, color,
                            receiver), this));
            submenu.add(menuItem);
        }

        submenu = addSubMenuToPopupMenu(receiver + " get the color from room");

        for (String color : ColorTranslator.getAllColors()) {
            JMenu submenu2 = new JMenu(color);
            submenu.add(submenu2);

            for (RoomInfo room : environmentDatabase.getRooms()) {
                String label = findLabelForRoom(room);
                JMenuItem menuItem = new JMenuItem(label);
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.getColorFromRoom, label,
                                color, receiver), this));
                submenu2.add(menuItem);
            }
        }

        BasicMenuOperations.addSectionTitleToPopupMenu("Ask:", getjPopupMenu());
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.areYouClose, null,
                null, receiver));
        addMenuItemToPopupMenu(new BW4TMessage(MessageType.willYouBeLong, null,
                null, receiver));

        getjPopupMenu().addSeparator();
        JMenuItem menuItem = new JMenuItem("Close menu");
        getjPopupMenu().add(menuItem);
    }

    /**
     * Adds a menu to the pop up menu
     * 
     * @param text
     *            , the title of the menu
     * @return the menu
     */
    public JMenu addSubMenuToPopupMenu(String text) {
        JMenu menu = new JMenu(text);
        getjPopupMenu().add(menu);

        return menu;
    }



    /**
     * Adds a menu item to the pop up menu, used for messages
     * 
     * @param message
     *            , the message that this item represents
     */
    public void addMenuItemToPopupMenu(BW4TMessage message) {
        JMenuItem menuItem = new JMenuItem(
                MessageTranslator.translateMessage(message));
        menuItem.addActionListener(new MessageSenderActionListener(message,
                this));
        getjPopupMenu().add(menuItem);
    }

    /**
     * Method to determine if the player is close to a box (within 0.5 of the
     * coordinates of the box)
     * 
     * @param boxID
     *            , the box that should be checked
     * @return true if close to the box, false if not
     */
    private boolean closeToBox(Long boxID) {
        HashMap<Long, java.awt.geom.Point2D.Double> objectPositions = environmentDatabase
                .getObjectPositions();
        double minX = objectPositions.get(boxID).getX() - 0.5;
        double maxX = objectPositions.get(boxID).getX() + 0.5;
        double minY = objectPositions.get(boxID).getY() - 0.5;
        double maxY = objectPositions.get(boxID).getY() + 0.5;
        Double[] entityLocation = environmentDatabase.getEntityLocation();
        if ((entityLocation[0] > minX) && (entityLocation[0] < maxX)
                && (entityLocation[1] > minY) && (entityLocation[1] < maxY))
            return true;
        else
            return false;
    }

    /**
     * Method used for returning the next action that a human player wants the
     * bot to perform. This is received by the GOAL human bot, and then
     * forwarded to the entity on the server side.
     * 
     * @return a percept containing the next action to be performed
     */
    public LinkedList<Percept> getToBePerformedAction() {
        LinkedList<Percept> toBePerformedActionClone = (LinkedList<Percept>) environmentDatabase
                .getToBePerformedAction().clone();
        environmentDatabase.setToBePerformedAction(new LinkedList<Percept>());
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

        getChatSession().append(sender + " : " + message + "\n");
        getChatSession().setCaretPosition(
                getChatSession().getDocument().getLength());

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
        environmentDatabase.getRoomLabels().put(label, point);
    }

    /**
     * Helper method to find the nav point label corresponding to a certain room
     * 
     * @param room
     *            , the room for which to find the navpoint label
     * @return the label
     */
    private String findLabelForRoom(RoomInfo room) {
        HashMap<String, Point> roomLabels = environmentDatabase.getRoomLabels();
        for (String label : roomLabels.keySet()) {
            Shape roomBoundaries = new Rectangle2D.Double(room.getX(),
                    room.getY(), room.getWidth(), room.getHeight());
            if (roomBoundaries.contains(roomLabels.get(label))) {
                return label;
            }
        }
        return null;
    }

    public JFrame getFrame() {
        return jFrame;
    }

    public void setStop() {
        stop = true;
    }

    public HumanAgent getHumanAgent() {
        return humanAgent;
    }

    public void setHumanAgent(HumanAgent humanAgent) {
        this.humanAgent = humanAgent;
    }

    public JTextArea getChatSession() {
        return chatSession;
    }

    public boolean isGoal() {
        return goal;
    }

    public JPopupMenu getjPopupMenu() {
        return jPopupMenu;
    }

    public EnvironmentDatabase getEnvironmentDatabase() {
        return environmentDatabase;
    }
}