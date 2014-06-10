package nl.tudelft.bw4t.message;

public interface TypeToStringCommand {
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
	String translatedMessage;
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

/**
 * Command to insert color between two given strings
 * @author Sille
 *
 */
class CommandInsertColor implements TypeToStringCommand {
	
	private final String pre;
	private final String post;
	
	/**
	 * Constructor that sets the prepend string.
	 * @param pre
	 * 			the string that should be placed before the room.
	 */
	public CommandInsertColor(String pre, String post) {
		this.pre = pre;
		this.post = post;
	}

	@Override
	public String exec(BW4TMessage message) {
		return pre + message.getColor() + post;
	}
}

class CommandPrefixPlayerID implements TypeToStringCommand {
	private final String post;
	
	public CommandPrefixPlayerID(String post) {
		this.post = post;
	}

	@Override
	public String exec(BW4TMessage message) {
		 return message.getPlayerId()
                 + post;
	}
}

// Specific Commands

class CommandGoingToRoom implements TypeToStringCommand {

	@Override
	public String exec(BW4TMessage message) {
		if (message.getColor() == null) {
            return "I am going to room " + message.getRoom();
		}
        else {
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
		}
        else {
            return "I have a " + message.getColor() + " block from room "
                    + message.getRoom();
        }
	}
}

class CommandWillGetColor implements TypeToStringCommand {

	@Override
	public String exec(BW4TMessage message) {
		 if (message.getRoom() == null){
             return "I will get a " + message.getColor() + " block";
		 } else {
             return "I will get a " + message.getColor()
                     + " block from room " + message.getRoom();
		 }
	}
}

class CommandAmGettingColor implements TypeToStringCommand {

	@Override
	public String exec(BW4TMessage message) {
        if (message.getRoom() == null){
            return "I am getting a " + message.getColor() + " block";
        } else {
            return "I am getting a " + message.getColor()
                    + " block from room " + message.getRoom();
        }
	}
}

class CommandDroppedOffBlock implements TypeToStringCommand {

	@Override
	public String exec(BW4TMessage message) {
        if (message.getColor() == null) {
            return "I just dropped off a block";
        } else {
            return "I just dropped off a " + message.getColor() + " block";
        }
	}
}

class CommandAmWaitingOutsideRoom implements TypeToStringCommand {
	private final String ret = "I am waiting outside room ";

	@Override
	public String exec(BW4TMessage message) {
		if (message.getColor() != null) {
		 return ret + message.getRoom()
                 + " with a " + message.getColor() + " block";
		} else {
			return ret + message.getRoom();
		}
	}
}

class CommandRoomContains implements TypeToStringCommand {

	@Override
	public String exec(BW4TMessage message) {
		if (message.getType() == MessageType.ROOMCONTAINS) {
		return "room " + message.getRoom() + " contains a "
                + message.getColor() + " block";
		} else if (message.getType() == MessageType.ROOMCONTAINSAMOUNT) {
			return "room " + message.getRoom() + " contains "
                    + message.getNumber() + " " + message.getColor()
                    + " blocks";
		} else {
			return null;
		}
		
	}
}

class CommandGotoRoom implements TypeToStringCommand {

	@Override
	public String exec(BW4TMessage message) {
		return message.getPlayerId() + ", go to room " + message.getRoom();
	}
}

class CommandFindColor implements TypeToStringCommand {

	@Override
	public String exec(BW4TMessage message) {
		 return message.getPlayerId() + ", find a " + message.getColor() + " block";
	}
}

class CommandGetColorFromRoom implements TypeToStringCommand {

	@Override
	public String exec(BW4TMessage message) {
		return message.getPlayerId() + ", get the " + message.getColor()
                + " from room " + message.getRoom();
	}
}


