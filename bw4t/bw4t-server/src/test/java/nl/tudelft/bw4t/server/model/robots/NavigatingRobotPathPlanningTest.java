package nl.tudelft.bw4t.server.model.robots;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import nl.tudelft.bw4t.server.model.zone.Room;
import nl.tudelft.bw4t.server.model.zone.Zone;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.continuous.NdPoint;

public class NavigatingRobotPathPlanningTest {

    private static List<Zone> graph = new ArrayList<>(3 * 3);

    private static Zone[][] zones = new Zone[3][3];

    /**
     * We create a 3x3 Grid of Zones with a room in the center that connects to the top.
     */
    @BeforeClass
    public static void setupNavigationGrid() {
        for (int row = 0; row < zones.length; row++) {
            for (int col = 0; col < zones[row].length; col++) {
                Zone zone = mock(Zone.class);
                if(col == 1 && row == 1) {
                    zone = mock(Room.class);
                }
                when(zone.getLocation()).thenReturn(new NdPoint((row + 1) * 5, (col + 1) * 5));
                zones[row][col] = zone;
                graph.add(zone);
            }
        }
    }
    
    @Before
    public void setupConnections() {
        when(zones[0][0].getNeighbours()).thenReturn(new HashSet<>(Arrays.asList(zones[0][1], zones[1][0])));
        when(zones[0][1].getNeighbours()).thenReturn(
                new HashSet<>(Arrays.asList(zones[0][2], zones[1][1], zones[0][0])));
        when(zones[0][2].getNeighbours()).thenReturn(new HashSet<>(Arrays.asList(zones[1][2], zones[0][1])));

        when(zones[1][0].getNeighbours()).thenReturn(new HashSet<>(Arrays.asList(zones[0][0], zones[2][0])));
        when(zones[1][1].getNeighbours()).thenReturn(new HashSet<>(Arrays.asList(zones[0][1])));
        when(zones[1][2].getNeighbours()).thenReturn(new HashSet<>(Arrays.asList(zones[0][2], zones[2][2])));

        when(zones[2][0].getNeighbours()).thenReturn(new HashSet<>(Arrays.asList(zones[1][0], zones[2][1])));
        when(zones[2][1].getNeighbours()).thenReturn(new HashSet<>(Arrays.asList(zones[2][2], zones[2][0])));
        when(zones[2][2].getNeighbours()).thenReturn(new HashSet<>(Arrays.asList(zones[1][2], zones[2][1])));
    }

    public static void assertEqual(Queue<NdPoint> expected, Queue<NdPoint> actual) {
        String error = String.format("expected: %s but was: %s", expected, actual);
        assertEquals(error, expected.size(), actual.size());

        Iterator<NdPoint> iexpect = expected.iterator();
        Iterator<NdPoint> iactual = actual.iterator();
        while (iexpect.hasNext()) {
            NdPoint e = iexpect.next();
            NdPoint a = iactual.next();
            assertTrue(error, e.equals(a));
        }
    }

    @Test
    public void testSameZone() {
        final Zone zone = zones[0][0];
        final NdPoint zLocation = zone.getLocation();
        NdPoint begin = new NdPoint(zLocation.getX() + 2, zLocation.getY() + 1);
        NdPoint end = new NdPoint(zLocation.getX() + 3, zLocation.getY() - 4);

        Queue<NdPoint> expected = new ArrayDeque<>();
        expected.add(end);

        Queue<NdPoint> actual = NavigatingRobot.planPath(begin, end, zone, zone, graph);

        assertEqual(expected, actual);
    }

    @Test
    public void testNextDirectZone() {
        NdPoint begin = new NdPoint(zones[0][0].getLocation().getX(), zones[0][0].getLocation().getY());
        NdPoint end = new NdPoint(zones[0][1].getLocation().getX() + 3, zones[0][1].getLocation().getY() - 4);

        Queue<NdPoint> expected = new ArrayDeque<>();
        expected.add(end);

        Queue<NdPoint> actual = NavigatingRobot.planPath(begin, end, zones[0][0], zones[0][1], graph);

        assertEqual(expected, actual);
    }

    @Test
    public void testNextNextZone() {
        NdPoint begin = new NdPoint(zones[0][0].getLocation().getX(), zones[0][0].getLocation().getY());
        NdPoint end = new NdPoint(zones[1][1].getLocation().getX() + 1, zones[1][1].getLocation().getY() - 1);

        Queue<NdPoint> expected = new ArrayDeque<>();
        expected.add(zones[0][1].getLocation());
        expected.add(zones[1][1].getLocation());
        expected.add(end);

        Queue<NdPoint> actual = NavigatingRobot.planPath(begin, end, zones[0][0], zones[1][1], graph);

        assertEqual(expected, actual);
    }

    @Test
    public void testRoundZone() {
        final Zone bzone = zones[2][0];
        final Zone ezone = zones[1][1];
        NdPoint begin = new NdPoint(bzone.getLocation().getX(), bzone.getLocation().getY());
        NdPoint end = new NdPoint(ezone.getLocation().getX() + 1, ezone.getLocation().getY() - 1);

        Queue<NdPoint> expected = new ArrayDeque<>();
        expected.add(zones[1][0].getLocation());
        expected.add(zones[0][0].getLocation());
        expected.add(zones[0][1].getLocation());
        expected.add(ezone.getLocation());
        expected.add(end);

        Queue<NdPoint> actual = NavigatingRobot.planPath(begin, end, bzone, ezone, graph);

        assertEqual(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoConnectionZone() {
        final Zone bzone = zones[2][0];
        final Zone ezone = zones[1][1];
        NdPoint begin = new NdPoint(bzone.getLocation().getX(), bzone.getLocation().getY());
        NdPoint end = new NdPoint(ezone.getLocation().getX() + 1, ezone.getLocation().getY() - 1);

        // disconnect the middle zone
        when(zones[0][1].getNeighbours()).thenReturn(new HashSet<Zone>(Arrays.asList(zones[0][2], zones[0][0])));
        when(ezone.getNeighbours()).thenReturn(new HashSet<Zone>());

        NavigatingRobot.planPath(begin, end, bzone, ezone, graph);
    }

    @Test
    public void testBreakRoom() {
        final Zone bzone = zones[1][1];
        final Zone ezone = zones[0][1];
        NdPoint begin = new NdPoint(bzone.getLocation().getX() - 1, bzone.getLocation().getY() + 3);
        NdPoint end = new NdPoint(ezone.getLocation().getX() + 1, ezone.getLocation().getY() - 1);
        
        assertTrue(bzone instanceof Room);

        Queue<NdPoint> expected = new ArrayDeque<>();
        expected.add(bzone.getLocation());
        expected.add(ezone.getLocation());
        expected.add(end);

        Queue<NdPoint> actual = NavigatingRobot.planPath(begin, end, bzone, ezone, graph);

        assertEqual(expected, actual);
    }
    
}
