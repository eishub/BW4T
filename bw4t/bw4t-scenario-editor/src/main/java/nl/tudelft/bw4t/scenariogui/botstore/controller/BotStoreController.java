package nl.tudelft.bw4t.scenariogui.botstore.controller;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditor;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotEditorPanel;
import nl.tudelft.bw4t.scenariogui.botstore.gui.BotStoreViewInterface;
import nl.tudelft.bw4t.scenariogui.editor.gui.MainPanel;
import nl.tudelft.bw4t.scenariogui.epartner.gui.EPartnerViewInterface;

/**
 * The BotStoreController class is in charge of all events that happen on the BotStoreGUI. It delegates all events
 * to classes implementing ActionListener, sending the view along as an argument.
 */
public class BotStoreController {

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
     *@param mainPanel
     *@param model
     */
    public BotStoreController(BotEditor botEditor, MainPanel mainPanel, BW4TClientConfig model) {
    	botConfig = model;
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
		botConfig.setBatteryEnabled(bep.getBatteryEnabled());
		botConfig.setBotAmount(bep.getBotAmountTextField());
		botConfig.setBotBatteryCapacity(newBatteryCapacity);
		botConfig.setBotBatteryDischargeRate(newBatteryDischargeRate);
		botConfig.setBotName(name);
		botConfig.setBotSize(newSize);
		botConfig.setBotSpeed(newSpeed);
		botConfig.setColorBlindHandicap(bool);
		for (BotStoreViewInterface bsvi: views) {
			bsvi.updateView();
		}
	}
    
    /**
     * Do we still need this? Or is it already calculated somewhere else?
     * 
    public double getDischargeRate() {
		return 0.0002 * botSize + 0.0004 * botSpeed;
    }
    */ 
}

/**
 * DIT KAN GEDELETED WORDEN ALS ALLES WERKT - wendy
 * 
 * 
 * 
 * this.view = pview;

        view.getBotEditorPanel().getResetButton().addActionListener(
                new ResetButton(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getSaveButton().addActionListener(
                new ApplyButton(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getSpeedSlider().addMouseListener(
                new SpeedSlider(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getSizeSlider().addMouseListener(
                new SizeSlider(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getBatterySlider().addMouseListener(
                new BatterySlider(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getGripperCheckbox().addActionListener(
                new GripperBox(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getColorblindCheckbox().addActionListener(
                new ColorBox(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getsizeoverloadCheckbox().addActionListener(
                new WalkingBox(getMainView().getBotEditorPanel()));

        view.getBotEditorPanel().getmovespeedCheckbox().addActionListener(
                new JumpBox(getMainView().getBotEditorPanel()));
        
        view.getBotEditorPanel().getCancelButton().addActionListener(
                new CancelButton(getMainView()));
        
        view.getBotEditorPanel().getBatteryEnabledCheckbox().addActionListener(
                new BatteryBox(getMainView().getBotEditorPanel()));
        
        view.getBotEditorPanel().getmovespeedCheckbox().addActionListener(
                new SpeedBox(getMainView().getBotEditorPanel()));
        
        view.getBotEditorPanel().getFileButton().addActionListener(
        		new GoalFileButton(getMainView().getBotEditorPanel()));
 */

