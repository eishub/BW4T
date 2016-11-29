package nl.tudelft.bw4t.client.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eis.iilang.Function;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import nl.tudelft.bw4t.client.BW4TClient;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.map.view.ViewBlock;

@RunWith(MockitoJUnitRunner.class)
public class ClientMapControllerTest {
	private NewMap map = new NewMap();
	@Mock
	private RemoteEnvironment remoteEnvironment;
	@Mock
	private BW4TClient client;

	private ClientMapController clientMapController;

	@Before
	public void setUp() throws Exception {
		// when(map.getArea()).thenReturn(new Point(1.0, 1.0));
		// when(clientController.getEnvironment()).thenReturn(remoteEnvironment);
		clientMapController = new ClientMapController(map);
	}

	/*********************************************************************************/
	/************************* support functions *************************************/
	/*********************************************************************************/
	// private ViewBlock getBlock(Long blockID) throws NoSuchMethodException,
	// IllegalAccessException, InvocationTargetException {
	// Method method = ClientMapController.class.getDeclaredMethod("getBlock",
	// Long.class);
	// method.setAccessible(true);
	// ViewBlock block = (ViewBlock) method.invoke(clientMapController,
	// blockID);
	// return block;
	// }

	/**
	 * Adds 3 blue blocks, having id 1, 2 and 3.
	 */
	private void add3Blocks() {
		for (int blocknr = 1; blocknr <= 3; blocknr++) {
			LinkedList<Parameter> parameters = new LinkedList<Parameter>();
			parameters.add(new Numeral(blocknr));
			parameters.add(new Identifier("blue"));
			clientMapController.handlePercept("color", parameters);
		}
	}

	private void addRooms() {
		Zone zone = new Zone("Room1", null, Zone.Type.ROOM);
		clientMapController.addOccupiedRoom(zone);
		clientMapController.getMap().addZone(zone);

		zone = new Zone(Zone.DROP_ZONE_NAME, null, Zone.Type.ROOM);
		clientMapController.getMap().addZone(zone);

	}

	/*********************************************************************************/
	/******************************** tests ******************************************/
	/*********************************************************************************/
	@Test
	public void testHandlePerceptNotOccupied() {
		addRooms();

		LinkedList<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Function("occupied", new Identifier("Room1")));
		clientMapController.handlePercept("not", parameters);
		assertFalse(clientMapController.getOccupiedRooms().contains("Room1"));
	}

	@Test
	public void testHandlePerceptHolding() {
		add3Blocks();

		ParameterList blocks = new ParameterList();
		blocks.add(new Numeral(2));
		blocks.add(new Numeral(3));
		LinkedList<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(blocks);
		clientMapController.handlePercept("holdingblocks", parameters);
		assertTrue(stackContains(clientMapController.getTheBot().getHolding(),
				2));
		assertTrue(stackContains(clientMapController.getTheBot().getHolding(),
				3));
		assertFalse(stackContains(clientMapController.getTheBot().getHolding(),
				1));

		blocks = new ParameterList();
		blocks.add(new Numeral(1));
		parameters = new LinkedList<Parameter>();
		parameters.add(blocks);
		clientMapController.handlePercept("holdingblocks", parameters);
		assertFalse(stackContains(clientMapController.getTheBot().getHolding(),
				2));
		assertFalse(stackContains(clientMapController.getTheBot().getHolding(),
				3));
		assertTrue(stackContains(clientMapController.getTheBot().getHolding(),
				1));

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
		assertEquals(new Long(2), clientMapController.getTheBot().getId());
	}

	@Test
	public void testHandlePerceptOccupied() {
		addRooms();
		LinkedList<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Identifier(Zone.DROP_ZONE_NAME));
		clientMapController.handlePercept("occupied", parameters);
		Zone dropzone = clientMapController.getDropZone();
		assertTrue(dropzone != null);
		assertTrue(clientMapController.isOccupied(dropzone));
		// can't check #calls, this is real object, not a mock.
		// verify(map, times(1)).getZone(Zone.DROP_ZONE_NAME);
	}

	/** check if some id is in the stack of blocks. */
	private boolean stackContains(Stack<ViewBlock> blocks, int id) {
		for (ViewBlock block : blocks) {
			if (block.getObjectId() == id) {
				return true;
			}
		}
		return false;
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
		ViewBlock block = clientMapController.getBlock(blockID);
		assertEquals(3.0, block.getPosition().getX(), 0.001);
		assertEquals(4.0, block.getPosition().getY(), 0.001);
	}

	@Test
	public void testHandlePerceptColor() throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		LinkedList<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Numeral(3));
		parameters.add(new Identifier("blue"));
		clientMapController.handlePercept("color", parameters);
		ViewBlock block = clientMapController.getBlock(new Long(3));
		assertEquals(BlockColor.BLUE, block.getColor());
	}

	@Test
	public void testHandlePerceptColors() throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		add3Blocks();

		assertEquals(3, clientMapController.getVisibleBlocks().size());
		ViewBlock block = clientMapController.getBlock(new Long(3));
		assertEquals(BlockColor.BLUE, block.getColor());
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
		assertEquals(BlockColor.YELLOW, clientMapController.getSequence()
				.get(3));
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

}
