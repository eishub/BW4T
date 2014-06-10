package nl.tudelft.bw4t.client;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.ManagementException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Parameter;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import repast.simphony.scenario.ScenarioLoadException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * We test if messages are send and received and to the intended recipients.
 */
public class CommunicationTest {
    
    /**
     * Client to be used for testing.
     */
    private RemoteEnvironment client;

    /**
     * Launch the client and set it for use by the TestFunctions class.
     * @throws ManagementException
     * @throws IOException
     * @throws ScenarioLoadException
     * @throws JAXBException
     * @throws InterruptedException
     */
//  @Before
    public void setUp() throws ManagementException, IOException,
            ScenarioLoadException, JAXBException, InterruptedException {        
        String[] clientArgs = new String[] {
                "-map", "Banana",
                "-agentclass", "nl.tudelft.bw4t.agent.BW4TAgent",
                "-agentcount", "3",
                "-humancount", "0"};
        nl.tudelft.bw4t.client.environment.Launcher.launch(clientArgs);
        client = nl.tudelft.bw4t.client.environment.Launcher.getEnvironment();
        TestFunctions.setClient(client);
    }

    /**
     * Here we verify if messages are being sent and received correctly.
     * @throws TranslationException
     * @throws ActException
     * @throws InterruptedException
     * @throws PerceiveException
     */
//  @Test
    public void communicationTest() throws TranslationException, ActException,
            InterruptedException, PerceiveException {
        String bot1 = client.getAgents().get(0);
        String bot2 = client.getAgents().get(1);
        String bot3 = client.getAgents().get(2);

        // We send the messages and wait a short while for them to arrive
        sendMessage(bot1, "all", "Test");
        sendMessage(bot1, bot2, "Test2");
        Thread.sleep(500L);

        // We verify if the messages arrived at the correct bots
        TestFunctions.retrievePercepts(bot1);
        assertFalse(TestFunctions.hasPercept("message([" + bot1 + ",Test])"));
        assertFalse(TestFunctions.hasPercept("message([" + bot1 + ",Test2])"));
        TestFunctions.retrievePercepts(bot2);
        assertTrue(TestFunctions.hasPercept("message([" + bot1 + ",Test])"));
        assertTrue(TestFunctions.hasPercept("message([" + bot1 + ",Test2])"));
        TestFunctions.retrievePercepts(bot3);
        assertTrue(TestFunctions.hasPercept("message([" + bot1 + ",Test])"));
        assertFalse(TestFunctions.hasPercept("message([" + bot1 + ",Test2])"));
    }
    
    /**
     * @param entityId Which bot should send the message
     * @param receiver To who the message is directed
     * @param message The contents of the message
     */
    private void sendMessage(String entityId, String receiver, String message) {
        try {
            Parameter[] receiverParam = Translator.getInstance()
                    .translate2Parameter(receiver);
            Parameter[] messageParam = Translator.getInstance()
                    .translate2Parameter(message);
            client.performAction(entityId, new Action("sendMessage", 
                    new Parameter[]{receiverParam[0], messageParam[0]}));
        } catch (TranslationException e) {
            e.printStackTrace();
        } catch (ActException e) {
            e.printStackTrace();
        }
    }
}
