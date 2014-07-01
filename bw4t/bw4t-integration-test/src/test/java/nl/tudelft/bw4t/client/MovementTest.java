package nl.tudelft.bw4t.client;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.ManagementException;
import eis.iilang.Action;
import eis.iilang.Parameter;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import nl.tudelft.bw4t.client.environment.Launcher;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repast.simphony.scenario.ScenarioLoadException;

import static org.junit.Assert.assertTrue;

/**
 * We test if we can properly move around the environment and receive percepts. We also test if collisions occur as
 * intended.
 */
public class MovementTest {

    /**
     * Client to be used for testing.
     */
    private RemoteEnvironment client;
    private String bot1, bot2;

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
        bot1 = client.getAgents().get(0);
        bot2 = client.getAgents().get(1);
    }

    /** 
     * Disconnects the bots from the server.
     * @throws ManagementException Thrown if the client cannot disconnect a bot.
     */
    @After
    public void tearDown() throws ManagementException {
        client.killHumanEntity(bot1);
        client.killHumanEntity(bot2);
    }

    /**
     * Here we test if movement and collision is working properly.
     * 
     * @throws TranslationException If the test fails.
     * @throws ActException If the test fails.
     * @throws InterruptedException May be thrown while sleeping.
     */
    @Test
    public void movementTest() throws TranslationException, ActException, InterruptedException {
        // We verify that we are indeed at the starting area, then move to RoomC1
        TestFunctions.retrievePercepts(bot1);
        assertTrue(TestFunctions.hasPercept("at(FrontDropZone)"));
        Parameter[] param = Translator.getInstance().translate2Parameter("RoomC1");
        client.performAction(bot1, new Action("goTo", param));
        Thread.sleep(4000L);

        // We verify if we have arrived correctly
        TestFunctions.retrievePercepts(bot1);
        assertTrue(TestFunctions.hasPercept("in(RoomC1)"));

        // Next we test collision by having a second bot attempt to enter the same room
        TestFunctions.retrievePercepts(bot2);
        assertTrue(TestFunctions.hasPercept("at(FrontDropZone)"));
        param = Translator.getInstance().translate2Parameter("RoomC1");
        client.performAction(bot2, new Action("goTo", param));
        Thread.sleep(4000L);

        // We verify if we've collided with the door as intended
        TestFunctions.retrievePercepts(bot2);
        assertTrue(TestFunctions.hasPercept("state(collided)"));
        assertTrue(TestFunctions.hasPercept("at(FrontRoomC1)"));
    }
}
