package nl.tudelft.bw4t.client.message;

import eis.iilang.Parameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import nl.tudelft.bw4t.map.ColorTranslator;

/**
 * Class used for translating messages (String->BW4TMessage and
 * BW4TMessage->String)
 */
public class MessageTranslator {
    
    /**
     * Should never
     */
    private MessageTranslator(){
        
    }
    private static final Map<String, StringToMessageCommand> stringToMessage = new HashMap<String, StringToMessageCommand>();
    private static final Map<String, StringToMessageCommand> stringToMessageEquals = new HashMap<String, StringToMessageCommand>();
    
    private static final Map<MessageType, MessageCommand> messageCommands = new HashMap<MessageType, MessageCommand>();
    
    static {
        
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
        stringToMessage.put("I want to go to ", new CommandRoom(MessageType.IWANTTOGO));
        stringToMessage.put("You forgot me in ", new CommandRoom(MessageType.YOUFORGOTME));
        
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
        
        messageCommands.put(MessageType.WHERESHOULDIGO, new CommandWhereShouldIGo());
        messageCommands.put(MessageType.CHECKED, new CommandChecked());
        messageCommands.put(MessageType.WHATCOLORSHOULDIGET, new CommandWhatColorShouldIGet());
        messageCommands.put(MessageType.YES, new CommandYes());
        messageCommands.put(MessageType.NO, new CommandNo());
        messageCommands.put(MessageType.IDO, new CommandIDo());
        messageCommands.put(MessageType.IDONOT, new CommandIDoNot());
        messageCommands.put(MessageType.IDONOTKNOW, new CommandIDoNotKnow());
        messageCommands.put(MessageType.OK, new CommandOk());
        messageCommands.put(MessageType.WAIT, new CommandWait());
        messageCommands.put(MessageType.ONTHEWAY, new CommandOnTheWay());
        messageCommands.put(MessageType.ALMOSTTHERE, new CommandAlmostThere());
        messageCommands.put(MessageType.FARAWAY, new CommandFarAway());
        messageCommands.put(MessageType.DELAYED, new CommandDelayed()); 
        messageCommands.put(MessageType.COULDNOT, new CommandCouldNot());
        messageCommands.put(MessageType.GOINGTOROOM, new CommandGoingToRoom());
        messageCommands.put(MessageType.ROOMISEMPTY, new CommandRoomIsEmpty());
        messageCommands.put(MessageType.ISANYBODYGOINGTOROOM, new CommandIsAnybodyGoingToRoom());
        messageCommands.put(MessageType.WHATISINROOM, new CommandWhatIsInRoom());
        messageCommands.put(MessageType.HASANYBODYCHECKEDROOM, new CommandHasAnybodyCheckedRoom());
        messageCommands.put(MessageType.WHOISINROOM, new CommandWhoIsInRoom());
        messageCommands.put(MessageType.INROOM, new CommandInRoom());
        messageCommands.put(MessageType.HASCOLOR, new CommandHasColor()); 
        messageCommands.put(MessageType.WHOHASABLOCK, new CommandWhoHasABlock());
        messageCommands.put(MessageType.WENEED, new CommandWeNeed());
        messageCommands.put(MessageType.LOOKINGFOR, new CommandLookingFor());
        messageCommands.put(MessageType.WILLGETCOLOR, new CommandWillGetColor());
        messageCommands.put(MessageType.AMGETTINGCOLOR, new CommandAmGettingColor());
        messageCommands.put(MessageType.WHEREISCOLOR, new CommandWhereIsColor());
        messageCommands.put(MessageType.ABOUTTODROPOFFBLOCK, new CommandAboutToDropOffBlock());
        messageCommands.put(MessageType.DROPPEDOFFBLOCK, new CommandDroppedOffBlock());
        messageCommands.put(MessageType.ROOMCONTAINS, new CommandRoomContains());
        messageCommands.put(MessageType.ROOMCONTAINSAMOUNT, new CommandRoomContainsAmount());
        messageCommands.put(MessageType.ATBOX, new CommandAtBox());
        messageCommands.put(MessageType.AMWAITINGOUTSIDEROOM, new CommandAmWaitingOutsideRoom());
        messageCommands.put(MessageType.PUTDOWN, new CommandPutDown());
        messageCommands.put(MessageType.GOTOROOM, new CommandGotoRoom());
        messageCommands.put(MessageType.FINDCOLOR, new CommandFindColor());
        messageCommands.put(MessageType.GETCOLORFROMROOM, new CommandGetColorFromRoom());
        messageCommands.put(MessageType.AREYOUCLOSE, new CommandAreYouClose());
        messageCommands.put(MessageType.WILLYOUBELONG, new CommandWillYouBeLong());
        messageCommands.put(MessageType.IWANTTOGO, new CommandEPartnerIWantToGo());
        messageCommands.put(MessageType.YOUFORGOTME, new CommandEPartnerYouForgotMe());
    }
    
    /**
     * Translate a message (String) to a message (BW4TMessage)
     * 
     * @param message
     *            , the message that should be translated
     * @return the translated message
     */
    public static BW4TMessage translateMessage(String message) {
            
        for (Entry<String, StringToMessageCommand> e : stringToMessageEquals.entrySet()) {
            String key = e.getKey();
            if (message.equals(key)) {
                return stringToMessageEquals.get(key).getMessage(message);
            }
        }
                        
        for (Entry<String, StringToMessageCommand> e : stringToMessage.entrySet()) {
            String key = e.getKey();
            if (message.contains(key)) {
                return stringToMessage.get(key).getMessage(message);
            }
        }
        return null; 
    }

    /**
     * Translate a message (BW4TMessage) to a message (String)
     *
     * @param message
     *            , the message that should be translated
     * @return the translated message
     */
    public static String translateMessage(BW4TMessage message) {
        
        return messageCommands.get(message.getType()).getString(message);
 
    }

    /**
     * Find a room id in a message. Note #1933, we can not look specifically for
     * room names because we have no map loaded and don't know any navpoint
     * names. Therefore the message must contain a segment "room abc" and abc
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
            if ("room".equals(token)) {
                return tokenizer.nextToken().replace("?", "");
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
                if (token.equals(color)) {
                    return color;
                }
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
        return messageCommands.get(message.getType()).getParam(message, entityId);
    }
}
