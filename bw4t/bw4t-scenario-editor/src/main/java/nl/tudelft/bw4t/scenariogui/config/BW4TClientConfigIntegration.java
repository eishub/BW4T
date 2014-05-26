package nl.tudelft.bw4t.scenariogui.config;

import javax.xml.bind.annotation.XmlRootElement;

import nl.tudelft.bw4t.scenarioeditor.BW4TClientConfig;
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
public class BW4TClientConfigIntegration {
    
    /**
     * A <code>BW4TClientConfig</code> object holding the configuration.
     *
     * @param mainPanel     Serves as the content pane.
     * @param newOutputFile Contains the file location used for saving.
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

}
