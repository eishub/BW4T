package nl.tudelft.bw4t.scenariogui.gui.botstore;

/**
 * @author Valentine
 * This class stores all the date from the BotEditorUI. 
 */
public final class BotEditorData {
	private String name;
	private int size = 2;
	private int batteryCapacity = 0;
	private int numberOfGrippers = 1;
	private double speedMod = 1.0;
	private double batteryDischargeRate = 0;
	private boolean hasColorBlindHandicap = false;
	
	/**
	 * Still needs to be implemented
	 */
	public BotEditorData(String n, int s, int bat, int nog, double speedM, double batDis, boolean cb) {	
		name = n;
		size = s;
		batteryCapacity = bat;
		numberOfGrippers = nog;
		speedMod = speedM;
		batteryDischargeRate = batDis;
		hasColorBlindHandicap = cb;
	}

	/**
	 * @return the name of the bot
	 */
	public String getBotName() {
		return name;
	}
	
	/**
	 * @param newName, sets the new name of the bot
	 */
	public void setBotSize(String newName) {
		name = newName;
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
	 * @return the bot movespeed
	 */
	public double getSpeedMod() {
		return speedMod;
	}
	
	/**
	 * @param newSpeedMod, the new speedMod of the robot (0.5 to 1.5)
	 */
	public void setSpeedMod(double newSpeedMod) 
{		speedMod = newSpeedMod;
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
