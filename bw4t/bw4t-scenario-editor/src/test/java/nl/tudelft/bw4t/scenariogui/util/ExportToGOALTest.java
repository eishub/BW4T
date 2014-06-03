package nl.tudelft.bw4t.scenariogui.util;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Calvin Wong Loi Sing
 *         Created on 3-6-2014.
 */
public class ExportToGOALTest {

    /**
     * Root for testing files
     */
    private static final String FILE_ROOT = System.getProperty("user.dir");

    /**
     * The BW4T Configuration used for thisd test.
     */
    private static final String CONFIG_PATH = FILE_ROOT + "/src/test/resources/export.xml";

    /**
     * The export directory
     */
    private static final String EXPORT_DIR = FILE_ROOT + "/src/test/resources/export/";
    /**
     * The directory which holds the agent files
     */
    private static final String AGENT_DIR = EXPORT_DIR + "agents/";
    /**
     * The GOAL file for the agent.
     */
    private static final String AGENT_GOAL_FILE = FILE_ROOT + "/src/test/resources/robot.goal";
    /**
     * The location of the GOAL file in the working directory.
     */
    private static final String AGENT_GOAL_FILE_WORKING = FILE_ROOT + "/robot.goal";
    /**
     * The MAS2G filename
     */
    private static final String MAS2G = "bw4t.mas2g";
    /**
     * The XML source name
     */
    private static final String XML_SRC = "source.xml";
    /**
     * The goal files as defined in the test xml.
     */
    private static final String[] GOAL_FILES = { "robot.goal", "grace.goal" };

    /**
     * The BW4T Configuration File
     */
    private BW4TClientConfig configuration;

    @Before
    public void setUp() throws IOException, JAXBException {
        /* Delete the export directory and everything in it */
        FileUtils.deleteDirectory(new File(EXPORT_DIR));

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
        ExportToGOAL.export(EXPORT_DIR, configuration);
    }

    @After
    public void takeDownShop() throws IOException {
        /* Delete the export directory and everything in it */
        FileUtils.deleteDirectory(new File(EXPORT_DIR));
        FileUtils.forceDelete(new File(AGENT_GOAL_FILE_WORKING));
    }

    /**
     * Verify that the export folder exists
     */
    @Test
    public void testRootFolderExists() {
        assertTrue(EXPORT_DIR, new File(EXPORT_DIR).exists());
    }

    /**
     * Test if the mas2g file has been made.
     */
    @Test
    public void testMAS2GExists() {
        String mas2gfile = EXPORT_DIR + MAS2G;
        assertTrue(mas2gfile, new File(mas2gfile).exists());

    }


    /**
     * Test if the mas2g file has been made.
     */
    @Test
    public void testXMLExists() {
        String xml = EXPORT_DIR + XML_SRC;
        assertTrue(xml, new File(xml).exists());
    }

    /**
     * Test if the agent directory has been made.
     */
    @Test
    public void testAgentFolderExists() {
        assertTrue(AGENT_DIR, new File(AGENT_DIR).exists());
    }

    /**
     * Test if the goal files have been made.
     */
    @Test
    public void testGoalFilesExists() {
        for (String file : GOAL_FILES) {
            assertTrue(AGENT_DIR + file, new File(AGENT_DIR + file).exists());
        }
    }

    /****************************************************************************************************************
     * Now follow the integrity tests.                                                                              *
     ****************************************************************************************************************/

    /**
     * Test of the robot.goal is equal to that in the working directory
     */
    @Test
    public void compareRobotGoalFiles() throws IOException {
        boolean result = FileUtils.contentEquals(new File(AGENT_GOAL_FILE), new File(AGENT_GOAL_FILE_WORKING));
        assertTrue("AGENT_GOAL_FILE <==> AGENT_GOAL_FILE_WORKING", result);
    }

}