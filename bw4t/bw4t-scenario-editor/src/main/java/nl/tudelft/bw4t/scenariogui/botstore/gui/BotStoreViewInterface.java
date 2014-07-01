package nl.tudelft.bw4t.scenariogui.botstore.gui;

import nl.tudelft.bw4t.scenariogui.botstore.controller.BotController;

/**
 * BotStoreViewInterface for the BotEditorPanel.
 *
 */
public interface BotStoreViewInterface {
    
    void updateView();
    
    void setController(BotController bsc);
    
    String getBotName();
    
    int getBotAmount();
    
    int getBotSize();
    
    int getBotSpeed();
    
    boolean isBatteryEnabled();
    
    int getBotBatteryCapacity();
    
    double getBotBatteryDischargeRate();
    
    boolean getColorBlindHandicap();

    boolean getGripperHandicap();
    
    boolean getMoveSpeedHandicap();

    boolean getSizeOverloadHandicap();
    
    int getGrippers();
    
    String getReferenceName();
    
    String getFileName();

    void dispose();
}
