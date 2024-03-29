package nl.tudelft.bw4t.client.environment.handlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.iilang.Action;
import nl.tudelft.bw4t.client.environment.ActionHandler;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

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
        List<String> agentsList = new LinkedList<>();
        agentsList.add("testAgent");
        Set<String> entitySet = new HashSet<>(1);
        entitySet.add("testEntity");
        String agent = "testAgent";
        
        when(remoteEnvironment.getAgents()).thenReturn(agentsList);
        when(remoteEnvironment.isSupportedByEnvironment(any())).thenReturn(true);
        when(remoteEnvironment.getAssociatedEntities(agent)).thenReturn(entitySet);
        when(remoteEnvironment.isSupportedByType(any(), any())).thenReturn(true);
        when(remoteEnvironment.isSupportedByEntity(any(), any())).thenReturn(true);
        
        String entities = "testEntity";
        ActionHandler.performActionDelegated(agent, new Action("test"), remoteEnvironment, entities);
    }

}
