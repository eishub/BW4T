package main.java.nl.tudelft.bw4t.scenariogui.gui.botstore

/**
 * @author Valentine
 * This class stores all the date from the BotEditorUI. 
 */
public final class BotEditorData {
	
	private int size = 2;
	private int batteryCapacity = 0;
	private int numberOfGrippers = 1;
	private double speedMod = 1.0;
	private double batteryDischargeRate = 0;
	private boolean hasColorBlindHandicap = false;
	
	/**
	 * This class does not need a constructor.
	 */
	public BotEditorData() {	
	}
	
	/**
	 * @return the size of the robot.
	 */
	public int getBotSize() {
		return size;
	}
	
	/**
	 * @param newSize, the new size of the robot.
	 */
	public void setBotSize(int newSize) {
		size = newSize;
	}
	
	/**
	 * @return the robot's battery capacity.
	 */
	public int getBatteryCapacity() {
		return batteryCapacity;
	}
	
	/**
	 * @param newBatteryCapacity, the new robot's battery capacity.
	 */
	public void setBatteryCapacity(int newBatteryCapacity) {
		batteryCapacity = newBatteryCapacity;
	}

	/**
	 * @return if the robot has a gripper handicap.
	 */
	public int getNumberOfGrippers() {
		return numberOfGrippers;
	}
	
	/**
	 * @param bool, adds or removes the gripper handicap.
	 */
	public void setNumberOfGrippers(int val) {
		numberOfGrippers = val;
	}
	
	/**
	 * @return the robot's battery discharge rate.
	 */
	public double getBotBatteryDischargeRate() {
		return batteryDischargeRate;
	}
	
	/**
	 * @param newBatteryDischargeRate, the new robot's battery discharge rate.
	 */
	public void setBotBatteryDischargeRate(double newBatteryDischargeRate) {
		batteryDischargeRate = newBatteryDischargeRate;
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
	public void setColorBlindHandicap(boolean bool) {
		hasColorBlindHandicap = bool;
	}	

}
