package nl.tudelft.bw4t.client.environment.handlers;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.rmi.NotBoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eis.exceptions.PerceiveException;
import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.environment.PerceptsHandler;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.map.NewMap;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class PerceptsHandlerTest {

    @Mock
    private RemoteEnvironment remoteEnvironment;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetAllPerceptsFromEntity() throws PerceiveException, NotBoundException, IOException {
        BW4TClient bw4tClient = new BW4TClient(remoteEnvironment);
        NewMap newMap = new NewMap();
        bw4tClient.useMap(newMap);
        when(remoteEnvironment.getClient()).thenReturn(bw4tClient);

        String entity = "test";
        PerceptsHandler.getAllPerceptsFromEntity(entity, remoteEnvironment);
    }
}
