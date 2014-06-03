package nl.tudelft.bw4t.client;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.ManagementException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import java.io.IOException;
import java.util.Iterator;
import javax.xml.bind.JAXBException;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import org.junit.Before;
import org.junit.Test;
import repast.simphony.scenario.ScenarioLoadException;
import static org.junit.Assert.assertTrue;

/**
 * We test if blocks are properly perceived, picked up and delivered.
 */
public class BlockTest {
    
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
    @Before
    public void setUp() throws ManagementException, IOException,
            ScenarioLoadException, JAXBException, InterruptedException {        
        String[] clientArgs = new String[] {
                "-map", "Banana",
                "-agentclass", "nl.tudelft.bw4t.agent.BW4TAgent",
                "-agentcount", "1",
                "-humancount", "0"};
        nl.tudelft.bw4t.client.startup.Launcher.launch(clientArgs);
        client = nl.tudelft.bw4t.client.startup.Launcher.getEnvironment();
        TestFunctions.setClient(client);
    }

    /**
     * Here we test the picking up and delivering of a block.
     * @throws TranslationException
     * @throws ActException
     * @throws InterruptedException
     * @throws PerceiveException
     */
    @Test
    public void blockTest() throws TranslationException, ActException,
            InterruptedException, PerceiveException {
        String bot = client.getAgents().get(0);
        
        // Move to RoomC1
        Parameter[] param = Translator.getInstance().translate2Parameter("RoomC1");
        client.performAction(bot, new Action("goTo", param));
        Thread.sleep(2000L);
        
        // Check if the yellow block is present and move to it
        // It should always be present on the Banana map
        TestFunctions.retrievePercepts(bot);
        Iterator<Percept> percepts = TestFunctions.getPercepts().iterator();
        int blockNumber = -1;
        while (percepts.hasNext()) {
            Percept percept = percepts.next();
            if (percept.toProlog().startsWith("color(") && percept.toProlog().endsWith(",YELLOW)")) {
                blockNumber = Integer.parseInt(percept.toProlog().replace("color(","").replace(",YELLOW)",""));
            }
        }
        assertTrue(blockNumber != -1);
        param = Translator.getInstance().translate2Parameter(blockNumber);
        client.performAction(bot, new Action("goToBlock", param));
        Thread.sleep(500L);
        
        // Pick the block up
        client.performAction(bot, new Action("pickUp"));
        Thread.sleep(200L);
        
        // Move to the DropZone
        param = Translator.getInstance().translate2Parameter("DropZone");
        client.performAction(bot, new Action("goTo", param));
        Thread.sleep(3000L);
        
        // Put the block down and verify the sequence index increased
        client.performAction(bot, new Action("putDown"));
        Thread.sleep(200L);
        TestFunctions.retrievePercepts(bot);
        assertTrue(TestFunctions.hasPercept("sequenceIndex(1)"));
    }
}
