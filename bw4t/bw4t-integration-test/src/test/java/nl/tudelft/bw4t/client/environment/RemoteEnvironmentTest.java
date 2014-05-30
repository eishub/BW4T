package nl.tudelft.bw4t.client.environment;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import eis.exceptions.ManagementException;
import eis.iilang.Parameter;

@RunWith(MockitoJUnitRunner.class)
public class RemoteEnvironmentTest {

    RemoteEnvironment remoteEnvironment;
    
    @Before
    public void setUp() {
        remoteEnvironment = new RemoteEnvironment();
    }
    
    @Test
    public void constructorTest() throws ManagementException {
        remoteEnvironment.init(new HashMap<String, Parameter>());
    }
}
