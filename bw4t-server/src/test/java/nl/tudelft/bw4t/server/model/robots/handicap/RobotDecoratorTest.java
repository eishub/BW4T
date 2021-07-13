package nl.tudelft.bw4t.server.model.robots.handicap;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import nl.tudelft.bw4t.server.model.blocks.Block;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import repast.simphony.space.continuous.NdPoint;

/**
 * Test suite for the AbstractRobotDecorator class. Tests functionality (calling
 * the methods of the robot in the class).
 */
public class RobotDecoratorTest {
	/**
	 * A gripper handicap (for testing the move method).
	 */
	private GripperHandicap gh;
	/**
	 * A mock IRobot to use for behaviour testing.
	 */
	@Mock
	private IRobot bot;
	/**
	 * A mock return type for getSuperParent().
	 */
	@Mock
	private AbstractRobot returnRobot;
	/**
	 * A mock Block object to be used in a test.
	 */
	@Mock
	private Block b;

	/**
	 * Set up the test suite.
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		when(bot.getSuperParent()).thenReturn(returnRobot);
		when(returnRobot.getHandicapsList())
				.thenReturn(new ArrayList<String>());
		gh = new GripperHandicap(bot);
	}

	/**
	 * test for getName().
	 */
	@Test
	public void getNameTest() {
		gh.getName();
		verify(bot).getName();
	}

	/**
	 * test for connect().
	 */
	@Test
	public void connectTest() {
		gh.connect();
		verify(bot).connect();
	}

	/**
	 * Test for disconnect().
	 */
	@Test
	public void disconnectTest() {
		gh.disconnect();
		verify(bot).disconnect();
	}

	/**
	 * Test for isHolding().
	 */
	@Test
	public void isHoldingTest() {
		gh.getHolding();
		verify(bot).getHolding();
	}

	/**
	 * Test for getTargetLoc().
	 */
	@Test
	public void getTargetLocTest() {
		gh.getTargetLocation();
		verify(bot).getTargetLocation();
	}

	/**
	 * Test for setTargetLoc().
	 */
	@Test
	public void setTargetLocTest() {
		NdPoint ndp = new NdPoint(0.0, 0.0);
		gh.setTargetLocation(ndp);
		verify(bot).setTargetLocation(ndp);
	}

	/**
	 * Test for drop().
	 */
	@Test
	public void dropTest() {
		gh.drop();
		verify(bot).drop();
	}

	/**
	 * Test for drop(amount).
	 */
	@Test
	public void dropAmountTest() {
		gh.drop(1);
		verify(bot).drop(1);
	}

	/**
	 * Test for moveTo()
	 */
	@Test
	public void moveToTest() {
		gh.moveTo(0.0, 0.0);
		verify(bot).moveTo(0.0, 0.0);
	}

	/**
	 * Test for getMoveType()
	 */
	@Test
	public void getMoveTypeTest() {
		gh.getMoveType(0.0, 0.0);
		verify(bot).getMoveType(0.0, 0.0);
	}

	/**
	 * Test for checkZoneAccess()
	 */
	@Test
	public void checkZoneAccessTest() {
		gh.checkZoneAccess(null, null, null);
		verify(bot).checkZoneAccess(null, null, null);
	}

	/**
	 * Test for getcurrentdoor()
	 */
	@Test
	public void getCurrentDoorTest() {
		gh.getCurrentDoor(0.0, 0.0);
		verify(bot).getCurrentDoor(0.0, 0.0);
	}

	/**
	 * Test for getcurrentroom()
	 */
	@Test
	public void getCurrentRoomTest() {
		gh.getCurrentRoom(0.0, 0.0);
		verify(bot).getCurrentRoom(0.0, 0.0);
	}

	/**
	 * Test for getzone()
	 */
	@Test
	public void getZoneTest() {
		gh.getZone();
		verify(bot).getZone();
	}

	/**
	 * Test for movebydisplacement()
	 */
	@Test
	public void moveByDisplacementTest() {
		gh.moveByDisplacement(0.0, 0.0);
		verify(bot).moveByDisplacement(0.0, 0.0);
	}

	/**
	 * Test for move()
	 */
	@Test
	public void moveTest() {
		gh.move();
		verify(bot).move();
	}

	/**
	 * Test for stop robot
	 */
	@Test
	public void stopRobotTest() {
		gh.stopRobot();
		verify(bot).stopRobot();
	}

