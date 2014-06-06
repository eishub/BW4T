package nl.tudelft.bw4t.scenariogui.epartner.controller;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.epartner.gui.EPartnerViewInterface;
import nl.tudelft.bw4t.scenariogui.epartner.gui.EpartnerFrame;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;

/**
 * EpartnerController is in charge of all events that happen on the EpartnerGUI.
 */

public class EpartnerController {

	/**
	 * Create a set with all views.
	 */
	private Set<EPartnerViewInterface> views = new HashSet<>();
	
	/**
	 * Create an EPartnerConfig.
	 */
	private EPartnerConfig epConfig;


	/**
	 * Create the Epartner controller.
	 * @param panel the MainPanel
	 * @param row row
	 */
	public EpartnerController(MainPanel panel, int row) {
		epConfig = panel.getClientConfig().getEpartner(row);
	}
	
	/**
	 * Create the Epartner controller 
	 * (this one is temporary and has to be deleted when everything is MVC)
	 * @param config : the epartner config file
	 */
	public EpartnerController(EPartnerConfig config) {
		epConfig = config;
	}
	
	/**
	 * Add a view to the set of views.
	 * 
	 * @param view : the view that is added to the set
	 */
	public void addView(EPartnerViewInterface view) {
		views.add(view);
		view.updateView();
	}
	
	/**
	 * Delete a view from the set of views.
	 * 
	 * @param view : the view that is deleted from the set
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
	 * Returns true if the GPS is used.
	 * 
	 * @return true if the GPS is used.
	 */
	public boolean isGps() {
		return epConfig.isGps();
	}

	/**
	 * Returns true if the ForgetMeNot function is used.
	 * 
	 * @return true if the ForgetMeNot function is used.
	 */
	public boolean isForgetMeNot() {
		return epConfig.isForgetMeNot();
	}

    public String getReferenceName() {
        return epConfig.getReferenceName();
    }

    public String getFileName() {
        return epConfig.getFileName();
    }

	/**
	 * Updates the epartner config file with the values from the EpartnerFrame
	 * 
	 * @param epf is the Epartnerframe the values are taken from.
	 */
	public void updateConfig(EpartnerFrame epf) {
		epConfig.setEpartnerName(epf.getName());
		epConfig.setEpartnerAmount(epf.getEpartnerAmount());
		epConfig.setGps(epf.getGPS());
		epConfig.setForgetMeNot(epf.getForgetMeNot());
		for (EPartnerViewInterface evi: views) {
			evi.updateView();
		}
	}
}
