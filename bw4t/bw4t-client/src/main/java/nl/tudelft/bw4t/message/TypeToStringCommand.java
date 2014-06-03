package nl.tudelft.bw4t.message;

/**
 * Interface for converting different types of messages
 * from {@link BW4TMessage} to {@link String} format.
 */
public interface TypeToStringCommand {
	
	/**
	 * Takes in a message in {@link BW4TMessage} format 
	 * and outputs it in {@link String} format.
	 * 
	 * @param message 
	 *            - The BW4TMessage to be translated.
	 * @return The message in String format.
	 */
	String exec(BW4TMessage message);
}

class CommandString implements TypeToStringCommand {
	private final String translatedMessage;
	
    public CommandString(String string) {
    	translatedMessage = string;
    }
    
	@Override
	public String exec(BW4TMessage message) {
		return translatedMessage;
	}
}

class CommandChecked implements TypeToStringCommand {
	@Override
	public String exec(BW4TMessage message) {
		if (message.getPlayerId() == null) {
            return "room " + message.getRoom() + " has been checked";
        } else {
            return "room " + message.getRoom() + " has been checked by "
                    + message.getPlayerId();
        }
	}
}

class CommandOK implements TypeToStringCommand {
	@Override
	public String exec(BW4TMessage message) {
		if (message.getRoom() == null) {
            return "OK";
		} else if (message.getRoom() != null) { 
			return "OK, room " + message.getRoom();
		} else return null;
	}
}

class CommandAppendRoom implements TypeToStringCommand {
	private final String pre;
	
	public CommandAppendRoom(String pre) {
		this.pre = pre;
	}
	
	@Override
	public String exec(BW4TMessage message) {
		return pre + message.getRoom();
	}
}

class CommandRoomIsEmpty implements TypeToStringCommand {
	@Override
	public String exec(BW4TMessage message) {
		return "room " + message.getRoom() + " is empty";
	}
}

/**
 * Command to insert room between a given string and a question mark.
 * @author Sille
 *
 */
class CommandInsertRoom implements TypeToStringCommand {
	private final String pre;
	
	/**
	 * Constructor that sets the prepend string.
	 * @param pre
	 * 			the string that should be placed before the room.
	 */
	public CommandInsertRoom(String pre) {
		this.pre = pre;
	}

	@Override
	public String exec(BW4TMessage message) {
		return pre + message.getRoom() + "?";
	}
}

class CommandGoingToRoom implements TypeToStringCommand {
	@Override
	public String exec(BW4TMessage message) {
		if (message.getColor() == null) {
            return "I am going to room " + message.getRoom();
		} else {
            return "I am going to room " + message.getRoom() + " to get a "
                    + message.getColor() + "  block";
        }
	}
}

class CommandHasColor implements TypeToStringCommand {
	@Override
	public String exec(BW4TMessage message) {
		if (message.getRoom() == null) {
            return "I have a " + message.getColor() + " block";
		} else {
            return "I have a " + message.getColor() + " block from room "
                    + message.getRoom();
        }
	}
}



