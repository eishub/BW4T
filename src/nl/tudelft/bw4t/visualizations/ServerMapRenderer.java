package nl.tudelft.bw4t.visualizations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.doors.Door;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.robots.Robot;
import nl.tudelft.bw4t.server.BW4TEnvironment;
import nl.tudelft.bw4t.zone.Corridor;
import nl.tudelft.bw4t.zone.DropZone;
import nl.tudelft.bw4t.zone.Room;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.visualizationOGL2D.StyleOGL2D;
import eis.iilang.EnvironmentState;

/**
 * Used for directly displaying the simulation from the context, unlike
 * {@link BW4TClientMapRenderer} does not use percepts and can show all
 * entities. Only used on the server side (BW4TEnvironment side).
 * <p>
 * Note, this renderer is largely independent of repast, so even though Repast
 * has its own rendering tools we don't use that.
 * <p>
 * Also note that this is a runnable and runs in its own thread with a refresh
 * rate of 10Hz.
 * 
 * @author trens
 * 
 */
public class ServerMapRenderer extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3897473512101235101L;
	private Context context;
	private HashMap<Class<?>, StyleOGL2D<?>> styles;
	private int scale = 7;
	private int frameWidth, frameHeight;
	private boolean haveRequestedFocusAlready = false;
	private boolean running;
	private Dimension size; // size of the paint area
	private static final int roomTextOffset = 25;

	/**
	 * Create a new instance of this class and initialize it
	 * 
	 * @param context
	 *            , the central data model
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public ServerMapRenderer(Context context) throws InstantiationException,
			IllegalAccessException {
		this.context = context;

		computeSize(context);

		registerStyles();

		new Thread(this).start();
	}

	/**
	 * compute required size for the map
	 * 
	 * @param context2
	 * @return
	 */
	private void computeSize(Context context2) {
		int width = (int) ((ContinuousSpace) context
				.getProjection("BW4T_Projection")).getDimensions().getWidth();
		frameWidth = (int) (scale * width);
		int height = (int) ((ContinuousSpace) context
				.getProjection("BW4T_Projection")).getDimensions().getHeight();
		frameHeight = (int) (scale * height);
		size = new Dimension(frameWidth + 10, frameHeight + 60);
		setPreferredSize(size);
	}

	/**
	 * Update the display by processing all different types of objects in turn
	 * 
	 * @param g
	 *            , the Graphics object
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		processDoors(g2d);
		processRooms(g2d);
		processCorridor(g2d);
		processBlocks(g2d);
		processGroupGoal(g2d);
		processRobots(g2d);
	}

	/**
	 * Process and draw all navPoint connections on the display with red lines
	 * 
	 * @param g2d
	 *            , the Graphics2D object
	 */
	private void processCorridor(Graphics2D g2d) {
		for (Object o : context.getObjects(Corridor.class)) {
			Corridor corr = (Corridor) o;

			g2d.setColor(Color.DARK_GRAY);
			g2d.setFont(new Font("Arial", Font.PLAIN, 10));
			if (corr.isLabelVisible()) {
				g2d.drawString(corr.getName(), (int) corr.getBoundingBox()
						.getBounds().getCenterX()
						* scale - roomTextOffset, (int) corr.getBoundingBox()
						.getBounds().getCenterY()
						* scale);
			}
		}
	}

	/**
	 * Process and draw the group goal (incl: progress) on the display
	 * 
	 * @param g2d
	 *            , the Graphics2D object
	 */
	private void processGroupGoal(Graphics2D g2d) {
		for (Object dropZone : context.getObjects(DropZone.class)) {
			DropZone dropZoneTemp = (DropZone) dropZone;
			List<BlockColor> sequence = dropZoneTemp.getSequence();
			int sequenceIndex = dropZoneTemp.getSequenceIndex();
			int startPosX = 0;
			int worldY = (int) ((ContinuousSpace) context
					.getProjection("BW4T_Projection")).getDimensions()
					.getHeight();
			for (BlockColor color : sequence) {
				g2d.setColor(color.getColor());
				g2d.fill(new Rectangle2D.Double(startPosX, worldY * scale, 20,
						20));
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

	}

	/**
	 * Process and draw all blocks present in the environment
	 * 
	 * @param g2d
	 *            , the Graphics2D object
	 */
	private void processBlocks(Graphics2D g2d) {
		for (Object block : context.getObjects(Block.class)) {
			Block blockTemp = (Block) block;
			g2d.setColor(((StyleOGL2D) styles.get(Block.class)).getColor(block));
			g2d.fill(transformRectangle(blockTemp.getBoundingBox()));
		}
	}

	/**
	 * Process and display all robots present in the environment
	 * 
	 * @param g2d
	 *            , the Graphics2D object
	 */
	private void processRobots(Graphics2D g2d) {
		for (Object robot : context.getObjects(Robot.class)) {
			Robot robotTemp = (Robot) robot;
			if (robotTemp.isConnected()) {
				g2d.setColor(((StyleOGL2D) styles.get(Robot.class))
						.getColor(robot));
				g2d.fill(transformRectangle(robotTemp.getBoundingBox()));

				g2d.setColor(Color.RED);
				g2d.setFont(new Font("Arial", Font.BOLD, 16));
				g2d.drawString(robotTemp.getName(), (int) robotTemp
						.getBoundingBox().getCenterX() * scale - 15,
						(int) robotTemp.getBoundingBox().getCenterY() * scale
								+ 20);
			}
		}
	}

	/**
	 * Process and display all rooms present in the environment
	 * 
	 * @param g2d
	 *            , the Graphics2D object
	 */
	private void processRooms(Graphics2D g2d) {
		for (Object roomObj : context.getObjects(Room.class)) {
			Room room = (Room) roomObj;
			Shape displayCoordinates = transformRectangle(room
					.getBoundingBox());
			g2d.setColor(Color.GRAY);
			g2d.fill(displayCoordinates);
			g2d.setColor(Color.BLACK);
			g2d.draw(displayCoordinates);

			g2d.setColor(Color.DARK_GRAY);
			g2d.setFont(new Font("Arial", Font.PLAIN, 10));
			if (room.isLabelVisible()) {
				g2d.drawString(room.getName(), (int) room
						.getBoundingBox().getBounds().getCenterX()
						* scale - roomTextOffset, (int) room
						.getBoundingBox().getBounds().getCenterY()
						* scale);
			}
		}
	}

	/**
	 * Process and display all doors present in the environment
	 * 
	 * @param g
	 *            , the Graphics2D object
	 */
	private void processDoors(Graphics2D g2d) {
		for (Object door : context.getObjects(Door.class)) {
			Door doorTemp = (Door) door;
			g2d.setColor(((StyleOGL2D) styles.get(Door.class)).getColor(door));
			g2d.fill(transformRectangle(doorTemp.getBoundingBox()));
		}
	}

	/**
	 * Create a mapping of classes that should be displayed and the class
	 * containing the style that should be used for displaying
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private void registerStyles() throws InstantiationException,
			IllegalAccessException {
		styles = new HashMap<Class<?>, StyleOGL2D<?>>();
		styles.put(Block.class, BlockStyle.class.newInstance());
		styles.put(Door.class, DoorStyle.class.newInstance());
		styles.put(Room.class, RoomStyle.class.newInstance());
		styles.put(Robot.class, RobotStyle.class.newInstance());
		styles.put(DropZone.class, DropZoneStyle.class.newInstance());

	}

	/**
	 * Transform a rectangle from world coordinates to display coordinates
	 * 
	 * @param boundingBox
	 *            , the rectangle to be transformed
	 * @return the transformed rectangle
	 */
	private Shape transformRectangle(Rectangle2D boundingBox) {
		int width = (int) (boundingBox.getWidth() * scale);
		int height = (int) (boundingBox.getHeight() * scale);
		int xPos = (int) (boundingBox.getX() * scale);
		int yPos = (int) (boundingBox.getY() * scale);

		return new Rectangle(xPos, yPos, width, height);
	}

	/**
	 * Should be ran in separate thread, will repaint the display at each tick
	 */
	@Override
	public void run() {
		running = true;
		while (running) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (BW4TEnvironment.getInstance().getState()
							.equals(EnvironmentState.RUNNING)
							&& !haveRequestedFocusAlready) {
						requestFocus();
						haveRequestedFocusAlready = true;
					}
					validate();
					repaint();
				}
			});

			try {
				// Sleep for a short while
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		running = false;
	}

}
