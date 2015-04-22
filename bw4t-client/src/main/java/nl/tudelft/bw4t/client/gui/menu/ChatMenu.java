package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageType;

/** Responsible for building the pop-up menu of the chat. */
public final class ChatMenu {
    
    /** Should never be instantiated. */
    private ChatMenu() { }
    
    /**
     * Used for building the pop-up menu that displays the chat actions a user can perform.
     * 
     * @param gui
     *            - The {@link BW4TClientGUI} to create the pop-up menu on.
     */
    public static void buildPopUpMenuForChat(BW4TClientGUI gui) {
        gui.getjPopupMenu().removeAll();

        BasicMenuOperations.addSectionTitleToPopupMenu("Answer:", gui.getjPopupMenu());

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.YES), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.NO), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.IDONOTKNOW), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.OK), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.IDO), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.IDONOT), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.WAIT), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.ONTHEWAY), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.ALMOSTTHERE), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.FARAWAY), gui);
        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.DELAYED), gui);

        gui.getjPopupMenu().addSeparator();
        JMenuItem menuItem = new JMenuItem("Close menu");
        gui.getjPopupMenu().add(menuItem);

    }
}
