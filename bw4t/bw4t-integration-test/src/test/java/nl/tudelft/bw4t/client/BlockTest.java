package nl.tudelft.bw4t.client;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import repast.simphony.scenario.ScenarioLoadException;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.ManagementException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.Parameter;

public class BlockTest {
	
	private RemoteEnvironment client;

	@Before
	public void setup() throws ManagementException, IOException,
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
		assertTrue(TestFunctions.hasPercept("color(46,YELLOW)"));
		param = Translator.getInstance().translate2Parameter(46);
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
