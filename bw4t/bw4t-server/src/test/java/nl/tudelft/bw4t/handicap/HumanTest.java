package nl.tudelft.bw4t.handicap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.blocks.EPartner;
import nl.tudelft.bw4t.robots.NavigatingRobot;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

/**
 * @author Valentine Mairet
 * This test tests the Human bot. It currently tests whether a 
 * human bot can pick up and drop an epartner.
 *
*/
public class HumanTest {
	
    /**
     * space Mock
     */
    @Mock private ContinuousSpace<Object> space;
    /**
     * context Mock
     */
    @Mock private Context<Object> context;
    /**
     * point Mock
     */
    @Mock private NdPoint point;
    /**
     * ePartner Mock
     */
    @Mock private EPartner ePartner;
    
    /**
     * Setup Mocks
     */
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
        IRobot r = new Human(new NavigatingRobot("", space, context, true, 1));

        assertTrue(r.getHandicapsList().contains("Human"));

        r.pickUpEPartner(ePartner);
        assertTrue(r.isHoldingEPartner());
    }
    
    /**
     * - the Human drops the e-Partner properly. 
     */
    @Test
    public void testDropEPartner() {
        NavigatingRobot robot = spy(new NavigatingRobot("", space, context, true, 1));
        
		IRobot r = new Human(robot);

        r.pickUpEPartner(ePartner);
        assertTrue(r.isHoldingEPartner());
        
        r.dropEPartner();
        assertFalse(r.isHoldingEPartner());
    	
    }
}
