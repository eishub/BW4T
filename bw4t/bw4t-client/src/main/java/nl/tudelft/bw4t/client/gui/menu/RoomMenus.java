package nl.tudelft.bw4t.client.gui.menu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.data.structures.BW4TClientInfo;
import nl.tudelft.bw4t.client.gui.data.structures.RoomInfo;
import nl.tudelft.bw4t.client.gui.listeners.GoToBlockActionListener;
import nl.tudelft.bw4t.client.gui.listeners.GoToRoomActionListener;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PickUpActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PutdownActionListener;
import nl.tudelft.bw4t.client.gui.operations.MapOperations;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;

public class RoomMenus {
    /**
     * Used for building the pop up menu when clicking on the agent while it is
     * near a box
     * 
     * @param box
     *            , the box that the robot is at.
     */
    public static void buildPopUpMenuForBeingAtBlock(Long boxID, RoomInfo room,
            BW4TClientGUI bw4tClientMapRenderer) {
        BW4TClientInfo bw4tClientInfo = bw4tClientMapRenderer
                .getBW4TClientInfo();
        String label = MapOperations.findLabelForRoom(room, bw4tClientInfo);

        bw4tClientInfo.jPopupMenu.removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:",
                bw4tClientInfo.jPopupMenu);

        HashMap<Long, BlockColor> allBlocks = bw4tClientInfo.environmentDatabase
                .getAllBlocks();

        JMenuItem menuItem = new JMenuItem("Pick up " + allBlocks.get(boxID)
                + " block");
        menuItem.addActionListener(new PickUpActionListener(
                bw4tClientMapRenderer));
        bw4tClientInfo.jPopupMenu.add(menuItem);

