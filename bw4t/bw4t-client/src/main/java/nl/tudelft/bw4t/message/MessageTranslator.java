package nl.tudelft.bw4t.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
 */
public class MessageTranslator {
    public static Map<String, StringToMessageCommand> stringToMessage = new HashMap<String, StringToMessageCommand>();
    public static Map<String, StringToMessageCommand> stringToMessageEquals = new HashMap<String, StringToMessageCommand>();
    
    public static Map<MessageType, TypeToStringCommand> messageToString = new HashMap<MessageType, TypeToStringCommand>();
    
    public static void init(){
        
        stringToMessage.put("I am going to ", new CommandRoomColor(MessageType.GOINGTOROOM));
        stringToMessage.put("I have a ", new CommandRoomColor(MessageType.HASCOLOR));
        stringToMessage.put("has been checked", new CommandContainsBy(MessageType.CHECKED));
        stringToMessage.put("contains", new CommandContains());
        stringToMessage.put("empty", new CommandRoom(MessageType.ROOMISEMPTY));
        stringToMessage.put("Is anybody going to", new CommandRoom(MessageType.ISANYBODYGOINGTOROOM));
        stringToMessage.put("Who has a", new CommandColor(MessageType.WHOHASABLOCK));
        stringToMessage.put("We need", new CommandColor(MessageType.WENEED));
        stringToMessage.put("I am looking for", new CommandColor(MessageType.LOOKINGFOR));
        stringToMessage.put("I will get", new CommandColor(MessageType.WILLGETCOLOR));
        stringToMessage.put("I am getting", new CommandRoomColor(MessageType.AMGETTINGCOLOR));
        stringToMessage.put("go to", new CommandRoomColorPlayer(MessageType.GOTOROOM));
        stringToMessage.put("find a", new CommandRoomColorPlayer(MessageType.FINDCOLOR));
        stringToMessage.put("get the", new CommandRoomColorPlayer(MessageType.GETCOLORFROMROOM));
        stringToMessage.put("Where should I go", new CommandType(MessageType.WHERESHOULDIGO));
        stringToMessage.put("What color should I ", new CommandType(MessageType.WHATCOLORSHOULDIGET));
        stringToMessage.put("Where is a ", new CommandColor(MessageType.WHEREISCOLOR));
        stringToMessage.put("What is in ", new CommandRoom(MessageType.WHATISINROOM));
        stringToMessage.put("Has anybody checked", new CommandRoom(MessageType.HASANYBODYCHECKEDROOM));
        stringToMessage.put("Who is in ", new CommandRoom(MessageType.WHOISINROOM));
        stringToMessage.put("I am in ", new CommandRoom(MessageType.INROOM));
        stringToMessage.put("I am about to drop ", new CommandColor(MessageType.ABOUTTODROPOFFBLOCK));
        stringToMessage.put("I just dropped off", new CommandColor(MessageType.DROPPEDOFFBLOCK));
        stringToMessage.put("I am waiting outside", new CommandRoom(MessageType.AMWAITINGOUTSIDEROOM));
        stringToMessage.put("are you close", new CommandRoomColorPlayer(MessageType.AREYOUCLOSE));
        stringToMessage.put("will you be long", new CommandRoomColorPlayer(MessageType.WILLYOUBELONG));
        stringToMessage.put("I am at a", new CommandColor(MessageType.ATBOX));
        
        stringToMessageEquals.put("yes", new CommandType(MessageType.YES));
        stringToMessageEquals.put("no", new CommandType(MessageType.NO));
        stringToMessageEquals.put("I do", new CommandType(MessageType.IDO));
        stringToMessageEquals.put("I don't", new CommandType(MessageType.IDONOT));
        stringToMessageEquals.put("I don't know", new CommandType(MessageType.IDONOTKNOW));
        stringToMessageEquals.put("OK", new CommandRoom(MessageType.OK));
        stringToMessageEquals.put("wait", new CommandType(MessageType.WAIT));
        stringToMessageEquals.put("I am on the way", new CommandType(MessageType.ONTHEWAY));
        stringToMessageEquals.put("I am almost there", new CommandType(MessageType.ALMOSTTHERE));
        stringToMessageEquals.put("I am far away", new CommandType(MessageType.FARAWAY));
        stringToMessageEquals.put("I am delayed", new CommandType(MessageType.DELAYED));
        
        
    }
    
