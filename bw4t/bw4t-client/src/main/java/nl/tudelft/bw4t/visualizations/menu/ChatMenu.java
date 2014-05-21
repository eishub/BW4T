package nl.tudelft.bw4t.visualizations.menu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;
import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;
import nl.tudelft.bw4t.visualizations.data.BW4TClientMapRendererData;

public class ChatMenu {
    /**
     * Build the pop up menu for sending chat messages to all players
     */
    public static void buildPopUpMenuForChat(BW4TClientMapRenderer bw4tClientMapRenderer) {
        BW4TClientMapRendererData data = bw4tClientMapRenderer.getData();
        data.jPopupMenu.removeAll();

        BasicMenuOperations.addSectionTitleToPopupMenu("Answer:", data.jPopupMenu);

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.yes), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.no), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDoNotKnow), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.ok), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDo), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDoNot), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.wait), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.onTheWay), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.almostThere), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.farAway), data);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.delayed), data);

        data.jPopupMenu.addSeparator();
        JMenuItem menuItem = new JMenuItem("Close menu");
        data.jPopupMenu.add(menuItem);

    }
}
