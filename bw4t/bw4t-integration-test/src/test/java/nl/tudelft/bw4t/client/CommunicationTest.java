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

public class CommunicationTest {
	
	private RemoteEnvironment client;

//	@Before
	public void setup() throws ManagementException, IOException,
			ScenarioLoadException, JAXBException, InterruptedException {		
		String[] clientArgs = new String[] {
				"-map", "Banana",
				"-agentclass", "nl.tudelft.bw4t.agent.BW4TAgent",
				"-agentcount", "3",
				"-humancount", "0"};
		nl.tudelft.bw4t.client.startup.Launcher.launch(clientArgs);
		client = nl.tudelft.bw4t.client.startup.Launcher.getEnvironment();
		TestFunctions.setClient(client);
	}

//	@Test
	public void movementTest() throws TranslationException, ActException,
			InterruptedException, PerceiveException {
		String bot1 = client.getAgents().get(0);
		String bot2 = client.getAgents().get(1);
		String bot3 = client.getAgents().get(2);

		sendMessage(bot1, "all", "Test");
		sendMessage(bot1, bot2, "Test2");
		Thread.sleep(500L);

		TestFunctions.retrievePercepts(bot1);
		assertFalse(TestFunctions.hasPercept("message([Bot1,Test])"));
		assertFalse(TestFunctions.hasPercept("message([Bot1,Test2])"));
		TestFunctions.retrievePercepts(bot2);
		assertTrue(TestFunctions.hasPercept("message([Bot1,Test])"));
		assertTrue(TestFunctions.hasPercept("message([Bot1,Test2])"));
		TestFunctions.retrievePercepts(bot3);
		assertTrue(TestFunctions.hasPercept("message([Bot1,Test])"));
		assertFalse(TestFunctions.hasPercept("message([Bot1,Test2])"));
	}
	
	private void sendMessage(String entityId, String receiver, String message){
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
