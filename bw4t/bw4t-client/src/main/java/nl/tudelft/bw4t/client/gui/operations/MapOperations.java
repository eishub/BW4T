package nl.tudelft.bw4t.client.gui.operations;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.VisualizerSettings;
import nl.tudelft.bw4t.client.gui.data.structures.BW4TClientInfo;
import nl.tudelft.bw4t.client.gui.data.structures.RoomInfo;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PutdownActionListener;
import nl.tudelft.bw4t.client.gui.menu.BasicMenuOperations;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;

public class MapOperations {
    /**
     * Build the pop up menu for clicking on a group goal color
     * 
     * @param color
     *            , the color that was clicked
     */
    public static void buildPopUpMenuForGoalColor(BlockColor color,
            BW4TClientGUI bw4tClientMapRenderer) {
        BW4TClientInfo bw4tClientInfo = bw4tClientMapRenderer
                .getBW4TClientInfo();
        long holdingID = bw4tClientInfo.environmentDatabase.getHoldingID();

        bw4tClientInfo.jPopupMenu.removeAll();

        JMenuItem menuItem = new JMenuItem(color.getName());
        bw4tClientInfo.jPopupMenu.add(menuItem);
        bw4tClientInfo.jPopupMenu.addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:",
                bw4tClientInfo.jPopupMenu);

        if (holdingID != Long.MAX_VALUE) {
            menuItem = new JMenuItem("Put down block");
            menuItem.addActionListener(new PutdownActionListener(bw4tClientInfo));
            bw4tClientInfo.jPopupMenu.add(menuItem);
        }

        bw4tClientInfo.jPopupMenu.addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ",
                bw4tClientInfo.jPopupMenu);

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.lookingFor, null, color.getName(), null),
                bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.willGetColor, null, color.getName(), null),
                bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.droppedOffBlock, null, color.getName(), null),
                bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.weNeed, null, color.getName(), null),
                bw4tClientInfo);

        if (holdingID != Long.MAX_VALUE) {
            BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                    MessageType.hasColor, null, color.getName(), null),
                    bw4tClientInfo);

            JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(
                    "I have a " + color + " block from room",
                    bw4tClientInfo.jPopupMenu);

            for (RoomInfo room : bw4tClientInfo.environmentDatabase.getRooms()) {
                String label = findLabelForRoom(room, bw4tClientInfo);
                menuItem = new JMenuItem(Long.toString(room.getId()));
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.hasColorFromRoom, label,
                                color.getName(), null), bw4tClientInfo));
                submenu.add(menuItem);
            }
        }

        bw4tClientInfo.jPopupMenu.addSeparator();
        BasicMenuOperations.addSectionTitleToPopupMenu("Ask: ",
                bw4tClientInfo.jPopupMenu);

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whereIsColor, null, color.getName(), null),
                bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whoHasABlock, null, color.getName(), null),
                bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whereShouldIGo), bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whatColorShouldIGet), bw4tClientInfo);

        bw4tClientInfo.jPopupMenu.addSeparator();
        menuItem = new JMenuItem("Close menu");
        bw4tClientInfo.jPopupMenu.add(menuItem);
    }

    /**
     * Helper method to find the nav point label corresponding to a certain room
     * 
     * @param room
     *            , the room for which to find the navpoint label
     * @return the label
     */
    public static String findLabelForRoom(RoomInfo room, BW4TClientInfo data) {
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
    public static boolean closeToBox(Long boxID, BW4TClientInfo data) {
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
