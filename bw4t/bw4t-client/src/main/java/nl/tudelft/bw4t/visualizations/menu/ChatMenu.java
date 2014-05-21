//package nl.tudelft.bw4t.visualizations.menu;
//
//import javax.swing.JMenuItem;
//
//import nl.tudelft.bw4t.message.BW4TMessage;
//import nl.tudelft.bw4t.message.MessageType;
//import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;
//
//public class ChatMenu {
//    public ChatMenu(BW4TClientMapRenderer bw4tClientMapRenderer) {
//        // TODO Auto-generated constructor stub
//    }
//    /**
//     * Build the pop up menu for sending chat messages to all players
//     */
//    public void buildPopUpMenuForChat() {
//        getjPopupMenu().removeAll();
//
//        addSectionTitleToPopupMenu("Answer:");
//
//        addMenuItemToPopupMenu(new BW4TMessage(MessageType.yes));
//        addMenuItemToPopupMenu(new BW4TMessage(MessageType.no));
//        addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDoNotKnow));
//        addMenuItemToPopupMenu(new BW4TMessage(MessageType.ok));
//        addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDo));
//        addMenuItemToPopupMenu(new BW4TMessage(MessageType.iDoNot));
//        addMenuItemToPopupMenu(new BW4TMessage(MessageType.wait));
//        addMenuItemToPopupMenu(new BW4TMessage(MessageType.onTheWay));
//        addMenuItemToPopupMenu(new BW4TMessage(MessageType.almostThere));
//        addMenuItemToPopupMenu(new BW4TMessage(MessageType.farAway));
//        addMenuItemToPopupMenu(new BW4TMessage(MessageType.delayed));
//
//        getjPopupMenu().addSeparator();
//        JMenuItem menuItem = new JMenuItem("Close menu");
//        getjPopupMenu().add(menuItem);
//
//    }
//}
