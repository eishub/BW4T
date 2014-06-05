package nl.tudelft.bw4t.scenariogui.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.agent.EntityType;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.util.FileUtils;

/**
 * Loads the BW4TClientConfiguration and exports it as a mas2g file, creating
 * all the necessary files.
 * 
 * @author Calvin Wong Loi Sing Created by on 2-6-2014.
 */
public final class ExportToMAS {

	private static final String TAB = "\t";
	private static final String NEWLINE = "\n";
	private static final String ENVIRONMENT = "env = \"BW4T3/BW4TClient.jar\" ."
			+ NEWLINE;

	private static final String INIT = "init = [ "
			+ "configfile = \"%s.xml\" ] ." + NEWLINE;

	private static String init = "";
	private static BW4TClientConfig configuration;
	private static String directory;
	private static File mas2gFile;
	private static int agentCount;
	private static int humanCount;
	private static StringBuilder launchPolicyBuilder;
	private static Map<String, String> goalFiles;

	/**
	 * Hide the constructor
	 */
	private ExportToMAS() {
	}

	/**
	 * Generates a GOAL configuration in the specified directory from the
	 * BW4TClientConfig given.
	 * 
	 * @param directory
	 *            The directory where the configuration has to be stored.
	 * @param configuration
	 *            The BW4TClientConfig to be generated.
	 */
	public static void export(String directory, BW4TClientConfig configuration,
			String configurationName) {
		ExportToMAS.directory = directory;
		ExportToMAS.configuration = configuration;
		goalFiles = new HashMap<String, String>();

		init = String
				.format(INIT, new File(directory + "/" + configurationName).getAbsolutePath());

		agentCount = 0;
		humanCount = 0;

		try {
			generateHierarchy(configurationName);
			buildLaunchPolicy();
			generateEnvironmentBlock();
			generateAgentBlock();
			generateLaunchPolicy();

			// Save the configuration again with the latest changes concerning
			// the goal files.
			configuration.setFileLocation(directory + "/" + configurationName
					+ ".xml");
			configuration.toXML();
		} catch (IOException ex) {
			ScenarioEditor.handleException(ex,
					"An IO Exception has occurred. Please try again.");
		} catch (JAXBException ex) {
			ScenarioEditor.handleException(ex,
					"A problem occured while attempting to export the protect. "
							+ "Please try again.");

		}
	}

	/**
	 * Generate the project hierarchy.
	 * 
	 * @throws IOException
	 *             Exception raised if there are problems reading/writing to
	 *             files
	 */
	private static void generateHierarchy(String configurationName)
			throws IOException {
		File directory = new File(ExportToMAS.directory);
		directory.mkdir();

		/* Create the agents directory. */
		File agentFolder = new File(directory.getAbsolutePath() + "/agents/");
		agentFolder.mkdir();

		/* And the mas2g file, since it will be overwritten in every case. */
		ExportToMAS.mas2gFile = new File(directory.getAbsolutePath() + "/"
				+ configurationName + ".mas2g");
		mas2gFile.delete();
		mas2gFile.createNewFile();

		generateAgentHierarchy(directory);
	}

	/**
	 * Generate the agent file hierarchy.
	 * 
	 * @param directory
	 *            The directory in which the project sits.
	 * @throws IOException
	 *             Exception raised if there are problems reading/writing to
	 *             files
	 */
	private static void generateAgentHierarchy(File directory)
			throws IOException {
		/*
		 * Finally loop through the bots, and create the files.
		 * File.createNewFile only creates a file if it does not yet exist, so
		 * no checks are needed.
		 */
		for (BotConfig bot : ExportToMAS.configuration.getBots()) {
			// TODO: Change this to goal file implementation and existing files.
			String botName = bot.getBotName();
			String botGoalFilename = bot.getFileName();

			File goalFile = new File(botGoalFilename);
			if (goalFile.exists()) {
				/*
				 * Case 1: Existing GOAL file Check if the filename doesn't
				 * already exist, if so do nothing.
				 */
				String goalFilename = goalFile.getName();
				File goalFileInDirectory = new File(directory.getAbsolutePath()
						+ "/agents/" + goalFilename);
				if (!goalFileInDirectory.exists()) {
					FileUtils.copyFile(goalFile, goalFileInDirectory);
				}
				// Set the filename to the copied one.
				bot.setFileName(goalFilename);
			}
		}

		for (EPartnerConfig epartner : ExportToMAS.configuration.getEpartners()) {
			String epartnerFileName = epartner.getFileName();

			File goalFile = new File(epartnerFileName);
			if (goalFile.exists()) {
				String goalFileName = goalFile.getName();
				File goalFileInDirectory = new File(directory.getAbsolutePath()
						+ "/agents/" + goalFileName);
				if (!goalFileInDirectory.exists()) {
					FileUtils.copyFile(goalFile, goalFileInDirectory);
				}

				epartner.setFileName(goalFileName);
			}
		}
	}

