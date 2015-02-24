package nl.tudelft.bw4t.map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The class <code>RenderOptionsTest</code> contains tests for the class
 * <code>{@link RenderOptions}</code>.
 */
public class RenderOptionsTest {
    /**
     * Run the RenderOptions() constructor test.
     * 
     * @generatedBy CodePro at 25.05.14 16:20
     */
    @Test
    public void testRenderOptions() throws Exception {
        RenderOptions result = new RenderOptions();
        assertNotNull(result);
    }

    /**
     * Run the Boolean isLabelVisible() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 25.05.14 16:20
     */
    @Test
    public void testIsLabelVisible() throws Exception {
        RenderOptions fixture = new RenderOptions();

        assertTrue(fixture.isLabelVisible());

        fixture.setLabelVisible(false);

        assertFalse(fixture.isLabelVisible());
    }

    /**
     * Launch the test.
     * 
     * @param args
     *            the command line arguments
     * 
     * @generatedBy CodePro at 25.05.14 16:20
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(RenderOptionsTest.class);
    }
}