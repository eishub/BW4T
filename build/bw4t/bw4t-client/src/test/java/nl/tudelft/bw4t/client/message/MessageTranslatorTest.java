package nl.tudelft.bw4t.client.message;

import static org.junit.Assert.assertTrue;

import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
 
/**
 * Tests if the MessageTranslator class is working correctly.
 * Because the class used to be very cluttered, it makes sure the test works on both
 * the old and new methods, to make sure that the new refactored method is working correctly.
 */
@RunWith(Parameterized.class)
public class MessageTranslatorTest {
 
        private static final String ROOM = "RoomC1";
        private static final String COLOR = "Red";
        private static final String AGENT = "Bot1";
        private static final int NUMBER = 5;
        private static final Identifier ROOM_ID = new Identifier(ROOM);
        private static final Identifier COLOR_ID = new Identifier(COLOR);
        private static final Identifier AGENT_ID = new Identifier(AGENT);
        private static final Identifier UNKNOWN_ID = new Identifier("unknown");
        private static final Numeral NUMBER_ID = new Numeral(NUMBER);
   
        private final String stringMessage;
        private final BW4TMessage bw4tMessage;
        private final Parameter param;
        
        /**
         * Initialize the Message Translator class
         */
        @Before
        public void init() {
        }

        /**
         * Set the parameters to test
         * @param stringMessage
         * @param bw4tMessage
         * @param param
         */
        public MessageTranslatorTest(String stringMessage, BW4TMessage bw4tMessage,
                Parameter param) {
            this.stringMessage = stringMessage;
            this.bw4tMessage = bw4tMessage;
            this.param = param;
        }
        
        /**
         * Tests whether the old function converts the messages from String->Message correctly.
         */
        @Test
        public void testStringToMessage() {
            assertTrue(MessageTranslator.translateMessage(stringMessage).equals(bw4tMessage));
        }
        
        /**
         * Tests whether the old function converts the message from Message->String correctly.
         */
        @Test
        public void testMessageToString() {
            assertTrue(MessageTranslator.translateMessage(bw4tMessage).equals(stringMessage));
        }
        
