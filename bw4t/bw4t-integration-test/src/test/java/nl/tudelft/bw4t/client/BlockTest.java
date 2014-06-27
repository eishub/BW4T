package nl.tudelft.bw4t.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.client.environment.Launcher;
import nl.tudelft.bw4t.client.environment.RemoteEnvironment;
import nl.tudelft.bw4t.map.BlockColor;
import nl.tudelft.bw4t.map.Zone;

import org.junit.Before;
import org.junit.Test;

import repast.simphony.scenario.ScenarioLoadException;
import eis.eis2java.translation.Translator;
import eis.exceptions.ManagementException;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.Percept;

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
                "-agentcount", "1",
                "-humancount", "0"};

        client = Launcher.launch(clientArgs);
        TestFunctions.setClient(client);
    }

    /**
     * Here we test the picking up and delivering of a block.
     * @throws Exception if the test fails
     */
    @Test
    public void blockTest() throws Exception {
        String bot = client.getAssociatedEntities(client.getAgents().get(0)).iterator().next();
        
        // Move to RoomC1
        Parameter[] param = Translator.getInstance().translate2Parameter("RoomC1");
        client.performAction(bot, new Action("goTo", param));
        Thread.sleep(2000L);
        
        // Check if the yellow block is present and move to it
        // It should always be present on the Banana map
        TestFunctions.retrievePercepts(bot);
        Iterator<Percept> percepts = TestFunctions.getPercepts().iterator();
        long blockNumber = -1;
        while (percepts.hasNext()) {
            Percept percept = percepts.next();
            String name = ((Identifier) percept.getParameters().get(0)).getValue();
            long blockId = ((Numeral) percept.getParameters().get(1)).getValue().longValue();
            BlockColor color = Enum.valueOf(BlockColor.class, ((Identifier) percept.getParameters().get(2)).getValue());
            if ("color".equals(name) && color == BlockColor.YELLOW) {
                blockNumber = blockId;
                break;
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
        param = Translator.getInstance().translate2Parameter(Zone.DROP_ZONE_NAME);
        client.performAction(bot, new Action("goTo", param));
        Thread.sleep(3000L);
        
        // Put the block down and verify the sequence index increased
        client.performAction(bot, new Action("putDown"));
        Thread.sleep(200L);
        TestFunctions.retrievePercepts(bot);
        assertEquals(1, client.getEntityController(bot).getMapController().getSequenceIndex());
    }
}
