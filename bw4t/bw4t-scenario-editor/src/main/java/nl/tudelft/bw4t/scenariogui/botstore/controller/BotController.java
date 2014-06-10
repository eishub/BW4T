package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotStoreViewInterface;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.util.FileFilters;

/**
 * The BotStoreController class is in charge of all events that happen on the BotStoreGUI. It delegates all events
 * to classes implementing ActionListener, sending the view along as an argument.
 */
public class BotController {

    /**
     * Create a set with all views.
     */
    private Set<BotStoreViewInterface> views = new HashSet<>();

    /**
     * Create a BotConfig.
     */
    private BotConfig botConfig;
    
    /**
     * Create the BotStore controllers
     *
     *@param botEditor The parent view, used to call relevant functions by the event listeners
     *@param mainPanel the main panel
     *@param row Index of the bot in BW4TClientConfig
     */
    public BotController(BotEditor botEditor, MainPanel mainPanel, int row) {
    	botConfig = mainPanel.getClientConfig().getBot(row);
    }
    
    /**
     * Add a view to the set of views.
     * 
     * @param view : the view that is added to the set.
     */
    public void addView(BotStoreViewInterface view) {
    	views.add(view);
    	view.updateView();
    }
    
    /**
	 * Delete a view from the set of views.
	 * 
	 * @param view : the view that is deleted from the set
	 */
	public void removeView(BotStoreViewInterface view) {
		views.remove(view);
	}
    
	/**
	 * Returns the name of the bot.
	 * 
	 * @return The name of the bot.
	 */
	public String getBotName() {
		return botConfig.getBotName();
	}
	
	/**
	 * Returns the amount of bots of this type.
	 * 
	 * @return The amount of bots of this type.
	 */
	public int getBotAmount() {
		return botConfig.getBotAmount();
	}
	
	/**
	 * @return the size of the robot.
	 */
	public int getBotSize() {
		return botConfig.getBotSize();
	}
	
	/**
	 * @return the speed of the robot
	 */
	public int getBotSpeed() {
		return botConfig.getBotSpeed();
	}
	
	/**
	 * Returns if the battery is enabled or not.
	 * 
	 * @return If the battery is enabled.
	 */
	public boolean isBatteryEnabled() {
		return botConfig.isBatteryEnabled();
	}
	
	/**
	 * @return the robot's battery capacity.
	 */
	public int getBotBatteryCapacity() {
		return botConfig.getBotBatteryCapacity();
	}
	
	/**
	 * @return the robot's battery discharge rate.
	 */
	public double getBotBatteryDischargeRate() {
		return botConfig.getBotBatteryDischargeRate();
	}
	
	/**
	 * @return if the robot has a color blind handicap.
	 */
	public boolean getColorBlindHandicap() {
		return botConfig.getColorBlindHandicap();
	}

	/**
	 * @return if the robot has a gripper handicap.
	 */
	public boolean getGripperHandicap() {
		return botConfig.getGripperHandicap();
	}
	
	/**
	 * @return if the robot has a move speed handicap.
	 */
	public boolean getMoveSpeedHandicap() {
		return botConfig.getMoveSpeedHandicap();
	}

	/**
	 * @return if the robot has a size overload handicap.
	 */
	public boolean getSizeOverloadHandicap() {
		return botConfig.getSizeOverloadHandicap();
	}
	
	/**
	 * Returns the amount of grippers the bot has.
	 * 
	 * @return The amount of grippers the bot has.
	 */
	public int getGrippers() {
		return botConfig.getGrippers();
	}
    
    /**
	 * Returns the reference name in goal.
	 * @return The reference name in goal.
	 */
	public String getReferenceName() {
		return botConfig.getReferenceName();
	}
	
	/**
	 * Returns the goal file name.
	 * @return The goal file name.
	 */
	public String getFileName() {
		return botConfig.getFileName();
	}
	
	/**
	 * Updates the bot config file with the values from the BotEditorPanel
	 * 
	 * @param bep is the BotEditorPanel the values are taken from.
	 */
	public void updateConfig(BotEditorPanel bep) {
		botConfig.setBatteryEnabled(bep.isBatteryEnabled());
		botConfig.setBotAmount(bep.getBotAmount());
		botConfig.setBotBatteryCapacity(bep.getBotBatteryCapacity());
		botConfig.setBotBatteryDischargeRate(bep.getBotBatteryDischargeRate());
		botConfig.setBotName(bep.getBotName());
		botConfig.setBotSize(bep.getBotSize());
		botConfig.setBotSpeed(bep.getBotSpeed());
		botConfig.setColorBlindHandicap(bep.getColorBlindHandicap());
		for (BotStoreViewInterface bsvi: views) {
			bsvi.updateView();
		}
	}
	
	/**
	 * Open a Goal file

	 * @param view the BotEditorPanel.
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
  
	/**
	 * Calculate the dischargeRate from a bot by using his size and speed.
	 * @param bot The bot we want to know the discharge rate from.
	 * @return discharge rate from that bot
	 */
    public double getDischargeRate(BotConfig bot) {
		return 0.0002 * bot.getBotSize() + 0.0004 * bot.getBotSpeed();
    }     
    
    /**
     
    public void getNewBatteryValue(BotEditorPanel view) {
    	if (bot.isBatteryEnabled()) {
	        int speed = bot.getBotSpeed();
	        int size = bot.getBotSize();
	        double res = 0.002 * size + 0.000025 * speed;
	        DecimalFormat df = new DecimalFormat("#.######");
	        String value = df.format(res);
	        view.getBatteryUseValueLabel().setText(padString(value));
    	}
    	view.getDataObject().setBotSpeed(view.getSpeedSlider().getValue());
    }*/
    
    /** 
     * When the speed or size slider is moved, calculate the new battery discharge rate
     * and update the BatteryUseValueLabel
     * @param botSpeed the (new) bot speed
     * @param botSize the (new) bot size
     * @param view the BotEditorPanel
     */
    public void setNewBatteryValue(int botSpeed, int botSize, BotEditorPanel view) {
    	if(view.isBatteryEnabled()) {
    		double res = 0.002 * botSize + 0.000025 * botSize;
    		DecimalFormat df = new DecimalFormat("#.######");
	        String value = df.format(res);
	        view.getBatteryUseValueLabel().setText(padString(value));
    	}
    }
    
    /**
     * Pad the string with zeros (the string with
     * the value for the battery usage is aligned with
     * the sliders, and will cause the sliders to resize
     * when changed. This function keeps the string at a
     * certain length, so the sliders aren't resized anymore).
     * @param value The string to be padded.
     * @return The padded string.
     */
    public String padString(String value) {
        StringBuffer buf = new StringBuffer();
        while (value.length() < 8) {
            buf.append("0");
        }
        return buf.toString();
    }
    
    
    
    
    
    
    
}
