package nl.tudelft.bw4t.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>PointTest</code> contains tests for the class <code>{@link Point}</code>.
 */
public class PointTest {
    /**
     * Run the Point() constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testPoint_1()
        throws Exception {

        Point result = new Point();

        // add additional test code here
        assertNotNull(result);
        assertEquals("Point2D.Double[0.0, 0.0]", result.toString());
        assertEquals(0.0, result.getX(), 1.0);
        assertEquals(0.0, result.getY(), 1.0);
    }

    /**
     * Run the Point(double,double) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testPoint_withValues()
        throws Exception {
        double newx = 2.0;
        double newy = 2.0;

        Point result = new Point(newx, newy);

        // add additional test code here
        assertNotNull(result);
        assertEquals("Point2D.Double[2.0, 2.0]", result.toString());
        assertEquals(2.0, result.getX(), 0.1);
        assertEquals(2.0, result.getY(), 0.1);
    }

    /**
     * Run the Point2D asPoint2D() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testAsPoint2D()
        throws Exception {
        Point fixture = new Point();

        Point2D result = fixture.getPoint2D();

        // add additional test code here
        assertNotNull(result);
        assertEquals(0.0, result.getX(), 1.0);
        assertEquals(0.0, result.getY(), 1.0);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testEquals_1()
        throws Exception {
        Point fixture = new Point();
        Object obj = fixture;

        boolean result = fixture.equals(obj);

        // add additional test code here
        assertEquals(true, result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testEquals_2()
        throws Exception {
        Point fixture = new Point();
        Object obj = null;

        boolean result = fixture.equals(obj);

        // add additional test code here
        assertEquals(false, result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testEquals_3()
        throws Exception {
        Point fixture = new Point();
        Object obj = new Object();

        boolean result = fixture.equals(obj);

        // add additional test code here
        assertEquals(false, result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testEquals_4()
        throws Exception {
        Point fixture = new Point();
        Object obj = new Point();

        boolean result = fixture.equals(obj);

        // add additional test code here
        assertEquals(true, result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testEquals_5()
        throws Exception {
        Point fixture = new Point();
        Object obj = new Point(1,1);

        boolean result = fixture.equals(obj);

        // add additional test code here
        assertEquals(false, result);
    }

    /**
     * Run the boolean equals(Object) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testEquals_6()
        throws Exception {
        Point fixture = new Point();
        Object obj = new Point();

        boolean result = fixture.equals(obj);

        // add additional test code here
        assertEquals(true, result);
    }

    /**
     * Run the double getX() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testGetSetX()
        throws Exception {
        Point fixture = new Point();

        // add additional test code here
        assertEquals(0.0, fixture.getX(), 0.1);

        fixture.setX(1.0);

        assertEquals(1.0, fixture.getX(), 0.1);
    }

    /**
     * Run the double getY() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testGetSetY()
        throws Exception {
        Point fixture = new Point();

        assertEquals(0.0, fixture.getY(), 0.1);

        fixture.setY(1.0);

        assertEquals(1.0, fixture.getY(), 0.1);
    }

    /**
     * Run the int hashCode() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testHashCode_1()
        throws Exception {
        Point fixture = new Point();

        int result = fixture.hashCode();

        // add additional test code here
        assertEquals(0, result);
    }

    /**
     * Run the String toString() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Test
    public void testToString_1()
        throws Exception {
        Point fixture = new Point();

        String result = fixture.toString();

        // add additional test code here
        assertEquals("Point2D.Double[0.0, 0.0]", result);
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @Before
    public void setUp()
        throws Exception {
        // add additional set up code here
    }

    /**
     * Perform post-test clean-up.
     *
     * @throws Exception
     *         if the clean-up fails for some reason
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    @After
    public void tearDown()
        throws Exception {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     *
     * @param args the command line arguments
     *
     * @generatedBy CodePro at 25.05.14 11:53
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(PointTest.class);
    }
}