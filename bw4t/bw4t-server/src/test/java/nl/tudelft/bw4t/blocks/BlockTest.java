package nl.tudelft.bw4t.blocks;

import static org.mockito.Mockito.*;

import java.awt.Color;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.context.SmallDefaultContext;
import repast.simphony.space.continuous.DefaultContinuousSpace;
import nl.tudelft.bw4t.map.BlockColor;
import repast.simphony.space.continuous.NdPoint;
import nl.tudelft.bw4t.robots.AbstractRobot;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * The class <code>BlockTest</code> contains tests for the class <code>{@link Block}</code>.
 */
@RunWith(MockitoJUnitRunner.class)
public class BlockTest {

    @Mock private ContinuousSpace<Object> space;
    @Mock private Context<Object> context;
    @Mock private AbstractRobot robot;
    
    /**
     * Run the Block(BlockColor,ContinuousSpace<Object>,Context<Object>) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 24.05.14 19:21
     */
    @Test
    public void testBlock_1()
        throws Exception {
        BlockColor colorId = BlockColor.BLUE;
        when(context.isEmpty()).thenReturn(true);
        Block result = new Block(colorId, space, context);

        assertNotNull(result);
        assertEquals(new NdPoint(0, 0), result.getLocation());
        assertTrue(result.isFree());
        assertEquals(null, result.getHeldBy());
        assertEquals(0L, result.getId());
    }

    /**
     * Run the Color getColor() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 24.05.14 19:21
     */
    @Test
    public void testGetColor_1()
        throws Exception {
        Block fixture = new Block(BlockColor.BLUE, space, context);

        Color result = fixture.getColor();

        // add additional test code here
        assertNotNull(result);
        assertEquals(0, result.getGreen());
        assertEquals(255, result.getBlue());
        assertEquals(0, result.getRed());
        assertEquals(-16776961, result.getRGB());
        assertEquals(255, result.getAlpha());
        assertEquals(1, result.getTransparency());
    }

    /**
     * Run the BlockColor getColorId() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 24.05.14 19:21
     */
    @Test
    public void testGetColorId_1()
        throws Exception {
        Block fixture = new Block(BlockColor.BLUE, space, context);

        BlockColor result = fixture.getColorId();

        // add additional test code here
        assertNotNull(result);
        assertEquals("Blue", result.getName());
        assertEquals(new Character('B'), result.getLetter());
        assertEquals("BLUE", result.name());
        assertEquals("BLUE", result.toString());
        assertEquals(0, result.ordinal());
    }

    /**
     * Run the Robot getHeldBy() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 24.05.14 19:21
     */
    @Test
    public void testGetHeldBy_1()
        throws Exception {
        Block fixture = new Block(BlockColor.BLUE, space, context);
        fixture.setHeldBy(robot);

        AbstractRobot result = fixture.getHeldBy();

        // add additional test code here
        assertNotNull(result);
        assertEquals(result, robot);
    }

    /**
     * Run the NdPoint getLocation() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 24.05.14 19:21
     */
    @Test
    public void testGetLocation_1()
        throws Exception {
        Block fixture = new Block(BlockColor.BLUE, space, context);
        fixture.setHeldBy(robot);

        NdPoint result = fixture.getLocation();

        // add additional test code here
        assertEquals(null, result);
        verify(robot).getLocation();
    }

    /**
     * Run the NdPoint getLocation() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 24.05.14 19:21
     */
    @Test
    public void testGetLocation_2()
        throws Exception {
        Block fixture = new Block(BlockColor.BLUE, space, context);
        fixture.setHeldBy((AbstractRobot) null);

        NdPoint result = fixture.getLocation();

        // add additional test code here
        assertEquals(new NdPoint(0, 0), result);
        verify(space).getLocation(fixture);
        verifyNoMoreInteractions(robot);
    }

    /**
     * Run the boolean isFree() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 24.05.14 19:21
     */
    @Test
    public void testIsFree_1()
        throws Exception {
        Block fixture = new Block(BlockColor.BLUE, space, context);
        fixture.setHeldBy((AbstractRobot) null);

        boolean result = fixture.isFree();

        // add additional test code here
        assertTrue( result);
    }

    /**
     * Run the boolean isFree() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 24.05.14 19:21
     */
    @Test
    public void testIsFree_2()
        throws Exception {
        Block fixture = new Block(BlockColor.BLUE, space, context);
        fixture.setHeldBy(robot);

        boolean result = fixture.isFree();

        // add additional test code here
        assertFalse(result);
    }

    /**
     * Run the void setColorGrey() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 24.05.14 19:21
     */
    @Test
    public void testSetColorGrey_1()
        throws Exception {
        Block fixture = new Block(BlockColor.BLUE, space, context);

        fixture.setColorGrey();
        
        Color result = fixture.getColor();

        // add additional test code here
        assertEquals(result.getBlue(), 182);
        assertEquals(result.getRed(), 182);
        assertEquals(result.getGreen(), 182);
    }

    /**
     * Run the void setColorGrey() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 24.05.14 19:21
     */
    @Test
    public void testSetColorGrey_2()
        throws Exception {
        Block fixture = new Block(BlockColor.GREEN, space, context);

        fixture.setColorGrey();
        
        Color result = fixture.getColor();

        // add additional test code here
        assertEquals(result.getBlue(), 18);
        assertEquals(result.getRed(), 18);
        assertEquals(result.getGreen(), 18);
    }

    /**
     * Run the void setHeldBy(Robot) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 24.05.14 19:21
     */
    @Test
    public void testSetHeldBy_1()
        throws Exception {
        Block fixture = new Block(BlockColor.BLUE, space, context);
        
        assertNull(fixture.getHeldBy());
        fixture.setHeldBy(robot);

        assertEquals(robot, fixture.getHeldBy());
        
        fixture.setHeldBy(null);
        assertNull(fixture.getHeldBy());
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 24.05.14 19:21
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
     * @generatedBy CodePro at 24.05.14 19:21
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
     * @generatedBy CodePro at 24.05.14 19:21
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(BlockTest.class);
    }
}