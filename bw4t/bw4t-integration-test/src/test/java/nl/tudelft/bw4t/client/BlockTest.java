package nl.tudelft.bw4t.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.client.environment.RemoteEnvironment;

import org.junit.Before;
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

	@Test
	public void movementTest() throws TranslationException, ActException,
			InterruptedException, PerceiveException {
		String bot = client.getAgents().get(0);
		
		Parameter[] param = Translator.getInstance().translate2Parameter("RoomC1");
		client.performAction(bot, new Action("goTo", param));
		Thread.sleep(2000L);
		
		TestFunctions.retrievePercepts(bot);
		assertTrue(TestFunctions.hasPercept("color(46,YELLOW)"));
		param = Translator.getInstance().translate2Parameter(46);
		client.performAction(bot, new Action("goToBlock", param));
		Thread.sleep(500L);
		
		client.performAction(bot, new Action("pickUp"));
		Thread.sleep(200L);
		
		TestFunctions.retrievePercepts(bot);
		assertFalse(TestFunctions.hasPercept("color(46,YELLOW)"));
		
		param = Translator.getInstance().translate2Parameter("DropZone");
		client.performAction(bot, new Action("goTo", param));
		Thread.sleep(3000L);
		
		client.performAction(bot, new Action("putDown"));
		Thread.sleep(200L);
		TestFunctions.retrievePercepts(bot);
		assertTrue(TestFunctions.hasPercept("sequenceIndex(1)"));
	}
}
