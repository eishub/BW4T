package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageType;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.map.Zone;

public class PlayerMenu {
    /**
     * Builds pop up menu for sending requests to a certain player, is called
     * when a player button is pressed
     * 
     * @param playerId
     *            , the playerId that the request should be sent to
     */
    public static void buildPopUpMenuForRequests(String playerId, BW4TClientGUI gui) {
        ClientMapController cmc = gui.getController().getMapController();
        gui.getjPopupMenu().removeAll();
        BasicMenuOperations.addSectionTitleToPopupMenu("Request:", gui.getjPopupMenu());

        // Check if the playerId is a specific player
        String receiver = "Somebody";
        if (!playerId.equalsIgnoreCase("all")) {
            receiver = playerId;
        }

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.PUTDOWN, null, null, receiver), gui);

        JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(receiver + " go to room", gui.getjPopupMenu());

        for (Zone room : cmc.getRooms()) {
            JMenuItem menuItem = new JMenuItem(room.getName());
            menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(MessageType.GOTOROOM, room.getName(),
                    null, receiver), gui));
            submenu.add(menuItem);
        }

        submenu = BasicMenuOperations.addSubMenuToPopupMenu(receiver + " find a color", gui.getjPopupMenu());

        for (String color : ColorTranslator.getAllColors()) {
            JMenuItem menuItem = new JMenuItem(color);
            menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(MessageType.FINDCOLOR, null,
                    color, receiver), gui));
            submenu.add(menuItem);
        }

        submenu = BasicMenuOperations
                .addSubMenuToPopupMenu(receiver + " get the color from room", gui.getjPopupMenu());

        for (String color : ColorTranslator.getAllColors()) {
            JMenu submenu2 = new JMenu(color);
            submenu.add(submenu2);

            for (Zone room : cmc.getRooms()) {
                JMenuItem menuItem = new JMenuItem(room.getName());
                menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(
                        MessageType.GETCOLORFROMROOM, room.getName(), color, receiver), gui));
                submenu2.add(menuItem);
            }
        }

        BasicMenuOperations.addSectionTitleToPopupMenu("Ask:", gui.getjPopupMenu());
        BasicMenuOperations
        .addMenuItemToPopupMenu(new BW4TMessage(MessageType.AREYOUCLOSE, null, null, receiver), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.WILLYOUBELONG, null, null, receiver),
                gui);

        gui.getjPopupMenu().addSeparator();
        JMenuItem menuItem = new JMenuItem("Close menu");
        gui.getjPopupMenu().add(menuItem);
    }
}
