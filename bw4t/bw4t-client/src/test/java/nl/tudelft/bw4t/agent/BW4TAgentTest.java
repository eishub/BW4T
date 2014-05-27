package nl.tudelft.bw4t.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

import java.rmi.RemoteException;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eis.exceptions.ActException;
import eis.iilang.Action;

@RunWith(MockitoJUnitRunner.class)
public class BW4TAgentTest {

	@Mock
	private RemoteEnvironment remoteEnvironment;
	private BW4TAgent testAgent;

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
	public void testpickUp() throws ActException, RemoteException {
		testAgent.pickUp();
		verify(remoteEnvironment).performEntityAction(eq("Entity1"), any(Action.class));
	}

	@Test(expected = Exception.class)
	public void testPickUpThrowsAct() throws ActException, RemoteException {
		when(remoteEnvironment.performEntityAction(eq("Entity1"), any(Action.class))).thenThrow(new ActException(""));
		testAgent.pickUp();
	}

	@Test(expected = Exception.class)
	public void testPickUpRemThrowsRemote() throws ActException, RemoteException {
		when(remoteEnvironment.performEntityAction(eq("Entity1"), any(Action.class))).thenThrow(new RemoteException(""));
		testAgent.pickUp();
	}

}
