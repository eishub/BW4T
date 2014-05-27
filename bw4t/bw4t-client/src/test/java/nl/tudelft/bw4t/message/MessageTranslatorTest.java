package nl.tudelft.bw4t.message;
 
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
 
@RunWith(Parameterized.class)
public class MessageTranslatorTest {
 
        private static final String ROOM = "RoomC1";
        private static final String COLOR = "Red";
        private static final String AGENT = "Bot1";
        private static final int NUMBER = 5;
   
        String stringMessage;
        BW4TMessage bw4tMessage;
       
        public MessageTranslatorTest(String stringMessage, BW4TMessage bw4tMessage){
                this.stringMessage=stringMessage;
                this.bw4tMessage=bw4tMessage;
        }
        
        @Before
        public void init(){
        	MessageTranslator.init();
        }
        
        @Test
        public void testMapSize(){
        	//assertTrue(MessageTranslator.translator.size() > 0);
        	MessageTranslator.init();
        	assertTrue(MessageTranslator.translator.size() > 0);
        }
        
        @Test
        public void testNewMap(){
        	assertTrue(MessageTranslator.translateMessage(stringMessage).equals(MessageTranslator.translateMessageLegacy(stringMessage)));
        	assertFalse(MessageTranslator.translateMessage(stringMessage) == null);
        }
       
        @Test
        public void testStringToMessage(){
                assertTrue(MessageTranslator.translateMessageLegacy(stringMessage).equals(bw4tMessage));
        }
       
        @Test
        public void testMessageToString(){
                assertTrue(stringMessage.equals(MessageTranslator.translateMessage(bw4tMessage)));
               
        }
       
