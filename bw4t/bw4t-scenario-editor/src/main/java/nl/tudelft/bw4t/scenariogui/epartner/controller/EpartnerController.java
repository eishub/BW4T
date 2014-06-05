package nl.tudelft.bw4t.scenariogui.epartner.controller;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.xml.bind.annotation.XmlElement;

import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.epartner.gui.EPartnerViewInterface;
import nl.tudelft.bw4t.scenariogui.epartner.gui.EpartnerFrame;
import nl.tudelft.bw4t.scenariogui.panel.gui.MainPanel;

/**
 * EpartnerController is in charge of all events that happen on the EpartnerGUI.
 * 
 * @author Wendy Bolier
 */

public class EpartnerController {

	private Set<EPartnerViewInterface> views = new HashSet<>();
	
	private EPartnerConfig epConfig;
	
	private String ePartnerName;
	private int ePartnerAmount;
	private boolean ePartnerGPS;
	private boolean ePartnerFMN;


	/**
	 * Create the Epartner controller
	 * 
	 */
	public EpartnerController(MainPanel panel, int row) {
		epConfig = panel.getEntityPanel().getEPartnerConfig(row);
	}
	
	/**
	 * Create the Epartner controller 
	 * (this one is temporary and has to be deleted when everything is MVC)
	 * @param config
	 */
	public EpartnerController(EPartnerConfig config) {
		epConfig = config;
	}
	
	/**
	 * Add a view to the set of views.
	 * 
	 * @param view: the view that is added to the set
	 */
	public void addView(EPartnerViewInterface view) {
		views.add(view);
		view.update();
	}
	
	/**
	 * Delete a view from the set of views.
	 * 
	 * @param view: the view that is deleted from the set
	 */
	public void removeView(EPartnerViewInterface view) {
		views.remove(view);
		view.update();
	}
	
	/**
	 * Returns the epartner name.
	 * 
	 * @return The epartner name.
	 */
	public String getEpartnerName() {
		return ePartnerName;
	}

	/**
	 * Returns the epartner amount.
	 * 
	 * @return The epartner amount.
	 */
	public int getEpartnerAmount() {
		return ePartnerAmount;
	}

	/**
	 * Returns the use of gps.
	 * 
	 * @return If gps is enabled.
	 */
	public boolean isGps() {
		return ePartnerGPS;
	}

	/**
	 * ??
	 * 
	 * @return
	 */
	public boolean isForgetMeNot() {
		return ePartnerFMN;
	}

	// VRAAG VOOR JAN
	// CONTROLLER UPDATEN MET WAARDEN UIT EPF
	// OF
	// EPF UPDATEN MET WAARDEN UIT CONTROLLER?
	// ????????????????????????????????????????????
	public void update(EpartnerFrame epf) {
		setEpartnerName(epf.getName());
		setEpartnerAmount(epf.getEpartnerAmount());
		setGps(epf.getGPSCheckbox().isSelected());
		setForgetMeNot(epf.getForgetMeNotCheckbox().isSelected());
	}
}