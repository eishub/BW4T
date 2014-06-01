package nl.tudelft.bw4t.handicap;

import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.blocks.EPartner;
import nl.tudelft.bw4t.robots.NavigatingRobot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

public class HumanTest {
	
    @Mock private ContinuousSpace<Object> space;
    @Mock private Context<Object> context;
    @Mock private NdPoint point;
    @Mock private EPartner ePartner;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * - the Human "handicap" was nicely added to the map.
     * - the Human is holding the e-Partner once picked up. 
     */
    @Test
    public void testPickUpEPartner() {
        HandicapInterface r = new Human(new NavigatingRobot("", space, context, true, 1));

        assertTrue(r.getSuperParent().getHandicapsList().contains("Human"));

        r.pickUpEPartner(ePartner);
        assertTrue(r.isHoldingEPartner());
    }
}
