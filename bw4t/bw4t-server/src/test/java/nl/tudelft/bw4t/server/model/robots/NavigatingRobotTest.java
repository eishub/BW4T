package nl.tudelft.bw4t.server.model.robots;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;
import nl.tudelft.bw4t.server.model.BoundedMoveableObject;
import nl.tudelft.bw4t.server.model.blocks.Block;
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
    private ContinuousSpace<Object> mockedOtherSpace = Mockito.mock(ContinuousSpace.class);
    
    private Context<Object> mockedContext = Mockito.mock(Context.class);
    private Context<Object> mockedOtherContext = Mockito.mock(Context.class);
    
    private Queue<NdPoint> mockedQueue = Mockito.mock(ConcurrentLinkedQueue.class);
    private BoundedMoveableObject mockedMovableObject = Mockito.mock(BoundedMoveableObject.class);
    //private BW4TEnvironment mockedEnv = Mockito.mock(BW4TEnvironment.class);
    //private MoveType mockedMoveType = Mockito.mock(MoveType.class);
    
    private NavigatingRobot bot;
    private NavigatingRobot bot2;
    private NavigatingRobot bot3;
    private NavigatingRobot bot4;
    private NavigatingRobot bot5;
    private NavigatingRobot bot6;
    
    private Block b;
    
    @Before
    public void createNavigatingRobot() {
        int cap = 2;
        int cap2 = 1;
        bot = new NavigatingRobot("Bot1", mockedSpace, mockedContext, true, cap);
        bot2 = new NavigatingRobot("Bot2", mockedSpace, mockedOtherContext, true, cap2);
        bot3 = new NavigatingRobot("Bot3", mockedOtherSpace, mockedContext, true, cap2);
        bot4 = null;
        bot5 = new NavigatingRobot("Bot5", null, mockedContext, true, cap2);
        bot6 = new NavigatingRobot("Bot5", null, mockedContext, true, cap2);
        
        b = new Block(BlockColor.BLUE, mockedOtherSpace, mockedContext);
    }

    @Test
    public void stopRobotTest() {      
        assertNotNull(bot);
        bot.setCollided(true);
        bot.stopRobot();
    }
    
    /**
     * TODO: Kijken naar gebruik mockedQueue, 
     * is volgens mij niet nodig bij gebruik van Fieldreflection
     */
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

    @Test
    public void getNameTest() {
        assertEquals(bot.getName(), "Bot1");
    }
    
    @Test
    public void disconnectTest() {
        bot.disconnect();
        assertEquals(bot.isConnected(), false);
    }
    
    @Test
    public void equalseTest() {
        Zone z = new Zone();
        assertEquals(bot.equals(bot2), false);
        assertEquals(bot.equals(bot3), false);
        assertEquals(bot5.equals(bot), false);
        assertEquals(bot.equals(bot4), false);
        assertEquals(bot.equals(z), false);
        
        assertEquals(bot.equals(bot), true);
        assertEquals(bot5.equals(bot6), true);
    }
    
    @Test
    public void hashCodeTest() {
        assertEquals(bot.hashCode(), 1);
    }
    
    @Test
    public void isHoldingTest() {
        List<Block> emptyList = new ArrayList<Block>();;
        assertEquals(bot.isHolding().isEmpty(), emptyList.isEmpty());
    }
    
    @Test
    public void canPickUpTest() {
        assertEquals(bot.canPickUp(bot2), false);
        assertEquals(bot.canPickUp(b), true);
    }
    
    @Test
    public void pickUpTest() {
        assertEquals(b.isFree(), true);
        bot.pickUp(b);
        assertEquals(b.getHeldBy(), bot);
    }
    
/*    @Test
    public void dropTest() {
        bot.pickUp(b);
        bot.drop();
    }
    
*/
    
    @Test
    public void dropIntAssertTest() {
        bot.drop(5);
    }
    
    /**
     * TODO: Kijken of dit kan.
     */
/*    @Test
    public void moveToTest() {
        when(bot.getMoveType(any(double.class), any(double.class))).thenReturn(mockedMoveType);
        bot.moveTo(1, 1);
    }
*/    
}
