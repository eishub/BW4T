package nl.tudelft.bw4t.message;

public interface MessageCommand {
	
    BW4TMessage translatedMessage = null;
    String room = null;
    String color = null;
    String playerId = null;
    int number = Integer.MAX_VALUE;
    MessageType type = null;
    
	void exec(String message);
	
	
}

class CommandTemplate implements MessageCommand {
	
	@Override
	public void exec(String message) {
		// execute stuff here
	}
}

class CommandGoingToRoom implements MessageCommand {
	
	@Override
	public void exec(String message) {
        room = MessageTranslator.findRoomId(message);
        color = findColorId(message);
        type = MessageType.goingToRoom;
	}
}