    /**
     * Translate a message (String) to a message (BW4TMessage)
     * 
     * @param message
     *            , the message that should be translated
     * @return the translated message
     */
    public static BW4TMessage translateMessage(String message){
            
        for(Entry<String, StringToMessageCommand> e : stringToMessageEquals.entrySet()){
            String key = e.getKey();
            if(message.equals(key)) {
                BW4TMessage msg = stringToMessageEquals.get(key).exec(message);
                return msg;
            }
        }
                        
        for(Entry<String, StringToMessageCommand> e : stringToMessage.entrySet()){
            String key = e.getKey();
            if(message.contains(key)) {
                return stringToMessage.get(key).exec(message);
            }
        }
        return null; 
    }
    
    /**
     * Translate a message (String) to a message (BW4TMessage)
     * 
     * @param message
     *            , the message that should be translated
     * @return the translated message
     */
    @Deprecated
    public static BW4TMessage translateMessageLegacy(String message) {
        BW4TMessage translatedMessage = null;
        String room = null;
        String color = null;
        String playerId = null;
        int number = Integer.MAX_VALUE;
        MessageType type = null;

        if (message.contains("I am going to ")) {
            room = findRoomId(message);
            color = findColorId(message);
            type = MessageType.GOINGTOROOM;
        } else if (message.contains("I have a ")) {
            room = findRoomId(message);
            color = findColorId(message);
            type = MessageType.HASCOLOR;
        } else if (message.contains("has been checked")) {
            room = findRoomId(message);
            if (message.contains("by")) {
                String[] splitMessage = message.split(" ");
                playerId = splitMessage[splitMessage.length - 1];
            }
            type = MessageType.CHECKED;
        } else if (message.contains("contains")) {
            room = findRoomId(message);
            color = findColorId(message);
            number = findNumber(message);
            if (number == Integer.MAX_VALUE)
                type = MessageType.ROOMCONTAINS;
            else
                type = MessageType.ROOMCONTAINSAMOUNT;
        } else if (message.contains("empty")) {
            room = findRoomId(message);
            type = MessageType.ROOMISEMPTY;
        } else if (message.contains("Is anybody going to")) {
            room = findRoomId(message);
            type = MessageType.ISANYBODYGOINGTOROOM;
        } else if (message.contains("Who has a ")) {
            color = findColorId(message);
            type = MessageType.WHOHASABLOCK;
        } else if (message.contains("We need")) {
            color = findColorId(message);
            type = MessageType.WENEED;
        } else if (message.contains("I am looking for")) {
            color = findColorId(message);
            type = MessageType.LOOKINGFOR;
        } else if (message.contains("I will get")) {
            color = findColorId(message);
            type = MessageType.WILLGETCOLOR;
        } else if (message.contains("I am getting")) {
            room = findRoomId(message);
            color = findColorId(message);
            type = MessageType.AMGETTINGCOLOR;
        } else if (message.contains("go to")) {
            room = findRoomId(message);
            StringTokenizer st = new StringTokenizer(message, ", ");
            playerId = st.nextToken();
            type = MessageType.GOTOROOM;
        } else if (message.contains("find a ")) {
            color = findColorId(message);
            StringTokenizer st = new StringTokenizer(message, ", ");
            playerId = st.nextToken();
            type = MessageType.FINDCOLOR;
        } else if (message.contains("get the ")) {
            room = findRoomId(message);
            color = findColorId(message);
            StringTokenizer st = new StringTokenizer(message, ", ");
            playerId = st.nextToken();
            type = MessageType.GETCOLORFROMROOM;
        } else if (message.contains("Where should I go")) {
            type = MessageType.WHERESHOULDIGO;
        } else if (message.contains("What color should I ")) {
            type = MessageType.WHATCOLORSHOULDIGET;
        } else if (message.contains("Where is a ")) {
            color = findColorId(message);
            type = MessageType.WHEREISCOLOR;
        } else if (message.contains("What is in ")) {
            room = findRoomId(message);
            type = MessageType.WHATISINROOM;
        } else if (message.contains("Has anybody checked")) {
            room = findRoomId(message);
            type = MessageType.HASANYBODYCHECKEDROOM;
        } else if (message.contains("Who is in ")) {
            room = findRoomId(message);
            type = MessageType.WHOISINROOM;
        } else if (message.contains("I am in ")) {
            room = findRoomId(message);
            type = MessageType.INROOM;
        } else if (message.contains("I am about to drop ")) {
            color = findColorId(message);
            type = MessageType.ABOUTTODROPOFFBLOCK;
        } else if (message.contains("I just dropped off")) {
            color = findColorId(message);
            type = MessageType.DROPPEDOFFBLOCK;
        } else if (message.contains("I am waiting outside")) {
            room = findRoomId(message);
            type = MessageType.AMWAITINGOUTSIDEROOM;
        } else if (message.contains("are you close")) {
            StringTokenizer st = new StringTokenizer(message, ", ");
            playerId = st.nextToken();
            type = MessageType.AREYOUCLOSE;
        } else if (message.contains("will you be long")) {
            StringTokenizer st = new StringTokenizer(message, ", ");
            playerId = st.nextToken();
            type = MessageType.WILLYOUBELONG;
        } else if (message.contains("I am at a")) {
            color = findColorId(message);
            type = MessageType.ATBOX;
        } else if (message.equals("yes")) {
            type = MessageType.YES;
        } else if (message.equals("no")) {
            type = MessageType.NO;
        } else if (message.equals("I do")) {
            type = MessageType.IDO;
        } else if (message.equals("I don't")) {
            type = MessageType.IDONOT;
        } else if (message.equals("I don't know")) {
            type = MessageType.IDONOTKNOW;
        } else if (message.equals("OK")) {
            room = findRoomId(message);
            type = MessageType.OK;
        } else if (message.equals("wait")) {
            type = MessageType.WAIT;
        } else if (message.equals("I am on the way")) {
            type = MessageType.ONTHEWAY;
        } else if (message.equals("I am almost there")) {
            type = MessageType.ALMOSTTHERE;
        } else if (message.equals("I am far away")) {
            type = MessageType.FARAWAY;
        } else if (message.equals("I am delayed")) {
            type = MessageType.DELAYED;
        } else {
            return null;
        }
        translatedMessage = new BW4TMessage(type, room, color, playerId, number);
        return translatedMessage;
    }
    
