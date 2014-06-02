
package nl.tudelft.bw4t.handicap;

import static org.junit.Assert.*;
import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.handicap.ColorBlindHandicap;
import nl.tudelft.bw4t.handicap.GripperHandicap;
import nl.tudelft.bw4t.handicap.AbstractHandicapFactory;
import nl.tudelft.bw4t.handicap.HandicapInterface;
import nl.tudelft.bw4t.handicap.MoveSpeedHandicap;
import nl.tudelft.bw4t.handicap.SizeOverloadHandicap;
import nl.tudelft.bw4t.robots.NavigatingRobot;
import nl.tudelft.bw4t.robots.Robot;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

@RunWith(MockitoJUnitRunner.class)
/**
 * @author Valentine Mairet
 */
public class NewHandicapTest {
	
    @Mock private ContinuousSpace<Object> space;
    @Mock private Context<Object> context;
    @Mock private NdPoint point;
    @Mock private Block block;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * - the ColorBlindHandicap was nicely added to the Handicaps Map.
     */
    @Test
    public void testColorBlindHandicap() {
        HandicapInterface r = new ColorBlindHandicap(new NavigatingRobot("", space, context, true, 0));
        
        assertTrue(r.getSuperParent().getHandicapsList().contains("ColorBlind"));
    }
    
    /**
     * - the GripperHandicap was nicely added to the Handicaps Map;
     * - the robot cannot pick up blocks.
     * - the robot's capacity is 0 no matter what. 
     */
    @Test
    public void testGripperHandicap() {
        HandicapInterface r = new GripperHandicap(new NavigatingRobot("", space, context, true, 200));
        
        assertTrue(r.getSuperParent().getHandicapsList().contains("Gripper"));
        
        assertFalse(r.canPickUp(block));
        assertTrue(r.getGripperCapacity() == 0);
    }
    
    /**
     * - the MoveSpeedHandicap was nicely added to the Handicaps Map;
     * - the robot's speed mod is 1.1.
     */
    @Test
    public void testMoveSpeedHandicap() {
    	HandicapInterface r = new MoveSpeedHandicap(new NavigatingRobot("", space, context, true, 1), 1.1);
    	
        assertTrue(r.getSuperParent().getHandicapsList().contains("MoveSpeed"));
        
    	assertTrue(r.getSpeedMod() == 1.1);
    }
    
    /**
     * - the SizeOverloadHandicap was nicely added to the Handicaps Map;
     * - the robot's size is 5.
     */
    @Test
    public void testSizeOverloadHandicap() {
    	HandicapInterface r = new SizeOverloadHandicap(new NavigatingRobot("", space, context, true, 1), 5);
    	
        assertTrue(r.getSuperParent().getHandicapsList().contains("SizeOverload"));
        
    	assertTrue(r.getSize() == 5);
    }
    
    /**
     * - the handicaps were nicely added to the Handicaps Map;
     * - the robot's speed mod is 0.8;
     * - the robot cannot pick up blocks;
     * - the robot's capacity is 0 no matter what;
     * - the robot's size is 2. 
     */
    @Test
    public void testAllHandicaps() {
        HandicapInterface r = 
        		new SizeOverloadHandicap(
        		new ColorBlindHandicap(
        		new GripperHandicap(
        		new MoveSpeedHandicap(
        	    new NavigatingRobot("", space, context, true, 1), 0.8))), 2);
        
        assertTrue(r.getSuperParent().getHandicapsList().size() == 4);
 
        assertTrue(r.getSpeedMod() == 0.8);
        assertFalse(r.canPickUp(block));
        assertTrue(r.getGripperCapacity() == 0);
        assertTrue(r.getSize() == 2);
    }
}
    
    
