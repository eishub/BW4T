package nl.tudelft.bw4t.startup;

import nl.tudelft.bw4t.client.startup.InitParam;
import nl.tudelft.bw4t.client.startup.Launcher;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Here we test if the Launcher correctly finds arguments.
 * The remainder of the Launcher functionality (i.e. starting the client) isn't tested here,
 * This is done in the integration tests instead.
 */
public class LauncherTest {
    
    /** We test if it correctly identifies the intended value amongst several values */
    @Test
    public void findArgumentTest() {
        String str = Launcher.findArgument(new String[]{
                "-clientport", "101",
                "-agentcount", "2",
                "-launchgui", "false",
                "-serverport", "5000"},
                InitParam.CLIENTPORT);
        assertTrue(str.equals("101"));
    }
    
    /** We test if it correctly returns the default value when the desired argument is not included. */
    @Test
    public void findArgumentDefaultTest() {
        String str = Launcher.findArgument(new String[]{
                "-agentcount", "2",
                "-launchgui", "false",
                "-serverport", "5000"},
                InitParam.CLIENTPORT);
        assertTrue(str.equals("2000"));
    }
}
