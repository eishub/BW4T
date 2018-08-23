package nl.tudelft.bw4t.server.eis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.model.blocks.Block;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;
import nl.tudelft.bw4t.server.model.zone.Room;
import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.space.continuous.NdPoint;

public class RobotEntityTest {
	private IRobot mockRobot = Mockito.mock(IRobot.class);
	private RobotEntity robot;
	private BW4TEnvironment env = Mockito.mock(BW4TEnvironment.class);
	private Context<Object> context = new DefaultContext<>();
	private Method method;
	private Method method2;
	private Method method3;
	private Field field;
	private Room mockRoom = Mockito.mock(Room.class);

	@Before
	public void createRobotEntity() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		this.robot = new RobotEntity(this.mockRobot, true);
		when(this.mockRobot.getLocation()).thenReturn(new NdPoint(1, 1));
		when(this.mockRobot.getHolding()).thenReturn(new Stack<Block>());

		when(this.mockRobot.isConnected()).then(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				// HACK but I don't know how to check the actual counts.
				// so we just check that connected was called and return true.
				Mockito.verify(RobotEntityTest.this.mockRobot).connect();
				return true;
			}
		});

		this.method = this.robot.getClass().getDeclaredMethod("getVisible", Class.class);
		this.method.setAccessible(true);

		this.method3 = this.robot.getClass().getDeclaredMethod("getSizes");
		this.method3.setAccessible(true);

		this.method2 = BW4TEnvironment.class.getDeclaredMethod("setInstance", BW4TEnvironment.class);
		this.method2.setAccessible(true);
		this.method2.invoke(null, this.env);
		when(this.env.getContext()).thenReturn(this.context);
		when(this.env.hasContext()).thenReturn(true);

		this.field = RobotEntity.class.getDeclaredField("ourRobotRoom");
		this.field.setAccessible(true);
	}

	@Test
	public void getRobotTest() {
		assertEquals(this.mockRobot, this.robot.getRobotObject());
	}

	@Test
	public void connectTest() {
		this.robot.connect();
		Mockito.verify(this.mockRobot).connect();
		this.robot.reset();
		Mockito.verify(this.mockRobot).getHolding();
		Mockito.verify(this.mockRobot).moveTo(1, 1);
	}

	@Test
	public void disconnectTest() {
		this.robot.connect();
		this.robot.disconnect();
		Mockito.verify(this.mockRobot).disconnect();
	}

	@Test
	public void getVisibleBlocksTest()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		assertEquals(new HashSet<Block>(), this.method.invoke(this.robot, Block.class));
		this.robot.initializePerceptionCycle();
		this.field.set(this.robot, this.mockRoom);
		assertNotNull(this.mockRoom);
		when(this.robot.getRoom()).thenReturn("in");
		assertNotNull(this.robot.getRoom());

	}

	// getSize is not testable
	// getAt is not testable
}
