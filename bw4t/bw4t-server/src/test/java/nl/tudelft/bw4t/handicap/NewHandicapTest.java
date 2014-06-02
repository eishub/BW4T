
package nl.tudelft.bw4t.handicap;

import nl.tudelft.bw4t.blocks.Block;
import nl.tudelft.bw4t.handicap.ColorBlindHandicap;
import nl.tudelft.bw4t.handicap.GripperHandicap;
import nl.tudelft.bw4t.handicap.HandicapInterface;
import nl.tudelft.bw4t.handicap.MoveSpeedHandicap;
import nl.tudelft.bw4t.handicap.SizeOverloadHandicap;
import nl.tudelft.bw4t.robots.NavigatingRobot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

/**
 * @author Joost Rothweiler
 * @author Valentine Mairet
 * 
 */
@RunWith(MockitoJUnitRunner.class)

public class NewHandicapTest {
	
    /**
     * space Mock
     */
    @Mock private ContinuousSpace<Object> space;
    /**
     * sontext Mock
     */
    @Mock private Context<Object> context;
    /**
     * point Mock
     */
    @Mock private NdPoint point;
    /**
     * block Mock
     */
    @Mock private Block block;
    
    /**
     * Initialize the Mocks used.
     */
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
     * - The Human handicap was added to the Handicaps Map;
     * - The Robot is not holding an e-Partner.
     */
    @Test
    public void testHumanHandicap() {
    	HandicapInterface r = new Human(new NavigatingRobot("", space, context, true, 200));
    	
    	assertTrue(r.getSuperParent().getHandicapsList().contains("Human"));
    	assertTrue(r.getEPartner() == null);
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
    
    /**
     * Testing whether the robot is an instance of
     * - HandicapInterface
     * - AbstractHandicapFactory
     */
    @Test
    public void testAllHandicapStructure() {
        HandicapInterface r = 
        		new SizeOverloadHandicap(
        		new ColorBlindHandicap(
        		new GripperHandicap(
        		new MoveSpeedHandicap(
        	    new NavigatingRobot("", space, context, true, 1), 1.0))), 3);
        
        assertTrue(r instanceof HandicapInterface);
        assertTrue(r instanceof AbstractHandicapFactory);
        
    }
}
    
    
