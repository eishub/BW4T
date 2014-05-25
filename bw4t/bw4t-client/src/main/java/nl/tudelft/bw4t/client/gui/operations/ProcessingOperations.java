package nl.tudelft.bw4t.client.gui.operations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.VisualizerSettings;
import nl.tudelft.bw4t.client.gui.data.structures.DoorInfo;
import nl.tudelft.bw4t.client.gui.data.structures.DropZoneInfo;
import nl.tudelft.bw4t.client.gui.data.structures.RoomInfo;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.view.Block;
import nl.tudelft.bw4t.map.view.Entity;

import org.apache.log4j.Logger;

import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;

public class ProcessingOperations {
	/**
	 * The log4j Logger which displays logs on console
	 */
	private final static Logger LOGGER = Logger.getLogger(ProcessingOperations.class);

	/**
	 * Display the goal sequence
	 * 
	 * @param g2d
	 *            , the graphics2d object
	 */
	public static void processSequence(Graphics2D g2d, BW4TClientGUI data) {
		int startPosX = 0;
		for (BlockColor color : data.getEnvironmentDatabase().getSequence()) {
			g2d.setColor(color.getColor());
			g2d.fill(new Rectangle2D.Double(startPosX, VisualizerSettings.worldY * VisualizerSettings.scale, 20, 20));
			if (data.getEnvironmentDatabase().getSequenceIndex() > (startPosX / 20)) {
				g2d.setColor(Color.BLACK);
				int[] xpoints = new int[] { startPosX, startPosX, startPosX + 20 };
				int[] ypoints = new int[] { VisualizerSettings.worldY * VisualizerSettings.scale,
						(VisualizerSettings.worldY * VisualizerSettings.scale) + 20,
						(VisualizerSettings.worldY * VisualizerSettings.scale) + 10 };
				g2d.fillPolygon(xpoints, ypoints, 3);
			}
			startPosX += 20;
		}
	}

