package nl.tudelft.bw4t.robots;

/**
 * Represents the robot's battery.
 * 
 * @author Valentine
 * @author Wendy
 */
public class Battery {
	/**
	 * A battery has a max capacity.
	 */
	private int max;
	
	/**
	 * A battery has a current capacity. 
	 */
	private int current;
	
	/**
	 * A battery has a discharge rate, 
	 * which represents how much the battery decreases per tick.
	 */
	private int dRate;
	/**
	 * Constructor. TODO: should we really allow the current value to be negative?
	 * @param m The max. capacity of the battery.
	 * @param c The current amount of energy left.
	 * @param dr The discharge rate.
	 */
	public Battery(int m, int c, int dr) {
		this.max = m;
		this.current = c;
		this.dRate = dr;
	}
	
	public int getCurrentCapacity() {
		return this.current;
	}
	
	public int getDischargeRate() {
		return this.dRate;
	}
	
	/**
	 * Returns percentage. TODO: make this function more accurate.
	 * Cast to double and reformat maybe?
	 * @return the percentage of battery power left.
	 */
	public int getPercentage() {
		return (int) ((((double) this.current) * 100.0) / ((double) this.max));
	}
	
	/**
	 * If the charging of the battery exceeds the max capacity, 
	 * the current capacity is set to the max capacity.
	 */
	public void recharge() {
		this.current = this.max;
	}
	
	/**
	 * If the battery is infinite, DO NOT DISCHARGE IT.
	 * If the emptying of the battery falls below 0, 
	 * the current capacity is set to 0.
	 */
	public void discharge() {
		if (this.max != Integer.MAX_VALUE) {
			int temp = this.current - this.dRate;
			
			if (temp < 0) {
				this.current = 0;
			}
			else {
				this.current = temp;
			}
		}
	}
}
