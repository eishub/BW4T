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
		view.updateView();
	}
	
	/**
	 * Delete a view from the set of views.
	 * 
	 * @param view: the view that is deleted from the set
	 */
	public void removeView(EPartnerViewInterface view) {
		views.remove(view);
	}
	
	/**
	 * Returns the epartner name.
	 * 
	 * @return The epartner name.
	 */
	public String getEpartnerName() {
		return epConfig.getEpartnerName();
	}

	/**
	 * Returns the epartner amount.
	 * 
	 * @return The epartner amount.
	 */
	public int getEpartnerAmount() {
		return epConfig.getEpartnerAmount();
	}

	/**
	 * Returns the use of gps.
	 * 
	 * @return If gps is enabled.
	 */
	public boolean isGps() {
		return epConfig.isGps();
	}

	/**
	 * ??
	 * 
	 * @return
	 */
	public boolean isForgetMeNot() {
		return epConfig.isForgetMeNot();
	}

	/**
	 * 
	 * @param epf
	 */
	public void updateConfig(EpartnerFrame epf) {
		epConfig.setEpartnerName(epf.getName());
		epConfig.setEpartnerAmount(epf.getEpartnerAmount());
		epConfig.setGps(epf.getGPS());
		epConfig.setForgetMeNot(epf.getForgetMeNot());
		for(EPartnerViewInterface evi: views){
			evi.updateView();
		}
	}
}