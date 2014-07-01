package nl.tudelft.bw4t.client.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import eis.eis2java.exception.TranslationException;
import eis.exceptions.ActException;
import eis.iilang.Action;

import java.rmi.RemoteException;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.message.BW4TMessage;
import nl.tudelft.bw4t.client.message.MessageType;
import nl.tudelft.bw4t.map.Zone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BW4TAgentTest {

    @Mock
    private RemoteEnvironment remoteEnvironment;
    private BW4TAgent testAgent;
    
    @Captor
    ArgumentCaptor<Action> captorAction;

    @Before
    public void setUp() throws Exception {
        testAgent = new BW4TAgent("Agent1", remoteEnvironment);
        testAgent.registerEntity("Entity1");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAgentConstructor() {
        assertNotNull(testAgent);
        assertEquals(testAgent.getAgentId(), "Agent1");
        assertEquals(remoteEnvironment, testAgent.getEnvironment());
    }

    @Test
    public void testAgentSetGet() {
        testAgent.registerEntity("Entity1");
        assertEquals("Entity1", testAgent.entityId);
    }

    @Test
    public void testKilledEnvironment() {
        assertFalse(testAgent.environmentKilled);
        testAgent.run();
        testAgent.setKilled();
        assertTrue(testAgent.environmentKilled);
        testAgent.run();
    }
    
    @Test
    public void testGoTo() throws ActException, RemoteException, TranslationException{
        testAgent.goTo(2.0,3.0);
        verify(remoteEnvironment).performEntityAction(any(String.class), captorAction.capture());
        assertEquals("2.0", captorAction.getValue().getParameters().get(0).toProlog());
        assertEquals("3.0", captorAction.getValue().getParameters().get(1).toProlog());
    }
    
    @Test
    public void testGoToNavPoint() throws ActException, RemoteException, TranslationException{
        testAgent.goTo(Zone.DROP_ZONE_NAME);
        verify(remoteEnvironment).performEntityAction(any(String.class), captorAction.capture());
        assertEquals("goTo", captorAction.getValue().getName());
        assertEquals(Zone.DROP_ZONE_NAME, captorAction.getValue().getParameters().get(0).toProlog());
    }

    @Test
    public void testPickUp() throws ActException, RemoteException {
        testAgent.pickUp();
        verify(remoteEnvironment).performEntityAction(eq("Entity1"), captorAction.capture());
        assertEquals("pickUp", captorAction.getValue().getName());
    }
    @Test
    public void testPutDown() throws ActException, RemoteException {
        testAgent.putDown();
        verify(remoteEnvironment).performEntityAction(eq("Entity1"), captorAction.capture());
        assertEquals("putDown", captorAction.getValue().getName());
    }
    
    @Test
    public void testgoToBlock() throws ActException, RemoteException {
        testAgent.goToBlock(123);
        verify(remoteEnvironment).performEntityAction(eq("Entity1"), captorAction.capture());
        assertEquals("goToBlock", captorAction.getValue().getName());
        assertEquals("123", captorAction.getValue().getParameters().get(0).toProlog());
    }
    
    @Test
    public void testSendGenericMessage() throws ActException, RemoteException {
        testAgent.sendMessage("Receiver", new BW4TMessage(MessageType.ALMOSTTHERE));
        verify(remoteEnvironment).performEntityAction(eq("Entity1"), captorAction.capture());
        assertEquals("sendMessage", captorAction.getValue().getName());
        assertEquals("Receiver", captorAction.getValue().getParameters().get(0).toProlog());
        assertEquals("I am almost there", captorAction.getValue().getParameters().get(1).toProlog());
    }

    @Test(expected = ActException.class)
    public void testPickUpThrowsAct() throws ActException, RemoteException {
        when(remoteEnvironment.performEntityAction(eq("Entity1"), any(Action.class))).thenThrow(new ActException(""));
        testAgent.pickUp();
    }

    @Test(expected = ActException.class)
    public void testPickUpRemThrowsRemote() throws ActException, RemoteException {
        when(remoteEnvironment.performEntityAction(eq("Entity1"), any(Action.class))).thenThrow(new RemoteException(""));
        testAgent.pickUp();
    }
    
    @Test(expected = ActException.class)
    public void testGoToEnvThrows() throws RemoteException, ActException {
        when(remoteEnvironment.performEntityAction(any(String.class), any(Action.class))).thenThrow(new RemoteException());
        testAgent.goTo(2.0,3.0);
    }
    
    @Test(expected = ActException.class)
    public void testGoToBlockEnvThrows() throws RemoteException, ActException {
        when(remoteEnvironment.performEntityAction(any(String.class), any(Action.class))).thenThrow(new RemoteException());
        testAgent.goToBlock(123);
    }
    
    @Test(expected = ActException.class)
    public void testSendMessageEnvThrows() throws RemoteException, ActException {
        when(remoteEnvironment.performEntityAction(any(String.class), any(Action.class))).thenThrow(new RemoteException());
        testAgent.sendMessage("receiver", new BW4TMessage(MessageType.AREYOUCLOSE));
    }
    
    @Test(expected = ActException.class)
    public void testGoToNavPointEnvThrows() throws RemoteException, ActException {
        when(remoteEnvironment.performEntityAction(any(String.class), any(Action.class))).thenThrow(new RemoteException());
        testAgent.goTo(Zone.DROP_ZONE_NAME);
    }
    
    @Test(expected = ActException.class)
    public void testputDownEnvThrows() throws RemoteException, ActException {
        when(remoteEnvironment.performEntityAction(any(String.class), any(Action.class))).thenThrow(new RemoteException());
        testAgent.putDown();
    }

}
