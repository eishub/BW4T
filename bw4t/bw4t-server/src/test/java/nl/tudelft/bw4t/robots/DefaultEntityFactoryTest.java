package nl.tudelft.bw4t.robots;

import java.util.ArrayList;

import nl.tudelft.bw4t.agent.EntityType;
import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.model.robots.DefaultEntityFactory;
import nl.tudelft.bw4t.model.robots.handicap.Human;
import nl.tudelft.bw4t.model.robots.handicap.IRobot;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.environment.Launcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 * This class tests the DefaultEntityFactory.
 * Whether robots can be made with or without handicaps properly.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BotConfig.class, Launcher.class})
public class DefaultEntityFactoryTest {
    
    private DefaultEntityFactory factory;
    private ArrayList<String> options;
    
    private BotConfig config = PowerMockito.mock(BotConfig.class);
    
    @Mock
    private BW4TEnvironment env;
    
    @Mock
    private NewMap map;
    
    @Mock
    private Context<Object> context;
    @Mock
    private ContinuousSpace<Object> space;

    /**
     * Set up the test suite.
     */
    @Before
    public void setUp() {
        setUpEnvironment();
        setUpMap();
        setUpConfig();
        setUpFactory();
        setUpOptions();     
    }
    
    @Test
    public void gripperHandicapRobotTest() {
        IRobot r = initializeRobot(makeNonGripper());
        assertEquals(r.getGripperCapacity(), 0);
        assertTrue(r.getHandicapsList().contains("Gripper"));
    }
    
    @Test
    public void speedRobotTest() {
        IRobot r = initializeRobot(makeSpeed());
        assertEquals(r.getSpeedMod(), 0.5, 1);
    }
    
    @Test
    public void sizeRobotTest() {
        IRobot r = initializeRobot(makeSize());
        assertEquals(r.getSize(), 3);
        assertTrue(r.getHandicapsList().contains("SizeOverload"));
    }
    
    @Test
    public void humanRobotTest() {
        IRobot r = initializeRobot(makeHuman());
        assertTrue(r instanceof Human);
        assertTrue(r.getHandicapsList().contains("Human"));
        assertTrue(r.getEPartner() == null);
    }
    
    @Test
    public void batteryRobotTest() {
        IRobot r = initializeRobot(makeBattery());
        assertFalse(r.getBattery() == null);
        assertEquals(r.getBattery().getCurrentCapacity(), 100, 1);
        assertEquals(r.getBattery().getDischargeRate(), 0.1, 1);
        assertEquals(r.getBattery().getPercentage(), 100, 1);
    }
    
    private void setUpEnvironment() {
        PowerMockito.mockStatic(Launcher.class);
        when(Launcher.getEnvironment()).thenReturn(env);
    }
    
    private void setUpMap() {
        when(env.getMap()).thenReturn(map);
        when(map.getOneBotPerCorridorZone()).thenReturn(true);
    }
    
    private void setUpConfig() {
        when(config.getBotName()).thenReturn("Bastiaan");
        when(config.getBotSpeed()).thenReturn(50);
        when(config.getBotSize()).thenReturn(3);
        when(config.getBotBatteryCapacity()).thenReturn(100);
        when(config.getBotBatteryDischargeRate()).thenReturn(0.1);
    }
    
    private void setUpFactory() {
        factory = new DefaultEntityFactory();
        factory.setContext(context);
        factory.setSpace(space);
    }
    
    private void setUpOptions() {
        options = new ArrayList<String>();
    }
    
    private IRobot initializeRobot(ArrayList<String> options) {
        setHandicaps(options);
        IRobot r = factory.makeRobot(config);
        return r;
    }
    
    private ArrayList<String> makeColorBlind() {
        options.add("colorblind"); 
        return options;
    }
    
    private ArrayList<String> makeNonGripper() {
        options.add("gripper");
        return options;
    }
    
    private ArrayList<String> makeSpeed() {
        options.add("speed");
        return options;
    }
    
    private ArrayList<String> makeSize() {
        options.add("size");
        return options;
    }
    
    private ArrayList<String> makeHuman() {
        options.add("human");
        return options;
    }
    
    private ArrayList<String> makeBattery() {
        options.add("battery");
        return options;
    }
    
    private void setHandicaps(ArrayList<String> choices) {
        if (choices.contains("colorblind")) {
            when(config.getColorBlindHandicap()).thenReturn(true);
        }
        if (choices.contains("gripper")) {
            when(config.getGripperHandicap()).thenReturn(true);
        }
        if (choices.contains("speed")) {
            when(config.getMoveSpeedHandicap()).thenReturn(true);
        }
        if (choices.contains("size")) {
            when(config.getSizeOverloadHandicap()).thenReturn(true);
        }
        if (choices.contains("human")) {
            when(config.getBotController()).thenReturn(EntityType.HUMAN);
        }
        if (choices.contains("battery")) {
            when(config.isBatteryEnabled()).thenReturn(true);
        } 
    }
}
