package nl.tudelft.bw4t.scenariogui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Test;


public class BW4TClientConfigTest {

    private static final String BASE = System.getProperty("user.dir") + "/src/test/resources/";
    private static final String TEST_XML_OUTPUT = BASE + "test_output.xml";

    private String fileLocation = TEST_XML_OUTPUT;
    private static final String TEST_XML_INPUT = BASE + "test_input.xml";
    private String clientIp = "192.168.13.37";
    private int clientPort = 1234;
    private String serverIp = "192.168.73.31";
    private int serverPort = 4321;
    private boolean launchGui = false;
    private String mapFile = "test.map";
    private boolean useGoal = true;
    private BW4TClientConfig config;
    private BW4TClientConfig config2;

    /**
     * Used to clean up any files created during testing.
     */
    @After
    public void cleanUp() {
        removeFile(TEST_XML_OUTPUT);
    }

    /**
     * Tests whether a Client config is correctly saved to XML.
     */
    @Test
    public void toXMLTest() {
        try {
            config = new BW4TClientConfig();
            config.setFileLocation(TEST_XML_OUTPUT);
            config.toXML();

            assertTrue(fileExists(TEST_XML_OUTPUT));

        } catch (FileNotFoundException e) {
            fail("File not found exception: " + e + ". Failed to save to file.");
        } catch (JAXBException e) {
            fail("JAXB Exception: " + e + ". Failed to save XML.");
        }
    }

    /**
     * Tests whether a XML file can be correctly opened and saved to BW4TClientConfig object.
     */
    @Test
    public void fromXMLTest() {
        try {
            assertTrue(fileExists(TEST_XML_INPUT));
            Object config = BW4TClientConfig.fromXML(TEST_XML_INPUT);
            assertTrue(config instanceof BW4TClientConfig);
        } catch (FileNotFoundException e) {
            fail("File not found exception: " + e + ". Failed to save to file.");
        } catch (JAXBException e) {
            fail("JAXB Exception: " + e + ". Failed to open XML.");
        }

    }

    /**
     * Tests whether a XML file is saved and opened correctly.     *
     */
    @Test
    public void toFromXMLCorrectlyTest() {
        try {

            config = setConfig();

            config.addBot(new BotConfig());
            config.addBot(new BotConfig());
            config.addEpartner(new EPartnerConfig());
            config.addEpartner(new EPartnerConfig());
            config.setFileLocation(TEST_XML_OUTPUT);
            config.toXML();
            BW4TClientConfig config2 = BW4TClientConfig.fromXML(TEST_XML_OUTPUT);

            assertEquals(clientIp, config2.getClientIp());
            assertEquals(clientPort, config2.getClientPort());
            assertEquals(serverIp, config2.getServerIp());
            assertEquals(serverPort, config2.getServerPort());
            assertEquals(launchGui, config2.isLaunchGui());
            assertEquals(mapFile, config2.getMapFile());
            assertEquals(useGoal, config2.isUseGoal());
            assertTrue(config.compareBotConfigs(config2.getBots()));
            assertTrue(config.compareEpartnerConfigs(config2.getEpartners()));
        } catch (FileNotFoundException e) {
            fail("File not found exception: " + e + ". Failed to save/open to file.");
        } catch (JAXBException e) {
            fail("JAXB Exception: " + e + ". Failed to save/open XML.");
        }
    }

    /**
     * Tests whether the update config for bots works.
     */
    @Test
    public void updateOldBotConfigsTest() {
        config = new BW4TClientConfig();
        assertEquals(config.getOldBots().size(), 0);
        config.addBot(new BotConfig());
        assertEquals(config.getOldBots().size(), 0);
        config.updateOldBotConfigs();
        assertEquals(config.getOldBots().size(), 1);
    }

    /**
     * Tests whether the update config for epartners works.
     */
    @Test
    public void updateOldEpartnerConfigsTest() {
        config = new BW4TClientConfig();
        assertEquals(config.getOldEpartners().size(), 0);
        config.addEpartner(new EPartnerConfig());
        assertEquals(config.getOldEpartners().size(), 0);
        config.updateOldEpartnerConfigs();
        assertEquals(config.getOldEpartners().size(), 1);
    }

    /**
     * Tests whether the bot amount is calculated correctly.
     */
    @Test
    public void getAmountBotTest() {
        config = new BW4TClientConfig();
        BotConfig bot = new BotConfig();
        bot.setBotAmount(10);
        config.addBot(bot);
        config.addBot(new BotConfig());
        assertEquals(config.getAmountBot(), 11);
    }

    /**
     * Tests whether the epartner amount is calculated correctly.
     */
    @Test
    public void getAmountEPartnerTest() {
        config = new BW4TClientConfig();
        EPartnerConfig epartner = new EPartnerConfig();
        epartner.setEpartnerAmount(10);
        config.addEpartner(epartner);
        config.addEpartner(new EPartnerConfig());
        assertEquals(config.getAmountEPartner(), 11);
    }

