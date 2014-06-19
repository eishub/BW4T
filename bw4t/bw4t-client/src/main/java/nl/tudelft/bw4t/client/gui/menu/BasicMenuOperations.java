package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import nl.tudelft.bw4t.client.agent.HumanAgent;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.client.gui.listeners.NavigateObstaclesActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PickUpActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PickUpEPartnerActionListener;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageTranslator;
import nl.tudelft.bw4t.map.view.ViewBlock;
import nl.tudelft.bw4t.map.view.ViewEPartner;
import nl.tudelft.bw4t.map.view.ViewEntity;

public class BasicMenuOperations {
    BW4TClientGUI bw4tClientMapRenderer;

    public BasicMenuOperations(BW4TClientGUI bw4tClientMapRenderer) {
        this.bw4tClientMapRenderer = bw4tClientMapRenderer;
    }

    /**
     * Adds a menu item to the pop up menu, used for messages
     *
     * @param message , the message that this item represents
     */
    public static void addMenuItemToPopupMenu(BW4TMessage message, BW4TClientGUI bw4tClientMapRendererData) {
        JMenuItem menuItem = new JMenuItem(MessageTranslator.translateMessage(message));
        menuItem.addActionListener(new MessageSenderActionListener(message, bw4tClientMapRendererData));
        bw4tClientMapRendererData.getjPopupMenu().add(menuItem);
    }

    /**
     * Adds a section title to the pop up menu
     *
     * @param title , the title of the section
     */
    public static void addSectionTitleToPopupMenu(String title, JPopupMenu jPopupMenu) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.setEnabled(false);
        jPopupMenu.add(menuItem);
    }

    /**
     * Adds a menu to the pop up menu
     *
     * @param text , the title of the menu
     * @return the menu
     */
    public static JMenu addSubMenuToPopupMenu(String text, JPopupMenu jPopupMenu) {
        JMenu menu = new JMenu(text);
        jPopupMenu.add(menu);
        return menu;
    }

    /**
     * Adds a menu item to the pop up menu that holds the option to navigate past obstacle,
     * but only if the "bumped" percept has been received.
     *
     * @param gui The client gui.
     */
    public static void addNavigateObstacleMenuItem(BW4TClientGUI gui) {
        ViewEntity theBot = gui.getController().getMapController().getTheBot();
        if (theBot.isCollided()) {
            JMenuItem menuItem = new JMenuItem("Navigate past obstacle");
            menuItem.addActionListener(new NavigateObstaclesActionListener(gui.getController()));

            gui.getjPopupMenu().add(menuItem);
        }
    }

    /**
     * Adds the option to pick up a box to the menu.
     *
     * @param gui The gui holding the menu.
     * @param box The box to be picked up.
     */
    public static void addBlockPickUpMenuItem(BW4TClientGUI gui, ViewBlock box) {
        if (gui.getController().getHumanAgent().canPickupAnotherObject(gui)) {
            String colorAsString = getColor(box.getColor().getName(),
                    gui.getController().getHumanAgent());
            JMenuItem menuItem = new JMenuItem("Pick up " + colorAsString + " block");
            menuItem.addActionListener(new PickUpActionListener(gui.getController()));
            gui.getjPopupMenu().add(menuItem);
        }
    }

    /**
     * Adds the option to pick up an e-partner to the menu.
     *
     * @param gui The gui holding the menu.
     */
    public static void addEPartnerPickUpMenuItem(BW4TClientGUI gui, ViewEPartner ep) {
        if (gui.getController().getHumanAgent().canPickupAnotherObject(gui)
                && !ep.isPickedUp()) {
            JMenuItem menuItem = new JMenuItem("Pick up e-partner");
            menuItem.addActionListener(new PickUpEPartnerActionListener(gui.getController(), gui));
            gui.getjPopupMenu().add(menuItem);
        }
    }

    /**
     * Gets the color while taking into account that the agent might be
     * color blind.
     *
     * @param color The color that is observed be the environment (but not
     *              necessarily by the agent.
     * @param agent The agent.
     * @return The color, unknown if the agent is colorblind.
     */
    public static String getColor(String color, HumanAgent agent) {
        if (agent.isColorBlind()) {
            if (Character.isUpperCase(color.charAt(0))) {
                return "Unknown";
            }
            return "unknown";
        }
        return color;
    }

}
