package nl.tudelft.bw4t.server.model.robots;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.robots.NavigatingRobot.State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;

@RunWith(MockitoJUnitRunner.class)
public class NavigatingRobotTest {
    
    private ContinuousSpace<Object> mockedSpace = Mockito.mock(ContinuousSpace.class);
    private Context<Object> mockedContext = Mockito.mock(Context.class);
    private Queue<NdPoint> mockedQueue = Mockito.mock(ConcurrentLinkedQueue.class);
    private BoundedMoveableObject mockedMovableObject = Mockito.mock(BoundedMoveableObject.class);
    
    private NavigatingRobot bot;
    
    @Before
    public void createNavigatingRobot() {
        int cap = 2;
        String name = "Bot1";
        bot = new NavigatingRobot(name, mockedSpace, mockedContext, true, cap);
    }

    @Test
    public void stopRobotTest() {      
        assertNotNull(bot);
        bot.setCollided(true);
        bot.stopRobot();
        
    }
    
    @Test
    public void useNextTargetNotEmptyTest() {
        when(mockedQueue.isEmpty()).thenReturn(false);
        
        bot.useNextTarget();
    }
    
    @Test
    public void getStateCollidedTest() {
        bot.setCollided(true);
        assertEquals(bot.getState(), State.COLLIDED);
    }
    
    @Test
    public void getStateTravelingTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        double point = 0.5;
        NdPoint ppoint = new NdPoint(point);
        Field move = NavigatingRobot.class.getDeclaredField("currentMove");
        move.setAccessible(true);
        move.set(bot, ppoint);
        assertEquals(bot.getState(), State.TRAVELING);
    }
    
    @Test
    public void getStateOtherTest() {
        assertEquals(bot.getState(), State.ARRIVED);
    }


}
