package nl.tudelft.bw4t.client.environment;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import nl.tudelft.bw4t.client.startup.InitParam;

import org.junit.Test;

import eis.iilang.Identifier;
import eis.iilang.Parameter;

/**
 * Here we test if the Launcher correctly finds arguments.
 * The remainder of the Launcher functionality (i.e. starting the client) isn't tested here,
 * This is done in the integration tests instead.
 */
public class LauncherTest {
    
    /** We test if it correctly identifies the intended value amongst several values */
    @Test
    public void findArgumentTest() {
        Identifier str = Launcher.findParameter(new String[]{
                "-clientport", "101",
                "-agentcount", "2",
                "-launchgui", "false",
                "-serverport", "5000"},
                InitParam.CLIENTPORT);
        assertTrue("101".equals(str.getValue()));
    }
    
    /** We test if it correctly returns the default value when the desired argument is not included. */
    @Test
    public void findArgumentDefaultTest() {
        Parameter str = Launcher.findParameter(new String[]{
                "-agentcount", "2",
                "-launchgui", "false",
                "-serverport", "5000"},
                InitParam.CLIENTPORT);
        assertNull(str);
    }
}
