package nl.tudelft.bw4t.server;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.scenariogui.BotConfig;
import nl.tudelft.bw4t.scenariogui.EPartnerConfig;

class ClientInfo {
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
    }

    public ClientInfo(List<BotConfig> reqBots, List<EPartnerConfig> reqEP) {
        if (reqBots != null){
            this.requestedBots = reqBots;
        }
        if (reqEP != null) {
            this.requestedEPartners = reqEP;
        }
    }

    public List<BotConfig> getRequestedBots() {
        return requestedBots;
    }

    public List<EPartnerConfig> getRequestedEPartners() {
        return requestedEPartners;
    }
}