	/**
	 * Generate the launch policy.
	 */
	private static void buildLaunchPolicy() {
		launchPolicyBuilder = new StringBuilder();
		for (BotConfig bot : configuration.getBots()) {
			String type = "";
			if (bot.getBotController() == EntityType.AGENT) {
				type = "bot";
				agentCount += bot.getBotAmount();
			} else if (bot.getBotController() == EntityType.HUMAN) {
				type = "human";
				humanCount += bot.getBotAmount();
			}
			String goalFileSanitized = bot.getFileName().toLowerCase()
					.replace(" ", "_");
			launchPolicyBuilder.append(TAB);
			launchPolicyBuilder.append(String.format(
					"when [type=%s,max=%d]@env do launch %s: %s .", type,
					new Integer(bot.getBotAmount()), bot.getBotName()
							.toLowerCase().replace(" ", "_"),
					bot.getReferenceName()));
			launchPolicyBuilder.append(NEWLINE);
			/* Remove the last 5 characters since that is the extension. */
			goalFiles.put(bot.getFileName(), bot.getReferenceName());
		}

		for (EPartnerConfig epartner : configuration.getEpartners()) {
			agentCount += epartner.getEpartnerAmount();

			launchPolicyBuilder.append(TAB);
			launchPolicyBuilder.append(String.format(
					"when [type=%s,max=%d]@env do launch %s: %s .",
					"e-partner", new Integer(epartner.getEpartnerAmount()),
					epartner.getEpartnerName().toLowerCase().replace(" ", "_"),
					epartner.getReferenceName()));
			launchPolicyBuilder.append(NEWLINE);

			goalFiles.put(epartner.getFileName(), epartner.getReferenceName());
		}
	}

	/**
	 * Write the environment block to the mas2g file.
	 * 
	 * @throws IOException
	 *             Exception raised if there are problems reading/writing to
	 *             files
	 */
	private static void generateEnvironmentBlock() throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				ExportToMAS.mas2gFile, true)));

		out.println("environment{");

		out.print(TAB);
		out.print(ENVIRONMENT);

		String initField = String.format(init, configuration.getClientIp(),
				configuration.getClientPort(), configuration.getServerIp(),
				configuration.getServerPort(), agentCount, humanCount,
				configuration.isLaunchGui() ? "true" : "false");

		out.print(TAB);
		out.print(initField);

		out.println("}");

		out.close();
	}

	/**
	 * Write the agent files block to the mas2g.
	 * 
	 * @throws IOException
	 *             Exception raised if there are problems reading/writing to
	 *             files
	 */
	private static void generateAgentBlock() throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				ExportToMAS.mas2gFile, true)));

		out.println();

		out.println("agentfiles{");
		for (Map.Entry<String, String> item : goalFiles.entrySet()) {
			String entry = TAB + "\"agents/%s\" [name = %s] ." + NEWLINE;
			out.append(String.format(entry, item.getKey(), item.getValue()));
		}
		out.println("}");

		out.close();
	}

	/**
	 * Write the launch policy to the mas2g.
	 * 
	 * @throws IOException
	 *             Exception raised if there are problems reading/writing to
	 *             files
	 */
	private static void generateLaunchPolicy() throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				ExportToMAS.mas2gFile, true)));

		out.println();

		out.println("launchpolicy{");
		out.print(launchPolicyBuilder);
		out.println("}");

		out.close();
	}
}
