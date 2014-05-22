package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.gui.data.structures.BW4TClientInfo;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;

public class ChatMenu {
    /**
     * Build the pop up menu for sending chat messages to all players
     */
    public static void buildPopUpMenuForChat(BW4TClientInfo bw4tClientinfo) {
        bw4tClientinfo.jPopupMenu.removeAll();

        BasicMenuOperations.addSectionTitleToPopupMenu("Answer:", bw4tClientinfo.jPopupMenu);

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.yes), bw4tClientinfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.no), bw4tClientinfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDoNotKnow), bw4tClientinfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.ok), bw4tClientinfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDo), bw4tClientinfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDoNot), bw4tClientinfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.wait), bw4tClientinfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.onTheWay), bw4tClientinfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.almostThere), bw4tClientinfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.farAway), bw4tClientinfo);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.delayed), bw4tClientinfo);

        bw4tClientinfo.jPopupMenu.addSeparator();
        JMenuItem menuItem = new JMenuItem("Close menu");
        bw4tClientinfo.jPopupMenu.add(menuItem);

    }
}
