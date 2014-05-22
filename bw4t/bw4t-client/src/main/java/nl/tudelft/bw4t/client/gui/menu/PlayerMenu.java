package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.data.structures.BW4TClientInfo;
import nl.tudelft.bw4t.client.gui.data.structures.RoomInfo;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.client.gui.operations.MapOperations;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;

public class PlayerMenu {
    /**
     * Builds pop up menu for sending requests to a certain player, is called
     * when a player button is pressed
     * 
     * @param playerId
     *            , the playerId that the request should be sent to
     */
    public static void buildPopUpMenuForRequests(String playerId,
            BW4TClientGUI bw4tClientMapRenderer) {
        BW4TClientInfo data = bw4tClientMapRenderer.getBW4TClientInfo();
        data.jPopupMenu.removeAll();
        BasicMenuOperations.addSectionTitleToPopupMenu("Request:",
                data.jPopupMenu);

        // Check if the playerId is a specific player
        String receiver = "Somebody";
        if (!playerId.equalsIgnoreCase("all"))
            receiver = playerId;

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.putDown, null, null, receiver), data);

        JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(receiver
                + " go to room", data.jPopupMenu);

        for (RoomInfo room : data.environmentDatabase.getRooms()) {
            String label = MapOperations.findLabelForRoom(room, data);
            JMenuItem menuItem = new JMenuItem(label);
            menuItem.addActionListener(new MessageSenderActionListener(
                    new BW4TMessage(MessageType.goToRoom, label, null, receiver),
                    data));
            submenu.add(menuItem);
        }

        submenu = BasicMenuOperations.addSubMenuToPopupMenu(receiver
                + " find a color", data.jPopupMenu);

        for (String color : ColorTranslator.getAllColors()) {
            JMenuItem menuItem = new JMenuItem(color);
            menuItem.addActionListener(new MessageSenderActionListener(
                    new BW4TMessage(MessageType.findColor, null, color,
                            receiver), data));
            submenu.add(menuItem);
        }

        submenu = BasicMenuOperations.addSubMenuToPopupMenu(receiver
                + " get the color from room", data.jPopupMenu);

        for (String color : ColorTranslator.getAllColors()) {
            JMenu submenu2 = new JMenu(color);
            submenu.add(submenu2);

            for (RoomInfo room : data.environmentDatabase.getRooms()) {
                String label = MapOperations.findLabelForRoom(room, data);
                JMenuItem menuItem = new JMenuItem(label);
                menuItem.addActionListener(new MessageSenderActionListener(
                        new BW4TMessage(MessageType.getColorFromRoom, label,
                                color, receiver), data));
                submenu2.add(menuItem);
            }
        }

        BasicMenuOperations.addSectionTitleToPopupMenu("Ask:", data.jPopupMenu);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.areYouClose, null, null, receiver), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(
                MessageType.willYouBeLong, null, null, receiver), data);

        data.jPopupMenu.addSeparator();
        JMenuItem menuItem = new JMenuItem("Close menu");
        data.jPopupMenu.add(menuItem);
    }
}
