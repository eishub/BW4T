package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;

public class ChatMenu {
    /**
     * Build the pop up menu for sending chat messages to all players
     */
    public static void buildPopUpMenuForChat(BW4TClientGUI bw4tClientGUI) {
        bw4tClientGUI.getjPopupMenu().removeAll();

        BasicMenuOperations.addSectionTitleToPopupMenu("Answer:", bw4tClientGUI.getjPopupMenu());

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.YES), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.NO), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.IDONOTKNOW), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.OK), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.IDO), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.IDONOT), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.WAIT), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.ONTHEWAY), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.ALMOSTTHERE), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.FARAWAY), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.DELAYED), bw4tClientGUI);

        bw4tClientGUI.getjPopupMenu().addSeparator();
        JMenuItem menuItem = new JMenuItem("Close menu");
        bw4tClientGUI.getjPopupMenu().add(menuItem);

    }
}
