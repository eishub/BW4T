package nl.tudelft.bw4t.visualizations.data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Constants;
import nl.tudelft.bw4t.visualizations.BasicOperations;
import nl.tudelft.bw4t.visualizations.VisualizerSettings;

public class DataProcessing {

    /**
     * Display the goal sequence
     * 
     * @param g2d
     *            , the graphics2d object
     */
    public static void processSequence(Graphics2D g2d,
            BW4TClientMapRendererData data) {
        int startPosX = 0;
        for (BlockColor color : data.environmentDatabase.getSequence()) {
            g2d.setColor(color.getColor());
            g2d.fill(new Rectangle2D.Double(startPosX,
                    VisualizerSettings.worldY * VisualizerSettings.scale, 20,
                    20));
            if (data.environmentDatabase.getSequenceIndex() > (startPosX / 20)) {
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
     * Process all rooms and their connected doors, and display them in the
     * panel with an outline
     * 
     * @param g2d
     *            , the graphics2d object
     */
    public static void processRooms(Graphics2D g2d,
            BW4TClientMapRendererData data) {
        for (RoomInfo room : data.environmentDatabase.getRooms()) {
            // first paint the doors. Matches the {@link ServerMapRenderer}
            if (data.environmentDatabase.getOccupiedRooms().contains(
                    BasicOperations.findLabelForRoom(room, data))) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.GREEN);
            }

            for (DoorInfo door : room.getDoors()) {
                g2d.fill(BasicOperations.transformRectangle(new Rectangle2D.Double(
                        door.getX(), door.getY(), door.getWidth(), door
                                .getHeight())));
            }

            // paint the room
            g2d.setColor(Color.GRAY);
            Shape roomDisplayCoordinates = BasicOperations
                    .transformRectangle(new Rectangle2D.Double(room.getX(),
                            room.getY(), room.getWidth(), room.getHeight()));
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
    public static void processLabels(Graphics2D g2d,
            BW4TClientMapRendererData data) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        HashMap<String, Point> roomLabels = data.environmentDatabase
                .getRoomLabels();
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
    public static void processDropZone(Graphics2D g2d,
            BW4TClientMapRendererData data) {
        g2d.setColor(Color.DARK_GRAY);
        DropZoneInfo dropZone = data.environmentDatabase.getDropZone();
        g2d.fill(BasicOperations.transformRectangle(new Rectangle2D.Double(
                dropZone.getX(), dropZone.getY(), dropZone.getWidth(), dropZone
                        .getHeight())));
        g2d.setColor(Color.BLACK);
        g2d.draw(BasicOperations.transformRectangle(new Rectangle2D.Double(
                dropZone.getX(), dropZone.getY(), dropZone.getWidth(), dropZone
                        .getHeight())));

        if (data.environmentDatabase.getOccupiedRooms().contains(
                VisualizerSettings.DROPZONE_NAME)) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(Color.GREEN);
        }

        for (DoorInfo door : dropZone.getDoors()) {
            g2d.fill(BasicOperations.transformRectangle(new Rectangle2D.Double(
                    door.getX(), door.getY(), door.getWidth(), door.getHeight())));
        }
    }

    /**
     * Process all blocks that are visible and display them with their color
     * 
     * @param g2d
     *            , the graphics2d object
     */
    public static void processBlocks(Graphics2D g2d,
            BW4TClientMapRendererData data) {
        HashMap<Long, BlockColor> allBlocks = data.environmentDatabase
                .getAllBlocks();
        for (Long box : allBlocks.keySet()) {
            if (data.environmentDatabase.getVisibleBlocks().contains(box)) {
                HashMap<Long, java.awt.geom.Point2D.Double> objectPositions = data.environmentDatabase
                        .getObjectPositions();
                if (objectPositions.get(box) != null) {
                    g2d.setColor(allBlocks.get(box).getColor());
                    g2d.fill(BasicOperations
                            .transformRectangle(new Rectangle2D.Double(
                                    objectPositions.get(box).getX(),
                                    objectPositions.get(box).getY(),
                                    Constants.BLOCK_SIZE, Constants.BLOCK_SIZE)));
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
    public static void processEntity(Graphics2D g2d,
            BW4TClientMapRendererData data) {
        g2d.setColor(data.environmentDatabase.getEntityColor());
        Double[] entityLocation = data.environmentDatabase.getEntityLocation();
        g2d.fill(BasicOperations.transformRectangle(new Rectangle2D.Double(
                entityLocation[0], entityLocation[1], Constants.ROBOT_SIZE,
                Constants.ROBOT_SIZE)));
    }

}
