package nl.tudelft.bw4t.scenariogui.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.ScenarioEditor;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the ExportToGoal class.
 */
public class ExportToMASTest {
    public static final String CONFIG_NAME = "testname";
    private static final String MAS2G = CONFIG_NAME + ".mas2g";
    private static final String XML_SRC = CONFIG_NAME + ".xml";
    private static final String FILE_ROOT = System.getProperty("user.dir");
    private static final String CONFIG_PATH = FILE_ROOT + "/src/test/resources/export.xml";
    private static final String EXPORT_DIR = FILE_ROOT + "/src/test/resources/export/";
    private static final String AGENT_DIR = EXPORT_DIR + "agents/";
    private static final String AGENT_GOAL_FILE = FILE_ROOT + "/src/test/resources/robot.goal";
    private static final String AGENT_GOAL_FILE_WORKING = FILE_ROOT + "/robot.goal";
    private static final String[] GOAL_FILES = {"robot.goal"};

    private BW4TClientConfig configuration;

    @Before
    public void setUp() throws IOException, JAXBException {
        /* Delete the export directory and everything in it */
        FileUtils.deleteDirectory(new File(EXPORT_DIR));

        /* Mock the alert boxes in ScenarioEditor in the case of an error (unexpected) so Jenkins doesn't freeze */
        ScenarioEditor.setOptionPrompt(OptionPromptHelper.getYesOptionPrompt());

        configuration = BW4TClientConfig.fromXML(CONFIG_PATH);

        /*
         * Nasty hack time:
         * The exporter supports loading an existing GOAL file and copying it to the directory.
         * To test this is a challenge since paths aren't equal across computers.
         *
         * The solution is to copy the robot.goal file to the working directory, thus making it available for loading
         * during the test, and in the test breakdown to delete it.
         */
        FileUtils.copyFile(new File(AGENT_GOAL_FILE), new File(AGENT_GOAL_FILE_WORKING));

        // The actual export.
        ExportToMAS.export(EXPORT_DIR, configuration, CONFIG_NAME);
    }

    @After
    public void takeDownShop() throws IOException {
        /* Delete the export directory and everything in it */
        FileUtils.deleteDirectory(new File(EXPORT_DIR));
        FileUtils.forceDelete(new File(AGENT_GOAL_FILE_WORKING));
    }

    @Test
    public void testRootFolderExists() {
        assertTrue(EXPORT_DIR, new File(EXPORT_DIR).exists());
    }

    @Test
    public void testMAS2GExists() {
        String mas2gfile = EXPORT_DIR + MAS2G;
        assertTrue(mas2gfile, new File(mas2gfile).exists());

    }

    @Test
    public void testXMLExists() {
        String xml = EXPORT_DIR + XML_SRC;
        assertTrue(xml, new File(xml).exists());
    }

    @Test
    public void testAgentFolderExists() {
        assertTrue(AGENT_DIR, new File(AGENT_DIR).exists());
    }

    @Test
    public void testGoalFilesExists() {
        for (String file : GOAL_FILES) {
            assertTrue(AGENT_DIR + file, new File(AGENT_DIR + file).exists());
        }
    }

    @Test
    public void compareRobotGoalFiles() throws IOException {
        boolean result = FileUtils.contentEquals(new File(AGENT_GOAL_FILE), new File(AGENT_GOAL_FILE_WORKING));
        assertTrue("AGENT_GOAL_FILE <==> AGENT_GOAL_FILE_WORKING", result);
    }

}
