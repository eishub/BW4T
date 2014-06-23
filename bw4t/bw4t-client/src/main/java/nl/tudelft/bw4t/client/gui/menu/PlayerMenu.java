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

/** Responsible for building the pop-up menu for sending requests to a player. */
public final class PlayerMenu {
    
    /** Should never be instantiated */
    private PlayerMenu() { 
        
    }
    
    /**
     * Used for building the pop-up menu that displays actions 
     * a user can perform when clicking on another player.
     * 
     * @param playerId
     *            - The playerId that the request should be sent to.
     */
    public static void buildPopUpMenuForRequests(String playerId, ClientController controller) {
        BW4TClientGUI gui = controller.getGui();
        ClientMapController cmc = gui.getController().getMapController();
        gui.getjPopupMenu().removeAll();
        BasicMenuOperations.addSectionTitleToPopupMenu("Request:", gui.getjPopupMenu());

        // Check if the playerId is a specific player
        String receiver = "Somebody";
        if (!"all".equalsIgnoreCase(playerId)) {
            receiver = playerId;
        }

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.PUTDOWN, null, null, receiver), gui);

        JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(receiver + " go to room", gui.getjPopupMenu());

        for (Zone room : cmc.getRooms()) {
            JMenuItem menuItem = new JMenuItem(room.getName());
            menuItem.addActionListener(new MessageSenderActionListener(
                                            new BW4TMessage(MessageType.GOTOROOM, room.getName(),
                    null, receiver), controller));
            submenu.add(menuItem);
        }

        submenu = BasicMenuOperations.addSubMenuToPopupMenu(receiver + " find a color", gui.getjPopupMenu());

        for (String color : ColorTranslator.getAllColors()) {
            JMenuItem menuItem = new JMenuItem(color);
            menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(MessageType.FINDCOLOR, null,
                    color, receiver), controller));
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
                        MessageType.GETCOLORFROMROOM, room.getName(), color, receiver), controller));
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