        @Parameters(name = "{index}: String: {0}, Message: {1}")
        public static Collection<Object[]> data(){
                return Arrays.asList(new Object[][]{
                                {"I am going to room "+ROOM,
                                new BW4TMessage(MessageType.goingToRoom, ROOM, null, null, Integer.MAX_VALUE)},
                                {"I have a "+COLOR+" block",
                                new BW4TMessage(MessageType.hasColor, null, COLOR, null, Integer.MAX_VALUE)},
                                {"room "+ROOM+" has been checked",
                                new BW4TMessage(MessageType.checked, ROOM, null, null, Integer.MAX_VALUE)},
                                {"room "+ROOM+" has been checked by "+AGENT,
                                new BW4TMessage(MessageType.checked, ROOM, null, AGENT, Integer.MAX_VALUE)},
                                {"room "+ROOM+" contains a "+COLOR+" block",
                                new BW4TMessage(MessageType.roomContains, ROOM, COLOR, null, Integer.MAX_VALUE)},
                                {"room "+ROOM+" contains "+NUMBER+" "+COLOR+" blocks",
                                new BW4TMessage(MessageType.roomContainsAmount, ROOM, COLOR, null, NUMBER)},
                                {"room "+ROOM+" is empty",
                                new BW4TMessage(MessageType.roomIsEmpty, ROOM, null, null, Integer.MAX_VALUE)},
                                {"Is anybody going to room "+ROOM+"?",
                                new BW4TMessage(MessageType.isAnybodyGoingToRoom, ROOM, null, null, Integer.MAX_VALUE)},
                                {"Who has a "+COLOR+" block"+"?",
                                new BW4TMessage(MessageType.whoHasABlock, null, COLOR, null, Integer.MAX_VALUE)},
                                {"We need a "+COLOR+" block",
                                new BW4TMessage(MessageType.weNeed, null, COLOR, null, Integer.MAX_VALUE)},
                                {"I am looking for a "+COLOR+" block",
                                new BW4TMessage(MessageType.lookingFor, null, COLOR, null, Integer.MAX_VALUE)},
                                {"I will get a "+COLOR+" block",
                                new BW4TMessage(MessageType.willGetColor, null, COLOR, null, Integer.MAX_VALUE)},
                                {"I am getting a "+COLOR+" block from room "+ROOM,
                                new BW4TMessage(MessageType.amGettingColor, ROOM, COLOR, null, Integer.MAX_VALUE)},
                                {AGENT+", go to room "+ROOM,
                                new BW4TMessage(MessageType.goToRoom, ROOM, null, AGENT, Integer.MAX_VALUE)},
                                {AGENT+", find a "+COLOR+" block",
                                new BW4TMessage(MessageType.findColor, null, COLOR, AGENT, Integer.MAX_VALUE)},
                                {AGENT+", get the "+COLOR+" from room "+ROOM,
                                new BW4TMessage(MessageType.getColorFromRoom, ROOM, COLOR, AGENT, Integer.MAX_VALUE)},
                                {"Where should I go?",
                                new BW4TMessage(MessageType.whereShouldIGo, null, null, null, Integer.MAX_VALUE)},
                                {"What color should I get?",
                                new BW4TMessage(MessageType.whatColorShouldIGet, null, null, null, Integer.MAX_VALUE)},
                                {"Where is a "+COLOR+" block?",
                                new BW4TMessage(MessageType.whereIsColor, null, COLOR, null, Integer.MAX_VALUE)},
                                {"What is in room "+ROOM+"?",
                                new BW4TMessage(MessageType.whatIsInRoom, ROOM, null, null, Integer.MAX_VALUE)},
                                {"Has anybody checked room "+ROOM+"?",
                                new BW4TMessage(MessageType.hasAnybodyCheckedRoom, ROOM, null, null, Integer.MAX_VALUE)},
                                {"Who is in room "+ROOM+"?",
                                new BW4TMessage(MessageType.whoIsInRoom, ROOM, null, null, Integer.MAX_VALUE)},
                                {"I am in room "+ROOM,
                                new BW4TMessage(MessageType.inRoom, ROOM, null, null, Integer.MAX_VALUE)},
                                {"I am about to drop off a "+COLOR+" block",
                                new BW4TMessage(MessageType.aboutToDropOffBlock, null, COLOR, null, Integer.MAX_VALUE)},
                                {"I just dropped off a "+COLOR+" block",
                                new BW4TMessage(MessageType.droppedOffBlock, null, COLOR, null, Integer.MAX_VALUE)},
                                {"I am waiting outside room "+ROOM,
                                new BW4TMessage(MessageType.amWaitingOutsideRoom, ROOM, null, null, Integer.MAX_VALUE)},
                                {AGENT+", are you close?",
                                new BW4TMessage(MessageType.areYouClose, null, null, AGENT, Integer.MAX_VALUE)},
                                {AGENT+", will you be long?",
                                new BW4TMessage(MessageType.willYouBeLong, null, null, AGENT, Integer.MAX_VALUE)},
                                {"I am at a "+COLOR+" block",
                                new BW4TMessage(MessageType.atBox, null, COLOR, null, Integer.MAX_VALUE)},
                                {"yes",
                                new BW4TMessage(MessageType.yes, null, null, null, Integer.MAX_VALUE)},
                                {"no",
                                new BW4TMessage(MessageType.no, null, null, null, Integer.MAX_VALUE)},
                                {"I do",
                                new BW4TMessage(MessageType.iDo, null, null, null, Integer.MAX_VALUE)},
                                {"I don't",
                                new BW4TMessage(MessageType.iDoNot, null, null, null, Integer.MAX_VALUE)},
                                {"I don't know",
                                new BW4TMessage(MessageType.iDoNotKnow, null, null, null, Integer.MAX_VALUE)},
                                {"OK",
                                new BW4TMessage(MessageType.ok, null, null, null, Integer.MAX_VALUE)},
                                {"wait",
                                new BW4TMessage(MessageType.wait, null, null, null, Integer.MAX_VALUE)},
                                {"I am on the way",
                                new BW4TMessage(MessageType.onTheWay, null, null, null, Integer.MAX_VALUE)},
                                {"I am almost there",
                                new BW4TMessage(MessageType.almostThere, null, null, null, Integer.MAX_VALUE)},
                                {"I am far away",
                                new BW4TMessage(MessageType.farAway, null, null, null, Integer.MAX_VALUE)},
                                {"I am delayed",
                                new BW4TMessage(MessageType.delayed, null, null, null, Integer.MAX_VALUE)}
                });
        }
}