    /**
     * Tests whether two bot configs are not equal when they have different sizes.
     */
    @Test
    public void compareBotConfigsDifSizeTest() {
        config = new BW4TClientConfig();
        config2 = new BW4TClientConfig();
        BotConfig humanBot = BotConfig.createDefaultHumans();
        config.addBot(humanBot);
        config2.addBot(humanBot);
        config.addBot(new BotConfig());
        assertFalse(config.compareBotConfigs(config2.getBots()));
    }

    /**
     * Tests when two bot configs are equal.
     */
    @Test
    public void compareBotConfigsTestTrue() {
        config = new BW4TClientConfig();
        config2 = new BW4TClientConfig();
        BotConfig humanBot = BotConfig.createDefaultHumans();
        BotConfig differentObjectBot = BotConfig.createDefaultHumans();
        config.addBot(humanBot);
        config2.addBot(differentObjectBot);
        assertTrue(config.compareBotConfigs(config2.getBots()));
    }

    /**
     * Tests when two bot configs are not equal.
     */
    @Test
    public void compareBotConfigsTestFalse() {
        config = new BW4TClientConfig();
        config2 = new BW4TClientConfig();
        BotConfig humanBot = BotConfig.createDefaultHumans();
        BotConfig humanBot2 = BotConfig.createDefaultHumans();
        humanBot2.setBotAmount(10);
        config.addBot(humanBot);
        config2.addBot(humanBot2);
        assertFalse(config.compareBotConfigs(config2.getBots()));
    }

    /**
     * Tests when two epartner configs are not equal when they have different sizes.
     */
    @Test
    public void compareEpartnerConfigsDifSizeTest() {
        config = new BW4TClientConfig();
        config2 = new BW4TClientConfig();
        EPartnerConfig epartner = new EPartnerConfig();
        config.addEpartner(epartner);
        config2.addEpartner(epartner);
        config.addEpartner(new EPartnerConfig());
        assertFalse(config.compareEpartnerConfigs(config2.getEpartners()));
    }

    /**
     * Tests when two epartner configs are equal.
     */
    @Test
    public void compareEpartnerConfigsTestTrue() {
        config = new BW4TClientConfig();
        config2 = new BW4TClientConfig();
        EPartnerConfig epartner = new EPartnerConfig();
        EPartnerConfig differentObjectEpartner = new EPartnerConfig();
        config.addEpartner(epartner);
        config2.addEpartner(differentObjectEpartner);
        assertTrue(config.compareEpartnerConfigs(config2.getEpartners()));
    }

    /**
     * Tests whether two epartner configs not equal.
     */
    @Test
    public void compareEpartnerConfigsTestFalse() {
        config = new BW4TClientConfig();
        config2 = new BW4TClientConfig();
        EPartnerConfig epartner = new EPartnerConfig();
        EPartnerConfig epartner2 = new EPartnerConfig();
        epartner2.setEpartnerAmount(10);
        config.addEpartner(epartner);
        config2.addEpartner(epartner2);
        assertFalse(config.compareEpartnerConfigs(config2.getEpartners()));
    }


    /**
     * Checks whether the bot and epartner list are cleared correctly.
     */
    @Test
    public void clearBotsAndEpartnersTest() {
        config = new BW4TClientConfig();
        BotConfig humanBot = BotConfig.createDefaultHumans();
        EPartnerConfig epartner = new EPartnerConfig();
        config.addBot(humanBot);
        config.addEpartner(epartner);

        assertFalse(config.getBots().isEmpty());
        assertFalse(config.getEpartners().isEmpty());
        config.clearBotsAndEpartners();
        assertTrue(config.getBots().isEmpty());
        assertTrue(config.getEpartners().isEmpty());
    }


    /**
     * Constructs a test bot configuration.
     *
     * @return Returns a test bot configuration.
     */
    private BW4TClientConfig setConfig() {
        BW4TClientConfig config = new BW4TClientConfig();
        config.setClientIp(clientIp);
        config.setClientPort(clientPort);
        config.setServerIp(serverIp);
        config.setServerPort(serverPort);
        config.setLaunchGui(launchGui);
        config.setMapFile(mapFile);
        config.setFileLocation(fileLocation);
        config.setUseGoal(useGoal);
        return config;
    }

    /**
     * Tests whether a given file exists.
     *
     * @param fileName The file name.
     * @return Returns whether the file exists.
     */
    public boolean fileExists(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }

    /**
     * Used to remove a given file
     *
     * @param fileName The filename of the file to remove.
     */
    public void removeFile(String fileName) {
        if (fileExists(fileName)) {
            File f = new File(fileName);
            f.delete();
        }
    }

}
