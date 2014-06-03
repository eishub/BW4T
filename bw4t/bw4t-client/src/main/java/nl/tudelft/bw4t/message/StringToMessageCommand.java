package nl.tudelft.bw4t.message;

import java.util.StringTokenizer;

/*
 * TODO: (Tom) I think this structure can be made better by using Abstracts
 * This to avoid having to set type every time.
 */

/**
 * Interface for converting different types of messages
 * from {@link String} to {@link BW4TMessage} format.
 */
public interface StringToMessageCommand {
	
	/**
	 * Takes in a message in {@link String} format 
	 * and outputs it in {@link BW4TMessage} format.
	 * 
	 * @param message 
	 *            - The String message to be translated.
	 * @return The message in BW4TMessage format.
	 */
    BW4TMessage exec(String message);
}

/**
 * Used for converting messages with no special traits (only a type).
 */
class CommandType implements StringToMessageCommand {
    private final MessageType type;

    public CommandType(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        return new BW4TMessage(type);
    }
}

/**
 * Used for converting messages with only a room trait.
 */
class CommandRoom implements StringToMessageCommand {
	private final MessageType type;

    public CommandRoom(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        return new BW4TMessage(type,
        		MessageTranslator.findRoomId(message),
        		MessageTranslator.findColorId(message),
        		Integer.MAX_VALUE);
    }
}

/**
 * Used for converting messages with only a color trait.
 */
class CommandColor implements StringToMessageCommand {
	private final MessageType type;

    public CommandColor(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        return new BW4TMessage(type,
        		MessageTranslator.findRoomId(message),
        		MessageTranslator.findColorId(message),
        		Integer.MAX_VALUE);
    }
}

/**
 * Used for converting messages with room and color traits.
 */
class CommandRoomColor implements StringToMessageCommand {
	private final MessageType type;

    public CommandRoomColor(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        return new BW4TMessage(type,
        		MessageTranslator.findRoomId(message),
        		MessageTranslator.findColorId(message),
                Integer.MAX_VALUE);
    }
}

/**
 * Used for converting messages with room, color and playerId traits.
 */
class CommandRoomColorPlayer implements StringToMessageCommand {
	private final MessageType type;

    public CommandRoomColorPlayer(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        String playerId = new StringTokenizer(message, ", ").nextToken();
        return new BW4TMessage(type,
        		MessageTranslator.findRoomId(message),
        		MessageTranslator.findColorId(message),
                playerId);
    }
}

/**
 * Used for converting messages with room, color and number traits.
 */
class CommandRoomColorNumber implements StringToMessageCommand {
	private final MessageType type;

    public CommandRoomColorNumber(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        return new BW4TMessage(type,
        		MessageTranslator.findRoomId(message),
        		MessageTranslator.findColorId(message),
                MessageTranslator.findNumber(message));
    }
}

/**
 * Used for converting messages with only a playerId trait.
 * In this case, PlayerId is identified via a "by" before the name.
 */
class CommandContainsBy implements StringToMessageCommand {
	private MessageType type;

    public CommandContainsBy(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
    	String playerId = null;
        if (message.contains("by")) {
            String[] splitMessage = message.split(" ");
            playerId = splitMessage[splitMessage.length - 1];
        }
        return new BW4TMessage(type,
        		MessageTranslator.findRoomId(message), 
        		MessageTranslator.findColorId(message),
        		playerId);
    }
}

/**
 * Used for converting messages with only number trait.
 */
class CommandContains implements StringToMessageCommand {
	private MessageType type;

    @Override
    public BW4TMessage exec(String message) {
        int number = MessageTranslator.findNumber(message);
        if (number == Integer.MAX_VALUE) {
            type = MessageType.ROOMCONTAINS;
        } else {
            type = MessageType.ROOMCONTAINSAMOUNT;
        }
        return new BW4TMessage(type, MessageTranslator.findRoomId(message), MessageTranslator.findColorId(message),
                number);
    }
}
