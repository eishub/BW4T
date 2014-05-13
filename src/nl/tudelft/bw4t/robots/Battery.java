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
	 * A battery has a power value, 
	 * which represents how much the battery increases or decreases per tick.
	 */
	private int max;
	private int current;
	private int powerval;
	
	public Battery(int m, int c, int pv)
	{
		max = m;
		current = c;
		powerval = pv;
	}
	
	public int getCurrentCapacity()
	{
		return current;
	}
	
	/**
	 * If the charging of the battery exceeds the max capacity, 
	 * the current capacity is set to the max capacity.
	 */
	public void increment()
	{
		int temp = current + powerval;
		
		if (temp > max)
		{
			current = max;
		}
		else
		{
			current = temp;
		}
	}
	
	/**
	 * If the emptying of the battery falls below 0, 
	 * the current capacity is set to 0.
	 */
	public void decrement()
	{
		int temp = current - powerval;
		
		if (temp < 0)
		{
			current = 0;
		}
		else
		{
			current = temp;
		}
	}
}
