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
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageType;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.renderer.MapRenderSettings;
import nl.tudelft.bw4t.map.view.ViewBlock;

public class HallwayMenu {
    /**
     * Builds a pop up menu for when the player clicked on a hallway
     * The string indicates what the first menu item will say. 
     * In normal circumstances, it says "go to here". 
     * When dealing with a charging zone, it says "go charge". 
     */
    public static void buildPopUpMenuForHallway(BW4TClientGUI gui, String gotohall) {
        gui.getjPopupMenu().removeAll();
        ClientMapController cmc = gui.getController().getMapController();
        MapRenderSettings set = cmc.getRenderSettings();

        ViewBlock holdingID = cmc.getTheBot().getFirstHolding();
        Color entityColor = cmc.getTheBot().getColor();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to: ", gui.getjPopupMenu());

        JMenuItem menuItem = new JMenuItem(gotohall);
        menuItem.addActionListener(new GotoPositionActionListener(new Point((int) (gui.getSelectedLocation().x / set
                .getScale()), (int) (gui.getSelectedLocation().y / set.getScale())), gui.getController()));
        gui.getjPopupMenu().add(menuItem);

        if (holdingID != null) {
            menuItem = new JMenuItem("Put down box");
            menuItem.addActionListener(new PutdownActionListener(gui.getController()));
            gui.getjPopupMenu().add(menuItem);
        }

        gui.getjPopupMenu().addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ", gui.getjPopupMenu());

        for (Zone roomInfo : cmc.getRooms()) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.AMWAITINGOUTSIDEROOM, roomInfo.getName(), null, null), gui);
        }

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.AMWAITINGOUTSIDEROOM, cmc.getDropZone()
                .getName(), null, null), gui);

        if (holdingID != null) {
            String colorAsString = BasicMenuOperations.getColor(
                    ColorTranslator.translate2ColorString(entityColor), gui.getController().getHumanAgent());
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.HASCOLOR, null, colorAsString,
                            null), gui);

            JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(
                    "I have a " + colorAsString + " block from ",
                    gui.getjPopupMenu());

            for (Zone roomInfo : cmc.getRooms()) {
                menuItem = new JMenuItem(roomInfo.getName());
                menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(MessageType.HASCOLOR,
                        roomInfo.getName(), colorAsString, null), gui
                        .getController()));
                submenu.add(menuItem);
            }
        }

        gui.getjPopupMenu().addSeparator();
        menuItem = new JMenuItem("Close menu");
        gui.getjPopupMenu().add(menuItem);
    }

}
