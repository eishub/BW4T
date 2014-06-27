package nl.tudelft.bw4t.scenariogui;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import nl.tudelft.bw4t.map.EntityType;

/**
 * This class stores all the date from the BotEditorUI.
 *
 * @version 0.1
 * @since 12-05-2014
 */

public final class BotConfig implements Serializable, Cloneable {
    public static final String DEFAULT_GOAL_FILENAME_REFERENCE = "robot";
    public static final String DEFAULT_GOAL_FILENAME = "robot.goal";
    private static final long serialVersionUID = -4261058226493472776L;

    private String name = "Bot";
    private EntityType controller = EntityType.AGENT;
    private int amount = 1;
    private int botSize = 2;
    private int botSpeed = 100;
    private int botBatteryCapacity = 10;
    private int numberOfGrippers = 1;
    private boolean batteryEnabled = false;
    private boolean hasColorBlindHandicap = false;
    private boolean hasGripperHandicap = false;
    private boolean hasMoveSpeedHandicap = false;
    private boolean hasSizeOverloadHandicap = false;
    private String fileName = "*.goal";
    private String referenceName = "";

    /**
     * @return the default configuration of a Human
     */
    public static BotConfig createDefaultHumans() {
        BotConfig bot = new BotConfig();
        bot.setBotName("Human");
        bot.setBotController(EntityType.HUMAN);
        return bot;
    }

    /**
     * @return the default configuration of a robot
     */
    public static BotConfig createDefaultRobot() {
        BotConfig bot = new BotConfig();
        bot.setBotController(EntityType.AGENT);
        return bot;
    }

    /**
     * Calculate the discharge rate given the size and speed of the bot.
     *
     * @param size  the size of the bot
     * @param speed the speed of the bot
     * @return the discharge rate per step
     */
    public static double calculateDischargeRate(int size, int speed) {
        return 0.0002 * size + 0.0004 * speed;
    }

    public String getBotName() {
        return this.name;
    }

    @XmlElement
    public void setBotName(String name) {
        this.name = name;
    }

    public EntityType getBotController() {
        return this.controller;
    }

    @XmlElement
    public void setBotController(EntityType controller) {
        this.controller = controller;
    }

    public int getBotAmount() {
        return this.amount;
    }

    @XmlElement
    public void setBotAmount(int amount) {
        this.amount = amount;
    }

    public int getBotSize() {
        return botSize;
    }

    @XmlElement
    public void setBotSize(int newSize) {
        botSize = newSize;
    }

    public int getBotSpeed() {
        return botSpeed;
    }

    @XmlElement
    public void setBotSpeed(int newSpeed) {
        botSpeed = newSpeed;
    }

    public boolean isBatteryEnabled() {
        return this.batteryEnabled;
    }

    @XmlElement
    public void setBatteryEnabled(boolean batteryEnabled) {
        this.batteryEnabled = batteryEnabled;
    }

    public int getBotBatteryCapacity() {
        return botBatteryCapacity;
    }

    @XmlElement
    public void setBotBatteryCapacity(int newBatteryCapacity) {
        botBatteryCapacity = newBatteryCapacity;
    }

    public double getBotBatteryDischargeRate() {
        return calculateDischargeRate(botSize, botSpeed);
    }

    public boolean getColorBlindHandicap() {
        return hasColorBlindHandicap;
    }

    @XmlElement
    public void setColorBlindHandicap(boolean bool) {
        hasColorBlindHandicap = bool;
    }

    public boolean getGripperHandicap() {
        return hasGripperHandicap;
    }

    @XmlElement
    public void setGripperHandicap(boolean bool) {
        hasGripperHandicap = bool;
    }

    public boolean getMoveSpeedHandicap() {
        return hasMoveSpeedHandicap;
    }

    @XmlElement
    public void setMoveSpeedHandicap(boolean bool) {
        hasMoveSpeedHandicap = bool;
    }

    public boolean getSizeOverloadHandicap() {
        return hasSizeOverloadHandicap;
    }

    @XmlElement
    public void setSizeOverloadHandicap(boolean bool) {
        hasSizeOverloadHandicap = bool;
    }

    public int getGrippers() {
        return this.numberOfGrippers;
    }

    @XmlElement
    public void setGrippers(int grippers) {
        this.numberOfGrippers = grippers;
    }

    /**
     * Returns all the properties as a String.
     *
     * @return All the BotConfig properties.
     */
    public String bcToString() {
        return name + controller + amount + botSize
                + botSpeed + botBatteryCapacity
                + numberOfGrippers + batteryEnabled + hasColorBlindHandicap
                + hasGripperHandicap + hasMoveSpeedHandicap
                + hasSizeOverloadHandicap + fileName + referenceName;
    }

    public String getReferenceName() {
        return referenceName;
    }

    @XmlElement
    public void setReferenceName(String _referenceName) {
        this.referenceName = _referenceName;
    }

    public String getFileName() {
        return fileName;
    }

    @XmlElement
    public void setFileName(String _fileName) {
        this.fileName = _fileName;
    }

    @Override
    public BotConfig clone() {
        try {
            return (BotConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
