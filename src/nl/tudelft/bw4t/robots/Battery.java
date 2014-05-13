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
	private int drate;
	
	public Battery(int m, int c, int dr)
	{
		this.max = m;
		this.current = c;
		this.drate = dr;
	}
	
	public int getCurrentCapacity()
	{
		return this.current;
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
	 * If the emptying of the battery falls below 0, 
	 * the current capacity is set to 0.
	 */
	public void discharge()
	{
		int temp = this.current - this.drate;
		
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
