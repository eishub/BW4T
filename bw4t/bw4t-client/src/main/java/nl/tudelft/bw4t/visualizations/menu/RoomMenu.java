package nl.tudelft.bw4t.visualizations.menu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;
import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;
import nl.tudelft.bw4t.visualizations.BasicOperations;
import nl.tudelft.bw4t.visualizations.data.BW4TClientMapRendererData;
import nl.tudelft.bw4t.visualizations.data.RoomInfo;
import nl.tudelft.bw4t.visualizations.listeners.GoToBlockActionListener;
import nl.tudelft.bw4t.visualizations.listeners.GoToRoomActionListener;
import nl.tudelft.bw4t.visualizations.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.visualizations.listeners.PickUpActionListener;
import nl.tudelft.bw4t.visualizations.listeners.PutdownActionListener;

public class RoomMenu {
    /**
     * Used for building the pop up menu when clicking on the agent while it is
     * near a box
     * 
     * @param box
     *            , the box that the robot is at.
     */
    public static void buildPopUpMenuForBeingAtBlock(Long boxID, RoomInfo room,
            BW4TClientMapRenderer bw4tClientMapRenderer) {
        BW4TClientMapRendererData data = bw4tClientMapRenderer.getData();
        String label = BasicOperations.findLabelForRoom(room, data);

        data.jPopupMenu.removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:",
                data.jPopupMenu);

        HashMap<Long, BlockColor> allBlocks = data.environmentDatabase
                .getAllBlocks();

        JMenuItem menuItem = new JMenuItem("Pick up " + allBlocks.get(boxID)
                + " block");
        menuItem.addActionListener(new PickUpActionListener(
                bw4tClientMapRenderer));
        data.jPopupMenu.add(menuItem);

        // Message sending
        data.jPopupMenu.addSeparator();
        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ",
                data.jPopupMenu);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.roomContains, label,
                allBlocks.get(boxID).getName(), null), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.amGettingColor, label, allBlocks.get(boxID)
                        .getName(), null), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.atBox, null, allBlocks.get(boxID).getName(), null),
                data);

        data.jPopupMenu.addSeparator();
        menuItem = new JMenuItem("Close menu");
        data.jPopupMenu.add(menuItem);
    }

    /**
     * Used for building a pop up menu when clicking on a box
     * 
     * @param box
     *            , the box that was clicked on
     */
    public static void buildPopUpMenuForBlock(Long boxID, RoomInfo room,
            BW4TClientMapRenderer bw4tClientMapRenderer) {
        BW4TClientMapRendererData data = bw4tClientMapRenderer.getData();
        String label = BasicOperations.findLabelForRoom(room, data);

        data.jPopupMenu.removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:",
                data.jPopupMenu);
        HashMap<Long, BlockColor> allBlocks = data.environmentDatabase
                .getAllBlocks();

        JMenuItem menuItem = new JMenuItem("Go to " + allBlocks.get(boxID)
                + " block");
        menuItem.addActionListener(new GoToBlockActionListener(boxID,
                bw4tClientMapRenderer));
        data.jPopupMenu.add(menuItem);

        // Message sending
        data.jPopupMenu.addSeparator();
        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ",
                data.jPopupMenu);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.roomContains, label,
                allBlocks.get(boxID).getName(), null), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.amGettingColor, label, allBlocks.get(boxID)
                        .getName(), null), data);

        data.jPopupMenu.addSeparator();
        menuItem = new JMenuItem("Close menu");
        data.jPopupMenu.add(menuItem);
    }

    /**
     * Used for building a pop up menu when clicking on a room
     * 
     * @param room
     *            , the room that was clicked on
     */
    public static void buildPopUpMenuRoom(RoomInfo room,
            BW4TClientMapRenderer bw4tClientMapRenderer) {
        BW4TClientMapRendererData data = bw4tClientMapRenderer.getData();
        long holdingID = data.environmentDatabase.getHoldingID();
        Color entityColor = data.environmentDatabase.getEntityColor();
        String label = BasicOperations.findLabelForRoom(room, data);
        data.jPopupMenu.removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to: ",
                data.jPopupMenu);

        JMenuItem menuItem = new JMenuItem("Go to " + label);
        menuItem.addActionListener(new GoToRoomActionListener(label,
                bw4tClientMapRenderer));
        data.jPopupMenu.add(menuItem);

        if (holdingID != Long.MAX_VALUE) {
            menuItem = new JMenuItem("Put down box");
            menuItem.addActionListener(new PutdownActionListener(
                    bw4tClientMapRenderer));
            data.jPopupMenu.add(menuItem);
        }

        data.jPopupMenu.addSeparator();

        // Message sending
        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ",
                data.jPopupMenu);

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.inRoom, label, null, null), data);

        JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(label
                + " contains ", data.jPopupMenu);

        for (String color : ColorTranslator.getAllColors()) {
            menuItem = new JMenuItem(color);
            menuItem.addActionListener(new MessageSenderActionListener(
                    new BW4TMessage(MessageType.roomContains, label, color,
                            null), data));
            submenu.add(menuItem);
        }

        submenu = BasicMenuOperations.addSubMenuToPopupMenu(label
                + " contains ", data.jPopupMenu);

        for (int i = 0; i < 6; i++) {
            JMenu submenuColor = new JMenu("" + i);
            submenu.add(submenuColor);

            for (String color : ColorTranslator.getAllColors()) {
                menuItem = new JMenuItem(color);
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.roomContainsAmount, label,
                                color, i), data));
                submenuColor.add(menuItem);
            }
        }

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.checked, label, null, null), data);

        submenu = BasicMenuOperations.addSubMenuToPopupMenu(label
                + " has been checked by ", data.jPopupMenu);

        ArrayList<String> otherPlayers = data.environmentDatabase
                .getOtherPlayers();
        for (int i = 0; i < otherPlayers.size(); i++) {
            menuItem = new JMenuItem("" + otherPlayers.get(i));
            menuItem.addActionListener(new MessageSenderActionListener(
                    new BW4TMessage(MessageType.checked, label, null,
                            otherPlayers.get(i)), data));
            submenu.add(menuItem);
        }

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.roomIsEmpty, label, null, null), data);

        if (holdingID != Long.MAX_VALUE) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.aboutToDropOffBlock, null,
                            ColorTranslator.translate2ColorString(entityColor),
                            null), data);
        } else {
            BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                    MessageType.droppedOffBlock, null, null, null), data);
        }

        if (holdingID != Long.MAX_VALUE) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.hasColor, null, ColorTranslator
                            .translate2ColorString(entityColor), null), data);

            submenu = BasicMenuOperations.addSubMenuToPopupMenu("I have a "
                    + ColorTranslator.translate2ColorString(entityColor)
                    + " block from ", data.jPopupMenu);

            for (RoomInfo roomInfo : data.environmentDatabase.getRooms()) {
                String labelT = BasicOperations
                        .findLabelForRoom(roomInfo, data);
                menuItem = new JMenuItem(labelT);
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.hasColor, labelT,
                                ColorTranslator
                                        .translate2ColorString(entityColor),
                                null), data));
                submenu.add(menuItem);
            }
        }

        BasicMenuOperations.addSectionTitleToPopupMenu("Ask:", data.jPopupMenu);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.isAnybodyGoingToRoom, label, null, null), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whatIsInRoom, label, null, null), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.hasAnybodyCheckedRoom, label, null, null), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whoIsInRoom, label, null, null), data);

        data.jPopupMenu.addSeparator();
        menuItem = new JMenuItem("Close menu");
        data.jPopupMenu.add(menuItem);
    }

}
