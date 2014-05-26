
package nl.tudelft.bw4t.handicap;

import static org.junit.Assert.*;
import nl.tudelft.bw4t.handicap.ColorBlindHandicap;
import nl.tudelft.bw4t.handicap.GripperHandicap;
import nl.tudelft.bw4t.handicap.Handicap;
import nl.tudelft.bw4t.handicap.HandicapInterface;
import nl.tudelft.bw4t.handicap.MoveSpeedHandicap;
import nl.tudelft.bw4t.handicap.SizeOverloadHandicap;
import nl.tudelft.bw4t.robots.Robot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

@RunWith(MockitoJUnitRunner.class)
public class HandicapTest {
	@Mock private ContinuousSpace<Object> space;
	@Mock private Context<Object> context;
	@Mock private NdPoint point;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test to check if the structure of the handicaps matches the decorator pattern.
	 * All handicaps are tested separately in the next 4 tests
	 * 
	 * Test ColorBlindHandicap structure
	 */	
	@Test
	public void structureColorBlindHandicapTest() {
		Robot r = new Robot("",space,context,true, false, 0);
		new ColorBlindHandicap(r);
		assertTrue(r.getHandicapsMap().get("ColorBlind") instanceof HandicapInterface);
		assertTrue(r.getHandicapsMap().get("ColorBlind") instanceof Handicap);
	}
	/**
	 * Test GripperHandicap structure
	 */
	@Test
	public void structureGripperHandicapTest() {
		Robot r = new Robot("",space,context,true, false, 0);
		new GripperHandicap(r);
		assertTrue(r.getHandicapsMap().get("Gripper") instanceof HandicapInterface);
		assertTrue(r.getHandicapsMap().get("Gripper") instanceof Handicap);
	}
	/**
	 * Test MoveSpeedHandicap structure
	 */
	@Test
	public void structureMoveSpeedHandicapTest() {
		Robot r = new Robot("",space,context,true, false, 0);
		new MoveSpeedHandicap(r, 1.0);
		assertTrue(r.getHandicapsMap().get("MoveSpeed") instanceof HandicapInterface);
		assertTrue(r.getHandicapsMap().get("MoveSpeed") instanceof Handicap);
	}
	/**
	 * Test SizeOverloadHandicap structure
	 */
	@Test
	public void structureSizeOverloadHandicapTest() {
		Robot r = new Robot("",space,context,true, false, 0);
		new SizeOverloadHandicap(r, 2);
		assertTrue(r.getHandicapsMap().get("SizeOverload") instanceof HandicapInterface);
		assertTrue(r.getHandicapsMap().get("SizeOverload") instanceof Handicap);
	}
	
	/**
	 * Test whether we can create a robot with all handicaps enabled
	 */
	@Test
	public void structureAllHandicapsTest() {
		Robot r = new Robot("",space,context,true, false, 0);
		ColorBlindHandicap cb = new ColorBlindHandicap(r);
		GripperHandicap gh = new GripperHandicap(cb);
		MoveSpeedHandicap msh = new MoveSpeedHandicap(gh, 0.8);
		new SizeOverloadHandicap(msh, 2);
		// Asserts
		assertTrue(r.getHandicapsMap().get("ColorBlind") instanceof HandicapInterface);
		assertTrue(r.getHandicapsMap().get("ColorBlind") instanceof Handicap);
		assertTrue(r.getHandicapsMap().get("Gripper") instanceof HandicapInterface);
		assertTrue(r.getHandicapsMap().get("Gripper") instanceof Handicap);
		assertTrue(r.getHandicapsMap().get("MoveSpeed") instanceof HandicapInterface);
		assertTrue(r.getHandicapsMap().get("MoveSpeed") instanceof Handicap);
		assertTrue(r.getHandicapsMap().get("SizeOverload") instanceof HandicapInterface);
		assertTrue(r.getHandicapsMap().get("SizeOverload") instanceof Handicap);				
	}
	
	/**
	 * Check that whenever a robot with movespeedhandicap is created
	 * whether or not its speedMod is set to a specific value.
	 * Whether a robot moves faster or slower with this speedMod has been manually tested
	 * by adding a speedMod to a regular robot and editing its move() method
	 * so that it uses the speedMod as implemented in the MoveSpeedHandicap.
	 */
	@Test
	public void getSpeedModTest() {
		Robot r = new Robot("",space,context,true, false, 0);
		MoveSpeedHandicap msh = new MoveSpeedHandicap(r, 0.5);
		double delta = 0;
		assertEquals(0.5, msh.getSpeedMod(), delta);
	}
	
	/**
	 * Test whether we can set the speedMod to 0 to create a robot with no ability to move.
	 */
	@Test
	public void getSpeedModImmovableTest() {
		Robot r = new Robot("",space,context,true, false, 0);
		MoveSpeedHandicap msh = new MoveSpeedHandicap(r, 0);
		double delta = 0;
		assertEquals(0, msh.getSpeedMod(), delta);
	}
	
	@Test
	public void getSizeSizeOverloadTest() {
		Robot r = new Robot("",space,context,true, false, 0);
		new SizeOverloadHandicap(r, 3);
		assertTrue(r.getHandicapsMap().get("SizeOverload") instanceof HandicapInterface);
		assertTrue(r.getHandicapsMap().get("SizeOverload") instanceof Handicap);
		assertEquals(3, r.getSize());
	}
	
	

}
