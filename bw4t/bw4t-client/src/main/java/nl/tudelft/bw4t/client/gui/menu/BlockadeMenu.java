package nl.tudelft.bw4t.client.gui.menu;

import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PutdownActionListener;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageType;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.ViewBlock;

public class BlockadeMenu {
    /**
     * Builds a pop up menu for when the player clicked on a hallway
     * 
     * @param gui
     *      gui on which the popUpMenu will be build
     */
    public static void buildPopUpMenuForBlockade(BW4TClientGUI gui) {
        gui.getjPopupMenu().removeAll();
        ClientMapController cmc = gui.getController().getMapController();

        ViewBlock holdingID = cmc.getTheBot().getFirstHolding();
        Color entityColor = cmc.getTheBot().getColor();

        JMenuItem menuItem = new JMenuItem();

        if (holdingID != null) {
            menuItem = new JMenuItem("Put down box");
            menuItem.addActionListener(new PutdownActionListener(gui.getController()));
            gui.getjPopupMenu().add(menuItem);
        }

        buildTellMenu(gui, cmc, holdingID, entityColor);
    }
    
    /**
     * 
     * @param gui
     *      gui on which the popUpMenu will be build
     * @param cmc
     *      ClientMapController used to get all rooms.
     * @param holdingID
     *      ID of the currently holding block (if any).
     * @param entityColor
     *      Color of the robot.
     */
    public static void buildTellMenu(BW4TClientGUI gui, ClientMapController cmc, ViewBlock holdingID, Color entityColor) {
        JMenuItem menuItem;
        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ", gui.getjPopupMenu());

        for (Zone roomInfo : cmc.getRooms()) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.AMWAITINGOUTSIDEROOM, roomInfo.getName(), null, null), gui);
        }

        tellMenuHolding(gui, cmc, holdingID, entityColor);

        gui.getjPopupMenu().addSeparator();
        menuItem = new JMenuItem("Close menu");
        gui.getjPopupMenu().add(menuItem);
    }

    /**
     * 
     * @param gui
     *      gui on which the popUpMenu will be build
     * @param cmc
     *      ClientMapController used to get all rooms.
     * @param holdingID
     *      ID of the currently holding block (if any).
     * @param entityColor
     *      Color of the robot.
     */
    private static void tellMenuHolding(BW4TClientGUI gui, ClientMapController cmc, ViewBlock holdingID,
            Color entityColor) {
        JMenuItem menuItem;
        if (holdingID != null) {
            String colorAsString = BasicMenuOperations.getColor(ColorTranslator.translate2ColorString(entityColor), gui
                    .getController().getHumanAgent());
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.HASCOLOR, null, colorAsString, null), gui);

            JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu("I have a " + colorAsString + " block from ",
                    gui.getjPopupMenu());

            for (Zone roomInfo : cmc.getRooms()) {
                menuItem = new JMenuItem(roomInfo.getName());
                menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(MessageType.HASCOLOR,
                        roomInfo.getName(), colorAsString, null), gui.getController()));
                submenu.add(menuItem);
            }
        }
    }

}
