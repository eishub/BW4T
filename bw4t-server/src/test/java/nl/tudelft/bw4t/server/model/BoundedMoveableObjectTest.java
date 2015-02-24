package nl.tudelft.bw4t.server.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.awt.geom.Rectangle2D;

import nl.tudelft.bw4t.map.NewMap;
import nl.tudelft.bw4t.server.model.doors.Door;
import nl.tudelft.bw4t.server.repast.MapLoader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.projection.Projection;

/**
 * The class <code>BoundedMoveableObjectTest</code> contains tests for the class
 * <code>{@link BoundedMoveableObject}</code>.
 */
@RunWith(MockitoJUnitRunner.class)
public class BoundedMoveableObjectTest {
    private static final double DELTA = 1e-15;
    @Mock
    private ContinuousSpace<Object> space;
    @Mock
    private Grid<Object> grid;
    @Mock
    private Context<Object> context;
    @Mock
    private NdPoint point;
    @Mock
    private NewMap map;
    private BW4TServerMap smap;

    @Before
    public void setup() {
        smap = spy(new BW4TServerMap(map, context));
        when(smap.getContinuousSpace()).thenReturn(space);
        when(smap.getGridSpace()).thenReturn(grid);
    }

    /**
     * Run the void addToContext() method test.
     */
    @Test
    public void testAddToContext_1() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);

        fixture.addToContext();

        // add additional test code here
        verify(context, times(2)).add(any());
    }

    /**
     * Run the double distanceTo(BoundedMoveableObject) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testDistanceTo_1() throws Exception {
        when(space.getLocation(any())).thenReturn(point);
        BoundedMoveableObject fixture = new Door(smap);
        BoundedMoveableObject o = new Door(smap);

        double result = fixture.distanceTo(o);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        // java.lang.NullPointerException
        // at nl.tudelft.bw4t.BoundedMoveableObject.distanceTo(BoundedMoveableObject.java:156)
        // at nl.tudelft.bw4t.BoundedMoveableObject.distanceTo(BoundedMoveableObject.java:169)
        assertEquals(0.0, result, 0.1);
    }

    /**
     * Run the double distanceTo(NdPoint) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testDistanceTo_2() throws Exception {
        when(space.getLocation(any())).thenReturn(point);
        BoundedMoveableObject fixture = new Door(smap);
        NdPoint there = new NdPoint(0, 0);

        double result = fixture.distanceTo(there);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        // java.lang.ArrayIndexOutOfBoundsException: 0
        // at repast.simphony.space.continuous.NdPoint.getX(NdPoint.java:31)
        // at nl.tudelft.bw4t.BoundedMoveableObject.distanceTo(BoundedMoveableObject.java:156)
        assertEquals(0.0, result, 0.1);
    }

    /**
     * Run the boolean equals(Object) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testEquals_1() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);
        Object obj = new Door(smap);
        assertTrue(fixture.equals(obj));
    }

    /**
     * Run the boolean equals(Object) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testEquals_2() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);
        Object obj = null;
        assertFalse(fixture.equals(obj));
    }

    /**
     * Run the boolean equals(Object) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testEquals_3() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);
        Object obj = new Object();
        assertFalse(fixture.equals(obj));
    }

    /**
     * Run the boolean equals(Object) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testEquals_4() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);
        Object obj = new Door(smap);
        assertTrue(fixture.equals(obj));
    }

    @Test
    public void testEquals_5() throws Exception {
        BoundedMoveableObject boundedMoveableObject = new Door(smap);
        Object obj = boundedMoveableObject;
        boolean result = boundedMoveableObject.equals(obj);
        assertEquals(true, result);
    }

    @Test
    public void testEquals_6() throws Exception {
        when(context.size()).thenReturn(1);
        BoundedMoveableObject boundedMoveableObject = new Door(smap);
        Object obj = new Door(smap);
        assertTrue(boundedMoveableObject.equals(obj));
    }

    @Test
    public void testEquals_7() throws Exception {
        BoundedMoveableObject boundedMoveableObject = new Door(smap);
        boundedMoveableObject.setSize(2, 3);
        Object obj = new Door(smap);
        boolean result = boundedMoveableObject.equals(obj);
        assertEquals(false, result);
    }

    /**
     * Run the Rectangle2D getBoundingBox() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testGetBoundingBox_1() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);

        Rectangle2D result = fixture.getBoundingBox();

        // add additional test code here
        assertNotNull(result);
        assertEquals(true, result.isEmpty());
        assertEquals(0.0, result.getY(), DELTA);
        assertEquals(0.0, result.getX(), DELTA);
        assertEquals(0.0, result.getHeight(), DELTA);
        assertEquals(0.0, result.getWidth(), DELTA);
        assertEquals(0.0, result.getCenterX(), DELTA);
        assertEquals(0.0, result.getCenterY(), DELTA);
        assertEquals(0.0, result.getMaxX(), DELTA);
        assertEquals(0.0, result.getMaxY(), DELTA);
        assertEquals(0.0, result.getMinX(), DELTA);
        assertEquals(0.0, result.getMinY(), DELTA);
    }

    /**
     * Run the Context<Object> getContext() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testGetContext_1() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);

        Context<Object> result = fixture.getContext();

        // add additional test code here
        assertNotNull(result);
        assertEquals(context, result);
    }

    /**
     * Run the long getId() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testGetId_1() throws Exception {
        when(context.size()).thenReturn(1);
        BoundedMoveableObject fixture = new Door(smap);
        BoundedMoveableObject fixture2 = new Door(smap);

        long result = fixture.getId();
        long result2 = fixture2.getId();

        // add additional test code here
        assertTrue(result >= 0);
        assertTrue(result2 >= 0);
        assertNotEquals(result2, result);
    }

    /**
     * Run the NdPoint getLocation() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testGetLocation_1() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);

        when(space.getLocation(any())).thenReturn(point);

        NdPoint result = fixture.getLocation();

        // add additional test code here
        assertEquals(point, result);
    }

    /**
     * Run the ContinuousSpace<Object> getSpace() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testGetSpace_1() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);

        ContinuousSpace<Object> result = fixture.getSpace();

        // add additional test code here
        assertEquals(space, result);
    }

    /**
     * Run the int hashCode() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testHashCode_1() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);

        int result = fixture.hashCode();

        // add additional test code here
        assertEquals(1, result);
    }

    /**
     * Run the void moveTo(double,double) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testMoveTo_1() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);
        double x = 1.0;
        double y = 1.0;

        fixture.moveTo(x, y);

        Rectangle2D b = fixture.getBoundingBox();
        assertEquals(b.getX(), x - b.getWidth() / 2, DELTA);
        assertEquals(b.getY(), y - b.getHeight() / 2, DELTA);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        // java.lang.IllegalArgumentException: Object 'nl.tudelft.bw4t.doors.Door@1' must be added to the space's
        // context before it can be moved
        // at repast.simphony.space.continuous.AbstractContinuousSpace.doMove(AbstractContinuousSpace.java:129)
        // at repast.simphony.space.continuous.AbstractContinuousSpace.moveTo(AbstractContinuousSpace.java:121)
        // at nl.tudelft.bw4t.BoundedMoveableObject.moveTo(BoundedMoveableObject.java:107)
    }

    /**
     * Run the void removeFromContext() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testRemoveFromContext_1() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);

        fixture.removeFromContext();

        // add additional test code here
        verify(context).remove(any());
    }

    /**
     * Run the void setSize(double,double) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Test
    public void testSetSize_1() throws Exception {
        BoundedMoveableObject fixture = new Door(smap);
        double width = 1.0;
        double height = 1.0;

        fixture.setSize(width, height);

        // add additional test code here
        Rectangle2D b = fixture.getBoundingBox();
        assertEquals(b.getWidth(), width, DELTA);
        assertEquals(b.getHeight(), height, DELTA);
    }

    /**
     * Perform pre-test initialization.
     * 
     * @throws Exception
     *             if the initialization fails for some reason
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @Before
    public void setUp() throws Exception {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     * 
     * @throws Exception
     *             if the clean-up fails for some reason
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    @After
    public void tearDown() throws Exception {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     * 
     * @param args
     *            the command line arguments
     * 
     * @generatedBy CodePro at 23.05.14 16:47
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(BoundedMoveableObjectTest.class);
    }
}
