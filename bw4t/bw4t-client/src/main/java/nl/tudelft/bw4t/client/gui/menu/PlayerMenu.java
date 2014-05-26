package nl.tudelft.bw4t.client.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import nl.tudelft.bw4t.client.controller.ClientController;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.gui.listeners.MessageSenderActionListener;
import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.message.BW4TMessage;
import nl.tudelft.bw4t.message.MessageType;

public class PlayerMenu {
	/**
	 * Builds pop up menu for sending requests to a certain player, is called
	 * when a player button is pressed
	 * 
	 * @param playerId
	 *            , the playerId that the request should be sent to
	 */
	public static void buildPopUpMenuForRequests(String playerId, BW4TClientGUI gui) {
		ClientController cmc = gui.getController();
		gui.getjPopupMenu().removeAll();
		BasicMenuOperations.addSectionTitleToPopupMenu("Request:", gui.getjPopupMenu());

		// Check if the playerId is a specific player
		String receiver = "Somebody";
		if (!playerId.equalsIgnoreCase("all")) {
			receiver = playerId;
		}

		BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.putDown, null, null, receiver), gui);

		JMenu submenu = BasicMenuOperations.addSubMenuToPopupMenu(receiver + " go to room", gui.getjPopupMenu());

		for (Zone room : cmc.getRooms()) {
			JMenuItem menuItem = new JMenuItem(room.getName());
			menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(MessageType.goToRoom, room.getName(),
					null, receiver), gui.getController()));
			submenu.add(menuItem);
		}

		submenu = BasicMenuOperations.addSubMenuToPopupMenu(receiver + " find a color", gui.getjPopupMenu());

		for (String color : ColorTranslator.getAllColors()) {
			JMenuItem menuItem = new JMenuItem(color);
			menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(MessageType.findColor, null,
					color, receiver), gui.getController()));
			submenu.add(menuItem);
		}

		submenu = BasicMenuOperations
				.addSubMenuToPopupMenu(receiver + " get the color from room", gui.getjPopupMenu());

		for (String color : ColorTranslator.getAllColors()) {
			JMenu submenu2 = new JMenu(color);
			submenu.add(submenu2);

			for (Zone room : cmc.getRooms()) {
				JMenuItem menuItem = new JMenuItem(room.getName());
				menuItem.addActionListener(new MessageSenderActionListener(new BW4TMessage(
						MessageType.getColorFromRoom, room.getName(), color, receiver), gui.getController()));
				submenu2.add(menuItem);
			}
		}

		BasicMenuOperations.addSectionTitleToPopupMenu("Ask:", gui.getjPopupMenu());
		BasicMenuOperations
		.addMenuItemToPopupMenu(new BW4TMessage(MessageType.areYouClose, null, null, receiver), gui);
		BasicMenuOperations.addMenuItemToPopupMenu(new BW4TMessage(MessageType.willYouBeLong, null, null, receiver),
				gui);

		gui.getjPopupMenu().addSeparator();
		JMenuItem menuItem = new JMenuItem("Close menu");
		gui.getjPopupMenu().add(menuItem);
	}
}
