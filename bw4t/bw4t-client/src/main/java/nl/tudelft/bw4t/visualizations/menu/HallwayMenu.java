package nl.tudelft.bw4t.visualizations.menu;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;
import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;
import nl.tudelft.bw4t.visualizations.BasicOperations;
import nl.tudelft.bw4t.visualizations.VisualizerSettings;
import nl.tudelft.bw4t.visualizations.data.BW4TClientMapRendererData;
import nl.tudelft.bw4t.visualizations.data.RoomInfo;
import nl.tudelft.bw4t.visualizations.listeners.GotoPositionActionListener;
import nl.tudelft.bw4t.visualizations.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.visualizations.listeners.PutdownActionListener;

public class HallwayMenu {
    /**
     * Builds a pop up menu for when the player clicked on a hallway
     */
    public static void buildPopUpMenuForHallway(BW4TClientMapRenderer bw4tClientMapRenderer) {
        BW4TClientMapRendererData data = bw4tClientMapRenderer.getData();
        data.jPopupMenu.removeAll();
        long holdingID = data.environmentDatabase.getHoldingID();
        Color entityColor = data.environmentDatabase.getEntityColor();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to: ",
                data.jPopupMenu);

        JMenuItem menuItem = new JMenuItem("Go to here");
        menuItem.addActionListener(new GotoPositionActionListener(new Point(
                data.selectedLocation[0] / VisualizerSettings.scale,
                data.selectedLocation[1] / VisualizerSettings.scale), bw4tClientMapRenderer));
        data.jPopupMenu.add(menuItem);

        if (holdingID != Long.MAX_VALUE) {
            menuItem = new JMenuItem("Put down box");
            menuItem.addActionListener(new PutdownActionListener(bw4tClientMapRenderer));
            data.jPopupMenu.add(menuItem);
        }

        data.jPopupMenu.addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ",
                data.jPopupMenu);

        for (RoomInfo room : data.environmentDatabase.getRooms()) {
            BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                    MessageType.amWaitingOutsideRoom, BasicOperations.findLabelForRoom(room,data),
                    null, null), data);
        }

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.amWaitingOutsideRoom,
                BasicOperations.findLabelForRoom(data.environmentDatabase.getDropZone(),data), null,
                null), data);

        if (holdingID != Long.MAX_VALUE) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.hasColor, null, ColorTranslator
                            .translate2ColorString(entityColor), null), data);

            JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu("I have a "
                    + ColorTranslator.translate2ColorString(entityColor)
                    + " block from ", data.jPopupMenu);

            for (RoomInfo roomInfo : data.environmentDatabase.getRooms()) {
                String label = BasicOperations.findLabelForRoom(roomInfo, data);
                menuItem = new JMenuItem(Long.toString(roomInfo.getId()));
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.hasColor, label,
                                ColorTranslator
                                        .translate2ColorString(entityColor),
                                null), data));
                submenu.add(menuItem);
            }
        }

        data.jPopupMenu.addSeparator();
        menuItem = new JMenuItem("Close menu");
        data.jPopupMenu.add(menuItem);
    }

    
}
