package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageTranslator;

/** Utility class containing some operations to add various things to pop-up menu's. */
public final class BasicMenuOperations {
	
	/** Should never be instantiated */
	private BasicMenuOperations() { }

    /**
     * Adds a menu item to a pop-up menu, used for messages.
     * 
     * @param message
     *            - The message that this item represents.
     * @param gui
     *            - The {@link BW4TClientGUI} to create the pop-up menu on.
     */
    public static void addMenuItemToPopupMenu(BW4TMessage message, BW4TClientGUI gui) {
        JMenuItem menuItem = new JMenuItem(MessageTranslator.translateMessage(message));
        menuItem.addActionListener(new MessageSenderActionListener(message, gui.getController()));
        gui.getjPopupMenu().add(menuItem);
    }

    /**
     * Adds a section title to a pop-up menu.
     * 
     * @param title
     *            - The title of the section to add.
     * @param jPopupMenu
     *            - The {@link JPopupMenu} to add the section to.
     */
    public static void addSectionTitleToPopupMenu(String title, JPopupMenu jPopupMenu) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.setEnabled(false);
        jPopupMenu.add(menuItem);
    }

    /**
     * Adds a submenu to a pop-up menu.
     * 
     * @param text
     *            - The title of the submenu to add.
     * @param jPopupMenu
     *            - The {@link JPopupMenu} to add the menu to.
     * @return The newly added submenu.
     */
    public static JMenu addSubMenuToPopupMenu(String text, JPopupMenu jPopupMenu) {
        JMenu menu = new JMenu(text);
        jPopupMenu.add(menu);
        return menu;
    }
}
