package nl.tudelft.bw4t.handicap;

import java.util.ArrayList;

import nl.tudelft.bw4t.robots.AbstractRobot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import repast.simphony.space.continuous.NdPoint;
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
     * Setup mocks + behaviour.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(robotInterface.getSuperParent()).thenReturn(noTarget);
        when(targetRobotInterface.getSuperParent()).thenReturn(yesTarget);
        when(noTarget.getHandicapsList()).thenReturn(new ArrayList<String>());
        when(noTarget.getTargetLocation()).thenReturn(null);
        when(yesTarget.getHandicapsList()).thenReturn(new ArrayList<String>());
        when(yesTarget.getTargetLocation()).thenReturn(target);
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
    }
}
