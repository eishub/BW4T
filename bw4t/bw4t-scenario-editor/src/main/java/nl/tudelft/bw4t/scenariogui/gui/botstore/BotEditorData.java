package nl.tudelft.bw4t.scenariogui.gui.botstore;

/**
 * @author Valentine
 * This class stores all the date from the BotEditorUI. 
 */
public final class BotEditorData {
	/**
	 * The size of the bot (default = 2).
	 */
	private int botSize = 2;
	/**
	 * The speed of the bot (default = 100).
	 */
	private int botSpeed = 100;
	/**
	 * The battery capacity of the bot (default = 0).
	 */
	private int botBatteryCapacity = 0;
	/**
	 * The battery discharge rate of the bot (default = 0).
	 */
	private int botBatteryDischargeRate = 0;
	/**
	 * Boolean value to determine if the bot is color blind (default = false).
	 */
	private boolean hasColorBlindHandicap = false;
	/**
	 * Boolean value to determine if the bot can't hold blocks (default = false).
	 */
	private boolean hasGripperHandicap = false;
	/**
	 * Boolean value to determine if the robot has a changed move speed.
	 */
	private boolean hasMoveSpeedHandicap = false;
	/**
	 * Boolean value to determine if the robot has a changed size.
	 */
	private boolean hasSizeOverloadHandicap = false;
	/**
	 * Boolean value to determine if the robot can be left
	 * alone without it giving warnings.
	 */
	private boolean leftAlone = false;
	/**
	 * Boolean value to determine if the bot can use GPS (default = false).
	 */
	private boolean gps = false;
	
	/**
	 * This class does not need a constructor.
	 */
	public BotEditorData() {	
	}
	
	/**
	 * @return the size of the robot.
	 */
	public int getBotSize() {
		return botSize;
	}
	
	/**
	 * @param newSize the new size of the robot.
	 */
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
	 * @param newSpeed the new speed of the robot.
	 */
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
	 * @param newBatteryCapacity the new robot's battery capacity.
	 */
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
	 * @param newBatteryDischargeRate the new robot's battery discharge rate.
	 */
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
	 * @param bool adds or removes the color blind handicap.
	 */
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
	 * @param bool adds or removes the gripper handicap.
	 */
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
	 * @param bool adds or removes the move speed handicap.
	 */
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
	 * @param bool adds or removes the size overload handicap.
	 */
	public void setSizeOverloadHandicap(boolean bool) {
		hasSizeOverloadHandicap = bool;
	}
	/**
	 * @param enabled adds or removes the ability to leave the robot alone.
	 */
	public void setLeftAlone(boolean enabled) {
		leftAlone = enabled;
	}
	/**
	 * @return whether or not the robot can be left alone.
	 */
	public boolean getLeftAlone() {
		return leftAlone;
	}
	/**
	 * @param enabled adds or removes the GPS usability.
	 */
	public void setGPS(boolean enabled) {
		gps = enabled;
	}
	/**
	 * @return whether or not the robot can make use of the GPS functionality.
	 */
	public boolean getGPS() {
		return gps;
	}
}
