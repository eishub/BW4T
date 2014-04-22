package nl.tudelft.bw4t.message;

import java.util.StringTokenizer;

import nl.tudelft.bw4t.map.ColorTranslator;
import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

/**
 * Class used for translating messages (String->BW4TMessage and
 * BW4TMessage->String)
 * 
 * @author trens
 * 
 */
public class MessageTranslator {

	/**
	 * Translate a message (String) to a message (BW4TMessage)
	 * 
	 * @param message
	 *            , the message that should be translated
	 * @return the translated message
	 */
	public static BW4TMessage translateMessage(String message) {
		BW4TMessage translatedMessage = null;
		String room = null;
		String color = null;
		String playerId = null;
		int number = Integer.MAX_VALUE;
		MessageType type = null;

		if (message.contains("I am going to ")) {
			room = findRoomId(message);
			color = findColorId(message);
			type = MessageType.goingToRoom;
		} else if (message.contains("I have a ")) {
			room = findRoomId(message);
			color = findColorId(message);
			type = MessageType.hasColor;
		} else if (message.contains("has been checked")) {
			room = findRoomId(message);
			if (message.contains("by")) {
				String[] splitMessage = message.split(" ");
				playerId = splitMessage[splitMessage.length - 1];
			}
			type = MessageType.checked;
		} else if (message.contains("contains")) {
			room = findRoomId(message);
			color = findColorId(message);
			number = findNumber(message);
			if (number == Integer.MAX_VALUE)
				type = MessageType.roomContains;
			else
				type = MessageType.roomContainsAmount;
		} else if (message.contains("empty")) {
			room = findRoomId(message);
			type = MessageType.roomIsEmpty;
		} else if (message.contains("Is anybody going to")) {
			room = findRoomId(message);
			type = MessageType.isAnybodyGoingToRoom;
		} else if (message.contains("Who has a ")) {
			color = findColorId(message);
			type = MessageType.whoHasABlock;
		} else if (message.contains("We need")) {
			color = findColorId(message);
			type = MessageType.weNeed;
		} else if (message.contains("I am looking for")) {
			color = findColorId(message);
			type = MessageType.lookingFor;
		} else if (message.contains("I will get")) {
			color = findColorId(message);
			type = MessageType.willGetColor;
		} else if (message.contains("I am getting")) {
			room = findRoomId(message);
			color = findColorId(message);
			type = MessageType.amGettingColor;
		} else if (message.contains("go to")) {
			room = findRoomId(message);
			StringTokenizer st = new StringTokenizer(message, ", ");
			playerId = st.nextToken();
			type = MessageType.goToRoom;
		} else if (message.contains("find a ")) {
			color = findColorId(message);
			StringTokenizer st = new StringTokenizer(message, ", ");
			playerId = st.nextToken();
			type = MessageType.findColor;
		} else if (message.contains("get the ")) {
			room = findRoomId(message);
			color = findColorId(message);
			StringTokenizer st = new StringTokenizer(message, ", ");
			playerId = st.nextToken();
			type = MessageType.getColorFromRoom;
		} else if (message.contains("Where should I go")) {
			type = MessageType.whereShouldIGo;
		} else if (message.contains("What color should I ")) {
			type = MessageType.whatColorShouldIGet;
		} else if (message.contains("Where is a ")) {
			color = findColorId(message);
			type = MessageType.whereIsColor;
		} else if (message.contains("What is in ")) {
			room = findRoomId(message);
			type = MessageType.whatIsInRoom;
		} else if (message.contains("Has anybody checked")) {
			room = findRoomId(message);
			type = MessageType.hasAnybodyCheckedRoom;
		} else if (message.contains("Who is in ")) {
			room = findRoomId(message);
			type = MessageType.whoIsInRoom;
		} else if (message.contains("I am in ")) {
			room = findRoomId(message);
			type = MessageType.inRoom;
		} else if (message.contains("I am about to drop ")) {
			color = findColorId(message);
			type = MessageType.aboutToDropOffBlock;
		} else if (message.contains("I just dropped off")) {
			color = findColorId(message);
			type = MessageType.droppedOffBlock;
		} else if (message.contains("I am waiting outside")) {
			room = findRoomId(message);
			type = MessageType.amWaitingOutsideRoom;
		} else if (message.contains("are you close")) {
			StringTokenizer st = new StringTokenizer(message, ", ");
			playerId = st.nextToken();
			type = MessageType.areYouClose;
		} else if (message.contains("will you be long")) {
			StringTokenizer st = new StringTokenizer(message, ", ");
			playerId = st.nextToken();
			type = MessageType.willYouBeLong;
		} else if (message.contains("I am at a")) {
			color = findColorId(message);
			type = MessageType.atBox;
		} else if (message.equals("yes")) {
			type = MessageType.yes;
		} else if (message.equals("no")) {
			type = MessageType.no;
		} else if (message.equals("I do")) {
			type = MessageType.iDo;
		} else if (message.equals("I don't")) {
			type = MessageType.iDoNot;
		} else if (message.equals("I don't know")) {
			type = MessageType.iDoNotKnow;
		} else if (message.equals("OK")) {
			room = findRoomId(message);
			type = MessageType.ok;
		} else if (message.equals("wait")) {
			type = MessageType.wait;
		} else if (message.equals("I am on the way")) {
			type = MessageType.onTheWay;
		} else if (message.equals("I am almost there")) {
			type = MessageType.almostThere;
		} else if (message.equals("I am far away")) {
			type = MessageType.farAway;
		} else if (message.equals("I am delayed")) {
			type = MessageType.delayed;
		} else {
			return null;
		}
		translatedMessage = new BW4TMessage(type, room, color, playerId, number);
		return translatedMessage;
	}

