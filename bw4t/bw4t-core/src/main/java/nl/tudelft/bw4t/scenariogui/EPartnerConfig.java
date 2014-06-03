package nl.tudelft.bw4t.scenariogui;

import javax.xml.bind.annotation.XmlElement;

/**
 * Information about an EPartner to be created by the server.
 */
public final class EPartnerConfig {

	/**
	 * The entity name for this e-partner.
	 */
	private String name = "E-Partner";

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

    @XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	@XmlElement
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isGps() {
		return gps;
	}

	@XmlElement
	public void setGps(boolean gps) {
		this.gps = gps;
	}

	public boolean isForgotMeNot() {
		return forgetmenot;
	}

	@XmlElement
	public void setForgetMeNot(boolean fmn) {
		this.forgetmenot = fmn;
	}
}
