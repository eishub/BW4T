package nl.tudelft.bw4t.client.environment.handlers;

import static org.junit.Assert.*;

import eis.exceptions.PerceiveException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;

import nl.tudelft.bw4t.agent.HumanAgent;
import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.map.NewMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

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
        HumanAgent humanAgent = new HumanAgent("agentID", remoteEnvironment);
        NewMap newMap = new NewMap();
        bw4tClient.useMap(newMap);
        
        BW4TClientGUI bw4tClientGUI = new BW4TClientGUI(remoteEnvironment, "entityID", humanAgent);
        HashMap<String, BW4TClientGUI> entityToGui = new HashMap<String, BW4TClientGUI>();
        
        entityToGui.put("entity", bw4tClientGUI);
        
        when(remoteEnvironment.getClient()).thenReturn(bw4tClient);
        when(remoteEnvironment.getEntityToGUI()).thenReturn(entityToGui);
        
        String entity = "test";
        
        PerceptsHandler.getAllPerceptsFromEntity(entity, remoteEnvironment);
    }
}
