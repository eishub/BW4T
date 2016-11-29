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

	// note, these values may differ from the default values in server.
	private String name = "Bot";
	private EntityType controller = EntityType.AGENT;
	private int amount = 1;
	private int botSize = 2;
	private int botSpeed = 50;
	private String fileName = "*.goal";
	private String referenceName = "";

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
	 * discharge rate is 0.0002 * size + 0.0004 * speed.
	 *
	 * @param size
	 *            the size of the bot
	 * @param speed
	 *            the speed of the bot
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

	/**
	 * Returns all the properties as a String.
	 *
	 * @return All the BotConfig properties.
	 */
	public String bcToString() {
		return name + controller + amount + botSize + botSpeed + fileName + referenceName;
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
