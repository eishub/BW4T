package nl.tudelft.bw4t.client.environment;

import java.util.Collection;
import java.util.List;
import eis.exceptions.NoEnvironmentException;
import eis.EnvironmentListener;
import eis.iilang.Percept;
import java.util.Map;
import java.util.Set;
import nl.tudelft.bw4t.client.gui.BW4TClientGUI;
import eis.AgentListener;
import org.junit.*;
import eis.iilang.Action;
import static org.junit.Assert.*;
import org.mockito.internal.util.collections.HashCodeAndEqualsSafeSet;
import eis.iilang.Parameter;
import eis.iilang.EnvironmentState;
import nl.tudelft.bw4t.client.BW4TClient;
import org.powermock.core.ListMap;

/**
 * The class <code>RemoteEnvironmentTest</code> contains tests for the class <code>{@link RemoteEnvironment}</code>.
 */
public class RemoteEnvironmentTest {
	/**
	 * Run the RemoteEnvironment() constructor test.
	 *
	 * @generatedBy CodePro at 5/27/14 3:25 PM
	 */
	@Test
	public void testRemoteEnvironment_1()
		throws Exception {
		RemoteEnvironment result = new RemoteEnvironment();
		assertNotNull(result);
		// add additional test code here
		assertFalse(result.isConnectedToGoal());
	}

}