package nl.tudelft.bw4t.client.gui.menu;

import java.awt.Color;
import java.awt.Point;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import nl.tudelft.bw4t.client.controller.ClientMapController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.listeners.GotoPositionActionListener;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.client.gui.listeners.PutdownActionListener;
import nl.tudelft.bw4t.controller.MapRenderSettings;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.Block;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;

/** Responsible for building the pop-up menu when the user clicks on a hallway. */
public final class HallwayMenu {
	
	/** Should never be instantiated */
	private HallwayMenu() { }
	
    /**
     * Used for building the pop-up menu that displays actions 
     * a user can perform when clicking on a hallway.
     * 
     * @param gui
     *            - The {@link BW4TClientGUI} to create the pop-up menu on.
     */
    public static void buildPopUpMenuForHallway(BW4TClientGUI gui) {
        gui.getjPopupMenu().removeAll();
        ClientMapController cmc = gui.getController().getMapController();
        MapRenderSettings set = cmc.getRenderSettings();

        Block holdingID = cmc.getTheBot().getFirstHolding();
        Color entityColor = cmc.getTheBot().getColor();

        // Robot commands
        BasicMenuOperations.addSectionTitleToPopupMenu("Command my robot to: ", gui.getjPopupMenu());

        JMenuItem menuItem = new JMenuItem("Go to here");
        menuItem.addActionListener(new GotoPositionActionListener(new Point((int) (gui.getSelectedLocation().x / set
                .getScale()), (int) (gui.getSelectedLocation().y / set.getScale())), gui.getController()));
        gui.getjPopupMenu().add(menuItem);

        if (holdingID != null) {
            menuItem = new JMenuItem("Put down box");
            menuItem.addActionListener(new PutdownActionListener(gui.getController()));
            gui.getjPopupMenu().add(menuItem);
        }

        gui.getjPopupMenu().addSeparator();

        BasicMenuOperations.addSectionTitleToPopupMenu("Tell: ", gui.getjPopupMenu());

        for (Zone roomInfo : cmc.getRooms()) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.AMWAITINGOUTSIDEROOM, roomInfo.getName(), null, null), gui);
        }

        BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.AMWAITINGOUTSIDEROOM, cmc.getDropZone()
                .getName(), null, null), gui);

        if (holdingID != null) {
            BasicMenuOperations.addMenuItemToPopupMenu(
                    new BW4TMessage(MessageType.HASCOLOR, null, ColorTranslator.translate2ColorString(entityColor),
                            null), gui);

            JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(
                    "I have a " + ColorTranslator.translate2ColorString(entityColor) + " block from ",
                    gui.getjPopupMenu());

            for (Zone roomInfo : cmc.getRooms()) {
                menuItem = new JMenuItem(roomInfo.getName());
                menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(MessageType.HASCOLOR,
                        roomInfo.getName(), ColorTranslator.translate2ColorString(entityColor), null), gui
                        .getController()));
                submenu.add(menuItem);
            }
        }

        gui.getjPopupMenu().addSeparator();
        menuItem = new JMenuItem("Close menu");
        gui.getjPopupMenu().add(menuItem);
    }
}
