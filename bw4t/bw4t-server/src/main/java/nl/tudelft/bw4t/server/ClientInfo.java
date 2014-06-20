package nl.tudelft.bw4t.server;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.scenariogui.BW4TClientConfig;
import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;

class ClientInfo {

    private BW4TClientConfig clientConfig;
    private List<BotConfig> requestedBots = new ArrayList<>();
    private List<EPartnerConfig> requestedEPartners = new ArrayList<>();

    public ClientInfo(int reqAgent, int reqHuman) {
        assert reqHuman >= 0;
        assert reqAgent >= 0;
        if (reqAgent > 0) {
            BotConfig bot = BotConfig.createDefaultRobot();
            bot.setBotAmount(reqAgent);
            requestedBots.add(bot);
        }
        if (reqHuman > 0) {
            BotConfig bot = BotConfig.createDefaultHumans();
            bot.setBotAmount(reqHuman);
            requestedBots.add(bot);
        }
        this.clientConfig = new BW4TClientConfig();
    }


    public ClientInfo(BW4TClientConfig clientConfig) {
        if (clientConfig.getBots() != null){
            this.requestedBots = clientConfig.getBots();
        }
        if (clientConfig.getEpartners() != null) {
            this.requestedEPartners = clientConfig.getEpartners();
        }
        this.clientConfig = clientConfig;
    }

    public List<BotConfig> getRequestedBots() {
        return requestedBots;
    }

    public List<EPartnerConfig> getRequestedEPartners() {
        return requestedEPartners;
    }

    public String getMapFile() {
        return clientConfig == null ? "" : clientConfig.getMapFile();
    }

    public boolean isCollisionEnabled() {
        return clientConfig.isCollisionEnabled();
    }

    public boolean isVisualizePaths() {
        return clientConfig.isVisualizePaths();
    }
}
