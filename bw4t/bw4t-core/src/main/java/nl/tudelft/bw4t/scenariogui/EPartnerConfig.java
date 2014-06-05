package nl.tudelft.bw4t.scenariogui;

import javax.xml.bind.annotation.XmlElement;

/**
 * Information about an EPartner to be created by the server.
 */
public final class EPartnerConfig {

	public static final String DEFAULT_GOAL_FILENAME_REFERENCE = "epartner";

	public static final String DEFAULT_GOAL_FILENAME = "epartner.goal";

	private String name = "E-Partner";

	private int amount = 1;

	private String epartnerReferenceName = "";

	private String epartnerGoalFileName = "*.goal";

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

	/**
	 * Returns the reference name in goal.
	 * 
	 * @return The reference name in goal.
	 */
	public String getReferenceName() {
		return epartnerReferenceName;
	}

	/**
	 * Sets the reference name in goal.
	 * 
	 * @param _referenceName
	 *            The reference name in goal.
	 */
	@XmlElement
	public void setReferenceName(String _referenceName) {
		this.epartnerReferenceName = _referenceName;
	}

	/**
	 * Returns the goal file name.
	 * 
	 * @return The goal file name.
	 */
	public String getFileName() {
		return epartnerGoalFileName;
	}

	/**
	 * Sets the goal file name.
	 * 
	 * @param _fileName
	 *            The goal file name.
	 */
	@XmlElement
	public void setFileName(String _fileName) {
		this.epartnerGoalFileName = _fileName;
	}

}
