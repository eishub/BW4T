package nl.tudelft.bw4t.client.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HumanAgentTest {
    @Mock
    private RemoteEnvironment remoteEnvironment;
    private HumanAgent testAgent;
    
    @Before
    public void setUp() throws Exception {
        testAgent = new HumanAgent("Agent1", remoteEnvironment);
    }
    
    @Test
    public void constructortest(){
        assertNotNull(testAgent);
        assertEquals(remoteEnvironment, testAgent.getEnvironment());
    }
}
