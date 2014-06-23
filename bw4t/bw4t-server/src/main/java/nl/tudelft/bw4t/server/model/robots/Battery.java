package nl.tudelft.bw4t.server.model.robots;

/**
 * Represents the robot's battery.
 */
public class Battery {
    /**
     * A battery has a max capacity.
     */
    private int max;
    
    /**
     * A battery has a current capacity. 
     */
    private double current;
    
    /**
     * A battery has a discharge rate, 
     * which represents how much the battery decreases per tick.
     */
    private double dRate;

    /**
     * Create a fully charged battery.
     * @param maxCharge the max capacity of the battery
     * @param dischargeRate the discharge rate per tick
     */
    public Battery(int maxCharge, double dischargeRate) {
        this.max = maxCharge;
        this.current = maxCharge;
        this.dRate = dischargeRate;
    }
    
    /**
     * Create a new Battery with the given configuration.
     * @param maxCharge The max. capacity of the battery.
     * @param currentCharge The current amount of energy left.
     * @param dischargeRate The discharge rate.
     */
    public Battery(int maxCharge, double currentCharge, double dischargeRate) {
        this.max = maxCharge;
        this.current = currentCharge;
        this.dRate = dischargeRate;
    }
    
    public double getCurrentCapacity() {
        return this.current;
    }
    
    public double getDischargeRate() {
        return this.dRate;
    }
    
    /**
     * Returns percentage.
     * @return the percentage of battery power left.
     */
    public double getPercentage() {
        return (this.current * 100.0) / this.max;
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
            double temp = this.current - this.dRate;
            
            if (temp < 0) {
                this.current = 0;
            } else {
                this.current = temp;
            }
        }
    }
}
