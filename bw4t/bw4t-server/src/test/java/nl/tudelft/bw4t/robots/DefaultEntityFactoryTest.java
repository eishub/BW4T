package nl.tudelft.bw4t.robots;

import java.util.ArrayList;

import nl.tudelft.bw4t.agent.EntityType;
import nl.tudelft.bw4t.handicap.IRobot;
import nl.tudelft.bw4t.scenariogui.BotConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * This class tests the DefaultEntityFactory.
 * Whether robots can be made with or without handicaps properly.
 * Whether e-Partners can be made with the right settings. 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(BotConfig.class)
public class DefaultEntityFactoryTest {
    
    private DefaultEntityFactory factory;
    private ArrayList<String> choices;
    
    private BotConfig config = PowerMockito.mock(BotConfig.class);

    /**
     * Set up the test suite.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        factory = new DefaultEntityFactory();
        choices = new ArrayList<String>();
    }
    
    @Test
    public void makeColorBlindRobotTest() {
        setHandicaps(makeNonGripper());
        IRobot r = factory.makeRobot(config);
        //assertEquals(r.getGripperCapacity(), 0);
    }
   
    private ArrayList<String> makeColorBlind() {
        choices.add("colorblind");
        
        return choices;
    }
    
    private ArrayList<String> makeNonGripper() {
        choices.add("gripper");
        
        return choices;
    }
    
    private void setHandicaps(ArrayList<String> choices) {
        if (choices.contains("colorblind")) {
            when(config.getColorBlindHandicap()).thenReturn(true);
        }
        if (choices.contains("gripper")) {
            when(config.getGripperHandicap()).thenReturn(true);
            debug("" + config.getGripperHandicap());
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
    
    private void debug(String s) {
        System.out.println(s);
    }
}
