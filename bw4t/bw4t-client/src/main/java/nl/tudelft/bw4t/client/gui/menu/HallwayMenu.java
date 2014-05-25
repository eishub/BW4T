package nl.tudelft.bw4t.client.gui.menu;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.VisualizerSettings;
import nl.tudelft.bw4t.client.gui.data.structures.RoomInfo;
import nl.tudelft.bw4t.client.gui.listeners.GotoPositionActionListener;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PutdownActionListener;
import nl.tudelft.bw4t.client.gui.operations.MapOperations;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;

public class HallwayMenu {
    /**
     * Builds a pop up menu for when the player clicked on a hallway
     */
    public static void buildPopUpMenuForHallway(BW4TClientGUI bw4tClientInfo) {
        bw4tClientInfo.getjPopupMenu().removeAll();
        long holdingID = bw4tClientInfo.getEnvironmentDatabase().getHoldingID();
        Color entityColor = bw4tClientInfo.getEnvironmentDatabase().getEntityColor();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to: ", bw4tClientInfo.getjPopupMenu());

        JMenuItem menuItem = new JMenuItem("Go to here");
        menuItem.addActionListener(new GotoPositionActionListener(new Point(bw4tClientInfo.getSelectedLocation()[0]
                / VisualizerSettings.scale, bw4tClientInfo.getSelectedLocation()[1] / VisualizerSettings.scale),
                bw4tClientInfo));
        bw4tClientInfo.getjPopupMenu().add(menuItem);

        if (holdingID != Long.MAX_VALUE) {
            menuItem = new JMenuItem("Put down box");
            menuItem.addActionListener(new PutdownActionListener(bw4tClientInfo));
            bw4tClientInfo.getjPopupMenu().add(menuItem);
        }

        bw4tClientInfo.getjPopupMenu().addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ", bw4tClientInfo.getjPopupMenu());

        for (RoomInfo room : bw4tClientInfo.getEnvironmentDatabase().getRooms()) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.amWaitingOutsideRoom, MapOperations.findLabelForRoom(room,
                            bw4tClientInfo), null, null), bw4tClientInfo);
        }

        BasicMenuOperations.addMenuItemToPopupMenu(
                new BW4TMessage(MessageType.amWaitingOutsideRoom, MapOperations.findLabelForRoom(bw4tClientInfo
                        .getEnvironmentDatabase().getDropZone(), bw4tClientInfo), null, null), bw4tClientInfo);

        if (holdingID != Long.MAX_VALUE) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.hasColor, null, ColorTranslator.translate2ColorString(entityColor),
                            null), bw4tClientInfo);

            JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(
                    "I have a " + ColorTranslator.translate2ColorString(entityColor) + " block from ",
                    bw4tClientInfo.getjPopupMenu());

            for (RoomInfo roomInfo : bw4tClientInfo.getEnvironmentDatabase().getRooms()) {
                String label = MapOperations.findLabelForRoom(roomInfo, bw4tClientInfo);
                menuItem = new JMenuItem(Long.toString(roomInfo.getId()));
                menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(MessageType.hasColor, label,
                        ColorTranslator.translate2ColorString(entityColor), null), bw4tClientInfo));
                submenu.add(menuItem);
            }
        }

        bw4tClientInfo.getjPopupMenu().addSeparator();
        menuItem = new JMenuItem("Close menu");
        bw4tClientInfo.getjPopupMenu().add(menuItem);
    }

}
