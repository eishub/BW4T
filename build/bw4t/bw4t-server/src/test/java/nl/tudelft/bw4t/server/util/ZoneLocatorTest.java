package nl.tudelft.bw4t.server.util;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import nl.tudelft.bw4t.server.environment.BW4TEnvironment;
import nl.tudelft.bw4t.server.model.zone.Zone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.space.continuous.NdPoint;

/** Tests the functions in the utility class ZoneLocator. */
public class ZoneLocatorTest {

    @Mock private static BW4TEnvironment env = mock(BW4TEnvironment.class);
    @Mock private static Zone ZONE1 = mock(Zone.class);
    @Mock private static Zone ZONE2 = mock(Zone.class);
    @Mock private static Zone ZONE3 = mock(Zone.class);

    /** A minor offset is done on certain elements in order to fulfill requirements of the definition of insideness. */
    private static double OFFSET = 1E-5;
    private static String ZONE1_NAME = "Zone1";
    private static String ZONE2_NAME = "Zone2";
    private static String ZONE3_NAME = "Zone3";
    
    private static Context<Zone> context = new DefaultContext<Zone>();
    
    @BeforeClass
    public static void setUp() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = BW4TEnvironment.class.getDeclaredMethod("setInstance", BW4TEnvironment.class);
        method.setAccessible(true);
        method.invoke(null, env);
        when(env.getContext()).thenReturn(context);

        context.add(ZONE1);
        when(ZONE1.distanceTo(any(NdPoint.class))).thenCallRealMethod();
        when(ZONE1.getBoundingBox()).thenReturn(new Rectangle(0, 0, 1, 1));
        when(ZONE1.getLocation()).thenReturn(new NdPoint(0.5, 0.5));
        when(ZONE1.getName()).thenReturn(ZONE1_NAME);

        context.add(ZONE2);
        when(ZONE2.distanceTo(any(NdPoint.class))).thenCallRealMethod();
        when(ZONE2.getBoundingBox()).thenReturn(new Rectangle(1, 0, 2, 2));
        when(ZONE2.getLocation()).thenReturn(new NdPoint(2, 1));
        when(ZONE2.getName()).thenReturn(ZONE2_NAME);

        context.add(ZONE3);
        when(ZONE3.distanceTo(any(NdPoint.class))).thenCallRealMethod();
        when(ZONE3.getBoundingBox()).thenReturn(new Rectangle(2, 0, 1, 1));
        when(ZONE3.getLocation()).thenReturn(new NdPoint(2.5, 0.5));
        when(ZONE3.getName()).thenReturn(ZONE3_NAME);
    }

    /** Tests {@link ZoneLocator#getZoneAt(double, double) at a valid location. */
    @Test
    public void getZoneAtTest() {
        assertTrue(ZoneLocator.getZoneAt(0, 0).equals(ZONE1));
    }

    /** Tests {@link ZoneLocator#getZoneAt(double, double)} at an empty location. */
    @Test
    public void getZoneAtNullTest() {
        assertTrue(ZoneLocator.getZoneAt(-1, -1) == null);
    }

    /** Test {@link ZoneLocator#getZoneAt(NdPoint)} at a valid location. */
    @Test
    public void getZoneAtLocTest() {
        assertTrue(ZoneLocator.getZoneAt(new NdPoint(1, 1)).equals(ZONE2));
    }

    /** Test {@link ZoneLocator#getZoneAt(NdPoint)} at an empty location. */
    @Test
    public void getZoneAtLocNullTest() {
        assertTrue(ZoneLocator.getZoneAt(new NdPoint(2, 2)) == null);
    }

    /** Test {@link ZoneLocator#getZone(String)} method with valid names. */
    @Test
    public void getZoneByNameTest() {
        assertTrue(ZoneLocator.getZone(ZONE1_NAME).equals(ZONE1));
        assertTrue(ZoneLocator.getZone(ZONE2_NAME).equals(ZONE2));
        assertTrue(ZoneLocator.getZone(ZONE3_NAME).equals(ZONE3));
    }

    /** Tests {@link ZoneLocator#getZone(String)} with an empty String. */
    @Test
    public void getZoneByNameNullTest() {
        assertTrue(ZoneLocator.getZone("") == null);
    }

    /** Tests {@link ZoneLocator#getZonesAt(double, double)} at a location of a single Zone.*/
    @Test
    public void getZonesAtTest1() {
        List<Zone> zones = ZoneLocator.getZonesAt(1, 0);
        assertTrue(zones.size() == 1 && zones.get(0).equals(ZONE2));
    }

    /** Tests {@link ZoneLocator#getZonesAt(double, double)} at a location where 2 Zones overlap. */
    @Test
    public void getZonesAtTest2() {
        List<Zone> zones = ZoneLocator.getZonesAt(2, 0);
        assertTrue(zones.size() == 2
                && ((zones.get(0).equals(ZONE2) && zones.get(1).equals(ZONE3))
                || (zones.get(0).equals(ZONE3) && zones.get(1).equals(ZONE2))));
    }

    /** Tests {@link ZoneLocator#getZonesAt(double, double) at an empty location. */
    @Test
    public void getZonesAtNullTest() {
        assertTrue(ZoneLocator.getZonesAt(-1, -1).size() == 0);
    }

    /** Tests {@link ZoneLocator#getNearestZone(NdPoint)} with a point in a Zone. */
    @Test
    public void getNearestZoneAtTest() {
        assertTrue(ZoneLocator.getNearestZone(new NdPoint(0, 0)).equals(ZONE1));
    }

    /** Tests {@link ZoneLocator#getNearestZone(NdPoint)} with a point near overlapping Zones. */
    @Test
    public void getNearestZoneNearTest1() {
        assertTrue(ZoneLocator.getNearestZone(new NdPoint(1, 2)).equals(ZONE2));
    }

    /** Tests {@link ZoneLocator#getNearestZone(NdPoint)} with a point near overlapping Zones (opposite end). */
    @Test
    public void getNearestZoneNearTest2() {
        assertTrue(ZoneLocator.getNearestZone(new NdPoint(3, 0)).equals(ZONE3));
    }
}
