package nl.tudelft.bw4t.robots;
import static org.junit.Assert.*;
import nl.tudelft.bw4t.handicap.AbstractHandicapFactory;
import nl.tudelft.bw4t.handicap.HandicapInterface;
import nl.tudelft.bw4t.robots.Battery;
import nl.tudelft.bw4t.robots.Robot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
@RunWith(MockitoJUnitRunner.class)
public class BatteryTest {
	@Mock private ContinuousSpace<Object> space;
	@Mock private Context<Object> context;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void robotBatteryTest() {
		Robot r = new Robot("",space,context,true,false, 0);
		assertTrue(r.getBatteryPercentage() == 100);
		assertTrue(r.getDischargeRate() == 0);
	}
	@Test
	public void infiniteBatteryTest() {
		Battery b = new Battery(Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
		b.discharge();
		assertTrue(b.getPercentage() == 100);
	}
	@Test
	public void finiteBatteryTest() {
		Battery b = new Battery(50, 20, 5);
		b.discharge();
		assertTrue(b.getCurrentCapacity() == 15);
	}
	@Test
	public void rechargeTest() {
		Battery b = new Battery(50, 20, 5);
		b.recharge();
		assertTrue(b.getCurrentCapacity() == 50);
	}
	@Test
	public void finiteBatteryPastZeroTest() {
		Battery b = new Battery(50, 2, 5);
		b.discharge();
		assertTrue(b.getCurrentCapacity() == 0);
	}
}
