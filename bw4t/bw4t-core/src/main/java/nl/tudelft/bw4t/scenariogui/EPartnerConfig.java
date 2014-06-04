package nl.tudelft.bw4t.scenariogui;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Valentine Mairet
 * @author Jan Giesenberg
 * 
 * Information about an EPartner to be created by the server.
 */
public final class EPartnerConfig {

	private String name = "E-Partner";

	private int amount = 1;

	private boolean gps = false;

	private boolean forgetmenot = false;

	/**
	 * Returns the epartner name.
	 * 
	 * @return The epartner name.
	 */
	public String getEpartnerName() {
		return name;
	}

	/**
	 * Sets the epartner name.
	 * 
	 * @param name
	 *            The epartner name.
	 */
	@XmlElement
	public void setEpartnerName(String name) {
		this.name = name;
	}

	/**
	 * Returns the epartner amount.
	 * 
	 * @return The epartner amount.
	 */
	public int getEpartnerAmount() {
		return amount;
	}

	/**
	 * Sets the epartner amount.
	 * 
	 * @param amount
	 *            The epartner amount.
	 */
	@XmlElement
	public void setEpartnerAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Returns the use of gps.
	 * 
	 * @return If gps is enabled.
	 */
	public boolean isGps() {
		return gps;
	}

	/**
	 * Sets the use of gps.
	 * 
	 * @param gps
	 *            If gps is enabled.
	 */
	@XmlElement
	public void setGps(boolean gps) {
		this.gps = gps;
	}

	/**
	 * ??
	 * 
	 * @return
	 */
	public boolean isForgetMeNot() {
		return forgetmenot;
	}

	/**
	 * ??
	 * 
	 * @param fmn
	 */
	@XmlElement
	public void setForgetMeNot(boolean fmn) {
		this.forgetmenot = fmn;
	}

	/**
	 * Returns all the properties as a String.
	 * 
	 * @return All the EPartnerConfig properties.
	 */
	public String ecToString() {
		return name + amount + gps + forgetmenot;
	}
}
