package nl.tudelft.bw4t.scenariogui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import nl.tudelft.bw4t.map.EntityType;

import org.junit.Test;

/**
 * Class used for testing the BotConfig class except the getters and setters.
 */
public class BotConfigTest {

    private String name;
    private EntityType controller;
    private int amount;
    private int botSize;
    private int botSpeed;
    private int botBatteryCapacity;
    private int numberOfGrippers;
    private boolean batteryEnabled;
    private boolean hasColorBlindHandicap;
    private boolean hasGripperHandicap;
    private boolean hasMoveSpeedHandicap;
    private boolean hasSizeOverloadHandicap;
    private String fileName;
    private String referenceName;

    /**
     * Tests whether the toString method displays the desired result.
     */
    @Test
    public void bcToStringTest() {
        setDefaultValues();
        BotConfig bot = createTestBot();
        assertNotEquals("Wrong string test", bot.bcToString());
        assertEquals((name + controller + amount + botSize
                + botSpeed + botBatteryCapacity
                + numberOfGrippers + batteryEnabled + hasColorBlindHandicap
                + hasGripperHandicap + hasMoveSpeedHandicap
                + hasSizeOverloadHandicap + fileName + referenceName), bot.bcToString());
    }

    /**
     * Test for cloning a bot
     */
    @Test
    public void cloneTest() {
        BotConfig bot = createTestBot();
        BotConfig clonedbot = bot.clone();
        assertEquals(bot.bcToString(), clonedbot.bcToString());
    }

    /**
     * Tests whether the default human bot created is of type HUMAN and
     * only differs in controller type from the default bot.
     */
    @Test
    public void createDefaultHumansTest() {
        BotConfig humanBot = BotConfig.createDefaultHumans();
        BotConfig defaultBot = new BotConfig();

        defaultBot.setBotName("Human");
        assertEquals(humanBot.getBotController(), EntityType.HUMAN);

        EntityType defaultController = defaultBot.getBotController();
        humanBot.setBotController(defaultController);
        assertEquals(humanBot.bcToString(), defaultBot.bcToString());
    }

    /**
     * Tests whether the default human bot created is of type HUMAN and
     * only differs in controller type from the default bot.
     */
    @Test
    public void createDefaultRobotTest() {
        BotConfig staticDefaultBot = BotConfig.createDefaultRobot();
        BotConfig defaultBot = new BotConfig();

        assertEquals(staticDefaultBot.getBotController(), EntityType.AGENT);

        EntityType defaultController = defaultBot.getBotController();
        staticDefaultBot.setBotController(defaultController);
        assertEquals(staticDefaultBot.bcToString(), defaultBot.bcToString());
    }

    private void setDefaultValues() {
        name = "testName";
        controller = EntityType.HUMAN;
        amount = 10;
        botSize = 3;
        botSpeed = 60;
        botBatteryCapacity = 100;
        numberOfGrippers = 2;
        batteryEnabled = true;
        hasColorBlindHandicap = true;
        hasGripperHandicap = false;
        hasMoveSpeedHandicap = false;
        hasSizeOverloadHandicap = false;
        fileName = "robot_test.goal";
        referenceName = "ref_test_name";
    }

    private BotConfig createTestBot() {
        BotConfig bot = new BotConfig();
        bot.setBotName(name);
        bot.setBotController(controller);
        bot.setBotAmount(amount);
        bot.setBotSize(botSize);
        bot.setBotSpeed(botSpeed);
        bot.setBotBatteryCapacity(botBatteryCapacity);
        bot.setGrippers(numberOfGrippers);
        bot.setBatteryEnabled(batteryEnabled);
        bot.setColorBlindHandicap(hasColorBlindHandicap);
        bot.setGripperHandicap(hasGripperHandicap);
        bot.setMoveSpeedHandicap(hasMoveSpeedHandicap);
        bot.setSizeOverloadHandicap(hasSizeOverloadHandicap);
        bot.setFileName(fileName);
        bot.setReferenceName(referenceName);
        return bot;
    }

}
