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

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.yes), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.no), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDoNotKnow), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.ok), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDo), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDoNot), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.wait), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.onTheWay), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.almostThere), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.farAway), bw4tClientGUI);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.delayed), bw4tClientGUI);

        bw4tClientGUI.getjPopupMenu().addSeparator();
        JMenuItem menuItem = new JMenuItem("Close menu");
        bw4tClientGUI.getjPopupMenu().add(menuItem);

    }
}
