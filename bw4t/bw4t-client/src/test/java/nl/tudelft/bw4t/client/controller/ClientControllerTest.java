package nl.tudelft.bw4t.client.controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;

import java.util.LinkedList;

import nl.tudelft.bw4t.client.agent.HumanAgent;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {
    
    @Mock
    RemoteEnvironment remoteEnvironment;
    @Mock
    NewMap map;
    @Mock
    HumanAgent humanAgent;
    @Mock
    BW4TClientGUI clientGUI;
    
    private ClientController clientController;
    private LinkedList<Percept> listOfPercepts;    
    private String entityID = "entityID";

    @Before
    public void setUp() throws Exception {
        listOfPercepts = new LinkedList<Percept>();
        when(map.getArea()).thenReturn(new Point(1.0, 1.0));
        clientController = new ClientController(remoteEnvironment, map, entityID);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testClientControllerRemoteEnvironmentNewMapString() {
        new ClientController(remoteEnvironment, map, entityID);
    }

    @Test
    public void testClientControllerRemoteEnvironmentNewMapStringHumanAgent() {
        new ClientController(remoteEnvironment, map, entityID, humanAgent);
    }

    @Test
    public void testGetToBePerformedAction() {
        listOfPercepts.add(new Percept("TestPercept"));
        clientController.setToBePerformedAction(listOfPercepts);
        assertEquals(listOfPercepts,clientController.getToBePerformedAction());
    }

    @Test
    public void testHandlePerceptsPlayer() {
        Parameter parameter = new Identifier("Test");
        Percept percept = new Percept("player");
        percept.addParameter(parameter);
        listOfPercepts.add(percept);
        clientController.setToBePerformedAction(listOfPercepts);
        clientController.handlePercepts(listOfPercepts);
    }
    
    @Test
    public void testHandlePerceptsMessage() {
        Parameter parameter1 = new Identifier("Test1");
        Parameter parameter2 = new Identifier("Test2");
        Parameter parameter3 = new Identifier("Test3");
        ParameterList parameterList = new ParameterList();
        parameterList.add(parameter1);
        parameterList.add(parameter2);
        parameterList.add(parameter3);
        Percept percept = new Percept("message");
        percept.addParameter(parameterList);
        listOfPercepts.add(percept);
        clientController.setToBePerformedAction(listOfPercepts);
        clientController.handlePercepts(listOfPercepts);
    }

    @Test
    public void testUpdateRenderer() {
        testHandlePerceptsMessage();
        clientController.updateRenderer(clientGUI);
    }

}
