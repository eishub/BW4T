package nl.tudelft.bw4t.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The class <code>ColorTranslatorTest</code> contains tests for the class <code>{@link ColorTranslator}</code>.
 */
public class ColorTranslatorTest {
    private static final Logger LOGGER = Logger.getLogger(ColorTranslatorTest.class);
    
    @BeforeClass
    public static void setupLogger() {
        if(!LOGGER.getAllAppenders().hasMoreElements()){
            BasicConfigurator.configure();
        }
    }
    
    /**
     * Run the ArrayList<String> getAllColors() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:54
     */
    @Test
    public void testGetAllColors_1() {
        BlockColor[] cs = BlockColor.values();

        List<String> result = ColorTranslator.getAllColors();

        // add additional test code here
        assertNotNull(result);
        assertEquals(cs.length, result.size());
        for(BlockColor c : cs){
            assertTrue(result.contains(c.getName()));
        }
    }

    /**
     * Run the Color translate2Color(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:54
     */
    @Test
    public void testTranslate2Color_blue()
            throws Exception {
        String color = "Blue";

        Color result = ColorTranslator.translate2Color(color);

        assertEquals(result, Color.BLUE);
    }

    /**
     * Run the Color translate2Color(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:54
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTranslate2Color_unkown()
            throws Exception {
        String color = "x";

        Color result = ColorTranslator.translate2Color(color);
    }

    /**
     * Run the String translate2ColorString(Color) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:54
     */
    @Test
    public void testTranslate2ColorString_unkown()
            throws Exception {
        Color color = new Color(1);

        LOGGER.info("The next error message can be ignored:");
        String result = ColorTranslator.translate2ColorString(color);

        // add additional test code here
        assertEquals("Unknown", result);
    }

    /**
     * Run the String translate2ColorString(Color) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 25.05.14 12:54
     */
    @Test
    public void testTranslate2ColorString_red()
            throws Exception {
        Color color = Color.RED;

        String result = ColorTranslator.translate2ColorString(color);

        // add additional test code here
        assertEquals("Red", result);
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 25.05.14 12:54
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
     * @generatedBy CodePro at 25.05.14 12:54
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
     * @generatedBy CodePro at 25.05.14 12:54
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(ColorTranslatorTest.class);
    }
}