	/**
	 * Translate a message (BW4TMessage) to a message (String)
	 * 
	 * @param message
	 *            , the message that should be translated
	 * @return the translated message
	 */
	public static String translateMessage(BW4TMessage message) {
		if (message.getType() == MessageType.whereShouldIGo) {
			return "Where should I go?";
		} else if (message.getType() == MessageType.checked) {
			if (message.getPlayerId() == null) {
				return "room " + message.getRoom() + " has been checked";
			} else {
				return "room " + message.getRoom() + " has been checked by "
						+ message.getPlayerId();
			}
		} else if (message.getType() == MessageType.whatColorShouldIGet) {
			return "What color should I get?";
		} else if (message.getType() == MessageType.yes) {
			return "yes";
		} else if (message.getType() == MessageType.no) {
			return "no";
		} else if (message.getType() == MessageType.iDo) {
			return "I do";
		} else if (message.getType() == MessageType.iDoNot) {
			return "I don't";
		} else if (message.getType() == MessageType.iDoNotKnow) {
			return "I don't know";
		} else if (message.getType() == MessageType.ok
				&& message.getRoom() == null) {
			return "OK";
		} else if (message.getType() == MessageType.wait) {
			return "wait";
		} else if (message.getType() == MessageType.onTheWay) {
			return "I am on the way";
		} else if (message.getType() == MessageType.almostThere) {
			return "I am almost there";
		} else if (message.getType() == MessageType.farAway) {
			return "I am far away";
		} else if (message.getType() == MessageType.delayed) {
			return "I am delayed";
		} else if (message.getType() == MessageType.couldNot) {
			return "I couldn't";
		} else if (message.getType() == MessageType.goingToRoom) {
			return "I am going to room " + message.getRoom();
		} else if (message.getType() == MessageType.roomIsEmpty) {
			return "room " + message.getRoom() + " is empty";
		} else if (message.getType() == MessageType.isAnybodyGoingToRoom) {
			return "Is anybody going to room " + message.getRoom() + "?";
		} else if (message.getType() == MessageType.whatIsInRoom) {
			return "What is in room " + message.getRoom() + "?";
		} else if (message.getType() == MessageType.hasAnybodyCheckedRoom) {
			return "Has anybody checked room " + message.getRoom() + "?";
		} else if (message.getType() == MessageType.whoIsInRoom) {
			return "Who is in room " + message.getRoom() + "?";
		} else if (message.getType() == MessageType.inRoom) {
			return "I am in room " + message.getRoom();
		} else if (message.getType() == MessageType.amWaitingOutsideRoom) {
			return "I am waiting outside room " + message.getRoom();
		} else if (message.getType() == MessageType.ok
				&& message.getRoom() != null) {
			return "OK, room " + message.getRoom();
		} else if (message.getType() == MessageType.goingToRoom) {
			if (message.getColor() == null)
				return "I am going to room " + message.getRoom();
			else
				return "I am going to room " + message.getRoom() + " to get a "
						+ message.getColor() + "  block";
		} else if (message.getType() == MessageType.hasColor) {
			if (message.getRoom() == null)
				return "I have a " + message.getColor() + " block";
			else
				return "I have a " + message.getColor() + " block from room "
						+ message.getRoom();
		} else if (message.getType() == MessageType.whoHasABlock) {
			return "Who has a " + message.getColor() + " block?";
		} else if (message.getType() == MessageType.weNeed) {
			return "We need a " + message.getColor() + " block";
		} else if (message.getType() == MessageType.lookingFor) {
			return "I am looking for a " + message.getColor() + " block";
		} else if (message.getType() == MessageType.willGetColor) {
			if (message.getRoom() == null)
				return "I will get a " + message.getColor() + " block";
			else
				return "I will get a " + message.getColor()
						+ " block from room " + message.getRoom();
		} else if (message.getType() == MessageType.amGettingColor) {
			if (message.getRoom() == null)
				return "I am getting a " + message.getColor() + " block";
			else
				return "I am getting a " + message.getColor()
						+ " block from room " + message.getRoom();
		} else if (message.getType() == MessageType.whereIsColor) {
			return "Where is a " + message.getColor() + " block?";
		} else if (message.getType() == MessageType.aboutToDropOffBlock) {
			return "I am about to drop off a " + message.getColor() + " block";
		} else if (message.getType() == MessageType.droppedOffBlock) {
			if (message.getColor() == null)
				return "I just dropped off a block";
			else
				return "I just dropped off a " + message.getColor() + " block";
		} else if (message.getType() == MessageType.roomContains) {
			return "room " + message.getRoom() + " contains a "
					+ message.getColor() + " block";
		} else if (message.getType() == MessageType.roomContainsAmount) {
			return "room " + message.getRoom() + " contains "
					+ message.getNumber() + " " + message.getColor()
					+ " blocks";
		} else if (message.getType() == MessageType.atBox) {
			return "I am at a " + message.getColor() + " block";
		} else if (message.getType() == MessageType.amWaitingOutsideRoom) {
			return "I am waiting outside room " + message.getRoom()
					+ " with a " + message.getColor() + " block";
		} else if (message.getType() == MessageType.putDown) {
			return message.getPlayerId()
					+ ", put down the block you are holding";
		} else if (message.getType() == MessageType.goToRoom) {
			return message.getPlayerId() + ", go to room " + message.getRoom();
		} else if (message.getType() == MessageType.findColor) {
			return message.getPlayerId() + ", find a " + message.getColor();
		} else if (message.getType() == MessageType.getColorFromRoom) {
			return message.getPlayerId() + ", get the " + message.getColor()
					+ " from room " + message.getRoom();
		} else if (message.getType() == MessageType.areYouClose) {
			return message.getPlayerId() + ", are you close?";
		} else if (message.getType() == MessageType.willYouBeLong) {
			return message.getPlayerId() + " will you be long?";
		}

		return null;
	}

