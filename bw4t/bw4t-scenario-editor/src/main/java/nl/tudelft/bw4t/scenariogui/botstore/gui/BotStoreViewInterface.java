package nl.tudelft.bw4t.scenariogui.botstore.gui;

import nl.tudelft.bw4t.scenariogui.botstore.controller.BotController;

/**
 * BotStoreViewInterface for the BotEditorPanel.
 * @author Wendy
 *
 */
public interface BotStoreViewInterface {
    
    /**
     * Updates the BotEditorPanel with the values from the controller
     */
    void updateView();
    
    /**
     * Set the controller
     * @param bsc 
     */
    void setController(BotController bsc);
    
    /**
     * Returns the name of the bot.
     * 
     * @return The name of the bot.
     */
    String getBotName();
    
    /**
     * Returns the amount of bots of this type.
     * 
     * @return The amount of bots of this type.
     */
    int getBotAmount();
    
    /**
     * @return the size of the robot.
     */
    int getBotSize();
    
    /**
     * @return the speed of the robot
     */
    int getBotSpeed();
    
    /**
     * Returns if the battery is enabled or not.
     * 
     * @return If the battery is enabled.
     */
    boolean isBatteryEnabled();
    
    /**
     * @return the robot's battery capacity.
     */
    int getBotBatteryCapacity();
    
    /**
     * @return the robot's battery discharge rate.
     */
    double getBotBatteryDischargeRate();
    
    /**
     * @return if the robot has a color blind handicap.
     */
    boolean getColorBlindHandicap();

    /**
     * @return if the robot has a gripper handicap.
     */
    boolean getGripperHandicap();
    
    /**
     * @return if the robot has a move speed handicap.
     */
    boolean getMoveSpeedHandicap();

    /**
     * @return if the robot has a size overload handicap.
     */
    boolean getSizeOverloadHandicap();
    
    /**
     * Returns the amount of grippers the bot has.
     * 
     * @return The amount of grippers the bot has.
     */
    int getGrippers();
    
    /**
     * Returns the reference name in goal.
     * @return The reference name in goal.
     */
    String getReferenceName();
    
    /**
     * Returns the goal file name.
     * @return The goal file name.
     */
    String getFileName();

    void dispose();
}
