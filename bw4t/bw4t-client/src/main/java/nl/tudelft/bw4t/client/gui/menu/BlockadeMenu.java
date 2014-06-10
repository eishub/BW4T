package nl.tudelft.bw4t.client.gui.menu;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.listeners.GotoPositionActionListener;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PutdownActionListener;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.renderer.MapRenderSettings;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;

public class BlockadeMenu {
    /**
     * Builds a pop up menu for when the player clicked on a hallway
     */
    public static void buildPopUpMenuForBlockade(BW4TClientGUI gui) {
        gui.getjPopupMenu().removeAll();
        ClientMapController cmc = gui.getController().getMapController();
        MapRenderSettings set = cmc.getRenderSettings();

        ViewBlock holdingID = cmc.getTheBot().getFirstHolding();
        Color entityColor = cmc.getTheBot().getColor();

        JMenuItem menuItem = new JMenuItem();

        if (holdingID != null) {
            menuItem = new JMenuItem("Put down box");
            menuItem.addActionListener(new PutdownActionListener(gui.getController()));
            gui.getjPopupMenu().add(menuItem);
        }

        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ", gui.getjPopupMenu());

        for (Zone roomInfo : cmc.getRooms()) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.AMWAITINGOUTSIDEROOM, roomInfo.getName(), null, null), gui);
        }

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.AMWAITINGOUTSIDEROOM, cmc.getDropZone()
                .getName(), null, null), gui);

        if (holdingID != null) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.HASCOLOR, null, ColorTranslator.translate2ColorString(entityColor),
                            null), gui);

            JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(
                    "I have a " + ColorTranslator.translate2ColorString(entityColor) + " block from ",
                    gui.getjPopupMenu());

            for (Zone roomInfo : cmc.getRooms()) {
                menuItem = new JMenuItem(roomInfo.getName());
                menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(MessageType.HASCOLOR,
                        roomInfo.getName(), ColorTranslator.translate2ColorString(entityColor), null), gui
                        .getController()));
                submenu.add(menuItem);
            }
        }

        gui.getjPopupMenu().addSeparator();
        menuItem = new JMenuItem("Close menu");
        gui.getjPopupMenu().add(menuItem);
    }

}
