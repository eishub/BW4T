package nl.tudelft.bw4t.server.model.robots.handicap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.server.model.BW4TServerMap;
import nl.tudelft.bw4t.server.model.epartners.EPartner;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;
import nl.tudelft.bw4t.server.model.robots.NavigatingRobot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

/**
 * This test tests the Human bot. It currently tests whether a human bot can pick up and drop an epartner.
 */
@RunWith(MockitoJUnitRunner.class)
public class HumanTest {

    /**
     * space Mock
     */
    @Mock
    private ContinuousSpace<Object> space;
    @Mock
    private Grid<Object> grid;

    /**
     * context Mock
     */
    @Mock
    private Context<Object> context;
    /**
     * point Mock
     */
    private NdPoint point;
    /**
     * ePartner Mock
     */
    @Mock
    private EPartner ePartner;
    /**
     * Robot mock out of reach of any epartner
     */
    @Mock
    private NavigatingRobot outOfReach;
    /**
     * Accompanying IRobot mock
     */
    @Mock
    private AbstractRobot outBot;
    /**
     * Robot mock in reach of an epartner
     */
    @Mock
    private NavigatingRobot inReach;
    /**
     * Accompanying IRobot mock
     */
    @Mock
    private AbstractRobot inBot;
    @Mock
    private NewMap map;
    private BW4TServerMap smap;

    @Before
    public void setup() {
        point = new NdPoint(0,0);
        when(space.getLocation(any())).thenReturn(point);
        when(ePartner.getLocation()).thenReturn(point);
        smap = spy(new BW4TServerMap(map, context));
        when(smap.getContinuousSpace()).thenReturn(space);
        when(smap.getGridSpace()).thenReturn(grid);
        when(inBot.getSuperParent()).thenReturn(inBot);
        when(outBot.getSuperParent()).thenReturn(outBot);
    }

    /**
     * - the Human "handicap" was nicely added to the map. - the Human is holding the e-Partner once picked up.
     */
    @Test
    public void testPickUpEPartner() {
        IRobot r = new Human(new NavigatingRobot("", smap, true, 1));

        assertTrue(r.getHandicapsList().contains("Human"));

        r.pickUpEPartner(ePartner);
        assertTrue(r.isHoldingEPartner());
    }

    /**
     * - the Human drops the e-Partner properly.
     */
    @Test
    public void testDropEPartner() {
        NavigatingRobot robot = new NavigatingRobot("", smap, true, 1);

        IRobot r = new Human(robot);

        r.pickUpEPartner(ePartner);
        assertTrue(r.isHoldingEPartner());

        r.dropEPartner();
        assertFalse(r.isHoldingEPartner());

    }

    /**
     * The human bot tries to drop an epartner without holding it (behavior test).
     */
    @Test
    public void testDropEPartnerNotHolding() {
        NavigatingRobot robot = spy(new NavigatingRobot("", smap, true, 1));
        IRobot r = new Human(robot);
        r.dropEPartner();
        verifyNoMoreInteractions(ePartner);
    }

    /**
     * can pick up epartner test (result is false). commented out because fail
     */
    @Test
    public void canPickUpEPartnerTest() {
        Human h = new Human(outBot);
        when(ePartner.getLocation()).thenReturn(new NdPoint(4));
        when(h.getSuperParent().getLocation()).thenReturn(new NdPoint(1));
        assertNotSame(h.getLocation(), ePartner.getLocation());
        assertFalse(h.getParent().canPickUp(ePartner));
    }

    /**
     * can pick up epartner test (result is true).
     */
    @Test
    public void canPickUpEPartnerTestTrue() {
        Human h = new Human(inBot);
        assertTrue(h.canPickUp(ePartner));
    }

    /**
     * Return EPartner for max. coverage
     */
    @Test
    public void getEPartnerTest() {
        NavigatingRobot robot = spy(new NavigatingRobot("", smap, true, 1));
        IRobot r = new Human(robot);
        r.pickUpEPartner(ePartner);
        assertTrue(r.getEPartner() == ePartner);
    }
}
