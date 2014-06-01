package nl.tudelft.bw4t.epartner;

import static org.junit.Assert.*;
import nl.tudelft.bw4t.blocks.EPartner;
import nl.tudelft.bw4t.handicap.HandicapInterface;
import nl.tudelft.bw4t.handicap.Human;
import nl.tudelft.bw4t.robots.NavigatingRobot;
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
public class EPartnerTest {
	
    @Mock private ContinuousSpace<Object> space;
    @Mock private Context<Object> context;
    @Mock private NdPoint point;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    
    /**
     * Test to check if a new human controlled Robot is constructed properly
     */ 
    @Test
    public void humanControlledRobotTest() {
        HandicapInterface r = new Human(new NavigatingRobot("", space, context, true, 0));
        assertFalse(r.isHoldingEPartner());
    }
    
    /**
     * Test to check if a non-human controlled Robot can pickup an E-Partner.
     */
    @Test
    public void nonHumanPickupEPartnerTest() {
        Robot r = new NavigatingRobot("", space, context, true, 0);
        EPartner e = new EPartner(space, context);
        r.pickUpEPartner(e);
        assertFalse(r.isHoldingEPartner());
    }
    
    /**
     * Test to check if a human controlled Robot can pickup an E-Partner
     */
    @Test
    public void humanPickupEPartnerTest() {
        HandicapInterface r = new Human(new NavigatingRobot("", space, context, true, 0));
        EPartner e = new EPartner(space, context);
        r.pickUpEPartner(e);
        assertTrue(r.isHoldingEPartner());
    }
}
