package nl.tudelft.bw4t.handicap;

import java.util.ArrayList;

import nl.tudelft.bw4t.robots.AbstractRobot;
import nl.tudelft.bw4t.robots.Battery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import repast.simphony.space.SpatialException;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Test the move speed handicap.
 * @author Tim van Rossum
 *
 */
public class MoveSpeedTest {
    /**
     * Mock the robot (has no target location).
     */
    @Mock private AbstractRobot noTarget;
    /**
     * Mock the robot (has a target).
     */
    @Mock private AbstractRobot yesTarget;
    /**
     * Mock the robot (has target, outside of min move
     * distance)
     */
    @Mock private AbstractRobot yesFarAwayTarget;
    /**
     * Mock the robot (has target, collides).
     */
    @Mock private AbstractRobot collidedBot;
    /**
     * Mock its target.
     */
    @Mock private NdPoint target;
    /**
     * Mock IRobot-object.
     */
    @Mock private IRobot robotInterface;
    /**
     * Mock second IRobot-object.
     */
    @Mock private IRobot targetRobotInterface;
    /**
     * Mock third IRobot object.
     */
    @Mock private IRobot notInMinMoveDistance;
    /**
     * Mock fourth IRobot object
     */
    @Mock private IRobot collidedInterface;
    /**
     * Mock a ContinuousSpace-object for use.
     */
    @Mock private ContinuousSpace<Object> mockSpace;
    /**
     * Setup mocks + behaviour.
     */
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(robotInterface.getSuperParent()).thenReturn(noTarget);
        when(targetRobotInterface.getSuperParent()).thenReturn(yesTarget);
        when(notInMinMoveDistance.getSuperParent()).thenReturn(yesFarAwayTarget);
        when(collidedInterface.getSuperParent()).thenReturn(collidedBot);
        when(noTarget.getHandicapsList()).thenReturn(new ArrayList<String>());
        when(noTarget.getTargetLocation()).thenReturn(null);
        when(yesTarget.getHandicapsList()).thenReturn(new ArrayList<String>());
        when(yesTarget.getTargetLocation()).thenReturn(target);
        when(yesTarget.distanceTo((NdPoint) any())).thenReturn(0.0);
        when(yesFarAwayTarget.getSpace()).thenReturn(mockSpace);
        when(yesFarAwayTarget.getHandicapsList()).thenReturn(new ArrayList<String>());
        when(yesFarAwayTarget.getTargetLocation()).thenReturn(target);
        when(yesFarAwayTarget.distanceTo((NdPoint) any())).thenReturn(5.0);
        when(yesFarAwayTarget.getBattery()).thenReturn(new Battery(0, 0));
        when(mockSpace.getDisplacement((NdPoint) any(), (NdPoint) any())).thenReturn(
                new double[] {0.0, 0.0});
        when(collidedBot.getSpace()).thenReturn(mockSpace);
        when(collidedBot.getHandicapsList()).thenReturn(new ArrayList<String>());
        when(collidedBot.getTargetLocation()).thenReturn(target);
        when(collidedBot.distanceTo((NdPoint) any())).thenReturn(5.0);
        when(collidedBot.getBattery()).thenThrow(SpatialException.class);
    }
    /**
     * Test with null targetLocation.
     */
    @Test
    public void noTargetLoc() {
        MoveSpeedHandicap msh = new MoveSpeedHandicap(robotInterface, 1.0);
        msh.move();
        verify(noTarget).getHandicapsList();
        verify(noTarget).getTargetLocation();
        verifyNoMoreInteractions(noTarget);
    }
    /**
     * Test with non-null targetLocation.
     */
    @Test
    public void yesTargetLoc() {
        MoveSpeedHandicap msh = new MoveSpeedHandicap(targetRobotInterface, 1.0);
        msh.move();
        verify(yesTarget).distanceTo((NdPoint) any());
        verify(yesTarget).stopRobot();
    }
    /**
     * Test with non-null target outside
     * of the current reach.
     */
    @Test
    public void yesTargetNotInReachLoc() {
        MoveSpeedHandicap msh = new MoveSpeedHandicap(notInMinMoveDistance, 1.0);
        msh.move();
        verify(yesFarAwayTarget).getLocation();
        verify(yesFarAwayTarget, times(2)).getTargetLocation();
        verify(yesFarAwayTarget).getSpace();
        verify(yesFarAwayTarget).moveByDisplacement(anyDouble(), anyDouble());
        verify(yesFarAwayTarget).getBattery();
    }
    /**
     * Test with a collision.
     */
    @Test
    public void collidedBot() {
        MoveSpeedHandicap msh = new MoveSpeedHandicap(collidedInterface, 1.0);
        msh.move();
        verify(collidedBot).setCollided(true);
        verify(collidedBot).stopRobot();
    }
    /**
     * Test the get speed mod function for coverage.
     */
    @Test
    public void getSpeedModTest() {
        MoveSpeedHandicap msh = new MoveSpeedHandicap(collidedInterface, 1.0);
        assertTrue(msh.getSpeedMod() == 1.0);
    }
}
