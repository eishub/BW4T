package nl.tudelft.bw4t.scenariogui;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import nl.tudelft.bw4t.agent.EntityType;

/**
 * This class stores all the date from the BotEditorUI.
 * 
 * @author Valentine Mairet
 * @author Katia Asmoredjo
 * @version 0.1
 * @since 12-05-2014
 */

public final class BotConfig implements Serializable {
	private static final long serialVersionUID = -4261058226493472776L;

	/** The default reference names for the goal files */
    public static final String DEFAULT_GOAL_FILENAME_REFERENCE = "robot";

    /** The Default filename for the robot goal files */
    public static final String DEFAULT_GOAL_FILENAME = "robot.goal";

	private String name = "Bot";

	private EntityType controller = EntityType.AGENT;

	private int amount = 1;

	private int botSize = 2;

	private int botSpeed = 100;

	private int botBatteryCapacity = 10;

	private double botBatteryDischargeRate = 0.1;

	private int numberOfGrippers = 1;

	private boolean batteryEnabled = false;

	private boolean hasColorBlindHandicap = false;

	private boolean hasGripperHandicap = false;

	private boolean hasMoveSpeedHandicap = false;

	private boolean hasSizeOverloadHandicap = false;

	private String fileName = "*.goal";

	private String referenceName = "";

	/**
	 * Sets the name of the bot.
	 * 
	 * @param name
	 *            The name of the bot.
	 */
	@XmlElement
	public void setBotName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the bot.
	 * 
	 * @return The name of the bot.
	 */
	public String getBotName() {
		return this.name;
	}

	/**
	 * Sets the controller type of the bot.
	 * 
	 * @param controller
	 *            The controller type of the bot.
	 */
	@XmlElement
	public void setBotController(EntityType controller) {
		this.controller = controller;
	}

	/**
	 * Returns the controller type of the bot.
	 * 
	 * @return The controller type of the bot.
	 */
	public EntityType getBotController() {
		return this.controller;
	}

	/**
	 * Sets the amount of bots of a type there are.
	 * 
	 * @param amount
	 *            The amount of bots.
	 */
	@XmlElement
	public void setBotAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Returns the amount of bots of this type.
	 * 
	 * @return The amount of bots of this type.
	 */
	public int getBotAmount() {
		return this.amount;
	}

	/**
	 * @return the size of the robot.
	 */
	public int getBotSize() {
		return botSize;
	}

	/**
	 * @param newSize
	 *            , the new size of the robot.
	 */
	@XmlElement
	public void setBotSize(int newSize) {
		botSize = newSize;
	}

	/**
	 * @return the speed of the robot.
	 */
	public int getBotSpeed() {
		return botSpeed;
	}

	/**
	 * @param newSpeed
	 *            , the new speed of the robot.
	 */
	@XmlElement
	public void setBotSpeed(int newSpeed) {
		botSpeed = newSpeed;
	}

	/**
	 * Sets if the battery is enabled or not.
	 * 
	 * @param batteryEnabled
	 *            If the battery is enabled.
	 */
	@XmlElement
	public void setBatteryEnabled(boolean batteryEnabled) {
		this.batteryEnabled = batteryEnabled;
	}

	/**
	 * Returns if the battery is enabled or not.
	 * 
	 * @return If the battery is enabled.
	 */
	public boolean isBatteryEnabled() {
		return this.batteryEnabled;
	}

	/**
	 * @return the robot's battery capacity.
	 */
	public int getBotBatteryCapacity() {
		return botBatteryCapacity;
	}

	/**
	 * @param newBatteryCapacity
	 *            , the new robot's battery capacity.
	 */
	@XmlElement
	public void setBotBatteryCapacity(int newBatteryCapacity) {
		botBatteryCapacity = newBatteryCapacity;
	}

	/**
	 * @return the robot's battery discharge rate.
	 */
	public double getBotBatteryDischargeRate() {
		return botBatteryDischargeRate;
	}

	/**
	 * @param newBatteryDischargeRate
	 *            , the new robot's battery discharge rate.
	 */
	@XmlElement
	public void setBotBatteryDischargeRate(double newBatteryDischargeRate) {
		botBatteryDischargeRate = newBatteryDischargeRate;
	}

	/**
	 * @return if the robot has a color blind handicap.
	 */
	public boolean getColorBlindHandicap() {
		return hasColorBlindHandicap;
	}

	/**
	 * @param bool
	 *            , adds or removes the color blind handicap.
	 */
	@XmlElement
	public void setColorBlindHandicap(boolean bool) {
		hasColorBlindHandicap = bool;
	}

	/**
	 * @return if the robot has a gripper handicap.
	 */
	public boolean getGripperHandicap() {
		return hasGripperHandicap;
	}

	/**
	 * @param bool
	 *            , adds or removes the gripper handicap.
	 */
	@XmlElement
	public void setGripperHandicap(boolean bool) {
		hasGripperHandicap = bool;
	}

	/**
	 * @return if the robot has a move speed handicap.
	 */
	public boolean getMoveSpeedHandicap() {
		return hasMoveSpeedHandicap;
	}

	/**
	 * @param bool
	 *            , adds or removes the move speed handicap.
	 */
	@XmlElement
	public void setMoveSpeedHandicap(boolean bool) {
		hasMoveSpeedHandicap = bool;
	}

	/**
	 * @return if the robot has a size overload handicap.
	 */
	public boolean getSizeOverloadHandicap() {
		return hasSizeOverloadHandicap;
	}

	/**
	 * @param bool
	 *            , adds or removes the size overload handicap.
	 */
	@XmlElement
	public void setSizeOverloadHandicap(boolean bool) {
		hasSizeOverloadHandicap = bool;
	}

	/**
	 * Sets the amount of grippers a bot has.
	 * 
	 * @param grippers
	 *            The amount of grippers.
	 */
	@XmlElement
	public void setGrippers(int grippers) {
		this.numberOfGrippers = grippers;
	}

	/**
	 * Returns the amount of grippers the bot has.
	 * 
	 * @return The amount of grippers the bot has.
	 */
	public int getGrippers() {
		return this.numberOfGrippers;
	}

	/**
	 * Returns all the properties as a String.
	 * 
	 * @return All the BotConfig properties.
	 */
	public String bcToString() {
		return name + controller + amount + botSize
				+ botSpeed + botBatteryCapacity + botBatteryDischargeRate
				+ numberOfGrippers + batteryEnabled + hasColorBlindHandicap
				+ hasGripperHandicap + hasMoveSpeedHandicap
				+ hasSizeOverloadHandicap + fileName + referenceName;
	}

	/**
	 * Returns the reference name in goal.
	 * @return The reference name in goal.
	 */
	public String getReferenceName() {
		return referenceName;
	}

	/**
	 * Sets the reference name in goal.
	 * @param _referenceName The reference name in goal.
	 */
	@XmlElement
	public void setReferenceName(String _referenceName) {
		this.referenceName = _referenceName;
	}

	/**
	 * Returns the goal file name.
	 * @return The goal file name.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the goal file name.
	 * @param _fileName The goal file name.
	 */
	@XmlElement
	public void setFileName(String _fileName) {
		this.fileName = _fileName;
	}

}
