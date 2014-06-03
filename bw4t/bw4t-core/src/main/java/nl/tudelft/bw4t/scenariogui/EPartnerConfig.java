package nl.tudelft.bw4t.scenariogui;

/**
 * Information about an EPartner to be created by the server.
 */
public final class EPartnerConfig {
	
	/**
	 * The entity name for this e-partner.
	 */
	private String name = "";

    private int amount = 1;

	/**
     * Boolean value to determine if the robot can be left
     * alone without it giving warnings.
     */
    private boolean leftAlone = false;
    
    /**
     * Boolean value to determine if the bot can use GPS (default = false).
     */
    private boolean gps = false;
	
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isLeftAlone() {
		return leftAlone;
	}

	public void setLeftAlone(boolean leftAlone) {
		this.leftAlone = leftAlone;
	}

	public boolean isGps() {
		return gps;
	}

	public void setGps(boolean gps) {
		this.gps = gps;
	}
}
