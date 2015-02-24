package nl.tudelft.bw4t.client.environment.handlers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.iilang.Action;

import java.util.HashSet;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.environment.ActionHandler;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

//@Ignore("Not yet implemented.")
@RunWith(MockitoJUnitRunner.class)
public class ActionHandlerTest {
    
    @Mock
    private RemoteEnvironment remoteEnvironment;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPerformActionDelegated() throws ActException, AgentException {
        LinkedList<String> agentsList = new LinkedList<String>();
        agentsList.add("testAgent");
        HashSet<String> entitySet = new HashSet<String>();
        entitySet.add("testEntity");
        String agent = "testAgent";
        
        when(remoteEnvironment.getAgents()).thenReturn(agentsList);
        when(remoteEnvironment.isSupportedByEnvironment(any(Action.class))).thenReturn(true);
        when(remoteEnvironment.getAssociatedEntities(agent)).thenReturn(entitySet);
        when(remoteEnvironment.isSupportedByType(any(Action.class), any(String.class))).thenReturn(true);
        when(remoteEnvironment.isSupportedByEntity(any(Action.class), any(String.class))).thenReturn(true);
        
        String entities = "testEntity";
        ActionHandler.performActionDelegated(agent, new Action("test"), remoteEnvironment, entities);
    }

}
