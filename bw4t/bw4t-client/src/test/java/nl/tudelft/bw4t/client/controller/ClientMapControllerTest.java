package nl.tudelft.bw4t.client.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Point;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.ViewBlock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientMapControllerTest {
    
    @Mock private ClientController clientController;
    @Mock private NewMap map;
    @Mock private RemoteEnvironment remoteEnvironment;
    @Mock private BW4TClientGUI clientGUI;
    @Mock private BW4TClient client;
    
    private ClientMapController clientMapController;
    
    @Before
    public void setUp() throws Exception {
        when(map.getArea()).thenReturn(new Point(1.0, 1.0));
        when(clientController.getEnvironment()).thenReturn(remoteEnvironment);
        clientMapController = new ClientMapController(map, clientController);
    }

    @Ignore
    @Test
    public void testRun() {
        clientMapController.run();
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
        assertFalse(clientMapController.getOccupiedRooms().contains("Room1"));
    }
    
    @Test
    public void testHandlePerceptNotHolding() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Function("holding", new Numeral(2)));
        clientMapController.handlePercept("not", parameters);
        assertFalse(clientMapController.getTheBot().getHolding().containsKey(2));
    }
    
    @Test
    public void testHandlePerceptNotOther() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Function("other", new Numeral(2)));
        clientMapController.handlePercept("not", parameters);
    }
    
    @Test
    public void testHandlePerceptRobot() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(2));
        clientMapController.handlePercept("robot", parameters);
        assertEquals(2, clientMapController.getTheBot().getId());
    }
    
    @Test
    public void testHandlePerceptOccupied() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Identifier(Zone.DROP_ZONE_NAME));
        clientMapController.handlePercept("occupied", parameters);
        verify(map, times(1)).getZone(Zone.DROP_ZONE_NAME);
    }
    
    @Test
    public void testHandlePerceptHolding() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(2));
        clientMapController.handlePercept("holding", parameters);
        assertFalse(clientMapController.getTheBot().getHolding().containsKey(2));
    }
    
    @Test
    public void testHandlePerceptPosition() throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(3));
        parameters.add(new Numeral(3));
        parameters.add(new Numeral(4));
        clientMapController.handlePercept("position", parameters);
        Long blockID = new Long(3);
        ViewBlock block = getBlock(blockID);
        assertEquals(3.0, block.getPosition().getX(), 0.001);
        assertEquals(4.0, block.getPosition().getY(), 0.001);
    }
    
    @Test
    public void testHandlePerceptPositionEPartner() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(3));
        parameters.add(new Numeral(3));
        parameters.add(new Numeral(3));
        parameters.add(new ParameterList());
        testHandlePerceptEPartner();
        clientMapController.handlePercept("position", parameters);
        assertEquals(3, clientMapController.getViewEPartner(3).getLocation().getX(), 0.001);
        assertEquals(3, clientMapController.getViewEPartner(3).getLocation().getX(), 0.001);
    }
    
    @Test
    public void testHandlePerceptColor() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(3));
        parameters.add(new Identifier("blue"));
        clientMapController.handlePercept("color", parameters);
        ViewBlock block = getBlock(new Long(3));
        assertEquals(BlockColor.BLUE, block.getColor());
    }
    
    @Test
    public void testHandlePerceptEPartner() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(3));
        parameters.add(new Identifier("NAAM"));
        parameters.add(new Numeral(3));
        parameters.add(new ParameterList());
        clientMapController.handlePercept("epartner", parameters);
        assertEquals(3,clientMapController.getViewEPartner(3).getId());
        assertEquals("NAAM", clientMapController.getViewEPartner(3).getName());
    }
    
    @Test
    public void testHandlePerceptEPartnerNull() {
        testHandlePerceptEPartner();
        testHandlePerceptEPartner();
    }
    
    @Test
    public void testHandlePerceptEPartnerHolderID() {
        testHandlePerceptRobot();
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(2));
        parameters.add(new Identifier("NAAM"));
        parameters.add(new Numeral(2));
        parameters.add(new ParameterList());
        clientMapController.handlePercept("epartner", parameters);
        assertEquals(2, clientMapController.getViewEPartner(2).getId());
        assertEquals("NAAM", clientMapController.getViewEPartner(2).getName());
    }
    
    @Test
    public void testHandlePerceptEPartnerHolderIDSameID() {
        testHandlePerceptRobot();
        testHandlePerceptEPartnerHolderID();
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(2));
        parameters.add(new Identifier("NAAM"));
        parameters.add(new Numeral(2));
        parameters.add(new ParameterList());
        clientMapController.handlePercept("epartner", parameters);
        assertEquals(2, clientMapController.getViewEPartner(2).getId());
        assertEquals("NAAM", clientMapController.getViewEPartner(2).getName());
    }
    
    @Test
    public void testHandlePerceptEPartnerSameIDHoldingPartner() {
        testHandlePerceptRobot();
        testHandlePerceptEPartnerHolderID();
        testHandlePerceptHolding();
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(2));
        parameters.add(new Identifier("NAAM"));
        parameters.add(new Numeral(3));
        parameters.add(new ParameterList());
        clientMapController.handlePercept("epartner", parameters);
        assertEquals(2, clientMapController.getViewEPartner(2).getId());
        assertEquals("NAAM", clientMapController.getViewEPartner(2).getName());
    }
    
    @Test
    public void testHandlePerceptEPartnerSameIDHoldingPartnerNegative() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(1));
        parameters.add(new Identifier("NAAM"));
        parameters.add(new Numeral(-2));
        parameters.add(new ParameterList());
        clientMapController.handlePercept("epartner", parameters);
        assertEquals(-1, clientMapController.getTheBot().getHoldingEpartner());
        assertEquals("NAAM", clientMapController.getViewEPartner(1).getName());
    }
    
    @Test
    public void testHandlePerceptColorSequence() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        ParameterList parameterList = new ParameterList();
        parameterList.add(new Identifier("b"));
        parameterList.add(new Identifier("r"));
        parameterList.add(new Identifier("g"));
        parameterList.add(new Identifier("y"));
        parameters.add(parameterList);
        clientMapController.handlePercept("sequence", parameters);
        assertEquals(BlockColor.BLUE, clientMapController.getSequence().get(0));
        assertEquals(BlockColor.RED, clientMapController.getSequence().get(1));
        assertEquals(BlockColor.GREEN, clientMapController.getSequence().get(2));
        assertEquals(BlockColor.YELLOW, clientMapController.getSequence().get(3));
    }
    
    @Test
    public void testHandlePerceptSequenceIndex() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(3));
        clientMapController.handlePercept("sequenceIndex", parameters);
        assertEquals(3, clientMapController.getSequenceIndex());
    }
    
    @Test
    public void testHandlePerceptLocation() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(3));
        parameters.add(new Numeral(3));
        clientMapController.handlePercept("location", parameters);
    }
    
    @Test
    public void testHandlePerceptRobotSize() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(2));
        parameters.add(new Numeral(2));
        testHandlePerceptRobot();
        clientMapController.handlePercept("robotSize", parameters);
        assertEquals(2, clientMapController.getTheBot().getRobotSize());
    }
    
    @Test
    public void testHandlePerceptRobotSizeNeq() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(314159));
        parameters.add(new Numeral(314159));
        testHandlePerceptRobotSize();
        testHandlePerceptRobot();
        clientMapController.handlePercept("robotSize", parameters);
        assertEquals(2, clientMapController.getTheBot().getRobotSize());
    }
    
    @Test
    public void testHandlePerceptIgnorePercept() {
        LinkedList<Parameter> parameters = new LinkedList<Parameter>();
        parameters.add(new Numeral(314159));
        parameters.add(new Numeral(314159));
        testHandlePerceptRobot();
        clientMapController.handlePercept("tudelft", parameters);
    }

    private ViewBlock getBlock(Long blockID) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        Method method = ClientMapController.class.getDeclaredMethod("getBlock", Long.class);
        method.setAccessible(true);
        ViewBlock block = (ViewBlock) method.invoke(clientMapController, blockID);
        return block;
    }
}
