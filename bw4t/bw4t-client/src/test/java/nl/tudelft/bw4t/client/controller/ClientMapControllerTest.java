package nl.tudelft.bw4t.client.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;

import org.junit.After;
import org.junit.Before;
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

    private ClientMapController clientMapController;
    
    @Before
    public void setUp() throws Exception {
        when(map.getArea()).thenReturn(new Point(1.0, 1.0));
        clientMapController = new ClientMapController(map, clientController);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRun() {
        clientMapController.run();
    }

    @Test
    public void testUpdateRenderer() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testClientMapController() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testHandlePercept() {
        fail("Not yet implemented"); // TODO
    }

}