        // Message sending
        bw4tClientInfo.jPopupMenu.addSeparator();
        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ",
                bw4tClientInfo.jPopupMenu);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.roomContains, label,
                allBlocks.get(boxID).getName(), null), bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.amGettingColor, label, allBlocks.get(boxID)
                        .getName(), null), bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.atBox, null, allBlocks.get(boxID).getName(), null),
                bw4tClientInfo);

        bw4tClientInfo.jPopupMenu.addSeparator();
        menuItem = new JMenuItem("Close menu");
        bw4tClientInfo.jPopupMenu.add(menuItem);
    }

    /**
     * Used for building a pop up menu when clicking on a box
     * 
     * @param box
     *            , the box that was clicked on
     */
    public static void buildPopUpMenuForBlock(Long boxID, RoomInfo room,
            BW4TClientGUI bw4tClientMapRenderer) {
        BW4TClientInfo bw4tClientInfo = bw4tClientMapRenderer
                .getBW4TClientInfo();
        String label = MapOperations.findLabelForRoom(room, bw4tClientInfo);

        bw4tClientInfo.jPopupMenu.removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:",
                bw4tClientInfo.jPopupMenu);
        HashMap<Long, BlockColor> allBlocks = bw4tClientInfo.environmentDatabase
                .getAllBlocks();

        JMenuItem menuItem = new JMenuItem("Go to " + allBlocks.get(boxID)
                + " block");
        menuItem.addActionListener(new GoToBlockActionListener(boxID,
                bw4tClientInfo));
        bw4tClientInfo.jPopupMenu.add(menuItem);

        // Message sending
        bw4tClientInfo.jPopupMenu.addSeparator();
        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ",
                bw4tClientInfo.jPopupMenu);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.roomContains, label,
                allBlocks.get(boxID).getName(), null), bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.amGettingColor, label, allBlocks.get(boxID)
                        .getName(), null), bw4tClientInfo);

        bw4tClientInfo.jPopupMenu.addSeparator();
        menuItem = new JMenuItem("Close menu");
        bw4tClientInfo.jPopupMenu.add(menuItem);
    }

    /**
     * Used for building a pop up menu when clicking on a room
     * 
     * @param room
     *            , the room that was clicked on
     */
    public static void buildPopUpMenuRoom(RoomInfo room,
            BW4TClientGUI bw4tClientMapRenderer) {
        BW4TClientInfo bw4tClientInfo = bw4tClientMapRenderer
                .getBW4TClientInfo();
        long holdingID = bw4tClientInfo.environmentDatabase.getHoldingID();
        Color entityColor = bw4tClientInfo.environmentDatabase.getEntityColor();
        String label = MapOperations.findLabelForRoom(room, bw4tClientInfo);
        bw4tClientInfo.jPopupMenu.removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to: ",
                bw4tClientInfo.jPopupMenu);

        JMenuItem menuItem = new JMenuItem("Go to " + label);
        menuItem.addActionListener(new GoToRoomActionListener(label,
                bw4tClientMapRenderer));
        bw4tClientInfo.jPopupMenu.add(menuItem);

        if (holdingID != Long.MAX_VALUE) {
            menuItem = new JMenuItem("Put down box");
            menuItem.addActionListener(new PutdownActionListener(bw4tClientInfo));
            bw4tClientInfo.jPopupMenu.add(menuItem);
        }

        bw4tClientInfo.jPopupMenu.addSeparator();

        // Message sending
        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ",
                bw4tClientInfo.jPopupMenu);

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.inRoom, label, null, null), bw4tClientInfo);

        JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(label
                + " contains ", bw4tClientInfo.jPopupMenu);

        for (String color : ColorTranslator.getAllColors()) {
            menuItem = new JMenuItem(color);
            menuItem.addActionListener(new MessageSenderActionListener(
                    new BW4TMessage(MessageType.roomContains, label, color,
                            null), bw4tClientInfo));
            submenu.add(menuItem);
        }

        submenu = BasicMenuOperations.addSubMenuToPopupMenu(label
                + " contains ", bw4tClientInfo.jPopupMenu);

        for (int i = 0; i < 6; i++) {
            JMenu submenuColor = new JMenu("" + i);
            submenu.add(submenuColor);

            for (String color : ColorTranslator.getAllColors()) {
                menuItem = new JMenuItem(color);
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.roomContainsAmount, label,
                                color, i), bw4tClientInfo));
                submenuColor.add(menuItem);
            }
        }

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.checked, label, null, null), bw4tClientInfo);

        submenu = BasicMenuOperations.addSubMenuToPopupMenu(label
                + " has been checked by ", bw4tClientInfo.jPopupMenu);

        ArrayList<String> otherPlayers = bw4tClientInfo.environmentDatabase
                .getOtherPlayers();
        for (int i = 0; i < otherPlayers.size(); i++) {
            menuItem = new JMenuItem("" + otherPlayers.get(i));
            menuItem.addActionListener(new MessageSenderActionListener(
                    new BW4TMessage(MessageType.checked, label, null,
                            otherPlayers.get(i)), bw4tClientInfo));
            submenu.add(menuItem);
        }

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.roomIsEmpty, label, null, null), bw4tClientInfo);

        if (holdingID != Long.MAX_VALUE) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.aboutToDropOffBlock, null,
                            ColorTranslator.translate2ColorString(entityColor),
                            null), bw4tClientInfo);
        } else {
            BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                    MessageType.droppedOffBlock, null, null, null),
                    bw4tClientInfo);
        }

        if (holdingID != Long.MAX_VALUE) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.hasColor, null, ColorTranslator
                            .translate2ColorString(entityColor), null),
                    bw4tClientInfo);

            submenu = BasicMenuOperations.addSubMenuToPopupMenu("I have a "
                    + ColorTranslator.translate2ColorString(entityColor)
                    + " block from ", bw4tClientInfo.jPopupMenu);

            for (RoomInfo roomInfo : bw4tClientInfo.environmentDatabase
                    .getRooms()) {
                String labelT = MapOperations.findLabelForRoom(roomInfo,
                        bw4tClientInfo);
                menuItem = new JMenuItem(labelT);
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.hasColor, labelT,
                                ColorTranslator
                                        .translate2ColorString(entityColor),
                                null), bw4tClientInfo));
                submenu.add(menuItem);
            }
        }

        BasicMenuOperations.addSectionTitleToPopupMenu("Ask:",
                bw4tClientInfo.jPopupMenu);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.isAnybodyGoingToRoom, label, null, null),
                bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whatIsInRoom, label, null, null), bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.hasAnybodyCheckedRoom, label, null, null),
                bw4tClientInfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.whoIsInRoom, label, null, null), bw4tClientInfo);

        bw4tClientInfo.jPopupMenu.addSeparator();
        menuItem = new JMenuItem("Close menu");
        bw4tClientInfo.jPopupMenu.add(menuItem);
    }

}
