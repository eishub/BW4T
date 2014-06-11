package nl.tudelft.bw4t.client.controller;

import static org.mockito.Mockito.*;

import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.client.startup.InitParam;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientMapControllerTest {
    
    @Mock
    ClientController clientController;
    @Mock
    NewMap map;
    @Mock
    RemoteEnvironment remoteEnvironment;
    @Mock
    BW4TClientGUI clientGUI;
    @Mock
    BW4TClient client;

    private ClientMapController clientMapController;
    
    @Before
    public void setUp() throws Exception {
        when(map.getArea()).thenReturn(new Point(1.0, 1.0));
        when(clientController.getEnvironment()).thenReturn(remoteEnvironment);
        clientMapController = new ClientMapController(map, clientController);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRun() {
        clientMapController.run();
        verify(clientController, times(1)).updatedNextFrame();
        verify(clientController, times(1)).getEnvironment();
    }

    @Test
    public void testUpdateRenderer() {
        clientMapController.updateRenderer(clientGUI);
        verify(clientGUI, times(1)).validate();
        verify(clientGUI, times(1)).repaint();
    }

    @Test
    public void testHandlePerceptNotOccupied() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Function("occupied", new Identifier("Room1")));
        clientMapController.handlePercept("not", parameters);
        
    }
    
    @Test
    public void testHandlePerceptNotHolding() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Function("holding", new Numeral(2)));
        clientMapController.handlePercept("not", parameters);
    }
    
    @Ignore("Working on this test case")
    @Test
    public void testHandlePerceptNull() throws RemoteException{
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Function("occupied", new Identifier("Room1")));
        when(clientController.getEnvironment()).thenReturn(remoteEnvironment);
        HashMap<String, BW4TClientGUI> entityToGUI = new HashMap<String, BW4TClientGUI>();
        entityToGUI.put(clientMapController.getTheBot().getName(), clientGUI);
        when(remoteEnvironment.getEntityToGUI()).thenReturn(entityToGUI);
        when(client.getAllPerceptsFromEntity(any(String.class))).thenReturn(null);
        clientMapController.handlePercept("not", parameters);
    }

}