	/**
	 * Find a room id in a message. Note #1933, we can not look specifically for
	 * room names because we have no map loaded and don't know any navpoint
	 * names. Therefore the message must contain a segment "room XXX" and XXX
	 * then will be the room.
	 * 
	 * @param message
	 *            the message in which "room <room id>" should be found
	 * @return the room id or Long.MAX_VALUE if no room id found
	 */
	private static String findRoomId(String message) {
		StringTokenizer tokenizer = new StringTokenizer(message);
		String token = tokenizer.nextToken();

		while (tokenizer.hasMoreTokens()) {
			if (token.equals("room")) {
				return tokenizer.nextToken();
			}
			token = tokenizer.nextToken();
		}

		return null;
	}

	/**
	 * Find a color id in a message
	 * 
	 * @param message
	 *            , the message in which a color id should be found
	 * @return the color id or null if no color id found
	 */
	private static String findColorId(String message) {
		StringTokenizer tokenizer = new StringTokenizer(message);
		String token = tokenizer.nextToken();
		while (tokenizer.hasMoreTokens()) {
			for (String color : ColorTranslator.getAllColors()) {
				if (token.equals(color))
					return color;
			}

			token = tokenizer.nextToken();
		}
		return null;
	}

	private static int findNumber(String message) {
		StringTokenizer tokenizer = new StringTokenizer(message);
		while (tokenizer.hasMoreTokens()) {
			try {
				String token = tokenizer.nextToken();
				return Integer.parseInt(token);
			} catch (NumberFormatException e) {
				// unclear how to properly test and avoid this exception.
				// we just want to get the first parseable number and skip the
				// rest.
			}
		}
		return Integer.MAX_VALUE;
	}

