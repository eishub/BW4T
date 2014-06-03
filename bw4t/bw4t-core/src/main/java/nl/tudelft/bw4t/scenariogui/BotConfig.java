package nl.tudelft.bw4t.scenariogui;

import javax.xml.bind.annotation.XmlElement;

/**
 * This class stores all the date from the BotEditorUI.         
 * <p>
 * @author      Valentine Mairet  
 * @author      Katia Asmoredjo
 * @version     0.1                
 * @since       12-05-2014        
 */

public final class BotConfig {
    
    private String name = "Bot";
    
    //FIXME This should be an enum!
    private String controller = "Agent";
    
    private int amount = 1;
    
    private int botGripperCapacity = 1;

    private int botSize = 2;

    private int botSpeed = 100;

    private int botBatteryCapacity = 0;

    private int botBatteryDischargeRate = 0;

    private boolean hasColorBlindHandicap = false;

    private boolean hasGripperHandicap = false;

    private boolean hasMoveSpeedHandicap = false;

    private boolean hasSizeOverloadHandicap = false;
    
    private boolean batteryEnabled = false;
    
    private String fileName = "*.goal";
    
    private String referenceName = "";

    /**
     * Sets the name of the bot.
     * @param name The name of the bot.
     */
    @XmlElement
    public void setBotName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of the bot.
     * @return The name of the bot.
     */
    public String getBotName() {
        return this.name;
    }
    
    /**
     * Sets the controller type of the bot.
     * @param controller The controller type of the bot.
     */
    @XmlElement
    public void setBotController(String controller) {
        this.controller = controller;
    }
    
    /**
     * Returns the controller type of the bot.
     * @return The controller type of the bot.
     */
    public String getBotController() {
        return this.controller;
    }
    
    /**
     * Sets the amount of bots of a type there are.
     * @param amount The amount of bots.
     */
    public void setBotAmount(int amount) {
        this.amount = amount;
    }
    
    /**
     * Returns the amount of bots of this type.
     * @return The amount of bots of this type.
     */
    public int getBotAmount() {
        return this.amount;
    }
    
    /**
     * @return the bot gripper capacity. 
     */
    public int getBotGripperCapacity() {
    	return botGripperCapacity;
    }
	
    /**
     * @param newgripcap, the new gripper capacity. 
     */
    @XmlElement
	public void setBotGripperCapacity(int newgripcap) {
		botGripperCapacity = newgripcap;
	}

    /**
     * @return the size of the robot.
     */
    public int getBotSize() {
        return botSize;
    }

    /**
     * @param newSize, the new size of the robot.
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
     * @param newSpeed, the new speed of the robot.
     */
    @XmlElement
    public void setBotSpeed(int newSpeed) {
        botSpeed = newSpeed;
    }

    /**
     * @return the robot's battery capacity.
     */
    public int getBotBatteryCapacity() {
        return botBatteryCapacity;
    }

    /**
     * @param newBatteryCapacity, the new robot's battery capacity.
     */
    @XmlElement
    public void setBotBatteryCapacity(int newBatteryCapacity) {
        botBatteryCapacity = newBatteryCapacity;
    }

    /**
     * @return the robot's battery discharge rate.
     */
    public int getBotBatteryDischargeRate() {
        return botBatteryDischargeRate;
    }

    /**
     * @param newBatteryDischargeRate, the new robot's battery discharge rate.
     */
    @XmlElement
    public void setBotBatteryDischargeRate(int newBatteryDischargeRate) {
        botBatteryDischargeRate = newBatteryDischargeRate;
    }

    /**
     * @return if the robot has a color blind handicap.
     */
    public boolean getColorBlindHandicap() {
        return hasColorBlindHandicap;
    }

    /**
     * @param bool, adds or removes the color blind handicap.
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
     * @param bool, adds or removes the gripper handicap.
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
     * @param bool, adds or removes the move speed handicap.
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
     * @param bool, adds or removes the size overload handicap.
     */
    @XmlElement
    public void setSizeOverloadHandicap(boolean bool) {
        hasSizeOverloadHandicap = bool;
    }
    
    /**
     * Returns all the properties as a String.
     * @return All the BotConfig properties.
     */
    public String bcToString() {
        return name + controller + amount + botSize + botSpeed
                + botBatteryCapacity + botBatteryDischargeRate 
                + hasColorBlindHandicap + hasGripperHandicap
                + hasMoveSpeedHandicap + hasSizeOverloadHandicap;
    }
	public String getName() {
		return name;
	}

	public void setName(String _name) {
		this.name = _name;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String _referenceName) {
		this.referenceName = _referenceName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String _fileName) {
		this.fileName = _fileName;
	}

	public void setBatteryEnabled(boolean batteryEnabled) {
		this.batteryEnabled = batteryEnabled;
	}
}