    public static String translateMessageMap(BW4TMessage message){
    	
    	messageToString.put(MessageType.WHERESHOULDIGO, new CommandString("Where should I go?"));
    	messageToString.put(MessageType.CHECKED, new CommandChecked());
    	messageToString.put(MessageType.WHATCOLORSHOULDIGET, new CommandString("What color should I get?"));
    	messageToString.put(MessageType.YES, new CommandString("yes"));
    	messageToString.put(MessageType.NO, new CommandString("no"));
    	messageToString.put(MessageType.IDO, new CommandString("I do"));
    	messageToString.put(MessageType.IDONOT, new CommandString("I don't"));
    	messageToString.put(MessageType.IDONOTKNOW, new CommandString("I don't know"));
    	messageToString.put(MessageType.OK, new CommandOK());
    	messageToString.put(MessageType.WAIT, new CommandString("wait"));
    	messageToString.put(MessageType.ONTHEWAY, new CommandString("I am on the way"));
    	messageToString.put(MessageType.ALMOSTTHERE, new CommandString("I am almost there"));
    	messageToString.put(MessageType.FARAWAY, new CommandString("I am far away"));
    	messageToString.put(MessageType.DELAYED, new CommandString("I am delayed"));
    	messageToString.put(MessageType.COULDNOT, new CommandString("I couldn't"));
    	messageToString.put(MessageType.GOINGTOROOM, new CommandAppendRoom("I am going to room "));
    	messageToString.put(MessageType.ROOMISEMPTY, new CommandRoomIsEmpty());
    	messageToString.put(MessageType.ISANYBODYGOINGTOROOM, new CommandInsertRoom("Is anybody going to room "));
    	messageToString.put(MessageType.WHATISINROOM, new CommandInsertRoom("What is in room "));
    	messageToString.put(MessageType.HASANYBODYCHECKEDROOM, new CommandInsertRoom("Has anybody checked room "));
    	messageToString.put(MessageType.WHOISINROOM, new CommandInsertRoom("Who is in room "));
    	messageToString.put(MessageType.INROOM, new CommandAppendRoom("I am in room "));
    	messageToString.put(MessageType.AMWAITINGOUTSIDEROOM, new CommandAppendRoom("I am waiting outside room "));
    	messageToString.put(MessageType.GOINGTOROOM, new CommandGoingToRoom());
    	messageToString.put(MessageType.HASCOLOR, new CommandHasColor());
    	
    	return null;
    }