        /**
         * Tests whether the old function converts the message from Message->Parameter correctly.
         */
        @Test
        public void testMessageToParameter() {
            assertTrue(MessageTranslator.translateMessage(bw4tMessage, AGENT).equals(param));
        }
       
       
        /**
         * Returns the list of parameters to test on.
         * @return A list of parameters in the form of {StringMessage, BW4TMessage, Parameter}
         */
        @Parameters(name = "{index}: String: {0}, Message: {1}")
        public static Collection<Object[]> data() {
                return Arrays.asList(new Object[][]{
                    {"I am going to room " + ROOM,
                    new BW4TMessage(MessageType.GOINGTOROOM, ROOM, null, null, Integer.MAX_VALUE),
                    new Function("imp", new Function("in", AGENT_ID, ROOM_ID))},
                    
                    {"I have a " + COLOR + " block",
                    new BW4TMessage(MessageType.HASCOLOR, null, COLOR, null, Integer.MAX_VALUE),
                    new Function("holding", AGENT_ID, COLOR_ID)},
                    
                    {"room " + ROOM + " has been checked",
                    new BW4TMessage(MessageType.CHECKED, ROOM, null, null, Integer.MAX_VALUE),
                    new Function("checked", ROOM_ID)},
                    
                    {"room " + ROOM + " has been checked by " + AGENT,
                    new BW4TMessage(MessageType.CHECKED, ROOM, null, AGENT, Integer.MAX_VALUE),
                    new Function("checked", AGENT_ID, ROOM_ID)},
                    
                    {"room " + ROOM + " contains a " + COLOR + " block",
                    new BW4TMessage(MessageType.ROOMCONTAINS, ROOM, COLOR, null, Integer.MAX_VALUE),
                    new Function("at", COLOR_ID, ROOM_ID)},
                    
                    {"room " + ROOM + " contains " + NUMBER + " " + COLOR + " blocks",
                    new BW4TMessage(MessageType.ROOMCONTAINSAMOUNT, ROOM, COLOR, null, NUMBER),
                    new Function("at", NUMBER_ID, COLOR_ID, ROOM_ID)},
                    
                    {"room " + ROOM + " is empty",
                    new BW4TMessage(MessageType.ROOMISEMPTY, ROOM, null, null, Integer.MAX_VALUE),
                    new Function("empty", ROOM_ID)},
                    
                    {"Is anybody going to room " + ROOM + "?",
                    new BW4TMessage(MessageType.ISANYBODYGOINGTOROOM, ROOM, null, null, Integer.MAX_VALUE),
                    new Function("int", new Function("imp", new Function("in", UNKNOWN_ID, ROOM_ID)))},
                    
                    {"Who has a " + COLOR + " block" + "?",
                    new BW4TMessage(MessageType.WHOHASABLOCK, null, COLOR, null, Integer.MAX_VALUE),
                    new Function("int", new Function("holding", UNKNOWN_ID, COLOR_ID))},
                    
                    {"We need a " + COLOR + " block",
                    new BW4TMessage(MessageType.WENEED, null, COLOR, null, Integer.MAX_VALUE),
                    new Function("need", COLOR_ID)},
                    
                    {"I am looking for a " + COLOR + " block",
                    new BW4TMessage(MessageType.LOOKINGFOR, null, COLOR, null, Integer.MAX_VALUE),
                    new Function("imp", new Function("found", AGENT_ID, COLOR_ID))},
                    
                    {"I will get a " + COLOR + " block",
                    new BW4TMessage(MessageType.WILLGETCOLOR, null, COLOR, null, Integer.MAX_VALUE),
                    new Function("imp", new Function("holding", AGENT_ID, COLOR_ID))},
                    
                    {"I am getting a " + COLOR + " block from room " + ROOM,
                    new BW4TMessage(MessageType.AMGETTINGCOLOR, ROOM, COLOR, null, Integer.MAX_VALUE),
                    new Function("imp", new Function("pickedUpFrom", AGENT_ID, COLOR_ID, ROOM_ID))},
                    
                    {AGENT + ", go to room " + ROOM,
                    new BW4TMessage(MessageType.GOTOROOM, ROOM, null, AGENT, Integer.MAX_VALUE),
                    new Function("imp", new Function("in", AGENT_ID, ROOM_ID))},
                    
                    {AGENT + ", find a " + COLOR + " block",
                    new BW4TMessage(MessageType.FINDCOLOR, null, COLOR, AGENT, Integer.MAX_VALUE),
                    new Function("imp", new Function("found", AGENT_ID, COLOR_ID))},
                    
                    {AGENT + ", get the " + COLOR + " from room " + ROOM,
                    new BW4TMessage(MessageType.GETCOLORFROMROOM, ROOM, COLOR, AGENT, Integer.MAX_VALUE),
                    new Function("imp", new Function("pickedUpFrom", AGENT_ID, COLOR_ID, ROOM_ID))},
                    
                    {"Where should I go?",
                    new BW4TMessage(MessageType.WHERESHOULDIGO, null, null, null, Integer.MAX_VALUE),
                    new Function("int", new Function("imp", new Function("in", AGENT_ID, new Identifier("unknown"))))},
                    
                    {"What color should I get?",
                    new BW4TMessage(MessageType.WHATCOLORSHOULDIGET, null, null, null, Integer.MAX_VALUE),
                    new Function("int", new Function("imp", new Function("holding", AGENT_ID, new Identifier("unknown"))))},
                    
                    {"Where is a " + COLOR + " block?",
                    new BW4TMessage(MessageType.WHEREISCOLOR, null, COLOR, null, Integer.MAX_VALUE),
                    new Function("int", new Function("at", COLOR_ID, UNKNOWN_ID))},
                    
                    {"What is in room " + ROOM + "?",
                    new BW4TMessage(MessageType.WHATISINROOM, ROOM, null, null, Integer.MAX_VALUE),
                    new Function("int", new Function("at", UNKNOWN_ID, ROOM_ID))},
                    
                    {"Has anybody checked room " + ROOM + "?",
                    new BW4TMessage(MessageType.HASANYBODYCHECKEDROOM, ROOM, null, null, Integer.MAX_VALUE),
                    new Function("int", new Function("checked", UNKNOWN_ID, ROOM_ID))},
                    
                    {"Who is in room " + ROOM + "?",
                    new BW4TMessage(MessageType.WHOISINROOM, ROOM, null, null, Integer.MAX_VALUE),
                    new Function("int", new Function("in", UNKNOWN_ID, ROOM_ID))},
                    
                    {"I am in room " + ROOM,
                    new BW4TMessage(MessageType.INROOM, ROOM, null, null, Integer.MAX_VALUE),
                    new Function("in", AGENT_ID, ROOM_ID)},
                    
                    {"I am about to drop off a " + COLOR + " block",
                    new BW4TMessage(MessageType.ABOUTTODROPOFFBLOCK, null, COLOR, null, Integer.MAX_VALUE),
                    new Function("imp", new Function("putDown", AGENT_ID))},
                    
                    {"I just dropped off a " + COLOR + " block",
                    new BW4TMessage(MessageType.DROPPEDOFFBLOCK, null, COLOR, null, Integer.MAX_VALUE),
                    new Function("putDown", AGENT_ID, COLOR_ID)},
                    
                    {"I am waiting outside room " + ROOM,
                    new BW4TMessage(MessageType.AMWAITINGOUTSIDEROOM, ROOM, null, null, Integer.MAX_VALUE),
                    new Function("waitingOutside", AGENT_ID, ROOM_ID)},
                    
                    {AGENT + ", are you close?",
                    new BW4TMessage(MessageType.AREYOUCLOSE, null, null, AGENT, Integer.MAX_VALUE),
                    new Function("int", new Function("areClose", AGENT_ID))},
                    
                    {AGENT + ", will you be long?",
                    new BW4TMessage(MessageType.WILLYOUBELONG, null, null, AGENT, Integer.MAX_VALUE),
                    new Function("int", new Function("willBeLong", AGENT_ID))},
                    
                    {"I am at a " + COLOR + " block",
                    new BW4TMessage(MessageType.ATBOX, null, COLOR, null, Integer.MAX_VALUE),
                    new Function("atBox", COLOR_ID)},
                    
                    {"yes",
                    new BW4TMessage(MessageType.YES, null, null, null, Integer.MAX_VALUE),
                    new Identifier("yes")},
                    
                    {"no",
                    new BW4TMessage(MessageType.NO, null, null, null, Integer.MAX_VALUE),
                    new Identifier("no")},
                    
                    {"I do",
                    new BW4TMessage(MessageType.IDO, null, null, null, Integer.MAX_VALUE),
                    new Identifier("ido")},
                    
                    {"I don't",
                    new BW4TMessage(MessageType.IDONOT, null, null, null, Integer.MAX_VALUE),
                    new Identifier("idont")},
                    
                    {"I don't know",
                    new BW4TMessage(MessageType.IDONOTKNOW, null, null, null, Integer.MAX_VALUE),
                    new Identifier("dontknow")},
                    
                    {"OK",
                    new BW4TMessage(MessageType.OK, null, null, null, Integer.MAX_VALUE),
                    new Identifier("ok")},
                    
                    {"wait",
                    new BW4TMessage(MessageType.WAIT, null, null, null, Integer.MAX_VALUE),
                    new Identifier("wait")},
                    
                    {"I am on the way",
                    new BW4TMessage(MessageType.ONTHEWAY, null, null, null, Integer.MAX_VALUE),
                    new Identifier("ontheway")},
                    
                    {"I am almost there",
                    new BW4TMessage(MessageType.ALMOSTTHERE, null, null, null, Integer.MAX_VALUE),
                    new Identifier("almostthere")},
                    
                    {"I am far away",
                    new BW4TMessage(MessageType.FARAWAY, null, null, null, Integer.MAX_VALUE),
                    new Identifier("faraway")},
                    
                    {"I am delayed",
                    new BW4TMessage(MessageType.DELAYED, null, null, null, Integer.MAX_VALUE),
                    new Identifier("delayed")}
                });
        }
}
