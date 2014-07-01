package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;

import nl.tudelft.bw4t.map.EntityType;
import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotStoreViewInterface;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;

/**
 * The BotController class is in charge of all events that happen on the BotEditorPanel. It delegates all events
 * to classes implementing ActionListener, sending the view along as an argument.
 */
public class BotController {

    private Set<BotStoreViewInterface> views = new HashSet<>();

    private BotConfig botConfig;
    
    private MainPanel mp;
    
    private BW4TClientConfig clientconfig;
    
    /**
     * Create the BotStore controllers
     *
     * @param mainPanel the main panel
     * @param row Index of the bot in BW4TClientConfig
     * @param cc BW4TClientConfig
     */
    public BotController(MainPanel mainPanel, int row, BW4TClientConfig cc) {
        botConfig = mainPanel.getClientConfig().getBot(row);
        mp = mainPanel;
        clientconfig = cc;
    }
    
    /**
     * Add a view to the set of views.
     * 
     * @param view The view that is added to the set.
     */
    public void addView(BotStoreViewInterface view) {
        views.add(view);
        view.updateView();
    }
    
    /**
     * Delete a view from the set of views.
     * 
     * @param view The view that is deleted from the set
     */
    public void removeView(BotStoreViewInterface view) {
        views.remove(view);
    }
    
    public String getBotName() {
        return botConfig.getBotName();
    }
    
    public int getBotAmount() {
        return botConfig.getBotAmount();
    }
    
    public int getBotSize() {
        return botConfig.getBotSize();
    }
    
    public int getBotSpeed() {
        return botConfig.getBotSpeed();
    }
    
    public boolean isBatteryEnabled() {
        return botConfig.isBatteryEnabled();
    }
    
    public int getBotBatteryCapacity() {
        return botConfig.getBotBatteryCapacity();
    }
    
    public double getBotBatteryDischargeRate() {
        return botConfig.getBotBatteryDischargeRate();
    }
    
    public boolean getColorBlindHandicap() {
        return botConfig.getColorBlindHandicap();
    }

    public boolean getGripperHandicap() {
        return botConfig.getGripperHandicap();
    }
    
    public boolean getMoveSpeedHandicap() {
        return botConfig.getMoveSpeedHandicap();
    }

    public boolean getSizeOverloadHandicap() {
        return botConfig.getSizeOverloadHandicap();
    }
    
    public int getGrippers() {
        return botConfig.getGrippers();
    }
    
    public String getReferenceName() {
        return botConfig.getReferenceName();
    }
    
    public String getFileName() {
        return botConfig.getFileName();
    }
    
    public MainPanel getMainPanel() {
        return mp;
    }
    
    public BW4TClientConfig getBW4TClientConfig() {
        return clientconfig;
    }
    
    public EntityType getBotController() {
        return botConfig.getBotController();
    }
    
    /**
     * Updates the BotConfig file with the values from the BotEditorPanel.
     * 
     * @param bep The BotEditorPanel the values are taken from.
     */
    public void updateConfig(BotEditorPanel bep) {
        botConfig.setBotName(bep.getBotName());
        botConfig.setBotController(EntityType.getType((String) bep
                .getBotControllerSelector()
                .getSelectedItem()));
        botConfig.setBotAmount(bep.getBotAmount());
        botConfig.setBotSize(bep.getBotSize());
        botConfig.setBotSpeed(bep.getBotSpeed());
        botConfig.setBotBatteryCapacity(bep.getBotBatteryCapacity());
        botConfig.setGrippers(bep.getGrippers());
        botConfig.setBatteryEnabled(bep.isBatteryEnabled());
        botConfig.setColorBlindHandicap(bep.getColorBlindHandicap());
        botConfig.setGripperHandicap(bep.getGripperHandicap());
        botConfig.setMoveSpeedHandicap(bep.getMoveSpeedHandicap());
        botConfig.setSizeOverloadHandicap(bep.getSizeOverloadHandicap());
        botConfig.setReferenceName(bep.getReferenceName());
        botConfig.setFileName(bep.getFileName());

        for (BotStoreViewInterface bsvi: views) {
            bsvi.updateView();
        }
    }
    
    /**
     * Open the JFileChooser to select a Goal file.

     * @param view The BotEditorPanel.
     */
    @SuppressWarnings("static-access")
    public void openGoalFile(BotEditorPanel view) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(FileFilters.goalFilter());
        if (jfc.showOpenDialog(view) == jfc.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            String path = f.getAbsolutePath();
            view.getFile().setText(path);
        }
    }
    
    public void setNewBatteryValue(BotEditorPanel view) {
        if (view.isBatteryEnabled()) {
            double res = getBotBatteryDischargeRate();
            DecimalFormat df = new DecimalFormat("#.###");
            String value = df.format(res);
            view.getBatteryUseValueLabel().setText(padString(value));
        }
    }
    
    private String padString(String value) {
        StringBuffer buf = new StringBuffer();
        while (buf.length() < 3) {
            buf.append(value);
        }
        return buf.toString();
    }

    public BotConfig getBotConfig() {
        return botConfig;
    }    
}
