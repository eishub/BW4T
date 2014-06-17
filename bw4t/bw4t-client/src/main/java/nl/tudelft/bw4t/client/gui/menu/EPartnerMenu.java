package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.View;

import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.listeners.DropEPartnerActionListener;
import nl.tudelft.bw4t.client.gui.listeners.EPartnerMessageSenderActionListener;
import nl.tudelft.bw4t.client.gui.listeners.GoToBlockActionListener;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PickUpActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PickUpEPartnerActionListener;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageType;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.ViewEPartner;

public class EPartnerMenu {

    public static void buildPopUpMenuForEPartner(ViewEPartner ep, BW4TClientGUI gui) {
        final JPopupMenu popUpMenu = gui.getjPopupMenu();
        popUpMenu.removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:", popUpMenu);

        JMenuItem menuItem = new JMenuItem("Drop e-partner");
        menuItem.addActionListener(new DropEPartnerActionListener(gui.getController(), gui));
        popUpMenu.add(menuItem);
        
        popUpMenu.addSeparator();
        
        // EPartner commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Inform e-partner that:", popUpMenu);

        JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu("I am going to ", gui.getjPopupMenu());
        
        for (Zone room : gui.getController().getMapController().getRooms()) {
            menuItem = new JMenuItem(room.getName());
            menuItem.addActionListener(new EPartnerMessageSenderActionListener(
                    new BW4TMessage(MessageType.IWANTTOGO, room.getName(), "", 0), gui));
            submenu.add(menuItem);
        }
        
        popUpMenu.addSeparator();
        popUpMenu.add(new JMenuItem("Close menu"));
    }

    public static void buildPopUpMenuPickUpEPartner(ViewEPartner ep, BW4TClientGUI gui) {
        gui.getjPopupMenu().removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:", gui.getjPopupMenu());

        BasicMenuOperations.addEPartnerPickUpMenuItem(gui, ep);

        gui.getjPopupMenu().addSeparator();
        gui.getjPopupMenu().add(new JMenuItem("Close menu"));
    }

    public static void buildPopUpMenuMoveToEPartner(ViewEPartner ep, BW4TClientGUI gui) {
        gui.getjPopupMenu().removeAll();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to:", gui.getjPopupMenu());

        JMenuItem menuItem = new JMenuItem("Go to e-partner");
        menuItem.addActionListener(new GoToBlockActionListener(ep.getId(), gui.getController()));
        gui.getjPopupMenu().add(menuItem);

        gui.getjPopupMenu().addSeparator();
        gui.getjPopupMenu().add(new JMenuItem("Close menu"));
    }

}
