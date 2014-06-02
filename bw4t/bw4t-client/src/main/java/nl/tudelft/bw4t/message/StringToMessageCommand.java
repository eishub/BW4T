package nl.tudelft.bw4t.message;

import java.util.StringTokenizer;

public interface MessageCommand {

    BW4TMessage exec(String message);

}

class CommandType implements MessageCommand {
    MessageType type;

    public CommandType(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        return new BW4TMessage(type);
    }
}

class CommandRoom implements MessageCommand {

    MessageType type;

    public CommandRoom(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        return new BW4TMessage(type, MessageTranslator.findRoomId(message), MessageTranslator.findColorId(message), Integer.MAX_VALUE);
    }
}

class CommandColor implements MessageCommand {

    MessageType type;

    public CommandColor(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        return new BW4TMessage(type, MessageTranslator.findRoomId(message), MessageTranslator.findColorId(message), Integer.MAX_VALUE);
    }
}

class CommandRoomColor implements MessageCommand {

    MessageType type;

    public CommandRoomColor(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        return new BW4TMessage(type, MessageTranslator.findRoomId(message), MessageTranslator.findColorId(message),
                Integer.MAX_VALUE);
    }
}

class CommandRoomColorPlayer implements MessageCommand {

    String message;
    MessageType type;
    String playerId;

    public CommandRoomColorPlayer(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        playerId = new StringTokenizer(message, ", ").nextToken();
        return new BW4TMessage(type, MessageTranslator.findRoomId(message), MessageTranslator.findColorId(message),
                playerId);
    }
}

class CommandRoomColorNumber implements MessageCommand {

    MessageType type;

    public CommandRoomColorNumber(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        return new BW4TMessage(type, MessageTranslator.findRoomId(message), MessageTranslator.findColorId(message),
                MessageTranslator.findNumber(message));
    }
}

class CommandContainsBy implements MessageCommand {

    MessageType type;
    String playerId;

    public CommandContainsBy(MessageType type) {
        this.type = type;
    }

    @Override
    public BW4TMessage exec(String message) {
        if (message.contains("by")) {
            String[] splitMessage = message.split(" ");
            playerId = splitMessage[splitMessage.length - 1];
        }

        return new BW4TMessage(type, MessageTranslator.findRoomId(message), MessageTranslator.findColorId(message), playerId);
    }
}

class CommandContains implements MessageCommand {
    
    MessageType type;

    public CommandContains() {
    }

    @Override
    public BW4TMessage exec(String message) {

        int number = MessageTranslator.findNumber(message);
        if (number == Integer.MAX_VALUE)
            type = MessageType.roomContains;
        else
            type = MessageType.roomContainsAmount;

        return new BW4TMessage(type, MessageTranslator.findRoomId(message), MessageTranslator.findColorId(message),
                number);
    }
}
