package nl.tudelft.bw4t.visualizations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Constants;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;
import nl.tudelft.bw4t.visualizations.data.BW4TClientMapRendererData;
import nl.tudelft.bw4t.visualizations.data.DoorInfo;
import nl.tudelft.bw4t.visualizations.data.DropZoneInfo;
import nl.tudelft.bw4t.visualizations.data.RoomInfo;
import nl.tudelft.bw4t.visualizations.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.visualizations.listeners.PutdownActionListener;
import nl.tudelft.bw4t.visualizations.menu.BasicMenuOperations;

public class BasicOperations {
    /**
     * Build the pop up menu for clicking on a group goal color
     * 
     * @param color
     *            , the color that was clicked
     */
    public static void buildPopUpMenuForGoalColor(BlockColor color,
            BW4TClientMapRenderer bw4tClientMapRenderer) {
        BW4TClientMapRendererData data = bw4tClientMapRenderer.getData();
        long holdingID = data.environmentDatabase.getHoldingID();

        data.jPopupMenu.removeAll();

        JMenuItem menuItem = new JMenuItem(color.getName());
        data.jPopupMenu.add(menuItem);
        data.jPopupMenu.addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:",
                data.jPopupMenu);

        if (holdingID != Long.MAX_VALUE) {
            menuItem = new JMenuItem("Put down block");
            menuItem.addActionListener(new PutdownActionListener(
                    bw4tClientMapRenderer));
            data.jPopupMenu.add(menuItem);
        }

        data.jPopupMenu.addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ",
                data.jPopupMenu);

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.lookingFor, null, color.getName(), null), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.willGetColor, null, color.getName(), null), data);
        BasicMenuOperations
                .addMenuItemToPopupMenu(new BW4TMessage(
                        MessageType.droppedOffBlock, null, color.getName(),
                        null), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.weNeed, null, color.getName(), null), data);

        if (holdingID != Long.MAX_VALUE) {
            BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                    MessageType.hasColor, null, color.getName(), null), data);

            JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(
                    "I have a " + color + " block from room", data.jPopupMenu);

            for (RoomInfo room : data.environmentDatabase.getRooms()) {
                String label = findLabelForRoom(room, data);
                menuItem = new JMenuItem(Long.toString(room.getId()));
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.hasColorFromRoom, label,
                                color.getName(), null), data));
                submenu.add(menuItem);
            }
        }

        data.jPopupMenu.addSeparator();
        BasicMenuOperations
                .addSectionTitleToPopupMenu("Ask: ", data.jPopupMenu);

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whereIsColor, null, color.getName(), null), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whoHasABlock, null, color.getName(), null), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whereShouldIGo), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whatColorShouldIGet), data);

        data.jPopupMenu.addSeparator();
        menuItem = new JMenuItem("Close menu");
        data.jPopupMenu.add(menuItem);
    }

    /**
     * Helper method to find the nav point label corresponding to a certain room
     * 
     * @param room
     *            , the room for which to find the navpoint label
     * @return the label
     */
    public static String findLabelForRoom(RoomInfo room,
            BW4TClientMapRendererData data) {
        HashMap<String, Point> roomLabels = data.environmentDatabase
                .getRoomLabels();
        for (String label : roomLabels.keySet()) {
            Shape roomBoundaries = new Rectangle2D.Double(room.getX(),
                    room.getY(), room.getWidth(), room.getHeight());
            if (roomBoundaries.contains(roomLabels.get(label))) {
                return label;
            }
        }
        return null;
    }

    /**
     * Transform world coordinates to our coordinates.
     * 
     * @param boundingBox
     *            , a rectangle in the world coordinate system
     * @return The new rectangle in our coordinate system.
     */
    public static Shape transformRectangle(Rectangle2D boundingBox) {
        int width = (int) (boundingBox.getWidth() * VisualizerSettings.scale);
        int height = (int) (boundingBox.getHeight() * VisualizerSettings.scale);
        int xPos = (int) (boundingBox.getX() * VisualizerSettings.scale)
                - (width / 2);
        int yPos = (int) (boundingBox.getY() * VisualizerSettings.scale)
                - (height / 2);

        return new Rectangle(xPos, yPos, width, height);
    }

    /**
     * Method to determine if the player is close to a box (within 0.5 of the
     * coordinates of the box)
     * 
     * @param boxID
     *            , the box that should be checked
     * @return true if close to the box, false if not
     */
    public static boolean closeToBox(Long boxID, BW4TClientMapRendererData data) {
        HashMap<Long, java.awt.geom.Point2D.Double> objectPositions = data.environmentDatabase
                .getObjectPositions();
        double minX = objectPositions.get(boxID).getX() - 0.5;
        double maxX = objectPositions.get(boxID).getX() + 0.5;
        double minY = objectPositions.get(boxID).getY() - 0.5;
        double maxY = objectPositions.get(boxID).getY() + 0.5;
        Double[] entityLocation = data.environmentDatabase.getEntityLocation();
        if ((entityLocation[0] > minX) && (entityLocation[0] < maxX)
                && (entityLocation[1] > minY) && (entityLocation[1] < maxY))
            return true;
        else
            return false;
    }
}
