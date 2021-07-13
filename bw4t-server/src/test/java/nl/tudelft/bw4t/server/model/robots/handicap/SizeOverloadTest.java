package nl.tudelft.bw4t.server.model.robots.handicap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.bw4t.server.model.doors.Door;
import nl.tudelft.bw4t.server.model.robots.AbstractRobot;
import nl.tudelft.bw4t.server.model.robots.MoveType;
import nl.tudelft.bw4t.server.model.zone.Corridor;
import nl.tudelft.bw4t.server.model.zone.Zone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Testing functionalities of the handicaps.
 */
public class SizeOverloadTest {
    /**
     * Mocked big robot object.
     */
    @Mock private IRobot bigRobotMock;
    /**
     * Second mocked big robot object
     * to return the other abstract robot.
     */
    @Mock private IRobot bigRobotMock2;
    /**
     * Mocked small robot object.
     */
    @Mock private IRobot smallRobotMock;
    /**
     * Mocked abstract robot object (big).
     */
    @Mock private AbstractRobot bigAbstractMock;
    /**
     * Second mocked big abstract robot
     * object, with different behaviour.
     */
    @Mock private AbstractRobot bigAbstractMock2;
    /**
     * Mocked abstract robot object (small).
     */
    @Mock private AbstractRobot smallAbstractMock;
    /**
     * Mock the start zone.
     */
    @Mock private Zone start;
    /**
     * Mock the end zone.
     */
    @Mock private Zone end;
    /**
     * Mock a corridor.
     */
    @Mock private Corridor corridor;
    /**
     * Mock the door
     */
    @Mock private Door door;
    /**
     * Set up mocks and behaviour.
     */
    @Before
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
        when(bigRobotMock.getSuperParent()).thenReturn(bigAbstractMock);
        when(bigRobotMock2.getSuperParent()).thenReturn(bigAbstractMock2);
        when(bigAbstractMock.getSize()).thenReturn(5);
        when(bigAbstractMock2.getSize()).thenReturn(5);
        when(smallRobotMock.getSuperParent()).thenReturn(smallAbstractMock);
        when(smallAbstractMock.getSize()).thenReturn(3);
        when(bigAbstractMock.isOneBotPerZone()).thenReturn(false);
        when(bigAbstractMock2.isOneBotPerZone()).thenReturn(true);
        when(corridor.containsMeOrNothing(bigAbstractMock2)).thenReturn(false);
    }
    /**
     * Test for behaviour when a robot is too big.
     */
    @Test
    public void sizeTooBigTest() {
        SizeOverloadHandicap soh = new SizeOverloadHandicap(bigRobotMock, 5);
        assertEquals(soh.checkZoneAccess(start, end, door), MoveType.ENTERING_FREESPACE);
    }
    /**
     * Test for getSize()
     */
    @Test
    public void getSizeTest() {
        SizeOverloadHandicap soh = new SizeOverloadHandicap(bigRobotMock, 5);
        assertEquals(soh.getSize(), 5);
    }
    /**
     * Test for behaviour when a robot is too big,
     * and both zones are the same.
     */
    @Test
    public void zonesTheSameTest() {
        SizeOverloadHandicap soh = new SizeOverloadHandicap(bigRobotMock, 5);
        assertEquals(soh.checkZoneAccess(null, null, door), MoveType.SAME_AREA);
    }
    /**
     * Test for behaviour when a robot is small (size < 4).
     */
    @Test
    public void sizeTooSmaalTest() {
        SizeOverloadHandicap soh = new SizeOverloadHandicap(smallRobotMock, 3);
        soh.checkZoneAccess(start, end, door);
        verify(smallRobotMock).checkZoneAccess(start, end, door);
    }
    /**
     * Test with a corridor.
     */
    @Test
    public void corridorTest() {
        SizeOverloadHandicap soh = new SizeOverloadHandicap(bigRobotMock, 5);
        assertEquals(soh.checkZoneAccess(start, corridor, door), MoveType.ENTER_CORRIDOR);
    }
    /**
     * Test with a corridor, and the robot return true if only one
     * bot per zone is allowed, and containsMeOrNothing() returns false.
     */
    @Test
    public void corridorTest2() {
        SizeOverloadHandicap soh = new SizeOverloadHandicap(bigRobotMock2, 5);
        assertEquals(soh.checkZoneAccess(start, corridor, door), MoveType.HIT_OCCUPIED_ZONE);
    }
}
