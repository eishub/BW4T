package nl.tudelft.bw4t.visualizations.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageTranslator;
import nl.tudelft.bw4t.visualizations.BW4TClientMapRenderer;
import nl.tudelft.bw4t.visualizations.data.BW4TClientMapRendererData;
import nl.tudelft.bw4t.visualizations.listeners.MessageSenderActionListener;

public class BasicMenuOperations {
    BW4TClientMapRenderer bw4tClientMapRenderer;
    public BasicMenuOperations(BW4TClientMapRenderer bw4tClientMapRenderer) {
        this.bw4tClientMapRenderer = bw4tClientMapRenderer;
    }
    /**
     * Adds a menu item to the pop up menu, used for messages
     * 
     * @param message
     *            , the message that this item represents
     */
    public static void addMenuItemToPopupMenu(BW4TMessage message, BW4TClientMapRendererData bw4tClientMapRendererData) {
        JMenuItem menuItem = new JMenuItem(
                MessageTranslator.translateMessage(message));
        menuItem.addActionListener(new MessageSenderActionListener(message, bw4tClientMapRendererData));
        bw4tClientMapRendererData.jPopupMenu.add(menuItem);
    }
    
    /**
     * Adds a section title to the pop up menu
     * 
     * @param title
     *            , the title of the section
     */
    public static void addSectionTitleToPopupMenu(String title, JPopupMenu jPopupMenu) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.setEnabled(false);
        jPopupMenu.add(menuItem);
    }
    
    /**
     * Adds a menu to the pop up menu
     * 
     * @param text
     *            , the title of the menu
     * @return the menu
     */
    public static JMenu addSubMenuToPopupMenu(String text, JPopupMenu jPopupMenu) {
        JMenu menu = new JMenu(text);
        jPopupMenu.add(menu);
        return menu;
    }
}