    /**
     * Translate a message (BW4TMessage) to a message (String)
     *
     * @param message
     *            , the message that should be translated
     * @return the translated message
     * @deprecated
     * 			Replaced by function with map
     */
    @Deprecated
    public static String translateMessage(BW4TMessage message) {
        if (message.getType() == MessageType.WHERESHOULDIGO) {
            return "Where should I go?";
        } else if (message.getType() == MessageType.CHECKED) {
            if (message.getPlayerId() == null) {
                return "room " + message.getRoom() + " has been checked";
            } else {
                return "room " + message.getRoom() + " has been checked by "
                        + message.getPlayerId();
            }
        } else if (message.getType() == MessageType.WHATCOLORSHOULDIGET) {
            return "What color should I get?";
        } else if (message.getType() == MessageType.YES) {
            return "yes";
        } else if (message.getType() == MessageType.NO) {
            return "no";
        } else if (message.getType() == MessageType.IDO) {
            return "I do";
        } else if (message.getType() == MessageType.IDONOT) {
            return "I don't";
        } else if (message.getType() == MessageType.IDONOTKNOW) {
            return "I don't know";
        } else if (message.getType() == MessageType.OK
                && message.getRoom() == null) {
            return "OK";
        } else if (message.getType() == MessageType.WAIT) {
            return "wait";
        } else if (message.getType() == MessageType.ONTHEWAY) {
            return "I am on the way";
        } else if (message.getType() == MessageType.ALMOSTTHERE) {
            return "I am almost there";
        } else if (message.getType() == MessageType.FARAWAY) {
            return "I am far away";
        } else if (message.getType() == MessageType.DELAYED) {
            return "I am delayed";
        } else if (message.getType() == MessageType.COULDNOT) {
            return "I couldn't";
        } else if (message.getType() == MessageType.GOINGTOROOM) {
            return "I am going to room " + message.getRoom();
        } else if (message.getType() == MessageType.ROOMISEMPTY) {
            return "room " + message.getRoom() + " is empty";
        } else if (message.getType() == MessageType.ISANYBODYGOINGTOROOM) {
            return "Is anybody going to room " + message.getRoom() + "?";
        } else if (message.getType() == MessageType.WHATISINROOM) {
            return "What is in room " + message.getRoom() + "?";
        } else if (message.getType() == MessageType.HASANYBODYCHECKEDROOM) {
            return "Has anybody checked room " + message.getRoom() + "?";
        } else if (message.getType() == MessageType.WHOISINROOM) {
            return "Who is in room " + message.getRoom() + "?";
        } else if (message.getType() == MessageType.INROOM) {
            return "I am in room " + message.getRoom();
        } else if (message.getType() == MessageType.AMWAITINGOUTSIDEROOM) {
            return "I am waiting outside room " + message.getRoom();
        } else if (message.getType() == MessageType.OK
                && message.getRoom() != null) {
            return "OK, room " + message.getRoom();
        } else if (message.getType() == MessageType.GOINGTOROOM) {
            if (message.getColor() == null)
                return "I am going to room " + message.getRoom();
            else
                return "I am going to room " + message.getRoom() + " to get a "
                        + message.getColor() + "  block";
        } else if (message.getType() == MessageType.HASCOLOR) {
            if (message.getRoom() == null)
                return "I have a " + message.getColor() + " block";
            else
                return "I have a " + message.getColor() + " block from room "
                        + message.getRoom();
        } else if (message.getType() == MessageType.WHOHASABLOCK) {
            return "Who has a " + message.getColor() + " block?";
        } else if (message.getType() == MessageType.WENEED) {
            return "We need a " + message.getColor() + " block";
        } else if (message.getType() == MessageType.LOOKINGFOR) {
            return "I am looking for a " + message.getColor() + " block";
        } else if (message.getType() == MessageType.WILLGETCOLOR) {
            if (message.getRoom() == null)
                return "I will get a " + message.getColor() + " block";
            else
                return "I will get a " + message.getColor()
                        + " block from room " + message.getRoom();
        } else if (message.getType() == MessageType.AMGETTINGCOLOR) {
            if (message.getRoom() == null)
                return "I am getting a " + message.getColor() + " block";
            else
                return "I am getting a " + message.getColor()
                        + " block from room " + message.getRoom();
        } else if (message.getType() == MessageType.WHEREISCOLOR) {
            return "Where is a " + message.getColor() + " block?";
        } else if (message.getType() == MessageType.ABOUTTODROPOFFBLOCK) {
            return "I am about to drop off a " + message.getColor() + " block";
        } else if (message.getType() == MessageType.DROPPEDOFFBLOCK) {
            if (message.getColor() == null)
                return "I just dropped off a block";
            else
                return "I just dropped off a " + message.getColor() + " block";
        } else if (message.getType() == MessageType.ROOMCONTAINS) {
            return "room " + message.getRoom() + " contains a "
                    + message.getColor() + " block";
        } else if (message.getType() == MessageType.ROOMCONTAINSAMOUNT) {
            return "room " + message.getRoom() + " contains "
                    + message.getNumber() + " " + message.getColor()
                    + " blocks";
        } else if (message.getType() == MessageType.ATBOX) {
            return "I am at a " + message.getColor() + " block";
        } else if (message.getType() == MessageType.AMWAITINGOUTSIDEROOM) {
            return "I am waiting outside room " + message.getRoom()
                    + " with a " + message.getColor() + " block";
        } else if (message.getType() == MessageType.PUTDOWN) {
            return message.getPlayerId()
                    + ", put down the block you are holding";
        } else if (message.getType() == MessageType.GOTOROOM) {
            return message.getPlayerId() + ", go to room " + message.getRoom();
        } else if (message.getType() == MessageType.FINDCOLOR) {
            return message.getPlayerId() + ", find a " + message.getColor() + " block";
        } else if (message.getType() == MessageType.GETCOLORFROMROOM) {
            return message.getPlayerId() + ", get the " + message.getColor()
                    + " from room " + message.getRoom();
        } else if (message.getType() == MessageType.AREYOUCLOSE) {
            return message.getPlayerId() + ", are you close?";
        } else if (message.getType() == MessageType.WILLYOUBELONG) {
            return message.getPlayerId() + ", will you be long?";
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
    static String findRoomId(String message) {
        StringTokenizer tokenizer = new StringTokenizer(message);
        String token = tokenizer.nextToken();

        while (tokenizer.hasMoreTokens()) {
            if (token.equals("room")) {
                return tokenizer.nextToken().replace("?","");
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
    static String findColorId(String message) {
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

    protected static int findNumber(String message) {
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
        if (message.getType() == MessageType.WHERESHOULDIGO) {
            return new Function("int", new Function("imp", new Function("in",
                    new Identifier(entityId), new Identifier("unknown"))));
        } else if (message.getType() == MessageType.WHATCOLORSHOULDIGET) {
            return new Function("int", new Function("imp", new Function(
                    "holding", new Identifier(entityId), new Identifier(
                            "unknown"))));
        } else if (message.getType() == MessageType.DROPPEDOFFBLOCK) {
            if (message.getColor() == null) {
                return new Function("putDown", new Identifier(entityId));
            } else {
                return new Function("putDown", new Identifier(entityId),
                        new Identifier(message.getColor()));
            }
        } else if (message.getType() == MessageType.YES) {
            return new Identifier("yes");
        } else if (message.getType() == MessageType.NO) {
            return new Identifier("no");

        } else if (message.getType() == MessageType.IDO) {
            return new Identifier("ido");

        } else if (message.getType() == MessageType.IDONOT) {
            return new Identifier("idont");
        } else if (message.getType() == MessageType.IDONOTKNOW) {
            return new Identifier("dontknow");
        } else if (message.getType() == MessageType.OK
                && message.getRoom() == null) {
            return new Identifier("ok");
        } else if (message.getType() == MessageType.WAIT) {
            return new Identifier("wait");
        } else if (message.getType() == MessageType.ONTHEWAY) {
            return new Identifier("ontheway");
        } else if (message.getType() == MessageType.ALMOSTTHERE) {
            return new Identifier("almostthere");
        } else if (message.getType() == MessageType.FARAWAY) {
            return new Identifier("faraway");
        } else if (message.getType() == MessageType.DELAYED) {
            return new Identifier("delayed");
        } else if (message.getType() == MessageType.COULDNOT) {
            return new Identifier("couldnot");
        } else if (message.getType() == MessageType.GOINGTOROOM) {
            return new Function("imp", new Function("in", new Identifier(
                    entityId), new Identifier(message.getRoom())));
        } else if (message.getType() == MessageType.ROOMISEMPTY) {
            return new Function("empty", new Identifier(message.getRoom()));
        } else if (message.getType() == MessageType.ISANYBODYGOINGTOROOM) {
            return new Function("int", new Function("imp", new Function("in",
                    new Identifier("unknown"),
                    new Identifier(message.getRoom()))));
        } else if (message.getType() == MessageType.CHECKED) {
            if (message.getPlayerId() == null) {
                return new Function("checked",
                        new Identifier(message.getRoom()));
            } else {
                return new Function("checked", new Identifier(
                        message.getPlayerId()), new Identifier(
                        message.getRoom()));
            }
        } else if (message.getType() == MessageType.WHATISINROOM) {
            return new Function("int", new Function("at", new Identifier(
                    "unknown"), new Identifier(message.getRoom())));
        } else if (message.getType() == MessageType.HASANYBODYCHECKEDROOM) {
            return new Function("int", new Function("checked", new Identifier(
                    "unknown"), new Identifier(message.getRoom())));
        } else if (message.getType() == MessageType.WHOISINROOM) {
            return new Function("int", new Function("in", new Identifier(
                    "unknown"), new Identifier(message.getRoom())));
        } else if (message.getType() == MessageType.INROOM) {
            return new Function("in", new Identifier(entityId), new Identifier(
                    message.getRoom()));
        } else if (message.getType() == MessageType.AMWAITINGOUTSIDEROOM) {
            return new Function("waitingOutside", new Identifier(entityId),
                    new Identifier(message.getRoom()));
        } else if (message.getType() == MessageType.OK
                && message.getRoom() != null) {
            return new Function("ok", new Identifier(message.getRoom()));
        } else if (message.getType() == MessageType.GOINGTOROOM) {
            return new Function("imp", new Function("in", new Identifier(
                    entityId), new Identifier(message.getRoom())));
        } else if (message.getType() == MessageType.HASCOLOR) {
            if (message.getRoom() != null)
                return new Function("pickedUpFrom", new Identifier(entityId),
                        new Identifier(message.getColor()), new Identifier(
                                message.getRoom()));
            else
                return new Function("holding", new Identifier(entityId),
                        new Identifier(message.getColor()));
        } else if (message.getType() == MessageType.WHOHASABLOCK) {
            return new Function("int", new Function("holding", new Identifier(
                    "unknown"), new Identifier(message.getColor())));
        } else if (message.getType() == MessageType.WENEED) {
            return new Function("need", new Identifier(message.getColor()));
        } else if (message.getType() == MessageType.LOOKINGFOR) {
            return new Function("imp", new Function("found", new Identifier(
                    entityId), new Identifier(message.getColor())));
        } else if (message.getType() == MessageType.WILLGETCOLOR) {
            return new Function("imp", new Function("holding", new Identifier(
                    entityId), new Identifier(message.getColor())));
        } else if (message.getType() == MessageType.AMGETTINGCOLOR) {
            return new Function("imp", new Function("pickedUpFrom",
                    new Identifier(entityId),
                    new Identifier(message.getColor()), new Identifier(
                            message.getRoom())));
        } else if (message.getType() == MessageType.WHEREISCOLOR) {
            return new Function("int", new Function("at", new Identifier(
                    message.getColor()), new Identifier("unknown")));
        } else if (message.getType() == MessageType.ABOUTTODROPOFFBLOCK) {
            return new Function("imp", new Function("putDown", new Identifier(
                    entityId)));
        } else if (message.getType() == MessageType.ROOMCONTAINS) {
            return new Function("at", new Identifier(message.getColor()),
                    new Identifier(message.getRoom()));
        } else if (message.getType() == MessageType.ROOMCONTAINSAMOUNT) {
            return new Function("at", new Numeral(message.getNumber()),
                    new Identifier(message.getColor()), new Identifier(
                            message.getRoom()));
        } else if (message.getType() == MessageType.ATBOX) {
            return new Function("atBox", new Identifier(message.getColor()));
        } else if (message.getType() == MessageType.AMWAITINGOUTSIDEROOM) {
            return new Function("waitingOutside", new Identifier(entityId),
                    new Identifier(message.getRoom()));
        } else if (message.getType() == MessageType.PUTDOWN) {
            return new Function("imp", new Function("putDown", new Identifier(
                    message.getPlayerId())));
        } else if (message.getType() == MessageType.GOTOROOM) {
            return new Function("imp", new Function("in", new Identifier(
                    message.getPlayerId()), new Identifier(message.getRoom())));
        } else if (message.getType() == MessageType.FINDCOLOR) {
            return new Function("imp", new Function("found", new Identifier(
                    message.getPlayerId()), new Identifier(message.getColor())));
        } else if (message.getType() == MessageType.GETCOLORFROMROOM) {
            return new Function("imp", new Function("pickedUpFrom",
                    new Identifier(message.getPlayerId()), new Identifier(
                            message.getColor()), new Identifier(
                            message.getRoom())));
        } else if (message.getType() == MessageType.AREYOUCLOSE) {
            return new Function("int", new Function("areClose", new Identifier(
                    message.getPlayerId())));
        } else if (message.getType() == MessageType.WILLYOUBELONG) {
            return new Function("int", new Function("willBeLong",
                    new Identifier(message.getPlayerId())));
        }

        return null;
    }

}