	/**
	 * Test for is collided
	 */
	@Test
	public void isCollidedTest() {
		gh.isCollided();
		verify(bot).isCollided();
	}

	/**
	 * Test for set collided
	 */
	@Test
	public void setCollidedTest() {
		gh.setCollided(true);
		verify(bot).setCollided(true);
	}

	/**
	 * Test for clear collided
	 */
	@Test
	public void clearCollidedTest() {
		gh.clearCollided();
		verify(bot).clearCollided();
	}

	/**
	 * Test for is connected
	 */
	@Test
	public void isConnectedTest() {
		gh.isConnected();
		verify(bot).isConnected();
	}

	/**
	 * Test for is one bot per zone
	 */
	@Test
	public void isOneBotPerZoneTest() {
		gh.isOneBotPerZone();
		verify(bot).isOneBotPerZone();
	}

	/**
	 * Test for get size
	 */
	@Test
	public void getSizeTest() {
		gh.getSize();
		verify(bot).getSize();
	}

	/**
	 * Test for set size
	 */
	@Test
	public void setSizeTest() {
		gh.setSize(5);
		verify(bot).setSize(5);
	}

	/**
	 * Test for get view
	 */
	@Test
	public void getViewTest() {
		gh.getView();
		verify(bot).getView();
	}

	/**
	 * Test for get agent record
	 */
	@Test
	public void getAgentRecordTest() {
		gh.getAgentRecord();
		verify(bot).getAgentRecord();
	}

	/**
	 * Test for get battery
	 */
	@Test
	public void getBatteryTest() {
		gh.getBattery();
		verify(bot).getBattery();
	}

	/**
	 * Test for set battery
	 */
	@Test
	public void setBatteryTest() {
		gh.setBattery(null);
		verify(bot).setBattery(null);
	}

	/**
	 * Test for recharge
	 */
	@Test
	public void rechargeTest() {
		gh.recharge();
		verify(bot).recharge();
	}

	/**
	 * Test for set parent
	 */
	@Test
	public void setParentTest() {
		gh.setParent(bot);
		verify(bot).setParent(bot);
	}

	/**
	 * Test for get handicaps
	 */
	@Test
	public void getHandicapsTest() {
		gh.getHandicapsList();
		verify(bot).getHandicapsList();
	}

	/**
	 * Test for speed mod
	 */
	@Test
	public void getSpeedModTest() {
		gh.getSpeedMod();
		verify(bot).getSpeedMod();
	}

	/**
	 * Test for is human
	 */
	@Test
	public void isHumanTest() {
		gh.isHuman();
		verify(bot).isHuman();
	}

	/**
	 * Test for get epartner
	 */
	@Test
	public void getEPartnerTest() {
		gh.getEPartner();
		verify(bot).getEPartner();
	}

	/**
	 * Test for is holding e partner
	 */
	@Test
	public void isHoldingEPartnerTest() {
		gh.isHoldingEPartner();
		verify(bot).isHoldingEPartner();
	}

	/**
	 * Test for pick up e partner
	 */
	@Test
	public void pickUpEPartnerTest() {
		gh.pickUpEPartner(null);
		verify(bot).pickUpEPartner(null);
	}

	/**
	 * Test for drop e partner
	 */
	@Test
	public void dropEPartnerTest() {
		gh.dropEPartner();
		verify(bot).dropEPartner();
	}

	/**
	 * Test for get state
	 */
	@Test
	public void getStateTest() {
		gh.getState();
		verify(bot).getState();
	}

	/**
	 * Test for set target
	 */
	@Test
	public void setTargetTest() {
		gh.setTarget(null);
		verify(bot).setTarget(null);
	}

	/**
	 * Test for get loc
	 */
	@Test
	public void getLocTest() {
		gh.getLocation();
		verify(bot).getLocation();
	}

	/**
	 * Test for get ID
	 */
	@Test
	public void getIDTest() {
		gh.getId();
		verify(bot).getId();
	}

	/**
	 * Test for get context
	 */
	@Test
	public void getContextTest() {
		gh.getContext();
		verify(bot).getContext();
	}

	/**
	 * Test for distance to
	 */
	@Test
	public void distanceToTest() {
		NdPoint p = null;
		gh.distanceTo(p);
		verify(bot).distanceTo(p);
	}
}
