package nl.tudelft.bw4t.client;

import static org.junit.Assert.assertTrue;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.ManagementException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Parameter;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.client.environment.Launcher;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import repast.simphony.scenario.ScenarioLoadException;

/**
 * We test if we can properly move around the environment and receive percepts. We also test if collisions occur as
 * intended.
 */
@Ignore
public class MovementTest {

    /**
     * Client to be used for testing.
     */
    private RemoteEnvironment client;

    /**
     * Launch the client and set it for use by the TestFunctions class.
     * 
     * @throws ManagementException
     * @throws IOException
     * @throws ScenarioLoadException
     * @throws JAXBException
     * @throws InterruptedException
     */
    @Before
    public void setUp() throws ManagementException, IOException, ScenarioLoadException, JAXBException,
            InterruptedException {
        String[] clientArgs = new String[] { "-map", "Banana", "-agentcount", "2", "-humancount", "0" };

        client = Launcher.launch(clientArgs);
        TestFunctions.setClient(client);
    }

    /**
     * Here we test if movement and collision is working properly.
     * 
     * @throws TranslationException
     * @throws ActException
     * @throws InterruptedException
     * @throws PerceiveException
     */
    @Test
    public void movementTest() throws TranslationException, ActException, InterruptedException, PerceiveException {
        String bot1 = client.getAgents().get(0);
        String bot2 = client.getAgents().get(1);

        // We verify that we are indeed at the starting area, then move to RoomC1
        TestFunctions.retrievePercepts(bot1);
        assertTrue(TestFunctions.hasPercept("at(FrontDropZone)"));
        Parameter[] param = Translator.getInstance().translate2Parameter("RoomC1");
        client.performAction(bot1, new Action("goTo", param));
        Thread.sleep(2000L);

        // We verify if we have arrived correctly
        TestFunctions.retrievePercepts(bot1);
        assertTrue(TestFunctions.hasPercept("in(RoomC1)"));

        // Next we test collision by having a second bot attempt to enter the same room
        TestFunctions.retrievePercepts(bot2);
        assertTrue(TestFunctions.hasPercept("at(FrontDropZone)"));
        param = Translator.getInstance().translate2Parameter("RoomC1");
        client.performAction(bot2, new Action("goTo", param));
        Thread.sleep(2000L);

        // We verify if we've collided with the door as intended
        TestFunctions.retrievePercepts(bot2);
        assertTrue(TestFunctions.hasPercept("state(collided)"));
        assertTrue(TestFunctions.hasPercept("at(FrontRoomC1)"));
    }
}
