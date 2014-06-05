package nl.tudelft.bw4t.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>BlockColorTest</code> contains tests for the class <code>{@link BlockColor}</code>.
 */
public class BlockColorTest {
    /**
     * Run the Color getColor() method test.
     */
    @Test
    public void testGetColor_blue()
            throws Exception {
        BlockColor fixture = BlockColor.BLUE;
        Color test = Color.BLUE;

        Color result = fixture.getColor();

        // add additional test code here
        assertNotNull(result);
        assertEquals(test.getGreen(), result.getGreen());
        assertEquals(test.getBlue(), result.getBlue());
        assertEquals(test.getRed(), result.getRed());
        assertEquals(test.getAlpha(), result.getAlpha());
    }

    /**
     * Run the Color getColor() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test
    public void testGetColor_red()
            throws Exception {
        BlockColor fixture = BlockColor.RED;
        Color test = Color.RED;

        Color result = fixture.getColor();

        // add additional test code here
        assertNotNull(result);
        assertEquals(test.getGreen(), result.getGreen());
        assertEquals(test.getBlue(), result.getBlue());
        assertEquals(test.getRed(), result.getRed());
        assertEquals(test.getAlpha(), result.getAlpha());
    }

    /**
     * Run the Color getColor() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test
    public void testGetColor_orange()
            throws Exception {
        BlockColor fixture = BlockColor.ORANGE;
        Color test = Color.ORANGE;

        Color result = fixture.getColor();

        // add additional test code here
        assertNotNull(result);
        assertEquals(test.getGreen(), result.getGreen());
        assertEquals(test.getBlue(), result.getBlue());
        assertEquals(test.getRed(), result.getRed());
        assertEquals(test.getAlpha(), result.getAlpha());
    }

    /**
     * Run the Color getColor() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test
    public void testGetColor_white()
            throws Exception {
        BlockColor fixture = BlockColor.WHITE;
        Color test = Color.WHITE;

        Color result = fixture.getColor();

        // add additional test code here
        assertNotNull(result);
        assertEquals(test.getGreen(), result.getGreen());
        assertEquals(test.getBlue(), result.getBlue());
        assertEquals(test.getRed(), result.getRed());
        assertEquals(test.getAlpha(), result.getAlpha());
    }

    /**
     * Run the Color getColor() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test
    public void testGetColor_pink()
            throws Exception {
        BlockColor fixture = BlockColor.PINK;
        Color test = Color.PINK;

        Color result = fixture.getColor();

        // add additional test code here
        assertNotNull(result);
        assertEquals(test.getGreen(), result.getGreen());
        assertEquals(test.getBlue(), result.getBlue());
        assertEquals(test.getRed(), result.getRed());
        assertEquals(test.getAlpha(), result.getAlpha());
    }

    /**
     * Run the Color getColor() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test
    public void testGetColor_yellow()
            throws Exception {
        BlockColor fixture = BlockColor.YELLOW;
        Color test = Color.YELLOW;

        Color result = fixture.getColor();

        // add additional test code here
        assertNotNull(result);
        assertEquals(test.getGreen(), result.getGreen());
        assertEquals(test.getBlue(), result.getBlue());
        assertEquals(test.getRed(), result.getRed());
        assertEquals(test.getAlpha(), result.getAlpha());
    }

    /**
     * Run the Character getLetter() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test
    public void testGetLetter_1()
            throws Exception {
        BlockColor fixture = BlockColor.BLUE;

        Character result = fixture.getLetter();

        // add additional test code here
        assertNotNull(result);
        assertEquals("B", result.toString());
    }

    /**
     * Run the String getName() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test
    public void testGetName_1()
            throws Exception {
        BlockColor fixture = BlockColor.BLUE;

        String result = fixture.getName();

        // add additional test code here
        assertEquals("Blue", result);
    }

    /**
     * Run the BlockColor toAvailableColor(Color) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test
    public void testToAvailableColor_BLUE()
            throws Exception {
        BlockColor result = BlockColor.toAvailableColor(Color.BLUE);

        assertNotNull(result);
        assertEquals(result, BlockColor.BLUE);
    }

    /**
     * Run the BlockColor toAvailableColor(Color) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testToAvailableColor_unavailable()
            throws Exception {
        Color color = new Color(1);

        BlockColor result = BlockColor.toAvailableColor(color);

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the BlockColor toAvailableColor(Color) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test
    public void testToAvailableColor_green()
            throws Exception {
        BlockColor result = BlockColor.toAvailableColor(Color.GREEN);

        // add additional test code here
        assertNotNull(result);
        assertEquals(result, BlockColor.GREEN);
    }

    /**
     * Run the BlockColor toAvailableColor(Color) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test
    public void testToAvailableColor_charBlue()
            throws Exception {
        BlockColor result = BlockColor.toAvailableColor('B');

        assertNotNull(result);
        assertEquals(result, BlockColor.BLUE);
    }

    /**
     * Run the BlockColor toAvailableColor(Color) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testToAvailableColor_charUnavailable()
            throws Exception {

        BlockColor result = BlockColor.toAvailableColor('X');

        // add additional test code here
        assertNotNull(result);
    }

    /**
     * Run the BlockColor toAvailableColor(Color) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:11
     */
    @Test
    public void testToAvailableColor_charGreen()
            throws Exception {
        BlockColor result = BlockColor.toAvailableColor('G');

        // add additional test code here
        assertNotNull(result);
        assertEquals(result, BlockColor.GREEN);
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 25.05.14 12:11
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
     * @generatedBy CodePro at 25.05.14 12:11
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
     * @generatedBy CodePro at 25.05.14 12:11
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(BlockColorTest.class);
    }
}