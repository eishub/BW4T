package nl.tudelft.bw4t.server.eis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.model.blocks.Block;
import nl.tudelft.bw4t.server.model.robots.handicap.IRobot;
import nl.tudelft.bw4t.server.model.zone.Room;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.space.continuous.NdPoint;

public class RobotEntityTest {

    private IRobot mockRobot = Mockito.mock(IRobot.class);
    private RobotEntity robot; 
    private BW4TEnvironment env = Mockito.mock(BW4TEnvironment.class); 
    private Context<Room> context = new DefaultContext<Room>();
    private Method method; 
    private Method method2; 
    private Method method3;
    private Field field;
    private Room mockRoom = Mockito.mock(Room.class);
    
    @Before 
    public void createRobotEntity() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        robot = new RobotEntity(mockRobot);
        when(mockRobot.getLocation()).thenReturn(new NdPoint(1, 1));
        
        method = robot.getClass().getDeclaredMethod("getVisible", Class.class);
        method.setAccessible(true); 
        
        method3 = robot.getClass().getDeclaredMethod("getSizes", null);
        method3.setAccessible(true); 
        
        method2 = BW4TEnvironment.class.getDeclaredMethod("setInstance", BW4TEnvironment.class);
        method2.setAccessible(true);
        method2.invoke(null, env);
        when(env.getContext()).thenReturn(context);
        
       
         field = RobotEntity.class.getDeclaredField("ourRobotRoom");
        field.setAccessible(true);
       
    }
    
    
    @Test
    public void getRobotTest() {
        assertEquals(mockRobot, robot.getRobotObject());
    }
    
    @Test 
    public void connectTest() {
        robot.connect();
        Mockito.verify(mockRobot).connect();
        robot.reset(); 
        Mockito.verify(mockRobot).drop();
        Mockito.verify(mockRobot).moveTo(1, 1);
    }
    
    @Test
    public void disconnectTest(){
        robot.connect();
        robot.disconnect();
        Mockito.verify(mockRobot).disconnect();
//        Mockito.verify(mockRobot).
    }
    
    @Test
    public void getVisibleBlocksTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
            assertEquals(new HashSet<Block>(), method.invoke(robot, Block.class));
            robot.initializePerceptionCycle();
            field.set(robot, mockRoom);
            assertNotNull(mockRoom);
            when(robot.getRoom()).thenReturn("in");
            assertNotNull(robot.getRoom());
                  
    }
    
    // getSize is not testable
    // getAt is not testable 
   
}
