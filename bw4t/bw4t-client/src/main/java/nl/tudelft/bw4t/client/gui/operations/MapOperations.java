package nl.tudelft.bw4t.client.gui.operations;

import java.awt.geom.Point2D;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PutdownActionListener;
import nl.tudelft.bw4t.client.gui.menu.BasicMenuOperations;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.Block;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;

public class MapOperations {
    /**
     * Build the pop up menu for clicking on a group goal color
     * 
     * @param color
     *            , the color that was clicked
     */
    public static void buildPopUpMenuForGoalColor(BlockColor color, BW4TClientGUI gui) {
        ClientMapController cmc = gui.getController().getMapController();
        Block holdingID = cmc.getTheBot().getFirstHolding();

        gui.getjPopupMenu().removeAll();

        JMenuItem menuItem = new JMenuItem(color.getName());
        gui.getjPopupMenu().add(menuItem);
        gui.getjPopupMenu().addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:", gui.getjPopupMenu());

        if (holdingID != null) {
            menuItem = new JMenuItem("Put down block");
            menuItem.addActionListener(new PutdownActionListener(gui.getController()));
            gui.getjPopupMenu().add(menuItem);
        }

        gui.getjPopupMenu().addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ", gui.getjPopupMenu());

        BasicMenuOperations.addMenuItemToPopupMenu(
                new BW4TMessage(MessageType.LOOKINGFOR, null, color.getName(), null), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.WILLGETCOLOR, null, color.getName(),
                null), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.DROPPEDOFFBLOCK, null, color.getName(),
                null), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.WENEED, null, color.getName(), null),
                gui);

        if (holdingID != null) {
            BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.HASCOLOR, null, color.getName(),
                    null), gui);

            JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu("I have a " + color + " block from room",
                    gui.getjPopupMenu());

            for (Zone room : cmc.getRooms()) {
                menuItem = new JMenuItem(room.getName());
                menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(
                        MessageType.HASCOLORFROMROOM, room.getName(), color.getName(), null), gui.getController()));
                submenu.add(menuItem);
            }
        }

        gui.getjPopupMenu().addSeparator();
        BasicMenuOperations.addSectionTitleToPopupMenu("Ask: ", gui.getjPopupMenu());

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.WHEREISCOLOR, null, color.getName(),
                null), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.WHOHASABLOCK, null, color.getName(),
                null), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.WHERESHOULDIGO), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.WHATCOLORSHOULDIGET), gui);

        gui.getjPopupMenu().addSeparator();
        menuItem = new JMenuItem("Close menu");
        gui.getjPopupMenu().add(menuItem);
    }

    /**
     * Method to determine if the player is close to a box (within 0.5 of the coordinates of the box)
     * 
     * @param boxID
     *            , the box that should be checked
     * @return true if close to the box, false if not
     */
    public static boolean closeToBox(Block boxID, ClientController data) {
        double minX = boxID.getPosition().getX() - 0.5;
        double maxX = boxID.getPosition().getX() + 0.5;
        double minY = boxID.getPosition().getY() - 0.5;
        double maxY = boxID.getPosition().getY() + 0.5;
        Point2D loc = data.getMapController().getTheBot().getLocation();
        return (loc.getX() > minX) && (loc.getX() < maxX) && (loc.getY() > minY) && (loc.getY() < maxY);
    }
}
