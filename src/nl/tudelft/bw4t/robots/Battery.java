package nl.tudelft.bw4t.robots;

/**
 * Represents the robot's battery.
 * 
 * @author Valentine
 */
public class Battery 
{
	/**
	 * A battery has a max capacity.
	 * 
	 * A battery has a current capacity.
	 * 
	 * A battery has a discharge rate, 
	 * which represents how much the battery decreases per tick.
	 */
	private int max;
	private int current;
	private int dRate;
	
	public Battery(int m, int c, int dr)
	{
		this.max = m;
		this.current = c;
		this.dRate = dr;
	}
	
	public int getCurrentCapacity()
	{
		return this.current;
	}
	
	public int getDischargeRate()
	{
		return this.dRate;
	}
	
	public int getPercentage()
	{
		return (this.current * 100) / this.max;
	}
	
	/**
	 * If the charging of the battery exceeds the max capacity, 
	 * the current capacity is set to the max capacity.
	 */
	public void recharge()
	{
		/*
		int temp = this.current + this.drate;
		
		if (temp > this.max)
		{
			this.current = this.max;
		}
		else
		{
			this.current = temp;
		}
		*/
		
		this.current = this.max;
	}
	
	/**
	 * If the battery is infinite, DO NOT DISCHARGE IT.
	 * If the emptying of the battery falls below 0, 
	 * the current capacity is set to 0.
	 */
	public void discharge()
	{
		if (this.max != Integer.MAX_VALUE)
		{
			int temp = this.current - this.dRate;
			
			if (temp < 0)
			{
				this.current = 0;
			}
			else
			{
				this.current = temp;
			}
		}
	}
}
