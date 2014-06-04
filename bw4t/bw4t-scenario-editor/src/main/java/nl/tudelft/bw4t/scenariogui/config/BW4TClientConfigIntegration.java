package nl.tudelft.bw4t.scenariogui.config;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;
import nl.tudelft.bw4t.scenariogui.gui.panel.ConfigurationPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.EntityPanel;
import nl.tudelft.bw4t.scenariogui.gui.panel.MainPanel;

/**
 * Allows a BW4TClientConfig object to be created from
 * a main panel.
 * 
 * @author Nick Feddes
 *
 */
public final class BW4TClientConfigIntegration {
    
    private static BotConfig currentBotConfig;
    private static EPartnerConfig currentEpartnerConfig;
    
    /** Prevents this class from being instantiated. */
    private BW4TClientConfigIntegration() { }
    
    /**
     * A <code>BW4TClientConfig</code> object holding the configuration.
     *
     * @param mainPanel     Serves as the content pane.
     * @param newOutputFile Contains the file location used for saving.
     * @return The created config object.
     */
    public static BW4TClientConfig createConfigFromPanel(final MainPanel mainPanel,
                            final String newOutputFile) {
        BW4TClientConfig config = new BW4TClientConfig();
        ConfigurationPanel configPanel = mainPanel.getConfigurationPanel();
        config.setClientIp(configPanel.getClientIP());
        config.setClientPort(configPanel.getClientPort());
        config.setServerIp(configPanel.getServerIP());
        config.setServerPort(configPanel.getServerPort());
        config.setLaunchGui(configPanel.useGui());
//        configPanel.useGoal();
        config.setUseGoal(ConfigurationPanel.DEFAULT_VALUES.USE_GOAL.getBooleanValue());
        config.setMapFile(configPanel.getMapFile());
        EntityPanel entityPanel = mainPanel.getEntityPanel();
//        TODO: read out bot panel and add each BotConfig to the list of bots
//        botPanel.getTable().;
        config.setFileLocation(newOutputFile);
        return config;
    }

    /**
     * Gets the config of the bot that is currently selected.
     * @return The config of the bot that is selected, null if none is selected.
     */
    public static BotConfig getCurrentBotConfig() {
        return currentBotConfig;
    }

    /**
     * Sets the botconfig of the bot that is currently selected.
     * @param currentBotConfig The botconfig of the bot selected in the bot table.
     */
    public static void setCurrentBotConfig(BotConfig currentBotConfig) {
        BW4TClientConfigIntegration.currentBotConfig = currentBotConfig;
    }

    /**
     * Gets the config of the e-partner that is currently selected.
     * @return The config of the e-partner that is selected, null if none is selected.
     */
    public static EPartnerConfig getCurrentEpartnerConfig() {
        return currentEpartnerConfig;
    }

    /**
     * Sets the e-partnerconfig of the e-partner that is currently selected.
     * @param currentEpartnerConfig The e-partnerconfig of the e-partner selected in the e-partner table.
     */
    public static void setCurrentEpartnerConfig(EPartnerConfig currentEpartnerConfig) {
        BW4TClientConfigIntegration.currentEpartnerConfig = currentEpartnerConfig;
    }
}
