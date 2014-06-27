package nl.tudelft.bw4t.server.model.epartner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.epartners.EPartner;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;
import nl.tudelft.bw4t.server.model.robots.NavigatingRobot;
import nl.tudelft.bw4t.server.model.robots.handicap.Human;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

@RunWith(MockitoJUnitRunner.class)
public class EPartnerTest {

    @Mock
    private ContinuousSpace<Object> space;
    @Mock
    private Grid<Object> grid;
    @Mock
    private Context<Object> context;
    @Mock
    private NdPoint point;
    @Mock
    private NewMap map;
    private BW4TServerMap smap;

    @Before
    public void setup() {
        smap = spy(new BW4TServerMap(map, context));
        when(smap.getContinuousSpace()).thenReturn(space);
        when(smap.getGridSpace()).thenReturn(grid);
    }

    /**
     * Test to check if a new human controlled Robot is constructed properly
     */
    @Test
    public void humanControlledRobotTest() {
        IRobot r = new Human(new NavigatingRobot("", smap, true, 0));
        assertFalse(r.isHoldingEPartner());
    }

    /**
     * Test to check if a non-human controlled Robot can pickup an E-Partner.
     */
    @Test
    public void nonHumanPickupEPartnerTest() {
        AbstractRobot r = new NavigatingRobot("", smap, true, 0);
        EPartner e = new EPartner("", smap);
        r.pickUpEPartner(e);
        assertFalse(r.isHoldingEPartner());
    }

    /**
     * Test to check if a human controlled Robot can pickup an E-Partner
     */
    @Test
    public void humanPickupEPartnerTest() {
        IRobot r = new Human(new NavigatingRobot("", smap, true, 0));
        EPartner e = new EPartner("", smap);
        r.pickUpEPartner(e);
        assertTrue(r.isHoldingEPartner());
    }
}
