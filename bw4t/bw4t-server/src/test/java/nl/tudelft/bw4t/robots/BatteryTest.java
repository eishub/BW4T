package nl.tudelft.bw4t.robots;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.model.robots.AbstractRobot;
import nl.tudelft.bw4t.model.robots.Battery;
import nl.tudelft.bw4t.model.robots.NavigatingRobot;
import nl.tudelft.bw4t.model.robots.handicap.AbstractRobotDecorator;
import nl.tudelft.bw4t.model.robots.handicap.IRobot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
@RunWith(MockitoJUnitRunner.class)
public class BatteryTest {
    /**
     * space Mock
     */
    @Mock private ContinuousSpace<Object> space;
    /**
     * context Mock
     */
    @Mock private Context<Object> context;
    
    /**
     * Initialize Mocks
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    /**
     * Test whether we can set a bot's battery to a certain battery.
     */
    @Test
    public void getBatteryTest() {
        AbstractRobot r = new NavigatingRobot("", space, context, true, 0);
        Battery b = new Battery(50, 20, 5);
        
        r.setBattery(b);
        assertEquals(r.getBattery(), b);
    }
     
    /**
     * Test whether a new robot without handicaps has a battery of 100%
     * and a discharge rate of 0.
     */
    @Test
    public void robotBatteryTest() {
        AbstractRobot r = new NavigatingRobot("", space, context, true, 0);
        assertTrue(r.getBatteryPercentage() == 100);
        assertTrue(r.getDischargeRate() == 0);
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
     * Check whether a battery capacity can fall below 0.
     * Should not be possible.
     */
    @Test
    public void finiteBatteryPastZeroTest() {
        Battery b = new Battery(50, 2, 5);
        b.discharge();
        assertTrue(b.getCurrentCapacity() == 0);
    }
    
    
}