	/**
	 * Translate a BW4TMessage to a LinkedList<Parameter> that can be sent to
	 * GOAL
	 * 
	 * @param message
	 *            , the message to be translated
	 * @param entityId
	 *            , the sender of the message
	 * @return the translated message
	 */
	public static Parameter translateMessage(BW4TMessage message,
			String entityId) {
		if (message.getType() == MessageType.whereShouldIGo) {
			return new Function("int", new Function("imp", new Function("in",
					new Identifier(entityId), new Identifier("unknown"))));
		} else if (message.getType() == MessageType.whatColorShouldIGet) {
			return new Function("int", new Function("imp", new Function(
					"holding", new Identifier(entityId), new Identifier(
							"unknown"))));
		} else if (message.getType() == MessageType.droppedOffBlock) {
			if (message.getColor() == null) {
				return new Function("putDown", new Identifier(entityId));
			} else {
				return new Function("putDown", new Identifier(entityId),
						new Identifier(message.getColor()));
			}
		} else if (message.getType() == MessageType.yes) {
			return new Identifier("yes");
		} else if (message.getType() == MessageType.no) {
			return new Identifier("no");

		} else if (message.getType() == MessageType.iDo) {
			return new Identifier("ido");

		} else if (message.getType() == MessageType.iDoNot) {
			return new Identifier("idont");
		} else if (message.getType() == MessageType.iDoNotKnow) {
			return new Identifier("dontknow");
		} else if (message.getType() == MessageType.ok
				&& message.getRoom() == null) {
			return new Identifier("ok");
		} else if (message.getType() == MessageType.wait) {
			return new Identifier("wait");
		} else if (message.getType() == MessageType.onTheWay) {
			return new Identifier("ontheway");
		} else if (message.getType() == MessageType.almostThere) {
			return new Identifier("almostthere");
		} else if (message.getType() == MessageType.farAway) {
			return new Identifier("faraway");
		} else if (message.getType() == MessageType.delayed) {
			return new Identifier("delayed");
		} else if (message.getType() == MessageType.couldNot) {
			return new Identifier("couldnot");
		} else if (message.getType() == MessageType.goingToRoom) {
			return new Function("imp", new Function("in", new Identifier(
					entityId), new Identifier(message.getRoom())));
		} else if (message.getType() == MessageType.roomIsEmpty) {
			return new Function("empty", new Identifier(message.getRoom()));
		} else if (message.getType() == MessageType.isAnybodyGoingToRoom) {
			return new Function("int", new Function("imp", new Function("in",
					new Identifier("unknown"),
					new Identifier(message.getRoom()))));
		} else if (message.getType() == MessageType.checked) {
			if (message.getPlayerId() == null) {
				return new Function("checked",
						new Identifier(message.getRoom()));
			} else {
				return new Function("checked", new Identifier(
						message.getPlayerId()), new Identifier(
						message.getRoom()));
			}
		} else if (message.getType() == MessageType.whatIsInRoom) {
			return new Function("int", new Function("at", new Identifier(
					"unknown"), new Identifier(message.getRoom())));
		} else if (message.getType() == MessageType.hasAnybodyCheckedRoom) {
			return new Function("int", new Function("checked", new Identifier(
					"unknown"), new Identifier(message.getRoom())));
		} else if (message.getType() == MessageType.whoIsInRoom) {
			return new Function("int", new Function("in", new Identifier(
					"unknown"), new Identifier(message.getRoom())));
		} else if (message.getType() == MessageType.inRoom) {
			return new Function("in", new Identifier(entityId), new Identifier(
					message.getRoom()));
		} else if (message.getType() == MessageType.amWaitingOutsideRoom) {
			return new Function("waitingOutside", new Identifier(entityId),
					new Identifier(message.getRoom()));
		} else if (message.getType() == MessageType.ok
				&& message.getRoom() != null) {
			return new Function("ok", new Identifier(message.getRoom()));
		} else if (message.getType() == MessageType.goingToRoom) {
			return new Function("imp", new Function("in", new Identifier(
					entityId), new Identifier(message.getRoom())));
		} else if (message.getType() == MessageType.hasColor) {
			if (message.getRoom() != null)
				return new Function("pickedUpFrom", new Identifier(entityId),
						new Identifier(message.getColor()), new Identifier(
								message.getRoom()));
			else
				return new Function("holding", new Identifier(entityId),
						new Identifier(message.getColor()));
		} else if (message.getType() == MessageType.whoHasABlock) {
			return new Function("int", new Function("holding", new Identifier(
					"unknown"), new Identifier(message.getColor())));
		} else if (message.getType() == MessageType.weNeed) {
			return new Function("need", new Identifier(message.getColor()));
		} else if (message.getType() == MessageType.lookingFor) {
			return new Function("imp", new Function("found", new Identifier(
					entityId), new Identifier(message.getColor())));
		} else if (message.getType() == MessageType.willGetColor) {
			return new Function("imp", new Function("holding", new Identifier(
					entityId), new Identifier(message.getColor())));
		} else if (message.getType() == MessageType.amGettingColor) {
			return new Function("imp", new Function("pickedUpFrom",
					new Identifier(entityId),
					new Identifier(message.getColor()), new Identifier(
							message.getRoom())));
		} else if (message.getType() == MessageType.whereIsColor) {
			return new Function("int", new Function("at", new Identifier(
					message.getColor()), new Identifier("unknown")));
		} else if (message.getType() == MessageType.aboutToDropOffBlock) {
			return new Function("imp", new Function("putDown", new Identifier(
					entityId)));
		} else if (message.getType() == MessageType.roomContains) {
			return new Function("at", new Identifier(message.getColor()),
					new Identifier(message.getRoom()));
		} else if (message.getType() == MessageType.roomContainsAmount) {
			return new Function("at", new Numeral(message.getNumber()),
					new Identifier(message.getColor()), new Identifier(
							message.getRoom()));
		} else if (message.getType() == MessageType.atBox) {
			return new Function("atBox", new Identifier(message.getColor()));
		} else if (message.getType() == MessageType.amWaitingOutsideRoom) {
			return new Function("waitingOutside", new Identifier(entityId),
					new Identifier(message.getRoom()));
		} else if (message.getType() == MessageType.putDown) {
			return new Function("imp", new Function("putDown", new Identifier(
					message.getPlayerId())));
		} else if (message.getType() == MessageType.goToRoom) {
			return new Function("imp", new Function("in", new Identifier(
					message.getPlayerId()), new Identifier(message.getRoom())));
		} else if (message.getType() == MessageType.findColor) {
			return new Function("imp", new Function("found", new Identifier(
					message.getPlayerId()), new Identifier(message.getColor())));
		} else if (message.getType() == MessageType.getColorFromRoom) {
			return new Function("imp", new Function("pickedUpFrom",
					new Identifier(message.getPlayerId()), new Identifier(
							message.getColor()), new Identifier(
							message.getRoom())));
		} else if (message.getType() == MessageType.areYouClose) {
			return new Function("int", new Function("areClose", new Identifier(
					message.getPlayerId())));
		} else if (message.getType() == MessageType.willYouBeLong) {
			return new Function("int", new Function("willBeLong",
					new Identifier(message.getPlayerId())));
		}

		return null;
	}

}