	/**
	 * Process all rooms and their connected doors, and display them in the panel with an outline
	 * 
	 * @param g2d
	 *            , the graphics2d object
	 */
	public static void processRooms(Graphics2D g2d, BW4TClientGUI data) {
		for (RoomInfo room : data.getEnvironmentDatabase().getRooms()) {
			// first paint the doors. Matches the {@link ServerMapRenderer}
			if (data.getEnvironmentDatabase().getOccupiedRooms().contains(MapOperations.findLabelForRoom(room, data))) {
				g2d.setColor(Color.RED);
			}
			else {
				g2d.setColor(Color.GREEN);
			}

			for (DoorInfo door : room.getDoors()) {
				g2d.fill(MapOperations.transformRectangle(new Rectangle2D.Double(door.getX(), door.getY(), door
						.getWidth(), door.getHeight())));
			}

			// paint the room
			g2d.setColor(Color.GRAY);
			Shape roomDisplayCoordinates = MapOperations.transformRectangle(new Rectangle2D.Double(room.getX(), room
					.getY(), room.getWidth(), room.getHeight()));
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
	public static void processLabels(Graphics2D g2d, BW4TClientGUI data) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.setFont(new Font("Arial", Font.PLAIN, 10));
		HashMap<String, Point> roomLabels = data.getEnvironmentDatabase().getRoomLabels();
		for (String label : roomLabels.keySet()) {
			g2d.drawString(label, (roomLabels.get(label).x * VisualizerSettings.scale)
					- VisualizerSettings.roomTextOffset, roomLabels.get(label).y * VisualizerSettings.scale);
		}
	}

	/**
	 * Process the drop zone and connected doors and display them in the panel
	 * 
	 * @param g2d
	 *            , the graphics2d object
	 */
	public static void processDropZone(Graphics2D g2d, BW4TClientGUI data) {
		g2d.setColor(Color.DARK_GRAY);
		DropZoneInfo dropZone = data.getEnvironmentDatabase().getDropZone();
		g2d.fill(MapOperations.transformRectangle(new Rectangle2D.Double(dropZone.getX(), dropZone.getY(), dropZone
				.getWidth(), dropZone.getHeight())));
		g2d.setColor(Color.BLACK);
		g2d.draw(MapOperations.transformRectangle(new Rectangle2D.Double(dropZone.getX(), dropZone.getY(), dropZone
				.getWidth(), dropZone.getHeight())));

		if (data.getEnvironmentDatabase().getOccupiedRooms().contains(VisualizerSettings.DROPZONE_NAME)) {
			g2d.setColor(Color.RED);
		}
		else {
			g2d.setColor(Color.GREEN);
		}

		for (DoorInfo door : dropZone.getDoors()) {
			g2d.fill(MapOperations.transformRectangle(new Rectangle2D.Double(door.getX(), door.getY(), door.getWidth(),
					door.getHeight())));
		}
	}

	/**
	 * Process all blocks that are visible and display them with their color
	 * 
	 * @param g2d
	 *            , the graphics2d object
	 */
	public static void processBlocks(Graphics2D g2d, BW4TClientGUI data) {
		HashMap<Long, BlockColor> allBlocks = data.getEnvironmentDatabase().getAllBlocks();
		for (Long box : data.getEnvironmentDatabase().getVisibleBlocks()) {
			HashMap<Long, java.awt.geom.Point2D.Double> objectPositions = data.getEnvironmentDatabase()
					.getObjectPositions();
			if (objectPositions.get(box) != null) {
				g2d.setColor(allBlocks.get(box).getColor());
				g2d.fill(MapOperations.transformRectangle(new Rectangle2D.Double(objectPositions.get(box).getX(),
						objectPositions.get(box).getY(), Block.BLOCK_SIZE, Block.BLOCK_SIZE)));
			}
		}
	}

	/**
	 * Display the robot this panel represents. The color is adapted depending on whether it holds a block or not.
	 * 
	 * @param g2d
	 *            , the graphics2d object
	 */
	public static void processEntity(Graphics2D g2d, BW4TClientGUI data) {
		g2d.setColor(data.getEnvironmentDatabase().getEntityColor());
		Double[] entityLocation = data.getEnvironmentDatabase().getEntityLocation();
		g2d.fill(MapOperations.transformRectangle(new Rectangle2D.Double(entityLocation[0], entityLocation[1],
				Entity.ROBOT_SIZE, Entity.ROBOT_SIZE)));
	}

	/**
	 * Processes the percepts list from the perceptPoller and updates the following data: - Visible blocks - Holding a
	 * block - Location of the entity - Group goal sequence - Room occupation
	 * 
	 * @param percepts
	 *            , a list of all received percepts
	 */
	public static void processPercepts(List<Percept> percepts, BW4TClientGUI data) {

		// first process the not percepts.
		for (Percept percept : percepts) {
			String name = percept.getName();
			if (name.equals("not")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				Function function = ((Function) parameters.get(0));
				if (function.getName().equals("occupied")) {
					LinkedList<Parameter> paramOcc = function.getParameters();
					String id = ((Identifier) paramOcc.get(0)).getValue();
					data.getEnvironmentDatabase().getOccupiedRooms().remove(id);
				}
				else if (function.getName().equals("holding")) {
					data.getEnvironmentDatabase().setHoldingID(Long.MAX_VALUE);
					data.getEnvironmentDatabase().setEntityColor(Color.BLACK);
				}
			}
		}

		// reset the ALWAYS percepts
		data.getEnvironmentDatabase().setVisibleBlocks(new ArrayList<Long>());

		// First create updated information based on the new percepts.
		for (Percept percept : percepts) {
			String name = percept.getName();

			// Initialize room ids in all rooms gotten from the map loader
			// Should only be done one time
			HashMap<Long, BlockColor> allBlocks = data.getEnvironmentDatabase().getAllBlocks();
			if (name.equals("position")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				long id = ((Numeral) parameters.get(0)).getValue().longValue();
				double x = ((Numeral) parameters.get(1)).getValue().doubleValue();
				double y = ((Numeral) parameters.get(2)).getValue().doubleValue();
				for (RoomInfo room : data.getEnvironmentDatabase().getRooms()) {
					if ((room.getX() == (int) x) && (room.getY() == (int) y)) {
						room.setId(id);
						break;
					}
				}

				// Also update drop zone id
				if ((data.getEnvironmentDatabase().getDropZone().getX() == x)
						&& (data.getEnvironmentDatabase().getDropZone().getY() == y)) {
					data.getEnvironmentDatabase().getDropZone().setId(id);
				}

				// Else it is a block, add it to all object positions
				data.getEnvironmentDatabase().getObjectPositions().put(id, new Point2D.Double(x, y));
			}

			else if (name.equals("color")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				long id = ((Numeral) parameters.get(0)).getValue().longValue();
				char color = ((Identifier) parameters.get(1)).getValue().charAt(0);
				data.getEnvironmentDatabase().getVisibleBlocks().add(id);
				if (!allBlocks.containsKey(id)) {
					allBlocks.put(id, BlockColor.toAvailableColor(color));
				}
			}

			// Prepare updated occupied rooms list
			else if (name.equals("occupied")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				String id = ((Identifier) parameters.get(0)).getValue();
				data.getEnvironmentDatabase().getOccupiedRooms().add(id);
			}

			else if (name.equals("not")) {
				// already processed, skip.
			}

			else if (name.equals("sequenceIndex")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				int index = ((Numeral) parameters.get(0)).getValue().intValue();
				data.getEnvironmentDatabase().setSequenceIndex(index);
			}

			// Location can be updated immediately.
			else if (name.equals("location")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				double x = ((Numeral) parameters.get(0)).getValue().doubleValue();
				double y = ((Numeral) parameters.get(1)).getValue().doubleValue();
				data.getEnvironmentDatabase().setEntityLocation(new Double[] { x, y });
			}

			// Check if holding a block
			else if (name.equals("holding")) {
				data.getEnvironmentDatabase().setHoldingID(
						((Numeral) percept.getParameters().get(0)).getValue().longValue());
				data.getEnvironmentDatabase().setEntityColor(
						allBlocks.get(data.getEnvironmentDatabase().getHoldingID()).getColor());
			}

			else if (name.equals("player")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				String player = ((Identifier) parameters.get(0)).getValue();
				if (!data.getEnvironmentDatabase().getOtherPlayers().contains(player)) {
					data.getEnvironmentDatabase().getOtherPlayers().add(player);
				}
			}

			// Update group goal sequence
			else if (name.equals("sequence")) {
				LinkedList<Parameter> parameters = percept.getParameters();
				for (Parameter i : parameters) {
					ParameterList list = (ParameterList) i;
					for (Parameter j : list) {
						char letter = (((Identifier) j).getValue().charAt(0));
						data.getEnvironmentDatabase().getSequence().add(BlockColor.toAvailableColor(letter));
					}
				}
			}

			// Update chat history
			else if (name.equals("message")) {
				LinkedList<Parameter> parameters = percept.getParameters();

				ParameterList parameterList = ((ParameterList) parameters.get(0));

				Iterator<Parameter> iterator = parameterList.iterator();

				String sender = ((Identifier) iterator.next()).getValue();
				String message = ((Identifier) iterator.next()).getValue();

				data.getChatSession().append(sender + " : " + message + "\n");
				data.getChatSession().setCaretPosition(data.getChatSession().getDocument().getLength());

				ArrayList<String> newMessage = new ArrayList<String>();
				newMessage.add(sender);
				newMessage.add(message);
				data.getEnvironmentDatabase().getChatHistory().add(newMessage);
			}

		}

	}
}
