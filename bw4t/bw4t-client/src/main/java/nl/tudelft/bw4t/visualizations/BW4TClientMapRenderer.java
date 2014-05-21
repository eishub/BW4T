package nl.tudelft.bw4t.visualizations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import nl.tudelft.bw4t.client.startup.Launcher;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.map.Constants;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageTranslator;
import nl.tudelft.bw4t.message.MessageType;
import nl.tudelft.bw4t.visualizations.data.DoorInfo;
import nl.tudelft.bw4t.visualizations.data.DropZoneInfo;
import nl.tudelft.bw4t.visualizations.data.RoomInfo;
import nl.tudelft.bw4t.visualizations.listeners.ChatListMouseListener;
import nl.tudelft.bw4t.visualizations.listeners.GoToBlockActionListener;
import nl.tudelft.bw4t.visualizations.listeners.GoToRoomActionListener;
import nl.tudelft.bw4t.visualizations.listeners.GotoPositionActionListener;
import nl.tudelft.bw4t.visualizations.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.visualizations.listeners.PickUpActionListener;
import nl.tudelft.bw4t.visualizations.listeners.PutdownActionListener;
import nl.tudelft.bw4t.visualizations.listeners.TeamListMouseListener;
import eis.exceptions.AgentException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
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
 * 
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

	private final String DROPZONE_NAME = "DropZone";
	/**
	 * Data needed for updating the graphical representation of the world
	 */
	private ArrayList<RoomInfo> rooms;
	private ArrayList<String> occupiedRooms;
	private DropZoneInfo dropZone;
	private Double[] entityLocation = new Double[] { 0., 0., 0., 0. };
	private HashMap<Long, Point2D.Double> objectPositions = new HashMap<Long, Point2D.Double>();
	private ArrayList<String> otherPlayers;
	
	/**
     * The log4j Logger which displays logs on console
     */
    private static Logger logger = Logger.getLogger(BW4TClientMapRenderer.class);

	private ArrayList<Long> visibleBlocks = new ArrayList<Long>();
	private HashMap<Long, BlockColor> allBlocks = new HashMap<Long, BlockColor>();
	private HashMap<String, Point> roomLabels = new HashMap<String, Point>();
	private Color entityColor = Color.BLACK;
	private long holdingID = Long.MAX_VALUE;
	private ArrayList<BlockColor> sequence;
	private int sequenceIndex;
	private String entityId;
	private boolean stop;
	private static final int roomTextOffset = 25;

	private ArrayList<ArrayList<String>> chatHistory;

	/**
	 * Used for transforming repast coordinates to our own coordinate system
	 */
	private int worldX, worldY, scale = 7;

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
	private LinkedList<Percept> toBePerformedAction;

	private boolean humanPlayer;

	/**
	 * Most of the server interfacing goes through the std eis percepts
	 */
	private BW4TRemoteEnvironment environment;

	/**
	 * @param env
	 *            the BW4TRemoteEnvironment that we are rendering
	 * 
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
		this.setGoal(goal);
		this.humanPlayer = humanPlayer;
		setToBePerformedAction(new LinkedList<Percept>());
	}

	/**
	 * 
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
	 * 
	 * @param entityId
	 *            , the id of the entity that needs to be displayed
	 * @param humanPlayer
	 *            , whether a human is supposed to control this panel
	 * @throws IOException
	 */
	private void init(final String entityId, boolean humanPlayer) throws IOException {
		// Initialize variables
	    logger.debug("Initializing agent window for entity: " + entityId);
	    
		rooms = new ArrayList<RoomInfo>();
		sequence = new ArrayList<BlockColor>();
		chatHistory = new ArrayList<ArrayList<String>>();
		occupiedRooms = new ArrayList<String>();
		objectPositions = new HashMap<Long, Point2D.Double>();
		otherPlayers = new ArrayList<String>();

		this.setEntityId(entityId);

		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.BLACK);
		JButton jButton = new JButton("all");
		buttonPanel.add(jButton);
		jButton.addMouseListener(new TeamListMouseListener(this));

		RendererMapLoader.loadMap(environment.getMap(), this);

		// Initialize graphics
		try {
            UIManager.setLookAndFeel(
              UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
              e.printStackTrace();
          }
		jFrame = new JFrame(entityId);
		jFrame.setSize(worldX * scale + 10, worldY * scale + 250);
        jFrame.setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
        jFrame.setLocation(BW4TClientSettings.getX(), BW4TClientSettings.getY());
        jFrame.setResizable(true);
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();
                logger.info("Exit request received from the Window Manager to close Window of entity: " + entityId);
                    try {
                        environment.kill();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
//                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
			setjPopupMenu(new JPopupMenu());
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
		if (!playerId.equals(getEntityId())) {
			JButton button = new JButton(playerId);
			button.addMouseListener(new TeamListMouseListener(this));
			buttonPanel.add(button);
		}
	}

	/**
	 * 
	 * @param worldX
	 *            , the x dimension of the world as specified in the map file
	 * @param worldY
	 *            , the y dimension of the world as specified in the map file
	 */
	public void setWorldDimensions(int worldX, int worldY) {
		this.worldX = worldX;
		this.worldY = worldY;
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
		DoorInfo info = new DoorInfo(x, y, width, height);
		for (RoomInfo room : rooms) {
			if (room.getName().equals(roomname)) {
				room.addDoor(info);
				return;
			}
		}

		if (roomname.equals("DropZone")) {
			dropZone.addDoor(info);
		}
	}

	/**
	 * 
	 * @param room
	 *            , add a representation of a room (Integer[4] with x, y, width
	 *            and height)
	 */
	public void addRoom(double x, double y, double width, double height,
			String name) {
		rooms.add(new RoomInfo(x, y, width, height, name));
	}

	/**
	 * 
	 * @param dropZone
	 *            , add a representation of the dropzone (Integer[4] with x, y,
	 *            width and height)
	 */
	public void addDropZone(Integer[] dropZone) {
		this.dropZone = new DropZoneInfo(dropZone[0], dropZone[1], dropZone[2],
				dropZone[3]);
	}

	/**
	 * Processes all objects to display them on the panel
	 * 
	 * @param g
	 *            , the graphics object
	 * 
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
		for (RoomInfo room : rooms) {
			// first paint the doors. Matches the {@link ServerMapRenderer}
			if (occupiedRooms.contains(findLabelForRoom(room))) {
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
		for (String label : roomLabels.keySet()) {
			g2d.drawString(label, roomLabels.get(label).x * scale
					- roomTextOffset, roomLabels.get(label).y * scale);
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
		g2d.fill(transformRectangle(new Rectangle2D.Double(dropZone.getX(),
				dropZone.getY(), dropZone.getWidth(), dropZone.getHeight())));
		g2d.setColor(Color.BLACK);
		g2d.draw(transformRectangle(new Rectangle2D.Double(dropZone.getX(),
				dropZone.getY(), dropZone.getWidth(), dropZone.getHeight())));

		if (occupiedRooms.contains(DROPZONE_NAME)) {
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
		for (Long box : allBlocks.keySet()) {
			if (visibleBlocks.contains(box)) {
				if (objectPositions.get(box) != null) {
					g2d.setColor(allBlocks.get(box).getColor());
					g2d.fill(transformRectangle(new Rectangle2D.Double(
							objectPositions.get(box).getX(), objectPositions
									.get(box).getY(), Constants.BLOCK_SIZE, Constants.BLOCK_SIZE)));
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
		g2d.setColor(entityColor);

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
		for (BlockColor color : sequence) {
			g2d.setColor(color.getColor());
			g2d.fill(new Rectangle2D.Double(startPosX, worldY * scale, 20, 20));
			if (sequenceIndex > (startPosX / 20)) {
				g2d.setColor(Color.BLACK);
				int[] xpoints = new int[] { startPosX, startPosX,
						startPosX + 20 };
				int[] ypoints = new int[] { worldY * scale,
						worldY * scale + 20, worldY * scale + 10 };
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
		int width = (int) (boundingBox.getWidth() * scale);
		int height = (int) (boundingBox.getHeight() * scale);
		int xPos = (int) (boundingBox.getX() * scale) - (width / 2);
		int yPos = (int) (boundingBox.getY() * scale) - (height / 2);

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
					percepts = environment.getAllPerceptsFromEntity(getEntityId()
							+ "gui");
					if (percepts != null) {
						processPercepts(percepts);
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

	/**
	 * Processes the percepts list from the perceptPoller and updates the
	 * following data: - Visible blocks - Holding a block - Location of the
	 * entity - Group goal sequence - Room occupation
	 * 
	 * @param percepts
	 *            , a list of all received percepts
	 */
	public void processPercepts(LinkedList<Percept> percepts) {

		// first process the not percepts.
		for (Percept percept : percepts) {
			String name = percept.getName();
			if (name.equals("not")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				Function function = ((Function) parameters.get(0));
				if (function.getName().equals("occupied")) {
					LinkedList<Parameter> paramOcc = function.getParameters();
					String id = ((Identifier) paramOcc.get(0)).getValue();
					occupiedRooms.remove(id);
				} else if (function.getName().equals("holding")) {
					holdingID = Long.MAX_VALUE;
					entityColor = Color.BLACK;
				}
			}
		}

		// reset the ALWAYS percepts
		visibleBlocks = new ArrayList<Long>();

		// First create updated information based on the new percepts.
		for (Percept percept : percepts) {
			String name = percept.getName();

			// Initialize room ids in all rooms gotten from the map loader
			// Should only be done one time
			if (name.equals("position")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				long id = ((Numeral) parameters.get(0)).getValue().longValue();
				double x = ((Numeral) parameters.get(1)).getValue()
						.doubleValue();
				double y = ((Numeral) parameters.get(2)).getValue()
						.doubleValue();
				for (RoomInfo room : rooms) {
					if (room.getX() == (int) x && room.getY() == (int) y) {
						room.setId(id);
						break;
					}
				}

				// Also update drop zone id
				if (dropZone.getX() == x && dropZone.getY() == y) {
					dropZone.setId(id);
				}

				// Else it is a block, add it to all object positions
				objectPositions.put(id, new Point2D.Double(x, y));
			}

			else if (name.equals("color")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				long id = ((Numeral) parameters.get(0)).getValue().longValue();
				char color = ((Identifier) parameters.get(1)).getValue()
						.charAt(0);
				visibleBlocks.add(id);
				if (!allBlocks.containsKey(id)) {
					allBlocks.put(id, BlockColor.toAvailableColor(color));
				}
			}

			// Prepare updated occupied rooms list
			else if (name.equals("occupied")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				String id = ((Identifier) parameters.get(0)).getValue();
				occupiedRooms.add(id);
			}

			else if (name.equals("not")) {
				// already processed, skip.
			}

			else if (name.equals("sequenceIndex")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				int index = ((Numeral) parameters.get(0)).getValue().intValue();
				sequenceIndex = index;
			}

			// Location can be updated immediately.
			else if (name.equals("location")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				double x = ((Numeral) parameters.get(0)).getValue()
						.doubleValue();
				double y = ((Numeral) parameters.get(1)).getValue()
						.doubleValue();
				entityLocation = new Double[] { x, y };
			}

			// Check if holding a block
			else if (name.equals("holding")) {
				holdingID = ((Numeral) percept.getParameters().get(0))
						.getValue().longValue();
				entityColor = allBlocks.get(holdingID).getColor();
			}

			else if (name.equals("player")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				String player = ((Identifier) parameters.get(0)).getValue();
				if (!otherPlayers.contains(player)) {
					otherPlayers.add(player);
				}
			}

			// Update group goal sequence
			else if (name.equals("sequence")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				for (Parameter i : parameters) {
					ParameterList list = (ParameterList) i;
					for (Parameter j : list) {
						char letter = (((Identifier) j).getValue().charAt(0));
						sequence.add(BlockColor.toAvailableColor(letter));
					}
				}
			}

			// Update chat history
			else if (name.equals("message")) {
				LinkedList<Parameter> parameters = percept.getParameters();

				ParameterList parameterList = ((ParameterList) parameters
						.get(0));

				Iterator<Parameter> iterator = parameterList.iterator();

				String sender = ((Identifier) iterator.next()).getValue();
				String message = ((Identifier) iterator.next()).getValue();

				getChatSession().append(sender + " : " + message + "\n");
				getChatSession().setCaretPosition(getChatSession().getDocument()
						.getLength());

				ArrayList<String> newMessage = new ArrayList<String>();
				newMessage.add(sender);
				newMessage.add(message);
				chatHistory.add(newMessage);
			}

		}

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
		for (BlockColor color : sequence) {
			Shape colorBounds = new Rectangle2D.Double(startPosX, worldY
					* scale, 20, 20);
			if (colorBounds.contains(new Point(selectedLocation[0],
					selectedLocation[1]))) {
				buildPopUpMenuForGoalColor(color);
				getjPopupMenu().show(this, selectedLocation[0], selectedLocation[1]);
				return;
			}
			startPosX += 20;
		}

		// Check if pressing on a room
		for (RoomInfo room : rooms) {
			Shape roomBoundaries = transformRectangle(new Rectangle2D.Double(
					room.getX(), room.getY(), room.getWidth(), room.getHeight()));
			if (roomBoundaries.contains(new Point(selectedLocation[0],
					selectedLocation[1]))) {
				// Check if pressing on a block
				for (Long boxID : visibleBlocks) {
					Shape boxBoundaries = transformRectangle(new Rectangle2D.Double(
							objectPositions.get(boxID).getX(), objectPositions
									.get(boxID).getY(), Constants.BLOCK_SIZE, Constants.BLOCK_SIZE));
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
				getjPopupMenu().show(this, selectedLocation[0], selectedLocation[1]);
				return;
			}
		}

		// Check for dropzone
		Shape dropZoneBoundaries = transformRectangle(new Rectangle2D.Double(
				dropZone.getX(), dropZone.getY(), dropZone.getWidth(),
				dropZone.getHeight()));
		if (dropZoneBoundaries.contains(new Point(selectedLocation[0],
				selectedLocation[1]))) {
			buildPopUpMenuRoom(dropZone);
			getjPopupMenu().show(this, selectedLocation[0], selectedLocation[1]);
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

		// Robot commands
		addSectionTitleToPopupMenu("Command my robot to: ");

		JMenuItem menuItem = new JMenuItem("Go to here");
		menuItem.addActionListener(new GotoPositionActionListener(new Point(
				selectedLocation[0] / scale, selectedLocation[1] / scale),this));
		getjPopupMenu().add(menuItem);

		if (holdingID != Long.MAX_VALUE) {
			menuItem = new JMenuItem("Put down box");
			menuItem.addActionListener(new PutdownActionListener(this));
			getjPopupMenu().add(menuItem);
		}

		getjPopupMenu().addSeparator();

		addSectionTitleToPopupMenu("Tell: ");

		for (RoomInfo room : rooms) {
			addMenuItemToPopupMenu(new BW4TMessage(
					MessageType.amWaitingOutsideRoom, findLabelForRoom(room),
					null, null));
		}

		addMenuItemToPopupMenu(new BW4TMessage(
				MessageType.amWaitingOutsideRoom, findLabelForRoom(dropZone),
				null, null));

		if (holdingID != Long.MAX_VALUE) {
			addMenuItemToPopupMenu(new BW4TMessage(MessageType.hasColor, null,
					ColorTranslator.translate2ColorString(entityColor), null));

			JMenu submenu = addSubMenuToPopupMenu("I have a "
					+ ColorTranslator.translate2ColorString(entityColor)
					+ " block from ");

			for (RoomInfo roomInfo : rooms) {
				String label = findLabelForRoom(roomInfo);
				menuItem = new JMenuItem(Long.toString(roomInfo.getId()));
				menuItem.addActionListener(new MessageSenderActionListener(
						new BW4TMessage(MessageType.hasColor, label,
								ColorTranslator
										.translate2ColorString(entityColor),
								null),this));
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

		addSectionTitleToPopupMenu("Answer:");

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
		getjPopupMenu().removeAll();

		JMenuItem menuItem = new JMenuItem(color.getName());
		getjPopupMenu().add(menuItem);
		getjPopupMenu().addSeparator();

		addSectionTitleToPopupMenu("Command my robot to:");

		if (holdingID != Long.MAX_VALUE) {
			menuItem = new JMenuItem("Put down block");
			menuItem.addActionListener(new PutdownActionListener(this));
			getjPopupMenu().add(menuItem);
		}

		getjPopupMenu().addSeparator();

		addSectionTitleToPopupMenu("Tell: ");

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

			for (RoomInfo room : rooms) {
				String label = findLabelForRoom(room);
				menuItem = new JMenuItem(Long.toString(room.getId()));
				menuItem.addActionListener(new MessageSenderActionListener(
						new BW4TMessage(MessageType.hasColorFromRoom, label,
								color.getName(), null),this));
				submenu.add(menuItem);
			}
		}

		getjPopupMenu().addSeparator();
		addSectionTitleToPopupMenu("Ask: ");

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
		addSectionTitleToPopupMenu("Command my robot to:");

		JMenuItem menuItem = new JMenuItem("Pick up " + allBlocks.get(boxID)
				+ " block");
		menuItem.addActionListener(new PickUpActionListener(this));
		getjPopupMenu().add(menuItem);

		// Message sending
		getjPopupMenu().addSeparator();
		addSectionTitleToPopupMenu("Tell: ");
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
		addSectionTitleToPopupMenu("Command my robot to:");

		JMenuItem menuItem = new JMenuItem("Go to " + allBlocks.get(boxID)
				+ " block");
		menuItem.addActionListener(new GoToBlockActionListener(boxID,this));
		getjPopupMenu().add(menuItem);

		// Message sending
		getjPopupMenu().addSeparator();
		addSectionTitleToPopupMenu("Tell: ");
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
		String label = findLabelForRoom(room);
		getjPopupMenu().removeAll();

		// Robot commands
		addSectionTitleToPopupMenu("Command my robot to: ");

		JMenuItem menuItem = new JMenuItem("Go to " + label);
		menuItem.addActionListener(new GoToRoomActionListener(label,this));
		getjPopupMenu().add(menuItem);

		if (holdingID != Long.MAX_VALUE) {
			menuItem = new JMenuItem("Put down box");
			menuItem.addActionListener(new PutdownActionListener(this));
			getjPopupMenu().add(menuItem);
		}

		getjPopupMenu().addSeparator();

		// Message sending
		addSectionTitleToPopupMenu("Tell: ");

		addMenuItemToPopupMenu(new BW4TMessage(MessageType.inRoom, label, null,
				null));

		JMenu submenu = addSubMenuToPopupMenu(label + " contains ");

		for (String color : ColorTranslator.getAllColors()) {
			menuItem = new JMenuItem(color);
			menuItem.addActionListener(new MessageSenderActionListener(
					new BW4TMessage(MessageType.roomContains, label, color,
							null),this));
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
								color, i),this));
				submenuColor.add(menuItem);
			}
		}

		addMenuItemToPopupMenu(new BW4TMessage(MessageType.checked, label,
				null, null));

		submenu = addSubMenuToPopupMenu(label + " has been checked by ");

		for (int i = 0; i < otherPlayers.size(); i++) {
			menuItem = new JMenuItem("" + otherPlayers.get(i));
			menuItem.addActionListener(new MessageSenderActionListener(
					new BW4TMessage(MessageType.checked, label, null,
							otherPlayers.get(i)),this));
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

			for (RoomInfo roomInfo : rooms) {
				String labelT = findLabelForRoom(roomInfo);
				menuItem = new JMenuItem(labelT);
				menuItem.addActionListener(new MessageSenderActionListener(
						new BW4TMessage(MessageType.hasColor, labelT,
								ColorTranslator
										.translate2ColorString(entityColor),
								null),this));
				submenu.add(menuItem);
			}
		}

		addSectionTitleToPopupMenu("Ask:");
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
		addSectionTitleToPopupMenu("Request:");

		// Check if the playerId is a specific player
		String receiver = "Somebody";
		if (!playerId.equalsIgnoreCase("all"))
			receiver = playerId;

		addMenuItemToPopupMenu(new BW4TMessage(MessageType.putDown, null, null,
				receiver));

		JMenu submenu = addSubMenuToPopupMenu(receiver + " go to room");

		for (RoomInfo room : rooms) {
			String label = findLabelForRoom(room);
			JMenuItem menuItem = new JMenuItem(label);
			menuItem.addActionListener(new MessageSenderActionListener(
					new BW4TMessage(MessageType.goToRoom, label, null, receiver),this));
			submenu.add(menuItem);
		}

		submenu = addSubMenuToPopupMenu(receiver + " find a color");

		for (String color : ColorTranslator.getAllColors()) {
			JMenuItem menuItem = new JMenuItem(color);
			menuItem.addActionListener(new MessageSenderActionListener(
					new BW4TMessage(MessageType.findColor, null, color,
							receiver),this));
			submenu.add(menuItem);
		}

		submenu = addSubMenuToPopupMenu(receiver + " get the color from room");

		for (String color : ColorTranslator.getAllColors()) {
			JMenu submenu2 = new JMenu(color);
			submenu.add(submenu2);

			for (RoomInfo room : rooms) {
				String label = findLabelForRoom(room);
				JMenuItem menuItem = new JMenuItem(label);
				menuItem.addActionListener(new MessageSenderActionListener(
						new BW4TMessage(MessageType.getColorFromRoom, label,
								color, receiver),this));
				submenu2.add(menuItem);
			}
		}

		addSectionTitleToPopupMenu("Ask:");
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
	 * Adds a section title to the pop up menu
	 * 
	 * @param title
	 *            , the title of the section
	 */
	public void addSectionTitleToPopupMenu(String title) {
		JMenuItem menuItem = new JMenuItem(title);
		menuItem.setEnabled(false);
		getjPopupMenu().add(menuItem);
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
		menuItem.addActionListener(new MessageSenderActionListener(message,this));
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
		double minX = objectPositions.get(boxID).getX() - 0.5;
		double maxX = objectPositions.get(boxID).getX() + 0.5;
		double minY = objectPositions.get(boxID).getY() - 0.5;
		double maxY = objectPositions.get(boxID).getY() + 0.5;
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
		LinkedList<Percept> toBePerformedActionClone = (LinkedList<Percept>) toBePerformedAction
				.clone();
		toBePerformedAction = new LinkedList<Percept>();
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
		getChatSession().setCaretPosition(getChatSession().getDocument().getLength());

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
		roomLabels.put(label, point);
	}

	/**
	 * Helper method to find the nav point label corresponding to a certain room
	 * 
	 * @param room
	 *            , the room for which to find the navpoint label
	 * @return the label
	 */
	private String findLabelForRoom(RoomInfo room) {
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

    public void setToBePerformedAction(LinkedList<Percept> toBePerformedAction) {
        this.toBePerformedAction = toBePerformedAction;
    }

    public JTextArea getChatSession() {
        return chatSession;
    }

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public JPopupMenu getjPopupMenu() {
        return jPopupMenu;
    }

    public void setjPopupMenu(JPopupMenu jPopupMenu) {
        this.jPopupMenu = jPopupMenu;
    }
}