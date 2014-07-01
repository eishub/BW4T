package nl.tudelft.bw4t.scenariogui.epartner.controller;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.epartner.gui.EPartnerViewInterface;
import nl.tudelft.bw4t.scenariogui.epartner.gui.EpartnerFrame;

/**
 * EpartnerController is in charge of all events that happen on the EpartnerFrame.
 */

public class EpartnerController {

    private Set<EPartnerViewInterface> views = new HashSet<>();
    
    private EPartnerConfig epConfig;
    
    private MainPanel parent;


    /**
     * Create the EpartnerController.
     * @param panel The MainPanel.
     * @param row The row the e-partner is on in the ScenarioEditor.
     */
    public EpartnerController(MainPanel panel, int row) {
        epConfig = panel.getClientConfig().getEpartner(row);
        this.parent = panel;
    }
    
    /**
     * Create the Epartner controller 
     * @param config The epartner config file
     */
    public EpartnerController(EPartnerConfig config) {
        epConfig = config;
    }
    
    /**
     * Add a view to the set of views.
     * 
     * @param view The view that is added to the set
     */
    public void addView(EPartnerViewInterface view) {
        views.add(view);
        view.updateView();
    }
    
    /**
     * Delete a view from the set of views.
     * 
     * @param view The view that is deleted from the set
     */
    public void removeView(EPartnerViewInterface view) {
        views.remove(view);
    }
    
    public String getEpartnerName() {
        return epConfig.getEpartnerName();
    }

    public int getEpartnerAmount() {
        return epConfig.getEpartnerAmount();
    }

    public boolean isGps() {
        return epConfig.isGps();
    }

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
     * Updates the EPartnerConfig file with the values from the EpartnerFrame.
     * 
     * @param epf The EpartnerFrame the values are taken from.
     */
    public void updateConfig(EpartnerFrame epf) {
        epConfig.setEpartnerName(epf.getEpartnerName());
        epConfig.setEpartnerAmount(epf.getEpartnerAmount());
        epConfig.setFileName(epf.getEpartnerGoalFile());
        epConfig.setReferenceName(epf.getEpartnerReference());
        epConfig.setGps(epf.getGPS());
        epConfig.setForgetMeNot(epf.getForgetMeNot());
        for (EPartnerViewInterface evi: views) {
            evi.updateView();
        }
    }

    public MainPanel getParent() {
        return parent;
    }

    public EPartnerConfig getEPartnerConfig() {
        return epConfig;
    }
}
