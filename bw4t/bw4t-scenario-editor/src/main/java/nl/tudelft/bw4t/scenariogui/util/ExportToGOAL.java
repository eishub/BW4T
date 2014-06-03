package nl.tudelft.bw4t.scenariogui.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;
import nl.tudelft.bw4t.util.FileUtils;

/**
 * Loads the BW4TClientConfiguration and exports it as a mas2g file, creating all the
 * necessary files.
 *
 * @author Calvin Wong Loi Sing
 *         Created by on 2-6-2014.
 */
public final class ExportToGOAL {

    /**
     * The environment used.
     */
    private static final String ENVIRONMENT = "env = \"BW4T3/BW4TClient.jar\" .\n";

    /**
     * The initialization string.
     * Has to be formatted in the order in which the variables appear.
     */
    private static final String INIT = "init = [ "
            + "clientip =\"%s\", "
            + "clientport=\"%d\", "
            + "serverip = \"%s\", "
            + "serverport = \"%d\", "
            + "agentcount = \"%d\", "
            + "humancount = \"%d\", "
            + "launchgui = \"%s\", "
            + "goal = \"true\"] "
            + ".\n";

    /**
     * The configuration file to be converted.
     */
    private static BW4TClientConfig configuration;

    /**
     * The target folder where the configuration will be saved.
     */
    private static String directory;

    /**
     * The mas2g file used in this export.
     */
    private static File mas2gFile;

    /**
     * The number of agent controlled bots.
     */
    private static int agentCount;

    /**
     * The number of human controlled bots.
     */
    private static int humanCount;

    /**
     * The StringBuilder used to create the launch policy.
     */
    private static StringBuilder launchPolicyBuilder;

    /**
     * A Map with the goal files, with the file as key.
     * Used to prevent duplicate addition of files to the file list.
     */
    private static Map<String, String> goalFiles;


    /**
     * Hide the constructor
     */
    private ExportToGOAL() { }


    /**
     * Generates a GOAL configuration in the specified directory from the
     * BW4TClientConfig given.
     *
     * @param directory     The directory where the configuration has to be stored.
     * @param configuration The BW4TClientConfig to be generated.
     */
    public static void export(String directory, BW4TClientConfig configuration) {
        ExportToGOAL.directory = directory;
        ExportToGOAL.configuration = configuration;
        goalFiles = new HashMap<String, String>();

        try {
            generateHierarchy();
            buildLaunchPolicy();
            generateEnvironmentBlock();
            generateAgentBlock();
            generateLaunchPolicy();
        } catch (SecurityException ex) {
            ScenarioEditor.handleException(ex, "File or Directory could not be created. Access Denied.");
        } catch (IOException ex) {
            ScenarioEditor.handleException(ex, "An IO Exception has occurred. Please try again.");
        }
    }

    /**
     * Generate the project hierarchy.
     *
     * @throws SecurityException Exception raised if there's no access to the file/folder
     * @throws IOException Exception raised if there are problems reading/writing to files
     */
    private static void generateHierarchy() throws SecurityException, IOException {
        File directory = new File(ExportToGOAL.directory);
        directory.mkdir();

        /* Create the agents directory. */
        File agentFolder = new File(directory.getAbsolutePath() + "/agents/");
        agentFolder.mkdir();

        /* And the mas2g file, since it will be overwritten in every case. */
        ExportToGOAL.mas2gFile = new File(directory.getAbsolutePath() + "/bw4t.mas2g");
        mas2gFile.delete();
        mas2gFile.createNewFile();

        generateAgentHierarchy(directory);
    }

    /**
     * Generate the agent file hierarchy.
     *
     * @param directory The directory in which the project sits.
     * @throws IOException Exception raised if there are problems reading/writing to files
     */
    private static void generateAgentHierarchy(File directory) throws IOException {
         /* Finally loop through the bots, and create the files. File.createNewFile only creates
               a file if it does not yet exist, so no checks are needed.
            */
        for (BotConfig bot : ExportToGOAL.configuration.getBots()) {
            //TODO: Change this to goal file implementation and existing files.
            String botName = bot.getBotName();
            String botGoalFilename = bot.getFileName();

            File goalFile = new File(botGoalFilename);
            if (goalFile.exists()) {
                    /* Case 1: Existing GOAL file
                       Check if the filename doesn't already exist, if so do nothing. */
                String goalFilename = goalFile.getName();
                File goalFileInDirectory = new File(directory.getAbsolutePath() + "/agents/" + goalFilename);
                if (!goalFileInDirectory.exists()) {
                    FileUtils.copyFile(goalFile, goalFileInDirectory);
                }
                // Set the filename to the copied one.
                bot.setFileName(goalFilename);
            }
            else {
                /* Case 2: New GOAL file */
                new File(directory.getAbsolutePath() + "/agents/" + botGoalFilename).createNewFile();
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
            if (bot.getBotController() == BotConfig.Controller.AGENT) {
                type = "bot";
                agentCount += bot.getBotAmount();
            } else if (bot.getBotController() == BotConfig.Controller.HUMAN) {
                type = "human";
                humanCount += bot.getBotAmount();
            }
            String goalFileSanitized = bot.getFileName().toLowerCase().replace(" ", "_");
            String goalFileNoExt = goalFileSanitized.substring(0, goalFileSanitized.length() - 5);
            launchPolicyBuilder.append("\t");
            launchPolicyBuilder.append(String.format("when [type=%s,max=%d]@env do launch %s: %s .",
                            type,
                            new Integer(bot.getBotAmount()),
                            bot.getBotName().toLowerCase().replace(" ", "_"),
                            goalFileNoExt)
            );
            launchPolicyBuilder.append("\n");
            /* Remove the last 5 characters since that is the extension. */
            goalFiles.put(bot.getFileName(), goalFileNoExt);
        }
    }

    /**
     * Write the environment block to the mas2g file.
     * @throws IOException Exception raised if there are problems reading/writing to files
     */
    private static void generateEnvironmentBlock() throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ExportToGOAL.mas2gFile, true)));

        out.println("environment{");

        out.print("\t");
        out.print(ENVIRONMENT);

        String init = String.format(
                INIT,
                configuration.getClientIp(),
                configuration.getClientPort(),
                configuration.getServerIp(),
                configuration.getServerPort(),
                agentCount,
                humanCount,
                configuration.isLaunchGui() ? "true" : "false"
        );

        out.print("\t");
        out.print(init);

        out.println("}");

        out.close();
    }

    /**
     * Write the agent files block to the mas2g.
     * @throws IOException Exception raised if there are problems reading/writing to files
     */
    private static void generateAgentBlock() throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ExportToGOAL.mas2gFile, true)));

        out.println();

        out.println("agentfiles{");
        for (Map.Entry<String, String> item : goalFiles.entrySet()) {
            String entry = "\t\"%s\" [name = %s] .\n";
            out.append(String.format(entry, item.getKey(), item.getValue()));
        }
        out.println("}");

        out.close();
    }

    /**
     * Write the launch policy to the mas2g.
     * @throws IOException Exception raised if there are problems reading/writing to files
     */
    private static void generateLaunchPolicy() throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ExportToGOAL.mas2gFile, true)));

        out.println();

        out.println("launchpolicy{");
        out.print(launchPolicyBuilder);
        out.println("}");

        out.close();
    }
}
