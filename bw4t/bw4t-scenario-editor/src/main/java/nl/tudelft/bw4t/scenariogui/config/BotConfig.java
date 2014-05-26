package nl.tudelft.bw4t.scenariogui.config;

import javax.xml.bind.annotation.XmlElement;

/**
 * This class stores all the date from the BotEditorUI.         
 * <p>
 * @author      Valentine Mairet  
 * @version     0.1                
 * @since       12-05-2014        
 */

public final class BotConfig {

    private int botSize = 2;

    private int botSpeed = 100;

    private int botBatteryCapacity = 0;

    private int botBatteryDischargeRate = 0;

    private boolean hasColorBlindHandicap = false;

    private boolean hasGripperHandicap = false;

    private boolean hasMoveSpeedHandicap = false;

    private boolean hasSizeOverloadHandicap = false;


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
}
