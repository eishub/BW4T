package nl.tudelft.bw4t.scenariogui;

/**
 * Information about an EPartner to be created by the server.
 */
public final class EPartnerConfig {
	
	/**
	 * The entity name for this e-partner.
	 */
	private String name = "";

	/**
	 * The amount of e-Partners created from the config. 
	 */
    private int amount = 1;

	/**
	 * Boolean value to determine if the bot can use GPS (default = false).
	 */
	private boolean gps = false;

	/**
     * Boolean value to determine if the robot can be left
     * alone without it giving warnings.
     */
    private boolean forgetmenot = false;
    
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

	public boolean isGps() {
		return gps;
	}

	public void setGps(boolean gps) {
		this.gps = gps;
	}

	public boolean isForgotMeNot() {
		return forgetmenot;
	}

	public void setForgetMeNot(boolean fmn) {
		this.forgetmenot = fmn;
	}
}
