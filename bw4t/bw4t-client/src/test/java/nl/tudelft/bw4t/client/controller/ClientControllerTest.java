package nl.tudelft.bw4t.client.controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        when(remoteEnvironment.getMap()).thenReturn(map);
        clientController = new ClientController(remoteEnvironment, entityID);
        clientController.setGui(clientGUI);
        Thread.sleep(1);
        clientController.getMapController().setRunning(false);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testClientControllerRemoteEnvironmentNewMapString() {
        ClientController testController = new ClientController(remoteEnvironment, entityID);
        assertNotNull(testController);
        assertEquals(remoteEnvironment, testController.getEnvironment());
        assertEquals(map, testController.getMapController().getMap());
        assertEquals(entityID, testController.getMapController().getTheBot().getName());
    }

    @Test
    public void testClientControllerRemoteEnvironmentNewMapStringHumanAgent() {
        ClientController testController = new ClientController(remoteEnvironment, entityID, humanAgent);
        assertEquals(humanAgent,testController.getHumanAgent());
    }

    @Test
    public void testGetToBePerformedAction() {
        listOfPercepts.add(new Percept("testPercept"));
        clientController.setToBePerformedAction(listOfPercepts);
        assertEquals(listOfPercepts, clientController.getToBePerformedAction());
    }

    @Test
    public void testHandlePerceptsPlayer() {
        Parameter parameter = new Identifier("TestPlayer");
        Percept percept = new Percept("player");
        percept.addParameter(parameter);
        listOfPercepts.add(percept);
        clientController.setToBePerformedAction(listOfPercepts);
        clientController.handlePercepts(listOfPercepts);
        assertEquals("TestPlayer", clientController.getOtherPlayers().iterator().next());
    }

    @Test
    public void testHandlePerceptsMessage() {
        Parameter parameter1 = new Identifier("TestSender");
        Parameter parameter2 = new Identifier("TestMessage");
        ParameterList parameterList = new ParameterList();
        parameterList.add(parameter1);
        parameterList.add(parameter2);
        Percept percept = new Percept("message");
        percept.addParameter(parameterList);
        listOfPercepts.add(percept);
        clientController.setToBePerformedAction(listOfPercepts);
        clientController.handlePercepts(listOfPercepts);
        assertEquals("TestSender: TestMessage", clientController.getBotChatHistory().get(0));
    }

    @Test
    public void testHandlePerceptsOther() {
        Percept percept = new Percept("test");
        listOfPercepts.add(percept);
        clientController.setToBePerformedAction(listOfPercepts);
        clientController.handlePercepts(listOfPercepts);
    }

    @Test
    public void testUpdateRenderer() {
        testHandlePerceptsMessage();
        clientController.updateGUI();
        verify(clientGUI, times(2)).update();
    }

}
