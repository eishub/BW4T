package nl.tudelft.bw4t.scenariogui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import nl.tudelft.bw4t.map.EntityType;

/**
 * Class used for testing the BotConfig class except the getters and setters.
 */
public class BotConfigTest {
    private String name;
    private EntityType controller;
    private int amount;
    private int botSize;
    private int botSpeed;
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
                + botSpeed + fileName + referenceName), bot.bcToString());
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
        controller = EntityType.AGENT;
        amount = 10;
        botSize = 3;
        botSpeed = 60;
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
        bot.setFileName(fileName);
        bot.setReferenceName(referenceName);
        return bot;
    }
}
