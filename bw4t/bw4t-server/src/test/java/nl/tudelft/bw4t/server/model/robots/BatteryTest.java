package nl.tudelft.bw4t.server.model.robots;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.server.model.BW4TServerMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

@RunWith(MockitoJUnitRunner.class)
public class BatteryTest {
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
     * Test whether we can set a bot's battery to a certain battery.
     */
    @Test
    public void getBatteryTest() {
        AbstractRobot r = new NavigatingRobot("", smap, true, 0);
        Battery b = new Battery(50, 20, 5);

        r.setBattery(b);
        assertEquals(r.getBattery(), b);
    }

    /**
     * Test whether a new robot without handicaps has a battery of 100% and a discharge rate of 0.
     */
    @Test
    public void robotBatteryTest() {
        AbstractRobot r = new NavigatingRobot("", smap, true, 0);
        assertTrue(r.getBattery().getPercentage() == 100);
        assertTrue(r.getBattery().getDischargeRate() == 0);
    }

    /**
     * A bot with infinite battery should not discharge.
     */
    @Test
    public void infiniteBatteryTest() {
        Battery b = new Battery(Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        b.discharge();
        assertTrue(b.getPercentage() == 100);
    }

    /**
     * A battery with finite battery and a discharge rate should discharge.
     */
    @Test
    public void finiteBatteryTest() {
        Battery b = new Battery(50, 20, 5);
        b.discharge();
        assertTrue(b.getCurrentCapacity() == 15);
    }

    /**
     * Test whether a bot can recharge.
     */
    @Test
    public void rechargeTest() {
        Battery b = new Battery(50, 20, 5);
        b.recharge();
        assertTrue(b.getCurrentCapacity() == 50);
    }

    /**
     * Check whether a battery capacity can fall below 0. Should not be possible.
     */
    @Test
    public void finiteBatteryPastZeroTest() {
        Battery b = new Battery(50, 2, 5);
        b.discharge();
        assertTrue(b.getCurrentCapacity() == 0);
    }

}
